package heartdiagapp.dsp;

public class Point {

    private int x;
    private double y;

    public Point() {
    }

    public Point(int x, double y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj instanceof Point) {
            Point point = (Point) obj;
            return point.x == this.x && (Math.abs(point.y - this.y) < 0.5);
        }
        return false;
    }

    public int hashCode() {
        return x;
    }
}
