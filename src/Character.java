import java.util.Random;

public class Character {
    private int hp;
    private int maxHp;
    private int dp;
    private int sp;
    private boolean isAlive = true;
    private int level = 1;
    private int[] position;
    private String image;
    private boolean fighting = false;
    private Character opponent;

    public int d6() {
        Random random = new Random();
        return random.nextInt(1, 7);
    }

    public void randomPosition(int[][] boardPattern) {
        Random random = new Random();
        int[] position = new int[2];
        do {
            position[0] = random.nextInt(0, boardPattern.length);
            position[1] = random.nextInt(0, boardPattern.length);
        } while (boardPattern[position[0]][position[1]] == 1);
        setPosition(position);
    }

    public void strike(Character otherCharacter) {
        if (2 * d6() + this.getSp() > otherCharacter.getDp()) {
            otherCharacter.setHp(otherCharacter.getHp() + otherCharacter.getDp() - (2 * d6() + this.getSp()));
        }
    }

    public int[] surroundingTiles(int[][] boardPattern, int boardSize, int tileSize) {
        //Order: upper, lower, left, right
        return new int[]{
                (getPosition()[0] == 0) ? 1 : boardPattern[getPosition()[0] - 1][getPosition()[1]],
                (getPosition()[0] == (boardSize - tileSize) / tileSize) ? 1 : boardPattern[getPosition()[0] + 1][getPosition()[1]],
                (getPosition()[1] == 0) ? 1 : boardPattern[getPosition()[0]][getPosition()[1] - 1],
                (getPosition()[1] == (boardSize - tileSize) / tileSize) ? 1 : boardPattern[getPosition()[0]][getPosition()[1] + 1],
        };
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    public int getDp() {
        return dp;
    }

    public void setDp(int dp) {
        this.dp = dp;
    }

    public int getSp() {
        return sp;
    }

    public void setSp(int sp) {
        this.sp = sp;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int[] getPosition() {
        return position;
    }

    public void setPosition(int[] position) {
        this.position = position;
    }

    public boolean isFighting() {
        return fighting;
    }

    public void setFighting(boolean fighting) {
        this.fighting = fighting;
    }

    public Character getOpponent() {
        return opponent;
    }

    public void setOpponent(Character opponent) {
        this.opponent = opponent;
    }
}
