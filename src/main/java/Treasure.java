import com.googlecode.lanterna.TextColor;

public class Treasure {
    private int x;
    private int y;
    private char symbol;
    private int value;
    private TextColor color;

    public Treasure(int x, int y, char symbol, int value, TextColor color) {
        this.x = x;
        this.y = y;
        this.symbol = symbol;
        this.value = value;
        this.color = color;
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

    public TextColor getColor() {
        return color;
    }
}

