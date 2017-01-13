import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Johan on 16/12/2016.
 */
public class Game extends JPanel{
    private Population p;
    private static MazeGenerator maze;
    private FitnessCalc calc;
    private boolean isRunning = true;
    private int fps = 5000;
    public static int windowWidth = 800;
    public static int windowHeight = 800;
    private int tickCount = 0;
    private int genCount = 0;
    private JFrame frame;
    BufferedImage backBuffer;
    Insets insets;

    /**
     * Constructor.
     */
    public Game() {
        maze = new MazeGenerator(800,800);
        p = new Population(50,true);
        p.printPop();
        calc = new FitnessCalc(windowWidth,windowHeight);
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.run();
        System.exit(0);
    }

    /**
     * Method that controls game speed and methods ran in each cycle.
     */
    public void run() {
        initialize();


        while(isRunning) {
            long time = System.currentTimeMillis();
            update();
            draw();

            time = (1000/fps) - (System.currentTimeMillis()-time);
            if(time>0) {
                try {
                    Thread.sleep(time);
                } catch (Exception e){}
            }
        }
        setVisible(false);
    }

    /**
     * Initialise.
     */
    public void initialize() {
        frame = new JFrame("Genetic algorithm");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        frame.add(new PaintMap());
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
        ImageIcon img = new ImageIcon("C:\\Users\\Johan\\Desktop\\background\\img.png");
        frame.setIconImage(img.getImage());
        insets = getInsets();
        setSize(insets.left + windowWidth + insets.right,
                insets.top + windowHeight + insets.bottom);

        backBuffer = new BufferedImage(windowWidth, windowHeight,   BufferedImage.TYPE_INT_RGB);
    }

    /**
     * Update population and keep track of ticks.
     */
    public void update() {
        //move pop
        for (int i=0; i<p.getSize(); i++) {
            p.getPlayer(i).move();
        }
        //calc fitness pop
        for (int i=0; i<p.getSize(); i++) {
            Player player = p.getPlayer(i);
            calc.calcFitness(player);
        }
        //tick controller
        if(tickCount>1200) {
            for (int i=0; i<p.getSize(); i++) {
                p.getPlayer(i).setDefaultPos();
            }
            tickCount = 0;
            //new gen
            for (int i=0; i<p.getSize(); i++) {
                System.out.print("Fitness: " + p.getPlayer(i).getFitness() + ", ");
                System.out.print("YPos: " + p.getPlayer(i).getYPosition() + ", ");
            }
            System.out.println("NEW LINE");
            Player[] newP = p.newGen();
            p.setIndividuals(newP);
            genCount ++;

        }
        tickCount ++;
    }

    /**
     * Paint frame.
     */
    public void draw() {
        frame.repaint();
        frame.add(new PaintMap());
    }

    public static MazeGenerator getMaze() {
        return maze;
    }

    /**
     * Class to paint map on init.
     */
    protected class PaintMap extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();
            paintMaze(g2d);
            paintPlayer(g2d);
            g2d.setColor(Color.GRAY);
            g2d.drawString("Generation: " + Integer.toString(genCount),50,20);
        }

        /**
         * Painting obstacles.
         * @param g2d
         */
        private void paintMaze(Graphics2D g2d) {
            for(int i=0; i<maze.getHeight(); i++) {
                for(int j=0; j<maze.getWidth(); j++) {
                    if(maze.getCell(i,j) != null) {
                        if(maze.isWall(i,j)) {
                            g2d.fillRect(i,j,5,5);
                        }
                    }
                }
            }
        }

        /**
         * Painting players.
         * @param g2d
         */
        public void paintPlayer(Graphics2D g2d) {
            g2d.setColor(Color.red);
            int count = 0;
            for (int i=0; i<p.getSize(); i++) {
                if(!(p.getPlayer(i)==null)) {
                    g2d.fillRect(p.getPlayer(i).getXPosition(),p.getPlayer(i).getYPosition(),3,3);
                }
                else {
                    System.out.println("empty player array" + count);
                    count ++;
                }

            }
        }
    }
}