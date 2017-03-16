package me.winter.newz.objects;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.utils.Array;
import me.winter.newz.World;
import me.winter.newz.physics.Limit;

/**
 * <p>A 3D world object</p>
 *
 * <p>Created by 1541869 on 2016-05-16.</p>
 */
public abstract class WorldObject
{
	private ModelInstance modelInstance;

	private World world;
	private Array<Limit> limits;
	private BoundingBox boundingBox;

	private Vector3 tmpDimensions = new Vector3(), tmpPosition = new Vector3();

	public WorldObject(World world, Model model, Vector3 position)
	{
		this.modelInstance = new ModelInstance(model, position);
		this.world = world;

		this.limits = new Array<>();
		boundingBox = new BoundingBox();
		modelInstance.calculateBoundingBox(boundingBox);
	}

	public abstract void tick(float delta);

	/**
	 * @return true if other world object should enter in collision with it
	 */
	public abstract boolean isSolid();

	public Array<Limit> getLimits()
	{
		return limits;
	}

	public BoundingBox getBoundingBox()
	{
		return boundingBox;
	}

	public Vector3 getDimensions()
	{
		return boundingBox.getDimensions(tmpDimensions);
	}

	public Vector3 getPosition()
	{
		return modelInstance.transform.getTranslation(tmpPosition);
	}

	public World getWorld()
	{
		return world;
	}

	public ModelInstance getModelInstance()
	{
		return modelInstance;
	}
}
