public class Skeleton extends Monster {
    boolean hasKey;
    public Skeleton(int gameLevel, int[][] initialPattern) {
        super(
                2 * gameLevel * (int) (1 + Math.random() * 6),
                2 * gameLevel * (int) (1 + Math.random() * 6),
                (gameLevel / 2) * (int) (1 + Math.random() * 6),
                gameLevel * (int) (1 + Math.random() * 6)
        );
        randomPosition(initialPattern);
        setImage("img/skeleton.png");
    }

    public boolean isHasKey() {
        return hasKey;
    }

    public void setHasKey(boolean hasKey) {
        this.hasKey = hasKey;
    }
}
