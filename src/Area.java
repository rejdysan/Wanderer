import java.awt.*;
import java.util.Random;

public class Area {

    public void drawTiles(Graphics graphics, int[][] boardPattern, int tileSize) {
        for (int i = 0; i < boardPattern.length; i++) {
            for (int j = 0; j < boardPattern.length; j++) {
                PositionedImage imageBoard = new PositionedImage(
                        (boardPattern[i][j] == 0) ? "img/floor.png" : "img/wall.png",
                        j * tileSize,
                        i * tileSize
                );
                imageBoard.draw(graphics);
            }
        }
    }

    //Method for random map generation.
    public int[][] createArray(int num, int dimensions) {
        int[][] array = new int[dimensions][dimensions];
        for (int i = 0; i < dimensions; i++) {
            for (int j = 0; j < dimensions; j++) {
                array[i][j] = num;
            }
        }
        return array;
    }

    public int[][] randomPattern(int dimensions, int maxTunnels, int maxLength) {
        int[][] map = createArray(1, dimensions);
        Random random = new Random();
        int currentRow = random.nextInt(0, dimensions);
        int currentColumn = random.nextInt(0, dimensions);
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        int[] randomDirection;
        int[] lastDirection = new int[2];

        while (maxTunnels > 0 && dimensions > 0 && maxLength > 0) {

            do {
                randomDirection = directions[random.nextInt(0, directions.length)];
            } while ((randomDirection[0] == -lastDirection[0] && randomDirection[1] == -lastDirection[1]) || (randomDirection[0] == lastDirection[0] && randomDirection[1] == lastDirection[1]));

            int randomLength = random.nextInt(0, maxLength);
            int tunnelLength = 0;

            while (tunnelLength < randomLength) {
                if (((currentRow == 0) && (randomDirection[0] == -1)) ||
                        ((currentColumn == 0) && (randomDirection[1] == -1)) ||
                        ((currentRow == dimensions - 1) && (randomDirection[0] == 1)) ||
                        ((currentColumn == dimensions - 1) && (randomDirection[1] == 1))) {
                    break;
                } else {
                    map[currentRow][currentColumn] = 0;
                    currentRow += randomDirection[0];
                    currentColumn += randomDirection[1];
                    tunnelLength++;
                }
            }
            if (tunnelLength >= 1) {
                lastDirection = randomDirection;
                maxTunnels--;
            }
        }
        return map;
    }
}