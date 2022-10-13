import java.util.Random;

public class Hero extends Character {
    private boolean strought = false;

    public Hero(int[][] initialPattern) {
        setMaxHp(20 + 3 * (int) (1 + Math.random() * 6));
        setHp(getMaxHp());
        setDp(2 * (int) (1 + Math.random() * 6));
        setSp(5 * (int) (1 + Math.random() * 6));
        randomPosition(initialPattern);
        setImage("img/hero-down.png");
    }

    public void levelUp() {
        setLevel(getLevel() + 1);
        setMaxHp(getMaxHp() + d6());
        setDp(getDp() + d6());
        setSp(getSp() + d6());
    }

    public void newArea() {
        Random random = new Random();
        int randNum = random.nextInt(0, 10);
        if (randNum == 0) {
            setHp(getMaxHp());
        } else if (randNum > 0 && randNum < 5) {
            setHp(Math.min(getHp() + getMaxHp() / 3, getMaxHp()));

        } else {
            setHp(Math.min(getHp() + getMaxHp() / 10, getMaxHp()));
        }

    }
}
