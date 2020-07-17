package utils;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Utility class which generate four-digit(every digit is unique by every another digit in this number)
 * random number and return it in String format
 */
public class FourDigitGenerator {
    private static final Random random = new Random();

    /**
     * Use HashSet to guarantee uniqueness digits
     * @return number parsed to String
     */
    public static String generate() {
        Set<Integer> digits = new HashSet<>();
        while (digits.size() != 4) {
            digits.add(random.nextInt(9) + 1);
        }
        return digits.stream().map(String::valueOf).collect(Collectors.joining());
    }
}
