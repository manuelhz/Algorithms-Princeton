import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import java.awt.Color;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


public class SeamCarver {
    private double[][] energy;
    private Picture picture;


    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        if (picture == null)
            throw new IllegalArgumentException("myException1");
        this.picture = new Picture(picture);
        this.energy = new double[this.picture.height()][this.picture.width()];
        calcEnergy();

    }
    private void calcEnergy() {
        for (int j = 0; j < width(); j++)
            for (int i = 0; i < height(); i++) {
                if (j == 0 || j == width() - 1 || i == 0 || i == height() - 1) {
                    energy[i][j] = 1000;
                }
                else {
                    energy[i][j] = Math.sqrt(dxSquared(j, i) + dySquared(j, i));
                }
            }
    }

    private double weight(int w) {
        int x, y;
        y = yCordOf(w);
        x = xCordOf(w);
        return energy(x, y);
    }

    // current picture
    public Picture picture() {
        return new Picture(picture);
    }

    // width of current picture
    public int width() {
        return energy[0].length;
    }

    // height of current picture
    public int height() {
        return energy.length;
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        if (!(x < width() && x >= 0 && y >= 0 && y < height()))
            throw new IllegalArgumentException("myException2");
        return energy[y][x];
    }
    private double dxSquared(int x, int y) {
        Color c1 = picture().get(x + 1, y);
        Color c2 = picture().get(x - 1, y);
        double rx = c1.getRed() - c2.getRed();
        double gx = c1.getGreen() - c2.getGreen();
        double bx = c1.getBlue() - c2.getBlue();
        return rx*rx + gx*gx + bx*bx;
    }
    private double dySquared(int x, int y) {
        Color c1 = picture().get(x, y + 1);
        Color c2 = picture().get(x, y - 1);
        double rx = c1.getRed() - c2.getRed();
        double gx = c1.getGreen() - c2.getGreen();
        double bx = c1.getBlue() - c2.getBlue();
        return rx*rx + gx*gx + bx*bx;
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        transpose();
        int[] seam = findVerticalSeam();
        transpose();
        return seam;
    }
    private void transpose() {
        double[][] tempE = new double[energy[0].length][energy.length];
        for (int i = 0; i < tempE.length; i++) {
            for (int j = 0; j < tempE[0].length; j++){
                tempE[i][j] = energy[j][i];
            }
        }
        energy = tempE;
    }
    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        int seam[];
        seam = new int[height()];
        int size = height() * width();
        Double distTo[] = new Double[size];
        int edgeTo[] = new int[size];
        for (int v = 0; v < size; v++) {
            if (v < width())
                distTo[v] = 0.0;
            else
                distTo[v] = Double.POSITIVE_INFINITY;
            edgeTo[v] = -1;
        }

        for (int i = 0; i < height() - 1; i++) {
            for (int j = 0; j < width(); j++) {
                int z = zCordOf(j, i);
                if (j > 0)
                    relax(z, zCordOf(j-1, i+1), edgeTo, distTo);
                relax(z, zCordOf(j, i+1), edgeTo, distTo);
                if (j < width() - 1)
                    relax(z, zCordOf(j+1, i+1), edgeTo, distTo);
            }
        }
        // find the last pixel of the minimal energy seam
        double minEnergy = Double.POSITIVE_INFINITY;
        int minIndex = -1;
        for (int j = 0; j < width(); j++) {
            int z = zCordOf(j, height()-1);
            if (distTo[z] < minEnergy) {
                minEnergy = distTo[z];
                minIndex = z;
            }
        }
        // find the minimal energy seam
        int index = height() - 1;
        while (edgeTo[minIndex] != -1) {
            seam[index] = xCordOf(minIndex);
            minIndex = edgeTo[minIndex];
            index--;
        }
        seam[index] = xCordOf(minIndex);
        return seam;
    }
    private void relax(int v, int w, int[] edgeTo, Double[] distTo) {
        if(distTo[w] > distTo[v] + weight(w)) {
            distTo[w] = distTo[v] + weight(w);
            edgeTo[w] = v;
        }
    }
    private int xCordOf(int z) {
        return z % width();
    }
    private int yCordOf(int z) {
        return z / width();
    }
    private int zCordOf(int x, int y) {
        return width() * y + x;
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        if (seam == null)
            throw new IllegalArgumentException("myException3");
        if (seam.length != width())
            throw new IllegalArgumentException("myException4");
        for (int i = 0; i < seam.length; i++) {
            if (seam[i] < 0 || seam[i] > height()-1)
                throw new IllegalArgumentException("myException5");
            if (i > 0)
                if (Math.abs(seam[i] - seam [i-1]) > 1)
                    throw new IllegalArgumentException("myException6");
        }
        if (height() <= 1)
            throw new IllegalArgumentException("myException7");

        Picture newPicture = new Picture(width(), height() - 1);
        double [][] newEnergy = new double[height()-1][width()];
        // first half of the new picture
        for (int j = 0; j < width(); j++) {
            for (int i = 0; i < seam[j]; i++) {
                newPicture.set(j, i, picture.get(j, i));
                newEnergy[i][j] = energy[i][j];
            }
            // border of seam picture and energy
//            Color colorTop = picture.get(j, seam[j]-1);
//            Color colorBottom = picture.get(j,seam[j]+1);
//            newPicture.set(j, seam[j], colorTop);
//            newPicture.set(j, seam[j]+1, colorBottom);
            if (seam[j]-2 >= 0)
                newEnergy[seam[j]-2][j] = recalcEnergy(seam[j] - 2, j);
            if (seam[j]-1 >= 0)
                newEnergy[seam[j]-1][j] = recalcEnergy(seam[j]-1, j);
            newEnergy[seam[j]][j] = recalcEnergy(seam[j], j);
            if (seam[j]+1 < height()-1)
                newEnergy[seam[j]+1][j] = recalcEnergy(seam[j] + 1, j);

            // second half of the picture
            for (int i = seam[j] + 1; i < height(); i++) {
                newPicture.set(j, i-1, picture.get(j, i));
                newEnergy[i-1][j] = energy[i][j];
            }
        }
        this.picture = newPicture;
        energy =  newEnergy;
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        if (seam == null)
            throw new IllegalArgumentException("myException8");
        if (seam.length != height())
            throw new IllegalArgumentException("myException9");
        for (int i = 0; i < seam.length; i++) {
            if (seam[i] < 0 || seam[i] > width()-1)
                throw new IllegalArgumentException("myException10");
            if (i > 0)
                if (Math.abs(seam[i] - seam [i-1]) > 1)
                    throw new IllegalArgumentException("myException11");
        }
        if (width() <= 1)
            throw new IllegalArgumentException("myException12");

        Picture newPicture = new Picture(width() - 1, height());
        double [][] newEnergy = new double[height()][width() - 1];
        for (int i = 0; i < height(); i++) {
            // first half of the resulting image
            for (int j = 0; j < seam[i]; j++) {
                newPicture.set(j, i, picture.get(j, i));
                newEnergy[i][j] = energy[i][j];
            }

            // border of seam picture and energy
//            Color colorLeft = picture.get(seam[i]-1, i);
//            Color colorRight = picture.get(seam[i]+1, i);
//            newPicture.set(seam[i], i, colorLeft);
//            newPicture.set(seam[i]+1, i, colorRight);
            if (seam[i]-2 >= 0)
                newEnergy[i][seam[i]-2] = recalcEnergy(i, seam[i] - 2);
            if (seam[i]-1 >= 0)
                newEnergy[i][seam[i]-1] = recalcEnergy(i, seam[i]-1);
            newEnergy[i][seam[i]] = recalcEnergy(i, seam[i] );
            if (seam[i]+1 < width()-1)
                newEnergy[i][seam[i]+1] = recalcEnergy(i, seam[i] + 1);

            // second half of resulting image
            for (int j = seam[i] + 1; j < width(); j++) {
                newPicture.set(j-1, i, picture.get(j, i));
                newEnergy[i][j-1] = energy[i][j];
            }
        }
        picture = newPicture;
        energy =  newEnergy;
    }
    private double recalcEnergy(int i, int j) {
        if (j == 0 || j == width() - 1 || i == 0 || i == height() - 1) {
            return 1000;
        }
        else {
            return Math.sqrt(dxSquared(j, i) + dySquared(j, i));
        }

    }

    //  unit testing (optional)
    public static void main(String[] args) {

        Picture picture = new Picture(args[0]);
        SeamCarver sc = new SeamCarver(picture);
        for (int i = 0; i < sc.height(); i++) {
            for (int j = 0; j < sc.width(); j++) {
                StdOut.print(Integer.toHexString(sc.picture().getRGB(j, i)) + " ");
            }
            StdOut.println();
        }
        StdOut.println();
        int seam[] = { 9, 9, 9, 9, 8, 8, 9 };
        sc.removeVerticalSeam(seam);
        for (int i = 0; i < sc.height(); i++) {
            for (int j = 0; j < sc.width(); j++) {
                StdOut.print(Integer.toHexString(sc.picture().getRGB(j, i)) + " ");
            }
            StdOut.println();
        }

        StdOut.println(sc.picture().height() + " " + sc.picture().width());
        sc.removeVerticalSeam(sc.findVerticalSeam());
        StdOut.println(sc.picture().height() + " " + sc.picture().width());
        sc.removeHorizontalSeam(sc.findHorizontalSeam());
        StdOut.println(sc.picture().height() + " " + sc.picture().width());
    }
}