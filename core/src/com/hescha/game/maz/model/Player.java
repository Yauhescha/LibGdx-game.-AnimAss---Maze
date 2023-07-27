package com.hescha.game.maz.model;


import static com.hescha.game.maz.screen.GameScreen.TEXTURE_SIZE;
import static com.hescha.game.maz.screen.GameScreen.playerTexture;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Player {
    private int x, y;
    private boolean isDragging = false;
    private Direction currentDirectionX = null;
    private Direction currentDirectionY = null;
    private float moveTimer = 0;
    private static final float MOVE_INTERVAL = 0.01f;  // Игрок будет двигаться каждые 0.2 секунды
    private static int MOVE_SPEED = 20;

    public Player(int x, int y) {
        this.x = x;
        this.y = y;
        MOVE_SPEED = TEXTURE_SIZE / 6;
    }

    public void resetDirection() {
        currentDirectionX = null;
        currentDirectionY = null;
    }

    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    public boolean move(Direction direction, Maze maze) {
        if(!isDragging) return false;
        int dx = 0;
        int dy = 0;

        switch (direction) {
            case UP:
                dy = MOVE_SPEED;
                break;
            case DOWN:
                dy = -MOVE_SPEED;
                break;
            case LEFT:
                dx = -MOVE_SPEED;
                break;
            case RIGHT:
                dx = MOVE_SPEED;
                break;
        }

        // Если следующий шаг вызовет столкновение со стеной, скорректировать смещение
        if (maze.isWall(x + dx, y)) {
            dx = (dx > 0) ? (TEXTURE_SIZE - (x + TEXTURE_SIZE) % TEXTURE_SIZE) : (-x % TEXTURE_SIZE);
        }
        if (maze.isWall(x, y + dy)) {
            dy = (dy > 0) ? (TEXTURE_SIZE - (y + TEXTURE_SIZE) % TEXTURE_SIZE) : (-y % TEXTURE_SIZE);
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
        // Для оси X
        if (deltaX > 0 && canMove(Direction.RIGHT, maze)) currentDirectionX = Direction.RIGHT;
        else if (deltaX < 0 && canMove(Direction.LEFT, maze)) currentDirectionX = Direction.LEFT;

        // Для оси Y
        if (deltaY > 0 && canMove(Direction.UP, maze)) currentDirectionY = Direction.UP;
        else if (deltaY < 0 && canMove(Direction.DOWN, maze)) currentDirectionY = Direction.DOWN;
    }

    public void update(float deltaTime, Maze maze) {
        moveTimer += deltaTime;
        if (moveTimer >= MOVE_INTERVAL) {
            if (currentDirectionX != null) {
                move(currentDirectionX, maze);
            }
            if (currentDirectionY != null) {
                move(currentDirectionY, maze);
            }
            moveTimer = 0;
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

