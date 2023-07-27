package com.hescha.game.maz.model;


import static com.hescha.game.maz.screen.GameScreen.TEXTURE_SIZE;
import static com.hescha.game.maz.screen.GameScreen.playerTexture;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Player {
    private int x, y;
    private boolean isDragging = false;
    private Direction currentDirection = null;
    private float moveTimer = 0;
    private static final float MOVE_INTERVAL = 0.1f;  // Игрок будет двигаться каждые 0.2 секунды

    public Player(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void resetDirection() {
        currentDirection = null;
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

    public void drag(float deltaX, float deltaY, Maze maze) {
        if (Math.abs(deltaX) > Math.abs(deltaY)) {
            if (deltaX > 0 && canMove(Direction.RIGHT, maze)) currentDirection = Direction.RIGHT;
            else if (deltaX < 0 && canMove(Direction.LEFT, maze)) currentDirection = Direction.LEFT;
        } else {
            if (deltaY > 0 && canMove(Direction.UP, maze)) currentDirection = Direction.UP;
            else if (deltaY < 0 && canMove(Direction.DOWN, maze)) currentDirection = Direction.DOWN;
        }
    }

    public void update(float deltaTime, Maze maze) {
        if (currentDirection != null) {
            moveTimer += deltaTime;
            if (moveTimer >= MOVE_INTERVAL) {
                move(currentDirection, maze);
                moveTimer = 0;
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

