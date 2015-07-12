package utils;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
/**
 * This class designed help with semantics
 * Firstly need to set up list of similar meanings inside SemanticSearch class
 * Then use search. When you input some value - firstly search begins from actual names. If in actual names
 * required name not found - semantic search starts using associations. It will loop for every association and
 * check in names for it presence.
 */
public class SearchFeature extends SemanticSearch{

    boolean  singleItemReturn = false;
    boolean  caseSensitivity = false;
    boolean  searchSemanticsOnly = true; // don't try search if user input is present in names directly
    boolean  onlyUniqueElements = false;

    ArrayList<String> names = new ArrayList<String>();

    public void     setNames                (ArrayList<String> newNames) {
        this.names = newNames;
    }
    public void     setSingleItemReturn     (boolean status)    {
        this.singleItemReturn = status;
    }
    public void     setSearchSemanticsOnly  (boolean status)    {
        this.searchSemanticsOnly = status;
    }
    public void     setCaseSensitivity      (boolean sensitivity) {
        this.caseSensitivity = sensitivity;
    }
    public void     setOnlyUniqueElements   (boolean status) {
        this.onlyUniqueElements = status;
    }
    public void     setUpDefaultSemantics   () {
        addSemantics("registration/sign up/register/registration form");
        addSemantics("login/sign in");
        addSemantics("button/submit");
    }
    public void     setUpDefaultNames       () {
        ArrayList<String> names = new ArrayList<String>(6);
        names.add("sign in"); names.add("button"); names.add("registration"); names.add("registration"); names.add("registration form");  names.add("button");
        setNames(names);
    }
    public ArrayList<String> getNames       () {return names;}

