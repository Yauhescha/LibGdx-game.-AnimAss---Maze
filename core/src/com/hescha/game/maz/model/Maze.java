package com.hescha.game.maz.model;


import static com.hescha.game.maz.screen.GameScreen.TEXTURE_SIZE;
import static com.hescha.game.maz.screen.GameScreen.wallTexture;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Maze {

    private final String[][] mazePath;

    public Maze(String[][] mazePath) {
        this.mazePath = mazePath;
    }

    public boolean isWall(int x, int y) {
        int topLeftI = y / TEXTURE_SIZE;
        int topLeftJ = x / TEXTURE_SIZE;

        int topRightI = y / TEXTURE_SIZE;
        int topRightJ = (x + TEXTURE_SIZE - 1) / TEXTURE_SIZE;

        int bottomLeftI = (y + TEXTURE_SIZE - 1) / TEXTURE_SIZE;
        int bottomLeftJ = x / TEXTURE_SIZE;

        int bottomRightI = (y + TEXTURE_SIZE - 1) / TEXTURE_SIZE;
        int bottomRightJ = (x + TEXTURE_SIZE - 1) / TEXTURE_SIZE;

        if (outOfBounds(topLeftI, topLeftJ) || outOfBounds(topRightI, topRightJ) ||
                outOfBounds(bottomLeftI, bottomLeftJ) || outOfBounds(bottomRightI, bottomRightJ)) {
            return true;
        }

        return !isValidSpace(topLeftI, topLeftJ) || !isValidSpace(topRightI, topRightJ) ||
                !isValidSpace(bottomLeftI, bottomLeftJ) || !isValidSpace(bottomRightI, bottomRightJ);
    }

    private boolean outOfBounds(int i, int j) {
        return i < 0 || i >= mazePath.length || j < 0 || j >= mazePath[0].length;
    }

    private boolean isValidSpace(int i, int j) {
        return mazePath[i][j].equals("#") || mazePath[i][j].equals(" ");
    }


    public int getStartX() {
        return 1 * TEXTURE_SIZE;
    }

    public int getStartY() {
        return( mazePath.length-1) * TEXTURE_SIZE;
    }

    public void draw(SpriteBatch batch) {
        for (int i = 0; i < mazePath.length; i++) {
            for (int j = 0; j < mazePath[i].length; j++) {
                if (isWall(j * TEXTURE_SIZE, i * TEXTURE_SIZE)) {
                    batch.draw(wallTexture, j * TEXTURE_SIZE, i * TEXTURE_SIZE, TEXTURE_SIZE, TEXTURE_SIZE);
                }
            }
        }
    }
}
