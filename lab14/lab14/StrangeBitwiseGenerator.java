package lab14;

import lab14lib.Generator;

public class StrangeBitwiseGenerator implements Generator {
    private int period;
    private int state;

    public StrangeBitwiseGenerator(int period) {
        this.period = period;
        this.state = 0;
    }

    private double normalize(int newState) {
        return newState * 2.0 / (period - 1) - 1.0;
    }

    @Override
    public double next() {
        state = state + 1;
        int weirdState = state & (state >> 7) % period;
        return normalize(weirdState);
    }

}
