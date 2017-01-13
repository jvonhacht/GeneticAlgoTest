/**
 * Created by Johan on 17/12/2016.
 */
public class FitnessCalc {
    private int goalY;
    private final double FITNESSMULT_IF_STUCK = 0.7;

    /**
     * Ran when initialised. Get the goal y and x coordinate.
     * @param width
     * @param height
     */
    public FitnessCalc(int width, int height) {
        goalY = height-150;
    }

    /**
     * Method to calculate fitness of a player.
     * @param p
     */
    public void calcFitness(Player p) {
        int diffY = Math.abs(p.getYPosition() - goalY) / 10;
        if(p.getStuck()) {
            int diffStuckY = (int)(diffY * FITNESSMULT_IF_STUCK);
            p.setFitness(diffStuckY);
        }
        else {
            p.setFitness(diffY);
        }
    }
}
