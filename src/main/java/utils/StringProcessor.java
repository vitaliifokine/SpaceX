package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class designed to help process strings, interpret them, swap letters, etc.
 */

public class StringProcessor {

    public static int currencyTailLength = 4;

    private static boolean isStringMatchesUsualDouble   (String string) {
        //I mean, usual double: 2.123 or 123.2134 (<int>(dot)<int>)
        Pattern patternDouble = Pattern.compile("(^\\d+([.]\\d+)?)?$"); // valid example: 123456.78910
        Matcher matcherDouble = patternDouble.matcher(string); // setting matcher for first type (123456.789)
        return matcherDouble.matches();
    }
    private static boolean isStringMatchesType2Double   (String string) {
        Pattern patternDouble = Pattern.compile("(^\\d+([,]\\d+)?)?$"); // valid example: 123456,78910
        Matcher matcherDouble = patternDouble.matcher(string); // setting matcher for first type (123456,789)
        return matcherDouble.matches();
    }
    private static boolean isStringMatchesType3Double   (String string) {
        Pattern patternDouble = Pattern.compile("^(\\d+([,]\\d+)+[.]\\d+)?$"); // valid example: 11,123,456.78910
        Matcher matcherDouble = patternDouble.matcher(string); // setting matcher for first type (11,123,456.78910)
        return matcherDouble.matches();
    }

    private static String  deleteAllSpaces              (String string){
        return string.replace(" ", "");
    }
    private static String  deleteAllCommas              (String string){
        return string.replace(",", "");
    }
    private static String  replaceAllCommasOnDots       (String string) {
        return string.replace(",", ".");
    }
    private static boolean hasTail                      (String string, int tailLength) {
        String tail;
        Pattern patternCurrencyTail = Pattern.compile("^\\s[A-Z]{3}$"); // valid for " ASK", " RUB", " AED" - it will check our tail coz money might be shown like 123.22 RUB - but it is double.. that contains chars ;)

        if (string.length() > tailLength) {
            tail = string.substring(string.length() - tailLength);
            Matcher matcherTail = patternCurrencyTail.matcher(tail);

            return matcherTail.matches();
        } else {
            return false;
        }
    }
    private static String  cutTail                      (String string, int tailLength) {
        if (string.length() > 0) {
            return string.substring(0, string.length() - tailLength);
        } else {
            return "";
        }
    }

    public static double[]parseArrayToDouble      (String[] array) { // take array of strings
        try {
            double[] newArrayOfDoubles = new double[array.length]; // create new empty array of doubles with length of given array of strings

            for (int i = 0; i < array.length; i++) { // for every element in array:
                if (array[i].length() == 0 || array[i].equals("No accounts") || array[i].equals("Нет счетов")) {
                    array[i] = "0.0";
                }
                if (hasTail(array[i], currencyTailLength)) {
                    array[i] = cutTail(array[i], currencyTailLength);
                }
                array[i] = deleteAllSpaces(array[i]);
                if (isStringMatchesUsualDouble(array[i])) { // e.g. 2.132 or 2323.2134 etc
                    newArrayOfDoubles[i] = Double.parseDouble(array[i]); //parse this element from string to double
                }
                else if (isStringMatchesType2Double(array[i])) { // e.g. 2,123 or 1234,312214
                    array[i] = replaceAllCommasOnDots(array[i]); // replace commas with dot before parsing
                    newArrayOfDoubles[i] = Double.parseDouble(array[i]); //parse this element from string to double
                }
                else if (isStringMatchesType3Double(array[i])) { // e.g. 123,213,000.00
                    array[i] = deleteAllCommas(array[i]); // to make 123,213,000.00 => 123213000.00
                    newArrayOfDoubles[i] = Double.parseDouble(array[i]); //parse this element from string to double
                } else {
                    System.out.println("Method: parseArrayToDouble(): We probably have 'double' that not matches any type: " + array[i]);
                }
            }

            return newArrayOfDoubles; // and return new array of doubles
        } catch (NumberFormatException e) { // if we've got Exception inside "try" block (e.g. trying to parse string.. maybe this method not used properly or something other has been broken)
            System.out.println("ERROR!!! Trying to parse non-numerical type at TableLogic.parseArrayToDouble(). Returning: {0.0}");
            // this implies that the tester will check for logs
            return new double[]{0.00}; // anyway return array of double (one element = 0.00)
        }
    }
    public static double  parseStringToDouble     (String string) {
        if (string.length() == 0 || string.equals("No accounts") || string.equals("Нет счетов")) {
            string = "0.0";
        }
        if (hasTail(string, currencyTailLength)) {
            string = cutTail(string, currencyTailLength);
        }
        string = deleteAllSpaces(string);
        if (isStringMatchesUsualDouble(string)) { // e.g. 2.132 or 2323.2134 etc
            return Double.parseDouble(string); //parse this element from string to double
        }
        else if (isStringMatchesType2Double(string)) { // e.g. 2,123 or 1234,312214
            string = replaceAllCommasOnDots(string); // replace commas with dot before parsing
            return Double.parseDouble(string); //parse this element from string to double
        }
        else if (isStringMatchesType3Double(string)) { // e.g. 123,213,000.00
            string = deleteAllCommas(string); // to make 123,213,000.00 => 123213000.00
            return Double.parseDouble(string); //parse this element from string to double
        } else {
            System.out.println("Method: parseArrayToDouble(): We probably have 'double' that not matches any type: " + string);
            return 0.0;
        }
    }
    public static String  extractNumberFromString(String string) {
        string = deleteAllSpaces(deleteAllCommas(string));
        Pattern numberPattern = Pattern.compile("-?(\\d\\s?)+([.](\\d\\s?)+)?");
        Matcher numberMatcher = numberPattern.matcher(string);
        if (numberMatcher.find()) {
            return deleteAllSpaces(numberMatcher.group());
        } else {
            return "";
        }
    }

