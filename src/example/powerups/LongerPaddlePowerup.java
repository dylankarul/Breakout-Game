package example.powerups;

import example.Powerup;
import example.RunGame;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.Group;

/***
 * Author: Dylan
 *
 * Purpose: This is a specific type of Powerup, which makes the paddle
 * longer in the game. This would be used to make the game more fun
 *
 * Assumptions: none
 *
 * Dependencies: example package, and abstract Powerup class
 *
 * How to use it: call new LongerPaddlePowerup(), then createPowerUpImage to make
 *  * the falling image, and implementPowerUp to actually make the paddle bigger
 */
public class LongerPaddlePowerup extends Powerup {

    public LongerPaddlePowerup(){}

    /***
     * creates the image of the falling longerpaddlepowerup
     * @return the image that will fall
     */
    @Override
    protected ImageView createPowerUpImage(){
        var image = new Image(this.getClass().getClassLoader().getResourceAsStream("sizepower.gif")); //change in future
        ImageView powerUp = new ImageView(image);
        return powerUp;
    }

    /***
     * makes the paddle longer
     * @param sceneSize
     * @param root
     * @param myGame
     */
    @Override
    protected void implementPowerUp(int sceneSize, Group root, RunGame myGame) {
        ImageView paddle = myGame.getPaddle();
        double oldWidth = paddle.getFitWidth();
        double newWidth = oldWidth+20;
        if(newWidth<sceneSize){
            root.getChildren().remove(paddle);
            paddle.setFitWidth(newWidth);
            root.getChildren().add(paddle);

        }
    }

}
