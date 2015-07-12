package utils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class designed to help precess arrays it might be helpful to analyze tables at the dashboard
 */
public class ArrayProcessor {

    public ArrayProcessor() {}
    int currencyTailLength = 4; // space + three chars

    protected int     getRandomIntInRange          (int min, int max) {
        return min + (int) (Math.random() * ((max - min) + 1));
    }
    protected String  findMostPopularStringInArray (String[] array){
        List<String> list = Arrays.asList(array);
        Map<String, Integer> stringsCount = new HashMap<String, Integer>();
        for(String string: list)
        {
            if (string.length() > 0) {
                Integer count = stringsCount.get(string);
                if(count == null) count = 0;
                count++;
                stringsCount.put(string,count);
            }
        }
        Map.Entry<String,Integer> mostRepeated = null;
        for(Map.Entry<String, Integer> e: stringsCount.entrySet())
        {
            if(mostRepeated == null || mostRepeated.getValue()<e.getValue())
                mostRepeated = e;
        }

        try {

            if (mostRepeated.getKey() != null) {
                return mostRepeated.getKey();
            } else {
                System.out.println("Cannot find most popular value at the List. Maybe all strings are empty");
                return "";
            }

        } catch (NullPointerException e) {
            return "";
        }

    }
    protected String  cutTail                      (String string, int tailLength) {
        if (string.length() > 0) {
            return string.substring(0, string.length() - tailLength);
        } else {
            return "";
        }
    }
    protected String  deleteAllSpaces              (String string){
        return string.replace(" ", "");
    }
    protected String  deleteAllCommas              (String string){
        return string.replace(",", "");
    }
    protected String  replaceAllCommasOnDots       (String string) {
        return string.replace(",", ".");
    }
    protected boolean hasTail                      (String string, int tailLength) {
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
    protected boolean isStringMatchesInt           (String string) {
        Pattern patternInt = Pattern.compile("^[-]?([1-9]\\d*?)$");
        Matcher matcherInt = patternInt.matcher(string);
        return matcherInt.matches();
    }
    protected boolean isStringMatchesUsualDouble   (String string) {
        //I mean, usual double: 2.123 or 123.2134 (<int>(dot)<int>)
        Pattern patternDouble = Pattern.compile("(^\\d+([.]\\d+)?)?$"); // valid example: 123456.78910
        Matcher matcherDouble = patternDouble.matcher(string); // setting matcher for first type (123456.789)
        return matcherDouble.matches();
    }
    protected boolean isStringMatchesType2Double   (String string) {
        Pattern patternDouble = Pattern.compile("(^\\d+([,]\\d+)?)?$"); // valid example: 123456,78910
        Matcher matcherDouble = patternDouble.matcher(string); // setting matcher for first type (123456,789)
        return matcherDouble.matches();
    }
    protected boolean isStringMatchesType3Double   (String string) {
        Pattern patternDouble = Pattern.compile("^(\\d+([,]\\d+)+[.]\\d+)?$"); // valid example: 11,123,456.78910
        Matcher matcherDouble = patternDouble.matcher(string); // setting matcher for first type (11,123,456.78910)
        return matcherDouble.matches();
    }
    protected boolean isStringMatchesDoublesRegExp (String string) {
        return  isStringMatchesUsualDouble(string) ||
                isStringMatchesType2Double(string) ||
                isStringMatchesType3Double(string);
    }

    protected double   getMaxValueFromArray     (double[] array) {
        double maxValue = array[0];

        for (double element : array) {
            if (element > maxValue) {
                maxValue = element;
            }
        }
        return maxValue;
    }
    protected double   getMinValueFromArray     (double[] array) {
        double minValue = array[0];

        for (double element : array) {
            if (element < minValue) {
                minValue = element;
            }
        }
        return minValue;
    }
    protected double   getMediumValueFromArray  (double[] array) {
        Arrays.sort(array);
        return array[array.length / 2];
    }
    protected boolean  isArrayElementsEquals    (double[] array, String value) {
        double doubleValue = Double.parseDouble(value);
        boolean isEqual = ( array[0] == doubleValue ); // setting up trigger
        for (int i = 1; i < array.length; i++) { // for every element in table (begin from second element and comparing with previous)
            isEqual = isEqual && (array[i] == array[i-1]); // compare "i" element with previous. If one of array's permanent will be not equal to other - is equal becomes false and stay at this condition until loop ends;
        }
        return isEqual;
    }
    protected boolean  isArrayElementsIdentical (String[] array) {

        boolean isEqual = true; // setting up trigger
        for (int i = 1; i < array.length; i++) { // for every element in table (begin from second element and comparing with previous)
            isEqual = isEqual && array[i].equals(array[i-1]); // compare "i" element with previous. If one of array's permanent will be not equal to other - is equal becomes false and stay at this condition until loop ends;
        }
        return isEqual;
    }
    protected boolean  isParsableToDouble       (String[] array) {

        if (array.length > 0) {
            boolean isParsableToDouble = true;
            int tailLength = 4;

            for (int i = 0; i < array.length; i++) { // iterating through permanent in array (we need to check whole array to be confident - every single element can be parsable to double)
                if (array[i].equals("No accounts") || array[i].equals("Нет счетов")) { // if we have "No accounts" or "Нет счетов" in table cell - probably we're checking cell with accounts there are should be doubles
                    break; // exit from for loop (isParsableToDouble ==> true); further processing will figure out cells with that phrases and replace them with {0.0} and show message to system log...
                }
                if (hasTail(array[i], tailLength)) { // hasTail method contains regExp of probable tail
                    array[i] = cutTail(array[i], tailLength); // if we have a tail that response to regExp - we cut it
                }

                array[i] = deleteAllSpaces(array[i]); // deleting all spaces inside current string

                if (!isStringMatchesDoublesRegExp(array[i])) { // if we found one element that can't be parsed to double
                    isParsableToDouble = false; // that means: all array is not parsable to double
                    break; // break the loop
                } else {
                    isParsableToDouble = true; // if element is not a string keep checking until we reach the end of array (check whole column to define if it is parsable)
                }
            }
            //System.out.println("Array is parsable to double: " + isParsableToDouble);
            return isParsableToDouble; // can be true or false depends on decision from "for" loop described above
        } else {
            System.out.println("Given array is empty");
            return false;
        }

    }
    protected double[] parseArrayToDouble       (String[] array) { // take array of strings
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
    protected String[] stringArrayToLowerCase   (String[] array) {
        String[] lowerCaseArray = new String[array.length]; // create new array
        for (int i = 0; i < array.length; i++) { // every element if array
            lowerCaseArray[i] = array[i].toLowerCase(); // set to lowerCase and write to new array
        }
        return lowerCaseArray; // return array of lowerCased strings
    }
    protected double[] reverseArray             (double[] array) {

        double[] reversedArray = new double[array.length]; // create new array

        for (int i = array.length - 1; i >= 0; i--) { // for every element: begins from the last to first element of given array
            reversedArray[array.length - i - 1] = array[i]; // keep writing permanent from first to last
            // e.g. length = 10; (index of given array's element) i = 9;
            // at the first iteration (i = 9) we have:  reversedArray[10 - 9 - 1] = array[9] => reversedArray[0] = array[9]
            // ar the second iteration (i-- => i = 8) we have: reversedArray[10 - 8 - 1] = array[8] => reversedArray[1] = array[8]
            // and so on...
        }
        return reversedArray;
    }
    protected String[] reverseArray             (String[] array) {

        String[] reversedArray = new String[array.length]; // create new array

        for (int i = array.length - 1; i >= 0; i--) { // for every element: begins from the last to first element of given array
            reversedArray[array.length - i - 1] = array[i]; // keep writing permanent from first to last
            // e.g. length = 10; (index of given array's element) i = 9;
            // at the first iteration (i = 9) we have:  reversedArray[10 - 9 - 1] = array[9] => reversedArray[0] = array[9]
            // ar the second iteration (i-- => i = 8) we have: reversedArray[10 - 8 - 1] = array[8] => reversedArray[1] = array[8]
            // and so on...
        }
        return reversedArray;
    }
    protected void     printArray               (double[] array) { // accepts doubles
        for (double anArray : array) { // for every element at the table
            System.out.print(anArray + "; "); // print it and add ";" to the tail
        }
        System.out.println(); // after end of loop add an indention
    }
    protected void     printArray               (String[] array) { // accepts Strings
        for (String anArray : array) // for every element at the table
            System.out.print(anArray + "; "); // print it and add ";" to the tail
        System.out.println(); // after end of loop add an indention
    }
    protected boolean  arrayOfStringsEqualsSequence  (String[] array, String sequence) {
        if (array.length > 0) { // if array length is more than 0:

            boolean equals = true; // setting up the trigger

            for (String anArray : array) { // moving through array and getting strings from it
                equals = equals && anArray.equals(sequence); // [not case-sensitive] if one of strings is not matching sequence - than this loop will return false (e.g. true && true && true && false => false)
            }
            if (equals) { // if all strings of given array of strings matches given sequence
                return true;
            } else { // if one or more of strings doesn't match then
                System.out.println("Given char sequence: " + sequence);
                System.out.println("String(s) don't matches the sequence: ");
                printArray(array);
                return false;
            }
        } else { // if array length is not more than 0; (empty array)
            System.out.println("Given array is empty");
            return false;
        }
    }
    protected boolean  arrayOfStringContainsSequence (String[] array, String sequence) {
        if (array.length > 0) { // if array length is more than 0:
            sequence = sequence.toLowerCase(); // transfers given sequence to lower case to make comparing process not case-sensitive
            boolean contains = true; // setting up the trigger

            for (String anArray : array) { // moving through array and getting strings from it
                contains = contains && anArray.toLowerCase().contains(sequence); // [not case-sensitive] if one of strings is not matching sequence - than this loop will return false (e.g. true && true && true && false => false)
            }
            if (contains) { // if all strings of given array of strings matches given sequence
                return true;
            } else { // if one or more of strings doesn't match then
                System.out.println("Given char sequence: " + sequence);
                System.out.println("String(s) don't matches the sequence: ");
                printArray(array);
                return false;
            }
        } else { // if array length is not more than 0; (empty array)
            System.out.println("Given array is empty");
            return false;
        }

    }
    protected boolean  arrayValuesAreGreaterThan     (double[] array, String valueStr)    { // accepts array of doubles and value (double too)
        double value = Double.parseDouble(valueStr);
        if (array.length > 0) {
            boolean isGreater = true; // setting up the trigger
            for (double anArray : array) { // for every element in table
                isGreater = isGreater && (anArray > value); // check, whether this element meets the condition
            }
            if (isGreater) { // if all permanent meets the condition...
                return true;
            } else { // if one or more permanent doesn't met the condition...
                System.out.println("Array value(s) is not greater than " + value + ":");
                printArray(array);
                return false;
            }
        } else { // if array is empty...
            System.out.println("Given array is empty");
            return false;
        }
    }
    protected boolean  arrayValuesAreLesserThan      (double[] array, String valueStr)    {
        double value = Double.parseDouble(valueStr);
        if (array.length > 0) { // if array is not empty
            boolean isLesser = true; // set up the trigger
            for (double anArray : array) { // for every element in array
                isLesser = isLesser && (anArray < value); // check, whether this element meets the condition
            }
            if (isLesser) { // if all permanent meets the condition...
                return true;
            } else { // if one or more permanent doesn't met the condition...
                System.out.println("Array value(s) is not lesser than " + value + ":");
                printArray(array);
                return false;
            }
        } else { // if array is empty
            System.out.println("Given array is empty");
            return false;
        }
    }
    protected boolean  arrayValuesAreGreaterOrEqual  (double[] array, String valueStr)    {
        double value = Double.parseDouble(valueStr);
        if (array.length > 0) { // if array is not empty
            boolean isGreaterOrEqual = true; // set up the trigger
            for (double anArray : array) { // for every element in array
                isGreaterOrEqual = isGreaterOrEqual && (anArray >= value); // check, whether this element meets the condition
            }
            if (isGreaterOrEqual) { // if all permanent meets the condition...
                return true;
            } else { // if one or more permanent doesn't met the condition...
                System.out.println("Array value(s) is not greater than " + value + ":");
                printArray(array);
                return false;
            }
        } else { // if array is empty
            System.out.println("Given array is empty");
            return false;
        }
    }
    protected boolean  arrayValuesAreLesserOrEqual   (double[] array, String valueStr)    {
        double value = Double.parseDouble(valueStr);
        if (array.length > 0) { // if array is not empty
            boolean isLesserOrEqual = true; // set up the trigger
            for (double anArray : array) { // for every element in array
                isLesserOrEqual = isLesserOrEqual && (anArray <= value); // check, whether this element meets the condition
            }
            if (isLesserOrEqual) { // if all permanent meets the condition...
                return true;
            } else { // if one or more permanent doesn't met the condition...
                System.out.println("Array value(s) is not lesser than " + value + ":");
                printArray(array);
                return false;
            }
        } else { // if array is empty
            System.out.println("Given array is empty");
            return false;
        }
    }
    protected boolean  arrayIsSorted      (double[] array) {
        if (array.length > 0) { // if array not empty
            double[] inputArray = array.clone(); // duplicate source array
            Arrays.sort(array); // sort input array

            double[] reversedSortedArray = reverseArray(array); // reverse sorted array array (reversed sorted array is sorted too)
            boolean isSorted = Arrays.equals(inputArray, array) || Arrays.equals(inputArray, reversedSortedArray); // array is sorted if it matches it's sorted copy or sorted-reversed copy;
            if (isSorted) {
                return true; // if source array is sorted - return that it is TRUE
            } else {
                System.out.println("Given: ");
                printArray(inputArray); // print out given array
                System.out.println("Not matches sorted: ");
                printArray(array); // and expected array (given array should match one of them)
                printArray(reversedSortedArray);
                return false; // and return FALSE (source array don't looks like sorted)
            }
        } else { // if array is empty
            System.out.println("Given array is empty");
            return false; // and return false
        }
    }
    protected boolean  arrayIsSorted      (String[] array) {
        if (array.length > 0) { // if array not empty
            array = stringArrayToLowerCase(array); // lower-casing given array
            String[] inputArray = array.clone(); // duplicate source array (lower-cased)
            Arrays.sort(array); // sort input array
            String[] sortedArray = array; // assign sorted array to sortedArray
            String[] reversedSortedArray = reverseArray(sortedArray); // reverse sorted array array (reversed sorted array is sorted too)
            boolean isSorted = Arrays.equals(inputArray, sortedArray) || Arrays.equals(inputArray, reversedSortedArray); // array is sorted if it matches it's sorted copy or sorted-reversed copy;
            if (isSorted) {
                return true; // if source array is actually sorted - return that it is TRUE
            } else {
                System.out.println("Given: ");
                printArray(inputArray); // print out given array
                System.out.println("Not matches sorted: ");
                printArray(sortedArray); // and expected array (given array should match one of them)
                printArray(reversedSortedArray);
                return false; // and return FALSE (source array don't looks like sorted)
            }
        } else { // if array is empty
            System.out.println("Given array is empty");
            return false; // and return false
        }
    }

}
