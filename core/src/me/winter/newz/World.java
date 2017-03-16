package me.winter.newz;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pools;
import me.winter.newz.objects.WorldObject;
import me.winter.newz.physics.AxisLimit;
import me.winter.newz.physics.AxisLimit.Axis;
import me.winter.newz.physics.IntVector;
import me.winter.newz.physics.Limit;
import me.winter.newz.physics.Limit.Collision;

/**
 *
 * Created by 1541869 on 2016-10-31.
 */
public class World
{
	private NewZ game;
	private Array<WorldObject> objects;

	private Model boxModel, playerModel;

	public World(NewZ game)
	{
		this.game = game;
		this.objects = new Array<>(500 * 500 + 50);

		ModelBuilder builder = new ModelBuilder();
		boxModel = builder.createBox(1f, 1f, 1f, new Material(TextureAttribute.createDiffuse(game.getAssets().get("ground_block.jpg", Texture.class))),
				VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates);

		playerModel = builder.createBox(0.5f, 0.5f, 0.5f, new Material(TextureAttribute.createDiffuse(game.getAssets().get("marioblock.png", Texture.class))),
				VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates);

	}

	public Array<WorldObject> getObjects()
	{
		return objects;
	}

	public boolean replace(final IntVector vector, WorldObject mover)
	{
		Array<Collision> collisions = Pools.obtain(Array.class);

		for(int objectIndex = 0; objectIndex < objects.size; objectIndex++)
		{
			WorldObject current = objects.get(objectIndex);
			if(current == mover || !current.isSolid())
				continue;

			for(Limit limit : current.getLimits())
				for(Limit boxLimit : mover.getLimits())
					if(limit.collides(vector, boxLimit))
					{
						Collision collision = Pools.obtain(Collision.class);
						collision.set(limit, boxLimit);
						collisions.add(collision);
					}
		}

		if(collisions.size == 0)
			return false;

		collisions.sort((c1, c2) -> Float.compare(c1.getPriority(vector), c2.getPriority(vector)));

		boolean atLeastOneReplace = false;

		for(Collision collision : collisions)
			if(collision.getLimitA().collides(vector, collision.getLimitB()))
				if(collision.getLimitA().replace(vector, collision.getLimitB()))
				{
					atLeastOneReplace = true;
					Pools.free(collision);
				}

		collisions.clear();
		Pools.free(collisions);
		return atLeastOneReplace;
	}

	public void limitsFromBoundingBox(Array<Limit> limitArray, WorldObject object)
	{
		Vector3 vecBuf = Pools.obtain(Vector3.class), vecBuf2 = Pools.obtain(Vector3.class);

		object.getBoundingBox().getCorner000(vecBuf).add(object.getPosition()).scl(1000);
		object.getBoundingBox().getCorner011(vecBuf2).add(object.getPosition()).scl(1000);
		limitArray.add(new AxisLimit(Axis.X, true, vecBuf, vecBuf2));

		object.getBoundingBox().getCorner100(vecBuf).add(object.getPosition()).scl(1000);
		object.getBoundingBox().getCorner111(vecBuf2).add(object.getPosition()).scl(1000);
		limitArray.add(new AxisLimit(Axis.X, false, vecBuf, vecBuf2));

		object.getBoundingBox().getCorner000(vecBuf).add(object.getPosition()).scl(1000);
		object.getBoundingBox().getCorner101(vecBuf2).add(object.getPosition()).scl(1000);
		limitArray.add(new AxisLimit(Axis.Y, true, vecBuf, vecBuf2));

		object.getBoundingBox().getCorner010(vecBuf).add(object.getPosition()).scl(1000);
		object.getBoundingBox().getCorner111(vecBuf2).add(object.getPosition()).scl(1000);
		limitArray.add(new AxisLimit(Axis.Y, false, vecBuf, vecBuf2));

		object.getBoundingBox().getCorner000(vecBuf).add(object.getPosition()).scl(1000);
		object.getBoundingBox().getCorner110(vecBuf2).add(object.getPosition()).scl(1000);
		limitArray.add(new AxisLimit(Axis.Z, true, vecBuf, vecBuf2));

		object.getBoundingBox().getCorner001(vecBuf).add(object.getPosition()).scl(1000);
		object.getBoundingBox().getCorner111(vecBuf2).add(object.getPosition()).scl(1000);
		limitArray.add(new AxisLimit(Axis.Z, false, vecBuf, vecBuf2));




		Pools.free(vecBuf);
		Pools.free(vecBuf2);

	}

	public Model getBoxModel()
	{
		return boxModel;
	}

	public NewZ getGame()
	{
		return game;
	}

	public WorldScreen getScreen()
	{
		return game.getWorldScreen();
	}

	public Model getPlayerModel()
	{
		return playerModel;
	}
}
