/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package heartdiagapp;

import heartdiagapp.core.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;
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
    private final TextField textFieldSampleUrl = new TextField("Heart Sample Path", "", 1024, TextField.URL);
    private final StringItem stringItemStatus = new StringItem("Status", "Choose a data file !");
    private final Alert alert = new Alert("Error", "", null, AlertType.ERROR);
    private boolean isInitialized = false;
    private Form form;

    public void startApp() {
        if (isInitialized) {
            return;
        }
        form = new Form("Heart Diag Application");
        form.append(textFieldSampleUrl);
        form.append(stringItemStatus);
        form.addCommand(exitCommand);
        form.addCommand(chooseCommand);
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
            FileReader fileReader = new FileReader(inputStream);
            String totalString = "";
            Vector lines = fileReader.readLines();
            for (int i = 0; i < lines.size(); i++) {
                String line = (String)lines.elementAt(i);
                totalString += line + "\r\n";
            }
            stringItemStatus.setText(totalString);
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
        }
    }
}
