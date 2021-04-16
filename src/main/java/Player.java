public class Player {
    private int x;
    private int y;
    private char symbol;
    private int previousX;
    private int previousY;

    public Player(int x, int y, char symbol) {
        this.x = x;
        this.y = y;
        this.symbol = symbol;
        this.previousX = x;
        this.previousY = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public char getSymbol() {
        return symbol;
    }

    public int getPreviousX() {
        return previousX;
    }

    public int getPreviousY() {
        return previousY;
    }

    public void moveUp(){
        previousX = x;
        previousY = y;
        y -= 1;
    }

    public void moveDown(){
        previousX = x;
        previousY = y;
        y += 1;
    }

    public void moveLeft(){
        previousX = x;
        previousY = y;
        x -= 1;
    }

    public void moveRight(){
        previousX = x;
        previousY = y;
        x += 1;
    }

    @Override
    public String toString() {
        return "monstergame2.Player{" +
                "x=" + x +
                ", y=" + y +
                ", symbol=" + symbol +
                ", previousX=" + previousX +
                ", previousY=" + previousY +
                '}';
    }
}