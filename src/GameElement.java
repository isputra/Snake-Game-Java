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
    public enum GameState {INITIALIZED, PLAYING, LEVELING_UP, GAME_OVER}
    public GameState state = GameState.INITIALIZED;
    public GameElement(int xMax, int yMax) {
        this.xMax = xMax;
        this.yMax = yMax;
        random = new Random();
        state = GameState.PLAYING;

        this.snake = new Snake(new Position(xMax / 2, yMax / 2), xMax, yMax);

        Food food = new Food(getRandomPosition());
        this.foods = new ArrayList<>(xMax * yMax);
        foods.add(food);
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
            state = GameState.GAME_OVER;
        }
    }
    private void snakeEatFood(){
        snake.growBody();
        SCORE++;
    }
    private Position getRandomPosition(){
        boolean overlap;
        int x, y;
        do {
            overlap = false;
            x = this.random.nextInt(0, xMax);
            y = this.random.nextInt(0, yMax);
            if(!Objects.isNull(this.snake)){
                for(Position body: this.snake.body){
                    if(body.isOverlap(new Position(x,y))){
                        overlap=true;
                        System.out.println("Overlap with body at x:"+body.x+", y:"+body.y);
                        break;
                    }
                }
            }
        }
        while(overlap);
        return new Position(x,y);
    }

}
