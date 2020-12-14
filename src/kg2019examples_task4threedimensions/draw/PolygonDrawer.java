package kg2019examples_task4threedimensions.draw;

import kg2019examples_task4threedimensions.math.Vector3;
import kg2019examples_task4threedimensions.screen.ScreenConverter;
import kg2019examples_task4threedimensions.screen.ScreenCoordinates;
import kg2019examples_task4threedimensions.screen.ScreenPoint;
import kg2019examples_task4threedimensions.third.MyPolygon;
import kg2019examples_task4threedimensions.third.PolyLine3D;

import java.awt.*;
import java.util.*;
import java.util.List;

public class PolygonDrawer implements IDrawer {
    private final ScreenConverter screenConverter;
    private final Graphics2D graphics2D;

    public PolygonDrawer(ScreenConverter sc, Graphics2D gr) {
        this.screenConverter = sc;
        this.graphics2D = gr;
    }

    public Graphics2D getGraphics() {
        return graphics2D;
    }

    public ScreenConverter getScreenConverter() {
        return screenConverter;
    }

    @Override
    public void clear(int color) {
        Graphics2D g = getGraphics();
        Color c = g.getColor();
        g.setColor(new Color(color));
        g.fillRect(0, 0, screenConverter.getWs(), screenConverter.getHs());
        g.setColor(c);
    }

    @Override
    public void draw(Collection<PolyLine3D> polyLines) {

    }

    @Override
    public void drawPolygon(Collection<MyPolygon> polygons) {
        List<MyPolygon> myPolygons = new LinkedList<>();
        IFilter<MyPolygon> filter = getFilter();
        for (MyPolygon polygon : polygons) {
            if (filter.permit(polygon))
                myPolygons.add(polygon);
        }

        MyPolygon[] array = myPolygons.toArray(new MyPolygon[0]);
        Arrays.sort(array, getComparator());
        for (MyPolygon polygon : array) {
            oneDraw(polygon);
        }

    }

    protected void oneDraw(MyPolygon polygon) {
        LinkedList<ScreenPoint> points = new LinkedList<>();

        points.add(getScreenConverter().r2s(polygon.getPoint1()));
        points.add(getScreenConverter().r2s(polygon.getPoint2()));
        points.add(getScreenConverter().r2s(polygon.getPoint3()));

        getGraphics().setColor(polygon.getColor());

        if (points.size() < 3) {
            if (points.size() > 0)
                getGraphics().drawRect(points.get(0).getI(), points.get(0).getJ(), 1, 1);
            return;
        }

        ScreenCoordinates coordinates = new ScreenCoordinates(points);
        getGraphics().drawPolygon(coordinates.getXx(), coordinates.getYy(), coordinates.size());

    }

    protected void oneDraw(PolyLine3D polyline) {
        LinkedList<ScreenPoint> points = new LinkedList<>();
        /*переводим все точки в экранные*/
        for (Vector3 v : polyline.getPoints()) {
            points.add(getScreenConverter().r2s(v));
        }
        getGraphics().setColor(Color.BLACK);
        /*если точек меньше двух, то рисуем отдельными алгоритмами*/
        if (points.size() < 2) {
            if (points.size() > 0) {
                getGraphics().fillRect(points.get(0).getI(), points.get(0).getJ(), 1, 1);
            }
            return;
        }
        /*создаём хранилище этих точек в виде двух массивов*/
        ScreenCoordinates coordinates = new ScreenCoordinates(points);
        /*если линия замкнута - рисем полиго, иначе - полилинию*/
        if (polyline.isClosed()) {
            getGraphics().drawPolygon(coordinates.getXx(), coordinates.getYy(), coordinates.size());
        } else {
            getGraphics().drawPolyline(coordinates.getXx(), coordinates.getYy(), coordinates.size());
        }
    }

    protected IFilter<MyPolygon> getFilter() {
        return polygon -> true;
    }

    protected Comparator<MyPolygon> getComparator() {
        return new Comparator<MyPolygon>() {
            private static final float EPSILON = 1e-10f;

            @Override
            public int compare(MyPolygon o1, MyPolygon o2) {
                float d = o1.avgZ() - o2.avgZ();
                if (-EPSILON < d && d < EPSILON)
                    return 0;
                return d < 0 ? -1 : 1;
            }
        };
    }
}
