package ProxyDesign;

public class ParserClient {
    ITextParser textParser;

    public ParserClient(ITextParser textParser) {
        this.textParser=textParser;
    }

    public int getTotalWc() {
        return textParser.getWordCnt();
    }
    public void print() {
        System.out.println("[PARSER CLIENT]-> does not use parser obj");
    }
    

    //a fn which does not even use textParser
    //this is where the performance comes in,
    //we don't even create a textParser obj,

    
}
