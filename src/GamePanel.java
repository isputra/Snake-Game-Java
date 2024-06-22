import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GamePanel extends JPanel implements ActionListener {
    private final int PANEL_WIDTH = 600;
    private final int PANEL_HEIGHT = 600;
    private final int UNIT_SIZE = 20;
    private final int DELAY = 200;
    Timer timer;
    GameElement game;
    public GamePanel() {
        initUIElements();
        initGameElements();
    }

    public final void initGameElements(){
        timer = new Timer(DELAY, this);
        timer.start();
        this.game = new GameElement(PANEL_WIDTH/UNIT_SIZE, PANEL_HEIGHT/UNIT_SIZE);
    }

    private void initUIElements(){
        this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Panel Grid to help visualise the grid
        /*g2d.setColor(Color.GRAY);
        for (int i = 0; i < PANEL_HEIGHT / UNIT_SIZE; i++) {
            g2d.drawLine(0, i*UNIT_SIZE, PANEL_WIDTH, i*UNIT_SIZE);
            g2d.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, PANEL_HEIGHT);
        }*/

        g2d.setColor(Color.RED);
        for (Food food: game.foods){
            g2d.fillOval(food.position.x * UNIT_SIZE, food.position.y* UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
        }

        g2d.setColor(Color.MAGENTA);
        for (Position body: game.snake.body){
            //System.out.println("Drawing Body X: "+body.x+", Y: "+body.y);
            g2d.fillRect(body.x *UNIT_SIZE, body.y * UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
        }

        //System.out.println("Drawing Head X: "+game.snake.head.x+", Y: "+game.snake.head.y);
        g2d.setColor(Color.BLUE);
        g2d.fillRect(game.snake.head.x * UNIT_SIZE, game.snake.head.y * UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);

        Toolkit.getDefaultToolkit().sync(); // necessary for linux users to draw  and animate image correctly
        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        game.update();
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            //System.out.println("Key Pressed: "+e.getKeyCode());
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP -> {
                    if(canChangeDirection(game.snake, Snake.Direction.KEY_UP)){
                        game.snake.setDirection(Snake.Direction.KEY_UP);
                    }
                }
                case KeyEvent.VK_DOWN -> {
                    if (canChangeDirection(game.snake, Snake.Direction.KEY_DOWN)) {
                        game.snake.setDirection(Snake.Direction.KEY_DOWN);
                    }
                }
                case KeyEvent.VK_LEFT -> {
                    if (canChangeDirection(game.snake, Snake.Direction.KEY_LEFT)) {
                        game.snake.setDirection(Snake.Direction.KEY_LEFT);
                    }
                }
                case KeyEvent.VK_RIGHT -> {
                    if (canChangeDirection(game.snake, Snake.Direction.KEY_RIGHT)) {
                        game.snake.setDirection(Snake.Direction.KEY_RIGHT);
                    }
                }
            }
        }
    }
    public boolean canChangeDirection(Snake snake, Snake.Direction keyDirection) {
        if(snake.body.isEmpty()){
            return true;
        }
        return !snake.getDirection().isOpposite(keyDirection);
    }
}
