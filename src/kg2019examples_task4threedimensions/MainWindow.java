package kg2019examples_task4threedimensions;

import kg2019examples_task4threedimensions.math.Vector3;
import kg2019examples_task4threedimensions.models.*;
import kg2019examples_task4threedimensions.operations.VoxelOperation;
import kg2019examples_task4threedimensions.third.IModel;

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
        sphere.setFont(FONT);
        sphere.addActionListener(e -> {
            clear();
            setStartPanel();
            repaint();
        });
        model.add(sphere);

        JRadioButton pyramid = new JRadioButton("Pyramid", false);
        pyramid.setPreferredSize(new Dimension(300, 25));
        pyramid.setFont(FONT);
        pyramid.addActionListener(e -> {
            clear();
            setPanelForPyramidOrCylinder();
            repaint();
        });
        model.add(pyramid);

        JRadioButton cylinder = new JRadioButton("Cylinder", false);
        cylinder.setPreferredSize(new Dimension(300, 25));
        cylinder.setFont(FONT);
        cylinder.addActionListener(e -> {
            clear();
            setPanelForPyramidOrCylinder();
            repaint();
        });
        model.add(cylinder);

        JRadioButton cube = new JRadioButton("Cube", false);
        cube.setPreferredSize(new Dimension(300, 25));
        cube.setFont(FONT);
        cube.addActionListener(e -> {
            clear();
            setCubePanel();
            repaint();
        });
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
        addIModel.setFont(FONT);
        addIModel.addActionListener(e -> {
            float a;
            float b;
            float x = Float.parseFloat(fieldX.getText());
            float y = Float.parseFloat(fieldY.getText());
            float z = Float.parseFloat(fieldZ.getText());
            int approximate;

            IModel model = null;
            if (sphere.isSelected()) {
                a = Float.parseFloat(textField1.getText());
                b = Float.parseFloat(textField2.getText());
                float c = Float.parseFloat(textField3.getText());
                approximate = Integer.parseInt(textField5.getText());
                model = new Sphere(a, b, c, approximate, new Vector3(x, y, z));
            } else if (pyramid.isSelected()) {
                a = Float.parseFloat(textField1.getText());
                b = Float.parseFloat(textField2.getText());
                approximate = Integer.parseInt(textField5.getText());
                model = new Pyramid(a, b, approximate, new Vector3(x, y, z));
            } else if (cylinder.isSelected()) {
                a = Float.parseFloat(textField1.getText());
                b = Float.parseFloat(textField2.getText());
                approximate = Integer.parseInt(textField5.getText());
                model = new Cylinder(a, b, approximate, new Vector3(x, y, z));
            } else if (cube.isSelected()) {
                b = Float.parseFloat(textField2.getText());
                model = new Parallelepiped(b, new Vector3(x, y, z));
            }

            if (model != null)
                drawPanel.scene.getModelsList().add(model);
            drawPanel.repaint();
        });
        buttonsPanel.add(addIModel);
    }

    private void setButtonsPanel() {
        buttonsPanel = new JPanel();
        buttonsPanel.setPreferredSize(new Dimension(500, getHeight()));

        setFileChooserOpen();
        buttonLoadInputFromFile.setPreferredSize(new Dimension(300, 25));
        buttonLoadInputFromFile.setFont(FONT);
        buttonsPanel.add(buttonLoadInputFromFile);

        voxelizate.addActionListener(actionEvent -> {
            try {
                drawPanel.voxelizate();
                drawPanel.repaint();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });

        voxelizate.setPreferredSize(new Dimension(300, 25));
        voxelizate.setFont(FONT);
        buttonsPanel.add(voxelizate);

        clear.addActionListener(actionEvent -> {
            try {
                drawPanel.scene.getModelsList().clear();
                drawPanel.repaint();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });

        clear.setPreferredSize(new Dimension(300, 25));
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

    private void setSurfacePanel(){
        Label label1 = new Label("Height");
        label1.setPreferredSize(new Dimension(200, 25));
        label1.setFont(FONT);

        TextField textField1 = new TextField();
        textField1.setPreferredSize(new Dimension(WIDTH_OF_PANEL, 25));
        surface.add(label1);
        surface.add(textField1);

        Label label2 = new Label("Width");
        label2.setPreferredSize(new Dimension(200, 25));
        label2.setFont(FONT);

        TextField textField2 = new TextField();
        textField2.setPreferredSize(new Dimension(WIDTH_OF_PANEL, 25));
        surface.add(label2);
        surface.add(textField2);

        Label label4 = new Label("Start");
        label4.setPreferredSize(new Dimension(200, 25));
        label4.setFont(FONT);

        TextField fieldX = new TextField();
        TextField fieldY = new TextField();
        TextField fieldZ = new TextField();
        fieldX.setPreferredSize(new Dimension(WIDTH_COORDINATE_PANEL, 25));
        fieldY.setPreferredSize(new Dimension(WIDTH_COORDINATE_PANEL, 25));
        fieldZ.setPreferredSize(new Dimension(WIDTH_COORDINATE_PANEL, 25));
        surface.add(label4);
        surface.add(fieldX);
        surface.add(fieldY);
        surface.add(fieldZ);
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
        model.remove(fieldX);
        model.remove(textField5);
    }
}
