package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import myLib.utils.Utils;

/**
 * Nagel-Schreckenberg traffic flow model
 */
public class NaSch {

    private final int sysSize;
    private double decelerationProbability;
    private int maxSpeed;
    private int numCars;
    private List<Car> carList;
    private final Random random;

    public NaSch(int sysSize, int numCars, int maxSpeed,
            double decelerationProbability, Random random) {
        this.random = random;
        if (sysSize < numCars) {
            throw new IllegalArgumentException();
        }
        this.sysSize = sysSize;
        this.maxSpeed = maxSpeed;
        this.numCars = numCars;
        this.decelerationProbability = decelerationProbability;
    }

    /**
     * 初期化：先頭から詰め込み(速度ゼロ)
     *
     * インデクスが大きい車両ほど位置座標が大きくなければならない
     */
    public void initialize() {
        carList = Utils.createList();
        for (int i = 0; i < numCars; i++) {
            int x = i;
            carList.add(new Car(maxSpeed, decelerationProbability, x, 0, random));
        }
    }

    public void randomInitialize() {
        carList = Utils.createList();
        int randomList[] = createRandomPosition(sysSize, numCars);
        Arrays.sort(randomList);
        for (int i = 0; i < randomList.length; i++) {
            carList.add(new Car(maxSpeed, decelerationProbability,
                    randomList[i], 0, random));
        }
    }

    /**
     * generate n random numbers in [0,nCell) Each random numbers appears once.
     *
     * @param nCell
     * @param n
     * @return
     */
    private int[] createRandomPosition(int nCell, int n) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < nCell; i++) {
            list.add(i);
        }
        int count = 0;
        int c[] = new int[n];
        while (count < n) {
            int k = random.nextInt(list.size());
            int x = list.remove(k);
            c[count] = x;
            count++;
        }
        return c;
    }

    public void initialize(int[] speeds) {
        carList = Utils.createList();
        int count = 0;
        for (int i = 0; i < Math.min(speeds.length, sysSize); i++) {
            if (speeds[i] >= 0) {
                Car car = new Car(maxSpeed, decelerationProbability,
                        i, speeds[i], random);
                carList.add(car);
                count++;
            }
        }
        this.numCars = count;
    }

    public void homogeneousInitialize() {
        carList = Utils.createList();
        int s = (int) ((double) sysSize / numCars);
        for (int i = 0; i < numCars; i++) {
            int x = i * s;
            Car car = new Car(maxSpeed, decelerationProbability, x, s, random);
            carList.add(car);
        }
    }

    public void update() {
        for (int i = 0; i < numCars; i++) {
            int j = (i + 1) % numCars;//先行車両はインデクスが大きい方
            int headway = carList.get(j).getPosition()
                    - carList.get(i).getPosition();
            headway = (headway + sysSize) % sysSize;
            carList.get(i).evalSpeed(headway);
        }
        carList.forEach(car -> car.move(sysSize));
    }

    /**
     * 平均速度
     *
     * @return
     */
    public double evalAverageSpeed() {
        double a = 0.;
        double count = 0;
        for (Car car : carList) {
            a += car.getSpeed();
            count++;
        }
        return a / count;
    }

    public List<Integer> getPositions() {
        List<Integer> list = Utils.createList();
        carList.stream().forEach((car) -> {
            list.add(car.getPosition());
        });
        return list;
    }

    public List<Integer> getSpeeds() {
        List<Integer> list = Utils.createList();
        carList.stream().forEach((car) -> {
            list.add(car.getSpeed());
        });
        return list;
    }

    public void printState(PrintStream out) {
        out.println(state2String());

    }

    public void setDecelerationProbability(double decelerationProbability) {
        this.decelerationProbability = decelerationProbability;
        initialize();
    }

    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
        initialize();
    }

    public void setNumCar(int numCar) {
        this.numCars = numCar;
        initialize();
    }

    public String state2String() {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < sysSize; i++) {
            str.append(" ");
        }
        carList.forEach(
                (c) -> {
                    int position = c.getPosition();
                    int speed = c.getSpeed();
                    str.replace(position,
                            position + 1, String.valueOf(speed));
                });
        return str.toString();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException {
        int tMax = 100;
        int sysSize = 60;
        int numCars = 15;
        int vmax = 5;
        double decelerationProbability = 0.1;
        NaSch sys = new NaSch(sysSize, numCars, vmax, decelerationProbability,
                new Random(48L));
        sys.randomInitialize();
        String filename = "NSch-4.txt";
        PrintStream out = new PrintStream(new File(filename));
        for (int t = 0; t < tMax; t++) {
            sys.printState(out);
            sys.update();
        }
    }

}
