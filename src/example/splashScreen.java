package example;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.FontWeight;
import javafx.scene.effect.DropShadow;

/***
 * Author: Dylan
 *
 * Sets up the splash screen before the game starts. Use it by making a new splashScreen
 * and calling setUpSplashScreen
 */
public class splashScreen {

    public splashScreen(){

    }

    /***
     * sets up all the text and colors for the splash screen, which is displayed before
     * the game starts. It lays out the rules and how to start.
     * @param sceneSize
     * @param lives
     * @return
     */
    public Scene setUpSplashScreen(int sceneSize, int lives){
        var screenGroup = new Group();
        var splashScreen = new Scene(screenGroup, sceneSize, sceneSize, Color.PINK);

        DropShadow ds = new DropShadow();
        ds.setOffsetY(3.0f);
        ds.setColor(Color.color(0.4f, 0.4f, 0.4f));

        Text gameTitle = new Text(sceneSize/2 - 200, sceneSize / 2 - 100, "Brick Breaker");
        gameTitle.setEffect(ds);
        gameTitle.setCache(true);

        gameTitle.setFill(Color.RED);
        //gameTitle.setText("Brick Breaker");
        gameTitle.setFont(Font.font(null, FontWeight.BOLD, 32));

        Text goalOfGame = new Text(sceneSize/2-200, sceneSize/2-50, "You have " + lives + " lives to break all the blocks!");
        goalOfGame.setFill(Color.BLUE);

        Text howToMove = new Text(sceneSize/2-200, sceneSize/2, "Move the paddle using the " +
                "left and right arrow keys");
        howToMove.setFill(Color.BLUE);

        Text howToStart = new Text(sceneSize/2-200, sceneSize/2+50, "Press enter or space to begin!");
        howToStart.setFill(Color.BLUE);

        Text runTest = new Text(sceneSize/2-200, sceneSize/2+100, "Press , . or / to run the desired test trial!");
        runTest.setFill(Color.BLUE);

        centerXText(gameTitle, sceneSize);
        centerXText(goalOfGame, sceneSize);
        centerXText(howToMove, sceneSize);
        centerXText(howToStart, sceneSize);
        centerXText(runTest, sceneSize);

        screenGroup.getChildren().addAll(gameTitle, goalOfGame, howToStart, howToMove, runTest);

        return splashScreen;
    }

    private void centerXText(Text sentence, int SIZE){
        sentence.setX(SIZE/2 - sentence.getBoundsInLocal().getWidth() / 2);
    }
}
