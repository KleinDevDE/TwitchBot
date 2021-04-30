package de.kleindev.twitchbot.configuration;

import de.kleindev.twitchbot.configuration.file.YamlConfiguration;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Config {
    private static class YamlEntry {

        String description;
        Object o;

        public YamlEntry(String description, Object o) {
            this.description = description;
            this.o = o;
        }

        public boolean isNode() {
            return (o instanceof HashMap);
        }

    }

    public void onReload() {

    }

    private static final Pattern PATTERN = Pattern.compile("([^.]+)");

    public abstract File getFile();

    protected void load() {
        load(true);
    }

    public void reload() {
        load(false);
    }

    protected void load(boolean save) {
        Object o = this;
        File file = getFile();

        Configuration cfg = YamlConfiguration.loadConfiguration(file);
        for (Field field : o.getClass().getDeclaredFields()) {
            ConfigPath path = field.getAnnotation(ConfigPath.class);
            if (path != null) {
                String p = path.path();
                try {
                    if (cfg.contains(p)) {
                        field.setAccessible(true);
                        field.set(o, cfg.get(p));
                    } else {
                        for (String s : path.mappings()) {
                            if (cfg.contains(s)) {
                                field.setAccessible(true);
                                field.set(o, cfg.get(s));
                            }
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
        if (save)
            save();
        long lastChecked = file.lastModified();
    }

    public void save() {
        Object o = this;
        File file = getFile();
        HashMap<String, YamlEntry> mappings = new HashMap<String, YamlEntry>();
        for (Field field : o.getClass().getDeclaredFields()) {
            ConfigPath path = field.getAnnotation(ConfigPath.class);
            if (path != null) {
                String p = path.path();
                try {
                    field.setAccessible(true);
                    Object value = field.get(o);
                    Matcher matcher = PATTERN.matcher(p);

                    String lastGroup = null;
                    List<String> previous = new ArrayList<String>();
                    while (matcher.find()) {
                        if (lastGroup != null) previous.add(lastGroup);
                        String s = matcher.group();
                        lastGroup = s;
                    }
                    HashMap<String, YamlEntry> hMap = mappings;
                    for (int i = 0; i < previous.size(); i++) {
                        String origin = previous.get(i);
                        YamlEntry en = hMap.get(origin);
                        if (en == null) {
                            en = new YamlEntry(null, new HashMap<String, YamlEntry>());
                            hMap.put(origin, en);
                        }
                        @SuppressWarnings("unchecked")
                        HashMap<String, YamlEntry> entryMap = (HashMap<String, YamlEntry>) en.o;

                        hMap = entryMap;
                    }
                    hMap.put(lastGroup, new YamlEntry(path.description(), value));


                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(file, false), true)) {
            writeNode(writer, mappings, new Yaml(), 0);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private void writeNode(PrintWriter writer, HashMap<String, YamlEntry> map, Yaml yaml, int level) {
        String prefix = "";
        for (int i = 0; i < level; i++) {
            prefix += "  ";
        }
        for (Entry<String, YamlEntry> en : map.entrySet()) {
            if (en.getValue().isNode()) {
                if (en.getValue().description != null && en.getValue().description.length() > 0) {
                    for (String s : en.getValue().description.split("\n")) {
                        writer.println(prefix + "# " + s);
                    }
                }
                writer.println((level == 0 ? "\n" : "") + prefix + en.getKey() + ":");
                writeNode(writer, (HashMap<String, YamlEntry>) en.getValue().o, yaml, level + 1);
            } else {
                if (en.getValue().description != null && en.getValue().description.length() > 0) {
                    for (String s : en.getValue().description.split("\n")) {
                        writer.println(prefix + "# " + s);
                    }
                }

                if (en.getValue().o instanceof List<?>) {
                    StringBuilder sb = new StringBuilder();
                    writer.println(prefix + en.getKey() + ":");
                    for (String s : (List<String>) en.getValue().o) {
                        sb.append(prefix).append("  - ").append(yaml.dump(s));
                    }
                    writer.println(sb.toString());
                } else
                    writer.println(prefix + en.getKey() + ": " + yaml.dump(en.getValue().o).replace("\r", "").replace("\n", ""));
            }
        }
    }
}
