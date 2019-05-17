package example;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.image.ImageView;

/***
 * Authors: Jaylyn and Dylan
 *
 * Purpose: this is the paddle for the game
 *
 * Assumptions: none
 *
 * Dependencies: example package
 *
 * How to use it: create a: new Paddle(), then call createPaddle(sceneSize) to get the
 *  * actual image of the paddle. Add it to the root and now its in the game
 */

public class Paddle {

    private static final int width = 75;
    private static final int height = 10;
    private static final int PADDLE_SPEED = 25;
    private String paddleFile = "paddle.gif";
    private ImageView myPaddleImage;

    public Paddle(){

    }

    /***
     * creates the actual paddle image for the game
     * @param sceneSize - size of the game
     * @return myPaddleImage, which is the paddle in the game
     */
    public ImageView createPaddle(int sceneSize){
        myPaddleImage = new ImageView(new Image(this.getClass().getClassLoader().getResourceAsStream(paddleFile)));
        myPaddleImage.setFitWidth(width);
        myPaddleImage.setFitHeight(height);
        myPaddleImage.setX(sceneSize/2 - width / 2);
        myPaddleImage.setY(sceneSize- 50);
        //Rectangle myPaddle = new Rectangle(sceneSize/2 - width / 2, sceneSize- 50, width, height);
        return myPaddleImage;
    }

    /***
     * moves the paddle left or right, based on the users keycode.
     * Doesn't let it go past the bounds of the game
     * @param code - button being pressed
     * @param paddle - the paddle being moved
     * @param sceneSize - size of game
     */
    public void movePaddle(KeyCode code, ImageView paddle, int sceneSize){
        if (code == KeyCode.RIGHT && paddle.getX() + paddle.getBoundsInLocal().getWidth() < sceneSize) {
            paddle.setX(paddle.getX() + PADDLE_SPEED);
        }
        else if (code == KeyCode.LEFT && paddle.getX() > 0) {
            paddle.setX(paddle.getX() - PADDLE_SPEED);
        }
    }

    /***
     * centers the paddle in the game
     * @param paddle - the game's padde
     * @param sceneSize - size of game
     */
    void centerPaddle(ImageView paddle, int sceneSize){
        paddle.setX(sceneSize/2 - paddle.getBoundsInLocal().getWidth() / 2);
        paddle.setY(sceneSize-50);
    }





}
