package me.winter.newz.physics;

import com.badlogic.gdx.math.Vector3;

/**
 * <p>Mutable vector class for movements and positions</p>
 *
 * <p>Created by Alexander Winter on 2016-10-10 at 12:38.</p>
 */
public class IntVector
{
	private int x, y, z;

	public IntVector()
	{
		set(0, 0, 0);
	}

	public IntVector(int x, int y, int z)
	{
		set(x, y, z);
	}

	public IntVector(IntVector vec)
	{
		set(vec);
	}

	public IntVector(Vector3 gdxVec)
	{
		set(gdxVec);
	}

	public IntVector(Vector3 gdxVec, float scale)
	{
		set(gdxVec, scale);
	}

	public IntVector(IntVector loc1, IntVector loc2)
	{
		this.x = loc2.x - loc1.x;
		this.y = loc2.y - loc1.y;
		this.z = loc2.z - loc1.z;
	}

	@Override
	public String toString()
	{
		return "IntVector[x=" + this.x + ", y=" + this.y+ ", z=" + this.z + "]";
	}

	public double length()
	{
		return Math.sqrt(lengthSquared());
	}

	public int lengthSquared()
	{
		return x * x + y * y + z * z;
	}

	public IntVector add(int x, int y, int z)
	{
		this.x += x;
		this.y += y;
		this.z += z;
		return this;
	}

	public IntVector add(IntVector vector)
	{
		return add(vector.x, vector.y, vector.z);
	}

	public IntVector substract(int x, int y, int z)
	{
		this.x -= x;
		this.y -= y;
		this.z -= z;
		return this;
	}

	public IntVector substract(IntVector vector)
	{
		return substract(vector.x, vector.y, vector.z);
	}

	public IntVector scale(double scalar)
	{
		this.x *= scalar;
		this.y *= scalar;
		this.z *= scalar;
		return this;
	}

	public IntVector divide(double scalar)
	{
		this.x /= scalar;
		this.y /= scalar;
		this.z /= scalar;
		return this;
	}

	public IntVector normalize()
	{
		double length = length();

		this.x /= length;
		this.y /= length;
		this.z /= length;
		return this;
	}

	public float dot(final IntVector vector)
	{
		return x * vector.x + y * vector.y + z * vector.z;
	}

	/** Returns the dot product between this and the given vector.
	 * @param x The x-component of the other vector
	 * @param y The y-component of the other vector
	 * @param z The z-component of the other vector
	 * @return The dot product */
	public float dot(float x, float y, float z)
	{
		return this.x * x + this.y * y + this.z * z;
	}

	/** Sets this vector to the cross product between it and the other vector.
	 * @param vector The other vector
	 * @return This vector for chaining
	 */
	public IntVector cross(final IntVector vector)
	{
		return this.set(y * vector.z - z * vector.y, z * vector.x - x * vector.z, x * vector.y - y * vector.x);
	}

	/** Sets this vector to the cross product between it and the other vector.
	 * @param x The x-component of the other vector
	 * @param y The y-component of the other vector
	 * @param z The z-component of the other vector
	 * @return This vector for chaining
	 */
	public IntVector cross(float x, float y, float z)
	{
		return this.set(this.y * z - this.z * y, this.z * x - this.x * z, this.x * y - this.y * x);
	}

	public boolean isNull()
	{
		return (x | y | z) == 0;
	}

	public IntVector clone()
	{
		return new IntVector(this);
	}

	public IntVector copyTo(IntVector destination)
	{
		return destination.set(this);
	}

	public Vector3 copyTo(Vector3 destination)
	{
		return destination.set(x, y, z);
	}

	public Vector3 copyTo(Vector3 destination, float scale)
	{
		return destination.set(x * scale, y * scale, z * scale);
	}

	public IntVector floor(float x, float y, float z)
	{
		this.x = (int)x;
		this.y = (int)y;
		this.z = (int)z;
		return this;
	}

	public IntVector floor(Vector3 gdxVec, float scale)
	{
		return set((int)(gdxVec.x * scale), (int)(gdxVec.y * scale), (int)(gdxVec.z * scale));
	}

	public IntVector set(int x, int y, int z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		return this;
	}

	public IntVector set(float x, float y, float z)
	{
		this.x = Math.round(x);
		this.y = Math.round(y);
		this.z = Math.round(z);
		return this;
	}

	public IntVector set(IntVector vec)
	{
		return set(vec.x, vec.y, vec.z);
	}

	public IntVector set(Vector3 gdxVec)
	{
		return set(gdxVec, 1f);
	}

	public IntVector set(Vector3 gdxVec, float scale)
	{
		return set(Math.round(gdxVec.x * scale), Math.round(gdxVec.y * scale), Math.round(gdxVec.z * scale));
	}

	public int getX()
	{
		return x;
	}

	public IntVector setX(int x)
	{
		this.x = x;
		return this;
	}

	public int getY()
	{
		return y;
	}

	public IntVector setY(int y)
	{
		this.y = y;
		return this;
	}

	public int getZ()
	{
		return z;
	}

	public IntVector setZ(int z)
	{
		this.z = z;
		return this;
	}
}
