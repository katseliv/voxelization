package kg2019examples_task4threedimensions.operations;

import kg2019examples_task4threedimensions.enums.Condition;
import kg2019examples_task4threedimensions.math.Vector2;
import kg2019examples_task4threedimensions.math.Vector3;
import kg2019examples_task4threedimensions.models.Line3D;
import kg2019examples_task4threedimensions.models.Parallelepiped;
import kg2019examples_task4threedimensions.models.Surface;
import kg2019examples_task4threedimensions.third.IModel;
import kg2019examples_task4threedimensions.third.MyPolygon;

import java.util.LinkedList;
import java.util.List;

public class VoxelOperation {
    private List<MyPolygon> polygons;

    public List<Parallelepiped> modelToVoxelOfCubs(IModel model, float sizeVoxel) {
        polygons = model.getPolygons();
        List<Vector3> points = new LinkedList<>();
        List<Parallelepiped> cubs = new LinkedList<>();
        Parallelepiped voxel = getSuitableVoxel(sizeVoxel);

        float length = (voxel.getRadius() * 2 - sizeVoxel) / 2;

        float xMax = findMax(polygons, Condition.GET_X);
        float xMin = findMin(polygons, Condition.GET_X);
        float yMax = findMax(polygons, Condition.GET_Y);
        float yMin = findMin(polygons, Condition.GET_Y);
        float zMax = findMax(polygons, Condition.GET_Z);
        float zMin = findMin(polygons, Condition.GET_Z);

        System.out.println("xmax " + xMax);
        System.out.println("xmin " + xMin);
        System.out.println("ymax " + yMax);
        System.out.println("ymin " + yMin);
        System.out.println("zmax " + zMax);
        System.out.println("zmin " + zMin);

//        for (float z = zMin + sizeVoxel / 2; z < zMax + sizeVoxel; z += sizeVoxel) {
//            for (float y = yMin + sizeVoxel / 2; y < yMax + sizeVoxel; y += sizeVoxel) {
//                for (float x = xMin + sizeVoxel / 2; x < xMax + sizeVoxel; x += sizeVoxel) {
//                    if (isInnerPoint(new Vector3(x, y, z))) {
//                        points.add(new Vector3(x, y, z));
//                    }
//                }
//            }
//        }

//        for (float z = voxel.getCenter().getX() + sizeVoxel / 2; z < zMax + sizeVoxel; z += sizeVoxel) {
//            for (float y = voxel.getCenter().getY() + sizeVoxel / 2; y < yMax + sizeVoxel; y += sizeVoxel) {
//                for (float x = voxel.getCenter().getZ() + sizeVoxel / 2; x < xMax + sizeVoxel; x += sizeVoxel) {
//                    if (isInnerPoint(new Vector3(x, y, z))) {
//                        points.add(new Vector3(x, y, z));
//                    }
//                }
//            }
//        }

        for (float x = voxel.getCenter().getX() - length; x <= voxel.getCenter().getX() + length; x += sizeVoxel) {
            for (float y = voxel.getCenter().getY() - length; y <= voxel.getCenter().getY() + length; y += sizeVoxel) {
                for (float z = voxel.getCenter().getZ() - length; z <= voxel.getCenter().getZ() + length; z += sizeVoxel) {

                    if (isInnerPoint(new Vector3(x, y, z))) {
//                        points.add(new Vector3(x, y, z));
                        cubs.add(new Parallelepiped(new Vector3(x, y, z), sizeVoxel / 2));
//                        System.out.println("\u001B[34m" + new Vector3(x, y, z));
//                        continue;
                    }
//                    cubs.add(new Parallelepiped(new Vector3(x, y, z), sizeVoxel / 4));
//                    System.out.println("\u001B[33m" + new Vector3(x, y, z));
//                    points.add(new Vector3(x, y, z));
                }
            }
        }

//        return fillWithCubs(points, sizeVoxel);
        return cubs;
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

    private float findMaxPoint(List<Vector3> points, Condition c) {
        float max = Float.MIN_VALUE;
        for (Vector3 point : points) {
            if (isMax(point, max, c)) {
                max = getCoordinate(point, c);
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

    private float findMinPoint(List<Vector3> points, Condition c) {
        float min = Float.MAX_VALUE;
        for (Vector3 point : points) {
            if (!isMax(point, min, c)) {
                min = getCoordinate(point, c);
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

        float b = Math.max(yMax - yMin, zMax - zMin) / 2f;
        float a = Math.max((xMax - xMin) / 2f, b), length = size;
        while (length <= a) {
            length *= 2;
        }
        float x = (((xMax - xMin) / 2f + xMin));
        float y = (((yMax - yMin) / 2f) + yMin);
        float z = (((zMax - zMin) / 2f) + zMin);
        return new Parallelepiped(new Vector3(x, y, z), length);
    }

    public boolean isInnerPoint(Vector3 point) {
        int numberX = getNumberOfIntersectionsWithSurfaceX(point.getY(), point.getZ()) - 1;
        int numberY = getNumberOfIntersectionsWithSurfaceY(point.getX(), point.getZ()) - 1;
        int numberZ = getNumberOfIntersectionsWithSurfaceZ(point.getX(), point.getY()) - 1;

//        return numberX != 0 && numberY != 0 && numberZ != 0 &&
//                numberX % 2 == 0
//                && numberY % 2 == 0
//                && numberZ % 2 == 0;

        return numberX % 2 == 1
                && numberY % 2 == 1
                && numberZ % 2 == 1;
    }

    private int getNumberOfIntersectionsWithSurfaceX(float y, float z) {
        int counter = 0;

        for (MyPolygon pl : polygons) {
            LinkedList<Vector2> points = new LinkedList<>();

            points.add(new Vector2(pl.getPoint1().getY(), pl.getPoint1().getZ()));
            points.add(new Vector2(pl.getPoint2().getY(), pl.getPoint2().getZ()));
            points.add(new Vector2(pl.getPoint3().getY(), pl.getPoint3().getZ()));

            if (calculateSquare(points.get(0), points.get(1), points.get(2)) != 0) {
                counter += triangleContainThisPoint(points, new Vector2(y, z));
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

            if (calculateSquare(points.get(0), points.get(1), points.get(2)) != 0) {
                counter += triangleContainThisPoint(points, new Vector2(x, z));
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

            if (calculateSquare(points.get(0), points.get(1), points.get(2)) != 0) {
                counter += triangleContainThisPoint(points, new Vector2(x, y));
            }
        }

        return counter;
    }

    private float calculateLength(Vector2 p1, Vector2 p2) {
        float dx = (float) Math.pow(p1.getP1() - p2.getP1(), 2);
        float dy = (float) Math.pow(p1.getP2() - p2.getP2(), 2);
        return (float) Math.pow(dx + dy, 0.5);
    }

    private int triangleContainThisPoint(List<Vector2> points, Vector2 point) {
        float a = (points.get(0).getP1() - point.getP1()) * (points.get(1).getP2() - points.get(0).getP2()) - (points.get(1).getP1() - points.get(0).getP1()) * (points.get(0).getP2() - point.getP2());
        float b = (points.get(1).getP1() - point.getP1()) * (points.get(2).getP2() - points.get(1).getP2()) - (points.get(2).getP1() - points.get(1).getP1()) * (points.get(1).getP2() - point.getP2());
        float c = (points.get(2).getP1() - point.getP1()) * (points.get(0).getP2() - points.get(2).getP2()) - (points.get(0).getP1() - points.get(2).getP1()) * (points.get(2).getP2() - point.getP2());

        if (a * b > 0 && b * c > 0) {
            return 2;
        }
        if ((a == 0 && b * c > 0) || (b == 0 && a * c > 0) || (c == 0 && a * b > 0)) {
            return 1;
        }

        return 0;
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

    public List<Parallelepiped> surfaceToVoxelOfCubs(Surface surface, float height, float sizeVoxel) {
        polygons = surface.getPolygons();
        List<Vector3> points = new LinkedList<>();
        List<LinkedList<Vector3>> lists = surface.getPoints();

        for (int i = 0; i < lists.size() - 1; i++) {
            for (int j = 0; j < lists.get(i).size() - 1; j++) {
                List<Vector3> buffer = new LinkedList<>();
                buffer.add(lists.get(i).get(j));
                buffer.add(lists.get(i + 1).get(j));
                buffer.add(lists.get(i).get(j + 1));
                buffer.add(lists.get(i + 1).get(j + 1));

                Vector2 middle = new Vector2(buffer.get(1).getX(), buffer.get(2).getZ());

                float min = findMinPoint(buffer, Condition.GET_Z);

                points.addAll(fill(height, min, middle, sizeVoxel));
            }
        }

        for (LinkedList<Vector3> list : lists) {
            for (Vector3 vector3 : list) {

            }
        }


        Parallelepiped voxel = getSuitableVoxel(sizeVoxel);

        float length = (voxel.getRadius() - sizeVoxel) / 2;

        float xMax = findMax(polygons, Condition.GET_X);
        float xMin = findMin(polygons, Condition.GET_X);
        float yMax = findMax(polygons, Condition.GET_Y);
        float yMin = findMin(polygons, Condition.GET_Y);
        float zMax = findMax(polygons, Condition.GET_Z);
//        System.out.println(zMax + " z max");
        float zMin = findMin(polygons, Condition.GET_Z);
//        System.out.println(zMin + " z min");

//        for (float z = -height + sizeVoxel / 2; z < zMin; z += sizeVoxel) {
//            for (float y = yMin + sizeVoxel / 2; y < yMax; y += sizeVoxel) {
//                for (float x = xMin + sizeVoxel / 2; x < xMax; x += sizeVoxel) {
//                    points.add(new Vector3(x, y, z));
//                }
//            }
//        }

//        for (MyPolygon polygon : polygons) {
//            List<Vector3> points1 = new LinkedList<>();
//            points1.add(polygon.getPoint1());
//            points1.add(polygon.getPoint2());
//            points1.add(polygon.getPoint3());
//
//            float limit = findMinPoint(points1, Condition.GET_Z);
//
//            for (float z = zMin + sizeVoxel / 2; z < limit; z += sizeVoxel) {
//                points.add(new Vector3(x, y, z));
//            }
//
//        }

//        for (float z = zMin + sizeVoxel / 2; z < zMax; z += sizeVoxel) {
//            for (float y = yMin + sizeVoxel / 2; y < yMax; y += sizeVoxel) {
//                for (float x = xMin + sizeVoxel / 2; x < xMax; x += sizeVoxel) {
//                    if (isInnerPointForSurface(new Vector3(x, y, z))) {
//                        points.add(new Vector3(x, y, z));
//                    }
//                }
//            }
//        }

        return fillWithCubs(points, sizeVoxel);
    }

    private List<Vector3> fill(float height, float limit, Vector2 middle, float step) {
        List<Vector3> points = new LinkedList<>();
        for (float i = -height; i < limit; i += step) {
            points.add(new Vector3(middle.getP1(), middle.getP2(), i));
        }

        return points;
    }

    public List<MyPolygon> modelToVoxelOfPolygon(IModel model) {

        return null;
    }

    public List<MyPolygon> surfaceToVoxelOfPolygon(IModel model) {

        return null;
    }
}
