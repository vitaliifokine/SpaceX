package utils;

public enum Priority {

    LOWEST(5),
    LOW(4),
    MEDIUM(3),
    HIGH(2),
    HIGHEST(1);

    private Priority(int value) {
        this.value = value;
    }

    public int intValue() {
        return this.value;
    }

    private int value;
}
