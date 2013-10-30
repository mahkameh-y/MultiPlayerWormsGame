package server.entity;


import server.state.GameState;
import server.config.ConfigurationManager;
import shared.GUIScales;
import shared.entity.holes.HoleType;
import shared.entity.holes.BazookaHole;

/**
 * Created by IntelliJ IDEA.
 * User: a.moallem
 * Date: Jan 27, 2006
 * Time: 11:47:17 AM
 * To change this template use File | Settings | File Templates.
 */
public class Island {
    private byte[][] rectangles;
    private int maxRowIndex;
    private static Island ourInstance = new Island();

    private final int NUM_OF_RECTANGLES_EACH_ROW = 200;
    private final int NUM_OF_RECTANGLES_EACH_COLUMN = 50;
    private final int X_LENGTH_EACH_RECTANGLE = 10;
    private final int Y_LENGTH_EACH_RECTANGLE = 10;
    int initialHeight = GUIScales.gameYDimension - GUIScales.islandInitialY -
            (NUM_OF_RECTANGLES_EACH_COLUMN * Y_LENGTH_EACH_RECTANGLE);

    public static Island getInstance() {
        return ourInstance;
    }

    public int getX_LENGTH_EACH_RECTANGLE() {
        return X_LENGTH_EACH_RECTANGLE;
    }

    public int getY_LENGTH_EACH_RECTANGLE() {
        return Y_LENGTH_EACH_RECTANGLE;
    }

    public static void initIsland() {
        getInstance().rectangles = ConfigurationManager.getIslandRects();
    }

    private Island() {
        rectangles = new byte[NUM_OF_RECTANGLES_EACH_ROW][NUM_OF_RECTANGLES_EACH_COLUMN];
        maxRowIndex = NUM_OF_RECTANGLES_EACH_COLUMN;
    }

    public int getYLocation(int x) { // in method max ertefae jazire dar noghte x ro bar migardoone (nesbate be paieene jazire)
        int column = x / X_LENGTH_EACH_RECTANGLE;
        if (column < 0)
            column = 0;
        for (int i = 0; i < NUM_OF_RECTANGLES_EACH_COLUMN; i++) {
            if (column < NUM_OF_RECTANGLES_EACH_ROW) {
                if (rectangles[column][i] == 1)
                    return Y_LENGTH_EACH_RECTANGLE * (NUM_OF_RECTANGLES_EACH_COLUMN - i)
                            + GUIScales.islandInitialY;
            }
        }
        return GameState.getIslandY();
    }

    public void destroy(int x, int y, HoleType holeType) {
        int column = x / X_LENGTH_EACH_RECTANGLE;
        int row = (y - initialHeight) / Y_LENGTH_EACH_RECTANGLE;
        int numOfDestroyedRectsEachDirectionInRow = holeType.getWidth() / X_LENGTH_EACH_RECTANGLE / 2;
        int numOfDestroyedRectsEachDirectionInColumn = holeType.getHeight() / X_LENGTH_EACH_RECTANGLE / 2;
        for (int i = column - numOfDestroyedRectsEachDirectionInRow; i <= column + numOfDestroyedRectsEachDirectionInRow; i++) {
            if (i >= 0 && i < NUM_OF_RECTANGLES_EACH_ROW) {
                int max = row + numOfDestroyedRectsEachDirectionInColumn;
                if (holeType instanceof BazookaHole)
                    max--;
                for (int j = row - numOfDestroyedRectsEachDirectionInColumn; j <= max; j++) {
                    if (j >= 0 && j < NUM_OF_RECTANGLES_EACH_COLUMN) {
                        rectangles[i][j] = 0;
                    }
                }
            }
        }
    }
    public void buildWall(Wall wall) {
        int fromColumn = wall.getX() / X_LENGTH_EACH_RECTANGLE;
        int toColumn = fromColumn + wall.getWidth() / X_LENGTH_EACH_RECTANGLE;
        int fromRow = (wall.getY() - initialHeight) / Y_LENGTH_EACH_RECTANGLE;
//        fromRow -= 1;
        int toRow = fromRow + wall.getHeight() / Y_LENGTH_EACH_RECTANGLE;
        for (int i = fromRow; i < toRow; i++) {
            for (int j = fromColumn ; j <= toColumn; j++) {
                if (i >= 0 && i < NUM_OF_RECTANGLES_EACH_COLUMN && j >= 0 && j < NUM_OF_RECTANGLES_EACH_ROW)
                    rectangles[j][i] = 1;
            }
        }
    }


