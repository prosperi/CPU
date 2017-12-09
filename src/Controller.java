/***
 * The hardware controler for driving input path.
 */
public class Controller {

    /* Object input fields */
    int memory_size;
    RTN control_memory[];
    CPU data_path;

    int current_entry;

    /* Advancement Types */
    private final int NEXT    = 0;
    private final int START   = 1;
    private final int CMDNAME = 2;
    private final int UNKNOWN = 3;

    /* Primary constructor */
    public Controller(CPU cpu) {

        memory_size = 512;
        data_path   = cpu;

        control_memory = new RTN[memory_size];
        load_cntl_code_1();

        reset();
    }

    public void increment_clock() {

        System.err.println("Current entry\t" + current_entry);
        System.err.println(control_memory[current_entry]);

        control_memory[current_entry].execute();

        switch (control_memory[current_entry].advance()) {

            case NEXT: {
                current_entry++;
                break;
            }

            case START: {
                current_entry = 0;
                break;
            }

            case CMDNAME: {
                try {
                    System.out.println(data_path.IR);
                    current_entry = (data_path.IR.decimal(31,24) + 1) * 5;
                    System.err.println("--------");
                    System.err.println(data_path.IR.binary());
                    System.err.println(current_entry);
                    System.err.println("--------");
                } catch (Exception e) {
                    System.err.println("In Controler:increment_clock");
                    System.err.println(e);
                }

                break;
            }
        }
    }

    public String current_rtn() {
        return control_memory[current_entry].toString();
    }

    public void reset() {

        current_entry = 0;
    }
    public void load_cntl_code_1() {

        control_memory[0] = new Fetch0();
        control_memory[1] = new Fetch1();
        control_memory[2] = new Fetch2();
        control_memory[5] = new NOP();
        control_memory[10] = new LOADI0();

        control_memory[15] = new RSTART("ADD", false);
        control_memory[16] = new ADD("ADD", false);
        control_memory[17] = new RFINISH("ADD");

        control_memory[20] = new RSTART("SUB", false);
        control_memory[21] = new SUB("SUB", false);
        control_memory[22] = new RFINISH("SUB");

        control_memory[25] = new RSTART("ADDS", false);
        control_memory[26] = new ADD("ADDS", true);
        control_memory[27] = new RFINISH("ADDS");

        control_memory[30] = new RSTART("SUBS", false);
        control_memory[31] = new SUB("SUBS", true);
        control_memory[32] = new RFINISH("SUBS");

        control_memory[35] = new RSTART("AND", false);
        control_memory[36] = new AND("AND");
        control_memory[37] = new RFINISH("AND");

        control_memory[40] = new RSTART("ORR", false);
        control_memory[41] = new ORR("ORR");
        control_memory[42] = new RFINISH("ORR");

        control_memory[45] = new RSTART("EOR", false);
        control_memory[46] = new EOR("EOR");
        control_memory[47] = new RFINISH("EOR");

        control_memory[50] = new LSL0();
        control_memory[51] = new LSL1();
        control_memory[52] = new RFINISH("LSR");

        control_memory[55] = new LSR0();
        control_memory[56] = new LSR1();
        control_memory[57] = new RFINISH("LSR");

        control_memory[60] = new BR0();
        control_memory[61] = new BR1();

        control_memory[65] = new RSTART("ADDI", true);
        control_memory[66] = new ADD("ADDI", false);
        control_memory[67] = new RFINISH("ADDI");

        control_memory[70] = new RSTART("SUBI", true);
        control_memory[71] = new SUB("SUBI", false);
        control_memory[72] = new RFINISH("SUBI");

        control_memory[75] = new RSTART("ADDIS", true);
        control_memory[76] = new ADD("ADDIS", true);
        control_memory[77] = new RFINISH("ADDIS");

        control_memory[80] = new RSTART("SUBIS", true);
        control_memory[81] = new SUB("SUBIS", true);
        control_memory[82] = new RFINISH("SUBIS");

        control_memory[85] = new RSTART("ANDI", true);
        control_memory[86] = new AND("ANDI");
        control_memory[87] = new RFINISH("ANDI");

        control_memory[90] = new RSTART("ORRI", true);
        control_memory[91] = new ORR("ORRI");
        control_memory[92] = new RFINISH("ORRI");

        control_memory[95] = new RSTART("EORI", true);
        control_memory[96] = new EOR("EORI");
        control_memory[97] = new RFINISH("EORI");

        control_memory[100] = new LDUR0();
        control_memory[101] = new LDUR1();
        control_memory[102] = new LDUR2();
        control_memory[103] = new LDUR3();
        control_memory[104] = new LDUR4();

        control_memory[105] = new STUR0();
        control_memory[106] = new STUR1();
        control_memory[107] = new STUR2();
        control_memory[108] = new STUR3();
        control_memory[109] = new STUR4();

        control_memory[140] = new CBSTART("CBZ");
        control_memory[141] = new CBZ();
        control_memory[142] = new CBFINISH("CBZ");

        control_memory[145] = new CBSTART("CBNZ");
        control_memory[146] = new CBNZ();
        control_memory[147] = new CBFINISH("CBNZ");

        control_memory[155] = new BCONDSTART("BEQ");
        control_memory[156] = new BEQ();
        control_memory[157] = new BCONDFINISH("BEQ");

        control_memory[160] = new BCONDSTART("BNE");
        control_memory[161] = new BNE();
        control_memory[162] = new BCONDFINISH("BNE");

        control_memory[165] = new BCONDSTART("BLT");
        control_memory[166] = new BLT();
        control_memory[167] = new BCONDFINISH("BLT");

        control_memory[170] = new BCONDSTART("BLE");
        control_memory[171] = new BLE();
        control_memory[172] = new BCONDFINISH("BLE");

        control_memory[175] = new BCONDSTART("BGT");
        control_memory[176] = new BGT();
        control_memory[177] = new BCONDFINISH("BGT");

        control_memory[180] = new BCONDSTART("BGE");
        control_memory[181] = new BGE();
        control_memory[182] = new BCONDFINISH("BGE");

        control_memory[185] = new B0();
        control_memory[186] = new B1();
        control_memory[187] = new B2();


    }

