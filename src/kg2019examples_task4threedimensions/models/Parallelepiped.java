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
import java.util.LinkedList;
import java.util.List;

/**
 * Описывает параллелипипед по двум диагональным точкам
 *
 * @author Alexey
 */
public class Parallelepiped implements IModel {
    private final Vector3 LTF;
    private final Vector3 RBN;
    private float radius;

    /**
     * Создаёт экземпляр параллелипипеда
     *
     * @param LTF Левая Верхняя Дальняя точка (Left Top Far)
     * @param RBN Правая Нижняя Ближняя точка (Right Bottom Near)
     */
    public Parallelepiped(Vector3 LTF, Vector3 RBN) {
        this.LTF = LTF;
        this.RBN = RBN;
        this.radius = LTF.length();
    }

    public Parallelepiped(Vector3 center, float radius) {
        this.LTF = new Vector3(
                center.getX() - radius,
                center.getY() - radius,
                center.getZ() - radius);
        this.RBN = new Vector3(
                center.getX() + radius,
                center.getY() + radius,
                center.getZ() + radius);
        this.radius = radius;
    }

    public float getRadius() {
        return radius;
    }

    public Vector3 getCenter() {
        return new Vector3(LTF.getX() - RBN.getX() / 2, LTF.getY() - RBN.getY() / 2, LTF.getZ() - RBN.getZ() / 2);
    }

    @Override
    public List<PolyLine3D> getLines() {
        LinkedList<PolyLine3D> lines = new LinkedList<>();
        /*Дальняя сторона (Z фиксирован и взят у LTF)*/
        lines.add(new PolyLine3D(Arrays.asList(
                new Vector3(LTF.getX(), LTF.getY(), LTF.getZ()),
                new Vector3(LTF.getX(), RBN.getY(), LTF.getZ()),
                new Vector3(RBN.getX(), RBN.getY(), LTF.getZ()),
                new Vector3(RBN.getX(), LTF.getY(), LTF.getZ())), true));
        /*Ближняя сторона  (Z фиксирован и взят у RBN)*/
        lines.add(new PolyLine3D(Arrays.asList(
                new Vector3(LTF.getX(), LTF.getY(), RBN.getZ()),
                new Vector3(LTF.getX(), RBN.getY(), RBN.getZ()),
                new Vector3(RBN.getX(), RBN.getY(), RBN.getZ()),
                new Vector3(RBN.getX(), LTF.getY(), RBN.getZ())), true));

        /*Верхняя сторона (Y фиксирован и взят у LTF)*/
        lines.add(new PolyLine3D(Arrays.asList(
                new Vector3(LTF.getX(), LTF.getY(), LTF.getZ()),
                new Vector3(LTF.getX(), LTF.getY(), RBN.getZ()),
                new Vector3(RBN.getX(), LTF.getY(), RBN.getZ()),
                new Vector3(RBN.getX(), LTF.getY(), LTF.getZ())), true));
        /*Нижняя сторона (Y фиксирован и взят у RBN)*/
        lines.add(new PolyLine3D(Arrays.asList(
                new Vector3(LTF.getX(), RBN.getY(), LTF.getZ()),
                new Vector3(LTF.getX(), RBN.getY(), RBN.getZ()),
                new Vector3(RBN.getX(), RBN.getY(), RBN.getZ()),
                new Vector3(RBN.getX(), RBN.getY(), LTF.getZ())), true));

        /*Левая сторона (X фиксирован и взят у LTF)*/
        lines.add(new PolyLine3D(Arrays.asList(
                new Vector3(LTF.getX(), LTF.getY(), LTF.getZ()),
                new Vector3(LTF.getX(), LTF.getY(), RBN.getZ()),
                new Vector3(LTF.getX(), RBN.getY(), RBN.getZ()),
                new Vector3(LTF.getX(), RBN.getY(), LTF.getZ())), true));
        /*Правая сторона (X фиксирован и взят у RBN)*/
        lines.add(new PolyLine3D(Arrays.asList(
                new Vector3(RBN.getX(), LTF.getY(), LTF.getZ()),
                new Vector3(RBN.getX(), LTF.getY(), RBN.getZ()),
                new Vector3(RBN.getX(), RBN.getY(), RBN.getZ()),
                new Vector3(RBN.getX(), RBN.getY(), LTF.getZ())), true));

        return lines;
    }

