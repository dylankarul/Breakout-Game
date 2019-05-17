package example.powerups;

import example.Powerup;
import example.RunGame;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/***
 * Author: Dylan
 * Purpose: Powerup that makes it so that the points per block hit increases by 100
 *
 * Dependencies: abstract Powerup class
 *
 * call new PointsMultiplierPowerup, then createPowerUpImage to make
 *  * the falling image, and implementPowerUp to actually make the pointsperhit increase by 100
 */
public class PointsMultiplierPowerup extends Powerup {

    public PointsMultiplierPowerup(){}

    /***
     * creates the image of the falling pointsmultiplier powerup
     * @return the image that will fall
     */
    @Override
    protected ImageView createPowerUpImage(){
        var image = new Image(this.getClass().getClassLoader().getResourceAsStream("pointspower.gif")); //change in future
        ImageView powerUp = new ImageView(image);
        return powerUp;
    }

    /***
     * increases score per hit by 100
     * @param sceneSize
     * @param root
     * @param myGame
     */
    @Override
    protected void implementPowerUp(int sceneSize, Group root, RunGame myGame) {
        myGame.setScorePerHit(myGame.getScorePerHit()+100);
    }



}
