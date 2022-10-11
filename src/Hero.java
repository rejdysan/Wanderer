public class Hero extends Character {
    private boolean strought = false;
    public Hero() {
        super(
                20 + 3 * (int) (1 + Math.random() * 6),
                20 + 3 * (int) (1 + Math.random() * 6),
                2 * (int) (1 + Math.random() * 6),
                5 * (int) (1 + Math.random() * 6)
        );
        setPosition(new int[]{0, 0});
        setImage("img/hero-down.png");
    }

    public boolean isStrought() {
        return strought;
    }

    public void setStrought(boolean strought) {
        this.strought = strought;
    }
}
