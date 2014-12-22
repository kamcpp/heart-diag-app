package heartdiagapp.dsp;

public interface Signal {
    
    double timeBetweenValues();
    
    int size();
    
    void center();
    
    double get(int time);
    
    double maxAltitude();
    
    double baseLineY();
    
    int[] highPoints();
}
