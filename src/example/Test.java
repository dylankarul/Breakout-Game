package example;

import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;


import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

/***
 * Skeleton for all tests, used as a base to run all tests.
 */
abstract public class Test {


    protected Group root = new Group();
    protected List<Block> hitList;
    protected Timeline animation;
    protected int myLives;
    protected int counter;
    protected int SIZE;
    protected int myLevel;

    public List<String> info;

    public Test(String config) {
        info = scanTestConfig(new File(config));
    }

    private static List<String> scanTestConfig(File file) {
        Scanner in = new Scanner(Block.class.getClassLoader().getResourceAsStream(file.getName()));
        ArrayList<String> info = new ArrayList<String>();
        while (in.hasNext()) {
            String line = in.nextLine();
            info.add(line);
        }

        return info;
    }

    /***
     * Used to display if a test has passed or failed
     * Assumes that the test has ended and the animation is running.
     * @param message
     * @param SIZE
     * @param root
     */
    public void endTest(String message, int SIZE, Group root){
        animation.stop();
        Text testOver = new Text(message);
        testOver.setFont(Font.font(null, FontWeight.BOLD, 30));
        testOver.setX(SIZE/2 - testOver.getBoundsInLocal().getWidth() / 2);
        testOver.setY(SIZE/2 - testOver.getBoundsInLocal().getHeight() / 2);
        testOver.setFill(Color.WHITE);
        root.getChildren().add(testOver);
    }

    /***
     * Used as a medium of communication between Game and the Test class
     * Assumes that all this information is available from the Game
     * @param count
     * @param size
     * @param ani
     * @param r
     * @param hit
     * @param lives
     * @param level
     */
    public void giveData(int count, int size, Timeline ani, Group r, List<Block> hit, int lives, int level){
        counter = count;
        SIZE = size;
        animation = ani;
        root = r;
        hitList = hit;
        myLives = lives;
        myLevel = level;
    }

    /***
     * Abstract class used to run all tests through out the game
     * Will always result in an endTest call
     */
    abstract void runTest();



}
