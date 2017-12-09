public class ALU {

    private Register _source_a, _dest_c;
    private Bus _bus;
    private int _wordsize;
    private Flags _flags;

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

    public void flags (Flags f) {
        _flags = f;
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

        String eor = Helper.EOR(_source_a.binary(), _source_b);

        System.out.println("EOR:\t" + eor);

        try {
            _dest_c.load(eor);
        } catch (Exception e) {
            System.out.println("ALU was not able to perform exclusive or operation");
            System.out.println(e);
        }
    }

    public void lsl () {
        String _source_b =  _bus.binary();

        System.out.println("----------- LSL ----------");
        System.out.println("A:\t" + _source_a.binary());
        System.out.println("Bus:\t" + _source_b);

        String lsl = Helper.LSL(_source_a.binary(), _source_b);

        System.out.println("LSL:\t" + lsl);

        try {
            _dest_c.load(lsl);
        } catch (Exception e) {
            System.out.println("ALU was not able to perform left shift operation");
            System.out.println(e);
        }
    }

    public void lsr () {
        String _source_b =  _bus.binary();

        System.out.println("----------- LSR ----------");
        System.out.println("A:\t" + _source_a.binary());
        System.out.println("Bus:\t" + _source_b);

        String lsr = Helper.LSR(_source_a.binary(), _source_b);

        System.out.println("LSR:\t" + lsr);

        try {
            _dest_c.load(lsr);
        } catch (Exception e) {
            System.out.println("ALU was not able to perform right shift operation");
            System.out.println(e);
        }
    }

    public void add (boolean flags) {
        String _source_b =  _bus.binary();

        System.out.println("----------- Addition ----------");
        System.out.println("A:\t" + _source_a.binary());
        System.out.println("Bus:\t" + _source_b);

        String sum = Helper.ADD(_source_a.binary(), _source_b);
        System.out.println("Sum:\t" + sum);

        if (flags) {
            if (sum.length() > _wordsize)  _flags.v();
            if (sum.equals(Helper.ZERO())) _flags.z();
            if (sum.charAt(0) == '1')      _flags.n();
            System.out.println(_flags);
        }

        sum = sum.substring(sum.length() - _wordsize, _wordsize);

        try {
            _dest_c.load(sum);
            System.out.println(_dest_c.binary());
        } catch (Exception e) {
            System.out.println("ALU was not able to perform addition");
            System.out.println(e);
        }

    }

    public void sub (boolean flags) {
        String _source_b =  _bus.binary();

        System.out.println("----------- Substraction ----------");
        System.out.println("A:\t" + _source_a.binary());
        System.out.println("Bus:\t" + _source_b);

        String dif = Helper.SUB(_source_b, _source_a.binary());
        System.out.println("Difference:\t" + dif);

        if (flags) {
            if (dif.length() > _wordsize)  _flags.v();
            if (dif.equals(Helper.ZERO())) _flags.z();
            if (dif.charAt(0) == '1')      _flags.n();
            System.out.println(_flags);
        }

        dif = dif.substring(dif.length() - _wordsize, _wordsize);

        try {
            _dest_c.load(dif);
            System.out.println(_dest_c.binary());
        } catch (Exception e) {
            System.out.println("ALU was not able to perform substraction");
            System.out.println(e);
        }
    }

    public void cbz () {
        String _source_b =  _bus.binary();

        System.out.println("----------- CBZ ----------");
        System.out.println("A:\t" + _source_a.binary());
        System.out.println("Bus:\t" + _source_b);

        int res = 0;
        if (_source_b.equals(Helper.ZERO())) {
            res = _source_a.decimal() * 4;
            _flags.z();
        }

        System.out.println("Result:\t" + res);


        try {
            _dest_c.store(res);
            System.out.println(_dest_c.binary());
        } catch (Exception e) {
            System.out.println("ALU was not able to perform CBZ");
            System.out.println(e);
        }
    }

    public void cbnz () {
        String _source_b =  _bus.binary();

        System.out.println("----------- CBNZ ----------");
        System.out.println("A:\t" + _source_a.binary());
        System.out.println("Bus:\t" + _source_b);

        int res = 0;
        if (!_source_b.equals(Helper.ZERO())) {
            res = _source_a.decimal() * 4;
        } else {
            _flags.z();
        }

        System.out.println("Result:\t" + res);


        try {
            _dest_c.store(res);
            System.out.println(_dest_c.binary());
        } catch (Exception e) {
            System.out.println("ALU was not able to perform CBNZ");
            System.out.println(e);
        }
    }

    public void beq () {
        String _source_b =  _bus.binary();

        System.out.println("----------- BEQ ----------");
        System.out.println("A:\t" + _source_a.binary());
        System.out.println("Bus:\t" + _source_b);

        int res = 0;
        if (_flags.getZ()) {
            res = _source_a.decimal() * 4;
        }

        System.out.println("Result:\t" + res);


        try {
            _dest_c.store(res);
            System.out.println(_dest_c.binary());
        } catch (Exception e) {
            System.out.println("ALU was not able to perform BEQ");
            System.out.println(e);
        }
    }

    public void bne () {
        String _source_b =  _bus.binary();

        System.out.println("----------- BNE ----------");
        System.out.println("A:\t" + _source_a.binary());
        System.out.println("Bus:\t" + _source_b);

        int res = 0;
        if (!_flags.getZ()) {
            res = _source_a.decimal() * 4;
        }

        System.out.println("Result:\t" + res);


        try {
            _dest_c.store(res);
            System.out.println(_dest_c.binary());
        } catch (Exception e) {
            System.out.println("ALU was not able to perform BNE");
            System.out.println(e);
        }
    }

    public void blt () {
        String _source_b =  _bus.binary();

        System.out.println("----------- BLT ----------");
        System.out.println("A:\t" + _source_a.binary());
        System.out.println("Bus:\t" + _source_b);

        int res = 0;
        if (_flags.getN() != _flags.getV()) {
            res = _source_a.decimal() * 4;
        }

        System.out.println("Result:\t" + res);


        try {
            _dest_c.store(res);
            System.out.println(_dest_c.binary());
        } catch (Exception e) {
            System.out.println("ALU was not able to perform BLT");
            System.out.println(e);
        }
    }

    public void ble () {
        String _source_b =  _bus.binary();

        System.out.println("----------- BLE ----------");
        System.out.println("A:\t" + _source_a.binary());
        System.out.println("Bus:\t" + _source_b);

        int res = 0;
        if (!(!_flags.getZ() && _flags.getN() == _flags.getV())) {
            res = _source_a.decimal() * 4;
        }

        System.out.println("Result:\t" + res);


        try {
            _dest_c.store(res);
            System.out.println(_dest_c.binary());
        } catch (Exception e) {
            System.out.println("ALU was not able to perform BLE");
            System.out.println(e);
        }
    }

    public void bgt () {
        String _source_b =  _bus.binary();

        System.out.println("----------- BGT ----------");
        System.out.println("A:\t" + _source_a.binary());
        System.out.println("Bus:\t" + _source_b);

        int res = 0;
        if (!_flags.getZ() && _flags.getN() == _flags.getV()) {
            res = _source_a.decimal() * 4;
        }

        System.out.println("Result:\t" + res);


        try {
            _dest_c.store(res);
            System.out.println(_dest_c.binary());
        } catch (Exception e) {
            System.out.println("ALU was not able to perform BGT");
            System.out.println(e);
        }
    }

    public void bge () {
        String _source_b =  _bus.binary();

        System.out.println("----------- BGE ----------");
        System.out.println("A:\t" + _source_a.binary());
        System.out.println("Bus:\t" + _source_b);

        int res = 0;
        if (_flags.getN() == _flags.getV()) {
            res = _source_a.decimal() * 4;
        }

        System.out.println("Result:\t" + res);

        try {
            _dest_c.store(res);
            System.out.println(_dest_c.binary());
        } catch (Exception e) {
            System.out.println("ALU was not able to perform BGE");
            System.out.println(e);
        }
    }



    public void exec (String control, boolean flags) {
        switch (control) {
            case "0000":
                add(flags);
                break;
            case "0001":
                sub(flags);
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
            case "0101":
                lsl();
                break;
            case "0110":
                lsr();
                break;
            case "0111":
                cbz();
                break;
            case "1000":
                cbnz();
                break;
            case "1001":
                beq();
                break;
            case "1010":
                bne();
                break;
            case "1011":
                blt();
                break;
            case "1100":
                ble();
                break;
            case "1101":
                bgt();
                break;
            case "1110":
                bge();
                break;
            default:
                break;
        }
    }

}
