package third;

import java.util.List;

/**
 * Описывает в общем виде любую модель
 * @author Alexey
 */
public interface IModel {
    /**
     * Любая модель - это набор полилиний.
     * @return Списко полилиний модели.
     */
    List<PolyLine3D> getLines();

    List<MyPolygon> getPolygons();
}
