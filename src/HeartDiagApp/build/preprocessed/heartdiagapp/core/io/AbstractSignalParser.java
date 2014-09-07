package heartdiagapp.core.io;

import heartdiagapp.core.dsp.DefaultSignalFactory;
import heartdiagapp.core.dsp.SignalFactory;

public abstract class AbstractSignalParser implements SignalParser {

    protected SignalFactory signalFactory;

    public AbstractSignalParser() {
        signalFactory = new DefaultSignalFactory();
    }
    
    public SignalFactory getSignalFactory() {
        return signalFactory;
    }

    public void setSignalFactory(SignalFactory signalFactory) {
        this.signalFactory = signalFactory;
    }
}
