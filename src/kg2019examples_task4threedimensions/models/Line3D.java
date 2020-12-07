/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kg2019examples_task4threedimensions.models;

import kg2019examples_task4threedimensions.math.Vector3;
import kg2019examples_task4threedimensions.third.IModel;
import kg2019examples_task4threedimensions.third.MyPolygon;
import kg2019examples_task4threedimensions.third.PolyLine3D;

import java.awt.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Описывает трёхмерный отрезок
 *
 * @author Alexey
 */
public class Line3D implements IModel {
    private final Vector3 p1;
    private final Vector3 p2;

    public Line3D(Vector3 p1, Vector3 p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    public Vector3 getP1() {
        return p1;
    }

    public Vector3 getP2() {
        return p2;
    }

    @Override
    public List<PolyLine3D> getLines() {
        return Collections.singletonList(new PolyLine3D(Arrays.asList(p1, p2), false));
    }

    @Override
    public List<MyPolygon> getPolygons() {
        List<MyPolygon> polygons = new LinkedList<>();
        polygons.add(new MyPolygon(p1, p2, p1, Color.BLACK));
        return polygons;
    }

}
