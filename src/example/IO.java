package example;

import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/***
 * Author: Jaylyn
 * Used to alter the Heads up display of the game (Level, Score, Highscore, etc).
 */
public class IO {

    private static final int SIZE = 400;

    private int lives = 3;
    private int myScore = 0;
    private int myLevel = 1;

    private HighScore highScoreClass = new HighScore();
    private int originalHighScore = highScoreClass.getHighScore();
    private int currentHighestScore = originalHighScore;

    private Text livesLeft = new Text(0, SIZE - 10, "Lives left: " + (lives));
    private Text score = new Text(90, SIZE - 10, "Score: " + Integer.toString(myScore));
    private Text currentLevel = new Text(190, SIZE - 10, "Level: " + Integer.toString(myLevel));
    private Text highScoreText = new Text(250, SIZE - 10, "High Score: " + Integer.toString(currentHighestScore));
    private Text loadingLevel = new Text("Level Complete! Press Space/Enter to begin next level!");
    private Text runTest = new Text(SIZE/2-200, SIZE/2+100, "Press , . or / to run the desired test trial!");

    private Text restartGame = new Text("Press ENTER or SPACE to restart!");


    /***
     * Adds text to Heads up display and sets color to White
     * Assumptions: This method assumes that the value it's setting and root have all been instantiated
     * @param root
     */
    public void textToRoot(Group root){
        livesLeft.setFill(Color.WHITE);
        score.setFill(Color.WHITE);
        currentLevel.setFill(Color.WHITE);
        highScoreText.setFill(Color.WHITE);
        restartGame.setFill(Color.WHITE);

        root.getChildren().addAll(livesLeft, score, currentLevel, highScoreText);
    }

    /***
     * Used to update the lives field of the Heads up display
     * Assumptions: This method assumes that root and livesLeft have been instantiated
     * @param myLives
     * @param root
     */
    public void updateLives(int myLives, Group root){

        root.getChildren().remove(livesLeft);
        livesLeft = new Text(0, SIZE-10, "Lives left: " + (myLives));
        livesLeft.setFill(Color.WHITE);
        root.getChildren().add(livesLeft);
    }

    /***
     * This method is the end screen of the game, it tells the player if they have won or lost
     * Assumptions: That its parameters are not null and that animation is running.
     * @param message
     * @param root
     * @param myBouncer
     * @param animation
     */
    public void endGame(String message, Group root, ImageView myBouncer, Timeline animation) {
        animation.stop();
        Text gameOver = new Text(message);
        gameOver.setFont(Font.font(null, FontWeight.BOLD, 32));
        gameOver.setX(SIZE/2 - gameOver.getBoundsInLocal().getWidth() / 2);
        gameOver.setY(SIZE/2 - gameOver.getBoundsInLocal().getHeight() / 2);

        if(message.equals("YOU LOSE!")){
            gameOver.setFill(Color.RED);
        }else{
            gameOver.setFill(Color.GREEN);
            root.getChildren().remove(myBouncer);
        }
        root.getChildren().clear();
        restartGame.setX(SIZE/2 - restartGame.getBoundsInLocal().getWidth() / 2);
        restartGame.setY(SIZE/2+75);
        root.getChildren().addAll(gameOver, restartGame);

    }

    /***
     * Used to create a landing screen in between levels
     * Assumptions: Assumes that this will only be called in between levels
     * @param root
     */
    public void loading(Group root){
        root.getChildren().clear();
        loadingLevel.setX(SIZE/2 - loadingLevel.getBoundsInLocal().getWidth() / 2);
        loadingLevel.setY(SIZE/2 - loadingLevel.getBoundsInLocal().getHeight() / 2);
        loadingLevel.setFill(Color.WHITE);
        runTest.setX(SIZE/2 - runTest.getBoundsInLocal().getWidth() / 2);
        runTest.setY(SIZE/2 + 55);
        runTest.setFill(Color.WHITE);
        root.getChildren().addAll(loadingLevel, runTest);

    }

    /***
     * Used to update the current level field of the heads up display
     * Assumptions: This method assumes that the variables used aren't null
     * @param level
     * @param root
     */
    public void updateCurrentLevel(int level, Group root){
        root.getChildren().remove(currentLevel);
        currentLevel.setText("Level: " + Integer.toString(level));
        root.getChildren().add(currentLevel);
    }

    /***
     * Used to update the score field of the Heads up display
     * Assumes that the variables used are not null
     * @param root
     * @param pointsMultiplier
     * @param SCORE_PER_HIT
     */
    public void updateScore(Group root, boolean pointsMultiplier, int SCORE_PER_HIT){
        root.getChildren().remove(score);
        if(pointsMultiplier==false) {
            myScore += SCORE_PER_HIT;
            score = new Text(90, SIZE - 10, "Score: " + Integer.toString(myScore));
        }
        else{
            SCORE_PER_HIT += 100;
            System.out.println(SCORE_PER_HIT);
            myScore += SCORE_PER_HIT;
            score = new Text(90, SIZE - 10, "Score: " + Integer.toString(myScore));
            pointsMultiplier = false;
        }
        score.setFill(Color.WHITE);
        root.getChildren().add(score);

        if(myScore>currentHighestScore){
            currentHighestScore=myScore;
            root.getChildren().remove(highScoreText);
            highScoreText = new Text(250, SIZE-10, "High Score: " + Integer.toString(currentHighestScore));
            highScoreText.setFill(Color.WHITE);
            root.getChildren().add(highScoreText);
        }
    }

    /***
     * Updates the highscore if it has been beaten.
     * Assumes that the values are not null.
     */
    public void updateHighScoreFile(){
        if(currentHighestScore>originalHighScore){
            highScoreClass.setHighScore(currentHighestScore);
        }
    }

}