    public void sink() {
        int numOfRectangles = GUIScales.eachSinkingScale / Y_LENGTH_EACH_RECTANGLE;
        maxRowIndex -= numOfRectangles;
        for (int i = 0; i < NUM_OF_RECTANGLES_EACH_ROW; i++) {
            for (int j = maxRowIndex; j < maxRowIndex + numOfRectangles; j++) {
                if (j > 0 && j < NUM_OF_RECTANGLES_EACH_COLUMN)
                    rectangles[i][j] = 0;
            }
        }
        GameState.setIslandY(GameState.getIslandY() + GUIScales.eachSinkingScale);
    }

    public boolean isValidWormSpace(Entity entity, int Xofs, int Yofs) {
        int fromColumn = (entity.getX() + Xofs) / X_LENGTH_EACH_RECTANGLE;
        int toColumn = fromColumn + entity.getWidth() / X_LENGTH_EACH_RECTANGLE;

        if (fromColumn < 0) {
            fromColumn = 0;
            return false;
        }
        if (toColumn >= NUM_OF_RECTANGLES_EACH_ROW)
            toColumn = NUM_OF_RECTANGLES_EACH_ROW;

        int fromRow = ((entity.getY() - initialHeight) + Yofs) / Y_LENGTH_EACH_RECTANGLE;
        int toRow = fromRow + entity.getHeight() / Y_LENGTH_EACH_RECTANGLE;

        if (fromRow < 0)
            fromRow = 0;
        if (toRow >= NUM_OF_RECTANGLES_EACH_COLUMN)
            toRow = NUM_OF_RECTANGLES_EACH_COLUMN;

        for (int i = fromColumn; i < toColumn; i++) {
            for (int j = fromRow; j < toRow; j++) {
                if (rectangles[i][j] != 0)
                    return false;
            }
        }

        return true;
    }

    public int getYOfsOfUpwardValidSpace(Worm worm, int Xofs) {
        int numOfCandidates = GUIScales.MAX_UPWARD_ABILITY / Y_LENGTH_EACH_RECTANGLE;
        for (int k = 1; k <= numOfCandidates; k++) { //khode rooberoosho dige barresi nemikone
            int Yofs = k * Y_LENGTH_EACH_RECTANGLE;
            if (isValidWormSpace(worm, Xofs, -Yofs))
                return Yofs;
        }
        return -1;
    }

    public int getYOfsOfDownwardValidSpace(Entity entity, int Xofs) {
        int fromColumn = (entity.getX() + Xofs) / X_LENGTH_EACH_RECTANGLE;
        int toColumn = fromColumn + entity.getWidth() / X_LENGTH_EACH_RECTANGLE;
        int row = ((entity.getY() - initialHeight) + entity.getHeight()) / Y_LENGTH_EACH_RECTANGLE;
        int minYofs = NUM_OF_RECTANGLES_EACH_COLUMN;
        for (int i = fromColumn; i < toColumn; i++) {
            int newYofs = getYofsOfDownward(i, row); // masalan 1 2 ina barmigardoone
            if (newYofs < minYofs) {
                minYofs = newYofs;
            }
        }
        return minYofs * Y_LENGTH_EACH_RECTANGLE;
    }

    private int getYofsOfDownward(int column, int initialRow) {
        for (int j = initialRow; j < NUM_OF_RECTANGLES_EACH_COLUMN; j++) {
            if (column >= 0 && j >= 0 && column < NUM_OF_RECTANGLES_EACH_ROW)
                if (rectangles[column][j] == 1) {
                    return j - initialRow;
                }
        }
        return (NUM_OF_RECTANGLES_EACH_COLUMN - initialRow);
    }

}
