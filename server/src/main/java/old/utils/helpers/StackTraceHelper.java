package old.utils.helpers;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StackTraceHelper {

	private static final Pattern PATTERN = Pattern.compile("(\\S+)\\.(\\S+)\\.(\\S+)\\((\\S+):(\\S+)\\)");
	
	public static class ConstructedStackTraceElement {
		
		public String packageName;
		public String className;
		public String methodName;
		public String fileName;
		public int line;
		
		public ConstructedStackTraceElement(Matcher matcher) {
			packageName = matcher.group(1);
			className = matcher.group(2);
			methodName = matcher.group(3);
			fileName = matcher.group(4);
			line = Integer.parseInt(matcher.group(5));
		}
		
	}
	
	public static List<ConstructedStackTraceElement> getElements(String stackTrace){
		List<ConstructedStackTraceElement> elements = new ArrayList<ConstructedStackTraceElement>();
		Matcher matcher = PATTERN.matcher(stackTrace);
		while(matcher.find()) {
			try {
				elements.add(new ConstructedStackTraceElement(matcher));
			} catch(Exception ex) {
				return null;
			}
		}
		return elements;
	}

	public static List<ConstructedStackTraceElement> getElements(StackTraceElement[] stackTraceElements){
		StringBuilder sb = new StringBuilder();
		for(StackTraceElement ste : stackTraceElements)
			sb.append(ste.toString()).append("\n");
		return getElements(sb.toString());
	}
	
}
