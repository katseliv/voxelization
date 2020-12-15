import math.Vector3;
import models.*;
import operations.VoxelOperation;
import third.IModel;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;

public class MainWindow extends JFrame {
    private DrawPanel drawPanel;
    private JPanel buttonsPanel;
    private JComponent model;
    private JComponent surface;

    private final JLabel label1 = new JLabel();
    private final JLabel label2 = new JLabel();
    private final JLabel label3 = new JLabel();
    private final JLabel label4 = new JLabel();
    private final JLabel label5 = new JLabel();

    private final JTextField textField1 = new JTextField();
    private final JTextField textField2 = new JTextField();
    private final JTextField textField3 = new JTextField();
    private final JTextField fieldX = new JTextField();
    private final JTextField fieldY = new JTextField();
    private final JTextField fieldZ = new JTextField();
    private final JTextField textField5 = new JTextField();

    private JTextField textFieldWidth;
    private JTextField textFieldLength;
    private JTextField fieldXStartPoint;
    private JTextField fieldYStartPoint;
    private JTextField fieldZStartPoint;

    private final JButton buttonLoadInputFromFile = new JButton("Load File");
    private final JButton voxelizate = new JButton("Voxelizate");
    private final JButton clear = new JButton("Clear");

    private final JFileChooser fileChooserOpen = new JFileChooser();
    private static final int WIDTH_OF_PANEL = 85;
    private static final int WIDTH_COORDINATE_PANEL = 25;
    private static final Font FONT = new Font(Font.SERIF, Font.ITALIC, 20);

    public MainWindow() throws HeadlessException {
        super("Voxelization");
        panel();
    }

    private void panel() {
        drawPanel = new DrawPanel();
        this.add(drawPanel);

        setButtonsPanel();
        this.add(buttonsPanel, BorderLayout.WEST);

        JTabbedPane tabbedPane = new JTabbedPane();
        model = new JPanel();
        tabbedPane.addTab("Model", model);

        JRadioButton sphere = new JRadioButton("Sphere", true);
        sphere.setPreferredSize(new Dimension(300, 25));
        sphere.addActionListener(e -> {
            clear();
            setStartPanel();
            repaint();
        });
        sphere.setFont(FONT);
        model.add(sphere);

        JRadioButton pyramid = new JRadioButton("Pyramid", false);
        pyramid.setPreferredSize(new Dimension(300, 25));
        pyramid.addActionListener(e -> {
            clear();
            setPanelForPyramidOrCylinder();
            repaint();
        });
        pyramid.setFont(FONT);
        model.add(pyramid);

        JRadioButton cylinder = new JRadioButton("Cylinder", false);
        cylinder.setPreferredSize(new Dimension(300, 25));
        cylinder.addActionListener(e -> {
            clear();
            setPanelForPyramidOrCylinder();
            repaint();
        });
        cylinder.setFont(FONT);
        model.add(cylinder);

        JRadioButton cube = new JRadioButton("Cube", false);
        cube.setPreferredSize(new Dimension(300, 25));
        cube.addActionListener(e -> {
            clear();
            setCubePanel();
            repaint();
        });
        cube.setFont(FONT);
        model.add(cube);

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(cylinder);
        buttonGroup.add(pyramid);
        buttonGroup.add(sphere);
        buttonGroup.add(cube);
        setStartPanel();

        surface = new JPanel();
        tabbedPane.addTab("Surface", surface);
        tabbedPane.setPreferredSize(new Dimension(400, 700));
        tabbedPane.setFont(FONT);
        setSurfacePanel();
        buttonsPanel.add(tabbedPane);

        JButton addIModel = new JButton("Add");
        addIModel.setPreferredSize(new Dimension(300, 25));
        addIModel.addActionListener(e -> {
            float a;
            float b;
            float x;
            float y;
            float z;
            int approximate;

            IModel model = null;
            if (sphere.isSelected() && !surface.isShowing()) {
                a = Float.parseFloat(textField1.getText());
                b = Float.parseFloat(textField2.getText());
                float c = Float.parseFloat(textField3.getText());

                x = Float.parseFloat(fieldX.getText());
                y = Float.parseFloat(fieldY.getText());
                z = Float.parseFloat(fieldZ.getText());

                approximate = Integer.parseInt(textField5.getText());
                model = new Sphere(a, b, c, approximate, new Vector3(x, y, z));
            } else if (pyramid.isSelected() && !cylinder.isSelected() && !surface.isShowing()) {
                a = Float.parseFloat(textField1.getText());
                b = Float.parseFloat(textField2.getText());

                x = Float.parseFloat(fieldX.getText());
                y = Float.parseFloat(fieldY.getText());
                z = Float.parseFloat(fieldZ.getText());

                approximate = Integer.parseInt(textField5.getText());
                model = new Pyramid(a, b, approximate, new Vector3(x, y, z));
            } else if (cylinder.isSelected() && !pyramid.isSelected() && !surface.isShowing()) {
                a = Float.parseFloat(textField1.getText());
                b = Float.parseFloat(textField2.getText());

                x = Float.parseFloat(fieldX.getText());
                y = Float.parseFloat(fieldY.getText());
                z = Float.parseFloat(fieldZ.getText());

                approximate = Integer.parseInt(textField5.getText());
                model = new Cylinder(a, b, approximate, new Vector3(x, y, z));
            } else if (cube.isSelected() && !surface.isShowing()) {
                b = Float.parseFloat(textField2.getText());

                x = Float.parseFloat(fieldX.getText());
                y = Float.parseFloat(fieldY.getText());
                z = Float.parseFloat(fieldZ.getText());

                model = new Parallelepiped(b, new Vector3(x, y, z));
            } else if (surface.isShowing()) {
                int length = Integer.parseInt(textFieldLength.getText());
                int width = Integer.parseInt(textFieldWidth.getText());

                x = Float.parseFloat(fieldXStartPoint.getText());
                y = Float.parseFloat(fieldYStartPoint.getText());
                z = Float.parseFloat(fieldZStartPoint.getText());

                model = new Surface(length, width, new Vector3(x, y, z));
            }

            if (model != null)
                drawPanel.scene.getModelsList().add(model);
            drawPanel.repaint();
        });
        addIModel.setFont(FONT);
        buttonsPanel.add(addIModel);
    }

