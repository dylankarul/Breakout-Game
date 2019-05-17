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
 * Purpose: this is a block that can take multiple hits to break it fully
 * You would use it in order to have different types of blocks in the game
 *
 * Assumptions: The max damage of this block is 3, and min is 1
 *
 * Dependencies: example package and abstract Block class
 *
 * How to use it: new MultipleHitBlock(all parameters, indicate the damage amount). This will add this
 *  * type of block to the game and the blockList. When it gets hit, then it will change
 *  its image or disappear
 */

public class MultipleHitBlock extends Block {

    private int damage = 3;

    private final List<ImageView> damage_blocks = List.of(
            new ImageView(new Image(this.getClass().getClassLoader().getResourceAsStream("brick1.gif"))),
            new ImageView(new Image(this.getClass().getClassLoader().getResourceAsStream("brick2.gif"))),
            new ImageView(new Image(this.getClass().getClassLoader().getResourceAsStream("brick3.gif")))
    );



    protected MultipleHitBlock(int damageAmount, int blockWidth, int blockHeight, double xSpot, double ySpot, Group root, List<Block> blockList){
        super(blockWidth, blockHeight);
        damage = damageAmount;
        myShape = damage_blocks.get(damage-1);
        this.setBlockSize(myShape);
        myShape.setX(xSpot);
        myShape.setY(ySpot);
        root.getChildren().add(myShape);
        blockList.add(this);
    }

    /***
     * The case in which the block gets hit. If damage is 1, the block will
     * disappear. If block is greater than 1, it will be replaced with a new image
     * , have its damage decreased by 1.
     * @param bouncer - the games bouncer that is hitting the block
     * @param b - bouncer class that created bouncer
     * @param root - the Group that contains the game and blocks
     * @param hitList - the List of all blocks that were hit
     */
    @Override
    public void blockHit(ImageView bouncer, Bouncer b, Group root, List<Block> hitList) {
        b.bounceBlock(myShape);
        double xSpot=myShape.getX();
        double ySpot=myShape.getY();
        root.getChildren().remove(myShape);
        damage--;
        if(damage>0) {
            myShape = damage_blocks.get(damage-1);
            setBlockSize(myShape);
            myShape.setX(xSpot);
            myShape.setY(ySpot);
            root.getChildren().add(myShape);
        }
        else{
            hitList.add(this);
        }
    }


}

