import java.io.*;
import java.util.Scanner;

public class WordCounter {

    // a main method that asks the user to choose to process a file with option 1, or text with option 2. If the user enters an invalid option, allow them to choose again until they have a correct option. Both of these items will be available as the first command line argument. It then checks to see if there is a second command line argument specifying a stopword. The method then calls the methods above to process the text, and outputs the number of words it counted. If the file was empty, this method will display the message of the exception raised (which includes the path of the file), and then continue processing with an empty string in place of the contents of the file (note that this will raise a TooSmallText exception later). Note that the path of the empty file may not be the same path that was specified in the command line by the time this exception is raised. If the stopword wasn’t found in the text, allow the user one chance to re-specify the stopword and try to process the text again. If they enter another stopword that can’t be found, report that to the user.

    public static void main(String[] args) throws InvalidStopwordException, TooSmallText, EmptyFileException {
        Scanner sc = new Scanner(System.in);
        String userChoice;
        String stopword;
        StringBuffer text = new StringBuffer();

        if (args.length > 0 && (args[0].equals("1") || args[0].equals("2"))) {
            userChoice = args[0];
        } else {
            System.out.println("Choose to process a file (Type \"1\") or text (Type \"2\")");
            userChoice = sc.nextLine();
            while (!userChoice.equals("1") && !userChoice.equals("2")) {
                System.out.println("Invalid Choice: choose to process a file (Type \"1\") or text (Type \"2\")");
                userChoice = sc.nextLine();
            }
        }

        if (args.length > 1) {
            stopword = args[1];
        } else {
            System.out.print("Enter your stopword: ");
            stopword = sc.nextLine();
        }

        if (userChoice.equals("1")) {
            System.out.print("Enter the filename: ");
            String filepath = sc.nextLine();
            try {
                text = WordCounter.processFile(filepath);
            } catch (EmptyFileException e) {
                System.out.println(e); 
                text = new StringBuffer(); 
            }
        } else {
            System.out.print("Enter your text: ");
            text = new StringBuffer(sc.nextLine());
        }

        try {
            int count = WordCounter.processText(text, stopword);
            System.out.println("Word count: " + count);
        } catch (InvalidStopwordException e1) {
            System.out.println("Stopword not found. Enter a new stopword: ");
            stopword = sc.nextLine();

            try {
                int count = WordCounter.processText(text, stopword);
                System.out.println("Word count: " + count);
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