package heartdiagapp.dsp.panandtompkins;

import heartdiagapp.dsp.Filter;
import heartdiagapp.dsp.Signal;
import heartdiagapp.dsp.SimpleSignal;

public class SqueringFilter implements Filter {
    public Signal apply(Signal x) {
        SimpleSignal y = new SimpleSignal(x.size(), x.timeBetweenValues());
        for (int i = 0; i < x.size(); i++) {
            if (x.get(i) >= 0) {
                y.set(i, x.get(i));
            } else {
                y.set(i, -x.get(i));
            }
        }
        return y; 
    }    
}
