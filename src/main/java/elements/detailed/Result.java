package elements.detailed;

public class Result <T> {

    public Result(Boolean status, T value, String description) {
        if (status == null) {
            this.status = Status.UNKNOWN;
        } else if (status) {
            this.status = Status.SUCCESS;
        } else {
            this.status = Status.FAIL;
        }
        this.description = description;
        this.value = value;
    }
    public Result(Boolean status, T value) {
        if (status == null) {
            this.status = Status.UNKNOWN;
        } else if (status) {
            this.status = Status.SUCCESS;
        } else {
            this.status = Status.FAIL;
        }
        this.value = value;
    }

    public Status getStatus() {
        return this.status;
    }
    public String getDescription() {
        return this.description;
    }
    public T getValue() {
        return this.value;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private Status status = null;
    private String description = "";
    private T value = null;

    @Override public String toString() {
        if (this.value != null) {
            return "Result: {Status: " + this.status + "; Description: '" + this.description + "'; " +
                    "Value (" + this.value.getClass().getSimpleName() + "): " + this.value + ";}";
        } else {
            return "Result: {Status: " + this.status + "; Description: '" + this.description + "'; " +
                    "Value: " + this.value + ";}";
        }

    }
}
