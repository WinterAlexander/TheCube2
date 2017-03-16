package me.winter.newz;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g3d.utils.FirstPersonCameraController;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Pools;
import me.winter.thecube2.Galaxy3D;

/**
 * <p>A simple class to manager input and move the camera !</p>
 *
 * <p>Created by 1541869 on 2016-05-11.</p>
 */
public class WorldInput implements InputProcessor
{
	private WorldScreen game;

	private Vector3 mousePos;
	private float sensitivity;

	public WorldInput(WorldScreen game)
	{
		this.game = game;
		Gdx.input.setCursorCatched(true);
		this.mousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);

		this.sensitivity = 0.25f;
	}

	@Override
	public boolean keyDown(int keycode)
	{
		if(keycode == Input.Keys.C)
			game.makeBlock(game.getCamera().position);

		if(keycode == Keys.ESCAPE)
			Gdx.input.setCursorCatched(!Gdx.input.isCursorCatched());

		if(keycode == Keys.F12)
			game.setDebug(!game.isDebug());

		return false;
	}

	@Override
	public boolean keyUp(int keycode)
	{
		return false;
	}

	@Override
	public boolean keyTyped(char character)
	{
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button)
	{
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button)
	{
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer)
	{
		return mouseMoved(screenX, screenY);
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY)
	{
		float deltaX = (mousePos.x - screenX) * sensitivity;
		float deltaY = (mousePos.y - screenY) * -sensitivity;

		Vector3 tmpAxis = Pools.obtain(Vector3.class);

		game.getCamera().rotate(Vector3.Y, deltaX);
		tmpAxis.set(game.getCamera().direction.z, 0, -game.getCamera().direction.x);


		float maxDeltaY = (float)Math.toDegrees(Math.acos(game.getCamera().up.dot(game.getCamera().up.x, 0.2f, game.getCamera().up.z)));


		if(deltaY > 0)
			deltaY = Math.min(deltaY, maxDeltaY);
		else
			deltaY = Math.max(deltaY, -maxDeltaY);

		game.getCamera().rotate(tmpAxis, deltaY);

		//if(game.getCamera().up.y < 0)
		//{
		//	game.getCamera().up.y = 0;
		//	game.getCamera().lookAt(game.getCamera().position.x, game.getCamera().position.y - 2f, game.getCamera().position.z);
		//}

		mousePos.set(screenX, screenY, 0);
		Pools.free(tmpAxis);
		return true;
	}

	@Override
	public boolean scrolled(int amount)
	{
		return false;
	}
}
