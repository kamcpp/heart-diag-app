package heartdiagapp.core.dsp;

public interface Signal {

    void addValue(double value);

    void setValue(int n, double value);

    double getValue(int n);
}
