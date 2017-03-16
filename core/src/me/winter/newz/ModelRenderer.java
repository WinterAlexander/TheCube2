package me.winter.newz;

import com.badlogic.gdx.graphics.g3d.ModelInstance;

/**
 * <p>renders specified model</p>
 *
 * <p>Created by Alexander Winter on 2016-11-01.</p>
 */
public class ModelRenderer implements RenderObject
{
	private ModelInstance modelInstance;
	private float priority;

	public ModelRenderer(ModelInstance modelInstance)
	{
		this(modelInstance, 0.5f);
	}

	public ModelRenderer(ModelInstance modelInstance, float priority)
	{
		this.modelInstance = modelInstance;
		this.priority = priority;
	}

	@Override
	public void render(WorldScreen screen)
	{
		screen.getModelBatch().render(modelInstance, screen.getEnvironment());
	}

	@Override
	public float getPriority()
	{
		return priority;
	}
}
