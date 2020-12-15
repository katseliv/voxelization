package models;

import math.Vector3;
import third.IModel;
import third.MyPolygon;
import third.PolyLine3D;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class Surface implements IModel {
    private final int length;
    private final int width;
    private final Vector3 startPoint;
    private final List<LinkedList<Vector3>> points = new LinkedList<>();

    public Surface(int length, int width, Vector3 center) {
        this.startPoint = center;
        this.length = length;
        this.width = width;
        create();
    }

    private void create() {
        int coefficient = 40;

        for (int i = 0; i < width; i++) {
            LinkedList<Vector3> buffer = new LinkedList<>();
            for (int j = 0; j < length; j++) {
                float x = i * 0.2f;
                float y = j * 0.2f;
                float z = (float) (2 * Math.sin(x) * Math.cos(y) + Math.sin(x));

                buffer.add(new Vector3(
                        x * width + startPoint.getX(),
                        y * length + startPoint.getY(),
                        z * coefficient + startPoint.getZ()));

            }
            points.add(buffer);
        }
    }

    public int getLength() {
        return length;
    }

    public int getWidth() {
        return width;
    }

    public Vector3 getStartPoint() {
        return startPoint;
    }

    public List<LinkedList<Vector3>> getPoints() {
        return points;
    }

    @Override
    public List<PolyLine3D> getLines() {
        List<PolyLine3D> polyLinePoints = new LinkedList<>();
        List<Vector3> listOfPoints = new LinkedList<>();

        for (int i = 0; i < length - 1; i++) {
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

        for (int i = 0; i < width - 1; i++) {
            for (int j = 0; j < length - 1; j++) {
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
