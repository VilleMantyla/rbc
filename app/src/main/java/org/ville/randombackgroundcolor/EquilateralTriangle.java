package org.ville.randombackgroundcolor;

/**
 * Created by Ville on 20.1.2017.
 */

public class EquilateralTriangle {

    private  int[] vertex;

    public EquilateralTriangle(int[] v) {
        vertex = v;
    }

    public EquilateralTriangle(float[] v) {
        vertex = new int[6];
        for(int i=0;i<v.length;i++) {
            vertex[i] = Math.round(v[i]);
        }
    }

    /**
     * Creates an equilateral triangle that sits on its side.
     * @param peak
     * @param sideLength
     */
    public EquilateralTriangle(int[] peak, double sideLength) {
        vertex = new int[6];
        double height = (Math.sqrt(3.0)/2)*sideLength;
        vertex[0] = peak[0]; vertex[1] = peak[1];
        vertex[2] = peak[0]-(int)sideLength/2; vertex[3] = peak[1]+(int)height;
        vertex[4] = peak[0]+(int)sideLength/2; vertex[5] = peak[1]+(int)height;
    }


    public int[] getVertex() {
        return vertex;
    }

    public double getSideLength() {
        double length = Math.sqrt((Math.pow((vertex[0] - vertex[2]), 2) + Math.pow((vertex[1] - vertex[3]), 2)));

        return length;
    }

    public double getHeight() {
        return (((Math.sqrt(3.0))/2))*getSideLength();
    }

    public int[] getPeak() {
        return new int[]{vertex[0], vertex[1]};
    }

    public String toString() {
        StringBuilder str = new StringBuilder();
        for(int i=0; i<vertex.length; i+=2)
        {
            str.append("(" + vertex[i] + ", " + vertex[i+1] + ") ");
        }
        str.deleteCharAt(str.length()-1);
        return str.toString();
    }

}
