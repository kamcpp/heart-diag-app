package heartdiagapp.core.dsp;

import java.util.Hashtable;

public class DefaultSignal implements Signal {

    private int counter;
    private final Hashtable pairsHashtable;

    public DefaultSignal() {
        counter = 0;
        pairsHashtable = new Hashtable();
    }

    public void addValue(double value) {
        setValue(counter++, value);
    }
    
    public void setValue(int n, double value) {
        pairsHashtable.put(new Integer(n), new Double(value));
    }

    public double getValue(int n) {
        return ((Double)pairsHashtable.get(new Integer(n))).doubleValue();
    }
}
