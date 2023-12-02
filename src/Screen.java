import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class Screen extends JPanel implements KeyListener {

    public static ArrayList<Integer>keys = new ArrayList<>();
    static ArrayList<Polygons> drawablePolygons = new ArrayList<>();
    static ArrayList<Polygon3D> dpolygons = new ArrayList<>();;
    double SleepTime = 1000/30, LastRefresh = 0;
    static double cX = 0, cY = 3, cZ = 0, horizAngle=0, screenZ=-500;
    public static double WIDTH = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    public static double HEIGHT = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
    int mouseLock = 1;
    double oldTime;
    int [] NewOrder;

    public Screen() {
        oldTime = System.nanoTime();

        dpolygons.add(new Polygon3D(new double[]{-2,2,2,-2},new double[]{-2,-2,2,2},new double[]{10,10,10,10},Color.red));
        dpolygons.add(new Polygon3D(new double[]{-2,2,2,-2},new double[]{-2,-2,2,2},new double[]{14,14,14,14},Color.orange));
        dpolygons.add(new Polygon3D(new double[]{-2,-2,-2,-2},new double[]{-2,2,2,-2},new double[]{10,10,14,14},Color.yellow));
        dpolygons.add(new Polygon3D(new double[]{2,2,2,2},new double[]{-2,2,2,-2},new double[]{10,10,14,14},Color.green));
        dpolygons.add(new Polygon3D(new double[]{-2,2,2,-2},new double[]{2,2,2,2},new double[]{10,10,14,14},Color.blue));
        dpolygons.add(new Polygon3D(new double[]{-2,2,2,-2},new double[]{-2,-2,-2,-2},new double[]{10,10,14,14},Color.magenta));


        for (int i = 0; i < 4; i++) {
            dpolygons.add(new Polygon3D(new double[]{0+i*2, 0+i*2, 2+i*2, 2+i*2}, new double[]{0, 2, 2, 0}, new double[]{-10, -10, -10, -10}, Color.red));
        }
        addKeyListener(this);
        setFocusable(true);
    }
    public void paintComponent(Graphics g) {
        if(mouseLock == 1) {
            Point a = MouseInfo.getPointerInfo().getLocation();
            if (a.getX() != 500) {
                horizAngle -= ((a.getX() - 500) / 10) * .05;
            }
            try {
                Robot robot = new Robot();
                robot.mouseMove(500, 500);
            } catch (AWTException e1) {
                e1.printStackTrace();
            }
        }

        if (keys.contains(18)) {
            mouseLock = (mouseLock - 1) * -1;
        }
        if (keys.contains(27)) {
            System.exit(0);
        }
        if (keys.contains(87)) {
            cZ+=.02*Math.cos(horizAngle);
            cX+=.02*Math.sin(horizAngle);
        }
        if (keys.contains(83)) {
            cZ-=.02*Math.cos(horizAngle);
            cX-=.02*Math.sin(horizAngle);
        }
        if (keys.contains(65)) {
            cZ-=.02*Math.sin(horizAngle);
            cX+=.02*Math.cos(horizAngle);
        }
        if (keys.contains(68)) {
            cZ+=.02*Math.sin(horizAngle);
            cX-=.02*Math.cos(horizAngle);
        }
        g.setColor(new Color(50,200,255));
        g.fillRect(0,0,4000,4000);
        for(int i = 0; i < dpolygons.size(); i++) {
            dpolygons.get(i).updatePolygon();
        }
        setOrder();
        for(int i = 0; i < drawablePolygons.size(); i++) {
            drawablePolygons.get(NewOrder[i]).drawPolygon(g);
        }
        g.setColor(Color.black);
        g.drawString("X: "+(Math.round(cX*100))/100.0,1,11);
        g.drawString("Y: "+Math.round(cY*100)/100.0,1,21);
        g.drawString("Z: "+Math.round(cZ*100)/100.0,1,31);
        g.drawString("FPS: "+Math.round(getFPS(oldTime)*100)/100.0,1,41);
        repaint();

    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (!keys.contains(e.getKeyCode())) {
            keys.add(e.getKeyCode());
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys.remove((Integer) e.getKeyCode());
    }
    public void draw() {
        repaint();
    }
    void setOrder() {
        double[] k = new double[drawablePolygons.size()];
        NewOrder = new int [drawablePolygons.size()];
        for(int i = 0; i < drawablePolygons.size(); i ++) {
            k[i] = drawablePolygons.get(i).distance;
            NewOrder[i]=i;
        }

        double temp;
        int tempr;
        for(int a = 0; a<k.length-1;a++) {
            for(int b = 0; b < k.length-1; b++) {
                if(k[b]<k[b+1]) {
                    temp = k[b];
                    tempr = NewOrder[b];
                    NewOrder[b] = NewOrder[b+1];
                    k[b] = k[b+1];
                    NewOrder[b+1] = tempr;
                    k[b+1]=temp;
                }
            }
        }
    }
    double getFPS(double old_Time) {
        double newTime = System.nanoTime();
        double delta = newTime - old_Time;
        double fps = 1/(delta/1000000000);
        oldTime = newTime;
        return fps;
    }
}
