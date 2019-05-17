package example;

import java.util.ArrayList;

/***
 * Class that tests that the different types of blocks function properly. Tests a different block for each level.
 */
public class testBreakOrBounce extends Test {

    protected int level;
    protected Block blockJustHit;
    protected double oldXVel;
    protected double oldYVel;
    protected Bouncer myBall;


    public testBreakOrBounce(String config, int levelNum, Bouncer bouncer){
        super(config);
        level = levelNum;
        myBall = bouncer;
        oldXVel = Integer.valueOf(info.get(1));
        oldYVel = Integer.valueOf(info.get(2));
    }

    /***
     * This runs the test for each specific level. For level 1, it makes sure that the block is destroyed and the ball
     * bounces back. For level 2, it makes sure the block was destroyed and it broke through, thus maintaining its velocity.
     * For level 3, it makes sure the ball was transported.
     */
    @Override
    void runTest() {
        if (level == 1) {
            if (counter == 100) {
                animation.stop();
                if (hitList.size() == 1 && oldYVel==myBall.getYVelocity()*-1) {
                    endTest("TEST PASSED", SIZE, root);
                } else {
                    endTest("TEST FAILED", SIZE, root);
                }
            }
        }

        if (level == 2) {
            if (counter == 65) {
                double currXVel = myBall.getXVelocity();
                double currYVel = myBall.getYVelocity();
                animation.stop();
                if (hitList.size() == 1 && currXVel==oldXVel && currYVel==oldYVel) {
                    endTest("TEST PASSED", SIZE, root);
                } else {
                    endTest("TEST FAILED", SIZE, root);
                }
            }
        }

        if (level == 3) {
            if (counter == 19) {
                animation.stop();
                double currXPos = myBall.getBouncerImage().getX();
                double currYPos = myBall.getBouncerImage().getY();
                if (hitList.size() == 1 && currXPos==SIZE/2 && currYPos==SIZE/2) {
                    endTest("TEST PASSED", SIZE, root);
                } else {
                    endTest("TEST FAILED", SIZE, root);
                }
            }
        }
    }
}
