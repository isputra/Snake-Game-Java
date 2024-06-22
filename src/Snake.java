import java.util.ArrayList;

public class Snake {
    public Position head;
    public ArrayList<Position> body;
    private int bodyLength;
    private final int xMax;
    private final int yMax;
    private boolean isAlive = true;
    public enum Direction {
        KEY_UP, KEY_DOWN, KEY_LEFT, KEY_RIGHT;
        public boolean isOpposite(Direction d){
            if(this.ordinal() == Direction.KEY_UP.ordinal()
                    && d.ordinal() == Direction.KEY_DOWN.ordinal()) {
                return true;
            } else if(this.ordinal() == Direction.KEY_DOWN.ordinal()
                    && d.ordinal() == Direction.KEY_UP.ordinal()) {
                return true;
            } else if(this.ordinal() == Direction.KEY_LEFT.ordinal()
                    && d.ordinal() == Direction.KEY_RIGHT.ordinal()) {
                return true;
            } else if(this.ordinal() == Direction.KEY_RIGHT.ordinal()
                    && d.ordinal() == Direction.KEY_LEFT.ordinal()) {
                return true;
            }
            return false;
        }
    }
    private Direction direction = Direction.KEY_UP;

    public Snake(Position head, int xMax, int yMax) {
        this.body = new ArrayList<>();
        this.bodyLength = 0;
        this.head = head;
        this.xMax = xMax;
        this.yMax = yMax;
    }

    public void update(){
        updateBody();
        updateHead();
    }
    private void updateHead(){
        switch (direction) {
            case KEY_UP -> move(0, -1);
            case KEY_DOWN -> move(0, 1);
            case KEY_LEFT -> move(-1, 0);
            case KEY_RIGHT -> move(1, 0);
        }
    }
    private void updateBody(){
        for (int i = bodyLength -1; i >= 0; i--) {
            if (i == 0) {
                body.get(i).replace(head);
            } else {
                body.get(i).replace(body.get(i - 1));
            }
        }
        /*if(bodyLength>0){
            body.addFirst(head.clone());
            body.removeLast();
        }*/
    }
    public void growBody(){
        Position tail = bodyLength == 0 ? head : body.get(bodyLength-1);
        switch (direction) {
            case KEY_UP :
                body.add(new Position(tail.x, tail.y + 1));
                break;
            case KEY_DOWN :
                body.add(new Position(tail.x, tail.y - 1));
                break;
            case KEY_LEFT :
                body.add(new Position(tail.x + 1, tail.y));
                break;
            case KEY_RIGHT :
                body.add(new Position(tail.x - 1, tail.y));
                break;
        }
        bodyLength++;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    private void move(int deltaX, int deltaY){
        head.x = Math.floorMod(head.x + deltaX, xMax);
        head.y = Math.floorMod(head.y + deltaY, yMax);
    }

    public boolean checkCollision(){
        for (Position b: body){
            if(b.isOverlap(head)){
                isAlive = false;
                return true;
            }
        }
        return false;
    }
}
