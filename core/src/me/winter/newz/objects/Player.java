package me.winter.newz.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Pools;
import me.winter.newz.World;
import me.winter.newz.physics.IntVector;

/**
 *
 * Created by 1541869 on 2016-10-31.
 */
public class Player extends WorldObject
{
	private IntVector position;
	private Vector3 tmpPosition = new Vector3();

	private Vector3 velocity; // m/s
	private Vector3 input; //aka Directional Influence

	private float speed, runningSpeed;
	private boolean ground;

	public Player(World world, Vector3 position)
	{
		super(world, world.getPlayerModel(), position);
		ground = false;
		velocity = new Vector3();
		input = new Vector3();

		speed = 5f;
		runningSpeed = 7f;
		this.position = new IntVector(position, 1000);
		getWorld().limitsFromBoundingBox(getLimits(), this);
	}

	@Override
	public void tick(float delta)
	{
		Vector3 inputVec = Pools.obtain(Vector3.class);
		IntVector tmpIntVector = Pools.obtain(IntVector.class);
		Vector3 tmpFloatVector = Pools.obtain(Vector3.class);

		if(ground)
			velocity.setZero();

		registerMovement(inputVec);
		inputVec.scl((Gdx.input.isKeyPressed(Keys.SHIFT_LEFT) ? runningSpeed : speed));

		float transitionRate = delta * 20f; //for a second (0.5 = in a second you did the average of 2 values)
		if(transitionRate > 1f)
			transitionRate = 1f;
		input.scl(1f - transitionRate).add(inputVec.scl(transitionRate));

		inputVec.set(input);

		if(Gdx.input.isKeyPressed(Keys.SPACE))
		{
			if(ground)
			{
				velocity.y = 5f;
				tmpFloatVector.set(input).scl(0.5f);
				velocity.add(tmpFloatVector);
			}
		}

		velocity.y += -10f * delta; //-10m/s² * s

		tmpFloatVector.set(velocity).scl(delta);



		//if(!ground)
		//	inputVec.scl(0.5f);

		tmpFloatVector.add(inputVec.scl(delta));

		tmpIntVector.floor(tmpFloatVector, 1000);

		ground = getWorld().replace(tmpIntVector, this);

		position.add(tmpIntVector);
		position.copyTo(tmpFloatVector, 0.001f);

		getModelInstance().transform.setToTranslation(tmpFloatVector);
		getModelInstance().calculateTransforms();
		getLimits().clear();
		getWorld().limitsFromBoundingBox(getLimits(), this);
		getWorld().getScreen().getCamera().position.set(getPosition().x, getPosition().y + 0.5f, getPosition().z);

		Pools.free(tmpIntVector);
		Pools.free(inputVec);
		Pools.free(tmpFloatVector);
	}

	private Vector3 registerMovement(Vector3 vec)
	{
		vec.setZero();

		Vector3 tmpVec = Pools.obtain(Vector3.class);

		if(Gdx.input.isKeyPressed(Keys.W))
			vec.add(tmpVec.set(getWorld().getScreen().getCamera().direction));

		if(Gdx.input.isKeyPressed(Keys.S))
			vec.add(tmpVec.set(getWorld().getScreen().getCamera().direction).scl(-1));

		if(Gdx.input.isKeyPressed(Keys.A))
			vec.add(tmpVec.set(getWorld().getScreen().getCamera().direction.z, 0, -getWorld().getScreen().getCamera().direction.x));

		if(Gdx.input.isKeyPressed(Keys.D))
			vec.add(tmpVec.set(-getWorld().getScreen().getCamera().direction.z, 0, getWorld().getScreen().getCamera().direction.x));

		vec.y = 0;

		return vec.nor();
	}

	@Override
	public Vector3 getPosition()
	{
		return position.copyTo(tmpPosition, 0.001f);
	}

	@Override
	public boolean isSolid()
	{
		return false;
	}

	public float getSpeed()
	{
		return speed;
	}

	public void setSpeed(float speed)
	{
		this.speed = speed;
	}

	public Vector3 getVelocity()
	{
		return velocity;
	}
}
