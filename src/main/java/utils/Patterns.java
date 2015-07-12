package utils;

import java.util.regex.Pattern;

/**
 * Patterns for proper parsing and checking
 */

public interface Patterns {

    public static Pattern patternDouble = Pattern.compile("^\\d+([.]\\d+)?"); // valid example: 123456.78910
    public static Pattern patternEmail = Pattern.compile("^[A-Za-z|0-9]+?([_|.|-][A-Za-z|0-9]+?)*?[@][a-z|0-9]+?([.|-][A-Za-z|0-9]+?)*?[.]([A-Za-z]{2,}?)$");
    public static Pattern patternName = Pattern.compile("^([A-Za-zА-Яа-я]+?([-|\\s][A-Za-zА-Яа-я]+?)*?)*?$");
    public static Pattern patternPhone = Pattern.compile("^[+]?([0-9]+?([-|\\s][0-9]+?)*?){8,15}$");

}
