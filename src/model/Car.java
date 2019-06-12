package model;

/**
 * 自動車のクラス
 */
public class Car {

    private int position;
    private int speed;
    private final int maxSpeed;
    private final double decelerationProbability;

    public Car(int maxSpeed, double decelerationProbability, int position) {
        this.position = position;
        this.decelerationProbability = decelerationProbability;
        this.maxSpeed = maxSpeed;
    }

    public Car(int maxSpeed, double deceleration, int position, int speed) {
        this.position = position;
        this.decelerationProbability = deceleration;
        this.maxSpeed = maxSpeed;
        this.speed = speed;
    }

    public int evalSpeed(int headway) {





        return speed;
    }

    public int move(int length) {
        position = (position + speed) % length;
        return position;
    }

    public int getPosition() {  return position;  }

    public int getSpeed() {  return speed;  }

}
