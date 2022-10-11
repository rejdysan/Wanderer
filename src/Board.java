import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Board extends JComponent implements KeyListener {

    final private int boardSize = 720;

    final private int boardNumberOfTiles = 10;
    final private int tileSize = boardSize / boardNumberOfTiles;
    private int[][] boardPattern;
    private int gameLevel;
    Hero hero;
    List<Monster> monsters;


    public Board() {
        int[][] initialPattern = randomPattern();
        setPreferredSize(new Dimension(boardSize, boardSize + 30));
        setVisible(true);
        this.gameLevel = 1;
        this.boardPattern = initialPattern;
        this.hero = new Hero();
        this.monsters = new ArrayList<>();

        int getsKey = (int) (Math.random() * 4);
        for (int i = 0; i < 4; i++) {
            if (i == 0) {
                monsters.add(new Boss(getGameLevel(), initialPattern));
            } else {
                monsters.add(new Skeleton(getGameLevel(), initialPattern));
            }
        }
    }


    public int[][] randomPattern() {
        return new int[][]{
                {0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 1, 0, 1, 0, 1, 1, 0},
                {0, 1, 1, 1, 0, 1, 0, 1, 1, 0},
                {0, 0, 0, 0, 0, 1, 0, 0, 0, 0},
                {1, 1, 1, 1, 0, 1, 1, 1, 1, 0},
                {0, 1, 0, 1, 0, 0, 0, 0, 0, 0},
                {0, 1, 0, 1, 0, 1, 1, 0, 1, 0},
                {0, 0, 0, 0, 0, 1, 1, 0, 1, 0},
                {0, 1, 1, 1, 0, 0, 0, 0, 1, 0},
                {0, 0, 0, 1, 0, 1, 1, 0, 0, 0}
        };
    }

    public void drawTiles(Graphics graphics) {
        for (int i = 0; i < this.boardPattern.length; i++) {
            for (int j = 0; j < this.boardPattern.length; j++) {
                PositionedImage imageBoard = new PositionedImage(
                        (this.boardPattern[i][j] == 0) ? "img/floor.png" : "img/wall.png",
                        j * tileSize,
                        i * tileSize
                );
                imageBoard.draw(graphics);
            }
        }
    }

    public void drawHero(Graphics graphics) {
        PositionedImage imageHero = new PositionedImage(
                hero.getImage(),
                hero.getPosition()[1] * tileSize,
                hero.getPosition()[0] * tileSize
        );
        imageHero.draw(graphics);
    }

    public void drawMonsters(Graphics graphics) {
        for (Character monster : monsters) {
            if (monster.isAlive()) {
                PositionedImage imageSkeleton = new PositionedImage(
                        monster.getImage(),
                        monster.getPosition()[1] * tileSize,
                        monster.getPosition()[0] * tileSize
                );
                imageSkeleton.draw(graphics);
            }
        }
    }

    public void drawStatus(Graphics graphics) {
//        PositionedImage heart = new PositionedImage("img/heart.png", 0, 0);
//        heart.draw(graphics);
//        PositionedImage shield = new PositionedImage("img/shield.png", 0, 0);
//        shield.draw(graphics);
//        PositionedImage sword = new PositionedImage("img/sword.png", 0, 0);
//        sword.draw(graphics);
        graphics.drawString(
                "Hero (Level " + hero.getLevel() + ") " +
                        "    HP: " + hero.getHp() + "/" + hero.getMaxHp() + "     | " +
                        "    DP: " + hero.getDp() + "     | " +
                        "    SP: " + hero.getSp(),
                10,
                740
        );
    }

    public void drawAction(Graphics graphics) {
        for (Monster monster : monsters) {
            if (Arrays.equals(monster.getPosition(), hero.getPosition())) {
                hero.setFighting(true);
                graphics.setColor(Color.RED);
                graphics.drawString((monster instanceof Boss) ? "BOSS BATTLE!" : "Skeleton BATTLE!", 400, 740);
                if (hero.isStrought()) {
                    battle(monster);
                }
            }
        }
    }

    public void battle(Monster monster) {
        do {

        } while (hero.getHp() > 0 && monster.getHp() > 0);
    }

    public void generateBoard(Graphics graphics) {
        drawTiles(graphics);
        drawMonsters(graphics);
        drawStatus(graphics);
        drawHero(graphics);
        drawAction(graphics);
    }


    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);
        generateBoard(graphics);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("RPG Game");
        Board board = new Board();
        frame.add(board);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.pack();
        frame.addKeyListener(board);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        //Order: upper, lower, left, right
        int[] surroundingTiles = {
                (hero.getPosition()[0] == 0) ? 1 : getBoardPattern()[hero.getPosition()[0] - 1][hero.getPosition()[1]],
                (hero.getPosition()[0] == (boardSize - tileSize) / tileSize) ? 1 : getBoardPattern()[hero.getPosition()[0] + 1][hero.getPosition()[1]],
                (hero.getPosition()[1] == 0) ? 1 : getBoardPattern()[hero.getPosition()[0]][hero.getPosition()[1] - 1],
                (hero.getPosition()[1] == (boardSize - tileSize) / tileSize) ? 1 : getBoardPattern()[hero.getPosition()[0]][hero.getPosition()[1] + 1],
        };
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            if (surroundingTiles[0] == 0 && !hero.isFighting()) {
                hero.setPosition(new int[]{hero.getPosition()[0] - 1, hero.getPosition()[1]});
            }
            hero.setImage("img/hero-up.png");
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            if (surroundingTiles[1] == 0 && !hero.isFighting()) {
                hero.setPosition(new int[]{hero.getPosition()[0] + 1, hero.getPosition()[1]});
            }
            hero.setImage("img/hero-down.png");
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (surroundingTiles[2] == 0 && !hero.isFighting()) {
                hero.setPosition(new int[]{hero.getPosition()[0], hero.getPosition()[1] - 1});
            }
            hero.setImage("img/hero-left.png");
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (surroundingTiles[3] == 0 && !hero.isFighting()) {
                hero.setPosition(new int[]{hero.getPosition()[0], hero.getPosition()[1] + 1});
            }
            hero.setImage("img/hero-right.png");
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            hero.setStrought(true);
        }
        repaint();
    }

    public int[][] getBoardPattern() {
        return boardPattern;
    }

    public void setBoardPattern(int[][] boardPattern) {
        this.boardPattern = boardPattern;
    }

    public int getGameLevel() {
        return gameLevel;
    }

    public void setGameLevel(int gameLevel) {
        this.gameLevel = gameLevel;
    }

    public List<Monster> getMonsters() {
        return monsters;
    }

    public void setMonsters(List<Monster> monsters) {
        this.monsters = monsters;
    }
}
