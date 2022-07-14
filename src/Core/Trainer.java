package Core;

import cclo.*;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 *
 * @author alanter
 */
public class Trainer implements Share {

    /**
     * IncLattice Contains all Category and their node
     */
    private Lattice lattice;
    private Category trainCat;

    /**
     * running: trainer is running
     */
    private static boolean running;
    ControlPan controlPan;

    /**
     * for analysis of in lattice distance
     */
    double avgMinDist = 0.0;
    Toaster toaster = new Toaster();

    public Trainer(ControlPan cp_) {
        running = false;
        controlPan = cp_;
    }

    // Train the given lattice based on a vector of input vectors
    public void setTraining(Lattice lattice_, Category trainCat_,
            ControlPan latticeRenderer) {
        lattice = lattice_;
        trainCat = trainCat_;
    }

    public void start() {
        if (lattice != null) {
            run();
        }
    }
    DecimalFormat df = new DecimalFormat("0.00");
    int distMax = NDIST;

    public void run() {
        String catName;
        if (lattice.cats.size() < 1) {
            // lattice is empty
            if (trainCat.chains.size() > 0) {
                // training set contains something
                // get first chaining chain
                String newCatName = trainCat.chains.get(0).name;
                Chain firstChain = trainCat.chains.get(0);
                Category newCat = new Category(newCatName);
                newCat.addChain(firstChain);
                lattice.addCat(newCat);
            }
        }
        for (Chain trainCh : trainCat.chains) {
            // ----- every train chain
            String chainName = trainCh.name;
            boolean catInLattice = false;
            int[][] trainArray = trainCh.getArray();
            double bestScore = 0.0;
            for (Category lcat : lattice.cats) {
                // ----- every cat in lattice
                if (lcat.name.equals(chainName)) {
                    catInLattice = true;
                    // compair the existing chain
                    for (Chain lchain : lcat.chains) {
                        // ---- every chain in lattice of same cat
                        double score = trainCh.matchScore_Array(trainArray, lchain);
                        if (score > bestScore) {
                            bestScore = score;
                        }
                    }
                    if (bestScore < 0.85) {
                        show("Best Score = " + bestScore + " so, add a new chain.");
                        lcat.addChain(trainCh);
                    }
                }  // the same cat
            }
            if (!catInLattice) {
                Category newCat = new Category(chainName);
                newCat.chains.add(trainCh);
                lattice.addCat(newCat);
            }
        }
        controlPan.latState = STATE.READY;

        if (GUI.ON) {
        } else {
        }
    }

    public void debug(int no_, String str_) {
        if (GUI.ON) {
            ((MsgPan) Main.msgPan).setMsg(no_, str_);
        }
    }

    public void show(String str) {
        System.out.println(str);
    }

    public void stop() {
        running = false;
    }
}
