package heartdiagapp.ui;

import heartdiagapp.dsp.Signal;
import javax.microedition.lcdui.Graphics;

public class SignalDrawer {

    private int offset;
    private int width;
    private int height;
    private Signal signal;

    public SignalDrawer(Signal signal) {
        this(signal, 100, 100);
    }

    public SignalDrawer(Signal signal, int width, int height) {
        this.signal = signal;
        this.offset = 0;
        this.width = width;
        this.height = height;
        signal.center();
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
        if (this.offset < 0) {
            this.offset = 0;
        }
        if (this.offset + getWidth() > signal.size()) {
            this.offset = signal.size() - getWidth();
        }
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Signal getSignal() {
        return signal;
    }

    public void setSignal(Signal signal) {
        this.signal = signal;
        offset = 0;
    }

    public void draw(Graphics g) {
        g.setColor(255, 255, 255);
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(200, 200, 200);
        int baseLineY;
        if (signal.baseLineY() >= 0) {
            baseLineY = (int) ((signal.baseLineY() / (double) signal.maxAltitude()) * (getHeight() / 2));
        } else {
            baseLineY = (int) ((Math.abs(signal.baseLineY()) / signal.maxAltitude()) * (getHeight() / 2));
            baseLineY = -baseLineY;
        }
        baseLineY = this.getHeight() / 2 - baseLineY;
        g.drawLine(0, baseLineY, getWidth(), baseLineY);

        for (int i = 0; i < signal.highPoints().length; i++) {
            if (signal.highPoints()[i] > offset && signal.highPoints()[i] < offset + getWidth()) {
                int highPointY;
                if (signal.get(signal.highPoints()[i]) >= 0) {
                    highPointY = (int) ((signal.get(signal.highPoints()[i]) / (double) signal.maxAltitude()) * (getHeight() / 2));
                } else {
                    highPointY = (int) ((Math.abs(signal.get(signal.highPoints()[i])) / signal.maxAltitude()) * (getHeight() / 2));
                    highPointY = -highPointY;
                }
                highPointY = this.getHeight() / 2 - highPointY;
                g.setColor(0, 0, 255);
                g.drawLine(signal.highPoints()[i] - offset, highPointY, signal.highPoints()[i] - offset, baseLineY);
                System.out.println(">>>>> " + signal.highPoints()[i] + ", " + highPointY + ", " + baseLineY);
            }
        }
        System.out.println("===============");

        int pixelsPerStep = (int) ((1 / 40.0) / signal.timeBetweenValues());
        int numberOfSteps = signal.size() / pixelsPerStep;
        for (int i = 1; i <= numberOfSteps; i++) {
            if (i * pixelsPerStep > offset && i * pixelsPerStep < offset + getWidth()) {
                g.setColor(249, 230, 213);
                g.drawLine(i * pixelsPerStep - offset, 0, i * pixelsPerStep - offset, getHeight());
            }
        }
        for (int i = 1; i <= getHeight() / pixelsPerStep; i++) {
            g.setColor(249, 230, 213);
            g.drawLine(0, i * pixelsPerStep, getWidth(), i * pixelsPerStep);
        }
        g.setColor(0, 0, 0);
        g.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2);
        for (int i = 1; i <= numberOfSteps / 20; i++) {
            if (i * 20 * pixelsPerStep > offset && i * 20 * pixelsPerStep < offset + getWidth()) {
                g.setColor(0, 0, 0);
                g.fillRect(i * 20 * pixelsPerStep - offset - 2, getHeight() / 2 - 4, 4, 8);
            }
        }
        int lastX = -1;
        int lastY = -1;
        g.setColor(255, 0, 0);
        for (int x = 0; x < getWidth(); x++) {
            int newX = x + offset;
            if (newX >= 0 && newX <= signal.size()) {
                int y;
                if (signal.get(newX) >= 0) {
                    y = (int) ((signal.get(newX) / (double) signal.maxAltitude()) * (getHeight() / 2));
                } else {
                    y = (int) ((Math.abs(signal.get(newX)) / signal.maxAltitude()) * (getHeight() / 2));
                    y = -y;
                }
                y = this.getHeight() / 2 - y;
                if (lastX < 0) {
                    g.fillRect(x, y, 2, 2);
                } else {
                    g.drawLine(lastX, lastY, x, y);
                }
                lastX = x;
                lastY = y;
            }
        }
    }
}
