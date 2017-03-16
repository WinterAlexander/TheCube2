package me.winter.newz;

/**
 * <p>An object to be rendered by the screen</p>
 *
 * <p>Created by Alexander Winter on 2016-11-01.</p>
 */
public interface RenderObject //TODO to rename
{
	void render(WorldScreen screen);
	float getPriority();
}
