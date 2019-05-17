package example;

/***
 * Tests 2 things for each level. It tests that lose life works, and in doing so, makes sure that the ball speeds
 * up between levels. It is moreso testing that the ball speeds up and uses losing a life to help do so.
 */
public class testLoseLifeBallSpeed extends Test {

    protected int level;
    private int prevLives;

    public testLoseLifeBallSpeed(String config, int levelNum){

        super(config);
        level = levelNum;
    }


    /***
     * Notice the desiredCounter value decreases for each level, as since the ball is moving faster, it will
     * take shorter to lose a life.
     */
    @Override
    protected void runTest() {
        if(level==1){
            implementTest(125);
        }
        else if(level==2){
            implementTest(105);
        }
        else if(level==3){
            implementTest(88);
        }
    }

    private void implementTest(int desiredCounter){
        if (counter == desiredCounter-1) {
            prevLives = myLives;
        } else if (counter >= desiredCounter) {
            animation.stop();
            if(myLives == prevLives - 1){
                endTest("TEST PASSED", SIZE, root);
            } else {
                endTest("TEST FAILED", SIZE, root);
            }
        }
    }


}
