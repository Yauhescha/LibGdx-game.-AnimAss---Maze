package com.hescha.game.maz.model;


import static com.hescha.game.maz.screen.GameScreen.TEXTURE_SIZE;
import static com.hescha.game.maz.screen.GameScreen.playerTexture;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import lombok.Data;

@Data
public class Player {
    public static final float MOVE_INTERVAL = 0.01f;  // Игрок будет двигаться каждые 0.2 секунды
    public static final int PLAYER_TEXTURE_SIZE = TEXTURE_SIZE/2;
    public static int MOVE_SPEED = TEXTURE_SIZE / 10;
    private int x, y;
    private boolean isDragging = false;
    private boolean isFinished = false;
    private Direction currentDirectionX = null;
    private Direction currentDirectionY = null;
    private float moveTimer = 0;

    public Player(int x, int y) {
        this.x = x;
        this.y = y;
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
        if (maze.isExit(x, y)) {
            isFinished = true;
        }
    }

    public void resetDirection() {
        currentDirectionX = null;
        currentDirectionY = null;
    }
    public enum Direction {
        UP, DOWN, LEFT, RIGHT

    }

    public boolean move(Direction direction, Maze maze) {
        if (!isDragging || isFinished) return false;
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
//        if (maze.isWall(x + dx, y)) {
//            dx = (dx > 0) ? (TEXTURE_SIZE - (x + TEXTURE_SIZE) % TEXTURE_SIZE) : (-x % TEXTURE_SIZE);
//        }
//        if (maze.isWall(x, y + dy)) {
//            dy = (dy > 0) ? (TEXTURE_SIZE - (y + TEXTURE_SIZE) % TEXTURE_SIZE) : (-y % TEXTURE_SIZE);
//        }

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


    private boolean canMove(Direction direction, Maze maze) {
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

        return !maze.isWall(x + dx, y + dy);
    }

    public void startDragging() {
        isDragging = true;
    }

    public void stopDragging() {
        isDragging = false;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(playerTexture, x, y, PLAYER_TEXTURE_SIZE, PLAYER_TEXTURE_SIZE);
    }
}

