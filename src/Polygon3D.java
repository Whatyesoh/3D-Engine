import java.awt.*;
import java.util.ArrayList;

public class Polygon3D {

    Color c;
    double[]x , y, z;
    int poly = 0;

    public Polygon3D(double[]x, double[]y,double[]z, Color c) {
        this.x=x;
        this.y=y;
        this.z=z;
        this.c=c;
        createPolygon();
    }
    void createPolygon() {
        double[] newX = new double[x.length];
        double[] newY = new double[x.length];
        poly = Screen.drawablePolygons.size();
        double[] newZ = new double[x.length];

        Screen.drawablePolygons.add(new Polygons(newX,newY,c));
        for(int i = 0; i < x.length; i++) {
            newX[i]=(x[i]-Screen.cX)*Math.cos(Screen.horizAngle)-(z[i]-Screen.cZ)*Math.sin(Screen.horizAngle)+Screen.cX;
            newZ[i]=(z[i]-Screen.cZ)*Math.cos(Screen.horizAngle)+(x[i]-Screen.cX)*Math.sin(Screen.horizAngle)+Screen.cZ;
            newY[i]= ((Screen.cY-y[i]))/(Screen.cZ-newZ[i])*(Screen.screenZ-newZ[i])+y[i]+Screen.HEIGHT/2;
            newX[i]= ((Screen.cX-newX[i])/(Screen.cZ-newZ[i]))*(Screen.screenZ-newZ[i])+newX[i]+Screen.WIDTH/2;
            Screen.drawablePolygons.get(poly).aveZ += newZ[i];
        }
        Screen.drawablePolygons.get(poly).aveZ /= newZ.length;
        Screen.drawablePolygons.set(poly,new Polygons(newX,newY,c));
        Screen.drawablePolygons.get(poly).distance = GetDist();
    }
    public void updatePolygon() {
        double[] newX = new double[x.length];
        double[] newY = new double[x.length];
        double[] newZ = new double[x.length];
        int totalZ = 0;
        Screen.drawablePolygons.set(poly,new Polygons(newX,newY,c));
        for(int i = 0; i < x.length; i++) {
            newX[i]=(x[i]-Screen.cX)*Math.cos(Screen.horizAngle)-(z[i]-Screen.cZ)*Math.sin(Screen.horizAngle)+Screen.cX;
            newZ[i]=(z[i]-Screen.cZ)*Math.cos(Screen.horizAngle)+(x[i]-Screen.cX)*Math.sin(Screen.horizAngle)+Screen.cZ;
            if(newZ[i]>Screen.cZ) {
                newY[i]= ((Screen.cY-y[i]))/(Screen.cZ-newZ[i])*(Screen.screenZ-newZ[i])+y[i]+Screen.HEIGHT/2;
                newX[i]= ((Screen.cX-newX[i])/(Screen.cZ-newZ[i]))*(Screen.screenZ-newZ[i])+newX[i]+Screen.WIDTH/2;
            }
            else {
                double tempScreen = newZ[i]-Screen.screenZ + newZ[i];
                newY[i]= ((Screen.cY-y[i]))/(Screen.cZ-newZ[i])*(tempScreen-newZ[i])+y[i]+Screen.HEIGHT/2;
                newX[i]= ((Screen.cX-newX[i])/(Screen.cZ-newZ[i]))*(tempScreen-newZ[i])+newX[i]+Screen.WIDTH/2;
            }

            Screen.drawablePolygons.get(poly).aveZ += newZ[i];
            /*
            if(newZ[i]<=Screen.cZ) {
                totalZ++;
            }
            if(Math.abs(newZ[i]-Screen.cZ)<=2) {
                if(newX[i]<0||newX[i]>Screen.WIDTH) {
                    totalZ++;
                }
            }

             */
        }
        int [] x = new int[newX.length];
        int [] y = new int[newY.length];
        for(int i = 0; i < newX.length; i++) {
            x[i]=((int)newX[i]);
            y[i]=((int)newY[i]);
        }
        Screen.drawablePolygons.get(poly).p = new Polygon(x,y,x.length);
        Screen.drawablePolygons.get(poly).distance = GetDist();
        Screen.drawablePolygons.get(poly).aveZ /= newZ.length;
        if(totalZ>=x.length/2.0) {
            Screen.drawablePolygons.get(poly).aveZ = -1000000000;
        }
    }
    private double GetDist() {
        double total = 0;
        for (int i = 0; i < x.length; i++) {
            total+=Math.sqrt((Screen.cX-x[i])*(Screen.cX-x[i])+(Screen.cY-y[i])*(Screen.cY-y[i])+(Screen.cZ-z[i])*(Screen.cZ-z[i]));
        }
        return total/3;
    }
}