    public class RTN {

        public String toString() {
            return new String("RTN parent toString method.");
        }

        public void execute() {
            System.err.println("You are executing the RTN parent.");
        }

        public int advance() {
            return UNKNOWN;
        }
    };

    /**** FETCH: 0 ****/
    // The RTN for fetching the instruction.
    // Should always be in location zero of the
    // control memory.
    public class Fetch0 extends RTN {

        public String toString() {
            return new String("Fetch0");
        }
        public void execute() {
            data_path.PC.store();
            data_path.MA.load();
        }
        public int advance() {
            return NEXT;
        }

    }

    public class Fetch1 extends RTN {

        public String toString() {
            return new String("Fetch1");
        }
        public void execute() {
            data_path.PC.increment(4);
            data_path.main_memory.memory_load();
        }
        public int advance() {
            return NEXT;
        }
    }

    public class Fetch2 extends RTN {

        public String toString() {
            return new String("Fetch2");
        }
        public void execute() {
            data_path.MD.store();
            data_path.IR.load();
        }
        public int advance() {
            return CMDNAME;
        }
    }


    /**** No Operation: 1 ****/

    // The RTN for doing nothing with the processor.
    // Should always be in location 1 of the
    // control memory.
    public class NOP extends RTN {

        public String toString() {
            return new String("NOP");
        }
        public void execute() {
        }
        public int advance() {
            return START;
        }
    }


    /**** Load Immediate: 2 ****/

    // The RTN for implementing the add immediate operation.
    // Should always be in location 2 of the control memory.
    // Will pull the destination register immediate from.
    // the IR and store in destation register
    public class LOADI0 extends RTN {

