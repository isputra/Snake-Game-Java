import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class GameElement {
    Snake snake;
    ArrayList<Food> foods;
    Random random;
    public static int SCORE = 0;
    private final int xMax;
    private final int yMax;

    public GameElement(int xMax, int yMax) {
        this.xMax = xMax;
        this.yMax = yMax;
        random = new Random();
        Food food = new Food(getRandomPosition());
        this.foods = new ArrayList<>(xMax * yMax);
        foods.add(food);

        this.snake = new Snake(new Position(xMax / 2, yMax / 2), xMax, yMax);
    }

    public void update() {
        Food foodEaten = null;
        for (Food f: foods){
            if(f.isEatenBy(snake)){
                System.out.println("Food is eaten by snake !!!");
                foodEaten = f;
                snakeEatFood(snake);
            }
        }
        if(!Objects.isNull(foodEaten)) {
            foods.remove(foodEaten);
            foods.add(new Food(getRandomPosition()));
            System.out.println("Eaten food is removed, here is new food.");
        }
        this.snake.update();
    }
    private void snakeEatFood(Snake snake){
        snake.growBody();
        SCORE++;
    }
    private Position getRandomPosition(){
        int x = this.random.nextInt(0, xMax);
        int y = this.random.nextInt(0, yMax);
        return new Position(x,y);
    }

}
