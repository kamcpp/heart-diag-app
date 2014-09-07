package heartdiagapp.core.dsp;

import java.util.Vector;

public interface SignalParser {

    Signal parse(Vector lines);
}
