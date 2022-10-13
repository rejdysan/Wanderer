import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Monster extends Character {
    boolean hasKey;

    public Monster(boolean hasKey) {
        this.hasKey = hasKey;
    }

    public boolean isHasKey() {
        return hasKey;
    }

    public void setHasKey(boolean hasKey) {
        this.hasKey = hasKey;
    }

    public void nextPosition(int[][] boardPattern, int boardSize, int tileSize) {
        //Order: upper, lower, left, right
        List<int[]> possibleMovesList = new ArrayList<>();
        int[][] possibleMoves = {
                {getPosition()[0] - 1, getPosition()[1]},
                {getPosition()[0] + 1, getPosition()[1]},
                {getPosition()[0], getPosition()[1] - 1},
                {getPosition()[0], getPosition()[1] + 1}
        };
        for (int[] move : possibleMoves) {
            if (move[0] >= 0 && move[0] <= 9 && move[1] >= 0 && move[1] <= 9) {
                if (boardPattern[move[0]][move[1]] != 1) {
                    possibleMovesList.add(move);
                }
            }
        }
        Random random = new Random();
        setPosition(possibleMovesList.get(random.nextInt(possibleMovesList.size())));
    }
}
