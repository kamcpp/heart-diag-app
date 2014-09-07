package heartdiagapp.core.dsp;

public abstract class AbstractFilter implements Filter{
    
    protected SignalFactory signalFactory;

    public AbstractFilter() {
        signalFactory = new DefaultSignalFactory();
    }
    
    public SignalFactory getSignalFactory() {
        return signalFactory;
    }

    public void setSignalFactory(SignalFactory signalFactory) {
        this.signalFactory = signalFactory;
    }
}