    @Override
    public List<MyPolygon> getPolygons() {
        List<MyPolygon> polygons = new LinkedList<>();
        /*Дальняя сторона (Z фиксирован и взят у LTF)*/
        polygons.add(new MyPolygon(
                new Vector3(LTF.getX(), LTF.getY(), LTF.getZ()),
                new Vector3(LTF.getX(), LTF.getY(), RBN.getZ()),
                new Vector3(LTF.getX(), RBN.getY(), RBN.getZ()), Color.BLACK));
        polygons.add(new MyPolygon(
                new Vector3(LTF.getX(), LTF.getY(), LTF.getZ()),
                new Vector3(LTF.getX(), RBN.getY(), LTF.getZ()),
                new Vector3(LTF.getX(), RBN.getY(), RBN.getZ()), Color.BLACK));
        /*Ближняя сторона  (Z фиксирован и взят у RBN)*/
        polygons.add(new MyPolygon(
                new Vector3(RBN.getX(), RBN.getY(), LTF.getZ()),
                new Vector3(RBN.getX(), RBN.getY(), RBN.getZ()),
                new Vector3(RBN.getX(), LTF.getY(), RBN.getZ()), Color.BLACK));
        polygons.add(new MyPolygon(
                new Vector3(RBN.getX(), RBN.getY(), LTF.getZ()),
                new Vector3(RBN.getX(), LTF.getY(), LTF.getZ()),
                new Vector3(RBN.getX(), LTF.getY(), RBN.getZ()), Color.BLACK));
        /*Верхняя сторона (Y фиксирован и взят у LTF)*/
        polygons.add(new MyPolygon(
                new Vector3(RBN.getX(), LTF.getY(), LTF.getZ()),
                new Vector3(LTF.getX(), LTF.getY(), LTF.getZ()),
                new Vector3(LTF.getX(), RBN.getY(), LTF.getZ()), Color.BLACK));
        polygons.add(new MyPolygon(
                new Vector3(RBN.getX(), LTF.getY(), LTF.getZ()),
                new Vector3(RBN.getX(), RBN.getY(), LTF.getZ()),
                new Vector3(LTF.getX(), RBN.getY(), LTF.getZ()), Color.BLACK));
        /*Нижняя сторона (Y фиксирован и взят у RBN)*/
        polygons.add(new MyPolygon(
                new Vector3(LTF.getX(), LTF.getY(), RBN.getZ()),
                new Vector3(RBN.getX(), LTF.getY(), RBN.getZ()),
                new Vector3(RBN.getX(), RBN.getY(), RBN.getZ()), Color.BLACK));
        polygons.add(new MyPolygon(
                new Vector3(LTF.getX(), LTF.getY(), RBN.getZ()),
                new Vector3(LTF.getX(), RBN.getY(), RBN.getZ()),
                new Vector3(RBN.getX(), RBN.getY(), RBN.getZ()), Color.BLACK));
        /*Левая сторона (X фиксирован и взят у LTF)*/
        polygons.add(new MyPolygon(
                new Vector3(RBN.getX(), LTF.getY(), LTF.getZ()),
                new Vector3(RBN.getX(), LTF.getY(), RBN.getZ()),
                new Vector3(LTF.getX(), LTF.getY(), RBN.getZ()), Color.BLACK));
        polygons.add(new MyPolygon(
                new Vector3(RBN.getX(), LTF.getY(), LTF.getZ()),
                new Vector3(LTF.getX(), LTF.getY(), LTF.getZ()),
                new Vector3(LTF.getX(), LTF.getY(), RBN.getZ()), Color.BLACK));
        /*Правая сторона (X фиксирован и взят у RBN)*/
        polygons.add(new MyPolygon(
                new Vector3(LTF.getX(), RBN.getY(), LTF.getZ()),
                new Vector3(LTF.getX(), RBN.getY(), RBN.getZ()),
                new Vector3(RBN.getX(), RBN.getY(), RBN.getZ()), Color.BLACK));
        polygons.add(new MyPolygon(
                new Vector3(LTF.getX(), RBN.getY(), LTF.getZ()),
                new Vector3(RBN.getX(), RBN.getY(), LTF.getZ()),
                new Vector3(RBN.getX(), RBN.getY(), RBN.getZ()), Color.BLACK));
        return polygons;
    }

}
