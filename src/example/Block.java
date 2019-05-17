package example;

import javafx.scene.Group;
import javafx.scene.image.ImageView;
import java.util.List;

/***
 * Author: Dylan
 * Purpose: This is the abstract Block class. It would be used to create
 * new types of blocks, such as the 3 we created.
 *
 * Assumptions: none
 *
 * Dependencies: it uses the example package given.
 *
 * How to use it: This class can be used if we wanted to make different
 * block types in the future. They would extend this class.
 */

abstract public class Block {

    protected ImageView myShape;
    protected int myWidth;
    protected int myHeight;

    protected Block(int blockWidth, int blockHeight){
        myWidth = blockWidth;
        myHeight = blockHeight;
    }

    /***
     * abstract function that is called when a block is hit
     * @param bouncer
     * @param b
     * @param root
     * @param hitList
     */
    abstract public void blockHit(ImageView bouncer, Bouncer b, Group root, List<Block> hitList);

    protected void setBlockSize(ImageView blockShape){
        blockShape.setFitWidth(myWidth);
        blockShape.setFitHeight(myHeight);
    }

    /***
     * Since block width is different depending on how many blocks you configure in each row, this
     * function returns the width of the blocks
     * @return myWidth, which is the width
     */
    public int getBlockWidth(){
        return myWidth;
    }

    public ImageView getMyShape(){
        return myShape;
    }


}
