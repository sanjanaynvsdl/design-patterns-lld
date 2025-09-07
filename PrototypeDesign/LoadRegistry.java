package PrototypeDesign;
  import java.util.HashMap;

public class LoadRegistry {

    HashMap<String, Item> map = new HashMap<>();

    public void loadRegistry() {
        // create prototypes
        Book fictionBooks = new Book(299, 200, 10, "xyz-author");
        Movie actionMovie = new Movie(599, 400, 15, "Action film",2);

        map.put("book", fictionBooks);
        map.put("movie", actionMovie);

    }

    public Item getClone(String key) {
        return map.get(key).clone();
        

    }

}
