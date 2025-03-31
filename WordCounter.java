import java.io.*;
import java.util.Scanner;

public class WordCounter {

    public static void main(String[] args) {
        
    }

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
            throw new TooSmallText(wordcount);
        }
        return wordcount;
    }

    public static StringBuffer processFile(String path) throws EmptyFileException {
        StringBuffer text = new StringBuffer();
        File file = new File(path);

        while(!file.exists() || !file.canRead()){
            System.out.print("Cannot open file. Enter a valid filename: ");

            Scanner sc = new Scanner(System.in);
            path = sc.nextLine();
            file = new File(path);
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            boolean hasContent = false;

            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    hasContent = true;
                }
                text.append(line + "\n");
            }

            if (!hasContent) {
                throw new EmptyFileException(file.getPath());
            }

        } catch (IOException e) {
            System.out.println("Checked IO exception caught: " + e.getMessage());
        }

        return text;
    }
}