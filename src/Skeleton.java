public class Skeleton extends Monster {
    public Skeleton(int gameLevel, int[][] initialPattern, boolean hasKey) {
        super(hasKey);
        setMaxHp(2 * gameLevel * (int) (1 + Math.random() * 6));
        setHp(getMaxHp());
        setDp((int) (1 + Math.random() * 6) * gameLevel / 2);
        setSp(gameLevel * (int) (1 + Math.random() * 6));
        randomPosition(initialPattern);
        setImage("img/skeleton.png");
    }
}
