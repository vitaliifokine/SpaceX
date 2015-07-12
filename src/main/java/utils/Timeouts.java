package utils;

/**
 * Interface to store constant timeouts for pauses, sleeps etc.
 */

public interface Timeouts {

    public static final int INSTANT = 0;
    public static final int SEC_SHORT = 2;
    public static final int SEC_MEDIUM = 5;
    public static final int SEC_LONG = 10;

    public static final int MILLIS_SHORT = 100;
    public static final int MILLIS_MEDIUM = 500;
    public static final int MILLIS_LONG = 1000;
    public static final int MILLIS_VERY_LONG = 5000;

}
