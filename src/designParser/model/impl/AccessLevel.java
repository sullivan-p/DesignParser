package designParser.model.impl;

public enum AccessLevel {
    Public,
    Private,
    Protected,
    Default;
    
    public static AccessLevel fromString(String s) {
        switch (s) {
        case "public":
            return Public;
        case "private":
            return Private;
        case "protected":
            return Protected;
        case "":
            return Default;
        default:
            throw new IllegalArgumentException();
        }
    }
    
    @Override
    public String toString() {
        switch (this) {
        case Public:
            return "public";
        case Private:
            return "private";
        case Protected:
            return "protected";
        default:
            return "";
        }
    }
    
    public String toUmlString() {
        switch (this) {
        case Public:
            return "+";
        case Private:
            return "-";
        case Protected:
            return "#";
        default:
            return "";
        }
    }
}
