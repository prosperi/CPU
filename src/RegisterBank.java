/***
 * Class implementing a collection of registers.
 * Source and destination selection is implemented
 * here to control how the bus is manipulated by
 * this device.
 *
 * @author mestiasz@lafayette.edu - just added description
 */
public class RegisterBank {

    /* Object input fields */
    private Register registers[];

    private int wordsize;
    private int register_cnt;

    /* Primary constructor */
    public RegisterBank(int wordSize, int registerCnt) {

        wordsize     = wordSize;
        register_cnt = registerCnt;

        registers = new Register[register_cnt];

        for(int index = 0; index < register_cnt; index++) {
            registers[index] = new Register(wordsize);
        }
    }

    public void set_source_bus(Bus bus) {

        for(int index = 0; index < register_cnt; index++) {
            registers[index].set_source_bus(bus);
        }
    }

    public void set_destination_bus(Bus bus) {

        for(int index = 0; index < register_cnt; index++) {
            registers[index].set_destination_bus(bus);
        }
    }

    /**
     * load Register, given register_id
     * @param register_id - int
     * @throws Exception
     */
    public void load(int register_id) throws Exception {

        if(register_id < 0 || register_cnt <= register_id)
            throw new Exception("RegisterBank binary register_id out of range.");

        registers[register_id].load();
    }

    /**
     *  load Register, given register_id and source in binary
     * @param register_id - int
     * @param source - String
     * @throws Exception
     */
    public void load(int register_id, String source) throws Exception {

        if(register_id < 0 || register_cnt <= register_id)
            throw new Exception("RegisterBank binary register_id out of range.");

        registers[register_id].load(source);
    }

    /**
     *  store Register, given register_id
     * @param register_id - int
     * @throws Exception
     */
    public void store(int register_id) throws Exception {

        if(register_id < 0 || register_cnt <= register_id)
            throw new Exception("RegisterBank binary register_id out of range.");

        registers[register_id].store();
    }

    /**
     *  increment value in Register, given register_id
     * @param register_id - int
     * @throws Exception
     */
    public void increment(int register_id) throws Exception {

        if(register_id < 0 || register_cnt <= register_id)
            throw new Exception("RegisterBank binary register_id out of range.");

        registers[register_id].increment();
    }

    /**
     *  negate value in Register, given register_id
     * @param register_id - int
     * @throws Exception
     */
    public void negate(int register_id) throws Exception {

        if(register_id < 0 || register_cnt <= register_id)
            throw new Exception("RegisterBank binary register_id out of range.");

        registers[register_id].negate();
    }


    /**
     *  get binary representation of value in Register, given register_id
     * @param register_id - int
     * @throws Exception
     */
    public String binary(int register_id) throws Exception {

        if(register_id < 0 || register_cnt <= register_id)
            throw new Exception("RegisterBank binary register_id out of range.");

        return registers[register_id].binary();
    }

    /**
     *  get decimal representation of value in Register, given register_id
     * @param register_id - int
     * @throws Exception
     */
    public int decimal(int register_id) throws Exception {
        if (register_id < 0 || register_cnt <= register_id)
            throw new Exception("RegisterBank decimal register_id out of range.");

        return registers[register_id].decimal();
    }

    /**
     *  get hex representation of value in Register, given register_id
     * @param register_id - int
     * @throws Exception
     */
    public String hex(int register_id) throws Exception {

        if(register_id < 0 || register_cnt <= register_id)
            throw new Exception("RegisterBank hex register_id out of range.");

        return registers[register_id].hex();
    }

    /**
     *  store value in Register, given register id
     * @param value  - int
     * @param register_id - int
     * @throws Exception
     */
    public void store(int value, int register_id) throws Exception {

        if(register_id < 0 || register_cnt <= register_id)
            throw new Exception("RegisterBank store(int) register_id out of range.");

        registers[register_id].store(value);
    }

    /**
     *  store binary value in Register, given register id
     * @param value  - String
     * @param register_id - int
     * @throws Exception
     */
    public void store(String value, int register_id) throws Exception {

        if(register_id < 0 || register_cnt <= register_id)
            throw new Exception("RegisterBank store(str) register_id out of range.");

        registers[register_id].store(value);
    }

    public static void main(String args[]) {


        /*** Examples of usage. ***/
        RegisterBank a = new RegisterBank(32, 8);

        try {

            for(int cnt = 0; cnt < 8; cnt++) {
                System.err.println(String.format("Reg 0x%02X: %s", cnt, a.binary(cnt)));
            }

            System.err.println("------------------------------------------");
            a.store(0x4A5, 0);
            a.store(0xFFFFFF, 1);
            a.store(0x38D, 2);
            a.store(0x3AB3, 3);

            for(int cnt = 0; cnt < 8; cnt++) {
                System.err.println(String.format("Reg 0x%02X: %s", cnt, a.binary(cnt)));
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

}

