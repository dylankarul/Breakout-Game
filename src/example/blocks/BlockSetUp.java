package example.blocks;

import example.Block;
import example.Bouncer;
import example.blocks.GoThroughBlock;
import example.blocks.MultipleHitBlock;
import example.blocks.TransportBlock;
import javafx.scene.Group;
import javafx.scene.image.ImageView;

import java.io.File;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

/***
 * Author: Dylan
 *
 * Purpose: this class is used to scan in the block config file,
 * generate the block set up, create all the necessary blocks, and
 * also loops through the block list to see if any block is hit during a
 * given step
 *
 * Assumptions: If given an invalid file, this class would fail
 *
 * Dependencies: the example package
 *
 * How to use it: create a new BlockSetUp, call scanBlocks with a config file
 * to populate the lists of blocks, call generate layout to set up the game with blocks,
 * call block intersect to see if any block was hit
 */
public class BlockSetUp {
    private static List<Block> blockList;
    private static List<Block> hitList;
    private int blockHeight = 30;

    private List<ArrayList<String>> scanBlocks(File file){
        blockList = new ArrayList<>();
        hitList = new ArrayList<>();
        Scanner in = new Scanner(Block.class.getClassLoader().getResourceAsStream(file.getName()));

        ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();

        while(in.hasNextLine()){

            String line = in.nextLine();
            String[] split = line.split(" ");

            ArrayList<String> temp = new ArrayList<String>();
            for(String num : split){
                temp.add(num);
            }
            result.add(temp);
        }

        return result;
    }

    /***
     * Generates the layout based on the configuration file and adds each specific block to the game. While doing
     * so, it returns a list of all the blocks in that level.
     * @param gameLayout - a config file that represents the game layout
     * @param SIZE - the size of the scene in which the game will be played
     * @param root - the Group which all the blocks will be placed in
     * @return - blockList, a list of all the blocks in that level
     */
    public List<Block> generateLayout(File gameLayout, int SIZE, Group root){
        List<ArrayList<String>> layout = scanBlocks(gameLayout);
        int i = 0;
        double j = 0;
        for(List<String> row : layout){

            int rowLength = row.size();
            for(String num : row){
                double xPos = j/rowLength*SIZE;
                double yPos = i*blockHeight;
                if(num.equals("g")){
                    new GoThroughBlock(SIZE/rowLength, blockHeight, xPos, yPos, root, blockList);
                }
                else if(num.equals("t")){
                    new TransportBlock(SIZE/rowLength, blockHeight, xPos, yPos, root, blockList);
                }
                else if(!num.equals("0")){
                    new MultipleHitBlock(Integer.valueOf(num), SIZE/rowLength, blockHeight, xPos, yPos, root, blockList);
                }

                j++;
            }
            i++;
            j = 0;
        }
        return blockList;
    }

    /***
     * Goes through each block in the level, and checks to see if the bouncer intersects with that block
     * @param myBouncer - the bouncer image in the game
     * @param b - the Bouncer class that myBouncer was made in
     * @param root - the Group in which the blocks and bouncer are
     * @return True if there was an intersection, false if there was no intersection
     */
    public static boolean blockIntersect(ImageView myBouncer, Bouncer b, Group root) {

        for (Block block : blockList) {

            if (myBouncer.getBoundsInParent().intersects(block.getMyShape().getBoundsInParent()) && hitList.contains(block) != true) {
                block.blockHit(myBouncer, b, root, hitList);
                return true;
            }
        }
        return false;
    }

    /***
     * Returns private member variable hitList, a list of all blocks that have been destroyed
     * @return hitList, an arraylist of all the blocks that have been destroyed
     */
    public List<Block> getHitList(){
        return hitList;
    }
}
