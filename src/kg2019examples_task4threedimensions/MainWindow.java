package kg2019examples_task4threedimensions;

import kg2019examples_task4threedimensions.models.Model;
import kg2019examples_task4threedimensions.operations.VoxelOperation;

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
                    VoxelOperation voxelOperation = new VoxelOperation();
                    drawPanel.scene.getModelsList().add(new Model(new File(fileChooserOpen.getSelectedFile().getPath())));
                    System.out.println(fileChooserOpen.getSelectedFile().getPath());
                    if (fileChooserOpen.getSelectedFile().getPath().equals("C:\\Users\\katse\\IdeaProjects\\3_semestr\\KG\\Task_4\\src\\files\\orange.obj")) {
                        drawPanel.scene.getModelsList().addAll(voxelOperation.modelToVoxelOfCubs(new Model(new File(fileChooserOpen.getSelectedFile().getPath())), 0.01f));
                    } else if (fileChooserOpen.getSelectedFile().getPath().equals("C:\\Users\\katse\\IdeaProjects\\3_semestr\\KG\\Task_4\\src\\files\\cup.obj")) {
                        drawPanel.scene.getModelsList().addAll(voxelOperation.modelToVoxelOfCubs(new Model(new File(fileChooserOpen.getSelectedFile().getPath())), 50f));
                    } else if (fileChooserOpen.getSelectedFile().getPath().equals("C:\\Users\\katse\\IdeaProjects\\3_semestr\\KG\\Task_4\\src\\files\\heart.obj")) {
                        drawPanel.scene.getModelsList().addAll(voxelOperation.modelToVoxelOfCubs(new Model(new File(fileChooserOpen.getSelectedFile().getPath())), 20f));
                    } else {
                        drawPanel.scene.getModelsList().addAll(voxelOperation.modelToVoxelOfCubs(new Model(new File(fileChooserOpen.getSelectedFile().getPath())), 200f));
                    }

                        drawPanel.repaint();
                    }
                } catch(Exception e){
                    System.out.println(e.getMessage());
                }
            });
            buttonLoadInputFromFile.setPreferredSize(new Dimension(200, 25));
            buttonsPanel.add(buttonLoadInputFromFile);

//        voxelizate.addActionListener(actionEvent -> {
//            try {
//                drawPanel.voxelizate();
//                drawPanel.repaint();
//            } catch (Exception e) {
//                System.out.println(e.getMessage());
//            }
//        });

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
