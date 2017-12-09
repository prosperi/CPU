
/***
 * Register class for implementing memory components
 * that hang together thru the bus.
 *
 * @author mestiasz@lafayette.edu  - just added description
 */
public class Register {

    /* Object input fields */
    private int bits[];
    private int wordsize;

    private Bus source;
    private Bus destination;

    /* Primary constructor */
    public Register(int _wordsize) {

        wordsize = _wordsize;

        bits = new int[wordsize];

        for(int index = 0; index < bits.length; index++) {
            bits[index] = 0;
        }
    }

    public void set_source_bus(Bus bus) {
        source = bus;
    }

    public void set_destination_bus(Bus bus) {
        destination = bus;
    }

    /**
     *  load data in register based on the source
     */
    public void load() {

        for(int cnt = 0; cnt < wordsize; cnt++) {
            bits[cnt] = source.bits[cnt];
        }
    }

    /**
     *  load data in register with given source in binary
     */
    public void load (String source) throws Exception{

        if (source.length() != wordsize)
            throw new Exception("Length of provided source does not equal to wordsize");

        for (int i = source.length() - 1; i >= 0; i--) {
            bits[i] = Integer.parseInt(source.charAt(wordsize - i - 1) + "");
        }
    }

    /**
     *  store data in destination
     */
    public void store() {
        for(int cnt = 0; cnt < wordsize; cnt++) {
            destination.bits[cnt] = bits[cnt];
        }
    }

    public void increment() {

        increment(1);
    }

    /**
     * increase data in register with provided number
     * @param times - int
     */
    public void increment(int times) {

        for(int cnt = 0; cnt < times; cnt++) {

            int carry = 1;

            for(int inner_cnt = 0; inner_cnt < wordsize; inner_cnt++) {

                bits[inner_cnt] += carry;

                if (bits[inner_cnt] > 1) {
                    bits[inner_cnt] = 0;
                    carry = 1;
                } else {
                    carry = 0;
                }
            }
        }
    }

    /**
     *  negate the value stored in register, replacing 0s with 1s and vice versa
     */
    public void negate() {

        for(int cnt = 0; cnt < wordsize; cnt++) {

            if (bits[cnt] == 1) {
                bits[cnt] = 0;
            } else {
                bits[cnt] = 1;
            }
        }

        increment();
    }

    /**
     *  get decimal representation of stored value in register
     * @return - int
     */
    public int decimal() {

        int pow_value = 1;
        int value     = 0;

        for(int index = 0; index < bits.length; index++) {

            if (bits[index] == 1) {
                value += pow_value;
            }
            pow_value *= 2;
        }

        return value;
    }

    /**
     *  get decimal representation of stored value in register given lower
     * and upper bounds
     * @param low - lower bound
     * @param high - upper bound
     * @return - int
     */
    public int decimal(int high, int low) throws Exception {

        String result = "";

        if (low > high)
            throw new Exception("Binary range values should have a low " +
                    "value less than or equal to the high");
        int pow_value = 1;
        int value     = 0;

        for(int index = low; index <= high; index++) {

            value     += bits[index] * pow_value;
            pow_value *= 2;
        }

        return value;
    }

    /**
     *  get hex representation of stored value in register
     * @return - String
     */
    public String hex() {

        int pow_value = 1;
        int value     = 0;

        for(int index = 0; index < bits.length; index++) {

            if (bits[index] == 1) {
                value += pow_value;
            }
            pow_value *= 2;
        }

        return String.format("%02X", value);
    }

    /**
     *  get binary representation of stored value in register
     * @return - String
     */
    public String binary() {

        String result = "";

        for(int index = (bits.length - 1); index >= 0; index--) {

            if (bits[index] == 1) {
                result += "1";
            } else {
                result += "0";
            }
        }

        return result;
    }

    /**
     *  get binary representation of stored value in register given lower
     * and upper bounds
     * @param low - lower bound
     * @param high - upper bound
     * @return - String
     */
    public String binary(int high, int low) throws Exception {

        String result = "";

        if (low > high)
            throw new Exception("Binary range values should have a low " +
                    "value less than or equal to the high");

        for(int index = high; index >= low; index--) {

            if (bits[index] == 1) {
                result += "1";
            } else {
                result += "0";
            }
        }

        return result;
    }

    /**
     *  Store value in register, given integer
     * @param value - int
     */
    public void store(int value) {

        for(int index = 0; index < bits.length; index++) {
            bits[index] = value % 2;
            value       = value / 2;
        }
    }

    /**
     *  Store value in register, given binary representation
     * @param value - String
     */
    public void store(String value) {

        int int_value = Integer.parseInt(value, 16);

        for(int index = 0; index < bits.length; index++) {
            bits[index] = int_value % 2;
            int_value   = int_value / 2;
        }
    }

    public static void main(String args[]) {

        /*** Examples of usage. ***/
        Register a = new Register(64);

        try {
            a.store(0xAAFF);

            System.out.println(a.hex());
            System.out.println(a.binary());
            System.out.println(a.binary(3,0));
            System.out.println(a.binary(2,0));
            System.out.println(a.binary(18,13));
            System.out.println(a.binary(13,13));
            System.out.println(a.binary(14,14));
            System.out.println(a.binary(15,15));
            System.out.println(a.binary(16,16));
            System.out.println(a.binary(17,17));
            System.out.println(a.binary(18,18));

            a.increment();
            System.out.println(a.binary());
            a.increment();
            System.out.println(a.binary());
            a.increment();
            System.out.println(a.binary());
            a.increment();
            System.out.println(a.binary());
            a.increment();
            System.out.println(a.binary());
            a.increment();
            System.out.println(a.binary());
            a.negate();
            System.out.println(a.binary());

        } catch (Exception e) {
            System.out.println(e);
        }
    }

}

