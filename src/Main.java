import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class Main {

    private static int numOfPics;
    private static int horizontalPics;
    private static int verticalPics;
    private static ArrayList<Photo> myPhotos;

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
                for(int i = 2; i < numOfTags;i++){
                    tags[j] = words[i];
                    j++;
                }

                myPhotos.add(new Photo(words[0],tags));

                line = br.readLine();
            }
        }
        catch(Exception e){
            System.out.println("File not found");
        }


    }

    public static void main(String[] args){
        numOfPics = 0;
        horizontalPics = 0;
        verticalPics = 0;

        processFile(new File("src/a_example.txt"));
        System.out.println(myPhotos.size());
    }
}
