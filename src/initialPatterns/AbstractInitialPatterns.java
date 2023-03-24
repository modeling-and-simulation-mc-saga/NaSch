package initialPatterns;

import java.io.PrintStream;
import java.util.Random;
import model.NaSch;

/**
 *
 * @author tadaki
 */
public class AbstractInitialPatterns {

    protected final int tMax = 50;
    protected int sysSize = 40;
    protected int numCars = 10;
    protected int vmax = 3;
    protected final double p = 0.;
    protected final NaSch sys;
    protected PrintStream out;

    public AbstractInitialPatterns(Random random) {
        sys = new NaSch(sysSize, numCars, vmax, p, random);
        out = System.out;
    }

    public void SimpleSimulation() {
        sys.initialize();
        for (int t = 0; t < tMax; t++) {
            sys.printState(out);
            sys.update();
        }
        out.println(delimitter());
    }

    protected void simulationWithPattern(InitialPattern initialPattern) {
        out.println();
        out.println(initialPattern.label());
        out.println();
        simulationWithPattern(initialPattern.s());
    }

    protected void simulationWithPattern(int[] s) {
        sys.initialize(s);
        for (int t = 0; t < tMax; t++) {
            sys.printState(out);
            sys.update();
        }
        out.println(delimitter());
    }

    public String delimitter() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < sysSize; i++) {
            sb.append("-");
        }
        return sb.toString();
    }

    public void setOut(PrintStream out) {
        this.out = out;
    }

}
