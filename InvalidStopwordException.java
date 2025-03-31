public class InvalidStopwordException extends Exception{
       
        public InvalidStopwordException(String stopword) {
            super("InvalidStopwordException: Couldn't find stopword: " + stopword);
        }
    
}
