package example;

import example.blocks.BlockSetUp;
import example.powerups.BiggerBallPowerup;
import example.powerups.LongerPaddlePowerup;
import example.powerups.PointsMultiplierPowerup;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/***
 * Main class to run the game.
 */
public class RunGame extends Application{

    private static final String TITLE = "Bouncer Game";
    private static final int SIZE = 400;
    private static final Paint BACKGROUND = Color.BLACK;
    private static final int FRAMES_PER_SECOND = 60;
    private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;

    private Scene myScene;
    private Group root = new Group();
    private Bouncer b = new Bouncer();
    private ImageView myBouncer;
    private Paddle p;
    private ImageView myPaddle;
    private Test t;
    private Scene mySplash;
    private BlockSetUp Blocks = new BlockSetUp();
    private List<Block> blockList;
    private List<Block> hitList;
    private List<Powerup> fallingPowerups;
    private Timeline animation = new Timeline();
    private IO HUD = new IO();
    private boolean startUp = true;
    private boolean TESTING = false;
    private int testNumber;

    private HighScore highScoreClass = new HighScore();

    private int myLives = 3;
    private int myScore = 0;
    private int myLevel = 1;
    private int counter = 0;
    private int ORIGINAL_SCORE_PER_HIT = 100;
    private int SCORE_PER_HIT = 100;
    private boolean pointsMultiplier = false;
    private double ballSpeedMultiplier = 1.2;
    private Stage stg;

    /***
     * starts the process of setting up the game. Makes it run
     * @param stage
     */
    @Override
    public void start(Stage stage){
        stg = stage;
        startSplash();
    }

    private void startSplash(){
        splashScreen splash = new splashScreen();
        mySplash = splash.setUpSplashScreen(SIZE, myLives);
        stg.setScene(mySplash);
        stg.setTitle(TITLE);
        stg.show();
        mySplash.setOnKeyPressed(e -> handleKeytoStart(e.getCode()));
    }

    private void handleKeytoStart(KeyCode code) {
        root = new Group();

        if(code == KeyCode.ENTER || code == KeyCode.SPACE) {
            setScene();
        } else if (code == KeyCode.COMMA){
            TESTING = true;
            testNumber = 1;
            setScene();
        } else if (code == KeyCode.PERIOD){
            TESTING = true;
            testNumber = 2;
            setScene();
        } else if (code == KeyCode.SLASH){
            TESTING = true;
            testNumber = 3;
            setScene();
        }
    }

