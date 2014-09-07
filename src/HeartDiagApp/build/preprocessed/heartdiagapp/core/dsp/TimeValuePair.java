package heartdiagapp.core.dsp;

public class TimeValuePair {

    private double time;
    private double value;

    public TimeValuePair(double time, double value) {
        this.time = time;
        this.value = value;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
