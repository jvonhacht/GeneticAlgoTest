import com.sun.org.apache.bcel.internal.generic.POP;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by Johan on 17/12/2016.
 */
public class Population {
    final static double MUTATION_RATE = 0.03; //probability of mutation
    final static double CROSSOVER_RATE = 0.7; //probability of crossover
    final static int K_SIZE = 2; //tournament selection comparable
    private Random rand = new Random();

    private int popSize;
    private Player[] individuals;

    /**
     * Construct poplation.
     * @param size
     * @param initialise
     */
    public Population(int size, boolean initialise) {
        popSize = size;
        individuals = new Player[popSize];
        if(initialise) {
            for (int i=0; i<getSize(); i++) {
                Player newPlayer = new Player();
                newPlayer.generatePlayer();
                savePlayer(i,newPlayer);
            }
        }
    }

    /**
     * Return player at index.
     * @param index
     * @return Player
     */
    public Player getPlayer(int index) {
        return individuals[index];
    }

    public void printIndi() {
        System.out.println(Arrays.toString(individuals));
    }

    /**
     * Return number of players in population.
     * @return int
     */
    public int getSize() {
        return individuals.length;
    }

    /**
     * Save a player to specified index.
     * @param index
     * @param p
     */
    public void savePlayer(int index, Player p) {
        individuals[index] = p;
    }

    public void printPop() { System.out.print(Arrays.toString(individuals));}

    /**
     * Return the fittest of two random players in population.
     * @return Player, fittest
     */
    public Player tournamentSelection() {
        Player best = null;
        for (int i=0; i<K_SIZE; i++) {
            Player ind = individuals[rand.nextInt(popSize)];
            if(best == null || ind.getFitness() < best.getFitness()) {
                best = ind;
            }

        }
        return best;
    }

    /**
     * Method to crossover two parents DNA.
     * @param p1
     * @param p2
     * @return Player, child of parents
     */
    public Player crossOver(Player p1, Player p2) {
        Player child = new Player();
        for (int i=0; i<p1.getSize()/2; i++) {
            child.setGene(i,p1.getGene(i));
        }
        for (int i=p2.getFitness()/2; i<p2.getSize(); i++) {
            child.setGene(i,p2.getGene(i));
        }
        return child;
    }

    /**
     * Method to create a new generation of Players.
     * @return Population, new generation
     */
    public Player[] newGen() {
        Player[] childPop = new Player[popSize];
        for (int i=0; i<popSize; i++) {
            Player parent1 = tournamentSelection();
            Player parent2 = tournamentSelection();
            Player child = crossOver(parent1,parent2);
            child.mutate();
            childPop[i] = child;
        }
        return childPop;

    }

    public void setIndividuals(Player[] p) {
        individuals = p;
    }
}
