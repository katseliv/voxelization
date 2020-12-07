package kg2019examples_task4threedimensions.models;

import kg2019examples_task4threedimensions.math.Vector3;
import kg2019examples_task4threedimensions.third.IModel;
import kg2019examples_task4threedimensions.third.MyPolygon;
import kg2019examples_task4threedimensions.third.PolyLine3D;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

import static java.lang.StrictMath.cos;
import static java.lang.StrictMath.sin;

public class Sphere implements IModel {
    private final float a = 100f;
    private final float b = 100f;
    private final float c = 100f;
    private final int approximate = 20;
    private final Vector3 center;

    public Sphere(Vector3 center) {
        this.center = center;
    }

    @Override
    public List<PolyLine3D> getLines() {
        List<PolyLine3D> parallels = new LinkedList<>();
        List<PolyLine3D> meridians = new LinkedList<>();
        List<Vector3> points = new LinkedList<>();

        float step = (float) (2 * Math.PI / approximate);

        float x, y, z;
        for (float alpha = 0; alpha <= 2 * Math.PI; alpha += step) {
            for (float beta = 0; beta <= 2 * Math.PI; beta += step) {
                x = (float) (a * cos(alpha) * sin(beta) + center.getX());
                y = (float) (b * sin(alpha) * sin(beta) + center.getY());
                z = (float) (c * cos(beta) + center.getZ());

                points.add(new Vector3(x, y, z));
            }
            meridians.add(new PolyLine3D(points, true));
            points.clear();
        }

        for (float beta = 0; beta <= 2 * Math.PI; beta += step) {
            for (float alpha = 0; alpha <= 2 * Math.PI; alpha += step) {
                x = (float) (a * cos(alpha) * sin(beta) + center.getX());
                y = (float) (b * sin(alpha) * sin(beta) + center.getY());
                z = (float) (c * cos(beta) + center.getZ());

                points.add(new Vector3(x, y, z));
            }
            parallels.add(new PolyLine3D(points, true));
            points.clear();
        }

        parallels.addAll(meridians);

        return parallels;
    }

    @Override
    public List<MyPolygon> getPolygons() {
        List<MyPolygon> polygons = new LinkedList<>();

        float step = (float) (2 * Math.PI / approximate);

        float x, y, z;
        MyPolygon polygon;
        for (float beta = 0; beta <= Math.PI; beta += step) { //поднимаемся по бетта
            for (float alpha = 0; alpha <= 2 * Math.PI; alpha += step) {

                x = (float) (a * cos(alpha + step) * sin(beta) + center.getX());
                y = (float) (b * sin(alpha + step) * sin(beta) + center.getY());
                z = (float) (c * cos(beta) + center.getZ());
                Vector3 vector1 = new Vector3(x, y, z);

                x = (float) (a * cos(alpha) * sin(beta) + center.getX());
                y = (float) (b * sin(alpha) * sin(beta) + center.getY());
                Vector3 vector2 = new Vector3(x, y, z);

                x = (float) (a * cos(alpha) * sin(beta + step) + center.getX());
                y = (float) (b * sin(alpha) * sin(beta + step) + center.getY());
                z = (float) (c * cos(beta + step) + center.getZ());
                Vector3 vector3 = new Vector3(x, y, z);

                x = (float) (a * cos(alpha + step) * sin(beta + step) + center.getX());
                y = (float) (b * sin(alpha + step) * sin(beta + step) + center.getY());
                Vector3 vector4 = new Vector3(x, y, z);

                int r = (int) (Math.random() * 255);
                int g = (int) (Math.random() * 255);
                int b = (int) (Math.random() * 255);

                polygon = new MyPolygon(vector1, vector2, vector3, new Color(r, g, b));
                polygons.add(polygon);
                polygon = new MyPolygon(vector1, vector4, vector3, new Color(r, g, b));
                polygons.add(polygon);

            }
        }

        return polygons;
    }
}
