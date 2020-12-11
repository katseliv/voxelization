package kg2019examples_task4threedimensions;

import kg2019examples_task4threedimensions.models.Model;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;

public class MainWindow extends JFrame {

    private DrawPanel drawPanel;
    private JPanel buttonsPanel;

    private final JButton buttonLoadInputFromFile = new JButton("Load File");
    private final JButton voxelizate = new JButton("Voxelizate");
    private final JButton clear = new JButton("Clear");

    private JFileChooser fileChooserOpen;

    public MainWindow() throws HeadlessException {
        super("Voxelization");

        panel();
        this.add(drawPanel);
        this.add(buttonsPanel, BorderLayout.NORTH);
    }

    private void panel() {
        drawPanel = new DrawPanel();
        buttonsPanel = new JPanel();

        fileChooserOpen = new JFileChooser();

        fileChooserOpen.setCurrentDirectory(new File("."));

        FileFilter filter = new FileNameExtensionFilter("Text files", "txt");
        fileChooserOpen.addChoosableFileFilter(filter);

        buttonLoadInputFromFile.addActionListener(actionEvent -> {
            try {
                if (fileChooserOpen.showOpenDialog(drawPanel) == JFileChooser.APPROVE_OPTION) {
                    drawPanel.scene.getModelsList().add(new Model(new File(fileChooserOpen.getSelectedFile().getPath())));
                    drawPanel.repaint();
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });
        buttonLoadInputFromFile.setPreferredSize(new Dimension(200, 25));
        buttonsPanel.add(buttonLoadInputFromFile);

        voxelizate.addActionListener(actionEvent -> {
//            try {
                drawPanel.voxelizate();
                drawPanel.repaint();
//            } catch (Exception e) {
//                System.out.println(e.getMessage());
//            }
        });

        voxelizate.setPreferredSize(new Dimension(200, 25));
        buttonsPanel.add(voxelizate);

        clear.addActionListener(actionEvent -> {
            try {
                drawPanel.scene.getModelsList().clear();
                drawPanel.repaint();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });

        clear.setPreferredSize(new Dimension(200, 25));
        buttonsPanel.add(clear);
    }
}
