package heartdiagapp.dsp;

public class SimpleSignal implements Signal {

    private double baseLineY;
    private double timeBetweenValues;
    private double[] values;
    private double maxAltitude;
    private int[] highPoints;

    public SimpleSignal(int numberOfValues) {
        values = new double[numberOfValues];
    }

    public SimpleSignal(int numberOfValues, double timeBetweenValues) {
        this.timeBetweenValues = timeBetweenValues;
        values = new double[numberOfValues];
    }

    public double timeBetweenValues() {
        return timeBetweenValues;
    }

    public void setTimeBetweenValues(double timeBetweenValues) {
        this.timeBetweenValues = timeBetweenValues;
    }

    public double baseLineY() {
        return baseLineY;
    }

    public int[] highPoints() {
        return highPoints;
    }

    public double get(int time) {
        return values[time];
    }

    public void set(int time, double value) {
        values[time] = value;
    }

    public int size() {
        return values.length;
    }

    public void center() {
        double sum = 0;
        for (int i = 0; i < values.length; i++) {
            sum += values[i];
        }
        double average = sum / values.length;
        for (int i = 0; i < values.length; i++) {
            values[i] = values[i] - average;
        }
    }

    public void calculateMaxAltitude() {
        double min;
        double max;
        min = max = get(0);
        for (int i = 1; i < size(); i++) {
            if (min > get(i)) {
                min = get(i);
            }
            if (get(i) > max) {
                max = get(i);
            }
        }
        max = Math.abs(max);
        min = Math.abs(min);
        maxAltitude = max > min ? max : min;
    }

    public double maxAltitude() {
        return maxAltitude;
    }

    protected void calculateBaseLine() {
        int[] counters = new int[(int) maxAltitude + 1];
        baseLineY = Double.MIN_VALUE;
        int maxCount = Integer.MIN_VALUE;
        for (int i = 0; i < size(); i++) {
            int index = (int) Math.abs(get(i));
            counters[index]++;
            if (counters[index] > maxCount) {
                maxCount = counters[index];
                baseLineY = index;
            }
        }
    }

    public void calculateHighPoints() {
        calculateBaseLine();
        int[] maximums = new int[30];
        int[] indices = new int[maximums.length];
        for (int i = 0; i < maximums.length; i++) {
            maximums[i] = Integer.MIN_VALUE;
        }
        for (int i = 0; i < indices.length; i++) {
            indices[i] = -1;
        }
        for (int i = 0; i < size(); i++) {
            int diff = Math.abs((int) get(i) - (int) baseLineY);
            for (int j = 0; j < maximums.length; j++) {
                if (diff > maximums[j]) {
                    boolean ok = true;
                    for (int k = 0; k < indices.length; k++) {
                        if (indices[k] != Integer.MIN_VALUE && Math.abs(indices[k] - i) < 25) {
                            ok = false;
                            break;
                        }
                    }
                    if (ok) {                        
                        for (int k = maximums.length - 1; k > j; k--) {
                            maximums[k] = maximums[k - 1];
                            indices[k] = indices[k - 1];
                        }
                        maximums[j] = diff;
                        indices[j] = i;                        
                        break;
                    }
                }
            }
        }
        highPoints = indices;
    }
}
