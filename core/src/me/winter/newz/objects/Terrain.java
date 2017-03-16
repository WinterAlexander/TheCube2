package me.winter.newz.objects;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder.VertexInfo;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Pools;
import me.winter.newz.World;
import me.winter.newz.physics.AxisLimit;
import me.winter.newz.physics.AxisLimit.Axis;

import static com.badlogic.gdx.graphics.VertexAttributes.Usage.Normal;
import static com.badlogic.gdx.graphics.VertexAttributes.Usage.Position;
import static com.badlogic.gdx.graphics.VertexAttributes.Usage.TextureCoordinates;

/**
 *
 * Created by 1541869 on 2016-11-14.
 */
public class Terrain extends WorldObject
{
	public Terrain(World world, Material ground, Vector3 position, int width, int length, float tileSize)
	{
		super(world, makeTerrain(ground, width, length, tileSize), position);
		Vector3 start = Pools.obtain(Vector3.class);
		Vector3 end = Pools.obtain(Vector3.class);

		start.set(position).add(width * tileSize / -2f, 0f, length * tileSize / -2f).scl(1000);
		end.set(position).add(width * tileSize / 2f, 0f, length * tileSize / 2f).scl(1000);
		getLimits().add(new AxisLimit(Axis.Y, false, start, end));

		Pools.free(start);
		Pools.free(end);
	}

	@Override
	public void tick(float delta)
	{

	}

	@Override
	public boolean isSolid()
	{
		return true;
	}

	private static Model makeTerrain(Material ground, int width, int length, float tileSize)
	{
		ModelBuilder builder = new ModelBuilder();

		builder.begin();
		MeshPartBuilder meshPart = builder.part("top", GL20.GL_TRIANGLES, Position | Normal | TextureCoordinates, ground);

		for(int i = 0; i <= width; i++)
		{
			for(int j = 0; j <= length; j++)
			{
				float x = i - width * tileSize / 2f - tileSize / 2f;
				float y = j - length * tileSize / 2f - tileSize / 2f;


				VertexInfo info = Pools.obtain(VertexInfo.class);
				info.setPos(x, 0f, y);
				info.setUV(0f, 0f);
				meshPart.vertex(info);
				Pools.free(info);


				if(i < width && j < length)
					meshPart.index(meshPart.lastIndex(), (short)(i * (length + 1) + j + 1), (short)((i + 1) * (length + 1) + j));

				if(i > 0 && j > 0)
					meshPart.index(meshPart.lastIndex(), (short)(i * (length + 1) + j - 1), (short)((i - 1) * (length + 1) + j));

			}
		}

		return builder.end();
	}
}
