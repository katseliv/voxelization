/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kg2019examples_task4threedimensions;

import kg2019examples_task4threedimensions.draw.IDrawer;
import kg2019examples_task4threedimensions.draw.PolygonDrawer;
import kg2019examples_task4threedimensions.draw.SimpleEdgeDrawer;
import kg2019examples_task4threedimensions.math.Vector3;
import kg2019examples_task4threedimensions.models.Cylinder;
import kg2019examples_task4threedimensions.models.Parallelepiped;
import kg2019examples_task4threedimensions.models.Pyramid;
import kg2019examples_task4threedimensions.models.Sphere;
import kg2019examples_task4threedimensions.screen.ScreenConverter;
import kg2019examples_task4threedimensions.third.*;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Alexey
 */
public class DrawPanel extends JPanel implements CameraController.RepaintListener {
    public final Scene scene = new Scene(Color.BLACK.getRGB());
    private final VoxelOperation voxelOperation = new VoxelOperation();
    private final ScreenConverter screenConverter;
    private final Camera camera;

    public DrawPanel() {
        super();
        screenConverter = new ScreenConverter(-100, 100, 200, 200, 100, 100);
        camera = new Camera();
        CameraController cameraController = new CameraController(camera, screenConverter);
        scene.showAxes();

//        scene.getModelsList().add(new Parallelepiped(
//                new Vector3(-40f, -40f, -40f),
//                new Vector3(0f, 0f, 0f)
//        ));

        scene.getModelsList().add(new Parallelepiped(new Vector3(0, 0, 0), 10));
        scene.getModelsList().addAll(voxelOperation.modelToVoxelOfCubs(new Parallelepiped(new Vector3(0, 0, 0), 10), 1f));

        scene.getModelsList().add(new Sphere(new Vector3(0,0,400)));
        //scene.getModelsList().addAll(voxelOperation.modelToVoxelOfCubs(new Sphere(new Vector3(0,0,400)), 50f));
//        scene.getModelsList().add(new Pyramid(new Vector3(0,-600,0)));
//        scene.getModelsList().addAll(voxelOperation.modelToVoxelOfCubs(new Pyramid(new Vector3(0,-600,0)), 10f));
        //scene.getModelsList().add(new Cylinder(new Vector3(0,800,0)));

        cameraController.addRepaintListener(this);
        addMouseListener(cameraController);
        addMouseMotionListener(cameraController);
        addMouseWheelListener(cameraController);
    }

    @Override
    public void paint(Graphics g) {
        screenConverter.setScreenSize(getWidth(), getHeight());
        BufferedImage bi = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = (Graphics2D) bi.getGraphics();
        IDrawer dr = new SimpleEdgeDrawer(screenConverter, graphics);
        IDrawer drawerPolygons = new PolygonDrawer(screenConverter, graphics);


        scene.drawSceneOfPolygons(drawerPolygons, camera);

        g.drawImage(bi, 0, 0, null);
        graphics.dispose();
    }

    @Override
    public void shouldRepaint() {
        repaint();
    }

}
