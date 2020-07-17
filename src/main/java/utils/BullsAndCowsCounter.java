package utils;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility class which count 'bulls' and 'cows'
 */
public class BullsAndCowsCounter {
    /**
     * Compare two strings by symbols. Use HashMap to save current state of comparable symbol.
     * 2 - absolute equal to other problem string(index and value), 1 - equal by value, 0 - none.
     * 2 - bull, 1 - cow. Bull state can't change, cow may be to bull state.
     * @param problem string compare to
     * @param answer string comparable
     * @return Entry pair, key - count of bulls, value - count of cows
     */
    public static Map.Entry<Integer, Integer> count(String problem, String answer) {
        int bulls, cows;
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < answer.length(); ++i) {
            map.put(answer.charAt(i), 0);
        }
        for (int i = 0; i < answer.length(); ++i) {
            int symbolIndexInProblem = problem.indexOf(answer.charAt(i));
            char currChar = answer.charAt(i);
            int currState = map.get(currChar);
            if (symbolIndexInProblem == i)
                map.put(currChar, 2);
            else if (symbolIndexInProblem != -1 && currState != 2)
                map.put(currChar, 1);
        }
        bulls = (int) map.values().stream().filter(x -> x == 2).count();
        cows = (int) map.values().stream().filter(x -> x == 1).count();
        return new AbstractMap.SimpleEntry<>(bulls, cows);
    }
}
