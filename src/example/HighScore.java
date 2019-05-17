package example;

import java.io.*;
import java.util.Scanner;

/***
 * Author: Dylan
 *
 * Purpose: This class is used to keep track of the total high score. It
 * reads in from the highscore file, and writes to it if the high score is beat
 *
 * Assumptions: That there is a file in resources called highscore.txt
 *
 * Dependencies: none
 *
 * Example: In the main RunGame file, call a new HighScore(). Set the total
 * highscore to the value of getHighScore. Whenever a block is hit, check to see if
 * that is greater than the highscore, if so, call setHighScore with the new score
 */

public class HighScore {

    File scoreFile = new File("resources/highscore.txt");

    /***
     * Reads in from the highscore.txt file what the highscore of the game is
     * @return returns that high score, or -1 if there was a scanner issue
     */
    public int getHighScore(){
        try {
            Scanner scanner = new Scanner(scoreFile);
            while(scanner.hasNextInt())
            {
                return scanner.nextInt();
            }
        }
        catch (FileNotFoundException e){
            System.out.println("Scanner issue");
        }
        return -1;
    }

    /***
     * sets the high score to score, which is a parameter. Writes to
     * the highscore.txt file
     * @param score - the given score that is the new highscore
     */
    public void setHighScore(int score){
        FileWriter fWriter;

        try {
            fWriter = new FileWriter(scoreFile,false);
            fWriter.write(Integer.toString(score));
            fWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
