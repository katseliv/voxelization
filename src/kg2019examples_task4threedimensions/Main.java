/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kg2019examples_task4threedimensions;

import javax.swing.*;
import java.io.File;

/**
 *
 * @author Alexey
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        MainWindow mainWindow = new MainWindow();
        mainWindow.setSize(1000, 1000);
        mainWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainWindow.setLocationRelativeTo(null);
        mainWindow.setVisible(true);

//        FileManager fileManager = new FileManager();
//        fileManager.readFile(new File("src/files/heart.obj"));
    }
}
