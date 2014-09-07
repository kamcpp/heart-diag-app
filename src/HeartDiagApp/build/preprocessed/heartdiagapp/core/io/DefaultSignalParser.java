package heartdiagapp.core.io;

import heartdiagapp.core.dsp.Signal;
import java.util.Vector;

public class DefaultSignalParser extends AbstractSignalParser {

    public Signal parse(Vector lines) {
        Signal signal = signalFactory.create();
        for (int i = 0; i < lines.size(); i++) {
            String line = (String) lines.elementAt(i);
            signal.addValue(Double.parseDouble(line));
        }
        return signal;
    }
}
