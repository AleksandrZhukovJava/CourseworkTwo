package type;

public enum Type {
    WORK("Work task"),
    PERSONAL("Personal task");
    private final String type;
    Type(String type) {
        this.type = type;
    }
    public String getType() {
        return type;
    }
}
