package kg2019examples_task4threedimensions;

import kg2019examples_task4threedimensions.draw.IDrawer;
import kg2019examples_task4threedimensions.draw.PolygonDrawer;
import kg2019examples_task4threedimensions.draw.SimpleEdgeDrawer;
import kg2019examples_task4threedimensions.math.Vector3;
import kg2019examples_task4threedimensions.models.*;
import kg2019examples_task4threedimensions.operations.VoxelOperation;
import kg2019examples_task4threedimensions.screen.ScreenConverter;
import kg2019examples_task4threedimensions.third.*;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

public class DrawPanel extends JPanel implements CameraController.RepaintListener {
    public final Scene scene = new Scene(Color.BLACK.getRGB());
    private final ScreenConverter screenConverter;
    private final Camera camera;

    public DrawPanel() {
        super();
        screenConverter = new ScreenConverter(-100, 100, 200, 200, 100, 100);
        camera = new Camera();
        CameraController cameraController = new CameraController(camera, screenConverter);
        scene.showAxes();

        voxelizate();

        cameraController.addRepaintListener(this);
        addMouseListener(cameraController);
        addMouseMotionListener(cameraController);
        addMouseWheelListener(cameraController);
    }

    public void voxelizate() {
        VoxelOperation voxelOperation = new VoxelOperation();
        LinkedList<IModel> models = new LinkedList<>();
        LinkedList<IModel> surfaces = new LinkedList<>();

        for (IModel m : scene.getModelsList()) {
            voxelOperation.modelToVoxelOfCubs(m, 50f);
            if (m instanceof Surface) {
                surfaces.addAll(voxelOperation.surfaceToVoxelOfCubs((Surface) m, 400f, 10f));
            } else {
                models.addAll(voxelOperation.modelToVoxelOfCubs(m, 10f));
            }
        }

        scene.getModelsList().addAll(models);
        scene.getModelsList().addAll(surfaces);

//        scene.getModelsList().add(new Parallelepiped(new Vector3(1, 2, 3),new Vector3(-1, -2, -3) ));

//        scene.getModelsList().add(new Parallelepiped(new Vector3(1, 2, 3), 100));
//        scene.getModelsList().addAll(voxelOperation.modelToVoxelOfCubs(new Parallelepiped(new Vector3(1, 2, 3), 100), 20f));

//        scene.getModelsList().add(new Sphere(new Vector3(0, 0, 400)));
//        scene.getModelsList().addAll(voxelOperation.modelToVoxelOfCubs(new Sphere(new Vector3(0, 0, 400)), 30f));
//        scene.getModelsList().add(new Pyramid(new Vector3(0, -600, 0)));
//        scene.getModelsList().addAll(voxelOperation.modelToVoxelOfCubs(new Pyramid(new Vector3(0,-600,0)), 30f));

//        scene.getModelsList().add(new Cylinder(new Vector3(0, 800, 0)));
//        scene.getModelsList().addAll(voxelOperation.modelToVoxelOfCubs(new Cylinder(new Vector3(0, 800, 0)), 300f));
//
//        scene.getModelsList().add(new Surface(80, 50, new Vector3(0, 0, 0)));
//        scene.getModelsList().addAll(voxelOperation.surfaceToVoxelOfCubs(new Surface(80, 50, new Vector3(0, 0, -100)), 400f, 10f));
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
