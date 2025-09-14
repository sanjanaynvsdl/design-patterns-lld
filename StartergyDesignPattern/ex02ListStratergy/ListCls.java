package ex02ListStratergy;

public class ListCls {

    IPrintingStratergy printStratergy;
    ISortingStratergy sortStratergy;

    public ListCls(IPrintingStratergy printStratergy, ISortingStratergy sortStratergy) {
        this.printStratergy = printStratergy;
        this.sortStratergy = sortStratergy;
    }

    void print() {
        printStratergy.print();
    }

    void sort() {
        sortStratergy.sort();
    }

}
