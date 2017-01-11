/**
 * Created by Johan on 17/12/2016.
 */
public class FitnessCalc {
    private int goalY;

    public FitnessCalc(int width, int height) {
        goalY = height-50;
    }

    public void calcFitness(Player p) {
        int difY = goalY - p.getYPosition();

        p.setFitness(difY);
    }
}
