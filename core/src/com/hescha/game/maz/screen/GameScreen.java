package com.hescha.game.maz.screen;

import static com.hescha.game.maz.AnimAssMaz.BACKGROUND_COLOR;
import static com.hescha.game.maz.AnimAssMaz.WORLD_HEIGHT;
import static com.hescha.game.maz.AnimAssMaz.WORLD_WIDTH;
import static com.hescha.game.maz.screen.LoadingScreen.UI_WINDOWS_CARD_PNG;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.hescha.game.maz.AnimAssMaz;
import com.hescha.game.maz.model.Game;
import com.hescha.game.maz.model.Level;
import com.hescha.game.maz.util.FontUtil;


import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class GameScreen extends ScreenAdapter {
    private final Level level;
    private OrthographicCamera camera;
    private Viewport viewport;
    private SpriteBatch batch;
    private Stage stage;
    private BitmapFont font;

    public static Texture playerTexture;
    public static Texture wallTexture;
    private Texture background;
    private Texture card;

    public int mazeSize;
    private Game game;

    @Override
    public void show() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, WORLD_WIDTH, WORLD_HEIGHT);
        camera.position.set(WORLD_WIDTH / 2, WORLD_HEIGHT / 2, 0);
        camera.update();
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        viewport.apply(true);

        batch = new SpriteBatch();
        font = FontUtil.generateFont(Color.WHITE);
        playerTexture = new Texture(Gdx.files.internal("1.png"));
        wallTexture = new Texture(Gdx.files.internal("2.png"));

        Table table = new Table();
        table.setFillParent(true);
        stage = new Stage(viewport);
        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);
        card = AnimAssMaz.assetManager.get(UI_WINDOWS_CARD_PNG, Texture.class);

        mazeSize = level.getLevelType().getSize();
        game = level.getGame();
        background = new Texture(Gdx.files.internal(level.texturePath()));
    }

    @Override
    public void render(float delta) {
        update(delta);
        draw();
    }

    private void update(float delta) {
        game.update(delta);

        if (game.isFinished()) {
            //SAVE DATA
            AnimAssMaz.launcher.setScreen(new GalleryScreen(level));
        }
    }

    private void draw() {
        ScreenUtils.clear(BACKGROUND_COLOR);

        stage.draw();

        batch.setProjectionMatrix(camera.projection);
        batch.setTransformMatrix(camera.view);
        batch.begin();
        batch.draw(background, 0, 0, WORLD_WIDTH, WORLD_WIDTH);
        batch.draw(card, 0, WORLD_WIDTH, WORLD_WIDTH, WORLD_HEIGHT - WORLD_WIDTH);
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
