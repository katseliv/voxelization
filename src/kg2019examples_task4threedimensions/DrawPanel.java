/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kg2019examples_task4threedimensions;

import kg2019examples_task4threedimensions.draw.IDrawer;
import kg2019examples_task4threedimensions.draw.PolygonDrawer;
import kg2019examples_task4threedimensions.draw.SimpleEdgeDrawer;
import kg2019examples_task4threedimensions.math.Vector3;
import kg2019examples_task4threedimensions.models.*;
import kg2019examples_task4threedimensions.operations.VoxelOperation;
import kg2019examples_task4threedimensions.screen.ScreenConverter;
import kg2019examples_task4threedimensions.screen.ScreenPoint;
import kg2019examples_task4threedimensions.third.*;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Collection;
import java.util.LinkedList;
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
//        scene.getModelsList().add(new Parallelepiped(new Vector3(1, 2, 3),new Vector3(-1, -2, -3) ));

//        scene.getModelsList().add(new Parallelepiped(new Vector3(1, 2, 3), 100));
//        scene.getModelsList().addAll(voxelOperation.modelToVoxelOfCubs(new Parallelepiped(new Vector3(1, 2, 3), 100), 20f));

//        scene.getModelsList().add(new Sphere(new Vector3(0, 0, 400)));
//        scene.getModelsList().addAll(voxelOperation.modelToVoxelOfCubs(new Sphere(new Vector3(0, 0, 400)), 30f));
//        scene.getModelsList().add(new Pyramid(new Vector3(0, -600, 0)));
//        scene.getModelsList().addAll(voxelOperation.modelToVoxelOfCubs(new Pyramid(new Vector3(0,-600,0)), 30f));

        scene.getModelsList().add(new Cylinder(new Vector3(0, 800, 0)));
        scene.getModelsList().addAll(voxelOperation.modelToVoxelOfCubs(new Cylinder(new Vector3(0, 800, 0)), 300f));
//
//        scene.getModelsList().add(new Surface(new Vector3(10, 30, 800), 40, 30));
//        scene.getModelsList().addAll(voxelOperation.surfaceToVoxelOfCubs(new Surface(new Vector3(10, 30, 800), 40, 400), 500f, 100f));

        cameraController.addRepaintListener(this);
        addMouseListener(cameraController);
        addMouseMotionListener(cameraController);
        addMouseWheelListener(cameraController);
    }

    public void voxelizate() {
        VoxelOperation voxelOperation = new VoxelOperation();
        LinkedList<Collection<? extends IModel>> allModels = new LinkedList<>();
        List<IModel> models = new LinkedList<>();
        allModels.add(scene.getModelsList());

        for (Collection<? extends IModel> mc : allModels) {
            for (IModel m : mc) {
                models.addAll(voxelOperation.modelToVoxelOfCubs(m, 20f));
            }
        }

        scene.getModelsList().addAll(models);
        //repaint();
    }

    @Override
    public void paint(Graphics g) {
        screenConverter.setScreenSize(getWidth(), getHeight());
        BufferedImage bi = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = (Graphics2D) bi.getGraphics();
        IDrawer dr = new SimpleEdgeDrawer(screenConverter, graphics);
        IDrawer drawerPolygons = new PolygonDrawer(screenConverter, graphics);

        scene.drawSceneOfPolygons(drawerPolygons, camera);

//        for (Parallelepiped p : voxelOperation.modelToVoxelOfCubs(new Cylinder(new Vector3(0, 800, 0)), 300f)) {
//            g.setColor(Color.CYAN);
//            ScreenPoint screenPoint = screenConverter.r2s(p.getCenter());
//            g.drawString(p.getCenter().getX() + " " + p.getCenter().getY() + " ", screenPoint.getI(), screenPoint.getJ());
//        }

        g.drawImage(bi, 0, 0, null);
        graphics.dispose();
    }

    @Override
    public void shouldRepaint() {
        repaint();
    }

}
