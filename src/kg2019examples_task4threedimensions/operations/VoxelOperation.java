package kg2019examples_task4threedimensions.operations;

import kg2019examples_task4threedimensions.enums.Condition;
import kg2019examples_task4threedimensions.math.Vector2;
import kg2019examples_task4threedimensions.math.Vector3;
import kg2019examples_task4threedimensions.models.Line3D;
import kg2019examples_task4threedimensions.models.Parallelepiped;
import kg2019examples_task4threedimensions.third.IModel;
import kg2019examples_task4threedimensions.third.MyPolygon;

import java.util.LinkedList;
import java.util.List;

public class VoxelOperation {
    private List<MyPolygon> polygons;

    public List<Parallelepiped> modelToVoxelOfCubs(IModel model, float sizeVoxel) {
        polygons = model.getPolygons();
        List<Vector3> points = new LinkedList<>();
        Parallelepiped voxel = getSuitableVoxel(sizeVoxel);

        float length = (voxel.getRadius() - sizeVoxel) / 2;

        float xMax = findMax(polygons, Condition.GET_X);
        float xMin = findMin(polygons, Condition.GET_X);
        float yMax = findMax(polygons, Condition.GET_Y);
        float yMin = findMin(polygons, Condition.GET_Y);
        float zMax = findMax(polygons, Condition.GET_Z);
        float zMin = findMin(polygons, Condition.GET_Z);

        for (float z = zMin + sizeVoxel / 2; z < zMax; z += sizeVoxel) {
            for (float y = yMin + sizeVoxel / 2; y < yMax; y += sizeVoxel) {
                for (float x = xMin + sizeVoxel / 2; x < xMax; x += sizeVoxel) {
                    if (isInnerPoint(new Vector3(x, y, z))) {
                        points.add(new Vector3(x, y, z));
                    }
                }
            }
        }

//        for (float x = voxel.getCenter().getX() - length; x <= voxel.getCenter().getX() + length; x += size) {
//            for (float y = voxel.getCenter().getY() - length; y <= voxel.getCenter().getY() + length; y += size) {
//                for (float z = voxel.getCenter().getZ() - length; z <= voxel.getCenter().getZ() + length; z += size) {
//
//                    if (isInnerPoint(new Vector3(x, y, z)))
//                        points.add(new Vector3(x, y, z));
//                        //result.add(new Voxel(new Vector3(x, y, z), minVoxelLength));
//
//                }
//            }
//        }

        return fillWithCubs(points, sizeVoxel);
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

    private Parallelepiped getSuitableVoxel(float size) {
        float xMax = findMax(polygons, Condition.GET_X);
        float xMin = findMin(polygons, Condition.GET_X);
        float yMax = findMax(polygons, Condition.GET_Y);
        float yMin = findMin(polygons, Condition.GET_Y);
        float zMax = findMax(polygons, Condition.GET_Z);
        float zMin = findMin(polygons, Condition.GET_Z);

        float a = Math.max(xMax - xMin, Math.max(yMax - yMin, zMax - zMin)),
                length = size;
        while (length <= a)
            length *= 2;
        float x = (int) (((xMax - xMin) / 2. + xMin) / size) * size;
        float y = (int) (((yMax - yMin) / 2. + yMin) / size) * size;
        float z = (int) (((zMax - zMin) / 2. + zMin) / size) * size;
        return new Parallelepiped(new Vector3(x, y, z), length);
    }

    public boolean isInnerPoint(Vector3 point) {
        int numberX = getNumberOfIntersectionsWithSurfaceX(point.getY(), point.getZ());
        int numberY = getNumberOfIntersectionsWithSurfaceY(point.getX(), point.getZ());
        int numberZ = getNumberOfIntersectionsWithSurfaceZ(point.getX(), point.getY());

        return numberX != 0 && numberY != 0 && numberZ != 0
                && numberX % 2 == 0
                && numberY % 2 == 0
                && numberZ % 2 == 0;
    }

    private int getNumberOfIntersectionsWithSurfaceX(float y, float z) {
        int counter = 0;

        for (MyPolygon pl : polygons) {
            LinkedList<Vector2> points = new LinkedList<>();

            points.add(new Vector2(pl.getPoint1().getY(), pl.getPoint1().getZ()));
            points.add(new Vector2(pl.getPoint2().getY(), pl.getPoint2().getZ()));
            points.add(new Vector2(pl.getPoint3().getY(), pl.getPoint3().getZ()));

            if (calculateSquare(points.get(0), points.get(1), points.get(2)) != 0
                    && triangleContainThisPoint(points, new Vector2(y, z))) {
                counter++;
            }
        }

        return counter;
    }

    private int getNumberOfIntersectionsWithSurfaceY(float x, float z) {
        int counter = 0;

        for (MyPolygon pl : polygons) {
            LinkedList<Vector2> points = new LinkedList<>();

            points.add(new Vector2(pl.getPoint1().getX(), pl.getPoint1().getZ()));
            points.add(new Vector2(pl.getPoint2().getX(), pl.getPoint2().getZ()));
            points.add(new Vector2(pl.getPoint3().getX(), pl.getPoint3().getZ()));

            if (calculateSquare(points.get(0), points.get(1), points.get(2)) != 0
                    && triangleContainThisPoint(points, new Vector2(x, z))) {
                counter++;
            }

        }
        return counter;
    }

    private int getNumberOfIntersectionsWithSurfaceZ(float x, float y) {
        int counter = 0;

        for (MyPolygon pl : polygons) {
            List<Vector2> points = new LinkedList<>();
            points.add(new Vector2(pl.getPoint1().getX(), pl.getPoint1().getY()));
            points.add(new Vector2(pl.getPoint2().getX(), pl.getPoint2().getY()));
            points.add(new Vector2(pl.getPoint3().getX(), pl.getPoint3().getY()));

            if (calculateSquare(points.get(0), points.get(1), points.get(2)) != 0
                    && triangleContainThisPoint(points, new Vector2(x, y))) {
                counter++;
            }
        }

        return counter;
    }


    private int findInnerPoint(List<MyPolygon> polygons, Line3D line, Condition condition) {
        int counter = 0;
        for (MyPolygon polygon : polygons) {
            if (findInsertionPoint(polygon, line, condition)) {
                counter++;
            }
        }
        return counter;
    }

    private boolean findInsertionPoint(MyPolygon polygon, Line3D line, Condition condition) {
        float l1 = line.getP2().getX();
        float l2 = line.getP2().getY();
        float l3 = line.getP2().getZ();

        Vector3 p1 = polygon.getPoint1();
        float x1 = p1.getX();
        float y1 = p1.getY();
        float z1 = p1.getZ();

        Vector3 p2 = polygon.getPoint2();
        float x2 = p1.getX();
        float y2 = p1.getY();
        float z2 = p1.getZ();

        Vector3 p3 = polygon.getPoint3();
        float x3 = p1.getX();
        float y3 = p1.getY();
        float z3 = p1.getZ();

        float a, b, c, d, e, f;
        switch (condition) {
            case WITHOUT_X:
                a = calculateLength(y1, z1, y2, z2);
                b = calculateLength(y2, z2, y3, z3);
                c = calculateLength(y3, z3, y1, z1);

                d = calculateLength(y1, z1, l2, l3);
                e = calculateLength(y2, z2, l2, l3);
                f = calculateLength(y3, z3, l2, l3);
                break;
            case WITHOUT_Y:
                a = calculateLength(x1, z1, x2, z2);
                b = calculateLength(x2, z2, x3, z3);
                c = calculateLength(x3, z3, x1, z1);

                d = calculateLength(x1, z1, l1, l3);
                e = calculateLength(x2, z2, l1, l3);
                f = calculateLength(x3, z3, l1, l3);
                break;
            case WITHOUT_Z:
                a = calculateLength(x1, y1, x2, y2);
                b = calculateLength(x2, y2, x3, y3);
                c = calculateLength(x3, y3, x1, y1);

                d = calculateLength(x1, y1, l1, l2);
                e = calculateLength(x2, y2, l1, l2);
                f = calculateLength(x3, y3, l1, l2);
                break;
            default:
                a = 0;
                b = 0;
                c = 0;
                d = 0;
                e = 0;
                f = 0;
        }

//        float a = calculateLength(p1, p2);
//        float b = calculateLength(p2, p3);
//        float c = calculateLength(p3, p1);

        float fullSquare = calculateSquare(a, b, c);

        //float peacesSquare = calculatePartsOfSquare(a);
//
//        d = calculateLength(p1, line.getP2());
//        e = calculateLength(p2, line.getP2());
        float square1 = calculateSquare(a, d, e);

//        d = calculateLength(p2, line.getP2());
//        e = calculateLength(p3, line.getP2());
        float square2 = calculateSquare(b, e, f);

//        d = calculateLength(p1, line.getP2());
//        e = calculateLength(p3, line.getP2());
        float square3 = calculateSquare(c, d, f);

        return compare(fullSquare, square1 + square2 + square3);
    }


    private float calculateLength(Vector2 p1, Vector2 p2) {
        float dx = (float) Math.pow(p1.getP1() - p2.getP1(), 2);
        float dy = (float) Math.pow(p1.getP2() - p2.getP2(), 2);
        return (float) Math.pow(dx + dy, 0.5);
    }

    private float calculateLength(float p1, float q1, float p2, float q2) {
        float dx = (float) Math.pow(p1 - p2, 2);
        float dy = (float) Math.pow(q1 - q2, 2);
        return (float) Math.pow(dx + dy, 0.5);
    }

    private float calculateSquare(float a, float b, float c) {
        float p = 0.5f * (a + b + c);
        return (float) Math.pow(p * (p - a) * (p - b) * (p - c), 0.5);
    }

    private boolean compare(float o1, float o2) {
        double EPSILON = Math.pow(10, -50);
        return o1 - o2 < EPSILON;
    }


    private boolean triangleContainThisPoint(List<Vector2> points, Vector2 point) {
        double[] arr = new double[3];
        for (int i = 0; i < 3; i++) {
            arr[i] = (points.get(i).getP1() - point.getP1()) * (points.get(i == 2 ? 0 : i + 1).getP2() - points.get(i).getP2())
                    - (points.get(i).getP2() - point.getP2()) * (points.get(i == 2 ? 0 : i + 1).getP1() - points.get(i).getP1());
        }
        return (arr[0] * arr[1] > 0 && arr[1] * arr[2] > 0)
                || arr[0] == 0. || arr[1] == 0. || arr[2] == 0.;
    }

    private float calculateSquare(Vector2 p1, Vector2 p2, Vector2 p3) {
        float a = calculateLength(p1, p2);
        float b = calculateLength(p2, p3);
        float c = calculateLength(p3, p1);
        float p = 0.5f * (a + b + c);
        return (float) Math.pow(p * (p - a) * (p - b) * (p - c), 0.5);
    }

    private List<Parallelepiped> fillWithCubs(List<Vector3> points, float length) {
        List<Parallelepiped> cubs = new LinkedList<>();

        for (Vector3 point : points) {
            cubs.add(new Parallelepiped(new Vector3(point.getX(), point.getY(), point.getZ()), length / 2));
        }

        return cubs;
    }

    public List<Parallelepiped> surfaceToVoxelOfCubs(IModel model, float height, float sizeVoxel) {
        polygons = model.getPolygons();
        List<Vector3> points = new LinkedList<>();
        Parallelepiped voxel = getSuitableVoxel(sizeVoxel);

        float length = (voxel.getRadius() - sizeVoxel) / 2;

        float xMax = findMax(polygons, Condition.GET_X);
        float xMin = findMin(polygons, Condition.GET_X);
        float yMax = findMax(polygons, Condition.GET_Y);
        float yMin = findMin(polygons, Condition.GET_Y);
        float zMax = findMax(polygons, Condition.GET_Z);
        float zMin = -height;

        for (float z = zMin + sizeVoxel / 2; z < zMax; z += sizeVoxel) {
            for (float y = yMin + sizeVoxel / 2; y < yMax; y += sizeVoxel) {
                for (float x = xMin + sizeVoxel / 2; x < xMax; x += sizeVoxel) {
                    if (isInnerPoint(new Vector3(x, y, z))) {
                        points.add(new Vector3(x, y, z));
                    }
                }
            }
        }
        return fillWithCubs(points, sizeVoxel);
    }

    public List<MyPolygon> modelToVoxelOfPolygon(IModel model) {

        return null;
    }

    public List<MyPolygon> surfaceToVoxelOfPolygon(IModel model) {

        return null;
    }
}
