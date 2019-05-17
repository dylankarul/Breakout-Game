package example.powerups;

import example.Powerup;
import example.RunGame;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.Group;

/***
 * Author: Dylan
 * Purpose: This class is a specific type of powerup that makes the bouncer
 * larger. It creates the falling powerup image and also implements the power
 * up if the image is caught.
 *
 * Assumptions: We only allow the ball to get bigger a few times,
 * as if it gets too big, the physics of the game get wacky
 *
 * Dependencies: This is a subclass of the class Powerup and it uses
 * the example package that was given
 *
 * How to use it: call new BiggerBallPowerup, then createPowerUpImage to make
 * the falling image, and implementPowerUp to actually make the ball bigger
 */
public class BiggerBallPowerup extends Powerup {
    double maxDiameter = 40;
    double incrementDiameter = 10;
    public BiggerBallPowerup(){}

    /***
     * creates the actual image of powerup falling
     * @return the image that will fall
     */
    @Override
    protected ImageView createPowerUpImage(){
        var image = new Image(this.getClass().getClassLoader().getResourceAsStream("bigballpower.gif")); //change in future
        ImageView powerUp = new ImageView(image);
        return powerUp;
    }

    /***
     * Makes the ball bigger in the game
     * @param sceneSize
     * @param root
     * @param myGame
     */
    @Override
    protected void implementPowerUp(int sceneSize, Group root, RunGame myGame) {
        ImageView bouncer = myGame.getBouncer();
        double oldDiameter = bouncer.getFitHeight();
        if(oldDiameter<maxDiameter) {
            double newDiameter = oldDiameter + incrementDiameter;
            double oldXPos = bouncer.getX();
            double oldYPos = bouncer.getY();

            root.getChildren().remove(bouncer);
            bouncer.setFitHeight(newDiameter);
            bouncer.setFitWidth(newDiameter);
            bouncer.setX(oldXPos);
            bouncer.setY(oldYPos);
            root.getChildren().add(bouncer);
        }
    }
}
