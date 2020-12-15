package models;

import managers.FileManager;
import third.IModel;
import third.MyPolygon;
import third.PolyLine3D;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class Model implements IModel {
    List<PolyLine3D> lines = new LinkedList<>();
    List<MyPolygon> polygons;

    public Model(File file) {
        this.polygons = FileManager.readFile(file);
    }

    @Override
    public List<PolyLine3D> getLines() {
        return lines;
    }

    @Override
    public List<MyPolygon> getPolygons() {
        return polygons;
    }
}
