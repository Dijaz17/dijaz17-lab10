public class TooSmallText extends Exception{

    public TooSmallText(int wordcount) {
        super("Only found " + wordcount + " words.");
    }
    
}
