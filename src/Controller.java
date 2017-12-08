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

        control_memory[15] = new ADD0("ADD", false);
        control_memory[16] = new ADD1("ADD", false);
        control_memory[17] = new ADD2("ADD");

        control_memory[20] = new SUB0("SUB", false);
        control_memory[21] = new SUB1("SUB", false);
        control_memory[22] = new SUB2("SUB");

        control_memory[25] = new ADD0("ADDS", false);
        control_memory[26] = new ADD1("ADDS", true);
        control_memory[27] = new ADD2("ADDS");

        control_memory[30] = new SUB0("SUBS", false);
        control_memory[31] = new SUB1("SUBS", true);
        control_memory[32] = new SUB2("SUBS");

        control_memory[35] = new AND0("AND", false);
        control_memory[36] = new AND1("AND");
        control_memory[37] = new AND2("AND");

        control_memory[40] = new ORR0("ORR", false);
        control_memory[41] = new ORR1("ORR");
        control_memory[42] = new ORR2("ORR");

        control_memory[45] = new EOR0("EOR", false);
        control_memory[46] = new EOR1("EOR");
        control_memory[47] = new EOR2("EOR");

        control_memory[50] = new LSL0();
        control_memory[51] = new LSL1();
        control_memory[52] = new LSL2();

        control_memory[55] = new LSR0();
        control_memory[56] = new LSR1();
        control_memory[57] = new LSR2();

        control_memory[65] = new ADD0("ADDI", true);
        control_memory[66] = new ADD1("ADDI", false);
        control_memory[67] = new ADD2("ADDI");

        control_memory[70] = new SUB0("SUBI", true);
        control_memory[71] = new SUB1("SUBI", false);
        control_memory[72] = new SUB2("SUBI");

        control_memory[75] = new ADD0("ADDIS", true);
        control_memory[76] = new ADD1("ADDIS", true);
        control_memory[77] = new ADD2("ADDIS");

        control_memory[80] = new SUB0("SUBIS", true);
        control_memory[81] = new SUB1("SUBIS", true);
        control_memory[82] = new SUB2("SUBIS");

        control_memory[85] = new AND0("ANDI", true);
        control_memory[86] = new AND1("ANDI");
        control_memory[87] = new AND2("ANDI");

        control_memory[90] = new ORR0("ORRI", true);
        control_memory[91] = new ORR1("ORRI");
        control_memory[92] = new ORR2("ORRI");

        control_memory[95] = new EOR0("EORI", true);
        control_memory[96] = new EOR1("EORI");
        control_memory[97] = new EOR2("EORI");

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
            data_path.flags.reset();
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


    /* -------------------------------------------------- ADD ------------------------------------------------------*/

    public class ADD0 extends RTN {

        private String _type;
        private boolean _immediate;

        public ADD0 (String type, boolean immediate) {
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

    public class ADD1 extends RTN {

        private String _type;
        private boolean _flags;

        public ADD1 (String type, boolean flags) {
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

    public class ADD2 extends RTN {
        private String _type;

        public ADD2 (String type) {
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


    /* -------------------------------------------------- SUB ------------------------------------------------------*/

    public class SUB0 extends RTN {

        private String _type;
        private boolean _immediate;

        public SUB0 (String type, boolean immediate) {
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

    public class SUB1 extends RTN {
        private String _type;
        private boolean _flags;

        public SUB1 (String type, boolean flags) {
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

    public class SUB2 extends RTN {
        private String _type;

        public SUB2 (String type) {
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


    /* -------------------------------------------------- AND ------------------------------------------------------*/
    public class AND0 extends RTN {
        private String _type;
        private boolean _immediate;

        public AND0 (String type, boolean immediate) {
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

    public class AND1 extends RTN {
        private String _type;

        public AND1 (String type) {
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

    public class AND2 extends RTN {
        private String _type;

        public AND2 (String type) {
            _type = type;
        }

        public String toString () {
            return _type + "1";
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


    /* -------------------------------------------------- ORR ------------------------------------------------------*/

    public class ORR0 extends RTN {
        private String _type;
        private boolean _immediate;

        public ORR0 (String type, boolean immediate) {
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

    public class ORR1 extends RTN {
        private String _type;

        public ORR1 (String type) {
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

    public class ORR2 extends RTN {
        private String _type;

        public ORR2 (String type) {
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


    /* -------------------------------------------------- EOR ------------------------------------------------------*/

    public class EOR0 extends RTN {
        private String _type;
        private boolean _immediate;

        public EOR0 (String type, boolean immediate) {
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

    public class EOR1 extends RTN {
        private String _type;

        public EOR1 (String type) {
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

    public class EOR2 extends RTN {
        private String _type;

        public EOR2 (String type) {
            _type = type;
        }

        public String toString () {
            return _type + "1";
        }

        public void execute () {
            try {
                int dest = data_path.IR.decimal(23, 16);
                data_path.bank.load(dest, data_path.C.binary());
            } catch (Exception e) {
                System.out.println("Controller was not able to finish EOR instruction");
                System.out.println(e);
            }

        }

        public int advance () {
            return START;
        }
    }


    /* -------------------------------------------------- LSL ------------------------------------------------------*/

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

    public class LSL2 extends RTN {
        public String toString () {
            return "LSL2";
        }

        public void execute () {
            try {
                int dest = data_path.IR.decimal(23, 16);
                data_path.bank.load(dest, data_path.C.binary());
            } catch (Exception e) {
                System.out.println("Controller was not able to finish LSL instruction");
                System.out.println(e);
            }

        }

        public int advance () {
            return START;
        }
    }

    /* -------------------------------------------------- LSR ------------------------------------------------------*/

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

    public class LSR2 extends RTN {
        public String toString () {
            return "LSR2";
        }

        public void execute () {
            try {
                int dest = data_path.IR.decimal(23, 16);
                data_path.bank.load(dest, data_path.C.binary());
            } catch (Exception e) {
                System.out.println("Controller was not able to finish LSR instruction");
                System.out.println(e);
            }

        }

        public int advance () {
            return START;
        }
    }


    /* -------------------------------------------------- LDUR ------------------------------------------------------*/
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

    /* -------------------------------------------------- STUR ------------------------------------------------------*/
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
}
