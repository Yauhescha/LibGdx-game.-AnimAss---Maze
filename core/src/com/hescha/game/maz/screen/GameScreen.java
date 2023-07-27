package com.hescha.game.maz.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.hescha.game.maz.AnimAssMaz;
import com.hescha.game.maz.generator.MazeGenerator;
import com.hescha.game.maz.model.Maze;
import com.hescha.game.maz.model.Player;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GameScreen extends ScreenAdapter {
    public static int TEXTURE_SIZE = 100;
    public static int MAZE_SIZE = 20;

    public static Texture playerTexture;
    public static Texture wallTexture;

    private OrthographicCamera camera;
    private Viewport viewport;
    private SpriteBatch batch;
    private Player player;
    private Maze maze;

    float lastTouchX = 0;
    float lastTouchY = 0;

    @Override
    public void show() {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        TEXTURE_SIZE = (int) (w / (MAZE_SIZE * 2 + 1));

        camera = new OrthographicCamera();
        camera.setToOrtho(false, w, h);
        camera.position.set(w / 2, h / 2, 0);
        camera.update();
        viewport = new FitViewport(w, h, camera);
        viewport.apply(true);

        batch = new SpriteBatch();

        String[][] generate = createMaze();
        maze = new Maze(generate);
        player = new Player(maze.getStartX(), maze.getStartY());

        playerTexture = new Texture(Gdx.files.internal("1.png"));
        wallTexture = new Texture(Gdx.files.internal("2.png"));
    }

    private static String[][] createMaze() {
        String[][] generate = MazeGenerator.generate(MAZE_SIZE);
        List<String[]> list = Arrays.asList(generate);
        Collections.reverse(list);

        for (int i = 0; i < generate.length; i++) {
            generate[i] = list.get(i);
        }
        return generate;
    }

    @Override
    public void render(float delta) {
        handleTouch();
        player.update(delta, maze);

        ScreenUtils.clear(Color.WHITE);
        batch.setProjectionMatrix(camera.projection);
        batch.setTransformMatrix(camera.view);
        batch.begin();
        maze.draw(batch);
        player.draw(batch);
        batch.end();

        if(player.isFinished()){
            AnimAssMaz.launcher.setScreen(new GameScreen());
        }
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

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }


    @Override
    public void dispose() {
        batch.dispose();
    }
}
