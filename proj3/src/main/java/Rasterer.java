import java.util.HashMap;
import java.util.Map;

/**
 * This class provides all code necessary to take a query box and produce
 * a query result. The getMapRaster method must return a Map containing all
 * seven of the required fields, otherwise the front end code will probably
 * not draw the output correctly.
 */
public class Rasterer {
    public static final double ROOT_ULLAT = 37.892195547244356, ROOT_ULLON = -122.2998046875,
            ROOT_LRLAT = 37.82280243352756, ROOT_LRLON = -122.2119140625;
    public static final int TILE_SIZE = 256;
    private static final double ROOT_LONDPP =  (ROOT_LRLON - ROOT_ULLON) / TILE_SIZE;
    private static final int MAX_DEPTH = 7;

    public Rasterer() {
        // YOUR CODE HERE
    }

    // if pass test, the modifier should be private
    public RequestInfo resolute(Map<String, Double> params) {
        RequestInfo info = new RequestInfo();
        for (Map.Entry<String, Double> entry : params.entrySet()) {
            info.setInfoByEntry(entry);
        }
        return info;
    }

    // the following three function still not test, maybe false
    private boolean validateLon(RequestInfo info) {
        return info.getUllon() <= ROOT_LRLON && info.getLrlon() >= ROOT_ULLON;
    }
    private boolean validateLat(RequestInfo info) {
        return info.getUllat() >= ROOT_LRLAT &&  info.getLrlat() <= ROOT_ULLAT;
    }
    private boolean validate(RequestInfo info) {
        return validateLat(info) && validateLon(info);
    }



    private static double getRootLonDPP() { return ROOT_LONDPP; }

    // if pass test, the modifier should be private
    private static double getLonDPP(int depth) {
        return getRootLonDPP() / Math.pow(2, depth);
    }

    public static int calDepth(double target_lonDPP) {
        for (int i = 0; i <= MAX_DEPTH ; ++i) {
            if (getLonDPP(i) < target_lonDPP) {
                return i;
            }
        }
        return MAX_DEPTH;
    }

    public int calTileNum(int depth) {
        return (int) Math.pow(2, depth);
    }

    public int calIndex(double now, double root, double std) {
        double dis = now - root;
        if (dis < 0) {
            return 0;
        }
        return (int) (dis / std);
    }

    public int calBeginRow(RequestInfo info, double dis) {
        return calIndex(ROOT_ULLAT, info.getUllat(), dis);
    }

    public int calEndRow(RequestInfo info, double dis) {
        return calIndex(ROOT_ULLAT, info.getLrlat(), dis);
    }

    public int calBeginCol(RequestInfo info, double dis) {
        return calIndex(info.getUllon(), ROOT_ULLON, dis);
    }

    public int calEndCol(RequestInfo info, double dis) {
        return calIndex(info.getLrlon(), ROOT_ULLON, dis);
    }

    private double calAveDis(double source, double target, int num) {
        return (target - source) / num;
    }



    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     *
     *     The grid of images must obey the following properties, where image in the
     *     grid is referred to as a "tile".
     *     <ul>
     *         <li>The tiles collected must cover the most longitudinal distance per pixel
     *         (LonDPP) possible, while still covering less than or equal to the amount of
     *         longitudinal distance per pixel in the query box for the user viewport size. </li>
     *         <li>Contains all tiles that intersect the query bounding box that fulfill the
     *         above condition.</li>
     *         <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     *     </ul>
     *
     * @param params Map of the HTTP GET request's query parameters - the query box and
     *               the user viewport width and height.
     *
     * @return A map of results for the front end as specified: <br>
     * "render_grid"   : String[][], the files to display. <br>
     * "raster_ul_lon" : Number, the bounding upper left longitude of the rastered image. <br>
     * "raster_ul_lat" : Number, the bounding upper left latitude of the rastered image. <br>
     * "raster_lr_lon" : Number, the bounding lower right longitude of the rastered image. <br>
     * "raster_lr_lat" : Number, the bounding lower right latitude of the rastered image. <br>
     * "depth"         : Number, the depth of the nodes of the rastered image <br>
     * "query_success" : Boolean, whether the query was able to successfully complete; don't
     *                    forget to set this to true on success! <br>
     */
    public Map<String, Object> getMapRaster(Map<String, Double> params) {

        RequestInfo paramsInfo = resolute(params);
        int depth = calDepth(paramsInfo.getLonDPP());
        boolean query_success = validate(paramsInfo);

        Map<String, Object> results = new HashMap<>();
        results.put("query_success", query_success);
        results.put("depth", depth);

        int tileNum = calTileNum(depth);
        double aveLatDis = calAveDis(ROOT_LRLAT, ROOT_ULLAT, tileNum);
        double aveLonDis = calAveDis(ROOT_ULLON, ROOT_LRLON, tileNum);

        int beginRow = calBeginRow(paramsInfo, aveLatDis);
        int endRow = calEndRow(paramsInfo, aveLatDis);
        int beginCol = calBeginCol(paramsInfo, aveLonDis);
        int endCol = calEndCol(paramsInfo, aveLonDis);

        int rows = endRow - beginRow + 1;
        int cols = endCol - beginCol + 1;
        String[][] tiles = new String[rows][cols];
        for (int row = beginRow; row <= endRow; ++row) {
            for (int col = beginCol; col <= endCol; ++col) {
                tiles[row - beginRow][col - beginCol] = "d" + depth + "_x" + col + "_y" + row + ".png";
            }
        }

        double raster_ul_lon = ROOT_ULLON + beginCol * aveLonDis;
        double raster_lr_lon = ROOT_ULLON + (endCol + 1) * aveLonDis;
        double raster_ul_lat = ROOT_ULLAT - beginRow * aveLatDis;
        double raster_lr_lat = ROOT_ULLAT - (endRow + 1) * aveLatDis;

        results.put("raster_ul_lon", raster_ul_lon);
        results.put("raster_lr_lon", raster_lr_lon);
        results.put("raster_ul_lat", raster_ul_lat);
        results.put("raster_lr_lat", raster_lr_lat);
        results.put("render_grid", tiles);

        return results;
    }

}
