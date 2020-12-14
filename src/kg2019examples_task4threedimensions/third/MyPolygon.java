package kg2019examples_task4threedimensions.third;

import kg2019examples_task4threedimensions.math.Vector3;

import java.awt.*;

public class MyPolygon {
    private final Vector3 point1;
    private final Vector3 point2;
    private final Vector3 point3;
    private final Color color;

    public MyPolygon(Vector3 point1, Vector3 point2, Vector3 point3, Color color) {
        this.point1 = point1;
        this.point2 = point2;
        this.point3 = point3;
        this.color = color;
    }

    public Vector3 getPoint1() {
        return point1;
    }

    public Vector3 getPoint2() {
        return point2;
    }

    public Vector3 getPoint3() {
        return point3;
    }

    public Color getColor() {
        return color;
    }

    public float avgZ() {
        if (point1 == null || point2 == null || point3 == null)
            return 0;
        float sum;
        sum = point1.getZ() + point2.getZ() + point3.getZ();
        return sum / 3;
    }
}
