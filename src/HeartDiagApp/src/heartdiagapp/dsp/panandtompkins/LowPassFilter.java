package heartdiagapp.dsp.panandtompkins;

import heartdiagapp.dsp.Filter;
import heartdiagapp.dsp.Signal;
import heartdiagapp.dsp.SimpleSignal;

public class LowPassFilter implements Filter {
    public Signal apply(Signal x) {
        SimpleSignal y = new SimpleSignal(x.size(), x.timeBetweenValues());
        for( int i = 12; i < x.size(); i++) {
            y.set(i, 2 * y.get(i - 1) - y.get(i - 2) + (1.0 / 32.0) * (x.get(i) - 2 * x.get(i - 6) + x.get(i - 12)));
        }
        return y;
    }    
}
