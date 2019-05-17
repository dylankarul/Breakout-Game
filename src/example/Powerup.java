package example;

import javafx.scene.Group;
import javafx.scene.image.ImageView;

import java.util.List;

/***
 * Author: Dylan
 *
 * Purpose: Allows for users to get unique powerups after destroying blocks. The powerup will falll,
 * and if caught by the paddle, implement the actual power
 *
 * How to use it: call one of the Powerup subclasses!
 */
abstract public class Powerup {
    private static final int mySpeed = 40;
    protected ImageView myPowerImage;
    private double myVelocityY = 1;

    public Powerup(){}

    /***
     * creates each individial image for the powerup falling
     * @return
     */
    abstract protected ImageView createPowerUpImage();

    /***
     * implements the specific power of whichever powerup
     * @param sceneSize
     * @param root
     * @param myGame
     */
    abstract protected void implementPowerUp(int sceneSize, Group root, RunGame myGame); //probably will need to
                                    // take root, maybe ball, paddle, and boolean?

    /***
     * sets the powerup image that was created using createPowerUpImage  in the actual game
     * @param fallingPowerups
     * @param root
     * @param blockSize
     * @param block
     * @return
     */
    public Powerup createNewPowerUp(List<Powerup> fallingPowerups, Group root, int blockSize, Block block){
        myPowerImage = createPowerUpImage();
        myPowerImage.setX(block.getMyShape().getX() + blockSize / 2);
        myPowerImage.setY(block.getMyShape().getY());
        root.getChildren().add(myPowerImage);
        fallingPowerups.add(this);
        return this;
    }

    /***
     * removes the image that is falling. Called for when you lose a life or make it to next level
     * while a powerup is falling
     * @param root
     */
    public void deleteFallingPowerUp(Group root){
        root.getChildren().remove(this.myPowerImage);
    }

    /***
     * makes the powerup fall down the screen
     * @param elapsedTime
     */
    void move(double elapsedTime){
        myPowerImage.setY(myPowerImage.getY() + (myVelocityY * mySpeed) * elapsedTime);
    }

    /***
     * checks to see if a powerup is still falling or if it fell through the bottom. Use this
     * so that you can remove the powerup from the list of falling powerups
     * @param sceneLength
     * @param root
     * @param removePowerups
     * @return True if still falling, false if otherwise
     */
    public Boolean checkForPowerUp(int sceneLength, Group root, List<Powerup> removePowerups){
        if(myPowerImage.getY()>0 && myPowerImage.getY()>sceneLength){
            root.getChildren().remove(myPowerImage);
            removePowerups.add(this);
            return false;
        }
        return true;

    }


}
