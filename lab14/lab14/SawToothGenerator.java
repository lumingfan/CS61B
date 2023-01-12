package lab14;

import lab14lib.Generator;

public class SawToothGenerator implements Generator {
    private int period;
    private int state;

    public SawToothGenerator(int period) {
        this.period = period;
        this.state = 0;
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
        return normalize(state);
    }
}
