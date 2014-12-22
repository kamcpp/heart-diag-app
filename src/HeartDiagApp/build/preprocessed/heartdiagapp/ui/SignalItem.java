package heartdiagapp.ui;

import heartdiagapp.dsp.Signal;
import javax.microedition.lcdui.CustomItem;
import javax.microedition.lcdui.Graphics;

public class SignalItem extends CustomItem {

    private int pressX = -1;
    private SignalDrawer signalDrawer;
    
    public SignalItem(String text) {
        super(text);     
    }
    
    public void setSignal(Signal signal) {
        signalDrawer = new SignalDrawer(signal, getPreferredWidth(), getPreferredHeight());
        repaint();
    }
    
    protected int getMinContentHeight() {
        return 100;
    }

    protected int getMinContentWidth() {
        return 100;
    }

    protected int getPrefContentHeight(int width) {
        return width;
    }

    protected int getPrefContentWidth(int height) {
        return height;
    }
    
    protected void keyPressed(int keyCode) {
        if(signalDrawer == null) {
            return;
        }
        int key = getGameAction(keyCode);
        if (key == Graphics.RIGHT) {
            signalDrawer.setOffset(signalDrawer.getOffset() + 10);
        } else if (key == Graphics.LEFT) {
            signalDrawer.setOffset(signalDrawer.getOffset() - 10);
        }
        repaint();
        super.keyPressed(keyCode);
    }    

    protected void pointerPressed(int x, int y) {
        if(signalDrawer == null) {
            return;
        }
        pressX = x;
        repaint();
    }    

    protected void pointerDragged(int x, int y) {
        if(signalDrawer == null) {
            return;
        }
        signalDrawer.setOffset(signalDrawer.getOffset() + (int)((pressX - x) * 0.5));        
        repaint();
    }

    protected void paint(Graphics g, int w, int h) {
        if(signalDrawer == null) {
            return;
        }
        signalDrawer.setWidth(w);
        signalDrawer.setHeight(h);
        signalDrawer.draw(g);
    }
}
