package kg2019examples_task4threedimensions.draw;

import kg2019examples_task4threedimensions.third.MyPolygon;
import kg2019examples_task4threedimensions.third.PolyLine3D;

import java.util.Collection;

public interface IDrawer {

    void clear(int color);

    void draw(Collection<PolyLine3D> polyLines);

    void drawPolygon(Collection<MyPolygon> polygons);

}
