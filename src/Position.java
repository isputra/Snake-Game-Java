public class Position {
    protected int x;
    protected int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void replace(Position p) {
        x = p.x;
        y = p.y;
    }

    public boolean isOverlap(Position p){
        return x  == p.x && y == p.y ;
    }

    @Override
    public String toString() {
        return "Position{" +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}

