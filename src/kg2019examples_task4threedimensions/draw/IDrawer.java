/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kg2019examples_task4threedimensions.draw;

import kg2019examples_task4threedimensions.third.MyPolygon;
import kg2019examples_task4threedimensions.third.PolyLine3D;

import java.util.Collection;

/**
 * Интерфейс, описывающий в общем виде процесс рисования полилинии
 * @author Alexey
 */
public interface IDrawer {
    /**
     * Очищает область заданным цветом
     * @param color цвет
     */
    void clear(int color);
    
    /**
     * Рисует все полилинии
     * @param polyLines набор рисуемых полилиний.
     */
    void draw(Collection<PolyLine3D> polyLines);

    void drawPolygon(Collection<MyPolygon> polygons);

}
