/***
 * The hardware controler for driving data path.
 */
public class Controller {

    /* Object data fields */
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

        control_memory[15] = new ADD0();
        control_memory[16] = new ADD1();
        control_memory[17] = new ADD2();

        control_memory[20] = new SUB0();
        control_memory[21] = new SUB1();
        control_memory[22] = new SUB2();

        control_memory[35] = new AND0();
        control_memory[36] = new AND1();
        control_memory[37] = new AND2();

        control_memory[40] = new ORR0();
        control_memory[41] = new ORR1();
        control_memory[42] = new ORR2();

        control_memory[45] = new EOR0();
        control_memory[46] = new EOR1();
        control_memory[47] = new EOR2();

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


    /* ------------------------------------------ Arithmetic Instructions -------------------------------------------*/

    /* -------------------------------------------------- ADD ------------------------------------------------------*/

    public class ADD0 extends RTN {
        public String toString () {
            return "ADD0";
        }

        public void execute () {
            try {
                int source = data_path.IR.decimal(7, 0);
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
        public String toString () {
            return "ADD1";
        }

        public void execute () {
            try {
                int source = data_path.IR.decimal(15, 8);
                data_path.master_bus.store(data_path.bank.decimal(source));

                data_path.alu.exec("0000");

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
        public String toString () {
            return "ADD2";
        }

        public void execute () {
            try {
                int dest = data_path.IR.decimal(23, 16);
                data_path.bank.load(dest, data_path.C.binary());
            } catch (Exception e) {
                System.out.println("Controller was not able to finish ADD instruction");
                System.out.println(e);
            }

        }

        public int advance () {
            return START;
        }
    }


    /* -------------------------------------------------- SUB ------------------------------------------------------*/

    public class SUB0 extends RTN {
        public String toString () {
            return "SUB0";
        }

        public void execute () {
            try {
                int source = data_path.IR.decimal(7, 0);
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
        public String toString () {
            return "SUB1";
        }

        public void execute () {
            try {
                int source = data_path.IR.decimal(15, 8);
                data_path.master_bus.store(data_path.bank.decimal(source));

                data_path.alu.exec("0001");

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
        public String toString () {
            return "SUB2";
        }

        public void execute () {
            try {
                int dest = data_path.IR.decimal(23, 16);
                data_path.bank.load(dest, data_path.C.binary());
            } catch (Exception e) {
                System.out.println("Controller was not able to finish ADD instruction");
                System.out.println(e);
            }

        }

        public int advance () {
            return START;
        }
    }


    /* -------------------------------------------------- AND ------------------------------------------------------*/
    public class AND0 extends RTN {
        public String toString () {
            return "AND0";
        }

        public void execute () {
            try {
                int source = data_path.IR.decimal(7, 0);
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
        public String toString () {
            return "AND1";
        }

        public void execute () {
            try {
                int source = data_path.IR.decimal(15, 8);
                data_path.master_bus.store(data_path.bank.decimal(source));

                data_path.alu.exec("0010");

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
        public String toString () {
            return "AND2";
        }

        public void execute () {
            try {
                int dest = data_path.IR.decimal(23, 16);
                data_path.bank.load(dest, data_path.C.binary());
            } catch (Exception e) {
                System.out.println("Controller was not able to finish AND instruction");
                System.out.println(e);
            }

        }

        public int advance () {
            return START;
        }
    }


    /* -------------------------------------------------- ORR ------------------------------------------------------*/

    public class ORR0 extends RTN {
        public String toString () {
            return "ORR0";
        }

        public void execute () {
            try {
                int source = data_path.IR.decimal(7, 0);
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
        public String toString () {
            return "ORR1";
        }

        public void execute () {
            try {
                int source = data_path.IR.decimal(15, 8);
                data_path.master_bus.store(data_path.bank.decimal(source));

                data_path.alu.exec("0011");

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
        public String toString () {
            return "ORR2";
        }

        public void execute () {
            try {
                int dest = data_path.IR.decimal(23, 16);
                data_path.bank.load(dest, data_path.C.binary());
            } catch (Exception e) {
                System.out.println("Controller was not able to finish ORR instruction");
                System.out.println(e);
            }

        }

        public int advance () {
            return START;
        }
    }


    /* -------------------------------------------------- EOR ------------------------------------------------------*/

    public class EOR0 extends RTN {
        public String toString () {
            return "EOR0";
        }

        public void execute () {
            try {
                int source = data_path.IR.decimal(7, 0);
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
        public String toString () {
            return "EOR1";
        }

        public void execute () {
            try {
                int source = data_path.IR.decimal(15, 8);
                data_path.master_bus.store(data_path.bank.decimal(source));

                data_path.alu.exec("0100");

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
        public String toString () {
            return "EOR2";
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

}
