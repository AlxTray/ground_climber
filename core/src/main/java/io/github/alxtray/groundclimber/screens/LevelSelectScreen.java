package io.github.alxtray.groundclimber.screens;

import io.github.alxtray.groundclimber.Core;
import io.github.alxtray.groundclimber.enums.DebugRenderMode;
import io.github.alxtray.groundclimber.enums.GameMode;
import io.github.alxtray.groundclimber.enums.LogLevel;
import io.github.alxtray.groundclimber.utilities.AssetLibrary;
import io.github.alxtray.groundclimber.utilities.Logger;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.ScreenUtils;
import text.formic.Stringf;

import java.util.Arrays;

public class LevelSelectScreen implements Screen {

    private final SpriteBatch batch;
    private final BitmapFont font;
    private final OrthographicCamera camera;
    private final Stage stage;

    public LevelSelectScreen(final Core game, final DebugRenderMode debugMode) {

        batch = new SpriteBatch();
        font = new BitmapFont();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        FileHandle[] levelFiles = Gdx.files.internal("levels").list();
        Logger.log(
                "LevelScreen",
                Stringf.format("Found the following level files: %s", Arrays.toString(levelFiles)),
                LogLevel.DEBUG);
        Skin skin = AssetLibrary.getInstance().getAsset("skin", Skin.class);
        float buttonXIncrement = 0;
        for (FileHandle levelFile : levelFiles) {
            JsonReader jsonReader = new JsonReader();
            JsonValue jsonData = jsonReader.parse(levelFile);

            TextButton levelButton = new TextButton(jsonData.get("data").get("name").asString(), skin);
            levelButton.setPosition(100 + buttonXIncrement, 110);
            levelButton.setName(levelFile.name());
            Logger.log(
                    "LevelScreen",
                    Stringf.format("Successfully created button for level: %s", levelFile.name()),
                    LogLevel.DEBUG);

            levelButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    Actor target = event.getTarget();
                    // If the text in the button is clicked the event is for the Label not
                    // TextButton
                    // So, if the event is Label the TextButton is the parent Actor
                    String levelName = (target instanceof Label) ? target.getParent().getName() : target.getName();
                    Logger.log(
                            "LevelSelectButton",
                            Stringf.format("Selected level: %s", levelName),
                            LogLevel.INFO);
                    game.setScreen(new GameScreen(
                            GameMode.NORMAL,
                            debugMode,
                            levelName));
                }
            });

            stage.addActor(levelButton);
            buttonXIncrement += levelButton.getWidth() + 10;
        }
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.3f, 0.3f, 0.46f, 1);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        stage.act(delta);
        stage.draw();

        batch.begin();
        font.draw(batch, "Select from the following levels.", 100, 150);
        batch.end();
    }

    @Override
    public void show() { // No logic needed
    }

    @Override
    public void resize(int i, int i1) { // No logic needed for resize currently
    }

    @Override
    public void pause() { // No logic needed
    }

    @Override
    public void resume() { // No logic needed
    }

    @Override
    public void hide() { // No logic needed
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        Logger.log(
                "LevelScreen",
                "Disposed objects",
                LogLevel.DEBUG);
    }

}