    private void setButtonsPanel() {
        buttonsPanel = new JPanel();
        buttonsPanel.setPreferredSize(new Dimension(500, getHeight()));

        setFileChooserOpen();
        buttonLoadInputFromFile.setPreferredSize(new Dimension(300, 25));
        buttonLoadInputFromFile.setFont(FONT);
        buttonsPanel.add(buttonLoadInputFromFile);

        voxelizate.setPreferredSize(new Dimension(300, 25));
        voxelizate.addActionListener(actionEvent -> {
            try {
                drawPanel.voxelizate();
                drawPanel.repaint();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });
        voxelizate.setFont(FONT);
        buttonsPanel.add(voxelizate);

        clear.setPreferredSize(new Dimension(300, 25));
        clear.addActionListener(actionEvent -> {
            try {
                drawPanel.scene.getModelsList().clear();
                drawPanel.repaint();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });
        clear.setFont(FONT);
        buttonsPanel.add(clear);
    }

    private void setFileChooserOpen() {
        fileChooserOpen.setCurrentDirectory(new File("."));

        FileFilter filter = new FileNameExtensionFilter("Text files", "txt");
        fileChooserOpen.addChoosableFileFilter(filter);

        buttonLoadInputFromFile.addActionListener(actionEvent -> {
            try {
                if (fileChooserOpen.showOpenDialog(drawPanel) == JFileChooser.APPROVE_OPTION) {
                    VoxelOperation voxelOperation = new VoxelOperation();
                    drawPanel.scene.getModelsList().add(new Model(new File(fileChooserOpen.getSelectedFile().getPath())));
                    if (fileChooserOpen.getSelectedFile().getPath().equals("C:\\Users\\katse\\IdeaProjects\\3_semestr\\KG\\Task_4\\files\\orange.obj")) {
                        drawPanel.scene.getModelsList().addAll(voxelOperation.modelToVoxelOfCubs(new Model(new File(fileChooserOpen.getSelectedFile().getPath())), 0.01f));
                    } else if (fileChooserOpen.getSelectedFile().getPath().equals("C:\\Users\\katse\\IdeaProjects\\3_semestr\\KG\\Task_4\\files\\cup.obj")) {
                        drawPanel.scene.getModelsList().addAll(voxelOperation.modelToVoxelOfCubs(new Model(new File(fileChooserOpen.getSelectedFile().getPath())), 40f));
                    } else if (fileChooserOpen.getSelectedFile().getPath().equals("C:\\Users\\katse\\IdeaProjects\\3_semestr\\KG\\Task_4\\files\\heart.obj")) {
                        drawPanel.scene.getModelsList().addAll(voxelOperation.modelToVoxelOfCubs(new Model(new File(fileChooserOpen.getSelectedFile().getPath())), 5f));
                    }
                    drawPanel.scene.getModelsList().add(new Model(new File(fileChooserOpen.getSelectedFile().getPath())));
                    drawPanel.repaint();
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });
        buttonLoadInputFromFile.setPreferredSize(new Dimension(200, 25));
        buttonLoadInputFromFile.setFont(FONT);
    }

    private void setStartPanel() {
        label1.setPreferredSize(new Dimension(200, 25));
        label1.setFont(FONT);
        label1.setText("A");
        textField1.setPreferredSize(new Dimension(WIDTH_OF_PANEL, 25));
        model.add(label1);
        model.add(textField1);

        label2.setPreferredSize(new Dimension(200, 25));
        label2.setFont(FONT);
        label2.setText("B");
        textField2.setPreferredSize(new Dimension(WIDTH_OF_PANEL, 25));
        model.add(label2);
        model.add(textField2);

        label3.setPreferredSize(new Dimension(200, 25));
        label3.setFont(FONT);
        label3.setText("C");
        textField3.setPreferredSize(new Dimension(WIDTH_OF_PANEL, 25));
        model.add(label3);
        model.add(textField3);

        label4.setPreferredSize(new Dimension(200, 25));
        label4.setFont(FONT);
        label4.setText("Center");
        fieldX.setPreferredSize(new Dimension(WIDTH_COORDINATE_PANEL, 25));
        fieldY.setPreferredSize(new Dimension(WIDTH_COORDINATE_PANEL, 25));
        fieldZ.setPreferredSize(new Dimension(WIDTH_COORDINATE_PANEL, 25));
        model.add(label4);
        model.add(fieldX);
        model.add(fieldY);
        model.add(fieldZ);

        label5.setPreferredSize(new Dimension(200, 25));
        label5.setFont(FONT);
        label5.setText("Approximate");
        textField5.setPreferredSize(new Dimension(WIDTH_OF_PANEL, 25));
        model.add(label5);
        model.add(textField5);
    }

    private void setPanelForPyramidOrCylinder() {
        label1.setPreferredSize(new Dimension(200, 25));
        label1.setFont(FONT);
        label1.setText("Height");
        textField1.setPreferredSize(new Dimension(WIDTH_OF_PANEL, 25));
        model.add(label1);
        model.add(textField1);

        label2.setPreferredSize(new Dimension(200, 25));
        label2.setFont(FONT);
        label2.setText("Radius");
        textField2.setPreferredSize(new Dimension(WIDTH_OF_PANEL, 25));
        model.add(label2);
        model.add(textField2);

        label4.setPreferredSize(new Dimension(200, 25));
        label4.setFont(FONT);
        label4.setText("Center");
        fieldX.setPreferredSize(new Dimension(WIDTH_COORDINATE_PANEL, 25));
        fieldY.setPreferredSize(new Dimension(WIDTH_COORDINATE_PANEL, 25));
        fieldZ.setPreferredSize(new Dimension(WIDTH_COORDINATE_PANEL, 25));
        model.add(label4);
        model.add(fieldX);
        model.add(fieldY);
        model.add(fieldZ);

        label5.setPreferredSize(new Dimension(200, 25));
        label5.setFont(FONT);
        label5.setText("Approximate");
        textField5.setPreferredSize(new Dimension(WIDTH_OF_PANEL, 25));
        model.add(label5);
        model.add(textField5);
    }

    private void setCubePanel() {
        label2.setPreferredSize(new Dimension(200, 25));
        label2.setFont(FONT);
        label2.setText("Radius");
        textField2.setPreferredSize(new Dimension(WIDTH_OF_PANEL, 25));
        model.add(label2);
        model.add(textField2);

        label4.setPreferredSize(new Dimension(200, 25));
        label4.setFont(FONT);
        label4.setText("Center");
        fieldX.setPreferredSize(new Dimension(WIDTH_COORDINATE_PANEL, 25));
        fieldY.setPreferredSize(new Dimension(WIDTH_COORDINATE_PANEL, 25));
        fieldZ.setPreferredSize(new Dimension(WIDTH_COORDINATE_PANEL, 25));
        model.add(label4);
        model.add(fieldX);
        model.add(fieldY);
        model.add(fieldZ);
    }

    private void setSurfacePanel() {
        JLabel labelLength = new JLabel("Height");
        labelLength.setPreferredSize(new Dimension(200, 25));
        labelLength.setFont(FONT);

        textFieldLength = new JTextField();
        textFieldLength.setPreferredSize(new Dimension(WIDTH_OF_PANEL, 25));
        surface.add(labelLength);
        surface.add(textFieldLength);

        JLabel labelWidth = new JLabel("Width");
        labelWidth.setPreferredSize(new Dimension(200, 25));
        labelWidth.setFont(FONT);

        textFieldWidth = new JTextField();
        textFieldWidth.setPreferredSize(new Dimension(WIDTH_OF_PANEL, 25));
        surface.add(labelWidth);
        surface.add(textFieldWidth);

        JLabel labelStartPoint = new JLabel("Start");
        labelStartPoint.setPreferredSize(new Dimension(200, 25));
        labelStartPoint.setFont(FONT);

        fieldXStartPoint = new JTextField();
        fieldYStartPoint = new JTextField();
        fieldZStartPoint = new JTextField();
        fieldXStartPoint.setPreferredSize(new Dimension(WIDTH_COORDINATE_PANEL, 25));
        fieldYStartPoint.setPreferredSize(new Dimension(WIDTH_COORDINATE_PANEL, 25));
        fieldZStartPoint.setPreferredSize(new Dimension(WIDTH_COORDINATE_PANEL, 25));
        surface.add(labelStartPoint);
        surface.add(fieldXStartPoint);
        surface.add(fieldYStartPoint);
        surface.add(fieldZStartPoint);
    }

    private void clear() {
        model.remove(label1);
        model.remove(label2);
        model.remove(label3);
        model.remove(label4);
        model.remove(label5);

        model.remove(textField1);
        model.remove(textField2);
        model.remove(textField3);
        model.remove(fieldXStartPoint);
        model.remove(textField5);
    }
}
