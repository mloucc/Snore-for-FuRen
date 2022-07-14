package Core;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.ArrayList;
import java.util.HashSet;

/**
 *
 * @author USER
 */
public class Category implements Share {

    public String name;
//     public HashSet<Integer> fSet = new HashSet<>();
    public ArrayList<Chain> chains = new ArrayList<>();

    public Category(String name_) {
        name = name_;
    }

    public void addChain(Chain chain_) {
        chains.add(chain_);
        // addFSet(chain_);
    }

    public int matchScore(int id_) {
        return 0;
    }

    public void showRouph() {
        show("Category: " + name);
        for (Chain chain : chains) {
            show("  Chain Size: " + chain.nodes.size());
        }
    }

    public void show(String str) {
        System.out.println(str);
    }

}
