package ex01ListClass;

import java.util.List;
import java.util.ArrayList;

public abstract class ListCls {
    ArrayList<Integer> list;

    

    public void sort() {
        System.out.print("sorting the list");
    }

    public void add(int num) {
        list.add(num);

    }
    public void reverse() {
        System.out.print("Reversing the list!");
    }

    public int getNum(int idx) {
        return list.get(idx);
    }

    abstract void print();

}
