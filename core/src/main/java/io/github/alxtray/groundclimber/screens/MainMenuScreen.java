package io.github.alxtray.groundclimber.screens;

import com.badlogic.gdx.graphics.Texture;
import io.github.alxtray.groundclimber.Core;
import io.github.alxtray.groundclimber.enums.DebugRenderMode;
import io.github.alxtray.groundclimber.enums.GameMode;
import io.github.alxtray.groundclimber.enums.LogLevel;
import io.github.alxtray.groundclimber.utilities.AssetLibrary;
import io.github.alxtray.groundclimber.utilities.Logger;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class MainMenuScreen implements Screen {

    private final Core game;

    private final SpriteBatch batch;
    private final BitmapFont font;
    private final OrthographicCamera camera;
    private final Texture backgroundImage;
    private DebugRenderMode debugMode = DebugRenderMode.NORMAL;

    public MainMenuScreen(final Core game) {
        this.game = game;

        batch = new SpriteBatch();
        font = new BitmapFont();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        backgroundImage = AssetLibrary.getInstance().getAsset("title_background", Texture.class);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.3f, 0.3f, 0.46f, 1);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(
            backgroundImage,
            camera.position.x - (camera.viewportWidth / 2),
            camera.position.y - (camera.viewportHeight / 2),
            camera.viewportWidth,
            camera.viewportHeight);
        font.draw(batch, "Welcome to Ground Climber! ", 100, 150);
        font.draw(batch, "Press Enter to begin, or Backspace for Endless mode!\n" +
                "Press F2 to enable debug rendering, or F3 for only debug rendering\n" +
                "F1 will reset the above options", 100, 100);
        batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            Logger.log(
                    "MainScreen",
                    "Changing to LevelScreen",
                    LogLevel.DEBUG);
            game.setScreen(new LevelSelectScreen(game, debugMode));
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.BACKSPACE)) {
            Logger.log(
                    "MainScreen",
                    "Changing to GameScreen",
                    LogLevel.DEBUG);
            game.setScreen(new GameScreen(GameMode.ENDLESS, debugMode));
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.F1)) {
            debugMode = DebugRenderMode.NORMAL;
            Logger.log(
                    "MainScreen",
                    "Updated render debug mode to: NORMAL",
                    LogLevel.INFO);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.F2)) {
            debugMode = DebugRenderMode.OVERLAY;
            Logger.log(
                    "MainScreen",
                    "Updated render debug mode to: OVERLAY",
                    LogLevel.INFO);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.F3)) {
            debugMode = DebugRenderMode.ONLY;
            Logger.log(
                    "MainScreen",
                    "Updated render debug mode to: ONLY",
                    LogLevel.INFO);
        }
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
                "MainScreen",
                "Disposed objects",
                LogLevel.DEBUG);
    }

}
