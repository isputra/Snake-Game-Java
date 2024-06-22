public class Food {
    public Position position;

    public Food(Position position) {
        this.position = position;
    }
    public boolean isEatenBy(Snake snake) {
        return this.position.isOverlap(snake.head);
    }
}