    public static String swapRussianLettersToEng (String stringOFRusLetters) {

        char[] arrayOfLetters = stringOFRusLetters.toCharArray();
        char[] renderedLetters = new char[arrayOfLetters.length];
        int index = 0;
        for (int letterCode : arrayOfLetters) {
            switch (letterCode) {

                //lowercase
                case 1081 : renderedLetters[index] = 113;index++;break; //q
                case 1094 : renderedLetters[index] = 119;index++;break; //w
                case 1091 : renderedLetters[index] = 101;index++;break; //e
                case 1082 : renderedLetters[index] = 114;index++;break; //r
                case 1077 : renderedLetters[index] = 116;index++;break; //t
                case 1085 : renderedLetters[index] = 121;index++;break; //y
                case 1075 : renderedLetters[index] = 117;index++;break; //u
                case 1096 : renderedLetters[index] = 105;index++;break; //i
                case 1097 : renderedLetters[index] = 111;index++;break; //o
                case 1079 : renderedLetters[index] = 112;index++;break; //p
                case 1092 : renderedLetters[index] =  97;index++;break; //a
                case 1099 : renderedLetters[index] = 115;index++;break; //s
                case 1074 : renderedLetters[index] = 100;index++;break; //d
                case 1072 : renderedLetters[index] = 102;index++;break; //f
                case 1087 : renderedLetters[index] = 103;index++;break; //g
                case 1088 : renderedLetters[index] = 104;index++;break; //h
                case 1086 : renderedLetters[index] = 106;index++;break; //j
                case 1083 : renderedLetters[index] = 107;index++;break; //k
                case 1076 : renderedLetters[index] = 108;index++;break; //l
                case 1103 : renderedLetters[index] = 122;index++;break; //z
                case 1095 : renderedLetters[index] = 120;index++;break; //x
                case 1089 : renderedLetters[index] =  99;index++;break; //c
                case 1084 : renderedLetters[index] = 118;index++;break; //v
                case 1080 : renderedLetters[index] =  98;index++;break; //b
                case 1090 : renderedLetters[index] = 110;index++;break; //n
                case 1100 : renderedLetters[index] = 109;index++;break; //m
                case 1093 : renderedLetters[index] =  91;index++;break; //[
                case 1098 : renderedLetters[index] =  93;index++;break; //]
                case 1078 : renderedLetters[index] =  59;index++;break; //;
                case 1101 : renderedLetters[index] =  39;index++;break; //'
                case 1073 : renderedLetters[index] =  44;index++;break; //,
                case 1102 : renderedLetters[index] =  46;index++;break; //.
                case 1105 : renderedLetters[index] =  96;index++;break; //`

                //uppercase
                case 1049 : renderedLetters[index] = 81;index++;break; //q
                case 1062 : renderedLetters[index] = 87;index++;break; //w
                case 1059 : renderedLetters[index] = 69;index++;break; //e
                case 1050 : renderedLetters[index] = 82;index++;break; //r
                case 1045 : renderedLetters[index] = 84;index++;break; //t
                case 1053 : renderedLetters[index] = 89;index++;break; //y
                case 1043 : renderedLetters[index] = 85;index++;break; //u
                case 1064 : renderedLetters[index] = 73;index++;break; //i
                case 1065 : renderedLetters[index] = 79;index++;break; //o
                case 1047 : renderedLetters[index] = 80;index++;break; //p
                case 1061 : renderedLetters[index] = 123;index++;break; //{
                case 1066 : renderedLetters[index] = 125;index++;break; //}
                case 1060 : renderedLetters[index] = 65;index++;break; //a
                case 1067 : renderedLetters[index] = 83;index++;break; //s
                case 1042 : renderedLetters[index] = 68;index++;break; //d
                case 1040 : renderedLetters[index] = 70;index++;break; //f
                case 1055 : renderedLetters[index] = 71;index++;break; //g
                case 1056 : renderedLetters[index] = 72;index++;break; //h
                case 1054 : renderedLetters[index] = 74;index++;break; //j
                case 1051 : renderedLetters[index] = 75;index++;break; //k
                case 1044 : renderedLetters[index] = 76;index++;break; //l
                case 1046 : renderedLetters[index] = 58;index++;break; //:
                case 1069 : renderedLetters[index] = 34;index++;break; //"
                case 1071 : renderedLetters[index] = 90;index++;break; //z
                case 1063 : renderedLetters[index] = 88;index++;break; //x
                case 1057 : renderedLetters[index] = 67;index++;break; //c

                case 1052 : renderedLetters[index] = 86;index++;break; //v
                case 1048 : renderedLetters[index] = 66;index++;break; //b
                case 1058 : renderedLetters[index] = 78;index++;break; //n
                case 1068 : renderedLetters[index] = 77;index++;break; //m
                case 1041 : renderedLetters[index] = 60;index++;break; //<
                case 1070 : renderedLetters[index] = 62;index++;break; //>
                case 8470 : renderedLetters[index] = 35;index++;break; //#
                case 1025 : renderedLetters[index] = 126;index++;break; //~

                case 44 : renderedLetters[index] = 63;index++;break; // ?
                case 46 : renderedLetters[index] = 47;index++;break; // /
                case 63 : renderedLetters[index] = 38;index++;break; // &
                case 59 : renderedLetters[index] = 36;index++;break; // $
                case 34 : renderedLetters[index] = 64;index++;break; // @
                case 58 : renderedLetters[index] = 94;index++;break; // ^
                default: renderedLetters[index] = (char)letterCode; index++;break;

            }
        }
        return String.valueOf(renderedLetters);
    }
    public static String swapEnglishLettersToRus (String stringOFEngLetters) {

        char[] arrayOfLetters = stringOFEngLetters.toCharArray();
        char[] renderedLetters = new char[arrayOfLetters.length];
        int index = 0;
        for (int letterCode : arrayOfLetters) {
            switch (letterCode) {

                //lowercase
                case 113 : renderedLetters[index] = 1081;index++;break; //q
                case 119 : renderedLetters[index] = 1094;index++;break; //w
                case 101 : renderedLetters[index] = 1091;index++;break; //e
                case 114 : renderedLetters[index] = 1082;index++;break; //r
                case 116 : renderedLetters[index] = 1077;index++;break; //t
                case 121 : renderedLetters[index] = 1085;index++;break; //y
                case 117 : renderedLetters[index] = 1075;index++;break; //u
                case 105 : renderedLetters[index] = 1096;index++;break; //i
                case 111 : renderedLetters[index] = 1097;index++;break; //o
                case 112 : renderedLetters[index] = 1079;index++;break; //p
                case 97 : renderedLetters[index] = 1092 ;index++;break; //a
                case 115 : renderedLetters[index] = 1099;index++;break; //s
                case 100 : renderedLetters[index] = 1074;index++;break; //d
                case 102 : renderedLetters[index] = 1072;index++;break; //f
                case 103 : renderedLetters[index] = 1087;index++;break; //g
                case 104 : renderedLetters[index] = 1088;index++;break; //h
                case 106 : renderedLetters[index] = 1086;index++;break; //j
                case 107 : renderedLetters[index] = 1083;index++;break; //k
                case 108 : renderedLetters[index] = 1076;index++;break; //l
                case 122 : renderedLetters[index] = 1103;index++;break; //z
                case 120 : renderedLetters[index] = 1095;index++;break; //x
                case 99 : renderedLetters[index] = 1089 ;index++;break; //c
                case 118 : renderedLetters[index] = 1084;index++;break; //v
                case 98 : renderedLetters[index] = 1080 ;index++;break; //b
                case 110 : renderedLetters[index] = 1090;index++;break; //n
                case 109 : renderedLetters[index] = 1100;index++;break; //m
                case 91 : renderedLetters[index] = 1093 ;index++;break; //[
                case 93 : renderedLetters[index] = 1098 ;index++;break; //]
                case 59 : renderedLetters[index] = 1078 ;index++;break; //;
                case 39 : renderedLetters[index] = 1101 ;index++;break; //'
                case 44 : renderedLetters[index] = 1073 ;index++;break; //,
                case 46 : renderedLetters[index] = 1102 ;index++;break; //.
                case 96 : renderedLetters[index] = 1105 ;index++;break; //`

                //uppercase
                case 81 : renderedLetters[index] = 1049;index++;break; //q
                case 87 : renderedLetters[index] = 1062;index++;break; //w
                case 69 : renderedLetters[index] = 1059;index++;break; //e
                case 82 : renderedLetters[index] = 1050;index++;break; //r
                case 84 : renderedLetters[index] = 1045;index++;break; //t
                case 89 : renderedLetters[index] = 1053;index++;break; //y
                case 85 : renderedLetters[index] = 1043;index++;break; //u
                case 73 : renderedLetters[index] = 1064;index++;break; //i
                case 79 : renderedLetters[index] = 1065;index++;break; //o
                case 80 : renderedLetters[index] = 1047;index++;break; //p
                case 123 : renderedLetters[index] = 1061;index++;break; //{
                case 125 : renderedLetters[index] = 1066;index++;break; //}
                case 65 : renderedLetters[index] = 1060;index++;break; //a
                case 83 : renderedLetters[index] = 1067;index++;break; //s
                case 68 : renderedLetters[index] = 1042;index++;break; //d
                case 70 : renderedLetters[index] = 1040;index++;break; //f
                case 71 : renderedLetters[index] = 1055;index++;break; //g
                case 72 : renderedLetters[index] = 1056;index++;break; //h
                case 74 : renderedLetters[index] = 1054;index++;break; //j
                case 75 : renderedLetters[index] = 1051;index++;break; //k
                case 76 : renderedLetters[index] = 1044;index++;break; //l
                case 58 : renderedLetters[index] = 1046;index++;break; //:
                case 34 : renderedLetters[index] = 1069;index++;break; //"
                case 90 : renderedLetters[index] = 1071;index++;break; //z
                case 88 : renderedLetters[index] = 1063;index++;break; //x
                case 67 : renderedLetters[index] = 1057;index++;break; //c
                case 86 : renderedLetters[index] = 1052;index++;break; //v
                case 66 : renderedLetters[index] = 1048;index++;break; //b
                case 78 : renderedLetters[index] = 1058;index++;break; //n
                case 77 : renderedLetters[index] = 1068;index++;break; //m
                case 60 : renderedLetters[index] = 1041;index++;break; //<
                case 62 : renderedLetters[index] = 1070;index++;break; //>
                case 35 : renderedLetters[index] = 8470;index++;break; //#
                case 126 : renderedLetters[index] = 1025;index++;break; //~

                case 63 : renderedLetters[index] = 44;index++;break; // ?
                case 47 : renderedLetters[index] = 46;index++;break; // /
                case 38 : renderedLetters[index] = 63;index++;break; // &
                case 36 : renderedLetters[index] = 59;index++;break; // $
                case 64 : renderedLetters[index] = 34;index++;break; // @
                case 94 : renderedLetters[index] = 58;index++;break; // ^

                default: renderedLetters[index] = (char)letterCode; index++;break;

            }
        }
        return String.valueOf(renderedLetters);
    }
    public static int getCharCode(String oneSymbol) {
        if (! (oneSymbol.length() == 1)) {
            System.err.println("Attention! getCharCode(...) method gets only first string's (if you send String as an input) character code");
        }
        return (int) oneSymbol.charAt(0);
    }
    public static int getCharCode(char c) {
        return (int) c;
    }

    public static String convertCustomMoneyAmountToDefaultDouble(String customDouble) {
        if (hasTail(customDouble, currencyTailLength)) {
            customDouble = cutTail(customDouble, currencyTailLength);
        }
        customDouble = deleteAllSpaces(customDouble);
        if (isStringMatchesUsualDouble(customDouble)) { // e.g. 2.132 or 2323.2134 etc
            return customDouble; //parse this element from string to double
        }
        else if (isStringMatchesType2Double(customDouble)) { // e.g. 2,123 or 1234,312214
            customDouble = replaceAllCommasOnDots(customDouble); // replace commas with dot before parsing
            return customDouble; //parse this element from string to double
        }
        else if (isStringMatchesType3Double(customDouble)) { // e.g. 123,213,000.00
            customDouble = deleteAllCommas(customDouble); // to make 123,213,000.00 => 123213000.00
            return customDouble; //parse this element from string to double
        } else {
            System.out.println("Method: parseArrayToDouble(): We probably have 'double' that not matches any type: " + customDouble);
            return "";
        }
    }

    public static String firstToUpperCase(String sourceString) {
        return sourceString.substring(0, 1).toUpperCase() + sourceString.substring(1);
    }

}
