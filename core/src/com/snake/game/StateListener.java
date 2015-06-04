package com.snake.game;

import com.snake.game.screens.GameScreen;

public interface StateListener {
    void onStateChange(GameScreen.GameState state);
}
