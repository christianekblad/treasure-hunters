import java.time.temporal.Temporal;

public class ScoreArea {
    private int x;
    private int y;
    private char symbol;

    public ScoreArea(int x, int y, char symbol) {
        this.x = x;
        this.y = y;
        this.symbol = symbol;
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
}
