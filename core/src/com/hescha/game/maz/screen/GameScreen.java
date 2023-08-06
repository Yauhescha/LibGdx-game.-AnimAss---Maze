package com.hescha.game.maz.screen;

import static com.hescha.game.maz.AnimAssMaz.BACKGROUND_COLOR;
import static com.hescha.game.maz.AnimAssMaz.PREFERENCE_SAVING_PATH;
import static com.hescha.game.maz.AnimAssMaz.WORLD_HEIGHT;
import static com.hescha.game.maz.AnimAssMaz.WORLD_WIDTH;
import static com.hescha.game.maz.screen.LoadingScreen.UI_WINDOWS_CARD_PNG;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
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

    private String levelScoreSavingPath;
    private float elapsedTime;
    private float minTime;
    private GlyphLayout glyphLayout;

    @Override
    public void show() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, WORLD_WIDTH, WORLD_HEIGHT);
        camera.position.set(WORLD_WIDTH / 2, WORLD_HEIGHT / 2, 0);
        camera.update();
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        viewport.apply(true);

        batch = new SpriteBatch();
        font = FontUtil.generateFont(Color.BLACK);
        playerTexture = new Texture(Gdx.files.internal("1.png"));
        wallTexture = new Texture(Gdx.files.internal("2.png"));

        Table table = new Table();
        table.setFillParent(true);
        stage = new Stage(viewport);
        stage.addActor(table);
//        Gdx.input.setInputProcessor(stage);
        Gdx.input.setCatchBackKey(true);

        InputProcessor inputProcessor = new MyInputProcessor();

        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(inputProcessor);
        inputMultiplexer.addProcessor(stage);

        Gdx.input.setInputProcessor(inputMultiplexer);

        card = AnimAssMaz.assetManager.get(UI_WINDOWS_CARD_PNG, Texture.class);

        mazeSize = level.getLevelType().getSize();
        game = level.getGame();
        background = new Texture(Gdx.files.internal(level.texturePath()));


        glyphLayout = new GlyphLayout();
        int size = 50;
        do {
            font.dispose();
            font = FontUtil.generateFont(Color.BLACK, size);
            String s = "Difficulty: \n" + level.getLevelType().name().replace("_", " ") + "\n" +
                    "Category: \n" + level.getCategory() + "\n" +
                    "Seconds: \n" + (int) elapsedTime + "\n" +
                    "Seconds min: \n" + (int) minTime;
            glyphLayout.setText(font, s);
            size++;
        } while (glyphLayout.width < WORLD_WIDTH - 100);

        levelScoreSavingPath = level.getLevelType().name() + "-" + level.getCategory() + "-" + level.getName();
        Preferences prefs = Gdx.app.getPreferences(PREFERENCE_SAVING_PATH);
        minTime = prefs.getInteger(levelScoreSavingPath, 9999);
    }

    @Override
    public void render(float delta) {
        update(delta);
        draw();
    }

    private void update(float delta) {
        game.update(delta);

        if (game.isFinished()) {
            saveBestResult();
            AnimAssMaz.launcher.setScreen(new GalleryScreen(level));
        } else {
            elapsedTime += Gdx.graphics.getDeltaTime();
        }


        String s = "Difficulty: " + level.getLevelType().name().replace("_", " ") + "\n" +
                "Category: \n" + level.getCategory() + "\n" +
                "Seconds: " + (int) elapsedTime + "\n" +
                "Seconds min: " + (int) minTime;
        glyphLayout.setText(font, s);

    }

    private void draw() {
        ScreenUtils.clear(BACKGROUND_COLOR);

        stage.draw();

        batch.setProjectionMatrix(camera.projection);
        batch.setTransformMatrix(camera.view);
        batch.begin();
        batch.draw(background, 0, 0, WORLD_WIDTH, WORLD_WIDTH);
        batch.draw(card, 0, WORLD_WIDTH, WORLD_WIDTH, WORLD_HEIGHT - WORLD_WIDTH);

        font.draw(batch, glyphLayout, 50, WORLD_HEIGHT-50);
        game.draw(batch);
        batch.end();
    }


    private void saveBestResult() {
        minTime = elapsedTime;
        Preferences prefs = Gdx.app.getPreferences(PREFERENCE_SAVING_PATH);
        prefs.putInteger(levelScoreSavingPath, (int) minTime);
        prefs.flush();
        System.out.println("Saved");
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

class MyInputProcessor implements InputProcessor {
    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.BACK) {

            AnimAssMaz.launcher.setScreen(SelectLevelScreen.screen);
        }
        return false; // Возвращает false, если клавиша не была обработана
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    // Другие методы интерфейса InputProcessor
}
