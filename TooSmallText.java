public class TooSmallText extends Exception{

    public TooSmallText(int wordcount) {
        super("TooSmallText: Only found " + wordcount + " words.");
    }
    
}
