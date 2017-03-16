package me.winter.newz.physics;

import com.badlogic.gdx.math.Vector3;

/**
 * <p>Undocumented :(</p>
 *
 * <p>Created by Alexander Winter on 2016-10-30.</p>
 */
public class AxisLimit implements Limit
{
	private Axis axis;
	private boolean forward;

	private IntVector start, end;
	private IntVector tmpVector;

	public AxisLimit(Axis axis, boolean forward, Vector3 start, Vector3 end)
	{
		this(axis, forward, start.x, start.y, start.z, end.x, end.y, end.z);
	}

	public AxisLimit(Axis axis, boolean forward, float x1, float y1, float z1, float x2, float y2, float z2)
	{
		this(axis, forward, Math.round(x1), Math.round(y1), Math.round(z1), Math.round(x2), Math.round(y2), Math.round(z2));
	}

	public AxisLimit(Axis axis, boolean forward, int x1, int y1, int z1, int x2, int y2, int z2)
	{
		this.axis = axis;
		this.forward = forward;

		this.start = new IntVector(Math.min(x1, x2), Math.min(y1, y2), Math.min(z1, z2)); //so start has the lowest coordinates
		this.end = new IntVector(Math.max(x1, x2), Math.max(y1, y2), Math.max(z1, z2)); //so end has the biggest coordinates

		if(axis.of(start) != axis.of(end))
			throw new IllegalArgumentException("Vectors should have the same value for the specified axis");

		this.tmpVector = new IntVector();
	}

	@Override
	public boolean collides(IntVector vector, Limit limit)
	{
		if(!(limit instanceof AxisLimit))
			return false;

		AxisLimit that = (AxisLimit)limit;

		if(that.axis != axis || that.forward == forward || axis.of(vector) == 0)
			return false;

		//finding the collision point
		tmpVector.set(vector);
		tmpVector.scale(axisValue() - that.axisValue());
		tmpVector.divide(axis.of(vector));

		if(forward)
			return that.axisValue() + axis.of(vector) > this.axisValue()   //if destination is higher than limit
					&& that.axisValue() <= this.axisValue()                  //if position is lower than limit
					&& this.contains(that, tmpVector);                      //if at the collision point, it is within the bounds

		else
			return that.axisValue() + axis.of(vector) < this.axisValue()    //if destination is lower than limit
					&& that.axisValue() >= this.axisValue()                 //if position is higher than limit
					&& this.contains(that, tmpVector);                      //if at the collision point, it is within the bounds
	}

	private boolean contains(AxisLimit limit, IntVector offset)
	{
		if(this.axis != limit.axis)
			return false;

		for(Axis axis : Axis.values()) //for all axes
		{
			if(axis == this.axis) //but the one of this limit
				continue;

			if(axis.of(limit.end) + axis.of(offset) <= axis.of(start)
			|| axis.of(limit.start) + axis.of(offset) >= axis.of(end))
				return false;
		}

		return true;
	}

	@Override
	public float getPriority(IntVector vector, Limit limit)
	{
		if(!(limit instanceof AxisLimit))
			return -1;

		AxisLimit that = (AxisLimit)limit;

		if(that.axis != axis || that.forward == forward)
			return -1;

		return (axis.of(start) - axis.of(that.start)) / (float)axis.of(vector); //scale his allowed movement from this limit to his total movement on the axis (x / x is same as len / len)
	}

	@Override
	public boolean replace(IntVector vector, Limit limit)
	{
		if(!(limit instanceof AxisLimit))
			return false;

		AxisLimit that = (AxisLimit)limit;

		if(that.axis != axis || that.forward == forward)
			return false;

		if((forward && axis.of(vector) > 0) || (!forward && axis.of(vector) < 0)) //if it's forward and movement is positive or if it's not and negative
		{
			axis.set(vector, axisValue() - that.axisValue()); //replace to limit
			return true;
		}

		return false;
	}

	@Override
	public boolean isTouching(Limit limit)
	{
		if(!(limit instanceof AxisLimit))
			return false;

		AxisLimit that = (AxisLimit)limit;

		if(that.axis != axis || that.forward == forward)
			return false;

		return axis.of(this.start) == axis.of(that.start);
	}

	/**
	 * Leads to bug, disabled for now (wrong by design)
	 */
	@Deprecated
	public boolean canSubstitute(IntVector vector, Limit limit)
	{
		if(!(limit instanceof AxisLimit))
			return false;

		if(((AxisLimit)limit).axis != axis || ((AxisLimit)limit).forward != forward)
			return false;


		if(forward)
			return ((AxisLimit)limit).axisValue() >= this.axisValue();
		else
			return ((AxisLimit)limit).axisValue() <= this.axisValue();
	}

	@Override
	public boolean equals(Object object)
	{
		if(object.getClass() != getClass())
			return false;

		if(((AxisLimit)object).getAxis() != getAxis())
			return false;

		if(!((AxisLimit)object).getStart().equals(getStart()))
			return false;

		if(!((AxisLimit)object).getEnd().equals(getEnd()))
			return false;

		if(((AxisLimit)object).forward != forward)
			return false;

		return true;
	}

	public IntVector getStart()
	{
		return start;
	}

	public IntVector getEnd()
	{
		return end;
	}

	public Axis getAxis()
	{
		return axis;
	}

	/**
	 * Limits can block toward positive value or toward negative
	 * A limit that is forward will block from lowest value toward highest
	 * For example, a Y forward limit will block things coming from bottom
	 * @return if the limit is forward
	 */
	public boolean isForward()
	{
		return forward;
	}

	/**
	 * Gives the x, y, or z coordinate of the limit depending of the axis of this limit
	 * If this is a Y limit, this value corresponds to the height etc.
	 * @return the value for the axis
	 */
	private int axisValue()
	{
		return axis.of(start);
	}

	public static enum Axis
	{
		X {
			@Override
			public int of(IntVector vector3)
			{
				return vector3.getX();
			}

			@Override
			public void set(IntVector vector3, int value)
			{
				vector3.setX(value);
			}
		},

		Y {
			@Override
			public int of(IntVector vector3)
			{
				return vector3.getY();
			}

			@Override
			public void set(IntVector vector3, int value)
			{
				vector3.setY(value);
			}
		},

		Z {
			@Override
			public int of(IntVector vector3)
			{
				return vector3.getZ();
			}

			@Override
			public void set(IntVector vector3, int value)
			{
				vector3.setZ(value);
			}
		};

		public abstract int of(IntVector vector3);
		public abstract void set(IntVector vector3, int value);
	}
}
