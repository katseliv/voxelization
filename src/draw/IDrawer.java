package draw;

import third.MyPolygon;
import third.PolyLine3D;

import java.util.Collection;

public interface IDrawer {

    void clear(int color);

    void draw(Collection<PolyLine3D> polyLines);

    void drawPolygon(Collection<MyPolygon> polygons);

}
