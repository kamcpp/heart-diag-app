package heartdiagapp.core.io;

import heartdiagapp.core.dsp.Signal;
import java.util.Vector;

public interface SignalParser {

    Signal parse(Vector lines);
}
