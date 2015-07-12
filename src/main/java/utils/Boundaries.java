package utils;

public class Boundaries<T extends Number> {

    public Boundaries() {
        this.max = null;
        this.min = null;
    }

    public Boundaries(T min, T max) {
        this.max = max;
        this.min = min;
    }

    public T getMin() {
        return this.min;
    }
    public T getMax() {
        return this.max;
    }

    public void setMax(T max) {
        this.max = max;
    }
    public void setMin(T min) {
        this.min = min;
    }

    private T min;
    private T max;

    @Override public String toString() {
        return "Boundaries: { MIN(" + this.min.getClass() + "): " + this.min + "; MAX(" + this.max.getClass() + "): " + this.max + "; }";
    }
}
