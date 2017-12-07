public class ALU {

    private Register _source_a, _dest_c;
    private Bus _bus;
    private int _wordsize;

    public ALU (int wordsize) {
        _wordsize = wordsize;
    }

    public void bus (Bus bus) {
        _bus = bus;
    }

    public void source_a (Register source) {
        _source_a = source;
    }

    public void dest_c (Register dest) {
        _dest_c = dest;
    }

    public void and () {
        String _source_b =  _bus.binary();

        System.out.println("----------- AND ----------");
        System.out.println("A:\t" + _source_a.binary());
        System.out.println("Bus:\t" + _source_b);

        String and = Helper.AND(_source_a.binary(), _source_b);

        System.out.println("AND:\t" + and);

        try {
            _dest_c.load(and);
            System.out.println(_dest_c.binary());
        } catch (Exception e) {
            System.out.println("ALU was not able to perform and operation");
            System.out.println(e);
        }
    }

    public void orr () {
        String _source_b =  _bus.binary();

        System.out.println("----------- ORR ----------");
        System.out.println("A:\t" + _source_a.binary());
        System.out.println("Bus:\t" + _source_b);

        String orr = Helper.OR(_source_a.binary(), _source_b);

        System.out.println("ORR:\t" + orr);

        try {
            _dest_c.load(orr);
        } catch (Exception e) {
            System.out.println("ALU was not able to perform or operation");
            System.out.println(e);
        }
    }

    public void eor () {
        String _source_b =  _bus.binary();

        System.out.println("----------- EOR ----------");
        System.out.println("A:\t" + _source_a.binary());
        System.out.println("Bus:\t" + _source_b);

        String orr = Helper.EOR(_source_a.binary(), _source_b);

        System.out.println("EOR:\t" + orr);

        try {
            _dest_c.load(orr);
        } catch (Exception e) {
            System.out.println("ALU was not able to perform exclusive or operation");
            System.out.println(e);
        }
    }

    public void add () {
        String _source_b =  _bus.binary();

        System.out.println("----------- Addition ----------");
        System.out.println("A:\t" + _source_a.binary());
        System.out.println("Bus:\t" + _source_b);

        String sum = Helper.ADD(_source_a.binary(), _source_b);

        System.out.println("Sum:\t" + sum);

        try {
            _dest_c.load(sum);
            System.out.println(_dest_c.binary());
        } catch (Exception e) {
            System.out.println("ALU was not able to perform addition");
            System.out.println(e);
        }

    }

    public void sub () {
        String _source_b =  _bus.binary();

        System.out.println("----------- Substraction ----------");
        System.out.println("A:\t" + _source_a.binary());
        System.out.println("Bus:\t" + _source_b);

        String dif = Helper.SUB(_source_b, _source_a.binary());

        System.out.println("Difference:\t" + dif);

        try {
            _dest_c.load(dif);
            System.out.println(_dest_c.binary());
        } catch (Exception e) {
            System.out.println("ALU was not able to perform substraction");
            System.out.println(e);
        }
    }

    public void b () {

    }

    public void nor () {

    }

    public void exec (String control) {
        switch (control) {
            case "0000":
                add();
                break;
            case "0001":
                sub();
                break;
            case "0010":
                and();
                break;
            case "0011":
                orr();
                break;
            case "0100":
                eor();
                break;
            default:
                break;
        }
    }

}
