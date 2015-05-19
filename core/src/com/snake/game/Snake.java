package com.snake.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.snake.game.screens.GameScreen;
import com.snake.game.screens.IntroScreen;

public class Snake extends Game {

    public SpriteBatch batch;

    @Override
    public void create() {
        batch = new SpriteBatch();
        setScreen(new IntroScreen(this));
    }

    public void startGame() {
        setScreen(new GameScreen(this));
    }

    @Override
    public void setScreen(Screen screen) {
        Gdx.graphics.setContinuousRendering(false);
        super.setScreen(screen);
        Gdx.graphics.setContinuousRendering(true);
    }
}
