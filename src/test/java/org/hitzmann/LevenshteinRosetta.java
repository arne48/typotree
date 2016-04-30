package org.hitzmann;

import java.util.List;

/**
 * Created by arne on 30.04.16.
 * Code can be found at https://rosettacode.org/wiki/Levenshtein_distance#Java
 * by the courtesy of the Rosetta Code examples
 */
public class LevenshteinRosetta {
    public static int distance(String a, String b) {
        a = a.toLowerCase();
        b = b.toLowerCase();
        // i == 0
        int[] costs = new int[b.length() + 1];
        for (int j = 0; j < costs.length; j++)
            costs[j] = j;
        for (int i = 1; i <= a.length(); i++) {
            // j == 0; nw = lev(i - 1, j)
            costs[0] = i;
            int nw = i - 1;
            for (int j = 1; j <= b.length(); j++) {
                int cj = Math.min(1 + Math.min(costs[j], costs[j - 1]), a.charAt(i - 1) == b.charAt(j - 1) ? nw : nw + 1);
                nw = costs[j];
                costs[j] = cj;
            }
        }
        return costs[b.length()];
    }

    public static boolean checkWord(String word, List<String> list, int maxDistance) {
        for (String entry : list) {
            if (distance(entry, word) <= maxDistance) {
                return true;
            }
        }
        return false;
    }
}
