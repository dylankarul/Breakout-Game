package example;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.List;

/***
 * Authors: Jaylyn and Dylan
 *
 * Purpose: This would be used to create a bouncer (or more) for
 * the game, and then get information about that bouncer
 *
 * Assumptions: none
 *
 * Dependencies: the example package
 *
 * How to use it: create a: new Bouncer(), then call createBouncer(sceneSize, info) to get the
 * actual image of the ball. Add it to the root and now its in the game! To add
 * more balls, create more new Bouncer()'s and repeat.
 *
 *
 */


public class Bouncer{

    private static final String BOUNCER_IMAGE = "orangeball.png";
    private static final int BOUNCER_SIZE = 15;
    private ImageView myBouncer;
    private double myVelocityX = 1;
    private  double myVelocityY = 1;
    private double original_bouncer_speed = 100;
    private double BOUNCER_SPEED = 100;

    public Bouncer(){
    }

    /***
     * Creates an imageview ball that will be the bouncer in the game
     * @param sceneSize - size of the scene the game is played in
     * @param info - a config file that states several things, among them being the bouncers original Y and X velocity
     * @return the imageview ball
     */
    public ImageView createBouncer(int sceneSize, List<String> info){
        var image = new Image(this.getClass().getClassLoader().getResourceAsStream(BOUNCER_IMAGE));
        ImageView bouncer = new ImageView(image);

        myBouncer = bouncer;

        myVelocityX *= Integer.valueOf(info.get(1));
        myVelocityY *= Integer.valueOf(info.get(2));

        myBouncer.setFitHeight(BOUNCER_SIZE);
        myBouncer.setFitWidth(BOUNCER_SIZE);
        myBouncer.setX(sceneSize / 2 - myBouncer.getBoundsInParent().getWidth() / 2);
        myBouncer.setY(sceneSize / 2 - myBouncer.getBoundsInLocal().getHeight() / 2);
        return bouncer;
    }

    /***
     * Gets the actual image of the bouncer that is in the root of the Game
     * @return myBouncer, the image.
     */
    public ImageView getBouncerImage(){
        return myBouncer;
    }

    /***
     * moves the bouncer along the screen. Sets the X and Y position based on its X and Y velocity
     * @param elapsedTime - how many frames occur per second
     * @param bouncer - the ball in the game that we are moving
     */
    void move(double elapsedTime, ImageView bouncer){
        bouncer.setY(bouncer.getY() + (myVelocityY * BOUNCER_SPEED) * elapsedTime);
        bouncer.setX(bouncer.getX() + (myVelocityX * BOUNCER_SPEED) * elapsedTime);
    }

    /***
     * Reverses the Y direction of the bouncer. If it is going up, then after this call it will go down
     * and vice versa.
     */
    void reverseYSpeed(){
        myVelocityY *= -1;
    }

    /***
     * When the bouncer hits the paddle, it reverses the Y speed so that
     * it will bounce upwards. Then, depending on where the bouncer hits
     * on the paddle and which direction it is coming from, it decides whether
     * to reverse the x direction or not. If its coming from the left and hits the left
     * it reverses. If its coming from the right and hits the right, it reverses
     * @param paddle is the paddle in the game
     */
    void bouncePaddle(ImageView paddle){
        myVelocityY *= -1;
        double paddleWidth = paddle.getBoundsInLocal().getWidth();
        double fractionOfWidth = paddleWidth/5;
        if(myBouncer.getX()< paddle.getX()+fractionOfWidth && myVelocityX>0){
            myVelocityX*=-1;
        }
        else if(myBouncer.getX()+myBouncer.getBoundsInParent().getWidth() > paddle.getX()+(4*fractionOfWidth) && myVelocityX<0){
            myVelocityX*=-1;
        }

    }

    /***
     * If the bouncer hits a wall, we want it to bounce off.
     * So if it hits a side wall, we reverse the X velocity
     * If it hits the top wall, we reverse the Y velocity
     * @param bouncer the bouncer in the game
     * @param screenWidth the width of the game we are playing, helps find the left and right walls
     * @param screenHeight the height of the game
     */
    void bounceWall(ImageView bouncer, double screenWidth, double screenHeight){
        if(bouncer.getX() < 0 || bouncer.getX() > screenWidth - bouncer.getBoundsInLocal().getWidth() ){
            myVelocityX *= -1;

        }
        if(bouncer.getY() < 0 ){
            myVelocityY *= -1;
        }

    }

    /***
     * Useful for our transporting block. Takes the ball and places it
     * in the center of the game, and subsequently makes it so that
     * the velocity is positive, meaning the ball will travel down
     */
    public void transportBall(){
        myBouncer.setX(200);
        myBouncer.setY(200);
        myVelocityY = Math.abs(myVelocityY);
    }

    /***
     * Places the ball in the center of the screen
     * @param sceneSize the size of the screen
     */
    void centerBall(int sceneSize){
        myBouncer.setX(sceneSize / 2 - myBouncer.getBoundsInLocal().getWidth() / 2);
        myBouncer.setY(sceneSize / 2 - myBouncer.getBoundsInLocal().getHeight() / 2);
    }

    /***
     * Gets called if the bouncer hits a block. If it hits the side of a block,
     * you want to reverse the X velocity, but keep it going in the same Y direction.
     * If it hits the bottom or top of a block, then we want to reverse the Y
     * but keep the same X direction
     * @param block the block that is being hit
     */
    public void bounceBlock(ImageView block){

        if(Math.floor(myBouncer.getBoundsInLocal().getMaxX()) == block.getX()
                || Math.ceil(myBouncer.getBoundsInLocal().getMinX()) == block.getX() + block.getBoundsInLocal().getWidth()
                && Math.ceil(myBouncer.getY()) > block.getY()
                && Math.ceil(myBouncer.getY()) < block.getY() + block.getBoundsInLocal().getHeight()){

            myVelocityX *= -1;
        } else {
            myVelocityY *= -1;
        }
    }

    /***
     * returns the Y velocity of the bouncer
     * @return myVelocityY, the Y velocity
     */
    public double getYVelocity(){
        return myVelocityY;
    }

    /***
     * returns the X velocity of the bouncer
     * @return myVelocityX, which is the x velocity
     */
    public double getXVelocity(){
        return myVelocityX;
    }

    /***
     * Checks to see if bouncer fell through the bottom of the game
     * @param bouncer . the bouncer of the game
     * @param screenHeight the height of the game
     * @return True if the ball fell through, false if not
     */
    boolean bouncerFellThrough(ImageView bouncer, double screenHeight){
        if(bouncer.getY()>screenHeight){
            return true;
        }
        return false;
    }

    /***
     * Sets the bouncer speed. If speed is less than or equal to 0, set it to 2.
     * We don't want speed to ever go below zero, which is why we do this
     * @param speed - the speed that it is being set to
     */
    public void setBOUNCER_SPEED(double speed){
        if(speed <= 0){
            BOUNCER_SPEED = 2;
        } else {
            BOUNCER_SPEED = speed;
        }
    }

    /***
     * resets bouncer speed to its original speed, which is a member variable
     */
    public void resetBOUNCER_SPEED(){
        BOUNCER_SPEED = original_bouncer_speed;
    }

    /***
     * gets the bouncer speed
     * @return BOUNCER_SPEED, which holds the speed of the bouncer
     */
    public double getBOUNCER_SPEED(){
        return BOUNCER_SPEED;
    }
}
