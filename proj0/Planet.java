public class Planet {
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;

    static final double G = 6.67 * 1e-11;

    public Planet(double xP, double yP, double xV, double yV, double m, String img) {
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    }

    public Planet(Planet p) {
        xxPos = p.xxPos;
        yyPos = p.yyPos;
        xxVel = p.xxVel;
        yyVel = p.yyVel;
        mass = p.mass;
        imgFileName = p.imgFileName;
    }

    public double calcDistance(Planet p) {
        return Math.sqrt((xxPos - p.xxPos) * (xxPos - p.xxPos) + (yyPos - p.yyPos) * (yyPos - p.yyPos));
    }

    public double calcForceExertedBy(Planet p) {
        double dis = calcDistance(p);
        return G * mass * p.mass / (dis * dis);
    }

    public double calcForceExertedByX(Planet p) {
        double dis = calcDistance(p);
        double force = calcForceExertedBy(p);
        return (p.xxPos - xxPos) / dis * force;
    }

    public double calcForceExertedByY(Planet p) {
        double dis = calcDistance(p);
        double force = calcForceExertedBy(p);
        return (p.yyPos - yyPos) / dis * force;
    }

    public double calcNetForceExertedByX(Planet[] allPlanets) {
        double res = 0;
        for (Planet p : allPlanets) {
            if (!this.equals(p)) {
                res += calcForceExertedByX(p);
            }
        }
        return res;
    }

    public double calcNetForceExertedByY(Planet[] allPlanets) {
        double res = 0;
        for (Planet p : allPlanets) {
            if (!this.equals(p)) {
                res += calcForceExertedByY(p);
            }
        }
        return res;
    }

    public void update(double dt, double forceX, double forceY) {
        double accelerateX = forceX / mass;
        double accelerateY = forceY / mass;
        xxVel += accelerateX * dt;
        yyVel += accelerateY * dt;
        xxPos += xxVel * dt;
        yyPos += yyVel * dt;
    }

}