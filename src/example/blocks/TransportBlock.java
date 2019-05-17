package example.blocks;

import example.Block;
import example.Bouncer;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.List;
import java.util.ArrayList;

/***
 * Author: Dylan
 *
 * Purpose: this is a block that when it is broken, transports
 * the bouncer to the middle of the game.
 *
 * Assumptions: There is no block in the middle of the game
 *
 * Dependencies: example package and abstract Block class
 *
 * How to use it: new TransportBlock(all parameters, indicate the damage amount). This will add this
 *  * type of block to the game and the blockList. When it gets hit, it will disappear and transport the ball
 */

public class TransportBlock extends Block {

    private ImageView blockImage = new ImageView(new Image(this.getClass().getClassLoader().getResourceAsStream("brick12.gif")));

    protected TransportBlock(int blockWidth, int blockHeight, double xSpot, double ySpot, Group root, List<Block> blockList){
        super(blockWidth, blockHeight);
        //myShape = new ImageView(new Image(this.getClass().getClassLoader().getResourceAsStream("brick3.gif")));
        myShape = blockImage;
        this.setBlockSize(myShape);
        myShape.setX(xSpot);
        myShape.setY(ySpot);
        root.getChildren().add(myShape);
        blockList.add(this);
    }

    /***
     * when the block gets hit, it calls transportball, which is in the bouncer class,
     * removes the block from the game, and adds it to the list of deleted blocks.
     * @param bouncer
     * @param b
     * @param root
     * @param hitList
     */
    @Override
    public void blockHit(ImageView bouncer, Bouncer b, Group root, List<Block> hitList) {
        b.transportBall();
        root.getChildren().remove(myShape);
        hitList.add(this);

    }


}
