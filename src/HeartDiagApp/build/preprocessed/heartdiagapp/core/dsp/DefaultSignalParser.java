package heartdiagapp.core.dsp;

import heartdiagapp.core.helpers.StringHelper;
import java.util.Vector;

public class DefaultSignalParser extends AbstractSignalParser {

    public Signal parse(Vector lines) {
        StringHelper stringHelper = new StringHelper();
        Signal signal = signalFactory.create();
        for (int i = 0; i < lines.size(); i++) {
            String line = (String) lines.elementAt(i);
            Vector tokens = stringHelper.split(line, ',');
            double time = Double.parseDouble((String) tokens.elementAt(0));
            double value = Double.parseDouble((String) tokens.elementAt(1));
            TimeValuePair pair = new TimeValuePair(time, value);
            signal.setPair(pair);
        }
        return signal;
    }
}
