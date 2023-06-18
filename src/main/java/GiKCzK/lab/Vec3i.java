package GiKCzK.lab;

public class Vec3i {

    public int x;
    public int y;
    public int z;

    public Vec3i(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int getXYZ(int a) {
        if (a == 0) {
            return x;
        } else if (a == 1) {
            return y;
        } else if (a == 2) {
            return z;
        } else {
            return -1;
        }
    }

    @Override
    public String toString() {
        return x + " " + y + " " + z;
    }
}
