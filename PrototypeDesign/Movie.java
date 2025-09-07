package PrototypeDesign;

public class Movie extends Item  {
    private String name;
    private int time;

    public Movie(int sp, int cp, int discount, String name, int time) {
        super(sp, cp, discount);
        this.name = name;
        this.time = time;
    }

    public Movie() {
        super();

    }

    @Override
    public Item clone() {
        Movie movie = new Movie();

        movie.sellingPrize = this.sellingPrize;
        movie.costPrize = this.costPrize;
        movie.discount = this.discount;
        movie.name = this.name;
        movie.time = this.time;
        return movie;
    }

}
