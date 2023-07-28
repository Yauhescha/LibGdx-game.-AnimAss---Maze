package com.hescha.game.maz.model;


import static com.hescha.game.maz.screen.GameScreen.playerTexture;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import lombok.Data;

@Data
public class Player {
    public final float MOVE_INTERVAL = 0.01f;  // Игрок будет двигаться каждые 0.2 секунды
    public int playerTextureSize;
    public int moveSpeed ;
    private int x, y;
    private boolean isDragging = false;
    private boolean isFinished = false;
    private Direction currentDirectionX = null;
    private Direction currentDirectionY = null;
    private float moveTimer = 0;

    public Player(int x, int y, int playerTextureSize, int moveSpeed) {
        this.x = x;
        this.y = y;
        this.playerTextureSize=playerTextureSize;
        this.moveSpeed=moveSpeed;
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
                dy = moveSpeed;
                break;
            case DOWN:
                dy = -moveSpeed;
                break;
            case LEFT:
                dx = -moveSpeed;
                break;
            case RIGHT:
                dx = moveSpeed;
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
                dy = moveSpeed;
                break;
            case DOWN:
                dy = -moveSpeed;
                break;
            case LEFT:
                dx = -moveSpeed;
                break;
            case RIGHT:
                dx = moveSpeed;
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
        batch.draw(playerTexture, x, y, playerTextureSize, playerTextureSize);
    }
}

