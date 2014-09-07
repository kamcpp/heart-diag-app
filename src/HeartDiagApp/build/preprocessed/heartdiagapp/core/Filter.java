package heartdiagapp.core;

public interface Filter {

    void setSignalFactory(SignalFactory signalFactory);
    
    Signal apply(Signal signal);
}
