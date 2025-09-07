package PrototypeDesign;

public class Book extends Item  {

    private String author;

    public Book(int sp, int cp, int discount, String author) {
        super(sp, cp, discount);
        this.author=author;
    }
    public Book() {
        super();

    }

    @Override
    public Item clone() {
        //why not this?
        // Book book = new (this.sellingPrize, this.costPrize, this.discount, this.author);
        //then what's the need of Prototype design pattern
        Book book = new Book();
        book.sellingPrize=this.sellingPrize;
        book.costPrize=this.costPrize;
        book.discount=this.discount;
        book.author=this.author;

        return book;
    }

}
