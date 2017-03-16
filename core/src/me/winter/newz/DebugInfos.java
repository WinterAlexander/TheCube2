package me.winter.newz;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import me.winter.newz.objects.Player;

/**
 *
 * Created by 1541869 on 2016-11-04.
 */
public class DebugInfos extends Actor
{
	private BitmapFont font = new BitmapFont();

	private Player player;

	public DebugInfos(Player player)
	{
		this.player = player;
	}

	@Override
	public void draw(Batch batch, float parentAlpha)
	{
		if(!player.getWorld().getScreen().isDebug())
			return;

		font.draw(batch, "pos " + player.getPosition(), 0, 20);
		font.draw(batch, "vel " + player.getVelocity(), 0, 40);
		font.draw(batch, "vel len " + player.getVelocity().len(), 0, 60);
		font.draw(batch, "cam " + roundToString(player.getWorld().getScreen().getCamera().direction), 0, 80);
		font.draw(batch, "cam up " + roundToString(player.getWorld().getScreen().getCamera().up), 0, 100);
		font.draw(batch, "fps " + Gdx.app.getGraphics().getFramesPerSecond(), 0, 120);
	}

	private String roundToString(Vector3 vec3)
	{
		return (Math.round(vec3.x * 1000) / 1000f) + ", " + (Math.round(vec3.y * 1000) / 1000f) + ", " + (Math.round(vec3.z * 1000) / 1000f);
	}
}