    private void setScene(){
        myScene = setup(SIZE, SIZE, BACKGROUND, myLevel);
        stg.setScene(myScene);
        stg.setTitle(TITLE);
        stg.show();
        if(startUp) {
            var frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(SECOND_DELAY));
            animation.setCycleCount(Timeline.INDEFINITE);
            animation.getKeyFrames().add(frame);
            startUp = false;
        }
        animation.play();
    }

    private Scene setup(int width, int height, Paint background, int level) {
        fallingPowerups = new ArrayList<>();
        var scene = new Scene(root, width, height, background);

        if(TESTING){
            if(testNumber == 1){
                t = new testBreakOrBounce("test" + testNumber + "_config_" + myLevel + ".txt", myLevel, b);
            } else if(testNumber == 2){
                t = new testCheatCode("test" + testNumber + "_config_" + myLevel + ".txt", this, b);
            } else if(testNumber == 3){
                t = new testLoseLifeBallSpeed("test" + testNumber + "_config_" + myLevel + ".txt", myLevel);
            }
        } else {
            t = new NormalConfig("basic_config_" + level + ".txt");
        }
        File layout = new File(t.info.get(0));

        blockList = Blocks.generateLayout(layout, SIZE, root);
        hitList = Blocks.getHitList();

        myBouncer = b.createBouncer(SIZE, t.info);

        p = new Paddle();
        myPaddle = p.createPaddle(SIZE);

        HUD.textToRoot(root);

        root.getChildren().addAll(myBouncer, myPaddle);

        scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));

        return scene;
    }

    private void step (double elapsedTime) {
        t.runTest();
        b.move(elapsedTime, myBouncer);

        if (myPaddle.getBoundsInParent().intersects(myBouncer.getBoundsInParent())) {
            b.bouncePaddle(myPaddle);
        }

        b.bounceWall(myBouncer, SIZE, SIZE);

        t.giveData(counter, SIZE, animation, root, hitList, myLives, myLevel);

        if(hitList.size() == blockList.size()){
            if(myLevel == 3) {
                HUD.endGame("YOU WIN!", root, myBouncer, animation);
                restartGame();
            } else {
                myLevel++;
                nextLevel();
            }
        }

        ArrayList<Powerup> removePowerUp= new ArrayList<>();
        for(Powerup fallingPower:fallingPowerups){
            fallingPower.move(elapsedTime);
            fallingPower.checkForPowerUp(SIZE, root, removePowerUp);
            powerUpIntersectPadde(fallingPower, removePowerUp);
        }
        fallingPowerups.removeAll(removePowerUp);

        int blocksDeleted = hitList.size();
        if(Blocks.blockIntersect(myBouncer, b, root)){

            if(hitList.size()>blocksDeleted ) {
                Block blockHit = hitList.get(hitList.size() - 1);
                decideIfPowerUp(blockHit);
            }
            HUD.updateScore(root, pointsMultiplier, SCORE_PER_HIT);
        }

        if (b.bouncerFellThrough(myBouncer, SIZE)){
            if(myLives > 1) {
                myLives--;
                HUD.updateLives(myLives, root);
                resetStage(true);
            }
            else {
                myLives--;
                HUD.updateLives(myLives, root);
                HUD.endGame("YOU LOSE!", root, myBouncer, animation);
                restartGame();
            }

        }

        HUD.updateHighScoreFile();
        counter++;
    }

    private void nextLevel(){
        animation.stop();
        counter = 0;
        b.setBOUNCER_SPEED(b.getBOUNCER_SPEED() * ballSpeedMultiplier);
        HUD.updateCurrentLevel(myLevel, root);
        HUD.loading(root);
        fallingPowerups.removeAll(fallingPowerups);
        myScene.setOnKeyPressed(e -> handleKeytoStart(e.getCode()));
    }

    private void restartGame(){
        animation.stop();
        myLevel=1;
        blockList.removeAll(blockList);
        hitList.removeAll(hitList);
        fallingPowerups.removeAll(fallingPowerups);
        myLives = 3;
        myScore = 0;
        counter = 0;
        SCORE_PER_HIT = 100;
        HUD = new IO();
        b.resetBOUNCER_SPEED();
        myScene.setOnKeyPressed(e -> handleKeytoStart(e.getCode()));
    }

    private void resetStage(boolean lostLife) {
        if (b.getYVelocity() < 0){
            b.reverseYSpeed();
        }

        b.centerBall(SIZE);

        p.centerPaddle(myPaddle, SIZE);

        if(lostLife) {
            root.getChildren().remove(myBouncer);
            myBouncer = b.createBouncer(SIZE, t.info);
            b.centerBall(SIZE);
            root.getChildren().add(myBouncer);

            root.getChildren().remove(myPaddle);
            myPaddle = p.createPaddle(SIZE);
            p.centerPaddle(myPaddle, SIZE);
            root.getChildren().add(myPaddle);

            for(Powerup fallingPowerUp:fallingPowerups){
                fallingPowerUp.deleteFallingPowerUp(root);
            }
            fallingPowerups.removeAll(fallingPowerups);

            SCORE_PER_HIT = ORIGINAL_SCORE_PER_HIT;
        }
    }

    private void powerUpIntersectPadde(Powerup fallingPower, List<Powerup> removePowerups){
        if(fallingPower.myPowerImage.getBoundsInParent().intersects(myPaddle.getBoundsInParent())){
            fallingPower.deleteFallingPowerUp(root);
            removePowerups.add(fallingPower);
            fallingPower.implementPowerUp(SIZE, root, this);
        }
    }

    private void decideIfPowerUp(Block blockHit){
        int randomNum = (int )(Math.random()*21);
        if(randomNum==1) {
            Powerup generatedPower = new PointsMultiplierPowerup();
            generatedPower.createNewPowerUp(fallingPowerups, root, blockHit.getBlockWidth(), blockHit);
        }
        if(randomNum==2){
            Powerup generatedPower = new BiggerBallPowerup();
            generatedPower.createNewPowerUp(fallingPowerups, root, blockHit.getBlockWidth(), blockHit);
        }
        if(randomNum==3){
            Powerup generatedPower = new LongerPaddlePowerup();
            generatedPower.createNewPowerUp(fallingPowerups, root, blockHit.getBlockWidth(), blockHit);
        }
    }

    /***
     * Used by the user to input key codes to activate cheat codes and/or move the paddle
     * Assumptions: A paddle exists. And "b" the bounce class is not null.
     * @param code
     */
    public void handleKeyInput (KeyCode code) {
        p.movePaddle(code, myPaddle, SIZE);

        if (code==KeyCode.L){
            myLives++;
            HUD.updateLives(myLives, root);
        }
        if (code==KeyCode.R){
            resetStage(false);
        }
        if (code == KeyCode.S){
            b.setBOUNCER_SPEED(b.getBOUNCER_SPEED() / 2);
        }
        if (code == KeyCode.Q){
            b.setBOUNCER_SPEED(b.getBOUNCER_SPEED() * 2);
        }
    }

    /***
     * getBouncer() allows for another class to access the game's bouncer image
     * @return the image of the bouncer that is in root
     */
    public ImageView getBouncer(){
        return myBouncer;
    }

    /***
     * getPaddle() allows for another class to access the game's paddle image
     * @return the image of the paddle that is in root
     */
    public ImageView getPaddle(){
        return myPaddle;
    }

    /***
     * gets the current integer value for how much the score is incremented after hitting a block
     * @return how much score increases, aka global private member variable, SCORE_PER_HIT
     */
    public int getScorePerHit(){
        return SCORE_PER_HIT;
    }

    /***
     * Allows for another class to set how much the score is incremented after a block is hit
     * @param score, which is the score that it will be set to.
     */
    public void setScorePerHit(int score){
        SCORE_PER_HIT = score;
    }
}