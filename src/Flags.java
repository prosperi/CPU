/**
 * Flags class keeps all of the 4 flags and updates
 * them based on current Instruction
 */
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

    public boolean getZ () {
        return _z;
    }

    public boolean getV () {
        return _v;
    }

    public boolean getN () {
        return _n;
    }

    /**
     * Reset flags
     */
    public void reset () {
        _z = false;
        _c = false;
        _v = false;
        _n = false;
    }

    /**
     * Get String representation of the flags
     * @return String
     */
    public String toString () {
        String output = "";

        output = "Z:  " + (_z ? 1 : 0) + "   C:  " + (_c ? 1 : 0) + "   V:  " + (_v ? 1 : 0) + "   N:  " + (_n ? 1 : 0);

        return output;
    }


}
