package main.java.com.mycompany.sigeliapp.modelos.utiles;

import java.awt.AWTException;
import java.awt.HeadlessException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Portapapeles {
    
    public static void copiar(String text){
        Clipboard clipboard = getSystemClipboard();

        clipboard.setContents(new StringSelection(text), null);
    }

    public static void pegar(){
        
        Robot robot;
        try {
            robot = new Robot();
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_CONTROL);
            robot.keyRelease(KeyEvent.VK_V);
        } catch (AWTException ex) {
            Logger.getLogger(Portapapeles.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static String get() throws Exception
    {
        Clipboard systemClipboard = getSystemClipboard();
        DataFlavor dataFlavor = DataFlavor.stringFlavor;

        if (systemClipboard.isDataFlavorAvailable(dataFlavor))
        {
            Object text = systemClipboard.getData(dataFlavor);
            return (String) text;
        }

        return null;
    }

    private static Clipboard getSystemClipboard()
    {
        Toolkit defaultToolkit = Toolkit.getDefaultToolkit();
        Clipboard systemClipboard = defaultToolkit.getSystemClipboard();

        return systemClipboard;
    }
}
