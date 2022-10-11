public class Boss extends Monster {
    boolean hasKey;

    public Boss(int gameLevel, int[][] initialPattern) {
        super(
                (2 * gameLevel + 1) * (int) (1 + Math.random() * 6),
                (2 * gameLevel + 1) * (int) (1 + Math.random() * 6),
                ((gameLevel / 2) + (1 / 2)) * (int) (1 + Math.random() * 6),
                (1 + (int) (1 + Math.random() * 6)) * gameLevel
        );
        randomPosition(initialPattern);
        setImage("img/boss.png");
    }

    public boolean isHasKey() {
        return hasKey;
    }

    public void setHasKey(boolean hasKey) {
        this.hasKey = hasKey;
    }
}
