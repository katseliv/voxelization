package kg2019examples_task4threedimensions.draw;

import kg2019examples_task4threedimensions.math.Vector3;
import kg2019examples_task4threedimensions.screen.ScreenConverter;
import kg2019examples_task4threedimensions.screen.ScreenCoordinates;
import kg2019examples_task4threedimensions.screen.ScreenPoint;
import kg2019examples_task4threedimensions.third.MyPolygon;
import kg2019examples_task4threedimensions.third.PolyLine3D;

import java.awt.*;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;

public class SimpleEdgeDrawer extends ScreenGraphicsDrawer {

    public SimpleEdgeDrawer(ScreenConverter sc, Graphics2D g) {
        super(sc, g);
    }

    @Override
    protected void oneDraw(PolyLine3D polyline) {
        LinkedList<ScreenPoint> points = new LinkedList<>();
        /*переводим все точки в экранные*/
        for (Vector3 v : polyline.getPoints())
            points.add(getScreenConverter().r2s(v));
        getGraphics().setColor(Color.BLACK);
        /*если точек меньше двух, то рисуем отдельными алгоритмами*/
        if (points.size() < 2) {
            if (points.size() > 0)
                getGraphics().fillRect(points.get(0).getI(), points.get(0).getJ(), 1, 1);
            return;
        }
        /*создаём хранилище этих точек в виде двух массивов*/
        ScreenCoordinates coordinates = new ScreenCoordinates(points);
        /*если линия замкнута - рисем полиго, иначе - полилинию*/
        if (polyline.isClosed())
            getGraphics().drawPolygon(coordinates.getXx(), coordinates.getYy(), coordinates.size());
        else
            getGraphics().drawPolyline(coordinates.getXx(), coordinates.getYy(), coordinates.size());
    }

    @Override
    protected IFilter<PolyLine3D> getFilter() {
        return line -> true;
    }

    @Override
    protected Comparator<PolyLine3D> getComparator() {
        return new Comparator<PolyLine3D>() {
            private static final float EPSILON = 1e-10f;

            @Override
            public int compare(PolyLine3D o1, PolyLine3D o2) {
                float d = o1.avgZ() - o2.avgZ();
                if (-EPSILON < d && d < EPSILON)
                    return 0;
                return d < 0 ? -1 : 1;
            }
        };
    }

    @Override
    public void drawPolygon(Collection<MyPolygon> polygons) {
    }
}
