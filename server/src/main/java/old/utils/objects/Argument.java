package old.utils.objects;

public enum Argument {
    NO_GUI("-nogui"),
    NO_CONSOLE("-noconsole"),
    DEBUG("-debug"),
    NO_COLOR("-nocolor");

    private String arg;

    Argument(String s) {
        this.arg = s;
    }

    public String getArg() {
        return arg;
    }
}
