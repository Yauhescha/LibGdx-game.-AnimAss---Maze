package com.hescha.game.maz.model;

import static com.hescha.game.maz.screen.AnimAssMaz.TEXTURE_SIZE;
import static com.hescha.game.maz.screen.AnimAssMaz.playerTexture;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Player {
    private int x, y;
    // Загрузите изображение игрока, например:
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

    public void moveTo(float targetX, float targetY, Maze maze) {
        // Определите направление движения игрока относительно касания экрана
        // Попробуйте передвинуть игрока в этом направлении, пока он не столкнется со стеной лабиринта или не достигнет цели

        Direction direction;

        float dx = targetX - x;
        float dy = targetY - y;

        if (Math.abs(dx) > Math.abs(dy)) {
            direction = dx > 0 ? Direction.RIGHT : Direction.LEFT;
        } else {
            direction = dy > 0 ? Direction.UP : Direction.DOWN;
        }

        while (true) {
            if (!move(direction, maze)) {
                break; // Если игрок не может двигаться в этом направлении из-за стены, прервите цикл
            }

            if ((direction == Direction.RIGHT && x >= targetX) ||
                    (direction == Direction.LEFT && x <= targetX) ||
                    (direction == Direction.UP && y >= targetY) ||
                    (direction == Direction.DOWN && y <= targetY)) {
                break; // Если игрок достиг цели, прервите цикл
            }
        }
    }


    public void draw(SpriteBatch batch) {
         batch.draw(playerTexture, x, y, TEXTURE_SIZE, TEXTURE_SIZE);
    }
}

