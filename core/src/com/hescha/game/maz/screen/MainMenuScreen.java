package com.hescha.game.maz.screen;


import static com.hescha.game.maz.AnimAssMaz.BACKGROUND_COLOR;
import static com.hescha.game.maz.AnimAssMaz.WORLD_HEIGHT;
import static com.hescha.game.maz.AnimAssMaz.WORLD_WIDTH;
import static com.hescha.game.maz.screen.LoadingScreen.UI_BUTTONS_BACK_USUAL_ENABLED_PNG;
import static com.hescha.game.maz.screen.LoadingScreen.UI_BUTTONS_BACK_USUAL_HOVERED_PNG;
import static com.hescha.game.maz.screen.LoadingScreen.UI_WINDOWS_FULL_PNG;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.hescha.game.maz.AnimAssMaz;
import com.hescha.game.maz.util.FontUtil;


public class MainMenuScreen extends ScreenAdapter {
    public static MainMenuScreen screen;
    private Stage stage;
    private BitmapFont font;
    private Viewport viewport;
    private Texture background;
    private SpriteBatch batch;

    @Override
    public void show() {
        screen = this;
        OrthographicCamera camera = new OrthographicCamera(WORLD_WIDTH, WORLD_HEIGHT);
        camera.position.set(WORLD_WIDTH / 2, WORLD_HEIGHT / 2, 0);
        camera.update();
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        viewport.apply(true);
        font = FontUtil.generateFont(Color.BLACK);
        batch = new SpriteBatch();


        Table table = new Table();
        table.setFillParent(true);
        stage = new Stage(viewport);
        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);

        Texture buttonEnabledTexture = AnimAssMaz.assetManager.get(UI_BUTTONS_BACK_USUAL_ENABLED_PNG, Texture.class);
        Texture buttonHoveredTexture = AnimAssMaz.assetManager.get(UI_BUTTONS_BACK_USUAL_HOVERED_PNG, Texture.class);
        Texture elementTexture = AnimAssMaz.assetManager.get("ui/element (22).png", Texture.class);
        background = AnimAssMaz.assetManager.get("ui/girl.png", Texture.class);


        TextureRegion title = new TextureRegion(elementTexture);
        TextureRegionDrawable buttonDrawable = new TextureRegionDrawable(title);
        TextureRegionDrawable buttonDrawable1 = new TextureRegionDrawable(buttonEnabledTexture);
        TextureRegionDrawable buttonDrawable2 = new TextureRegionDrawable(buttonHoveredTexture);
        ImageTextButton imageTextButton = new ImageTextButton("Maze GAME", new ImageTextButton.ImageTextButtonStyle(buttonDrawable, null, null, font));
        table.add(imageTextButton).center().padBottom(WORLD_WIDTH/2).row();

        ImageTextButton imageTextButton1 = new ImageTextButton("PLAY", new ImageTextButton.ImageTextButtonStyle(buttonDrawable1, buttonDrawable2, null, font));
        table.add(imageTextButton1).center().padTop(10).padBottom(10).row();
        imageTextButton1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                AnimAssMaz.launcher.setScreen(new SelectTypeScreen(false));
                dispose();
            }
        });

        ImageTextButton imageTextButton2 = new ImageTextButton("GALLERY", new ImageTextButton.ImageTextButtonStyle(buttonDrawable1, buttonDrawable2, null, font));
        table.add(imageTextButton2).center().padTop(10).padBottom(10).row();
        imageTextButton2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                AnimAssMaz.launcher.setScreen(new SelectTypeScreen(true));
            }
        });

        ImageTextButton imageTextButton3 = new ImageTextButton("EXIT", new ImageTextButton.ImageTextButtonStyle(buttonDrawable1, buttonDrawable2, null, font));
        table.add(imageTextButton3).center().padTop(10).padBottom(10).row();
        imageTextButton3.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(BACKGROUND_COLOR);
        batch.begin();
        batch.draw(background, 0, WORLD_HEIGHT-WORLD_WIDTH, WORLD_WIDTH, WORLD_WIDTH);
        batch.end();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void dispose() {
        font.dispose();
        batch.dispose();
        stage.dispose();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }
}
