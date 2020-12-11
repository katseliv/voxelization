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

public class Cylinder implements IModel {
    private final float height = 1000f;
    private final float radius = 1000f;
    private final int approximate = 30;
    private final Vector3 center;

    public Cylinder(Vector3 center) {
        this.center = center;
    }

    @Override
    public List<PolyLine3D> getLines() {
        List<PolyLine3D> points = new LinkedList<>();
        List<Vector3> circle1 = new LinkedList<>();
        List<Vector3> circle2 = new LinkedList<>();
        List<Vector3> edges = new LinkedList<>();

        float step = (float) (2 * Math.PI / approximate);

        float x, y, z;

        for (float alpha = 0; alpha <= 2 * Math.PI; alpha += step) {
            x = (float) (radius * cos(alpha) + center.getX());
            y = (float) (radius * sin(alpha) + center.getY());
            z = 0;
            circle1.add(new Vector3(x, y, z));
            edges.add(new Vector3(x, y, z));
            z = height;
            circle2.add(new Vector3(x, y, z));
            edges.add(new Vector3(x, y, z));
            points.add(new PolyLine3D(edges, false));
            edges.clear();
        }

        points.add(new PolyLine3D(circle1, true));
        points.add(new PolyLine3D(circle2, true));

        return points;
    }

    @Override
    public List<MyPolygon> getPolygons() {
        List<MyPolygon> polygons = new LinkedList<>();

        float step = (float) (2 * Math.PI / approximate);

        float x, y, z;
        MyPolygon polygon;

        for (float alpha = 0; alpha <= 2 * Math.PI; alpha += step) {
            x = (float) (radius * cos(alpha) + center.getX());
            y = (float) (radius * sin(alpha) + center.getY());
            z = 0;

            Vector3 vector1 = new Vector3(x, y, z);

            x = (float) (radius * cos(alpha + step) + center.getX());
            y = (float) (radius * sin(alpha + step) + center.getY());

            Vector3 vector2 = new Vector3(x, y, z);

            z = height;

            Vector3 vector3 = new Vector3(x, y, z);

            x = (float) (radius * cos(alpha) + center.getX());
            y = (float) (radius * sin(alpha) + center.getY());

            Vector3 vector4 = new Vector3(x, y, z);

            int r = (int) (Math.random() * 255);
            int g = (int) (Math.random() * 255);
            int b = (int) (Math.random() * 255);

            polygon = new MyPolygon(vector1, vector4, vector3, new Color(r, g, b));
            polygons.add(polygon);
            polygon = new MyPolygon(vector1, vector2, vector3, new Color(r, g, b));
            polygons.add(polygon);
        }

        Vector3 vector0 = new Vector3(center.getX(), center.getY(), center.getZ() + height);

        for (float alpha = 0; alpha <= 2 * Math.PI; alpha += step) {
            x = (float) (radius * cos(alpha) + center.getX());
            y = (float) (radius * sin(alpha) + center.getY());
            z = 0;

            Vector3 vector1 = new Vector3(x, y, z);

            z = height;
            Vector3 vector2 = new Vector3(x, y, z);

            x = (float) (radius * cos(alpha + step) + center.getX());
            y = (float) (radius * sin(alpha + step) + center.getY());
            z = 0;

            Vector3 vector3 = new Vector3(x, y, z);

            z = height;
            Vector3 vector4 = new Vector3(x, y, z);

            int r = (int) (Math.random() * 255);
            int g = (int) (Math.random() * 255);
            int b = (int) (Math.random() * 255);

            polygon = new MyPolygon(center, vector1, vector3, new Color(r, g, b));
            polygons.add(polygon);
            polygon = new MyPolygon(vector0, vector2, vector4, new Color(r, g, b));
            polygons.add(polygon);
        }

        return polygons;
    }
}
