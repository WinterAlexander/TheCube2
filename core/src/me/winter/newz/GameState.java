package me.winter.newz;


import com.badlogic.gdx.Screen;

/**
 * <p>An extension to the original Screen with a create method and default for useless methods</p>
 *
 * <p>Created by 1541869 on 2016-05-16.</p>
 */
public interface GameState extends Screen
{
	void create();

	@Override
	default void resize(int width, int height) {}

	@Override
	default void show() {}

	@Override
	default void pause() {}

	@Override
	default void resume() {}

	@Override
	default void hide() {}
}
