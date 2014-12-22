/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package heartdiagapp.ui;

import heartdiagapp.dsp.Signal;
import heartdiagapp.dsp.SignalFactory;
import heartdiagapp.dsp.SimpleSignal;
import heartdiagapp.dsp.panandtompkins.PanAndTimpkins;
import java.io.IOException;
import java.io.InputStream;
import javax.microedition.io.Connector;
import javax.microedition.io.file.FileConnection;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.StringItem;
import javax.microedition.lcdui.TextField;
import javax.microedition.midlet.*;

public class HeartDiagAppMidlet extends MIDlet implements CommandListener {

    private final Command exitCommand = new Command("Exit", Command.EXIT, 2);
    private final Command chooseCommand = new Command("Choose", Command.SCREEN, 1);
    private final Command drawCommand = new Command("Draw", Command.SCREEN, 2);
    private final TextField textFieldSampleUrl = new TextField("Heart Sample Path", "", 1024, TextField.URL);
    private final StringItem stringItemStatus = new StringItem("", "Choose a data file !");
    private final SignalItem signalItem = new SignalItem("SIGNAL");
    private final Alert alert = new Alert("Error", "", null, AlertType.ERROR);
    private boolean isInitialized = false;
    private Form form;
    private Signal currentSignal;

    public void startApp() {
        if (isInitialized) {
            return;
        }
        
        form = new Form("Heart Diag Application");
        form.append(textFieldSampleUrl);
        form.append(stringItemStatus);
        signalItem.setPreferredSize(form.getWidth(), form.getHeight() - textFieldSampleUrl.getPreferredHeight() - stringItemStatus.getPreferredHeight());
        form.append(signalItem);
        form.addCommand(exitCommand);
        form.addCommand(chooseCommand);
        form.addCommand(drawCommand);
        form.setCommandListener(this);
        Display.getDisplay(this).setCurrent(form);
        alert.addCommand(new Command("Back", Command.SCREEN, 1));
        isInitialized = true;
    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
    }

    public void setSelectedFile(String selectedFile) {
        textFieldSampleUrl.setString(selectedFile);
    }

    public void displayThis() {
        Display.getDisplay(this).setCurrent(form);
    }

    public void displayAndCalculate() {
        try {            
            Display.getDisplay(this).setCurrent(form);
            FileConnection fileConnection = (FileConnection) Connector.open(textFieldSampleUrl.getString(), Connector.READ);
            InputStream inputStream = fileConnection.openInputStream();
            currentSignal = SignalFactory.fromStream(inputStream);
            ((SimpleSignal) currentSignal).calculateMaxAltitude();
            ((SimpleSignal) currentSignal).calculateHighPoints();
            PanAndTimpkins panAndTimpkins = new PanAndTimpkins();
            panAndTimpkins.analyze(currentSignal);
            String diseaseName;
            if (panAndTimpkins.getDistanceTimeAverage() < 0.6) {
                diseaseName = "Sinus Tachycardia";
            } else if (panAndTimpkins.getDistanceTimeAverage() > 1.0) {
                diseaseName = "Sinus Bradicardia";
            } else if (panAndTimpkins.getDistanceTimeAverage() >= 0.6 && panAndTimpkins.getDistanceTimeAverage() <= 1.0) {
                diseaseName = "HEALTHY - No Disease!";
            } else {
                diseaseName = "Atrial Fibrillation";
            }
            stringItemStatus.setText("Disease: " + diseaseName);
            signalItem.setSignal(currentSignal);
        } catch (IOException e) {
        }
    }

    public void commandAction(Command c, Displayable d) {
        if (c == exitCommand) {
            destroyApp(false);
            notifyDestroyed();
        } else if (c == chooseCommand) {
            FileSelector f = new FileSelector(this);
            Display.getDisplay(this).setCurrent(f);
        } else if (c == drawCommand) {
            Displayable displayable = new SignalCanvas(this, currentSignal);
            Display.getDisplay(this).setCurrent(displayable);            
        }
    }
}
