package me.winter.newz.objects;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.math.Vector3;
import me.winter.newz.World;

/**
 *
 * Created by 1541869 on 2016-10-31.
 */
public class LivingShip extends WorldObject
{
	public LivingShip(World world, Vector3 position)
	{
		super(world, world.getGame().getAssets().get("ship/ship.g3db", Model.class), position);
	}

	@Override
	public void tick(float delta)
	{
		/*Vector3 tmpVector = getPool().obtain(Vector3.class);
		IntVector tmpIntVector = getPool().obtain(IntVector.class);

		registerMovement(tmpVector);

		tmpVector.scl(finalSpeed() * delta);

		float newSpeedRatio = ground ? 1f : 0.1f;
		float previousSpeedRatio = ground ? 0.75f : 1f;

		velocity.set(previousSpeedRatio * velocity.x + newSpeedRatio * tmpVector.x, velocity.y, previousSpeedRatio * velocity.z + newSpeedRatio * tmpVector.z);

		if(ground && velocity.y < 0)
			velocity.y = 0;
		else if(Gdx.input.isKeyPressed(Keys.SPACE))
			if(ground)
				velocity.y = 1000f * delta;
			else if(velocity.y > 0)
				velocity.y += 70f * delta;

		velocity.y += -100f * delta;

		//if(getPosition().y + velocity.y < 0)
		//{
		//  System.out.println("Will break the ground !");
		//}

		tmpIntVector.set(velocity);

		ground = getWorld().replace(tmpIntVector, this);


		tmpIntVector.copyTo(velocity);

		transform.translate(velocity);
		calculateTransforms();
		getLimits().clear();
		getWorld().limitsFromBoundingBox(getLimits(), this);
		getWorld().getScreen().getCamera().position.set(getPosition().x, getPosition().y + 200f, getPosition().z);

		getPool().free(tmpIntVector);
		getPool().free(tmpVector);
	}

	private Vector3 registerMovement(Vector3 vec)
	{
		vec.setZero();

		Vector3 tmpVec = getWorld().getGame().getPool().obtain(Vector3.class);

		if(Gdx.input.isKeyPressed(Keys.W))
			vec.add(tmpVec.set(getWorld().getScreen().getCamera().direction));

		if(Gdx.input.isKeyPressed(Keys.S))
			vec.add(tmpVec.set(getWorld().getScreen().getCamera().direction).scl(-1));

		if(Gdx.input.isKeyPressed(Keys.A))
			vec.add(tmpVec.set(getWorld().getScreen().getCamera().direction.z, 0, -getWorld().getScreen().getCamera().direction.x));

		if(Gdx.input.isKeyPressed(Keys.D))
			vec.add(tmpVec.set(-getWorld().getScreen().getCamera().direction.z, 0, getWorld().getScreen().getCamera().direction.x));

		return vec.nor();*/
	}

	@Override
	public boolean isSolid()
	{
		return false;
	}
}
