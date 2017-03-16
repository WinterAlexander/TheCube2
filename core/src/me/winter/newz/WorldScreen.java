package me.winter.newz;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Pools;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import me.winter.newz.objects.Cube;
import me.winter.newz.objects.Player;
import me.winter.newz.objects.Terrain;
import me.winter.newz.objects.WorldObject;

import java.util.Random;

import static com.badlogic.gdx.graphics.VertexAttributes.Usage.Normal;
import static com.badlogic.gdx.graphics.VertexAttributes.Usage.Position;
import static com.badlogic.gdx.graphics.VertexAttributes.Usage.TextureCoordinates;

/**
 * <p>The actual game</p>
 *
 * <p>Created by 1541869 on 2016-05-16.</p>
 */
public class WorldScreen implements GameState
{
	private NewZ game;

	private WorldInput input;

	private World world;

	private Environment environment;

	private ShaderProgram shaderProgram;
	private ModelBatch batch;
	private ShapeRenderer debugRenderer;
	private Stage stage;
	private PerspectiveCamera camera;

	private boolean debug;

	public WorldScreen(NewZ game)
	{
		this.game = game;
		debug = false;
	}

	@Override
	public void create()
	{
		world = new World(game);

		camera = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.near = 0.01f; //one centimeter
		camera.far = 1000f; //1000 meters
		camera.position.set(3f, 0.3f, 0.3f);
		camera.lookAt(0, 0, 0);

		batch = new ModelBatch();
		debugRenderer = new ShapeRenderer();
		stage = new Stage(new ScreenViewport());

		input = new WorldInput(this);

		Gdx.input.setInputProcessor(new InputMultiplexer(stage, input));

		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
		environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));

		Player player = new Player(world, new Vector3(3f, 0.25f, 0.035f));
		world.getObjects().add(player);
		stage.addActor(new DebugInfos(player));

		makeBlock(new Vector3(0f, 10f, 0f));

		Material groundMat = new Material(TextureAttribute.createDiffuse(getGame().getAssets().get("dirt_ground.jpg", Texture.class)));
/*
		builder.begin();
		MeshPartBuilder meshPart = builder.part("top", GL20.GL_TRIANGLES, Position | Normal | TextureCoordinates, groundMat);
		meshPart.rect(  -1f, 0f, -1f,
						-1f, 0f,  1f,
						 1f, 0f,  1f,
						 1f, 0f, -1f,

						 0f, 1f, 0f//normals
		);
		meshPart.setUVRange(0f, 0f, 1f, 1f);
*/
//		Model ground = builder.end();
/*
		for(int i = -50; i < 50; i+=2)
			for(int j = -50; j < 50; j+=2)
				world.getObjects().add(new WorldObject(world, ground, new Vector3(i, 0f, j))
				{
					{
						world.limitsFromBoundingBox(getLimits(), this);
					}

					@Override
					public void tick(float delta)
					{

					}

					@Override
					public boolean isSolid()
					{
						return true;
					}
				});*/
		world.getObjects().add(new Terrain(world, groundMat, new Vector3(0f, 0f, 0f), 50, 50, 2f));

		world.getObjects().add(new WorldObject(world, getGame().getAssets().get("ship/ship.g3db", Model.class), new Vector3(5, 2, 5))
		{

			@Override
			public void tick(float delta)
			{

			}

			@Override
			public boolean isSolid()
			{
				return false;
			}
		});

		world.getObjects().add(new WorldObject(world, getGame().getAssets().get("dirt_bag.g3db", Model.class), new Vector3(-5, 2, 5))
		{
			{
				getModelInstance().transform.scale(0.005f, 0.005f, 0.005f);
				getModelInstance().calculateTransforms();
			}

			@Override
			public void tick(float delta)
			{

			}

			@Override
			public boolean isSolid()
			{
				return false;
			}
		});

		/*ShaderProgram.pedantic = false;
		shaderProgram = new ShaderProgram(Gdx.files.internal("shaders/vertexShader.glsl"), Gdx.files.internal("shaders/fragmentShader.glsl"));

		if(!shaderProgram.isCompiled())
		{
			System.out.println(shaderProgram.getLog());
		}*/
	}

	public void makeBlock(Vector3 position)
	{
		world.getObjects().add(new Cube(world, position));
	}

	@Override
	public void render(float delta)
	{
		stage.act();
		world.getObjects().forEach(object -> object.tick(Gdx.graphics.getDeltaTime()));
		camera.update(true);

		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		batch.begin(camera);
		if(debug)
		{
			debugRenderer.setProjectionMatrix(camera.combined);
			debugRenderer.begin(ShapeType.Line);
		}

		world.getObjects().forEach(object -> {

			if(camera.frustum.sphereInFrustum(object.getPosition(), object.getDimensions().len() / 2))
				batch.render(object.getModelInstance(), environment);

			if(debug)
				drawBoundingBox(object.getPosition(), object.getBoundingBox());
		});
		batch.end();
		if(debug)
			debugRenderer.end();
		stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
		stage.draw();
	}

	public void drawBoundingBox(Vector3 position, BoundingBox box)
	{
		Vector3 tmpVector = Pools.obtain(Vector3.class), tmpVector2 = Pools.obtain(Vector3.class);

		debugRenderer.setColor(Color.BLUE);

		debugRenderer.line(box.getCorner000(tmpVector).add(position), box.getCorner100(tmpVector2).add(position));
		debugRenderer.line(box.getCorner100(tmpVector).add(position), box.getCorner101(tmpVector2).add(position));
		debugRenderer.line(box.getCorner101(tmpVector).add(position), box.getCorner001(tmpVector2).add(position));
		debugRenderer.line(box.getCorner001(tmpVector).add(position), box.getCorner000(tmpVector2).add(position));

		debugRenderer.line(box.getCorner010(tmpVector).add(position), box.getCorner110(tmpVector2).add(position));
		debugRenderer.line(box.getCorner110(tmpVector).add(position), box.getCorner111(tmpVector2).add(position));
		debugRenderer.line(box.getCorner111(tmpVector).add(position), box.getCorner011(tmpVector2).add(position));
		debugRenderer.line(box.getCorner011(tmpVector).add(position), box.getCorner010(tmpVector2).add(position));

		debugRenderer.line(box.getCorner000(tmpVector).add(position), box.getCorner010(tmpVector2).add(position));
		debugRenderer.line(box.getCorner100(tmpVector).add(position), box.getCorner110(tmpVector2).add(position));
		debugRenderer.line(box.getCorner001(tmpVector).add(position), box.getCorner011(tmpVector2).add(position));
		debugRenderer.line(box.getCorner101(tmpVector).add(position), box.getCorner111(tmpVector2).add(position));

		Pools.free(tmpVector);
		Pools.free(tmpVector2);
	}

	@Override
	public void resize(int width, int height)
	{
		if(stage != null)
			stage.getViewport().update(width, height, true);
	}

	@Override
	public void pause()
	{

	}

	@Override
	public void resume()
	{

	}


	@Override
	public void dispose()
	{
		stage.dispose();
		debugRenderer.dispose();
		batch.dispose();
	}

	public NewZ getGame()
	{
		return game;
	}

	public ModelBatch getModelBatch()
	{
		return batch;
	}

	public Environment getEnvironment()
	{
		return environment;
	}

	public PerspectiveCamera getCamera()
	{
		return camera;
	}

	public boolean isDebug()
	{
		return debug;
	}

	public void setDebug(boolean debug)
	{
		this.debug = debug;
	}
}
