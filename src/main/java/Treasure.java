public class Treasure {
    private int x;
    private int y;
    private char symbol;
    private int value;

    public Treasure(int x, int y, char symbol, int value) {
        this.x = x;
        this.y = y;
        this.symbol = symbol;
        this.value = value;
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

    public int getValue() {
        return value;
    }
}
