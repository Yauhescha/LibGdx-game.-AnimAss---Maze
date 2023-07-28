package com.hescha.game.maz.model;


import static com.hescha.game.maz.screen.GameScreen.wallTexture;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Maze {
    private final int textureSize;
    private final int playerTextureSize;
    private final String[][] mazePath;

    public Maze(String[][] mazePath, int textureSize, int playerTextureSize) {
        this.mazePath = mazePath;
        this.textureSize = textureSize;
        this.playerTextureSize = playerTextureSize;
    }

    public boolean isWall(int x, int y) {
        int topLeftI = y / textureSize;
        int topLeftJ = x / textureSize;

        int topRightI = y / textureSize;
        int topRightJ = (x + playerTextureSize - 1) / textureSize;

        int bottomLeftI = (y + playerTextureSize - 1) / textureSize;
        int bottomLeftJ = x / textureSize;

        int bottomRightI = (y + playerTextureSize - 1) / textureSize;
        int bottomRightJ = (x + playerTextureSize - 1) / textureSize;

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
        return textureSize;
    }

    public int getStartY() {
        return (mazePath.length - 1) * textureSize;
    }

    public void draw(SpriteBatch batch) {
        for (int i = 0; i < mazePath.length; i++) {
            for (int j = 0; j < mazePath[i].length; j++) {
                if (isWall(j * textureSize, i * textureSize)) {
                    batch.draw(wallTexture, j * textureSize, i * textureSize, textureSize, textureSize);
                }
            }
        }
    }

    public boolean isExit(int x, int y) {
        int i = y / textureSize;
        int j = x / textureSize;

        if (i < 0 || i >= mazePath.length || j < 0 || j >= mazePath[0].length) {
            return false;
        }

        return i == 0 && j == mazePath.length - 2 && y < textureSize / 4;
    }

}
