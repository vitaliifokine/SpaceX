package utils;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListProcessor{

    public static boolean isSorted(List<String> list) {
        int index = 0;
        List<String> copy = new ArrayList<String>(list);
        Collections.sort(copy);
        for (String element: list) {
            if (!element.equals(copy.get(index))) {
                return false;
            }
            index++;
        }
        return true;
    }

    public static boolean notSorted(List<String> list) {
        int index = 0;
        List<String> copy = new ArrayList<String>(list);
        Collections.sort(copy);
        for (String element: list) {
            if (!element.equals(copy.get(index))) {
                return true;
            }
            index++;
        }
        return false;
    }

    public static boolean allContains(List<String> list, String text) {

        for (String s : list) {
            if (!s.contains(text)) {
                return false;
            }
        }
        return true;

    }
}
