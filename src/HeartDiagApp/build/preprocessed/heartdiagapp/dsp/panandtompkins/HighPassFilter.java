package heartdiagapp.dsp.panandtompkins;

import heartdiagapp.dsp.Filter;
import heartdiagapp.dsp.Signal;
import heartdiagapp.dsp.SimpleSignal;

public class HighPassFilter implements Filter {
    public Signal apply(Signal x) {
        SimpleSignal t = new SimpleSignal(x.size(), x.timeBetweenValues());
        for (int i = 32; i < x.size(); i++) {
            t.set(i, t.get(i - 1) + x.get(i) - x.get(i - 32));
        }
        SimpleSignal y = new SimpleSignal(x.size(), x.timeBetweenValues());
        for (int i = 16; i < x.size(); i++) {
            y.set(i, x.get(i - 16) - (1.0/32.0) * t.get(i));
        }
        return y;
    }    
}
