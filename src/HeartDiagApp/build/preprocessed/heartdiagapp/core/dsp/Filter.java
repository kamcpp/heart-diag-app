package heartdiagapp.core.dsp;

public interface Filter {

    void setSignalFactory(SignalFactory signalFactory);
    
    Signal apply(Signal signal);
}
