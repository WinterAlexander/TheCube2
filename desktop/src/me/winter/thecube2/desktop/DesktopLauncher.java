package me.winter.thecube2.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.math.Vector3;
import me.winter.newz.NewZ;
import me.winter.thecube2.Galaxy3D;
import me.winter.thecube2.RayPickingTest;

public class DesktopLauncher
{
	public static void main (String[] arg)
	{
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1280;
		config.height = 720;
		config.title = "NewZ";
		config.vSyncEnabled = true;
		config.foregroundFPS = 60;

		//new LwjglApplication(new Galaxy3D(), config);
		//new LwjglApplication(new RayPickingTest(), config);
		new LwjglApplication(new NewZ(), config);
	}
}
