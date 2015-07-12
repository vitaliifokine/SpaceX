package elements.detailed;

public enum Status {

    SUCCESS (true), FAIL (false), UNKNOWN (null);

    public Boolean getBooleanValue() {
        return this.bool;
    }

    private Status(Boolean value) {
        this.bool = value;
    }

    private Boolean bool = null;

}
