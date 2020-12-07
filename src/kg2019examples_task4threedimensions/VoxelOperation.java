package kg2019examples_task4threedimensions;

import kg2019examples_task4threedimensions.math.Vector3;
import kg2019examples_task4threedimensions.models.Line3D;
import kg2019examples_task4threedimensions.models.Parallelepiped;
import kg2019examples_task4threedimensions.third.IModel;
import kg2019examples_task4threedimensions.third.MyPolygon;
import kg2019examples_task4threedimensions.third.PolyLine3D;

import java.util.LinkedList;
import java.util.List;

public class VoxelOperation {

    public List<Parallelepiped> modelToVoxelOfCubs(IModel model, float length) {
        List<MyPolygon> polygons = model.getPolygons();
        List<Line3D> lines = new LinkedList<>();
        List<PolyLine3D> polyLine3DS = new LinkedList<>();
        List<Vector3> points = new LinkedList<>();

        float xMax = findMax(polygons, Condition.GET_X);
        float xMin = findMin(polygons, Condition.GET_X);
        float yMax = findMax(polygons, Condition.GET_Y);
        float yMin = findMin(polygons, Condition.GET_Y);
        float zMax = findMax(polygons, Condition.GET_Z);
        float zMin = findMin(polygons, Condition.GET_Z);
        //lines.add(new Line3D(new Vector3(xMin, yMin, 0), new Vector3(xMax, yMax, 0)));

        for (float z = zMin + length / 2; z < zMax; z += length) {
            for (float y = yMin + length / 2; y < yMax; y += length) {
                for (float x = xMin + length / 2; x < xMax + length / 2; x += length / 2) {
                    if (isIntersectionPoints(polygons, new Vector3(x, y, z))) {
                        points.add(new Vector3(x, y, z));
                    }
                }
                if (points.size() == 0) {
                    continue;
                }

                lines.add(new Line3D(points.get(0), points.get(points.size() - 1)));
                points.clear();
            }
        }

        return fillWithCubs(lines, length);
        //return lines;
    }

    private float findMax(List<MyPolygon> polygons, Condition c) {
        float max = Float.MIN_VALUE;

        for (MyPolygon polygon : polygons) {
            List<Vector3> points = new LinkedList<>();
            points.add(polygon.getPoint1());
            points.add(polygon.getPoint2());
            points.add(polygon.getPoint3());

            for (Vector3 point : points) {
                if (isMax(point, max, c)) {
                    max = getCoordinate(point, c);
                }
            }
        }
        return max;
    }

    private float findMin(List<MyPolygon> polygons, Condition c) {
        float min = Float.MAX_VALUE;

        for (MyPolygon polygon : polygons) {
            List<Vector3> points = new LinkedList<>();
            points.add(polygon.getPoint1());
            points.add(polygon.getPoint2());
            points.add(polygon.getPoint3());

            for (Vector3 point : points) {
                if (!isMax(point, min, c)) {
                    min = getCoordinate(point, c);
                }
            }
        }
        return min;
    }

    private boolean isMax(Vector3 p1, float p2, Condition c) {
        switch (c) {
            case GET_X:
                return p1.getX() > p2;
            case GET_Y:
                return p1.getY() > p2;
            case GET_Z:
                return p1.getZ() > p2;
        }
        return false;
    }

    private float getCoordinate(Vector3 point, Condition c) {
        switch (c) {
            case GET_X:
                return point.getX();
            case GET_Y:
                return point.getY();
            case GET_Z:
                return point.getZ();
        }
        return Float.MIN_VALUE;
    }

    private boolean isIntersectionPoints(List<MyPolygon> polygons, Vector3 point) {
        for (MyPolygon polygon : polygons) {
            if (isBelongingFlat(polygon, point)) {
                return true;
            }
        }
        return false;
    }

    private void findInsertionPoint(List<MyPolygon> polygons, Line3D line) {

    }

    private boolean isBelongingFlat(MyPolygon polygon, Vector3 point) {
        float x0 = polygon.getPoint1().getX();
        float y0 = polygon.getPoint1().getY();
        float z0 = polygon.getPoint1().getZ();
        float x1 = polygon.getPoint2().getX();
        float y1 = polygon.getPoint2().getY();
        float z1 = polygon.getPoint2().getZ();
        float x2 = polygon.getPoint3().getX();
        float y2 = polygon.getPoint3().getY();
        float z2 = polygon.getPoint3().getZ();

        return (point.getX() - x0) * (y1 - y0) * (z2 - z0)
                + (x1 - x0) * (y2 - y0) * (point.getZ() - z0)
                + (x2 - x0) * (point.getY() - y0) * (z1 - z0)
                - (x2 - x0) * (y1 - y0) * (point.getZ() - z0)
                - (point.getX() - x0) * (y2 - y0) * (z1 - z0)
                - (x1 - x0) * (point.getY() - y0) * (z2 - z0) == 0;
    }

    private List<Parallelepiped> fillWithCubs(List<Line3D> lines, float length) {
        List<Parallelepiped> cubs = new LinkedList<>();
        for (Line3D line3D : lines) {
            Vector3 p1 = line3D.getP1();
            Vector3 p2 = line3D.getP2();
            for (float i = p1.getX(); i < p2.getX(); i += length) {
                //for (float j = p1.getZ(); j < p2.getZ(); j += length) {
                cubs.add(new Parallelepiped(new Vector3(i, p1.getY(), p1.getZ()), length / 2));
                //}
            }
        }
        return cubs;
    }

    public List<Parallelepiped> surfaceToVoxelOfCub(IModel model) {

        return null;
    }

    public List<MyPolygon> modelToVoxelOfPolygon(IModel model) {
        return null;
    }

    public List<MyPolygon> surfaceToVoxelOfPolygon(IModel model) {

        return null;
    }

}
