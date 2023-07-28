package com.hescha.game.maz.screen;

import static com.hescha.game.maz.AnimAssMaz.BACKGROUND_COLOR;
import static com.hescha.game.maz.AnimAssMaz.WORLD_HEIGHT;
import static com.hescha.game.maz.AnimAssMaz.WORLD_WIDTH;

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
import com.hescha.game.maz.model.Game;


import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class GameScreen extends ScreenAdapter {
    public static Texture playerTexture;
    public static Texture wallTexture;

    public final int mazeSize;

    private OrthographicCamera camera;
    private Viewport viewport;
    private SpriteBatch batch;
    private Game game;
    private Texture background;

    @Override
    public void show() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, WORLD_WIDTH, WORLD_HEIGHT);
        camera.position.set(WORLD_WIDTH / 2, WORLD_HEIGHT / 2, 0);
        camera.update();
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        viewport.apply(true);

        batch = new SpriteBatch();


        playerTexture = new Texture(Gdx.files.internal("1.png"));
        wallTexture = new Texture(Gdx.files.internal("2.png"));
//        background = AnimAssMaz.assetManager.get(UI_WINDOWS_FULL_PNG, Texture.class);
        background =  new Texture(Gdx.files.internal("back.jpg"));

        game = new Game(mazeSize);
    }

    @Override
    public void render(float delta) {
        update(delta);
        draw();
    }

    private void update(float delta) {
        game.update(delta);

        if(game.isFinished()){
            AnimAssMaz.launcher.setScreen(new GameScreen(mazeSize +1));
        }
    }

    private void draw() {
        ScreenUtils.clear(BACKGROUND_COLOR);
        batch.setProjectionMatrix(camera.projection);
        batch.setTransformMatrix(camera.view);
        batch.begin();
        batch.draw(background, 0, 0, WORLD_WIDTH, WORLD_WIDTH);
        game.draw(batch);
        batch.end();
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
