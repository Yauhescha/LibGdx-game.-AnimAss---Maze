package com.hescha.game.maz.screen;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.hescha.game.maz.AnimAssMaz;

public class LoadingScreen extends ScreenAdapter {
    private static final float WORLD_WIDTH = 960;
    private static final float WORLD_HEIGHT = 544;
    private static final float PROGRESS_BAR_WIDTH = 100;
    private static final float PROGRESS_BAR_HEIGHT = 25;
    public static final String UI_BUTTONS_BACK_USUAL_ENABLED_PNG = "ui/buttons/usual/enabled.png";
    public static final String UI_BUTTONS_BACK_USUAL_HOVERED_PNG = "ui/buttons/usual/hovered.png";
    public static final String UI_BUTTONS_BACK_USUAL_PRESSED_PNG = "ui/buttons/usual/pressed.png";
    public static final String UI_BUTTONS_BACK_USUAL_DISABLED_PNG = "ui/buttons/usual/disabled.png";
    public static final String UI_WINDOWS_FULL_PNG = "ui/windows/full.png";
    public static final String UI_WINDOWS_CARD_PNG = "ui/windows/card.png";

    private ShapeRenderer shapeRenderer;
    private Viewport viewport;
    private OrthographicCamera camera;

    private float progress = 0;


    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void show() {
        camera = new OrthographicCamera();
        camera.position.set(WORLD_WIDTH / 2, WORLD_HEIGHT / 2, 0);
        camera.update();
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        shapeRenderer = new ShapeRenderer();

        AssetManager assetManager = AnimAssMaz.assetManager;
        assetManager.load("ui/girl.png", Texture.class);

        assetManager.load(UI_BUTTONS_BACK_USUAL_DISABLED_PNG, Texture.class);
        assetManager.load(UI_BUTTONS_BACK_USUAL_ENABLED_PNG, Texture.class);
        assetManager.load(UI_BUTTONS_BACK_USUAL_HOVERED_PNG, Texture.class);
        assetManager.load(UI_BUTTONS_BACK_USUAL_PRESSED_PNG, Texture.class);

        assetManager.load(UI_WINDOWS_FULL_PNG, Texture.class);
        assetManager.load(UI_WINDOWS_CARD_PNG, Texture.class);


        assetManager.load("ui/element (22).png", Texture.class);
    }

    @Override
    public void render(float delta) {
        update();
        clearScreen();
        draw();
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }

    private void update() {
        if (AnimAssMaz.assetManager.update()) {
            AnimAssMaz.launcher.setScreen(new MainMenuScreen());
        } else {
            progress = AnimAssMaz.assetManager.getProgress();
        }
    }

    private void clearScreen() {
        ScreenUtils.clear(Color.BLACK);
    }

    private void draw() {
        shapeRenderer.setProjectionMatrix(camera.projection);
        shapeRenderer.setTransformMatrix(camera.view);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(
                (WORLD_WIDTH - PROGRESS_BAR_WIDTH) / 2, WORLD_HEIGHT / 2 -
                        PROGRESS_BAR_HEIGHT / 2,
                progress * PROGRESS_BAR_WIDTH, PROGRESS_BAR_HEIGHT);
        shapeRenderer.end();
    }
}
