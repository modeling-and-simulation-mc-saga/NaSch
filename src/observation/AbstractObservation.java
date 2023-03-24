package observation;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import model.NaSch;

/**
 *
 * @author tadaki
 */
public abstract class AbstractObservation {

    protected NaSch sys;
    protected int sysSize;
    protected int numSample = 4;

    public AbstractObservation(int sysSize, int vmax, double p, Random random) {
        sys = new NaSch(sysSize, 0, vmax, p, random);
        this.sysSize = sysSize;
    }

    /**
     * Get average value
     *
     * @param numCar the number of cars
     * @return 
     */
    abstract public double calcValue(int numCar);

    /**
     * Initialize and relax
     *
     * @param numCar the number of cars
     * @param tRelax relaxing time
     */
    protected void initializeAndRelax(int numCar, int tRelax) {
        sys.setNumCar(numCar);
        sys.randomInitialize();
        //relaxing
        for (int t = 0; t < tRelax; t++) {
            sys.update();
        }
    }

    public void setNumSample(int numSample) {
        this.numSample = numSample;
    }

    /**
     * getting average value
     *
     * @param dp changing step in density
     * @param tRelax relaxing time
     * @return 
     */
    public List<Point2D.Double> calcValues(double dp, int tRelax) {
        List<Point2D.Double> pList = Collections.synchronizedList(new ArrayList<>());
        int k = (int) (1. / dp);
        pList.add(new Point2D.Double(0., 0.));
        for (int i = 1; i < k; i++) {
            double p = i * dp;//density
            int numCar = (int) (sysSize * p);
            for (int j = 0; j < numSample; j++) {
                initializeAndRelax(numCar, tRelax);
                double value = calcValue(numCar);
                p = (double) numCar / sysSize;
                pList.add(new Point2D.Double(p, value));
            }
        }
        pList.add(new Point2D.Double(1., 0.));
        return pList;
    }
}
