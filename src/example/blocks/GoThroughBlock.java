package example.blocks;

import example.Block;
import example.Bouncer;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.List;

/***
 * Author: Dylan
 *
 * Purpose: this is a specific type of block, which the ball breaks through
 * and doesn't bounce off of it.
 *
 * Assumptions: none
 *
 * Depenencies: example package
 *
 * How to use it: new GoThroughBlock(all parameters). This will add this
 * type of block to the game and the blockList.
 */

public class GoThroughBlock extends Block {

    private ImageView blockImage = new ImageView(new Image(this.getClass().getClassLoader().getResourceAsStream("brick11.gif")));

    protected GoThroughBlock(int blockWidth, int blockHeight, double xSpot, double ySpot, Group root, List<Block> blockList){
        super(blockWidth, blockHeight);
        myShape = blockImage;
        this.setBlockSize(myShape);
        myShape.setX(xSpot);
        myShape.setY(ySpot);
        root.getChildren().add(myShape);
        blockList.add(this);
    }

    /***
     * If a block gets hit, this method is called. It removes that block from
     * the game
     * and adds it to the hitlist
     * @param bouncer the ball that is hitting the block
     * @param b the bouncer class of the ball
     * @param root the Group which contains the blocks and everything in the game
     * @param hitList a List of all the blocks that have been destroyed
     */
    @Override
    public void blockHit(ImageView bouncer, Bouncer b, Group root, List<Block> hitList) {
        root.getChildren().remove(myShape);
        hitList.add(this);
    }

}
