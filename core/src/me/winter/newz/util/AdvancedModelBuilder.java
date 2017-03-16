package me.winter.newz.util;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.utils.Disposable;

/**
 * A class extending ModelBuilder to add extra methods
 * Created by 1541869 on 2016-11-14.
 */
public class AdvancedModelBuilder extends ModelBuilder
{
	public Model createBox(float width, float height, float depth, Material[] materials, long attributes)
	{
		return createBox(width, height, depth, GL20.GL_TRIANGLES, materials, attributes);
	}

	public Model createBox(float width, float height, float depth, int primitiveType, Material[] materials, long attributes)
	{
		/*begin();
		for(int x = -1; x < 0; x += 2)
			for(int y = -1; y < 0; y += 2)
				for(int z = -1; z < 0; z += 2)
				part("box", primitiveType, attributes, materials[0]).rect();*/
		return end();
	}
/*
	public Model createRect(float x00, float y00, float z00, float x10, float y10, float z10, float x11, float y11, float z11,
	                         float x01, float y01, float z01, float normalX, float normalY, float normalZ, final Material material, final long attributes) {
		return createRect(x00, y00, z00, x10, y10, z10, x11, y11, z11, x01, y01, z01, normalX, normalY, normalZ, GL20.GL_TRIANGLES,
				material, attributes);
	}*/
}
