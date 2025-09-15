package ProxyDesign;

//now, a class (means a client wants to use the utility methods of text parser)
//refer the final clas ParserClient.java
public class Clients {

}

// client-1 directly created bookParser obj
// violates Dependecy Inversoin -> High level modules depend on low level
// modules.
// solution to create interface
class client1 {
    BookParser bp = new BookParser("book");

    int getTotalCnt() {
        return bp.getWordCnt();
    }
}

// client-2
// we use IText Parser which adds a layer of abstraction,
// issue- eager loadgin let's say we don't even use f2()
// even then due to the obj being intialized while client2 creation
// this leads to performance issue.

class Client2 {
    ITextParser textParser;

    public Client2(ITextParser texpParser) {
        this.textParser = textParser;
    }

    int f1() {
        return this.textParser.getWordCnt();
    }

    int f2() {
        return this.textParser.getSentenceCnt();
    }
}

// client-3
// creates a function to intialize
// issue- bad-desing we are making client to init the parsers.
// since factory creates the obj, we make use of factory nd call here.
class Client3 {
    ITextParser textParser;

    public Client3(ITextParser texpParser) {
        // this.textParser = textParser;
    }

    int f1() {
        if (textParser == null) {
            init();
        }
        return this.textParser.getWordCnt();
    }

    int f2() {
        return this.textParser.getSentenceCnt();
    }

    void init() {
        // textParser = factory.getInstance("book");
        // passing a ref to factory that we need bookParser
    }
}

// client-4, instead of changing the code on client side,
// we create a wrapper as BookParserProxy which has this init, and all the
// checks
// now, in factory we pass this BookParserProxy -> just a wrapper on top of the
// Bookparser,
