package designParser.markupGen.util;

public final class UmlProcessString {
    private UmlProcessString() {}

    public static String escapeAngleBraces(String str) {
        return str.replace("<", "\\<").replace(">", "\\>");
    }
}
