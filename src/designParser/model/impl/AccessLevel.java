package designParser.model.impl;

public enum AccessLevel {
    Public,
    Private,
    Protected,
    Default;
    
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
}
