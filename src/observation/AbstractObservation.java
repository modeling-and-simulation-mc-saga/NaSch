package observation;

import java.awt.geom.Point2D;
import java.util.List;
import java.util.Random;
import model.NaSch;
import myLib.utils.Utils;

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
     * 平均量の取得
     *
     * @param numCar 車両数
     * @return 平均量
     */
    abstract public double calcValue(int numCar);

    /**
     * 初期化して緩和
     *
     * @param numCar 車両数
     * @param tRelax 緩和させる時間
     */
    protected void initializeAndRelax(int numCar, int tRelax) {
        sys.setNumCar(numCar);
        sys.randomInitialize();
        //緩和
        for (int t = 0; t < tRelax; t++) {
            sys.update();
        }
    }

    public void setNumSample(int numSample) {
        this.numSample = numSample;
    }

    /**
     * 平均量の取得
     *
     * @param dp 密度の間隔
     * @param tRelax 緩和を行う時間
     * @return 密度に対する平均量のリスト
     */
    public List<Point2D.Double> calcValues(double dp, int tRelax) {
        List<Point2D.Double> pList = Utils.createList();
        int k = (int) (1. / dp);
        pList.add(new Point2D.Double(0., 0.));
        for (int i = 1; i < k; i++) {
            double p = i * dp;//初期密度
            int numCar = (int) (sysSize * p);
            for (int j = 0; j < numSample; j++) {
                initializeAndRelax(numCar, tRelax);
                double value = calcValue(numCar);//平均量
                p = (double) numCar / sysSize;//実際の密度を再計算
                pList.add(new Point2D.Double(p, value));
            }
        }
        pList.add(new Point2D.Double(1., 0.));
        return pList;
    }
}
