package heartdiagapp.ui;

import heartdiagapp.dsp.Signal;
import heartdiagapp.dsp.SimpleSignal;
import heartdiagapp.dsp.panandtompkins.DerivativeFilter;
import heartdiagapp.dsp.panandtompkins.HighPassFilter;
import heartdiagapp.dsp.panandtompkins.IntegrationFilter;
import heartdiagapp.dsp.panandtompkins.LowPassFilter;
import heartdiagapp.dsp.panandtompkins.SqueringFilter;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Graphics;

public class SignalCanvas extends Canvas implements CommandListener {
    
    private int pressX = -1;
    private Signal originalSignal;
    private Signal afterLowPassFilterSignal;
    private Signal afterHighPassFilterSignal;
    private Signal afterDerivateFilterSignal;
    private Signal afterSqueringFilterSignal;
    private Signal afterIntegrationFilterSignal;
    private final SignalDrawer signalDrawer;
    private final HeartDiagAppMidlet parent;
    
    private final Command originalSignalCommand = new Command("Original", Command.SCREEN, 6);
    private final Command afterIntegrationFilterSignalCommand = new Command("After Integration Filter", Command.SCREEN, 5);
    private final Command afterSqueringFilterSignalCommand = new Command("After Squering Filter", Command.SCREEN, 4);
    private final Command afterDerivativeFilterSignalCommand = new Command("After Derivative Filter", Command.SCREEN, 3);
    private final Command afterHighPassFilterSignalCommand = new Command("After HighPass Filter", Command.SCREEN, 2);
    private final Command afterLowPassFilterSignalCommand = new Command("After LowPass Filter", Command.SCREEN, 1);
    private final Command backCommand = new Command("Back", Command.EXIT, 2);
    
    public SignalCanvas(HeartDiagAppMidlet parent, Signal signal) {
        this.originalSignal = signal;
        this.signalDrawer = new SignalDrawer(signal, getWidth(), getHeight());
        this.parent = parent;
        addCommand(originalSignalCommand);
        addCommand(afterIntegrationFilterSignalCommand);
        addCommand(afterSqueringFilterSignalCommand);
        addCommand(afterDerivativeFilterSignalCommand);
        addCommand(afterHighPassFilterSignalCommand);
        addCommand(afterLowPassFilterSignalCommand);
        addCommand(backCommand);
        setCommandListener(this);
    }

    protected void keyPressed(int keyCode) {
        int key = getGameAction(keyCode);
        if (key == RIGHT) {
            signalDrawer.setOffset(signalDrawer.getOffset() + 10);
        } else if (key == LEFT) {
            signalDrawer.setOffset(signalDrawer.getOffset() - 10);
        }
        repaint();
        super.keyPressed(keyCode);
    }    

    protected void pointerPressed(int x, int y) {
        pressX = x;        
        repaint();
    }    

    protected void pointerDragged(int x, int y) {
        signalDrawer.setOffset(signalDrawer.getOffset() + (int)((pressX - x) * 0.5));        
        repaint();
    }
    
    protected void paint(Graphics g) {  
        signalDrawer.draw(g);
    }    

    public void commandAction(Command c, Displayable d) {
        if (c == backCommand) {
            parent.displayThis();
        } else if (c == originalSignalCommand) {
            signalDrawer.setSignal(getOriginal());
            repaint();
        } else if (c == afterLowPassFilterSignalCommand) {
            signalDrawer.setSignal(getAfterLowPassFilter());
            repaint();
        } else if (c == afterHighPassFilterSignalCommand) {
            signalDrawer.setSignal(getAfterHighPassFilter());
            repaint();
        } else if (c == afterDerivativeFilterSignalCommand) {
            signalDrawer.setSignal(getAfterDerivativeFilter());
            repaint();
        } else if (c == afterSqueringFilterSignalCommand) {
            signalDrawer.setSignal(getAfterSqueringFilter());
            repaint();
        } else if (c == afterIntegrationFilterSignalCommand) {
            signalDrawer.setSignal(getAfterIntegrationFilter());
            repaint();
        }
    }
    
    private Signal getOriginal() {
        return originalSignal;
    }
    
    private Signal getAfterLowPassFilter() {
        if (afterLowPassFilterSignal == null) {
            afterLowPassFilterSignal = new LowPassFilter().apply(originalSignal);
            ((SimpleSignal)afterLowPassFilterSignal).calculateMaxAltitude();
            ((SimpleSignal)afterLowPassFilterSignal).calculateHighPoints();
        }
        return afterLowPassFilterSignal;
    }
    
    private Signal getAfterHighPassFilter() {
        if (afterHighPassFilterSignal == null) {
            afterHighPassFilterSignal = new HighPassFilter().apply(getAfterLowPassFilter());
            ((SimpleSignal)afterHighPassFilterSignal).calculateMaxAltitude();
            ((SimpleSignal)afterHighPassFilterSignal).calculateHighPoints();
        }
        return afterHighPassFilterSignal;
    }
    
    private Signal getAfterDerivativeFilter() {
        if (afterDerivateFilterSignal == null) {
            afterDerivateFilterSignal = new DerivativeFilter().apply(getAfterHighPassFilter());
            ((SimpleSignal)afterDerivateFilterSignal).calculateMaxAltitude();
            ((SimpleSignal)afterDerivateFilterSignal).calculateHighPoints();
        }
        return afterDerivateFilterSignal;
    }
    
    private Signal getAfterSqueringFilter() {
        if (afterSqueringFilterSignal == null) {
            afterSqueringFilterSignal = new SqueringFilter().apply(getAfterDerivativeFilter());
            ((SimpleSignal)afterSqueringFilterSignal).calculateMaxAltitude();
            ((SimpleSignal)afterSqueringFilterSignal).calculateHighPoints();
        }
        return afterSqueringFilterSignal;
    }
    
    private Signal getAfterIntegrationFilter() {
        if (afterIntegrationFilterSignal == null) {
            afterIntegrationFilterSignal = new IntegrationFilter().apply(getAfterSqueringFilter());
            ((SimpleSignal)afterIntegrationFilterSignal).calculateMaxAltitude();
            ((SimpleSignal)afterIntegrationFilterSignal).calculateHighPoints();
        }
        return afterIntegrationFilterSignal;
    }
}
