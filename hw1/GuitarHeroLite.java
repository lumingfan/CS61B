/** A client that uses the synthesizer package to replicate a plucked guitar string sound */
public class GuitarHeroLite {
    private static final double CONCERT_A = 440.0;
    private static final double CONCERT_C = CONCERT_A * Math.pow(2, 3.0 / 12.0);

    private static synthesizer.GuitarString[] generateGuitarArr() {
        synthesizer.GuitarString[] guitarString = new synthesizer.GuitarString[37];
        for (int i = 0; i < 37; ++i) {
            double consert = 440.0 * Math.pow(2, (i - 24.0) / 12.0);
            guitarString[i] = new synthesizer.GuitarString(consert);
        }
        return guitarString;
    }
    public static void main(String[] args) {
        /* create two guitar strings, for concert A and C */
        synthesizer.GuitarString stringA = new synthesizer.GuitarString(CONCERT_A);
        synthesizer.GuitarString stringC = new synthesizer.GuitarString(CONCERT_C);
        synthesizer.GuitarString[] guitarString = generateGuitarArr();
        String keyboard = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";
        while (true) {

            /* check if the user has typed a key; if so, process it */
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                if (key == 'a') {
                    stringA.pluck();
                } else if (key == 'c') {
                    stringC.pluck();
                } else {
                    int index = keyboard.indexOf(key);
                    if (index != -1) {
                        guitarString[index].pluck();
                    }
                }
            }

        /* compute the superposition of samples */
            double sample = stringA.sample() + stringC.sample();
            for (int i = 0; i < 37; ++i) {
                sample += guitarString[i].sample();
            }

        /* play the sample on standard audio */
            StdAudio.play(sample);

        /* advance the simulation of each guitar string by one step */
            stringA.tic();
            stringC.tic();
            for (int i = 0; i < 37; ++i) {
                guitarString[i].tic();
            }
        }
    }
}

