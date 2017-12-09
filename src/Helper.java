/**
 * Helper class provides all the arithmetic and logical operations
 * that are used by ALU
 */
public final class Helper {

    private static int _wordsize;

    private Helper () {
        _wordsize = 16;
    }

    /**
     * ADD operation, add two numbers in binary
     * @param a - first operand
     * @param b - second operand
     * @return sum - String
     */
    public static String  ADD (String a, String b) {
        String output = "";

        a = new StringBuilder(a).reverse().toString();
        b = new StringBuilder(b).reverse().toString();

        int c = 0;
        char tmp_a, tmp_b;

        // use carry and sum up two numbers
        for (int i = 0; i < a.length(); i++) {
            tmp_a = a.charAt(i);
            tmp_b = b.charAt(i);

            if (tmp_a == '0' && tmp_b == '0' && c == 0) { output += 0; c = 0; continue; }
            if (tmp_a == '0' && tmp_b == '0' && c == 1) { output += 1; c = 0; continue; }

            if (tmp_a == '0' && tmp_b == '1' && c == 0) { output += 1; c = 0; continue; }
            if (tmp_a == '0' && tmp_b == '1' && c == 1) { output += 0; c = 1; continue; }

            if (tmp_a == '1' && tmp_b == '0' && c == 0) { output += 1; c = 0; continue; }
            if (tmp_a == '1' && tmp_b == '0' && c == 1) { output += 0; c = 1; continue; }
            if (tmp_a == '1' && tmp_b == '1' && c == 0) { output += 0; c = 1; continue; }
            if (tmp_a == '1' && tmp_b == '1' && c == 1) { output += 1; c = 1; continue; }

        }

        return new StringBuilder(output).reverse().toString();
    }

    /**
     * SUB operation, finds difference between two numbers in binary
     * using 2's complement
     * @param a - first operand
     * @param b - second operand
     * @return difference - String
     */
    public static String  SUB (String a, String b) {
        String output;

        // calculate with 2's complement
        b = NEGATE(b);
        b = ADD(b, ONE());

        output = ADD(a, b);

        return output;
    }

    /**
     * AND operation, performs AND between two numbers in binary
     * @param a - first operand
     * @param b - second operand
     * @return and - String
     */
    public static String  AND (String a, String b) {
        String output = "";

        char tmp_a, tmp_b;

        for (int i = 0; i < _wordsize; i++) {
            tmp_a = a.charAt(i);
            tmp_b = b.charAt(i);

            if (tmp_a == '1' && tmp_b == '1') output += '1';
            else output += '0';
        }

        return output;
    }

    /**
     * OR operation, performs OR between two numbers in binary
     * @param a - first operand
     * @param b - second operand
     * @return or - String
     */
    public static String  OR (String a, String b) {
        String output = "";

        char tmp_a, tmp_b;

        for (int i = 0; i < _wordsize; i++) {
            tmp_a = a.charAt(i);
            tmp_b = b.charAt(i);

            if (tmp_a == '1' || tmp_b == '1') output += '1';
            else output += '0';
        }

        return output;
    }

    /**
     * EOR operation, performs Exclusive OR between two numbers in binary
     * @param a - first operand
     * @param b - second operand
     * @return eor - String
     */
    public static String EOR (String a, String b) {
        String output = "";

        char tmp_a, tmp_b;

        for (int i = 0; i < _wordsize; i++) {
            tmp_a = a.charAt(i);
            tmp_b = b.charAt(i);

            if ((tmp_a == '1' && tmp_b == '1') || (tmp_a == '0' && tmp_b == '0')) output += '0';
            else output += '1';
        }

        return output;
    }

    /**
     * LSL operation, performs left shift between two numbers in binary
     * @param a - first operand
     * @param b - second operand
     * @return lsl - String
     */
    public static String LSL (String a, String b) {
        String output = "";

        int size = Integer.parseInt(a, 2);

        for (int i = 0; i < size; i++) {
            output += '0';
        }

        output = b.substring(size, b.length()) + output;

        System.out.println("OUTPUT:\t" + output);

        return output;
    }
    /**
     * LSR operation, performs right shift between two numbers in binary
     * @param a - first operand
     * @param b - second operand
     * @return lsr - String
     */
    public static String LSR (String a, String b) {
        String output = "";

        int size = Integer.parseInt(a, 2);

        for (int i = 0; i < size; i++) {
            output += '0';
        }

        output += b.substring(0, b.length() - size);

        System.out.println("OUTPUT:\t" + output);

        return output;
    }

    /**
     * NEGATE operation negates given number in binary
     * @param a - number in binary
     * @return negation - String
     */
    public static String NEGATE (String a) {
        String output = "";

        for (int i = 0; i < a.length(); i++) {
            output += a.charAt(i) == '0' ? '1' : '0';
        }

        return output;
    }

    /**
     * ONE provides 1 written in binary that is same size as wordsize
     * @return one - String
     */
    public static String ONE () {
        String output = "";

        for (int i = 0; i < _wordsize - 1; i++) {
            output += '0';
        }

        output += '1';

        return output;
    }

    /**
     * ZERO provides 0 written in binary that is same size as wordsize
     * @return zero - String
     */
    public static String ZERO () {
        String output = "";

        for (int i = 0; i < _wordsize; i++) {
            output += '0';
        }

        return output;
    }

    public static void wordsize (int word) {
        _wordsize = word;
    }
}
