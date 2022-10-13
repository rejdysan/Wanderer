import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;
import java.util.List;
import java.util.Timer;

public class Board extends JComponent implements KeyListener {
    final private int boardSize = 720;
    final private int boardNumberOfTiles = 10;
    final private int tileSize = boardSize / boardNumberOfTiles;
    private int[][] boardPattern;
    private int gameLevel = 1;
    Hero hero;
    List<Monster> monsters;
    Area area;
//    Timer timer;
//    TimerTask task;


    public Board() {
        setPreferredSize(new Dimension(boardSize, boardSize + 180));
        setVisible(true);
//        timer = new Timer();
//        task = new TimerTask() {
//            @Override
//            public void run() {
//                repaint();
//            }
//        };
//        timer.scheduleAtFixedRate(task, new Date(), 1000);
        area = new Area();
        this.boardPattern = area.randomPattern(boardNumberOfTiles, 20, 10);
        this.hero = new Hero(boardPattern);
        this.monsters = new ArrayList<>();
        int getsKey = (int) (Math.random() * 4);
        for (int i = 0; i < 4; i++) {
            if (i == 0) {
                monsters.add(new Boss(getGameLevel(), boardPattern, i == getsKey));
            } else {
                monsters.add(new Skeleton(getGameLevel(), boardPattern, i == getsKey));
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
        for (Monster monster : monsters) {
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

    public void drawStatusHero(Graphics graphics) {
        graphics.drawString(
                "A" + gameLevel + "  " +
                        "Hero (Level " + hero.getLevel() + ") " +
                        "    HP: " + hero.getHp() + "/" + hero.getMaxHp() + "     | " +
                        "    DP: " + hero.getDp() + "     | " +
                        "    SP: " + hero.getSp(),
                10,
                740
        );
    }

    public void drawStatusMonsters(Graphics graphics) {
        int ycoordinate = 760;
        for (Monster monster : monsters) {
            ycoordinate += 30;
            graphics.drawString(
                    monster.getClass().getName() + " " +
                            "HP: " + monster.getHp() + "/" + monster.getMaxHp() + " | " +
                            "DP: " + monster.getDp() + " | " +
                            "SP: " + monster.getSp() + " | " +
                            "Key: " + monster.isHasKey(),
                    10,
                    ycoordinate
            );
        }
    }

    public void drawAction(Graphics graphics) {
        for (Monster monster : monsters) {
            if (Arrays.equals(monster.getPosition(), hero.getPosition())) {
//                task.cancel();
                hero.setFighting(true);
                hero.setOpponent(monster);
                graphics.setColor(Color.RED);
                graphics.drawString((monster instanceof Boss) ? "BOSS BATTLE!" : "Skeleton BATTLE!", 400, 740);
            }
        }
    }

    public void drawGameOver(Graphics graphics) {
        graphics.drawRect(0, 0, 720, 720);
        graphics.setColor(Color.WHITE);
        graphics.drawString("GAME OVER", 360, 360);
    }

    public boolean battle(Character attacker, Character defender) {
        boolean key = false;
        attacker.strike(defender);
        if (defender.getHp() > 0) {
            defender.strike(attacker);
        }
        if (defender.getHp() <= 0) {
            //Kill monster or hero.
            defender.setAlive(false);
            //Level up if hero else remove monster from list.
            if (attacker instanceof Hero) {
                ((Hero) attacker).levelUp();
            }
            if (defender instanceof Monster) {
                key = ((Monster) defender).isHasKey();
                monsters.remove(defender);
            }
            //Get him out of fight.
            attacker.setFighting(false);
        }
        if (attacker.getHp() <= 0) {
            attacker.setAlive(false);
        }
        return key;
    }

    public void nextArea() {
        setBoardPattern(area.randomPattern(boardNumberOfTiles, 20, 10));
        gameLevel++;
        hero.newArea();
        hero.randomPosition(boardPattern);
        monsters.removeAll(monsters);
        int getsKey = (int) (Math.random() * 4);
        for (int i = 0; i < 4; i++) {
            if (i == 0) {
                monsters.add(new Boss(getGameLevel(), boardPattern, i == getsKey));
            } else {
                monsters.add(new Skeleton(getGameLevel(), boardPattern, i == getsKey));
            }
        }
    }

    public void generateBoard(Graphics graphics) {
        if (hero.isAlive()) {
            area.drawTiles(graphics, boardPattern, tileSize);
            drawMonsters(graphics);
            drawStatusHero(graphics);
            drawStatusMonsters(graphics);
            drawHero(graphics);
            drawAction(graphics);
        } else {
            drawGameOver(graphics);
        }
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
        int[] surroundingTiles = hero.surroundingTiles(boardPattern, boardSize, tileSize);
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            if (surroundingTiles[0] == 0 && !hero.isFighting() && hero.isAlive()) {
                hero.setPosition(new int[]{hero.getPosition()[0] - 1, hero.getPosition()[1]});
            }
            hero.setImage("img/hero-up.png");
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            if (surroundingTiles[1] == 0 && !hero.isFighting() && hero.isAlive()) {
                hero.setPosition(new int[]{hero.getPosition()[0] + 1, hero.getPosition()[1]});
            }
            hero.setImage("img/hero-down.png");
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (surroundingTiles[2] == 0 && !hero.isFighting() && hero.isAlive()) {
                hero.setPosition(new int[]{hero.getPosition()[0], hero.getPosition()[1] - 1});
            }
            hero.setImage("img/hero-left.png");
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (surroundingTiles[3] == 0 && !hero.isFighting() && hero.isAlive()) {
                hero.setPosition(new int[]{hero.getPosition()[0], hero.getPosition()[1] + 1});
            }
            hero.setImage("img/hero-right.png");
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE && hero.isFighting()) {
            if (battle(hero, hero.getOpponent())) {
                nextArea();
            }
        }
//        Code for moving when hero moves
        if ((e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT) && !hero.isFighting()) {
            for (Monster monster : monsters) {
                monster.nextPosition(boardPattern, boardSize, tileSize);
            }
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
