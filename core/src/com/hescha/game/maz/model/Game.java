package com.hescha.game.maz.model;

import static com.hescha.game.maz.AnimAssMaz.WORLD_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.hescha.game.maz.generator.MazeGenerator;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import lombok.Data;

@Data
public class Game {
    private Maze maze;
    private Player player;

    private int mazeSize;
    private int textureSize;
    private int playerTextureSize;

    float lastTouchX = 0;
    float lastTouchY = 0;


    public Game(int mazeSize) {
        this.mazeSize = mazeSize;

        textureSize = (int) (WORLD_WIDTH / (mazeSize * 2 + 1));
        playerTextureSize = (int) (textureSize /1.2);

        String[][] generate = createMaze();
        maze = new Maze(generate, textureSize, playerTextureSize);
        player = new Player(maze.getStartX(), maze.getStartY(), textureSize, playerTextureSize);
    }

    public void draw(SpriteBatch batch){
        maze.draw(batch);
        player.draw(batch);
    }
    public void update(float delta){
        handleTouch();
        player.update(delta, maze);
    }

    private String[][] createMaze() {
        String[][] generate = MazeGenerator.generate(mazeSize);
        List<String[]> list = Arrays.asList(generate);
        Collections.reverse(list);

        for (int i = 0; i < generate.length; i++) {
            generate[i] = list.get(i);
        }
        return generate;
    }


    private void handleTouch() {
        if (Gdx.input.isTouched()) {
            float touchX = Gdx.input.getX();
            float touchY = Gdx.graphics.getHeight() - Gdx.input.getY(); // инвертируем ось Y

            if (!player.isDragging()) {
                player.startDragging();
                lastTouchX = touchX;
                lastTouchY = touchY;
            } else {
                float deltaX = touchX - lastTouchX;
                float deltaY = touchY - lastTouchY;

                player.drag(deltaX, deltaY, maze);

                lastTouchX = touchX;
                lastTouchY = touchY;
            }
        } else if (player.isDragging()) {
            player.stopDragging();
        }

        if (!Gdx.input.isTouched() && player.isDragging()) {
            player.stopDragging();
            player.resetDirection();
        }
    }

    public boolean isFinished() {
        return player.isFinished();
    }
}
