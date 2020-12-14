package kg2019examples_task4threedimensions.third;

import kg2019examples_task4threedimensions.draw.IDrawer;
import kg2019examples_task4threedimensions.math.Vector3;
import kg2019examples_task4threedimensions.models.Line3D;
import kg2019examples_task4threedimensions.operations.VoxelOperation;

import java.awt.*;
import java.util.List;
import java.util.*;

/**
 * Описывает трёхмерную со всеми объектами на ней
 *
 * @author Alexey
 */
public class Scene {
    private final List<IModel> models = new ArrayList<>();

    public List<IModel> getModelsList() {
        return models;
    }

    private final int backgroundColor;

    /**
     * Создаём сцену с заданным фоном
     *
     * @param backgroundColorRGB цвет фона.
     */
    public Scene(int backgroundColorRGB) {
        this.backgroundColor = backgroundColorRGB;
        this.showAxes = false;
    }

    private boolean showAxes;

    public boolean isShowAxes() {
        return showAxes;
    }

    public void setShowAxes(boolean showAxis) {
        this.showAxes = showAxis;
    }

    public void showAxes() {
        this.showAxes = true;
    }

    public void hideAxes() {
        this.showAxes = false;
    }

    private static final List<Line3D> axes = Arrays.asList(
            new Line3D(new Vector3(0, 0, 0), new Vector3(5000, 0, 0)),
            new Line3D(new Vector3(0, 0, 0), new Vector3(0, 5000, 0)),
            new Line3D(new Vector3(0, 0, 0), new Vector3(0, 0, 5000))
    );

    /**
     * Рисуем сцену со всеми моделями
     *
     * @param drawer то, с помощью чего будем рисовать
     * @param cam    камера для преобразования координат
     */
    public void drawScene(IDrawer drawer, ICamera cam) {
        List<PolyLine3D> lines = new LinkedList<>();
        LinkedList<Collection<? extends IModel>> allModels = new LinkedList<>();
        allModels.add(models);
        /*Если требуется, то добавляем оси координат*/
//        if (isShowAxes())
//            allModels.add(axes);
        /*перебираем все полилинии во всех моделях*/
        for (Collection<? extends IModel> mc : allModels) {
            for (IModel m : mc) {
                for (PolyLine3D pl : m.getLines()) {
                    /*Все точки конвертируем с помощью камеры*/
                    List<Vector3> points = new LinkedList<>();
                    for (Vector3 v : pl.getPoints()) {
                        points.add(cam.w2s(v));
                    }
                    /*Создаём на их сонове новые полилинии, но в том виде, в котором их видит камера*/
                    lines.add(new PolyLine3D(points, pl.isClosed()));
                }
            }
        }

        /*Закрашиваем фон*/
        drawer.clear(backgroundColor);
        /*Рисуем все линии*/
        drawer.draw(lines);
    }

    public void drawSceneOfPolygons(IDrawer drawer, ICamera cam) {
        List<MyPolygon> polygons = new LinkedList<>();
        LinkedList<Collection<? extends IModel>> allModels = new LinkedList<>();

        allModels.add(models);
        /*Если требуется, то добавляем оси координат*/

        for (IModel m : axes) {
            for (MyPolygon polygon : m.getPolygons()) {
                Color color;
                /*Все точки конвертируем с помощью камеры*/
                List<Vector3> points = new LinkedList<>();
                points.add(cam.w2s(polygon.getPoint1()));
                points.add(cam.w2s(polygon.getPoint2()));
                points.add(cam.w2s(polygon.getPoint3()));

                /*Создаём на их сонове новые полилинии, но в том виде, в котором их видит камера*/
                if (polygon.getPoint2().getX() != 0) {
                    color = Color.RED;
                } else if (polygon.getPoint2().getY() != 0) {
                    color = Color.GREEN;
                } else {
                    color = Color.BLUE;
                }

                polygons.add(new MyPolygon(points.get(0), points.get(1), points.get(2), color));
            }
        }

        /*перебираем все полигоны во всех моделях*/
        for (Collection<? extends IModel> mc : allModels) {
            for (IModel m : mc) {
                for (MyPolygon polygon : m.getPolygons()) {
                    /*Все точки конвертируем с помощью камеры*/
                    List<Vector3> points = new LinkedList<>();
                    points.add(cam.w2s(polygon.getPoint1()));
                    points.add(cam.w2s(polygon.getPoint2()));
                    points.add(cam.w2s(polygon.getPoint3()));

                    /*Создаём на их сонове новые полилинии, но в том виде, в котором их видит камера*/
                    polygons.add(new MyPolygon(points.get(0), points.get(1), points.get(2), polygon.getColor()));
                }
            }
        }

        /*Закрашиваем фон*/
        drawer.clear(backgroundColor);
        /*Рисуем все линии*/
        drawer.drawPolygon(polygons);
    }
}
