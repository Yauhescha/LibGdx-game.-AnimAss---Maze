package com.hescha.game.maz.screen;

import static com.hescha.game.maz.AnimAssMaz.BACKGROUND_COLOR;
import static com.hescha.game.maz.screen.LoadingScreen.UI_BUTTONS_BACK_USUAL_ENABLED_PNG;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.hescha.game.maz.AnimAssMaz;
import com.hescha.game.maz.MyFunctionalInterface;
import com.hescha.game.maz.model.Level;
import com.hescha.game.maz.model.LevelType;
import com.hescha.game.maz.util.FontUtil;

import java.util.List;
import java.util.stream.Collectors;

public class SelectCategoryScreen extends ScreenAdapter {
    public static SelectCategoryScreen screen;
    private final LevelType levelType;
    private Stage stage;
    private BitmapFont font;
    private Table innerTable;
    private Viewport viewport;
    private final boolean isGalleryMode;

    public SelectCategoryScreen(LevelType levelType, boolean isGalleryMode) {
        this.levelType = levelType;
        this.isGalleryMode = isGalleryMode;
    }

    @Override
    public void show() {
        screen = this;
        float worldWidth = Gdx.graphics.getWidth();
        float worldHeight = Gdx.graphics.getHeight();
        OrthographicCamera camera = new OrthographicCamera(worldWidth, worldHeight);
        camera.position.set(worldWidth / 2, worldHeight / 2, 0);
        camera.update();
        viewport = new FitViewport(worldWidth, worldHeight, camera);
        viewport.apply(true);

        Texture buttonTexture = AnimAssMaz.assetManager.get(UI_BUTTONS_BACK_USUAL_ENABLED_PNG, Texture.class);
        Texture headerTexture = AnimAssMaz.assetManager.get("ui/element (22).png", Texture.class);

        Table table = new Table();
        table.setFillParent(true);
        font = FontUtil.generateFont(Color.BLACK);
        innerTable = new Table();

        createButton(headerTexture, levelType.name().replace("_", " "), 50, null);
        createButton(buttonTexture, "BACK", 100, addAction(() -> AnimAssMaz.launcher.setScreen(SelectTypeScreen.screen)));

        List<Level> levels = MainMenuScreen.levels.stream()
                .filter(level -> levelType == level.getLevelType())
                .collect(Collectors.toList());
        List<String> categories = levels
                .stream()
                .map(Level::getCategory)
                .distinct()
                .collect(Collectors.toList());

        for (String category : categories) {
            createButton(buttonTexture, category, 0, addAction(() -> AnimAssMaz.launcher.setScreen(new SelectLevelScreen(levelType, category, levels, isGalleryMode))));
        }


        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.BLACK);
        Label emptyLabel1 = new Label("", labelStyle);
        innerTable.add(emptyLabel1).pad(200).row();

        ScrollPane scrollPane = new ScrollPane(innerTable);
        table.add(scrollPane);
        stage = new Stage(viewport);
        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);
    }

    private void createButton(Texture headerTexture, String CATEGORIES, int padBottom, EventListener listener) {
        TextureRegion headerCover = new TextureRegion(headerTexture);
        TextureRegionDrawable buttonDrawable0 = new TextureRegionDrawable(headerCover);
        ImageTextButton imageTextButton0 = new ImageTextButton(CATEGORIES, new ImageTextButton.ImageTextButtonStyle(buttonDrawable0, null, null, font));
        innerTable.add(imageTextButton0).center().padTop(10).padBottom(padBottom).row();
        if (listener != null) {
            imageTextButton0.addListener(listener);
        }
    }


    @Override
    public void render(float delta) {
        ScreenUtils.clear(BACKGROUND_COLOR);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void dispose() {
        font.dispose();
        stage.dispose();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    private ClickListener addAction(MyFunctionalInterface lambda) {
        return new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                lambda.perform();
            }
        };
    }
}
