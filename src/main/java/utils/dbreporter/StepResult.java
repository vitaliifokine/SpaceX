package utils.dbreporter;

public enum StepResult {

    SUCCESS(1),
    UNKNOWN(-1),
    FAILED(0);

    private StepResult(int value) {
        this.value = value;
    }

    public int intValue() {
        return this.value;
    }

    private int value;
}
