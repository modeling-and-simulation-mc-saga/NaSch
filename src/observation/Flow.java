package observation;

import java.awt.geom.Point2D;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import myLib.utils.FileIO;

/**
 * 流量を求めるクラス
 *
 * @author tadaki
 */
public class Flow extends AbstractObservation {

    /**
     * セルの数を与えて初期化するコンストラクタ
     *
     * @param sysSize
     * @param vmax
     * @param decelerationProbability
     */
    public Flow(int sysSize, int vmax, double decelerationProbability,
            Random random) {
        super(sysSize, vmax, decelerationProbability, random);
    }

    /**
     * 流量を求める
     *
     * @param numCar 車両数
     * @return 平均速度
     */
    @Override
    public double calcValue(int numCar) {
        double v = sys.evalAverageSpeed();
        return v * numCar / sysSize;//流量を返す
    }

    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     */
    public static void main(String[] args) throws IOException {
        int n = 100;//num site
        int vmax = 5;
        double accel = 0.;//減速確率
//        double accel = 0.2;//減速確率
        double dp = 0.01;
        int tmax = 100;
        Random random = new Random(48L);
        Flow sys = new Flow(n, vmax, accel, random);
        List<Point2D.Double> points = sys.calcValues(dp, tmax);
        try (BufferedWriter out = FileIO.openWriter("fundamental-deterministic.txt")) {
            for (Point2D.Double p : points) {
                FileIO.writeSSV(out, p.x, p.y);
            }
        }
        sys = new Flow(n, vmax, 0.2, random);
        points = sys.calcValues(dp, tmax);
        try (BufferedWriter out = FileIO.openWriter("fundamental.txt")) {
            for (Point2D.Double p : points) {
                FileIO.writeSSV(out, p.x, p.y);
            }
        }
    }

}
