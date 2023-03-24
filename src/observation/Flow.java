package observation;

import java.awt.geom.Point2D;
import java.io.PrintStream;
import java.io.IOException;
import java.util.List;
import java.util.Random;

/**
 * Flow
 *
 * @author tadaki
 */
public class Flow extends AbstractObservation {

    /**
     * Initializing by specifying system Size
     *
     * @param sysSize
     * @param vmax
     * @param decelerationProbability
     * @param random
     */
    public Flow(int sysSize, int vmax, double decelerationProbability,
            Random random) {
        super(sysSize, vmax, decelerationProbability, random);
    }

    /**
     * Evaluating flow
     *
     * @param numCar the number of cars
     * @return 
     */
    @Override
    public double calcValue(int numCar) {
        double v = sys.evalAverageSpeed();
        return v * numCar / sysSize;
    }

    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     */
    public static void main(String[] args) throws IOException {
        int n = 100;//number of sites
        int vmax = 5;
        double accel = 0.;//decelelation probability
//        double accel = 0.2;
        double dp = 0.01;
        int tmax = 100;
        Random random = new Random(48L);
        Flow sys = new Flow(n, vmax, accel, random);
        List<Point2D.Double> points = sys.calcValues(dp, tmax);
        try (PrintStream out = new PrintStream("fundamental-deterministic.txt")) {
            points.forEach(p->out.println(p.x+" "+p.y));
        }
        sys = new Flow(n, vmax, 0.2, random);
        points = sys.calcValues(dp, tmax);
        try (PrintStream out = new PrintStream("fundamental.txt")) {
            points.forEach(p->out.println(p.x+" "+p.y));
        }
    }

}
