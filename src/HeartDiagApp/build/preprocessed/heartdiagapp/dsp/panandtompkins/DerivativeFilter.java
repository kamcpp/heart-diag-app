package heartdiagapp.dsp.panandtompkins;

import heartdiagapp.dsp.Filter;
import heartdiagapp.dsp.Signal;
import heartdiagapp.dsp.SimpleSignal;

public class DerivativeFilter implements Filter {
    public Signal apply(Signal x) {
        SimpleSignal y = new SimpleSignal(x.size(), x.timeBetweenValues());
        for (int i = 4; i < x.size(); i++) {
            y.set(i, (1.0/8.0) * (2 * x.get(i) + x.get(i - 1) - x.get(i - 3) - 2 * x.get(i - 4)));
        }
        return y;
    }    
}
