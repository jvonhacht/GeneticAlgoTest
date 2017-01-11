/**
 * Created by Johan on 17/12/2016.
 */
public class FitnessCalc {
    private int goalY;

    /**
     * Ran when initialised. Get the goal y and x coordinate.
     * @param width
     * @param height
     */
    public FitnessCalc(int width, int height) {
        goalY = height-50;
    }

    /**
     * Method to calculate fitness of a player.
     * @param p
     */
    public void calcFitness(Player p) {
        int difY = goalY - p.getYPosition();

        p.setFitness(difY);
    }
}
