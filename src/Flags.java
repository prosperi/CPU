public class Flags {

    private boolean _z, _c, _v, _n;

    public Flags () {
        _z = false;
        _c = false;
        _v = false;
        _n = false;
    }

    public void z (boolean t) {
        _z = t;
    }

    public boolean z () {
        return _z;
    }

    public void c (boolean t) {
        _c = t;
    }

    public boolean c () {
        return _c;
    }

    public void v (boolean t) {
        _v = t;
    }

    public boolean v () {
        return _v;
    }

    public void n (boolean t) {
        _n = t;
    }

    public boolean n () {
        return _n;
    }
}
