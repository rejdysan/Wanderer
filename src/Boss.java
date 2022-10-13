public class Boss extends Monster {
    public Boss(int gameLevel, int[][] initialPattern, boolean hasKey) {
        super(hasKey);
        setMaxHp(2 * gameLevel * ((int) (1 + Math.random() * 6)) + ((int) (1 + Math.random() * 6)));
        setHp(getMaxHp());
        setDp(((int) (1 + Math.random() * 6) * gameLevel / 2) + ((int) (1 + Math.random() * 6)) / 2);
        setSp((1 + (int) (1 + Math.random() * 6)) * gameLevel);
        randomPosition(initialPattern);
        setImage("img/boss.png");
    }
}
