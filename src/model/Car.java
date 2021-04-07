package model;

import java.util.Random;

/**
 * 自動車のクラス
 */
public class Car {

    private int position;
    private int speed;
    private final int maxSpeed;
    private final double decelerationProbability;
    private final Random random;

    public Car(int maxSpeed, double decelerationProbability, int position, Random random) {
        this.position = position;
        this.decelerationProbability = decelerationProbability;
        this.maxSpeed = maxSpeed;
        this.random = random;
    }

    public Car(int maxSpeed, double deceleration, int position, int speed, Random random) {
        this.position = position;
        this.decelerationProbability = deceleration;
        this.maxSpeed = maxSpeed;
        this.speed = speed;
        this.random = random;
    }

    public int evalSpeed(int headway) {
        speed = Math.min(maxSpeed, speed + 1);
        speed = Math.min(speed, headway - 1);
        if (random.nextDouble() < decelerationProbability) {
            speed = Math.max(0, speed - 1);
        }
        return speed;
    }

    public int move(int length) {
        position = (position + speed) % length;
        return position;
    }

    public int getPosition() {
        return position;
    }

    public int getSpeed() {
        return speed;
    }

}
