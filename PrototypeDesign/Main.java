package PrototypeDesign;

public class Main {

    public static void main(String[] args) {
       LoadRegistry registry = new LoadRegistry();

       registry.loadRegistry();

       Item book1 = registry.getClone("book");
       Item book2 = registry.getClone("book");
       Item book3= registry.getClone("book");

       System.out.println("book1 : "+book1+" book2 : "+book2+" book3 : "+book3);

       Item movie1 = registry.getClone("movie");
       Item movie2 = registry.getClone("movie");
       Item movie3 = registry.getClone("movie");

       System.out.println(" movie1 : "+movie1+" movie2 : "+movie2+" movie3 : "+movie3);

    }   
}
