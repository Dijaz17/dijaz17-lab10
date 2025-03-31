public class WordCounter {

    public static void main(String[] args) {
        
    }

    // a method called processText that expects a StringBuffer as argument (the text), and a String stopword, and counts the number of words in that text through that stopword. If the stopword is not found the text, the method will raise an InvalidStopwordException. If the stopword is null, your method will count all words in the file. The methods returns the integer word count, unless the count was less than five, it which case it raises a TooSmallText exception (regardless of whether or not it found the stopword). For example, if there are only three words in the text, your code will raise the exception.

    public static int processText(StringBuffer str, String stopword) throws InvalidStopwordException, TooSmallText{
        int wordcount = 0;

        if(stopword == null){
            String[] words = str.toString().split(" ");
            for(String word : words){
                if (word.matches(".*[a-zA-Z0-9].*")) { 
                    wordcount++;
                }
            }
        } else if (str.indexOf(stopword) == -1){
            throw new InvalidStopwordException(stopword);
        } else {
            String str2 = str.substring(0, (str.indexOf(stopword) + stopword.length()));
            String[] words = str2.split(" ");

            for(String word : words){
                if (word.matches(".*[a-zA-Z0-9].*")) { 
                    wordcount++;
                }
            }
        }


        if(wordcount < 5){
            throw new TooSmallText();
        }
        return wordcount;
    }
}