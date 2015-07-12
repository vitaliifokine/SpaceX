package utils;

import java.util.*;

/**
 * This class designed help with semantics
 * List of similar meanings contains lists of synonymous strings that will be used
 * for search in synonyms.
 */

public class SemanticSearch {

    public SemanticSearch() {    }

    protected ArrayList<ArrayList<String>> listOfSimilarMeanings = new ArrayList<ArrayList<String>>();

    public void                         addSemantics      (String string) {
        this.listOfSimilarMeanings.add(createListFromString(string));
    }
    public ArrayList<ArrayList<String>> getListOfSemantics() {
        return listOfSimilarMeanings;
    }

    public ArrayList<String>    createListFromString (String string) {
        String[] words = string.toLowerCase().split("/");
        return new ArrayList<String>(Arrays.asList(words));
    }
    public ArrayList<String>    getListThatContains (String string) {
        ArrayList<String> resultList = new ArrayList<String>();
        if (string.length() > 1) {
            for (ArrayList<String> subList : listOfSimilarMeanings) {
                if (listContainsString(subList, string.toLowerCase())) {
                    resultList = subList;
                    break;
                }
            }
            return resultList;}
        else {return resultList;}

    }
    public boolean              listContainsString(ArrayList<String> list, String requiredString) {
        boolean result = false;
        for (String string : list) {
            if (string.contains(requiredString)) {
                result = true;
                break;
            } else { result = false; }
        }
        return result;
    }
}
