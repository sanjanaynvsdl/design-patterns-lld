package ProxyDesign;

public class Main {

    //create a book-parser 
    //a client will use this untility methods
    //for client he has no clue on parser(proxy's) 
    //factory will pass the obj of bookparseProxy

public static void main(String[] args) {
    
    ITextParser bookParserProxy = new BookParserProxy("book");
    ITextParser websiteParser =  new WebsiteParserProxy("crazy");
    
    ParserClient client1 = new ParserClient(bookParserProxy);
    ParserClient client2 = new ParserClient(websiteParser);

    // bookParser.print();
    client1.print();
    int wordCnt = client1.getTotalWc();
    System.out.println("[TOTAL CNT ]" + wordCnt);

    System.out.println("--------------------webiste parser ex--------------------------");

    client2.print();
    int wordCnt2 = client2.getTotalWc();
    System.out.println("[TOTAL Cnt -> WEBSITE PARSER]" + wordCnt2);
}
    
}
