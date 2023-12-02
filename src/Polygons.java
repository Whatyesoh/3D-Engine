import java.awt.*;

public class Polygons {

    Polygon p;
    Color c;
    double distance = 0;
    double aveZ ;

    public Polygons(double[]x, double[]y, Color c) {
        p = new Polygon();
        for(int i = 0; i < x.length; i++)
            p.addPoint((int)x[i],(int)y[i]);
        p.npoints = x.length;
        this.c=c;
    }
    void drawPolygon(Graphics g) {
        g.setColor(c);
        if(aveZ>Screen.cZ) {
            g.fillPolygon(p);
            g.setColor(Color.black);
            g.drawPolygon(p);
        }
    }
}
