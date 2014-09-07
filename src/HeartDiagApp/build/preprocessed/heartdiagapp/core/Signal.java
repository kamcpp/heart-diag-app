package heartdiagapp.core;

public interface Signal {

    void setValueAt(double time, double value);
    
    Double getValueAt(double time);

    double[] getValusBetween(double t1, double t2);
}
