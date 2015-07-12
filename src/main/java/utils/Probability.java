package utils;

public class Probability {

    public Probability(int percentage) {
        this.probability = (double) percentage/100;
    }
    public Probability(double percentage) {
        this.probability = percentage/100;
    }

    public double getDoubleValue() {
        return this.probability;
    }
    @Override public String toString() {
        return "Probability { value: " + this.probability + "; }";
    }

    private double probability;
}
