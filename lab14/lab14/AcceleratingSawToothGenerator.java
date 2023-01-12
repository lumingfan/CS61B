package lab14;

import lab14lib.Generator;

public class AcceleratingSawToothGenerator implements Generator {
    private int period;
    private int state;

    private double factor;


    public AcceleratingSawToothGenerator(int period, double factor) {
        this.period = period;
        this.state = 0;
        this.factor = factor;
    }

    private double normalize(int newState) {
        return newState * 2.0 / (period - 1) - 1.0;
    }

    private void nextState() {
        state = (state + 1) % period;
    }

    @Override
    public double next() {
        nextState();
        if (state == 0) {
            period = (int) (period * factor);
        }
        if (period == 1) {
            period = 2;
        }
        return normalize(state);
    }
}
