package com.hescha.game.maz.model;


import static com.hescha.game.maz.screen.GameScreen.TEXTURE_SIZE;
import static com.hescha.game.maz.screen.GameScreen.playerTexture;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Player {
    private int x, y;
    private boolean isDragging = false;
    // private Texture playerTexture = new Texture("path_to_player_image.png");

    public Player(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    public boolean move(Direction direction, Maze maze) {
        int dx = 0;
        int dy = 0;

        switch (direction) {
            case UP:
                dy = TEXTURE_SIZE;
                break;
            case DOWN:
                dy = -TEXTURE_SIZE;
                break;
            case LEFT:
                dx = -TEXTURE_SIZE;
                break;
            case RIGHT:
                dx = TEXTURE_SIZE;
                break;
        }

        if (!maze.isWall(x + dx, y + dy)) {
            x += dx;
            y += dy;
            return true;
        }

        return false;
    }
    public boolean isDragging() {
        return isDragging;
    }

    public void drag(float targetX, float targetY, Maze maze) {
        if (isDragging) {
            Direction direction = null;

            float dx = targetX - x;
            float dy = targetY - y;

            if (Math.abs(dx) > Math.abs(dy)) {
                if (dx > 0 && canMove(Direction.RIGHT, maze)) direction = Direction.RIGHT;
                if (dx < 0 && canMove(Direction.LEFT, maze)) direction = Direction.LEFT;
            } else {
                if (dy > 0 && canMove(Direction.UP, maze)) direction = Direction.UP;
                if (dy < 0 && canMove(Direction.DOWN, maze)) direction = Direction.DOWN;
            }

            if (direction != null) {
                move(direction, maze);
            }
        }
    }

    private boolean canMove(Direction direction, Maze maze) {
        int dx = 0;
        int dy = 0;

        switch (direction) {
            case UP:
                dy = TEXTURE_SIZE;
                break;
            case DOWN:
                dy = -TEXTURE_SIZE;
                break;
            case LEFT:
                dx = -TEXTURE_SIZE;
                break;
            case RIGHT:
                dx = TEXTURE_SIZE;
                break;
        }

        return !maze.isWall(x + dx, y + dy);
    }

    public void startDragging() {
        isDragging = true;
    }

    public void stopDragging() {
        isDragging = false;
    }
    
    public void draw(SpriteBatch batch) {
         batch.draw(playerTexture, x, y, TEXTURE_SIZE, TEXTURE_SIZE);
    }
}

