package me.winter.newz;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.Model;

/**
 * <p>NewZ, a whole new game !</p>
 *
 * <p>Created by 1541869 on 2016-05-16.</p>
 */
public class NewZ extends Game
{
	private LoadingScreen loading;
	private MenuScreen menu;
	private WorldScreen world;

	private AssetManager assetManager;

	@Override
	public void create()
	{
		loading = new LoadingScreen(this);
		menu = new MenuScreen(this);
		world = new WorldScreen(this);

		assetManager = new AssetManager();
		assetManager.load("ship/ship.g3db", Model.class);
		assetManager.load("invaderscene.g3db", Model.class);
		assetManager.load("dirt_ground.jpg", Texture.class);
		assetManager.load("ground.jpg", Texture.class);
		assetManager.load("ground_block.jpg", Texture.class);
		assetManager.load("marioblock.png", Texture.class);
		assetManager.load("nidoking/nidoking.g3db", Model.class);
		assetManager.load("dirt_bag.g3db", Model.class);

		loading.create();

		setScreen(loading);
	}

	public void doneLoading()
	{
		setScreen(getWorldScreen());
		getMenu().create();
		getWorldScreen().create();
	}

	@Override
	public void resize(int width, int height)
	{
		super.resize(width, height);
	}

	public AssetManager getAssets()
	{
		return assetManager;
	}

	public WorldScreen getWorldScreen()
	{
		return world;
	}

	public MenuScreen getMenu()
	{
		return menu;
	}

	public LoadingScreen getLoading()
	{
		return loading;
	}
}