    public ArrayList<String>    getOnlyUniqueElements           (ArrayList<String> list) {
        HashSet<String> hs = new HashSet<String>();
        hs.addAll(list);
        list.clear();
        list.addAll(hs);
        return list;
    }
    public ArrayList<String>    getStringsMatchesExactly        (String userRequest) {
        ArrayList<String> result = new ArrayList<String>(names.size());

        for (String name : names) {
            if (caseSensitivity) {
                if (userRequest.trim().equals(name)) {
                    result.add(name);
                    if (singleItemReturn) {
                        break;
                    }

                }
            } else {
                if (userRequest.trim().toLowerCase().equals(name.toLowerCase())) {
                    result.add(name);
                    if (singleItemReturn) {
                        break;
                    }

                }
            }

        }
        if (onlyUniqueElements) {
            result = getOnlyUniqueElements(result);
        }
        if (result.size() == 0) {
            System.out.println("System message: No exact results found!");
        } //else {
            //System.out.println("System message: Found " + result.length + " results that matches exactly: ");
        //}

        return result;
    }
    public ArrayList<String>    getStringsMatchesPartially      (String userRequest) {
        ArrayList<String> result = new ArrayList<String>(names.size());

        for (String name : names) {
            if (caseSensitivity) {
                if (name.contains(userRequest.trim())) {
                    result.add(name);
                    if (singleItemReturn) {
                        break;
                    }

                }
            } else {
                if (name.toLowerCase().contains(userRequest.trim().toLowerCase())) {
                    result.add(name);
                    if (singleItemReturn) {
                        break;
                    }

                }
            }

        }
        if (onlyUniqueElements) {
            result = getOnlyUniqueElements(result);
        }
        if (result.size() == 0) {
            System.out.println("System message: No partially matches results found!");
        } //else {
        //System.out.println("System message: Found " + result.length + " results that matches partially: ");
        //}

        return result;
    }
    public ArrayList<String>    getGuessMatches                 (String userRequest) {
        ArrayList<String> result = new ArrayList<String>(names.size());
        userRequest = userRequest.trim().toLowerCase();
        int casesCount = userRequest.length();
        String prefix, suffix;

        for (String name : names) {
            int nextCharIndex = 1;
            if (caseSensitivity) {
                for (int i = 0; i < casesCount; i++) {
                    if (i == 0) { prefix = ""; }
                    else {prefix = userRequest.substring(0, i); }
                    suffix = userRequest.substring(nextCharIndex);
                    if (name.matches(".*?" + prefix + ".{0,2}" + suffix + ".*?")) {
                        result.add(name);
                        break;
                    } else {nextCharIndex++;}
                }
            } else {
                for (int i = 0; i < casesCount; i++) {
                    if (i == 0) { prefix = ""; }
                    else {prefix = userRequest.substring(0, i); }
                    suffix = userRequest.substring(nextCharIndex);
                    if (name.toLowerCase().matches(".*?" + prefix.toLowerCase() + ".{0,2}" + suffix.toLowerCase() + ".*?")) {
                        result.add(name);
                        break;
                    } else {nextCharIndex++;}
                }
            }
        }
        if (onlyUniqueElements) {
            result = getOnlyUniqueElements(result);
        }
        if (result.size() == 0) {
            System.out.println("System message: No guesses!");
        } else {
            if (onlyUniqueElements) {
                System.out.println("System message: Found " + result.size() + " unique guesses: ");
            } else {
                System.out.println("System message: Found " + result.size() + " guesses: ");
            }

        }

        return result;
    }
    public boolean              isExactStringPresentAtAllNames  (String userRequest) {
        boolean result = false;
        for (String name : names) {
            if (caseSensitivity) {
                if (name.equals(userRequest)) {
                    result = true;
                    break;
                }
            } else {
                if (name.toLowerCase().equals(userRequest.toLowerCase())) {
                    result = true;
                    break;
                }
            }

        }
        return result;
    }
    public boolean              isStringsMatchesPartially       (String userRequest) {
        boolean result = false;
        for (String name : names) {
            if (caseSensitivity) {
                if (name.contains(userRequest)) {
                    result = true;
                    break;
                }
            } else {
                if (name.toLowerCase().contains(userRequest.toLowerCase())) {
                    result = true;
                    break;
                }
            }

        }
        return result;
    }
    public boolean              isGuessMatches                  (String userRequest) {
        boolean result = false;
        userRequest = userRequest.trim().toLowerCase();
        int casesCount = userRequest.length();
        int nextCharIndex = 1;

        String prefix, suffix;

        for (int i = 0; i < casesCount; i++) {
            if (i == 0) { prefix = ""; } else { prefix = userRequest.substring(0, i); }
            suffix = userRequest.substring(nextCharIndex);
            nextCharIndex++;

            for (String name : names) {
                if (caseSensitivity) {
                    if (name.matches(".*?" + prefix + ".{0,2}" + suffix + ".*?")) {
                        result = true;
                        break;
                    }
                } else {
                    if (name.toLowerCase().matches(".*?" + prefix.toLowerCase() + ".{0,2}" + suffix.toLowerCase() + ".*?")) {
                        result = true;
                        break;
                    }
                }

            }
        }

        return result;
    }
    public void                 printStringList                 (ArrayList<String> arr) {
        for (String anArr : arr) {
            System.out.println(anArr);
        }
    }
    public String               findMostPopularStringInList     (ArrayList<String> list){

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

            if (mostRepeated != null) {
                if (mostRepeated.getKey() != null) {
                    return mostRepeated.getKey();
                } else {
                    System.out.println("Cannot find most popular value at the List. Maybe all strings are empty");
                    return "";
                }
            } else {return "";}

        } catch (NullPointerException e) {
            return "";
        }

    }
    public String               searchSemantics                 (String userInput, ArrayList<String> actualNames) {

        //SearchFeature search = new SearchFeature();
        setNames(actualNames);
        setUpDefaultSemantics();
        String result = "";
        boolean semanticsRequired = true;

        if (caseSensitivity) {
            for (String string : actualNames) {
                if (string.contains(userInput)) {
                    result = string;
                    semanticsRequired = false;
                    break;
                }
            }
        } else {
            for (String string : actualNames) {
                if (string.toLowerCase().contains(userInput.toLowerCase())) {
                    result = string;
                    semanticsRequired = false;
                    break;
                }
            }
        }

        if (semanticsRequired) {
            ArrayList<String> listThatMayMeanUserToInput = getListThatContains(userInput);
            if (listThatMayMeanUserToInput.size() > 0) {
                for (String str : listThatMayMeanUserToInput) {
                    if (isExactStringPresentAtAllNames(str)) {
                        result = str;
                        break;
                    } else {
                        result = userInput;
                    }
                }
            } else {
                System.out.println("I cant find list that matches user's input");
            }
        }
        return result;
    }
    public ArrayList<String>    searchSemantics                 (String userInput) {
        ArrayList<String> result = new ArrayList<String>(names.size());
        boolean semanticsRequired = true;

        if (!searchSemanticsOnly) {
            if (caseSensitivity) {
                for (String string : names) {
                    if (string.contains(userInput)) {
                        result.add(string);
                        if (singleItemReturn) {
                            semanticsRequired = false;
                            break;
                        }
                    }
                }
            } else {
                for (String string : names) {
                    if (string.toLowerCase().contains(userInput.toLowerCase())) {
                        result.add(string);
                        if (singleItemReturn) {
                            semanticsRequired = false;
                            break;
                        }
                    }
                }
            }
        }
        if (semanticsRequired) {
            ArrayList<String> listThatMayMeanUserToInput = getListThatContains(userInput);

            if (listThatMayMeanUserToInput.size() > 0) {
                for (String str : listThatMayMeanUserToInput) {
                    for (String name : names) {
                        if (singleItemReturn && result.size() == 1) {
                            break;
                        }
                        if (str.equals(name)) {
                            result.add(str);
                            if (singleItemReturn) {
                                break;
                            }
                        }

                    }
                }
            } else {
                System.out.println("I cant find semantics list list that contains '" + userInput + "'");
            }
        }
        if (onlyUniqueElements) {
            result = getOnlyUniqueElements(result);
        }
        return result;
    }
    public void                 printSearchResult               (String userInput) {
        if (userInput.length() > 1) {
            if (isExactStringPresentAtAllNames(userInput)) {
                System.out.println("Exact matches for input '" +userInput+ "':");
                printStringList(getStringsMatchesExactly(userInput));
                if (!singleItemReturn) {
                    System.out.println("Partial matches for input '" +userInput+ "':");
                    printStringList(getStringsMatchesPartially(userInput));
                    System.out.println("Guess matches for input '" +userInput+ "':");
                    printStringList(getGuessMatches(userInput));
                    System.out.println("Semantic matches for input '" +userInput+ "':");
                    printStringList(searchSemantics(userInput));
                }
            } else if (isStringsMatchesPartially(userInput)) {
                System.out.println("Partial matches for input '" +userInput+ "':");
                printStringList(getStringsMatchesPartially(userInput));
                if (!singleItemReturn) {
                    System.out.println("Guess matches for input '" +userInput+ "':");
                    printStringList(getGuessMatches(userInput));
                    System.out.println("Semantic matches for input '" +userInput+ "':");
                    printStringList(searchSemantics(userInput));
                }
            } else if (isGuessMatches(userInput)) {
                System.out.println("Guess matches for input '" +userInput+ "':");
                printStringList(getGuessMatches(userInput));
                if (!singleItemReturn) {
                    System.out.println("Semantic matches for input '" +userInput+ "':");
                    printStringList(searchSemantics(userInput));
                }
            } else {
                System.out.println("Semantic matches for input '" +userInput+ "':");
                printStringList(searchSemantics(userInput));
            }
        } else {
            System.out.println("Search request too short!");
        }
    }


}