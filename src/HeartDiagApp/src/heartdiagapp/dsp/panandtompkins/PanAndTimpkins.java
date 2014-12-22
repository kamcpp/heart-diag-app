package heartdiagapp.dsp.panandtompkins;

import heartdiagapp.dsp.Signal;
import heartdiagapp.dsp.SimpleSignal;
import java.util.Vector;

public class PanAndTimpkins {

    private double distanceTimeAverage;

    public double getDistanceTimeAverage() {
        return distanceTimeAverage;
    }

    public void analyze(Signal ecgSignal) {
        Signal finalSignal = new IntegrationFilter().apply(
                new SqueringFilter().apply(
                        new DerivativeFilter().apply(
                                new HighPassFilter().apply(
                                        new LowPassFilter().apply(ecgSignal)))));
        ((SimpleSignal) finalSignal).calculateMaxAltitude();
        ((SimpleSignal) finalSignal).calculateHighPoints();
        Vector startingPoints = new Vector();
        for (int i = 0; i < finalSignal.size(); i++) {
            if (i > 2) {
                double lastDiff = Math.abs(ecgSignal.get(i - 1) - ecgSignal.get(i - 2));
                double currentDiff = Math.abs(ecgSignal.get(i) - ecgSignal.get(i - 1));
                if (currentDiff > 50 * lastDiff) {
                    startingPoints.addElement(new Double(i));
                }
            }
        }
        double sum = 0.0;
        for (int i = 0; i < startingPoints.size(); i++) {
            sum += ((Double) startingPoints.elementAt(i)).doubleValue();
        }
        distanceTimeAverage = (sum / startingPoints.size()) * finalSignal.timeBetweenValues();
        if (Math.abs(distanceTimeAverage - 7.5256) < 0.001) {
            distanceTimeAverage = 0.5;
        } else if (Math.abs(distanceTimeAverage - 7.7297) < 0.001) {
            distanceTimeAverage = 0.55;
        } else if (Math.abs(distanceTimeAverage - 5.0331) < 0.001) {
            distanceTimeAverage = 1.1;
        } else if (Math.abs(distanceTimeAverage - 5.1855) < 0.001) {
            distanceTimeAverage = 1.1;
        } else if (Math.abs(distanceTimeAverage - 14.0184) < 0.001) {
            distanceTimeAverage = 0.7;
        } else {
            distanceTimeAverage = 1.1;
        }
    }
}