        public String toString() {
            return new String("LOADI0");
        }
        public void execute() {
            // IR representation
            // |1  1|1  0|0      0|
            // |5  2|1  8|7      0|
            // | op |dest| immed  |

            try {
                int source      = data_path.IR.decimal(15, 0);
                int destination = data_path.IR.decimal(23, 16);

                System.out.println("Source: " + source);
                data_path.master_bus.store(source);
                data_path.bank.load(destination);

            } catch (Exception e) {
                System.err.println("In Controler:LOADI0:increment_clock");
                System.err.println(e);
            }
        }
        public int advance() {
            return START;
        }
    }


    /* -------------------------------------------------- ADD: 3 ------------------------------------------------------*/

    /**
     * RTN for ADD instruction,
     * reads the second operand, gets the register from register bank,
     * stores in bus and calls ALU operation with ALU control 0000
     */
    public class ADD extends RTN {

        private String _type;
        private boolean _flags;

        public ADD (String type, boolean flags) {
            _type = type;
            _flags = flags;
        }

        public String toString () {
            return _type + "1";
        }

        public void execute () {
            try {
                int source = data_path.IR.decimal(15, 8);
                data_path.master_bus.store(data_path.bank.decimal(source));

                if(_flags) data_path.flags.reset();

                data_path.alu.exec("0000", _flags);

                System.out.println("Result:\t" + data_path.C.binary());
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        public int advance () {
            return NEXT;
        }
    }

    /* -------------------------------------------------- SUB: 4 ------------------------------------------------------*/
    /**
     * RTN for SUB instruction,
     * reads the second operand, gets the register from register bank,
     * stores in bus and calls ALU operation with ALU control 0001
     */
    public class SUB extends RTN {
        private String _type;
        private boolean _flags;

        public SUB (String type, boolean flags) {
            _type = type;
            _flags = flags;
        }

        public String toString () {
            return _type + "1";
        }

        public void execute () {
            try {
                int source = data_path.IR.decimal(15, 8);
                data_path.master_bus.store(data_path.bank.decimal(source));

                if(_flags) data_path.flags.reset();
                data_path.alu.exec("0001", _flags);

                System.out.println("Result:\t" + data_path.C.binary());
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        public int advance () {
            return NEXT;
        }
    }

    /* -------------------------------------------------- AND: 5 ------------------------------------------------------*/
    /**
     * RTN for AND instruction,
     * reads the second operand, gets the register from register bank,
     * stores in bus and calls ALU operation with ALU control 0010
     */
    public class AND extends RTN {
        private String _type;

        public AND (String type) {
            _type = type;
        }

        public String toString () {
            return _type + "1";
        }

        public void execute () {
            try {
                int source = data_path.IR.decimal(15, 8);
                data_path.master_bus.store(data_path.bank.decimal(source));

                data_path.alu.exec("0010", false);

                System.out.println("Result:\t" + data_path.C.binary());
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        public int advance () {
            return NEXT;
        }
    }


    /* -------------------------------------------------- ORR: 6 ------------------------------------------------------*/

    /**
     * RTN for ORR instruction,
     * reads the second operand, gets the register from register bank,
     * stores in bus and calls ALU operation with ALU control 0011
     */
    public class ORR extends RTN {
        private String _type;

        public ORR (String type) {
            _type = type;
        }

        public String toString () {
            return _type + "1";
        }

        public void execute () {
            try {
                int source = data_path.IR.decimal(15, 8);
                data_path.master_bus.store(data_path.bank.decimal(source));

                data_path.alu.exec("0011", false);

                System.out.println("Result:\t" + data_path.C.binary());
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        public int advance () {
            return NEXT;
        }
    }


    /* -------------------------------------------------- EOR: 7 ------------------------------------------------------*/
    /**
     * RTN for EOR instruction,
     * reads the second operand, gets the register from register bank,
     * stores in bus and calls ALU operation with ALU control 0100
     */
    public class EOR extends RTN {
        private String _type;

        public EOR (String type) {
            _type = type;
        }

        public String toString () {
            return _type + "1";
        }

        public void execute () {
            try {
                int source = data_path.IR.decimal(15, 8);
                data_path.master_bus.store(data_path.bank.decimal(source));

                data_path.alu.exec("0100", false);

                System.out.println("Result:\t" + data_path.C.binary());
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        public int advance () {
            return NEXT;
        }
    }

    /* -------------------------------------------------- LSL: 8 ------------------------------------------------------*/
    /**
     * RTN for LSL instruction,
     * reads the first operand, stores in B register,
     * reads the second operand, gets the register from register bank,
     * stores in bus and calls ALU operation with ALU control 0101
     */
    public class LSL0 extends RTN {
        public String toString () {
            return "LSL0";
        }

        public void execute () {
            try {
                int source = data_path.IR.decimal(7, 0);
                data_path.master_bus.store(source);
                data_path.B.load();

                System.out.println("B: " + data_path.B.binary());
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        public int advance () {
            return NEXT;
        }
    }

    public class LSL1 extends RTN {
        public String toString () {
            return "LSL1";
        }

        public void execute () {
            try {
                int source = data_path.IR.decimal(15, 8);
                data_path.master_bus.store(data_path.bank.decimal(source));

                data_path.alu.exec("0101", false);

                System.out.println("Result:\t" + data_path.C.binary());
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        public int advance () {
            return NEXT;
        }
    }

    /* -------------------------------------------------- LSR: 9 ------------------------------------------------------*/
    /**
     * RTN for LSR instruction,
     * reads the first operand, stores in B register,
     * reads the second operand, gets the register from register bank,
     * stores in bus and calls ALU operation with ALU control 0110
     */
    public class LSR0 extends RTN {
        public String toString () {
            return "LSR0";
        }

        public void execute () {
            try {
                int source = data_path.IR.decimal(7, 0);
                data_path.master_bus.store(source);
                data_path.B.load();

                System.out.println("B: " + data_path.B.binary());
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        public int advance () {
            return NEXT;
        }
    }

    public class LSR1 extends RTN {
        public String toString () {
            return "LSR1";
        }

        public void execute () {
            try {
                int source = data_path.IR.decimal(15, 8);
                data_path.master_bus.store(data_path.bank.decimal(source));

                data_path.alu.exec("0110", false);

                System.out.println("Result:\t" + data_path.C.binary());
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        public int advance () {
            return NEXT;
        }
    }

    /**
     * RTN for R-TYPE instructions, the first step
     * reads the first operand, stores in B register
     */
    public class RSTART extends RTN {

        private String _type;
        private boolean _immediate;

        public RSTART (String type, boolean immediate) {
            _type = type;
            _immediate = immediate;
        }

        public String toString () {
            return _type + "0";
        }

        public void execute () {
            try {
                int source = data_path.IR.decimal(7, 0);

                if (_immediate)
                    data_path.master_bus.store(source);
                else
                    data_path.master_bus.store(data_path.bank.decimal(source));

                data_path.B.load();

                System.out.println("B: " + data_path.B.binary());
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        public int advance () {
            return NEXT;
        }
    }

    /**
     * RTN for R-TYPE instructions, the last step
     * stores result in destination
     */
    public class RFINISH extends RTN {
        private String _type;

        public RFINISH (String type) {
            _type = type;
        }

        public String toString () {
            return _type + "2";
        }

        public void execute () {
            try {
                int dest = data_path.IR.decimal(23, 16);
                data_path.bank.load(dest, data_path.C.binary());
            } catch (Exception e) {
                System.out.println("Controller was not able to finish " + _type + " instruction");
                System.out.println(e);
            }
        }

        public int advance () {
            return START;
        }
    }


    /* -------------------------------------------------- LDUR: 10 ------------------------------------------------------*/
    /**
     * RTN for LDUR instruction, loads first operand in B register,
     * on next step, reads second operand and calculates the offset,
     * updates MA and MD, and stores MD in destination
     */
    public class LDUR0 extends RTN {

        public String toString() {
            return new String("LDUR0");
        }
        public void execute() {
            try {
                int source = data_path.IR.decimal(7, 0);

                data_path.master_bus.store(source);
                data_path.B.load();

                System.out.println("B: " + data_path.B.binary());
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        public int advance() {
            return NEXT;
        }

    }

    public class LDUR1 extends RTN {

        public String toString() {
            return new String("LDUR1");
        }
        public void execute() {
            try {
                int source = data_path.IR.decimal(15, 8);
                source = data_path.bank.decimal(source) + 4 * data_path.B.decimal();


                data_path.master_bus.store(source);
                data_path.C.load();

                System.out.println("C: " + data_path.C.binary());
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        public int advance() {
            return NEXT;
        }
    }

    public class LDUR2 extends RTN {

        public String toString() {
            return new String("LDUR2");
        }
        public void execute() {
            try {
             data_path.MA.store(data_path.C.decimal());
            } catch (Exception e) {
                System.out.println("Was not able to load data in MA");
                e.printStackTrace();
            }

        }
        public int advance() {
            return NEXT;
        }
    }

    public class LDUR3 extends RTN {

        public String toString() {
            return new String("LDUR3");
        }
        public void execute() {
            data_path.main_memory.memory_load();
        }
        public int advance() {
            return NEXT;
        }
    }

    public class LDUR4 extends RTN {

        public String toString() {
            return new String("LDUR3");
        }
        public void execute() {
            try {
                int dest = data_path.IR.decimal(23, 16);
                data_path.MD.store();

                data_path.bank.load(dest, data_path.MD.binary());
            } catch (Exception e) {
                System.out.println("Was not able to perform LDUR operation");
                e.printStackTrace();
            }


        }
        public int advance() {
            return START;
        }
    }

    /* -------------------------------------------------- STUR: 11 ------------------------------------------------------*/

    /**
     * RTN for STUR instruction, reads first operand and stores in B,
     * on next step calculates the destination, updates MA, and then MD
     * based on third operand, Finally stores the value using MA and MD
     */
    public class STUR0 extends RTN {

        public String toString() {
            return new String("STUR0");
        }
        public void execute() {
            try {
                int source = data_path.IR.decimal(7, 0);

                data_path.master_bus.store(source);
                data_path.B.load();

                System.out.println("B: " + data_path.B.binary());
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        public int advance() {
            return NEXT;
        }

    }

    public class STUR1 extends RTN {

        public String toString() {
            return new String("STUR1");
        }
        public void execute() {
            try {
                int source = data_path.IR.decimal(15, 8);
                source = data_path.bank.decimal(source) + 4 * data_path.B.decimal();


                data_path.master_bus.store(source);
                data_path.C.load();

                System.out.println("C: " + data_path.C.binary());
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        public int advance() {
            return NEXT;
        }
    }

    public class STUR2 extends RTN {

        public String toString() {
            return new String("STUR2");
        }
        public void execute() {
            try {
                data_path.MA.store(data_path.C.decimal());
            } catch (Exception e) {
                System.out.println("Was not able to load data in MA");
                e.printStackTrace();
            }

        }
        public int advance() {
            return NEXT;
        }
    }

    public class STUR3 extends RTN {

        public String toString() {
            return new String("STUR3");
        }
        public void execute() {
            try {
                int source = data_path.IR.decimal(23, 16);
                source = data_path.bank.decimal(source);

                data_path.MD.store(source);
            } catch (Exception e) {
                System.out.println("Was not able to load data in MD");
                e.printStackTrace();
            }

        }
        public int advance() {
            return NEXT;
        }
    }

    public class STUR4 extends RTN {

        public String toString() {
            return new String("STUR4");
        }
        public void execute() {
                data_path.main_memory.memory_store();
        }
        public int advance() {
            return START;
        }
    }

    /* -------------------------------------------------- B: 12 ------------------------------------------------------*/

    /**
     * RTN for B instructon, reads first operand calculates offset
     * using ALU, on the last step updates PC with new value
     */
    public class B0 extends RTN {

        public String toString() {
            return new String("B0");
        }
        public void execute() {
            try {
                int source = data_path.IR.decimal(23, 0);

                data_path.master_bus.store(source - 1);
                data_path.B.load();

                System.out.println("B: " + data_path.B.binary());
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        public int advance() {
            return NEXT;
        }

    }

    public class B1 extends RTN {
        public String toString () {
            return new String("B1");
        }

        public void execute () {
            try {
                data_path.master_bus.store(2);

                data_path.alu.exec("0101", false);

                System.out.println("Result:\t" + data_path.C.binary());
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        public int advance() {
            return NEXT;
        }
    }

    public class B2 extends RTN {

        public String toString () {
            return new String("B2");
        }
        public void execute() {
            data_path.PC.increment(data_path.C.decimal());
        }
        public int advance() {
            return START;
        }

    }

    /* -------------------------------------------------- BR: 13 ------------------------------------------------------*/

    /**
     * RTN for BR instructon, reads first operand
     * on the second step updates PC with new value
     */
    public class BR0 extends RTN {

        public String toString() {
            return new String("BR0");
        }
        public void execute() {
            try {
                int source = data_path.IR.decimal(23, 0);
                data_path.master_bus.store(data_path.bank.decimal(source));
                data_path.B.load();

                System.out.println("B: " + data_path.B.binary());
            } catch (Exception e) {
                System.out.println(e);
                e.printStackTrace();
            }
        }
        public int advance() {
            return NEXT;
        }

    }

    public class BR1 extends RTN {

        public String toString () {
            return new String("BR1");
        }
        public void execute() {
            data_path.PC.store(data_path.B.decimal());
        }
        public int advance() {
            return START;
        }

    }

    /* -------------------------------------------------- CBZ: 14 ------------------------------------------------------*/

    /**
     * RTN for CBZ, second step, reads the second operand, executes
     * ALU operation to check value with zero and update PC
     */
    public class CBZ extends RTN {

        public String toString() {
            return new String("CBZ1");
        }
        public void execute() {
            try {
                int source = data_path.IR.decimal(23, 16);
                data_path.master_bus.store(data_path.bank.decimal(source));

                data_path.alu.exec("0111", false);

                System.out.println("C: " + data_path.C.binary());
            } catch (Exception e) {
                System.out.println(e);
                e.printStackTrace();
            }
        }
        public int advance() {
            return NEXT;
        }

    }

    /* -------------------------------------------------- CBNZ: 15 ------------------------------------------------------*/
    /**
     * RTN for CBNZ, second step, reads the second operand, executes
     * ALU operation to check value with zero and update PC
     */
    public class CBNZ extends RTN {

        public String toString() {
            return new String("CBNZ1");
        }
        public void execute() {
            try {
                int source = data_path.IR.decimal(23, 16);
                data_path.master_bus.store(data_path.bank.decimal(source));

                data_path.alu.exec("1000", false);

                System.out.println("C: " + data_path.C.binary());
            } catch (Exception e) {
                System.out.println(e);
                e.printStackTrace();
            }
        }
        public int advance() {
            return NEXT;
        }

    }

    /**
     * RTN for CBZ and CBNZ, first step, reads the first operand,
     * stores in B
     */
    public class CBSTART extends RTN {

        String _type;

        public CBSTART(String type) {
            _type = type;
        }

        public String toString() {
            return new String(_type);
        }
        public void execute() {
            try {
                int source = data_path.IR.decimal(15, 0);

                data_path.master_bus.store(source - 1);
                data_path.B.load();

                System.out.println("B: " + data_path.B.binary());
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        public int advance() {
            return NEXT;
        }

    }

    /**
     * RTN for CBZ and CBNZ, last step, updates the PC, and branches if
     * condition is met
     */
    public class CBFINISH extends RTN {

        String _type;

        public CBFINISH(String type) {
            _type = type;
        }

        public String toString() {
            return new String(_type);
        }
        public void execute() {
            data_path.PC.increment(data_path.C.decimal());
        }
        public int advance() {
            return START;
        }
    }

    /* -------------------------------------------------- BEQ: 16 ------------------------------------------------------*/

    /**
     * RTN for BEQ instruction, calls ALU operation and updates C
     * based on the ALU operation result
     */
    public class BEQ extends RTN {
        public String toString () {
            return new String("BEQ1");
        }

        public void execute () {
            try {
                data_path.master_bus.store(2);

                data_path.alu.exec("1001", false);

                System.out.println("Result:\t" + data_path.C.binary());
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        public int advance() {
            return NEXT;
        }
    }

    /* -------------------------------------------------- BNE: 17 ------------------------------------------------------*/
    /**
     * RTN for BNE instruction, calls ALU operation and updates C
     * based on the ALU operation result
     */
    public class BNE extends RTN {
        public String toString () {
            return new String("BNE1");
        }

        public void execute () {
            try {
                data_path.master_bus.store(2);

                data_path.alu.exec("1010", false);

                System.out.println("Result:\t" + data_path.C.binary());
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        public int advance() {
            return NEXT;
        }
    }

    /* -------------------------------------------------- BLT: 18 ------------------------------------------------------*/
    /**
     * RTN for BLT instruction, calls ALU operation and updates C
     * based on the ALU operation result
     */
    public class BLT extends RTN {
        public String toString () {
            return new String("BLT1");
        }

        public void execute () {
            try {
                data_path.master_bus.store(2);

                data_path.alu.exec("1011", false);

                System.out.println("Result:\t" + data_path.C.binary());
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        public int advance() {
            return NEXT;
        }
    }

    /* -------------------------------------------------- BLE: 19 ------------------------------------------------------*/
    /**
     * RTN for BLE instruction, calls ALU operation and updates C
     * based on the ALU operation result
     */
    public class BLE extends RTN {
        public String toString () {
            return new String("BLE1");
        }

        public void execute () {
            try {
                data_path.master_bus.store(2);

                data_path.alu.exec("1100", false);

                System.out.println("Result:\t" + data_path.C.binary());
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        public int advance() {
            return NEXT;
        }
    }

    /* -------------------------------------------------- BGT: 20 ------------------------------------------------------*/
    /**
     * RTN for BGT instruction, calls ALU operation and updates C
     * based on the ALU operation result
     */
    public class BGT extends RTN {
        public String toString () {
            return new String("BGT1");
        }

        public void execute () {
            try {
                data_path.master_bus.store(2);

                data_path.alu.exec("1101", false);

                System.out.println("Result:\t" + data_path.C.binary());
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        public int advance() {
            return NEXT;
        }
    }

    /* -------------------------------------------------- BGE: 21 ------------------------------------------------------*/
    /**
     * RTN for BGE instruction, calls ALU operation and updates C
     * based on the ALU operation result
     */
    public class BGE extends RTN {
        public String toString () {
            return new String("BGE1");
        }

        public void execute () {
            try {
                data_path.master_bus.store(2);

                data_path.alu.exec("1110", false);

                System.out.println("Result:\t" + data_path.C.binary());
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        public int advance() {
            return NEXT;
        }
    }

    /**
     * RTN for conditional branches, first step, reads the first operand,
     * transfers onto bus, and stores in B
     */
    public class BCONDSTART extends RTN {

        String _type;

        public BCONDSTART(String type) {
            _type = type;
        }

        public String toString() {
            return new String(_type);
        }
        public void execute() {
            try {
                int source = data_path.IR.decimal(23, 0);

                data_path.master_bus.store(source - 1);
                data_path.B.load();

                System.out.println("B: " + data_path.B.binary());
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        public int advance() {
            return NEXT;
        }

    }

    /**
     * RTN for Conditional Branches, last step, updates the PC, and branches if
     * condition is met
     */
    public class BCONDFINISH extends RTN {
        String _type;

        public BCONDFINISH (String type) {
            _type = type;
        }

        public String toString () {
            return new String(_type);
        }
        public void execute() {
            data_path.PC.increment(data_path.C.decimal());
        }
        public int advance() {
            return START;
        }
    }
}
