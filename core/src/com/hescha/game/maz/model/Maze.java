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
        int i = y / TEXTURE_SIZE;
        int j = x / TEXTURE_SIZE;

        if (i < 0 || i >= mazePath.length || j < 0 || j >= mazePath[0].length) {
            return true;
        }

        return !mazePath[i][j].equals("#") && !mazePath[i][j].equals(" ");
    }

    public int getStartX() {
        return 1 * TEXTURE_SIZE;
    }

    public int getStartY() {
        return mazePath.length * TEXTURE_SIZE;
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
