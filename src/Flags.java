public class Flags {

    private boolean _z, _c, _v, _n;

    public Flags () {
        _z = false;
        _c = false;
        _v = false;
        _n = false;
    }

    public void z () {
        _z = true;
    }

    public void c () {
        _c = true;
    }

    public void v () {
        _v = true;
    }

    public void n () {
        _n = true;
    }

    public void reset () {
        _z = false;
        _c = false;
        _v = false;
        _n = false;
    }

    public String toString () {
        String output = "";

        output = "Z:  " + _z + "\tC:  " + _c + "\tV:  " + _v + "\tN:  " + _n;

        return output;
    }


}
