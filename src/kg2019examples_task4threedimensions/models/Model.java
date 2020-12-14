package kg2019examples_task4threedimensions.models;

import kg2019examples_task4threedimensions.managers.FileManager;
import kg2019examples_task4threedimensions.third.IModel;
import kg2019examples_task4threedimensions.third.MyPolygon;
import kg2019examples_task4threedimensions.third.PolyLine3D;

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
