package ProxyDesign;

public class BookParserProxy implements ITextParser {

    BookParser bp;
    String text;

    public BookParserProxy(String text) {
        this.text=text;
    }


    void init() {
        System.out.println("[INIT]-> bookParser");
        bp = new BookParser(text);
    }
    
    @Override
    public int getWordCnt() {
        if(bp==null) {
            init();
        }
        System.out.println("[BOOK PARSER PROXY]-> called bookParser obj");
        return bp.getWordCnt();
    }

    @Override
    public int getSentenceCnt() {
        if(bp==null) {
            init();
        }
        return bp.getSentenceCnt();
    }

    @Override
    public boolean searchWord(String word) {
        if(bp==null) {
            init();
        }
        return bp.searchWord(word);
    }
    
}
