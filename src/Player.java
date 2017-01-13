import java.util.Arrays;
import java.util.Random;

/**
 * Created by Johan on 17/12/2016.
 */
public class Player {
    private int xPosition;
    private int yPosition;
    private static int defaultGeneLength;
    private boolean stuck;
    private int[] genes;
    private int fitness;
    private Random rand;
    private int moves;

    public Player() {
        xPosition = MazeGenerator.getWidth()/2;
        yPosition = 80;
        defaultGeneLength = 800;
        genes = new int[defaultGeneLength];
        fitness = 50;
        rand = new Random();
        moves = 0;
        stuck = false;
    }

    /**
     * Generate new player gene.
     */
    public void generatePlayer() {
        int decidedDirection = rand.nextInt(4);
        for (int i=0; i<defaultGeneLength; i+=1) {
            int randDirection = rand.nextInt(4);
            //Make every other move down.
            for (int j=0; j<1 ; j++) {
                if(rand.nextBoolean()) {
                    genes[i+j] = randDirection;
                }
                else {
                    genes[i+j] = decidedDirection;
                }
            }
        }
    }

    /**
     * Method to move player to default spawn position.
     */
    public void setDefaultPos() {
        xPosition = MazeGenerator.getWidth()/2;
        yPosition = 80;
        stuck = false;
    }

    /**
     * Get gene at specified index.
     * @param index
     * @return int (gene)
     */
    public int getGene(int index) {
        return genes[index];
    }

    /**
     * Set value of gene at specified index.
     * @param index
     * @param value
     */
    public void setGene(int index, int value) {
        genes[index] = value;
    }

    /**
     * Mutate a single gene.
     */
    public void mutate() {
        for (int i=0; i<defaultGeneLength*Population.MUTATION_RATE; i++) {
            int index = rand.nextInt(defaultGeneLength);
            this.setGene(index, rand.nextInt(4));
        }
    }


    /**
     * Return fitness of specified player.
     * @return fitness (int)
     */
    public int getFitness() {
        return fitness;
    }

    /**
     * Set fitness of player.
     * @param fitness
     */
    public void setFitness(int fitness) {
        this.fitness = fitness;
    }

    /**
     * Change player stuck state.
     * @param bool
     */
    public void setStuck(boolean bool) {
        stuck = bool;
    }

    /**
     * Method to return if player is stuck.
     * @return
     */
    public boolean getStuck() {
        return stuck;
    }

    /**
     * Return size of gene.
     * @return
     */
    public int getSize() {
        return genes.length;
    }

    /**
     * Return x position of player.
     * @return
     */
    public int getXPosition() {
        return xPosition;
    }

    /**
     * Return y position of player.
     * @return
     */
    public int getYPosition() {
        return yPosition;
    }

    /**
     * Method to move player and check if stuck.
     */
    public void move() {
        moves = moves % defaultGeneLength;
        if(xPosition>750 || xPosition<50 || yPosition>650 || yPosition<30) {
            stuck = true;
        }

        if(Game.getMaze().getCell(getXPosition() + 2,getYPosition()).isWall
                || Game.getMaze().getCell(getXPosition() - 2,getYPosition()).isWall
                || Game.getMaze().getCell(getXPosition(),getYPosition() + 2).isWall
                || Game.getMaze().getCell(getXPosition(),getYPosition() - 2).isWall
                || Game.getMaze().getCell(getXPosition() + 2,getYPosition() + 2).isWall
                || Game.getMaze().getCell(getXPosition() - 2,getYPosition() - 2).isWall
                || getYPosition()<30){
            stuck = true;
            //set to 0 if it hits wall.
        }
        if(!stuck) {
            if(genes[moves]==0) {
                yPosition -= 1;
            }
            if(genes[moves]==1) {
                xPosition += 1;
            }
            if(genes[moves]==2) {
                yPosition += 1;
            }
            if(genes[moves]==3) {
                xPosition -= 1;
            }
            moves ++;
        }
    }
}

