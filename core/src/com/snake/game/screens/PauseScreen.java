package com.snake.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.snake.game.Snake;
import com.snake.game.screens.BaseScreen;

public class PauseScreen extends BaseScreen {

    public PauseScreen(Snake game) {
        super(game);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
    }

    @Override
    public boolean keyDown(int keycode) {
        if (Input.Keys.ENTER == keycode) {
            game.startGame();
            return true;
        }
        return false;
    }
}
