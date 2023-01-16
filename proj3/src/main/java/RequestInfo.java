import java.util.Map;
public class RequestInfo {
    private static final String[] PARAMS = {"lrlon", "ullon", "lrlat", "ullat", "w", "h"};
    private double lrlon;
    private double ullon;
    private double lrlat;
    private double ullat;
    private double w;
    private double h;

    private double lonDPP;
    private boolean lonDPPCalculated;

    public RequestInfo() { }

    public RequestInfo(double lrlon, double ullon, double lrlat, double ullat, double w, double h) {
        this.lrlon = lrlon;
        this.ullon = ullon;
        this.lrlat = lrlat;
        this.ullat = ullat;
        this.w = w;
        this.h = h;
        this.lonDPP = 0.0;
        this.lonDPPCalculated = false;
    }

    public double getUllat() {
        return ullat;
    }

    public void setUllat(double ullat) {
        this.ullat = ullat;
    }

    public double getLrlon() {
        return lrlon;
    }

    public void setLrlon(double lrlon) {
        this.lrlon = lrlon;
    }

    public double getUllon() {
        return ullon;
    }

    public void setUllon(double ullon) {
        this.ullon = ullon;
    }

    public double getLrlat() {
        return lrlat;
    }

    public void setLrlat(double lrlat) {
        this.lrlat = lrlat;
    }

    public double getW() {
        return w;
    }

    public void setW(double w) {
        this.w = w;
    }

    public double getH() {
        return h;
    }

    public void setH(double h) {
        this.h = h;
    }

    public void setInfoByEntry(Map.Entry<String, Double> entry) {

        String item = entry.getKey();
        double value = entry.getValue();
        if (item.equals(PARAMS[0])) {
            setLrlon(value);
        } else if (item.equals(PARAMS[1])) {
            setUllon(value);
        } else if (item.equals(PARAMS[2])) {
            setLrlat(value);
        } else if (item.equals(PARAMS[3])) {
            setUllat(value);
        } else if (item.equals(PARAMS[4])) {
            setW(value);
        } else if (item.equals(PARAMS[5])) {
            setH(value);
        }
    }

    private double calLonDPP() {
        return (lrlon - ullon) / w;
    }

    public double getLonDPP() {
        if (lonDPPCalculated) {
            return lonDPP;
        }
        lonDPP = calLonDPP();
        lonDPPCalculated = true;
        return lonDPP;
    }
}
