import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.io.FileWriter;

@SuppressWarnings("Duplicates")
public class Main {

    private static int numOfPics;
    private static int horizontalPics;
    private static int verticalPics;
    private static ArrayList<Photo> myPhotos;
    private static HashMap<String,Integer> tagFrequency;

    public static void processFile(File source){
        try {
            BufferedReader br = new BufferedReader(new FileReader(source));
            String line = br.readLine();
            numOfPics = Integer.parseInt(line.substring(0,1));
            myPhotos = new ArrayList<Photo>();
            line = br.readLine();

            while(line!= null){
                String[] words = line.split(" ");
                int numOfTags = Integer.parseInt(words[1]);
                String[] tags = new String[numOfTags];

                int j = 0;
                for(int i = 2; i < numOfTags+2;i++){
                    tags[j] = words[i];
                    j++;
                }

                if(words[0].equals("H")){
                    horizontalPics++;
                    myPhotos.add(new Photo(words[0],tags));
                }
                if(words[0].equals("V")){
                    verticalPics++;
                    myPhotos.add(null);
                }



                line = br.readLine();
            }
        }
        catch(Exception e){
            System.out.println("File not found");
        }

    }

    public static int scorePair(Photo thisPhoto, Photo otherPhoto){
        ArrayList<String> firstTags = new ArrayList<String>(Arrays.asList(thisPhoto.getTags()));
        ArrayList<String> otherTags =  new ArrayList<String>(Arrays.asList(otherPhoto.getTags()));

        int tagsinCommon = 0;
        int tagsinOther = 0;
        for(String s : otherTags) {
            if (firstTags.contains(s)){
                tagsinCommon++;
            }
            else {
                tagsinOther++;
            }
        }
        int tagsInFirst = firstTags.size()-tagsinCommon;

        int[] scores = {tagsinCommon,tagsInFirst, tagsinOther};

        return Math.min(Math.min(tagsinCommon,tagsInFirst),tagsinOther);
    }


    public static void main(String[] args){

        numOfPics = 0;
        horizontalPics = 0;
        verticalPics = 0;

        processFile(new File("/Users/martinajireckova/Desktop/e_example.txt"));
        System.out.println(myPhotos.size());

        ArrayList<Boolean> flagArray = new ArrayList<Boolean>();
        for(int i = 0; i < myPhotos.size(); i++){
            flagArray.add(false);
        }

        Photo first;
        if(myPhotos.get(0)!=null) {
            first = myPhotos.get(0);
            System.out.println(0);
            flagArray.set(0,true);
        }
        else {
            System.out.println(1);
            first = myPhotos.get(1);
            flagArray.set(1,true);
        }


        ArrayList<Integer> scores = new ArrayList<Integer>();
        for(int k = 0; k < myPhotos.size(); k++){
            scores.add(0);
        }


        for(int j = 1; j < myPhotos.size(); j++) {
            if(flagArray.get(j) == false && myPhotos.get(j) != null) {
                int score = scorePair(first, myPhotos.get(j));
                scores.add(j,score);
            }
        }
        int maxScore = Collections.max(scores);
        System.out.println(scores.indexOf(maxScore));
        flagArray.set(scores.indexOf(maxScore),true);
        first = myPhotos.get(scores.indexOf(maxScore));

        while(flagArray.contains(false)){
            scores = new ArrayList<Integer>();
            for(int k = 0; k < myPhotos.size(); k++){
                scores.add(0);
            }

            int score = 0;
            for(int j = 1; j < myPhotos.size(); j++) {
                if(flagArray.get(j) == false && myPhotos.get(j) != null) {
                    score = scorePair(first, myPhotos.get(j));
                    scores.add(j,score);
                    if(score > 0)
                        break;

                }
            }
           maxScore = Collections.max(scores);
            if(maxScore == 0)
                break;
            System.out.println(scores.indexOf(maxScore));
            flagArray.set(scores.indexOf(maxScore), true);
            first = myPhotos.get(scores.indexOf(maxScore));
        }


    }
}
