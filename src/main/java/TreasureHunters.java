import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.graphics.TextGraphics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class TreasureHunters {
    static int score=0;
    static int treasuresLeft = 5;

    public static void main(String[] args) {
        try {
            startGame();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            System.exit(1);
        } finally {
            System.out.println("Game over!");
        }

    }

    private static void gameOver(Terminal terminal) throws IOException {
        final TextGraphics textGraphics = terminal.newTextGraphics();
        //terminal.setForegroundColor(TextColor.ANSI.RED);
        textGraphics.putString(32, 11, "G A M E   O V E R", SGR.BLINK, SGR.BOLD);
    }

    private static void startGame() throws IOException, InterruptedException {

        Terminal terminal = createTerminal();

        drawLogo(terminal);

        ScoreArea scoreArea = createScoreArea(terminal);

        Player player = createPlayer();

        List<Monster> monsters = createMonsters();

        List<Treasure> treasures = createTreasures();

        drawTreasures(terminal, treasures);

        drawCharacters(terminal, scoreArea, player, monsters, treasures);





        do {
            KeyStroke keyStroke = getUserKeyStroke(terminal);

            movePlayer(player, keyStroke,scoreArea);

            moveMonsters(player, monsters);

            drawCharacters(terminal, scoreArea, player, monsters, treasures);

        } while (isPlayerAlive(player, monsters));

        terminal.setForegroundColor(TextColor.ANSI.RED);
        terminal.setCursorPosition(player.getX(), player.getY());
        terminal.putCharacter(player.getSymbol());
        terminal.bell();
        gameOver(terminal);
        terminal.flush();
    }

    private static void moveMonsters(Player player, List<Monster> monsters) {
        for (Monster monster : monsters) {
            monster.moveTowards(player);
        }
    }

    private static void movePlayer(Player player, KeyStroke keyStroke , ScoreArea scoreArea) {


            switch (keyStroke.getKeyType()) {
                case ArrowUp:
                    if (player.checkBlock(scoreArea.getY())) {
                        player.moveUp();
                    }
                    break;
                case ArrowDown:
                    player.moveDown();
                    break;
                case ArrowLeft:
                    player.moveLeft();
                    break;
                case ArrowRight:
                    player.moveRight();
                    break;
            }


    }

    private static KeyStroke getUserKeyStroke(Terminal terminal) throws InterruptedException, IOException {
        KeyStroke keyStroke;
        do {
            Thread.sleep(5);
            keyStroke = terminal.pollInput();
        } while (keyStroke == null);
        return keyStroke;
    }

    private static Player createPlayer() {
        return new Player(40, 12, '\u0398');
    }

    private static List<Monster> createMonsters() {
        List<Monster> monsters = new ArrayList<>();
        monsters.add(new Monster(63, 3, 'M', TextColor.ANSI.MAGENTA));
        monsters.add(new Monster(23, 22, 'M', TextColor.ANSI.MAGENTA));
        monsters.add(new Monster(23, 3, 'M', TextColor.ANSI.MAGENTA));
        monsters.add(new Monster(63, 22, 'M', TextColor.ANSI.MAGENTA));
        return monsters;
    }

    private static List<Treasure> createTreasures() {
        List<Treasure> treasures = new ArrayList<>();
        treasures.add(new Treasure(78, 4, '$',50, TextColor.ANSI.GREEN));
        treasures.add(new Treasure(2, 10, '£',100, TextColor.ANSI.YELLOW));
        treasures.add(new Treasure(50, 6, '€',150, TextColor.ANSI.CYAN));
        treasures.add(new Treasure(22, 18, '$',50, TextColor.ANSI.GREEN));
        treasures.add(new Treasure(38, 22, '£',100, TextColor.ANSI.YELLOW));
        treasures.add(new Treasure(72, 12, '€',150, TextColor.ANSI.CYAN));
        return treasures;
    }

    private static Terminal createTerminal() throws IOException {
        DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory();
        Terminal terminal = terminalFactory.createTerminal();
        terminal.setCursorVisible(false);
        return terminal;
    }

    private static void drawLogo(Terminal terminal) throws InterruptedException, IOException {
        final TextGraphics textGraphics = terminal.newTextGraphics();
        textGraphics.putString(10, 8, "___    ____    ____    ____    ____    _  _    ____    ____", SGR.BOLD);
        textGraphics.putString(10, 9, " |     |__/    |___    |__|    [__     |  |    |__/    |___ ", SGR.BOLD);
        textGraphics.putString(10, 10, " |     |  \\    |___    |  |    ___]    |__|    |  \\    |___ ", SGR.BOLD);
        textGraphics.putString(14, 12, "_  _    _  _    _  _    ___    ____    ____    ____         ", SGR.BOLD);
        textGraphics.putString(14, 13, "|__|    |  |    |\\ |     |     |___    |__/    [__          ", SGR.BOLD);
        textGraphics.putString(14, 14, "|  |    |__|    | \\|     |     |___    |  \\    ___]         ", SGR.BOLD);
        Thread.sleep(3000);
        terminal.clearScreen();
    }

    private static ScoreArea createScoreArea(Terminal terminal) throws IOException{
        final TextGraphics textGraphics = terminal.newTextGraphics();
        textGraphics.putString(1, 0, "SCORE: " + score, SGR.BOLD);
        textGraphics.putString(25, 0, "T R E A S U R E   H U N T E R S", SGR.BOLD);
        ScoreArea scoreArea = new ScoreArea(1,1,'\u2550');
        return scoreArea;
    }


    private static void drawCharacters(Terminal terminal, ScoreArea scoreArea, Player player, List<Monster> monsters, List<Treasure> treasures) throws IOException {

        for (Monster monster : monsters) {
            terminal.setCursorPosition(monster.getPreviousX(), monster.getPreviousY());
            terminal.setForegroundColor(TextColor.ANSI.BLACK);
            terminal.putCharacter(' ');

            terminal.setForegroundColor(monster.getColor());
            terminal.setCursorPosition(monster.getX(), monster.getY());
            terminal.putCharacter(monster.getSymbol());

        }
        terminal.setForegroundColor(TextColor.ANSI.WHITE);
        for (int x = scoreArea.getX(); x < 79;x++) {   // Printing the score area line
            terminal.setCursorPosition(x, scoreArea.getY());
            terminal.putCharacter(scoreArea.getSymbol());
        }

        terminal.setCursorPosition(player.getPreviousX(), player.getPreviousY());
        terminal.putCharacter(' ');

        terminal.setCursorPosition(player.getX(), player.getY());
        terminal.putCharacter(player.getSymbol());

        final TextGraphics textGraphics = terminal.newTextGraphics();

        for (Treasure treasure:treasures) {
            if (player.getX() == treasure.getX() && player.getY()==treasure.getY())
            {
                terminal.setCursorPosition(player.getX(), player.getY());
                terminal.putCharacter(player.getSymbol());
                terminal.setCursorPosition(player.getPreviousX(), player.getPreviousY());
                //terminal.bell();
                treasure.setY (0);
                treasure.setX (75);
                terminal.setCursorPosition(treasure.getX(), treasure.getY());
                terminal.putCharacter(treasure.getSymbol());
                score +=treasure.getValue();
                textGraphics.putString(7, 0, " " + score, SGR.BOLD);
                treasuresLeft = treasuresLeft -1;
                System.out.println(treasuresLeft);
                if (treasuresLeft == 0) {
                    terminal.setCursorPosition(78, 20);
                    terminal.putCharacter('\u2588');
                }
            }
            if (treasuresLeft == 0 && player.getX() == 78 && player.getY() == 20) {
                terminal.setForegroundColor(TextColor.ANSI.WHITE);
                textGraphics.putString(32, 11, "Y O U   W O N !", SGR.BLINK, SGR.BOLD);
            }
    }
        terminal.flush();
    }
    private static void drawTreasures (Terminal terminal, List<Treasure> treasures) throws IOException{
        for (Treasure treasure:treasures){
            terminal.setCursorPosition(treasure.getX(), treasure.getY());
            terminal.setForegroundColor(treasure.getColor());
            terminal.putCharacter(treasure.getSymbol());

        }
    }

    private static boolean isPlayerAlive(Player player, List<Monster> monsters){
        for (Monster monster : monsters) {
            if (monster.getX() == player.getX() && monster.getY() == player.getY()) {
                return false;
            }
        }
        return true;
    }
}