package heartdiagapp.dsp.panandtompkins;

import heartdiagapp.dsp.Filter;
import heartdiagapp.dsp.Signal;
import heartdiagapp.dsp.SimpleSignal;

public class IntegrationFilter implements Filter {
    private final int WINDOW_WIDTH = 30;

    public Signal apply(Signal x) {
        SimpleSignal y = new SimpleSignal(x.size(), x.timeBetweenValues());
        for (int i = 0; i < x.size(); i++) {
            double sum = 0;
            for (int j = i - WINDOW_WIDTH >= 0 ? i - WINDOW_WIDTH : 0; j <= i; j++) {
                sum += x.get(j);
            }
            y.set(i, sum / WINDOW_WIDTH);
        }
        return y;
    }
}
