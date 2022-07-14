package Core;

import Core.Node;
import cclo.*;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 *
 * @author user
 */
public class Chain implements Share {

    public String name;
    public ArrayList<Node> nodes = new ArrayList<>();

    public Chain(String name_) {
        name = name_;
    }

    public void addNode(Node node_) {
        nodes.add(node_);
    }

    public String toString() {
        String res = "[";
        for (Node nd : nodes) {
            res += "[";
            for (int i = 0; i < PATT_SIZE; i++) {
                res += nd.peaks[i] + ",";
            }
            res += "]";
        }
        res += "]";
        return res;
    }

    public int[][] getArray() {
        int[][] array = new int[nodes.size() + 1][PATT_SIZE];
        for (int i = 0; i < nodes.size(); i++) {
            Node cNode;
            cNode = nodes.get(i);
            for (int j = 0; j < PATT_SIZE; j++) {
                array[i + 1][j] = cNode.peaks[j];
            }
        }
        return array;
    }

    public double matchScore(Chain otherChain) {
        DecimalFormat df = new DecimalFormat("0.00");

        int[][] thisArray = this.getArray();
        int[][] otherArray = otherChain.getArray();

        double lenRatio = ((double) thisArray.length) / ((double) otherArray.length);
        if (lenRatio > 1.4 || lenRatio < 0.7) {
            return 0.0;
        }

        int mLen = LCS(thisArray, otherArray);
        int pLen = thisArray.length;
        double matchRatio = ((double) mLen) / ((double) pLen);
        // show("Match Ratio: " + df.format(matchRatio));
        return matchRatio;
    }

    public double matchScore_Array(int[][] a, Chain otherChain) {
        DecimalFormat df = new DecimalFormat("0.00");

        int[][] thisArray = a;
        int[][] otherArray = otherChain.getArray();

        double lenRatio = ((double) thisArray.length) / ((double) otherArray.length);
        if (lenRatio > 1.4 || lenRatio < 0.7) {
            return 0.0;
        }

        int mLen = LCS(thisArray, otherArray);
        int pLen = thisArray.length;
        double matchRatio = ((double) mLen) / ((double) pLen);
        // show("Match Ratio: " + df.format(matchRatio));
        return matchRatio;
    }

    public int LCS(int[][] a, int[][] b) {
        int n1 = a.length;
        int n2 = b.length;
        int[][] slength = new int[n1][n2];
        int[][] prev = new int[n1][n2];

        for (int i = 0; i < n1; i++) {
            slength[i][0] = 0;
        }
        for (int j = 0; j < n2; j++) {
            slength[0][j] = 0;
        }
        for (int i = 1; i < n1; i++) {
            for (int j = 1; j < n2; j++) {
                if (similar(a[i], b[j])) {
                    slength[i][j] = slength[i - 1][j - 1] + 1;
                    prev[i][j] = 0; // 左上方
                } else {
                    if (slength[i - 1][j] < slength[i][j - 1]) {
                        slength[i][j] = slength[i][j - 1];
                        prev[i][j] = 1; // 左方
                    } else {
                        slength[i][j] = slength[i - 1][j];
                        prev[i][j] = 2; // 上方
                    }
                }
            }
        }
        // System.out.println("LCS Length: " + slength[n1 - 1][n2 - 1]);
        // print_LCS(n1 - 1, n2 - 1);
        return slength[n1 - 1][n2 - 1];
    }

    public boolean similar(int[] a, int[] b) {
        // show("similar array a[]: 【" + a[0] + "," + a[1] + "," + a[2] + "," + a[3] + "," + a[4] + "," + a[5] + "]");
        // show("similar array b[]: 【" + b[0] + "," + b[1] + "," + b[2] + "," + b[3] + "," + b[4] + "," + b[5] + "]");
        int diff = 0;
        if (a[0] == -1 && b[0] == -1) {
            return true;
        } else if (a[0] == -1 || b[0] == -1) {
            return false;
        }
        diff += minDist(a[PATT_SIZE - 1], b);
        diff += minDist(a[PATT_SIZE - 2], b);
        diff += minDist(a[PATT_SIZE - 3], b);

        diff += minDist(b[PATT_SIZE - 1], a);
        diff += minDist(b[PATT_SIZE - 2], a);
        diff += minDist(b[PATT_SIZE - 3], a);
        // show("Diff: " + diff);

        if (diff < 5) {
            return true;
        } else {
            return false;
        }
    }

    public int minDist(int a, int[] b) {
        int len = b.length;
        int minD = 10000;
        int dist;
        for (int i = len - 3; i < len; i++) {
            dist = Math.abs(a - b[i]);
            if (dist < minD) {
                minD = dist;
            }
        }
        return minD;
    }

    public void show(String str) {
        System.out.println(str);
    }

}
