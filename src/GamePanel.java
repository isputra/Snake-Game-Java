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

        if(game.state == GameElement.GameState.GAME_OVER){
            renderGameOver(g2d);
            renderScore(g2d);
        } else if(game.state == GameElement.GameState.PLAYING){
            renderFoods(g2d);
            renderSnake(g2d);
            renderScore(g2d);
        }

        Toolkit.getDefaultToolkit().sync(); // necessary for linux users to draw  and animate image correctly
        g.dispose();
    }

    private void renderFoods(Graphics2D g2d){
        g2d.setColor(Color.RED);
        for (Food food: game.foods){
            g2d.fillOval(food.position.x * UNIT_SIZE, food.position.y* UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
        }
    }

    private void renderSnake(Graphics2D g2d){
        g2d.setColor(new Color(0, 128, 128));
        for (Position body: game.snake.body){
            //System.out.println("Drawing Body X: "+body.x+", Y: "+body.y);
            g2d.fillRect(body.x *UNIT_SIZE, body.y * UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
        }

        //System.out.println("Drawing Head X: "+game.snake.head.x+", Y: "+game.snake.head.y);
        g2d.setColor(new Color(0, 192, 128));
        g2d.fillRect(game.snake.head.x * UNIT_SIZE, game.snake.head.y * UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
    }

    private void renderGameOver(Graphics2D g2d){
        String text = "Game Over";
        g2d.setColor(Color.RED);
        g2d.setFont(new Font("Algerian", Font.BOLD, 75));
        FontMetrics metrics = getFontMetrics(g2d.getFont());
        g2d.drawString(text, (PANEL_WIDTH - metrics.stringWidth(text))/2, PANEL_HEIGHT/2);
    }

    private void renderScore(Graphics2D g2d){
        String text = "Score: " + GameElement.SCORE;
        g2d.setColor(Color.RED);
        g2d.setFont(new Font("Algerian", Font.BOLD, 18));
        FontMetrics metrics = getFontMetrics(g2d.getFont());
        g2d.drawString(text, (PANEL_WIDTH - metrics.stringWidth(text))/2, g2d.getFont().getSize());

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(game.state == GameElement.GameState.PLAYING){
            game.update();
        }
        repaint();
        if(game.state == GameElement.GameState.GAME_OVER){
            timer.stop();
        }
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
