public class NBody {
    public static double readRadius(String fileName) {
        In in = new In(fileName);
        in.readInt();
        double radius = in.readDouble();
        return radius;
    }

    public static Planet[] readPlanets(String fileName) {
        In in = new In(fileName);
        int numbers = in.readInt();
        in.readDouble();
        Planet[] planets = new Planet[numbers];
        for (int i = 0; i < numbers; ++i) {
            double xxPos, yyPos, xxVel, yyVel, mass;
            String imgFileName;
            xxPos = in.readDouble();
            yyPos = in.readDouble();
            xxVel = in.readDouble();
            yyVel = in.readDouble();
            mass = in.readDouble();
            imgFileName = in.readString();
            planets[i] = new Planet(xxPos, yyPos, xxVel, yyVel, mass, imgFileName);
        }
        return planets;
    }

    public static void main(String[] args) {
        // import data
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        double radius = readRadius(args[2]);
        Planet[] planets = readPlanets(args[2]);
        // store the net forces of each planet
        double[] xForces = new double[planets.length];
        double[] yForces = new double[planets.length];

        // open the double buffer
        StdDraw.enableDoubleBuffering();

        for (double t = 0.0; t < T; t += dt) {
            // calculate the net forces for each planet
            for (int i = 0; i < planets.length; ++i) {
                xForces[i] = planets[i].calcNetForceExertedByX(planets);
                yForces[i] = planets[i].calcNetForceExertedByY(planets);
                planets[i].update(dt, xForces[i], yForces[i]);
            }

            // draw the background
            StdDraw.setScale(-radius, radius);
            StdDraw.clear();

            StdDraw.picture(0, 0, "images/starfield.jpg");

            // draw the planet
            for (Planet p : planets) {
                p.draw();
            }
            StdDraw.show();
            StdDraw.pause(10);
        }

        StdOut.printf("%d\n", planets.length);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < planets.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                    planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
                    planets[i].yyVel, planets[i].mass, planets[i].imgFileName);
        }

    }
}
