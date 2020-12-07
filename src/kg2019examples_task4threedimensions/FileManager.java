package kg2019examples_task4threedimensions;

import kg2019examples_task4threedimensions.math.Vector3;
import kg2019examples_task4threedimensions.third.MyPolygon;

import java.awt.*;
import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class FileManager {

    public List<MyPolygon> readFile(File file) {
        List<MyPolygon> polygons = new LinkedList<>();
        List<Vector3> points = new LinkedList<>();

        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader reader = new BufferedReader(fileReader);
            String line = reader.readLine();
            while (line != null) {
                if (line.startsWith("#")) {
                    line = reader.readLine();
                    continue;
                }

                if (line.startsWith("v")) {
                    if (line.startsWith("vt") || line.startsWith("vn")) {
                        line = reader.readLine();
                        continue;
                    }

                    String newLine = line.replace('v', ' ');
                    String[] string = newLine.split(" ");
                    List<Float> coordinates = new LinkedList<>();

                    for (String s : string) {
                        if (s != null && s.length() > 0) {
                            coordinates.add(Float.parseFloat(s));
                        }

                    }

                    points.add(new Vector3(coordinates.get(0), coordinates.get(1), coordinates.get(2)));
                    coordinates.clear();
                }

                line = reader.readLine();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return readPolygons(file, points, polygons);
    }

    private List<MyPolygon> readPolygons(File file, List<Vector3> points, List<MyPolygon> polygons) {
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader reader = new BufferedReader(fileReader);
            String line = reader.readLine();

            while (line != null) {
                if (line.startsWith("#") || line.startsWith("v")) {
                    line = reader.readLine();
                    continue;
                }

                if (line.startsWith("f")) {
                    String newLine = line.replace('f', ' ');
                    String[] string = newLine.split(" ");
                    List<Integer> indexes = new LinkedList<>();

                    for (String s : string) {
                        if (s != null && s.length() > 0) {
                            String[] subString = s.split("/");
                            int index = Integer.parseInt(subString[0]);
                            indexes.add(index - 1);
                        }
                    }

                    int r = (int) (Math.random() * 255);
                    int g = (int) (Math.random() * 255);
                    int b = (int) (Math.random() * 255);

                    polygons.add(new MyPolygon(
                            points.get(indexes.get(0)),
                            points.get(indexes.get(1)),
                            points.get(indexes.get(2)), new Color(r, g, b)));

                    if (indexes.size() == 4) {
                        polygons.add(new MyPolygon(
                                points.get(indexes.get(0)),
                                points.get(indexes.get(2)),
                                points.get(indexes.get(3)), new Color(r, g, b)));
                    }

                    indexes.clear();
                }

                line = reader.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return polygons;
    }

    private int returnIndex() {
        return -1;
    }

    private String checkFileName(String fileName) {
        if (!fileName.endsWith(".obj")) {
            fileName += ".obj";
        }
        return fileName;
    }

    public void writeFile() {

    }

}
