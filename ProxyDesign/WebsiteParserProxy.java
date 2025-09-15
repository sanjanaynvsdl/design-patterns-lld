package ProxyDesign;

public class WebsiteParserProxy implements ITextParser{
    
    WebsiteParser wp;
    String text;

    public WebsiteParserProxy(String text) {
        this.text=text;
    }


    void init() {
        System.out.println("[INIT]-> bookParser");
        wp = new WebsiteParser(text);
    }
    
    @Override
    public int getWordCnt() {
        if(wp==null) {
            init();
        }
        System.out.println("[WEBSITE PARSER PROXY]-> called WebsiteParser obj");
        return wp.getWordCnt();
    }

    @Override
    public int getSentenceCnt() {
        if(wp==null) {
            init();
        }
        return wp.getSentenceCnt();
    }

    @Override
    public boolean searchWord(String word) {
        if(wp==null) {
            init();
        }
        return wp.searchWord(word);
    }
    
}
