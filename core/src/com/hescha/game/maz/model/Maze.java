package com.hescha.game.maz.model;

import static com.hescha.game.maz.screen.AnimAssMaz.TEXTURE_SIZE;
import static com.hescha.game.maz.screen.AnimAssMaz.wallTexture;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Maze {

    private final String[][] mazePath;

    public Maze(String[][] mazePath) {
        this.mazePath = mazePath;
    }

    public boolean isWall(int x, int y) {
        int i = y / TEXTURE_SIZE;
        int j = x / TEXTURE_SIZE;
        return !mazePath[i][j].equals("#") && !mazePath[i][j].equals(" ");
    }

    public int getStartX() {
        return 1 * TEXTURE_SIZE; // начальное положение игрока по X
    }

    public int getStartY() {
        return mazePath.length * TEXTURE_SIZE; // начальное положение игрока по Y
    }

    public void draw(SpriteBatch batch) {
        for (int i = 0; i < mazePath.length; i++) {
            for (int j = 0; j < mazePath[i].length; j++) {
                if (isWall(j * TEXTURE_SIZE, i * TEXTURE_SIZE)) {
                    // Здесь можно рисовать ваш блок стены
                    batch.draw(wallTexture, j * TEXTURE_SIZE, i * TEXTURE_SIZE, TEXTURE_SIZE, TEXTURE_SIZE);
                }
            }
        }
    }
}
