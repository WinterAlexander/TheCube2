package me.winter.thecube2;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;

/**
 * <p>A model instance with a dimension and position</p>
 *
 * <p>Created by 1541869 on 2016-05-16.</p>
 */
public class GameObject extends ModelInstance
{
	private BoundingBox boundingBox;
	private Vector3 position, center, dimensions;

	public GameObject(Model model)
	{
		this(model, 0, 0, 0);
	}

	public GameObject(Model model, Vector3 vector3)
	{
		this(model, vector3.x, vector3.y, vector3.z);
	}

	public GameObject(Model model, float x, float y, float z)
	{
		super(model, x, y, z);
	}

	@Override
	public void calculateTransforms()
	{
		super.calculateTransforms();
		if(boundingBox == null)
		{
			boundingBox = new BoundingBox();
			position = new Vector3();
			dimensions = new Vector3();
			center = new Vector3();
		}

		calculateBoundingBox(boundingBox);
		boundingBox.getCenter(center);
		transform.getTranslation(position);
		position.add(center);
		boundingBox.getDimensions(dimensions);
	}

	public Vector3 getPosition()
	{
		return position;
	}

	public Vector3 getDimensions()
	{
		return dimensions;
	}

	public BoundingBox getBoundingBox()
	{
		return boundingBox;
	}
}
