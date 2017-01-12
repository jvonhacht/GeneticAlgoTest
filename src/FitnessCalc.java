/**
 * Created by Johan on 17/12/2016.
 */
public class FitnessCalc {
    private int goalY;
    private final double FITNESSMULT_IF_STUCK = 0.85;

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

        if(p.getStuck()) {
            int difStuckY = (int)(difY * FITNESSMULT_IF_STUCK);
            p.setFitness(difStuckY);
        }
    }
}
