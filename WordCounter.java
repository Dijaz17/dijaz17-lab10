import java.io.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordCounter {

    public static void main(String[] args) throws InvalidStopwordException, TooSmallText, EmptyFileException, IOException {
        Scanner sc = new Scanner(System.in);
        StringBuffer text = new StringBuffer();
        String stopword = null;

        if (args.length > 1) {
            stopword = args[1];
        } else {
            System.out.println("Enter your stopword: ");
            stopword = sc.nextLine();
        }

        if (args.length > 0) {
            File file = new File(args[0]);

            if (file.exists() && file.canRead()) {
                try {
                    text = WordCounter.processFile(args[0]);
                } catch (EmptyFileException e) {
                    System.out.println(e);
                    text = new StringBuffer();
                }
            } else {
                text = new StringBuffer(args[0]);
            }
        } else {
            System.out.print("Enter text to process: ");
            text = new StringBuffer(sc.nextLine());
        }

        try {
            int count = WordCounter.processText(text, stopword);
            System.out.println("Found " + count + " words.");
        } catch (InvalidStopwordException e1) {
            System.out.println("Stopword not found. Enter a new stopword: ");
            stopword = sc.nextLine();

            try {
                int count = WordCounter.processText(text, stopword);
                System.out.println("Found " + count + " words.");
            } catch (InvalidStopwordException e2) {
                System.out.println("Second stopword does not exist. Word counter failed.");
            } catch (TooSmallText e2) {
                System.out.println(e2);
            }
        } catch (TooSmallText e) {
            System.out.println(e);
        }

    }



    public static int processText(StringBuffer str, String stopword) throws InvalidStopwordException, TooSmallText{
        int wordcount = 0;
        String text = str.toString();

        Pattern regex = Pattern.compile("[a-zA-Z0-9']+");
        Matcher regexMatcher = regex.matcher(text);

        while (regexMatcher.find()) {
            wordcount++;
        }

        if(wordcount < 5){
            throw new TooSmallText(wordcount);
        }

        wordcount = 0;

        if(stopword == null){
            text = text.toString();
        } else if (str.indexOf(stopword) == -1){
            throw new InvalidStopwordException(stopword);
        } else {
            text = str.substring(0, (str.indexOf(stopword) + stopword.length()));
        }

        regexMatcher = regex.matcher(text);

        while (regexMatcher.find()) {
            wordcount++;
        }

        if(wordcount < 5){
            throw new TooSmallText(wordcount);
        }
        return wordcount;
    }



    public static StringBuffer processFile(String path) throws EmptyFileException, IOException {
        StringBuffer text = new StringBuffer();
        File file = new File(path);

        while(!file.exists() || !file.canRead()){
            System.out.print("Cannot open file. Enter a valid filename: ");

            Scanner sc = new Scanner(System.in);
            path = sc.nextLine();
            file = new File(path);
        }

        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        boolean hasContent = false;

        while ((line = reader.readLine()) != null) {
            if (!line.trim().isEmpty()) {
                hasContent = true;
            }
            text.append(line);
        }

        reader.close();

        if (!hasContent) {
            throw new EmptyFileException(file.getPath());
        }

        return text;
    }
}