package ProxyDesign;

public class BookParser implements ITextParser {
    String text;
    // XML xlmText;

    public BookParser(String book) {
        this.text=book;
        //now parse the text and set in a data strucutre as needed.

    }

    @Override
    public int getWordCnt() {
        System.out.println("[BOOK PARSER]-> Word Cnt");
        return 0;
    }

    @Override
    public int getSentenceCnt() {
        return 0;
    }

    @Override
    public boolean searchWord(String word) {
        return true;
    }
}
