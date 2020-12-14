package kg2019examples_task4threedimensions.operations;

import kg2019examples_task4threedimensions.enums.Condition;
import kg2019examples_task4threedimensions.math.Vector2;
import kg2019examples_task4threedimensions.math.Vector3;
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
        List<Parallelepiped> cubs = new LinkedList<>();
        Parallelepiped voxel = getBoundaries(sizeVoxel);

        float length = (voxel.getRadius() * 2 - sizeVoxel) / 2;

        for (float x = voxel.getCenter().getX() - length; x <= voxel.getCenter().getX() + length; x += sizeVoxel) {
            for (float y = voxel.getCenter().getY() - length; y <= voxel.getCenter().getY() + length; y += sizeVoxel) {
                for (float z = voxel.getCenter().getZ() - length; z <= voxel.getCenter().getZ() + length; z += sizeVoxel) {
                    if (isInnerPoint(new Vector3(x, y, z))) {
                        cubs.add(new Parallelepiped(sizeVoxel / 2, new Vector3(x, y, z)));
                    }
                }
            }
        }

        return cubs;
    }

    private Parallelepiped getBoundaries(float size) {
        float xMax = findMax(polygons, Condition.GET_X);
        float xMin = findMin(polygons, Condition.GET_X);
        float yMax = findMax(polygons, Condition.GET_Y);
        float yMin = findMin(polygons, Condition.GET_Y);
        float zMax = findMax(polygons, Condition.GET_Z);
        float zMin = findMin(polygons, Condition.GET_Z);

        float a = Math.max(yMax - yMin, zMax - zMin) / 2f;
        float b = Math.max((xMax - xMin) / 2f, a), length = size;

        while (length <= b) {
            length *= 2;
        }

        float x = ((xMax - xMin) / 2f + xMin);
        float y = ((yMax - yMin) / 2f + yMin);
        float z = ((zMax - zMin) / 2f + zMin);

        return new Parallelepiped(length, new Vector3(x, y, z));
    }

    private float findMax(List<MyPolygon> polygons, Condition c) {
        float max = -Float.MAX_VALUE;

        for (MyPolygon polygon : polygons) {
            List<Vector3> points = new LinkedList<>();
            points.add(polygon.getPoint1());
            points.add(polygon.getPoint2());
            points.add(polygon.getPoint3());

            for (Vector3 point : points) {
                max = getMax(point, max, c);
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
                min = getMin(point, min, c);
            }
        }
        return min;
    }

    private float getMax(Vector3 p1, float p2, Condition c) {
        switch (c) {
            case GET_X:
                return Math.max(p1.getX(), p2);
            case GET_Y:
                return Math.max(p1.getY(), p2);
            case GET_Z:
                return Math.max(p1.getZ(), p2);
        }
        return -Float.MAX_VALUE;
    }

    private float getMin(Vector3 p1, float p2, Condition c) {
        switch (c) {
            case GET_X:
                return Math.min(p1.getX(), p2);
            case GET_Y:
                return Math.min(p1.getY(), p2);
            case GET_Z:
                return Math.min(p1.getZ(), p2);
        }
        return Float.MAX_VALUE;
    }

    public boolean isInnerPoint(Vector3 point) {
        int numberX = getNumberOfIntersectionsWithSurfaceX(point.getY(), point.getZ()) - 1;
        int numberY = getNumberOfIntersectionsWithSurfaceY(point.getX(), point.getZ()) - 1;
        int numberZ = getNumberOfIntersectionsWithSurfaceZ(point.getX(), point.getY()) - 1;

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
            cubs.add(new Parallelepiped(length / 2, new Vector3(point.getX(), point.getY(), point.getZ())));
        }

        return cubs;
    }

    public List<Parallelepiped> surfaceToVoxelOfCubs(Surface surface, float height, float sizeVoxel) {
        polygons = surface.getPolygons();
        List<Vector3> points = new LinkedList<>();

        float xMax = findMax(polygons, Condition.GET_X);
        float yMax = findMax(polygons, Condition.GET_Y);

        for (float i = surface.getStartPoint().getX() + sizeVoxel / 2; i < xMax; i += sizeVoxel) {
            for (float j = surface.getStartPoint().getY() + sizeVoxel / 2; j < yMax; j += sizeVoxel) {

                MyPolygon polygon = getIntersectingPolygonWithSurface(i, j);
                List<Vector3> buffer = new LinkedList<>();

                if (polygon != null) {
                    buffer.add(new Vector3(polygon.getPoint1().getX(), polygon.getPoint1().getY(), polygon.getPoint1().getZ()));
                    buffer.add(new Vector3(polygon.getPoint2().getX(), polygon.getPoint2().getY(), polygon.getPoint2().getZ()));
                    buffer.add(new Vector3(polygon.getPoint3().getX(), polygon.getPoint3().getY(), polygon.getPoint3().getZ()));
                } else {
                    continue;
                }

                float max = findMaxPoint(buffer, Condition.GET_Z);

                for (float k = max - sizeVoxel; k < max; k += sizeVoxel) {
                    points.add(new Vector3(i, j, k));
                }

            }
        }

        return fillWithCubs(points, sizeVoxel);
    }

    private MyPolygon getIntersectingPolygonWithSurface(float x, float y) {
        for (MyPolygon polygon : polygons) {
            List<Vector2> points = new LinkedList<>();

            points.add(new Vector2(polygon.getPoint1().getX(), polygon.getPoint1().getY()));
            points.add(new Vector2(polygon.getPoint2().getX(), polygon.getPoint2().getY()));
            points.add(new Vector2(polygon.getPoint3().getX(), polygon.getPoint3().getY()));

            if (calculateSquare(points.get(0), points.get(1), points.get(2)) != 0) {
                if (triangleContainThisPoint(points, new Vector2(x, y)) != 0) {
                    return polygon;
                }
            }
        }

        return null;
    }

    private float findMaxPoint(List<Vector3> points, Condition c) {
        float max = -Float.MAX_VALUE;
        for (Vector3 point : points) {
            max = getMax(point, max, c);
        }
        return max;
    }

    public List<MyPolygon> modelToVoxelOfPolygon(IModel model, float sizeVoxel) {
        polygons = model.getPolygons();
        List<MyPolygon> polygonsOfModel = new LinkedList<>();
        Parallelepiped voxel = getBoundaries(sizeVoxel);

        float length = (voxel.getRadius() * 2 - sizeVoxel) / 2;

        for (float x = voxel.getCenter().getX() - length; x <= voxel.getCenter().getX() + length; x += sizeVoxel) {
            for (float y = voxel.getCenter().getY() - length; y <= voxel.getCenter().getY() + length; y += sizeVoxel) {
                for (float z = voxel.getCenter().getZ() - length; z <= voxel.getCenter().getZ() + length; z += sizeVoxel) {
                    if (isInnerPoint(new Vector3(x, y, z))) {
                        polygonsOfModel.addAll(new Parallelepiped(sizeVoxel / 2, new Vector3(x, y, z)).getPolygons());
                    }
                }
            }
        }

        return polygonsOfModel;
    }

    public List<MyPolygon> surfaceToVoxelOfPolygon(IModel model) {

        return null;
    }
}
