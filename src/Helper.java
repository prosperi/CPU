public final class Helper {

    private static int _wordsize;

    private Helper () {
        _wordsize = 16;
    }

    public static String  ADD (String a, String b) {
        String output = "";

        System.out.println("A:\t" + a);
        System.out.println("B:\t" + b);

        a = new StringBuilder(a).reverse().toString();
        b = new StringBuilder(b).reverse().toString();

        int c = 0;
        char tmp_a, tmp_b;

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

    public static String  SUB (String a, String b) {
        String output;

        b = NEGATE(b);
        b = ADD(b, ONE());

        output = ADD(a, b);

        return output;
    }

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

    public static String NEGATE (String a) {
        String output = "";

        for (int i = 0; i < a.length(); i++) {
            output += a.charAt(i) == '0' ? '1' : '0';
        }

        return output;
    }

    public static String ONE () {
        String output = "";

        for (int i = 0; i < _wordsize - 1; i++) {
            output += '0';
        }

        output += '1';

        return output;
    }

    public static void wordsize (int word) {
        _wordsize = word;
    }
}
