package kg2019examples_task4threedimensions.models;

import kg2019examples_task4threedimensions.math.Vector3;
import kg2019examples_task4threedimensions.third.IModel;
import kg2019examples_task4threedimensions.third.MyPolygon;
import kg2019examples_task4threedimensions.third.PolyLine3D;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class Surface implements IModel {
    private final int height;
    private final int width;
    private final Vector3 center;
    private final List<LinkedList<Vector3>> points = new LinkedList<>();

    public Surface(Vector3 center, int height, int width) {
        this.center = center;
        this.height = height;
        this.width = width;
        create();
    }

    private void create() {
        int coefficient = 300;

        for (int i = 0; i < height; i++) {
            LinkedList<Vector3> buffer = new LinkedList<>();
            for (int j = 0; j < width; j++) {
                float x = i * 0.2f;
                float y = j * 0.2f;
                float z = (float) (2 * Math.sin(x) * Math.cos(y) + Math.sin(x));

                buffer.add(j, new Vector3(
                        x * coefficient + center.getX(),
                        y * coefficient + center.getY(),
                        z * coefficient + center.getZ()));
            }
            points.add(i, buffer);
        }
    }

    @Override
    public List<PolyLine3D> getLines() {
        List<PolyLine3D> polyLinePoints = new LinkedList<>();
        List<Vector3> listOfPoints = new LinkedList<>();

        for (int i = 0; i < height - 1; i++) {
            for (int j = 0; j < width - 1; j++) {
                Vector3 v1 = points.get(i).get(j);
                Vector3 v2 = points.get(i + 1).get(j);
                Vector3 v3 = points.get(i + 1).get(j + 1);

                listOfPoints.add(v1);
                listOfPoints.add(v2);
                listOfPoints.add(v3);
                polyLinePoints.add(new PolyLine3D(listOfPoints, true));
                listOfPoints.clear();

                v2 = points.get(i).get(j + 1);

                listOfPoints.add(v1);
                listOfPoints.add(v2);
                listOfPoints.add(v3);
                polyLinePoints.add(new PolyLine3D(listOfPoints, true));
            }
        }
        return polyLinePoints;
    }

    @Override
    public List<MyPolygon> getPolygons() {
        List<MyPolygon> polygons = new LinkedList<>();
        MyPolygon polygon;

        for (int i = 0; i < height - 1; i++) {
            for (int j = 0; j < width - 1; j++) {
                Vector3 v1 = points.get(i).get(j);
                Vector3 v2 = points.get(i + 1).get(j);
                Vector3 v3 = points.get(i + 1).get(j + 1);

                polygon = new MyPolygon(v1, v2, v3, Color.RED);
                polygons.add(polygon);

                v2 = points.get(i).get(j + 1);

                polygon = new MyPolygon(v1, v2, v3, Color.RED);
                polygons.add(polygon);
            }
        }

        return polygons;
    }
}
