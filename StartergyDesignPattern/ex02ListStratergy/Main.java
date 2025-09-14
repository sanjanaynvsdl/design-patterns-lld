package ex02ListStratergy;

public class Main {
    public static void main(String[] args) {

        //ex-1 
        ISortingStratergy mergeSort = new MergeSort();
        IPrintingStratergy verticalPrint = new VerticalPrint();

        //ex-2
        ISortingStratergy timSort = new TimSort();
        IPrintingStratergy horizontalPrint = new VHorizontalPrint();




        ListCls list = new ListCls(verticalPrint, mergeSort);
        ListCls list2 = new ListCls(horizontalPrint, timSort);

        list.print();
        list.sort();

        System.out.println("-----Example-2--------");
        list2.print();
        list2.sort();


    }
    
}
