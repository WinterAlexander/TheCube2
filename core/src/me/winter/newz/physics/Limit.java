package me.winter.newz.physics;


import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool.Poolable;

/**
 * Represents a physical limit that objects cannot cross
 * Example: a box edge
 */
public interface Limit
{
	/**
	 * Checks if the parameter limit while moving with it's vector creates a collision
	 * @param movementVector Movement vector
	 * @return true if there's collision
	 */
	boolean collides(IntVector movementVector, Limit limit);
	float getPriority(IntVector movementVector, Limit limit);
	boolean replace(IntVector movementVector, Limit limit);

	boolean isTouching(Limit limit);

	default float getPriority(IntVector vector, Array<Limit> limits)
	{
		float priority = -1;

		for(Limit limit : limits)
		{
			float newValue = getPriority(vector, limit);

			if(newValue > priority)
				priority = newValue;
		}

		return priority;
	}

	public static class Collision
	{
		private Limit limitA, limitB;

		public Collision()
		{

		}

		public Collision(Limit limitA, Limit limitB)
		{
			set(limitA, limitB);
		}

		public void set(Limit limitA, Limit limitB)
		{
			this.limitA = limitA;
			this.limitB = limitB;
		}

		public float getPriority(IntVector movement)
		{
			return limitA.getPriority(movement, limitB);
		}

		public Limit getLimitA()
		{
			return limitA;
		}

		public void setLimitA(Limit limitA)
		{
			this.limitA = limitA;
		}

		public Limit getLimitB()
		{
			return limitB;
		}

		public void setLimitB(Limit limitB)
		{
			this.limitB = limitB;
		}
	}
}
