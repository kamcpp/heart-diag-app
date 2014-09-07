package heartdiagapp.core;

public abstract class AbstractFilter implements Filter{
    
    private SignalFactory signalFactory;

    public SignalFactory getSignalFactory() {
        return signalFactory;
    }

    public void setSignalFactory(SignalFactory signalFactory) {
        this.signalFactory = signalFactory;
    }
}
