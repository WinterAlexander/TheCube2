package me.winter.newz.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.math.Vector3;
import me.winter.newz.World;
import me.winter.newz.physics.IntVector;

/**
 *
 * Created by 1541869 on 2016-10-31.
 */
public class Cube extends WorldObject
{
	private float speed = 0;
	private Vector3 vector3 = new Vector3();
	private IntVector intVector = new IntVector();

	public Cube(World world, Vector3 position)
	{
		super(world, world.getBoxModel(), position);
		//materials.get(0).set(new Material(TextureAttribute.createDiffuse(getWorld().getGame().getAssets().get("dirt_ground.jpg", Texture.class))));
		getWorld().limitsFromBoundingBox(getLimits(), this);
	}

	@Override
	public void tick(float delta)
	{
		speed += delta * 0.5f;
		vector3.set(0, -speed, 0);

		intVector.set(vector3, 1000);
		getWorld().replace(intVector, this);
		intVector.copyTo(vector3, 0.001f);

		getModelInstance().transform.translate(vector3);
		getModelInstance().calculateTransforms();
		getLimits().clear();
		getWorld().limitsFromBoundingBox(getLimits(), this);
	}

	@Override
	public boolean isSolid()
	{
		return true;
	}

}
