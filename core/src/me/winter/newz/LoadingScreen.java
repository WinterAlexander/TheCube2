package me.winter.newz;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * <p>A simple loading screen</p>
 *
 * <p>Created by 1541869 on 2016-05-16.</p>
 */
public class LoadingScreen implements GameState
{
	private NewZ game;

	private SpriteBatch batch;
	private OrthographicCamera camera;
	private Texture texture;

	public LoadingScreen(NewZ game)
	{
		this.game = game;
	}

	@Override
	public void create()
	{
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		texture = new Texture("loading.png");
	}

	@Override
	public void render(float delta)
	{
		if(getGame().getAssets().update())
		{
			getGame().doneLoading();
			return;
		}

		Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		batch.setProjectionMatrix(camera.combined);
		batch.begin();

		batch.draw(texture,
				camera.viewportWidth / 2 - texture.getWidth() / 4,
				camera.viewportHeight / 2 - texture.getHeight() / 4,
				texture.getWidth() / 2,
				texture.getHeight() / 2);

		batch.end();
	}


	@Override
	public void dispose()
	{

	}

	public NewZ getGame()
	{
		return game;
	}
}
