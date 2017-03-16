package me.winter.newz.physics;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

/**
 * <p>Third version of my physic engine</p>
 *
 * <p>Created by Alexander Winter on 2016-11-27.</p>
 */
public class LimitV3 implements Limit
{
	private final IntVector position; //of the center
	private final Vector3 direction;
	private final IntVector size;

	public LimitV3(IntVector position, Vector3 direction, IntVector size)
	{
		this.position = position;
		this.direction = direction;
		this.size = size;
	}

	@Override
	public boolean collides(IntVector movementVector, Limit rawLimit)
	{
		LimitV3 limit = (LimitV3)rawLimit;

		if(direction.dot(limit.direction) != -1)
			return false;



		return true;
	}

	@Override
	public float getPriority(IntVector movementVector, Limit limit)
	{
		return 0;
	}

	@Override
	public boolean replace(IntVector movementVector, Limit limit)
	{
		return false;
	}

	@Override
	public boolean isTouching(Limit limit)
	{
		return false;
	}

	public IntVector getPosition()
	{
		return position;
	}

	public Vector3 getDirection()
	{
		return direction;
	}

	public IntVector getSize()
	{
		return size;
	}
}
