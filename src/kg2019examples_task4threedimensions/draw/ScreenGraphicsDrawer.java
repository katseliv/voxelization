package kg2019examples_task4threedimensions.draw;

import kg2019examples_task4threedimensions.screen.ScreenConverter;
import kg2019examples_task4threedimensions.third.PolyLine3D;

import java.awt.*;
import java.util.List;
import java.util.*;

public abstract class ScreenGraphicsDrawer implements IDrawer {
    private final ScreenConverter screenConverter;
    private final Graphics2D graphics2D;

    public ScreenGraphicsDrawer(ScreenConverter sc, Graphics2D gr) {
        this.screenConverter = sc;
        this.graphics2D = gr;
    }

    public Graphics2D getGraphics() {
        return graphics2D;
    }

    public ScreenConverter getScreenConverter() {
        return screenConverter;
    }

    @Override
    public void draw(Collection<PolyLine3D> polyLines) {
        List<PolyLine3D> lines = new LinkedList<>();
        IFilter<PolyLine3D> filter = getFilter();
        for (PolyLine3D pl : polyLines) {
            if (filter.permit(pl))
                lines.add(pl);
        }
        PolyLine3D[] arr = lines.toArray(new PolyLine3D[0]);
        Arrays.sort(arr, getComparator());
        for (PolyLine3D pl : arr) {
            oneDraw(pl);
        }
    }

    @Override
    public void clear(int color) {
        Graphics2D g = getGraphics();
        Color c = g.getColor();
        g.setColor(new Color(color));
        g.fillRect(0, 0, screenConverter.getWs(), screenConverter.getHs());
        g.setColor(c);
    }

    protected abstract void oneDraw(PolyLine3D polyline);

    protected abstract IFilter<PolyLine3D> getFilter();

    protected abstract Comparator<PolyLine3D> getComparator();
}
