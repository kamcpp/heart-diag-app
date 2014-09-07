package heartdiagapp.core.dsp;

public class DefaultSignalFactory implements SignalFactory {

    public Signal create() {
        return new DefaultSignal();
    }
}
