import java.io.*;
import java.util.*;

public class VideoApp {

    static ArrayList<Video> videoList = new ArrayList<Video>();
    static ArrayList<String> filenameList = new ArrayList<String>();
    static ArrayList<Integer> courseVideoCount = new ArrayList<Integer>();

    public static void main(String[] args) {
    	
        listVideos(new File("C:/Users/SBoarder/Desktop/folder/downloads"));
        Collections.sort(videoList);
        listFilenames();
        moveVideos();

    }

    public static void listVideos(File fromDir) {
        File[] fromDirFiles = fromDir.listFiles();
        for(File file : fromDirFiles) {
            if(!file.isDirectory())
                videoList.add(new Video(file.lastModified(), file.getPath()));
            else
                listVideos(file);
        }
    }

    public static void listFilenames() {
        String line;
        int videoCounter = 0;

        try {
            File f = new File("C:/Users/SBoarder/Desktop/folder/courselist.txt");
            FileReader fReader = new FileReader(f);
            BufferedReader bReader = new BufferedReader(fReader);

            while((line = bReader.readLine()) != null) {
                filenameList.add(line);
                if(Character.isLetter(line.charAt(0))) {
                    if(videoCounter != 0)
                        courseVideoCount.add(Integer.valueOf(videoCounter));
                    videoCounter = 0;
                } else {
                    videoCounter++;
                }
            }
            courseVideoCount.add(Integer.valueOf(videoCounter));

            fReader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void moveVideos() {
        int j = 0;
        String fullPath = "F:/downloads";
        String workingDir = null;
        int courseCounter = 0;
        int videoCounter = 0;

        for(int i = 0; i < filenameList.size(); i++) {
            String filename = filenameList.get(i);
            if(Character.isLetter(filename.charAt(0))) {
                String newDirPath = fullPath + filename;
                File newDir = new File(newDirPath);
                newDir.mkdir();
                workingDir = newDirPath;
                messageGenerator();
                courseCounter++;
                videoCounter = 0;
            } else {
                videoCounter++;
                messageGenerator(courseCounter, videoCounter);
                File videoFrom = new File(videoList.get(j).getPath());
                File videoTo = new File(workingDir + "/" + filenameList.get(i) + ".mp4");

                if(videoFrom.renameTo(videoTo))
                    System.out.println("Success!\n");
                else
                    System.out.println("ERROR!\n");

                j++;
            }
        }
    }

    public static void messageGenerator() {
        System.out.println("\n============================");
        System.out.println("New course directory created");
        System.out.println("============================");
    }

    public static void messageGenerator(int courseCount, int videoCount) {
        int videosPerCourse = courseVideoCount.get(courseCount - 1).intValue();

        String message = "Course " + courseCount +
          " of " + courseVideoCount.size() + "\nVideo  ";

        if(videoCount < 10 && videosPerCourse > 100)
            message += "  ";
        else if((videoCount < 10 && videosPerCourse > 10) ||
          (videoCount < 100 && videosPerCourse > 100))
            message += " ";

        message += videoCount + " of " + videosPerCourse + "\nMoving video...";
        System.out.print(message);
    }

}