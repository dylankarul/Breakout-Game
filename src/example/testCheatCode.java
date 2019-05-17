package example;

import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/***
 * Test logic for testing each cheat code in between levels.
 */

public class testCheatCode extends Test {

    private RunGame myGame;
    private int startingLives = 0;
//    ImageView ball = myGame.getBouncer();
    private double x;
    private double y;
    private double initSpeed;
    private Bouncer bouncer;



    public testCheatCode(String config, RunGame game, Bouncer b) {
        super(config);
        myGame = game;
        bouncer = b;
    }

    @Override
    void runTest() {

        if (myLevel == 1) {

            Text prompt = new Text("Adding a life!");
            prompt.setX(SIZE / 2 - prompt.getBoundsInLocal().getWidth() / 2);
            prompt.setY((SIZE - 100) - prompt.getBoundsInLocal().getHeight() / 2);
            prompt.setFill(Color.WHITE);

            root.getChildren().add(prompt);

            if (counter == 1) startingLives = myLives;

            if (counter == 100) myGame.handleKeyInput(KeyCode.L);

            if (counter == 200) {
                if (myLives > startingLives) {
                    endTest("TEST PASSED!", SIZE, root);
                } else {
                    endTest("TEST FAILED!", SIZE, root);
                }
            }

        } else if(myLevel == 2){

            Text prompt = new Text("Resetting Ball after 100 ticks!");
            prompt.setX(SIZE / 2 - prompt.getBoundsInLocal().getWidth() / 2);
            prompt.setY((SIZE - 100) - prompt.getBoundsInLocal().getHeight() / 2);
            prompt.setFill(Color.WHITE);

            root.getChildren().add(prompt);

            ImageView ball = myGame.getBouncer();

            if(counter == 1){
                x = ball.getBoundsInLocal().getCenterX();
                y = ball.getBoundsInLocal().getCenterY();
            }

            if(counter == 100) myGame.handleKeyInput(KeyCode.R);

            if (counter == 101) {
                if (ball.getBoundsInLocal().getCenterX() == x && ball.getBoundsInLocal().getCenterY() == y) {
                    endTest("TEST PASSED!", SIZE, root);
                } else {
                    endTest("TEST FAILED!", SIZE, root);
                }
            }
        } else {
            Text prompt = new Text("Speeding up ball!");
            prompt.setX(SIZE / 2 - prompt.getBoundsInLocal().getWidth() / 2);
            prompt.setY((SIZE - 100) - prompt.getBoundsInLocal().getHeight() / 2);
            prompt.setFill(Color.WHITE);

            root.getChildren().add(prompt);

            if(counter == 1) initSpeed = bouncer.getBOUNCER_SPEED();

            if(counter == 100) myGame.handleKeyInput(KeyCode.Q);

            if (counter == 200) {
                if(bouncer.getBOUNCER_SPEED() == initSpeed * 2){
                    endTest("TEST PASSED!", SIZE, root);
                } else {
                    endTest("TEST FAILED!", SIZE, root);
                }
            }

        }
    }
}
