import java.util.ArrayList;
import java.util.Random;

public class GameElement {
    Snake snake;
    ArrayList<Food> foods;
    Random random;
    public static int SCORE = 0;
    private final int xMax;
    private final int yMax;
    private boolean isGameOver;

    public GameElement(int xMax, int yMax) {
        this.xMax = xMax;
        this.yMax = yMax;
        random = new Random();
        isGameOver = false;
        Food food = new Food(getRandomPosition());
        this.foods = new ArrayList<>(xMax * yMax);
        foods.add(food);

        this.snake = new Snake(new Position(xMax / 2, yMax / 2), xMax, yMax);
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public void update() {
        for (Food food: foods){
            if(food.isEatenBy(snake)){
                snakeEatFood();
                foods.remove(food);
                foods.add(new Food(getRandomPosition()));
            }
        }
        this.snake.update();

        if(this.snake.checkCollision()){
            isGameOver = true;
        }
    }
    private void snakeEatFood(){
        snake.growBody();
        SCORE++;
    }
    private Position getRandomPosition(){
        int x = this.random.nextInt(0, xMax);
        int y = this.random.nextInt(0, yMax);
        return new Position(x,y);
    }

}
