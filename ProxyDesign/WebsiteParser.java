package ProxyDesign;

public class WebsiteParser implements ITextParser {

    String text;

    public WebsiteParser(String text) {
        this.text=text;
    }


    @Override
    public int getWordCnt() {
        System.out.println("[WEBSITE PARSER] -> word cnt");
        return 1;
    }

    @Override
    public int getSentenceCnt() {
        System.out.println("[WEBSITE PARSER]-> sentene cnt");
        return 1;
    }

    @Override
    public boolean searchWord(String word) {
        System.out.println("[WEBSITE PARSER]-> searching word!");
        return true;
    }
    
}
