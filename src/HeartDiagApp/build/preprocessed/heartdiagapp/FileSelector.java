package heartdiagapp;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Vector;
import javax.microedition.io.Connector;
import javax.microedition.io.file.FileConnection;
import javax.microedition.io.file.FileSystemListener;
import javax.microedition.io.file.FileSystemRegistry;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.List;

public final class FileSelector extends List implements CommandListener, FileSystemListener {

    private final HeartDiagAppMidlet heartDiagApp;

    private final static String FILE_SEPARATOR = "/";
    private final static String UPPER_DIR = "..";

    private final Command selectCommand = new Command("Select", Command.OK, 1);
    private final Command backCommand = new Command("Back", Command.EXIT, 2);
    private final Vector rootsList = new Vector();
    private FileConnection currentRoot = null;

    FileSelector(HeartDiagAppMidlet heartDiagApp) {
        super("File Browser", List.IMPLICIT);
        this.heartDiagApp = heartDiagApp;
        deleteAll();
        addCommand(selectCommand);
        addCommand(backCommand);
        setSelectCommand(selectCommand);
        setCommandListener(this);
        FileSystemRegistry.addFileSystemListener(FileSelector.this);
        execute();
    }

    public void execute() {
        String initDir = "file:///cv/";
        loadRoots();
        System.out.println("Init dir is NOT null.");
        try {
            currentRoot = (FileConnection) Connector.open(initDir, Connector.READ);
            displayCurrentRoot();
        } catch (IOException e) {
            System.out.println("Init dir is NOT null but EXCEPTION is thrown.");
            displayAllRoots();
        }
    }

    private void loadRoots() {
        if (!rootsList.isEmpty()) {
            rootsList.removeAllElements();
        }
        try {
            Enumeration roots = FileSystemRegistry.listRoots();
            while (roots.hasMoreElements()) {
                String r = (String) roots.nextElement();
                rootsList.addElement(FILE_SEPARATOR + r);
            }
        } catch (Throwable e) {
        }
    }

    private void displayCurrentRoot() {
        try {
            setTitle(currentRoot.getURL());
            deleteAll();
            append(UPPER_DIR, null);
            Enumeration listOfDirs = currentRoot.list("*", false);
            while (listOfDirs.hasMoreElements()) {
                String currentDir = (String) listOfDirs.nextElement();
                if (currentDir.endsWith(FILE_SEPARATOR)) {
                    append(currentDir, null);
                } else {
                    append(currentDir, null);
                }
            }
        } catch (IOException e) {
        } catch (SecurityException e) {
        }
    }

    private void displayAllRoots() {
        setTitle("[Roots]");
        deleteAll();
        Enumeration roots = rootsList.elements();
        while (roots.hasMoreElements()) {
            String root = (String) roots.nextElement();
        }
        currentRoot = null;
    }

    private void openSelected() {
        int selectedIndex = getSelectedIndex();
        if (selectedIndex >= 0) {
            String selectedFile = getString(selectedIndex);
            if (selectedFile.endsWith(FILE_SEPARATOR)) {
                try {
                    if (currentRoot == null) {
                        currentRoot = (FileConnection) Connector.open("file:///" + selectedFile, Connector.READ);
                    } else {
                        currentRoot.setFileConnection(selectedFile);
                    }
                    displayCurrentRoot();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                } catch (SecurityException e) {
                    System.out.println(e.getMessage());
                }
            } else if (selectedFile.equals(UPPER_DIR)) {
                if (rootsList.contains(currentRoot.getPath() + currentRoot.getName())) {
                    displayAllRoots();
                } else {
                    try {
                        currentRoot = (FileConnection) Connector.open("file://" + currentRoot.getPath(), Connector.READ);
                        displayCurrentRoot();
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                }
            } else {
                String url = currentRoot.getURL() + selectedFile;
                heartDiagApp.setSelectedFile(url);
                heartDiagApp.displayAndCalculate();
            }
        }
    }

    public void stop() {
        if (currentRoot != null) {
            try {
                currentRoot.close();
            } catch (IOException e) {
            }
        }
    }

    public void commandAction(Command c, Displayable d) {
        if (c == backCommand) {
            heartDiagApp.displayThis();
            return;
        }
        if (c == selectCommand) {
            openSelected();
        }
    }

    public void rootChanged(int state, String rootNmae) {
    }
}
