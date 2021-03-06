package Core;



import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author cclo
 * Data Structure:
 *  nodeList    : list of matching node
 *  idSet       : set of node id
 *  catList     : list of category
 *  nameSet     : set of category name
 * 
 * Method:
 *  addCatId    : add an id to a category
 *  addNode     : add a node to nodeList, also add its id to idSet
 * 
 */


public class Lattice {
    public String name;
    public ArrayList<Category> cats = new ArrayList<>();
    
    public Lattice(String name_) {
        name = name_;
    }
    
    public void addCat(Category cat_) {
        cats.add(cat_);
    }
    
    public void showRouph() {
        show("Lattice: " + name);
        for (Category cat: cats) {
            show("  Cat: " + cat.name + ", Size: " + cat.chains.size());
        }        
    }

    public void showDetail() {
        show("Lattice: " + name);
        for (Category cat: cats) {
            show("  Cat: " + cat.name + ", Size: " + cat.chains.size());
            cat.showRouph();
        }        
    }
    public void show(String str) {
        System.out.println(str);
    }
}
