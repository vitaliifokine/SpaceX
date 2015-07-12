package utils;

import java.util.ArrayList;
import java.util.List;
/**
 * This class designed to provide random values:
 * random strings
 * random numbers
 * random chars
 * random booleans
 * with required settings
 */
public class RandomGenerator {

    static boolean caseSensitivity = false;

    public void     setCaseSensitivity       (boolean sensitivity) {
        this.caseSensitivity = sensitivity;
    }
    public void     switchCaseSensitivity    () {
        this.caseSensitivity = !caseSensitivity;
    }
    public boolean  getCaseSensitivityStatus () {
        return caseSensitivity;
    }

    public static String   shuffleString               (String input){
        List<Character> characters = new ArrayList<Character>();
        for(char c: input.toCharArray() ){
            characters.add(c);
        }
        StringBuilder output = new StringBuilder(input.length());
        while(characters.size() != 0){
            int randPicker = (int)(Math.random()*characters.size());
            output.append(characters.remove(randPicker));
        }
        return output.toString();
    }
    public static boolean  getRandomTrueOrFalse        () {
        double random = Math.random();
        if (random > 0.5) {
            return true;
        } else {
            return false;
        }
    }
    public static boolean  getTrueProbably             (Probability probability) {
        double random = Math.random();
        if (random < probability.getDoubleValue()) {
            return true;
        } else {
            return false;
        }
    }
    public static char     getRandomLetter             () {
        if (caseSensitivity) {
            return getRandomLowercaseLetter();
        } else {
            boolean nowWePickLowerCaseCharacter = getRandomTrueOrFalse();
            if (nowWePickLowerCaseCharacter) {
                return getRandomLowercaseLetter();
            } else {
                return getRandomUppercaseLetter();
            }
        }
    }
    public static int      getRandomIntInRange         (int min, int max) {
        int random = min + (int) (Math.random() * ((max - min) + 1));
        return random;
    }
    public static double   getRandomDoubleInRange      (double min, double max) {
        double random = min + (Math.random() * ((max - min)));
        return random;
    }
    public static char     getRandomSymbol             () {
        return (char) getRandomIntInRange(32, 126);
    }
    public static char     getRandomSymbol             (char firstChar, char lastChar) {

        return (char) getRandomIntInRange(firstChar, lastChar);
    }
    public static char     getRandomUppercaseLetter    () {
        return (char) getRandomIntInRange(65, 90);
    }
    public static char     getRandomLowercaseLetter    () {
        return (char) getRandomIntInRange(97, 122);
    }
    public static String   getStringOfLetters (int length) {   //this method generate string in numbers
        if (length < 1 || length > 100000) {
            System.err.println("Minimum length of required string to generate should be 1 and max 100000");
            return "";
        } else {
            StringBuilder builder = new StringBuilder(length);
            for (int i = 0; i < length; i++) {
                builder.append(getRandomLowercaseLetter());
            }
            return builder.toString();
        }

    }
    public static String   getStringOfSpacedLetters (int length, Probability spacingProbability) {
        if (length < 1 || length > 100000) {
            System.err.println("Minimum length of required string to generate should be 1 and max 100000");
            return "";
        } else {
            StringBuilder builder = new StringBuilder(length);
            for (int i = 0; i < length - 1; i++) {
                builder.append(getRandomLowercaseLetter());
                if (getTrueProbably(spacingProbability)) {
                    builder.append(" ");
                    i++;
                }

            }
            builder.append(getRandomLowercaseLetter());
            return builder.toString();
        }

    }
    public static String   getStringWithSortedUniqueLetters    (int length) {
        StringBuilder output = new StringBuilder(length);
        int baseCharASCII = 65;
        boolean caseNonSensitive = true;
        char currentChar;
        if (length > 24) {
            length = 24;
        }
        if (length < 1) {
            length = 1;
        }

        for (int i = 0; i < length; i++) {
            if (caseNonSensitive) {
                boolean generateLowerCase = getRandomTrueOrFalse();
                if (generateLowerCase) {
                    currentChar = (char) baseCharASCII;
                    baseCharASCII++;
                } else {
                    currentChar = (char) (baseCharASCII + 32);
                    baseCharASCII++;
                }

            }  else {
                currentChar = (char) baseCharASCII;
                baseCharASCII++;
            }
            output.append(currentChar);
        }
        return output.toString();
    }
    public static String   getStringWithSortedUniqueLetters    (char firstChar, int length) {
        int minLength = 1;
        int defaultCharCode = 65; // 'A'
        boolean charIsUpperCase;

        int lastCharASCII;
        int givenChar = (int) firstChar;

        //setting up first char ASCII value - we need chars
        if (givenChar >= 65 && givenChar <= 90) {
            lastCharASCII = 90;
            charIsUpperCase = true;
        } else if (givenChar >= 97 && givenChar <= 122) {
            lastCharASCII = 122;
            charIsUpperCase = false;
        } else {
            lastCharASCII = defaultCharCode;
            charIsUpperCase = true;
        }

        char currentChar;
        int givenCharASCII = givenChar;
        int availableLength = 1 + lastCharASCII - givenCharASCII;

        if (length > availableLength) {
            length = availableLength;
        }
        if (length < minLength) {
            length = minLength;
        }

        StringBuilder output = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            if (!caseSensitivity) {
                boolean generateAnotherCase = getRandomTrueOrFalse();
                if (generateAnotherCase) {
                    if (charIsUpperCase) {
                        currentChar = (char) (givenCharASCII + 32); // + 32
                        givenCharASCII++;
                    } else {
                        currentChar = (char) (givenCharASCII - 32);
                        givenCharASCII++;
                    }
                } else {
                    currentChar = (char) givenCharASCII;
                    givenCharASCII++;
                }

            }  else {
                currentChar = (char) givenCharASCII;
                givenCharASCII++;
            }
            output.append(currentChar);
        }
        return output.toString();
    }
    public static String   getStringWithSortedUniqueLetters    () {
        int length = getRandomIntInRange(4, 24);
        StringBuilder output = new StringBuilder(length);
        int baseCharASCII = 65;
        char currentChar;
        if (length > 24) {
            length = 24;
        }
        if (length < 1) {
            length = 1;
        }

        for (int i = 0; i < length; i++) {
            if (!caseSensitivity) {
                boolean generateLowerCase = getRandomTrueOrFalse();
                if (generateLowerCase) {
                    currentChar = (char) baseCharASCII;
                    baseCharASCII++;
                } else {
                    currentChar = (char) (baseCharASCII + 32);
                    baseCharASCII++;
                }

            }  else {
                currentChar = (char) baseCharASCII;
                baseCharASCII++;
            }
            output.append(currentChar);
        }
        return output.toString();
    }
    public static String   getStringOfRandomLetters            (int length) {
        List<Character> characters = new ArrayList<Character>();
        for(int i = 0; i < length; i++){
            characters.add(getRandomLetter());
        }
        StringBuilder output = new StringBuilder(length);
        while(characters.size() != 0){
            int randPicker = (int)(Math.random()*characters.size());
            output.append(characters.remove(randPicker));
        }
        return output.toString();
    }
    public static String   getSequenceOfRandomSymbols          (int length) {
        List<Character> characters = new ArrayList<Character>();
        for(int i = 0; i < length; i++){
            characters.add(getRandomSymbol());
        }
        StringBuilder output = new StringBuilder(length);
        while(characters.size() != 0){
            int randPicker = (int)(Math.random()*characters.size());
            output.append(characters.remove(randPicker));
        }
        return output.toString();
    }
    public static String   getSequenceOfRandomSymbols          (int length, char firstChar, char lastChar) {
        List<Character> characters = new ArrayList<Character>();
        for(int i = 0; i < length; i++){
            characters.add(getRandomSymbol(firstChar, lastChar));
        }
        StringBuilder output = new StringBuilder(length);
        while(characters.size() != 0){
            int randPicker = (int)(Math.random()*characters.size());
            output.append(characters.remove(randPicker));
        }
        return output.toString();
    }
    public static String   getShuffledUniqueRandomLetters      () {
        return shuffleString(getStringWithSortedUniqueLetters());
    }
    public static String   getShuffledUniqueRandomLetters      (int length) {
        return shuffleString(getStringWithSortedUniqueLetters(length));
    }
    public static String   getRandomPhoneNumber(int sumInt) {
        StringBuffer phoneNumber = new StringBuffer();
        int [] tempnumber = new int[9];
        for (int k = 0; k < sumInt; k++) {
            phoneNumber.append(RandomGenerator.getRandomIntInRange(0, 9));
        }
        return "+380"+phoneNumber;
    }
}

