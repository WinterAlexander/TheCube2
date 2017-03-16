package me.winter.thecube2;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.StringBuilder;

import java.util.Random;


public class Galaxy3D implements ApplicationListener
{
	private Music music;

	private PerspectiveCamera camera;
	private ModelBatch batch;
	private Environment environment;

	private Array<ModelInstance> worldObjects;
	private AssetManager assetManager;

	private Array<ModelInstance> blocks;
	private Array<ModelInstance> invaders;
	private ModelInstance ship;
	private ModelInstance space;
	private Array<ModelInstance> rockets;

	private long lastShoot;

	private Stage stage;
	private Label label;
	private BitmapFont font;
	private StringBuilder text;

	private Vector3 posBuf, sizeBuf;
	private BoundingBox boxBuf;

	@Override
	public void create()
	{
		blocks = new Array<>();
		invaders = new Array<>();
		rockets = new Array<>();

		worldObjects = new Array<>();
		assetManager = new AssetManager();
		batch = new ModelBatch();

		stage = new Stage();
		font = new BitmapFont();
		label = new Label(" ", new Label.LabelStyle(font, Color.WHITE));
		stage.addActor(label);
		text = new StringBuilder();

		posBuf = new Vector3();
		sizeBuf = new Vector3();
		boxBuf = new BoundingBox();
		lastShoot = 0;

		camera = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.direction.z = -1;
		camera.direction.y = -0.5f;
		camera.position.set(0, 1f, 7.5f);
		camera.near = 0.01f;
		camera.far = 300f;
		camera.update();

		music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
		music.setLooping(true);
		music.play();

		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
		environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));

		assetManager.load("ship/ship.g3db", Model.class);
		assetManager.load("block/block.g3db", Model.class);
		assetManager.load("spacesphere/spacesphere.g3db", Model.class);
		assetManager.load("invader/invader.g3db", Model.class);
		assetManager.load("rocket.g3db", Model.class);
		assetManager.finishLoading();

		ship = new ModelInstance(assetManager.get("ship/ship.g3db", Model.class));
		ship.transform.setToRotation(Vector3.Y, 180).trn(0, 0, 6f);
		worldObjects.add(ship);

		Model blockModel = assetManager.get("block/block.g3db", Model.class);
		for(float x = -5f; x <= 5f; x += 2f)
		{
			for(float y = -6f; y <= 6f; y += 1f)
			{
				ModelInstance block = new ModelInstance(blockModel);
				block.transform.setToTranslation(x, y, 3f);
				worldObjects.add(block);
				blocks.add(block);
			}
		}

		Model invaderModel = assetManager.get("invader/invader.g3db", Model.class);
		for(float x = -5f; x <= 5f; x += 2f)
		{
			for(float y = -6f; y <= 6f; y += 1f)
			{
				for(float z = -8f; z <= 0f; z += 2f)
				{
					ModelInstance invader = new ModelInstance(invaderModel);
					invader.transform.setToTranslation(x, y, z);
					worldObjects.add(invader);
					invaders.add(invader);
				}
			}
		}

		space = new ModelInstance(assetManager.get("spacesphere/spacesphere.g3db", Model.class));


		/*ModelBuilder modelBuilder = new ModelBuilder();
		Model model = modelBuilder.createBox(5f, 5f, 5f,
				new Material(ColorAttribute.createDiffuse(Color.BLUE)),
				VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
		models.add(new ModelInstance(model, 0, 2f, 0));

		model = modelBuilder.createBox(1000f, 2f, 1000f,
				new Material(ColorAttribute.createDiffuse(Color.LIME)),
				VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
		models.add(new ModelInstance(model, 0, -2f, 0));

		assetManager.load("ship/ship.g3db", Model.class);
		assetManager.load("old_house_obj/house_01.g3db", Model.class);
		assetManager.load("tree/AlanTree.g3db", Model.class);
		assetManager.load("invader/invader.g3db", Model.class);
		assetManager.finishLoading();

		models.add(new ModelInstance(assetManager.get("tree/AlanTree.g3db"), -30f, 0f, -30f));
		models.add(new Ship(assetManager.get("ship/ship.g3db")));
		models.add(new ModelInstance(assetManager.get("old_house_obj/house_01.g3db"), 100f, 0f, 100f));
		models.add(new ModelInstance(assetManager.get("invader/invader.g3db"), -100f, 0f, 100f));*/
	}

	@Override
	public void resize(int width, int height)
	{
		stage.getViewport().update(width, height, true);
	}

	@Override
	public void render()
	{
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClearColor(91 / 255f, 202 / 255f, 1f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);


		batch.begin(camera);
		int visibleCount = 0;
		for(final ModelInstance instance : worldObjects)
		{
			if(isVisible(camera, instance))
			{
				batch.render(instance, environment);
				visibleCount++;
			}
		}
		batch.render(space);
		batch.end();

		text.setLength(0);
		text.append(" FPS: ").append(Gdx.graphics.getFramesPerSecond());
		text.append(" Visible: ").append(visibleCount);
		label.setText(text);
		stage.draw();

		float bonus = Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) ? 25 : 10;

		if(Gdx.input.isKeyPressed(Input.Keys.D))
		{
			posBuf.set(-bonus * Gdx.graphics.getDeltaTime(), 0, 0);

			if(camera.position.cpy().scl(-1).add(posBuf).x < -6)
				posBuf.x = -6 - camera.position.cpy().scl(-1).x;

			ship.transform.translate(posBuf);
			camera.translate(posBuf.scl(-1));
			space.transform.translate(posBuf.scl(0.5f));
		}


		if(Gdx.input.isKeyPressed(Input.Keys.A))
		{

			posBuf.set(bonus * Gdx.graphics.getDeltaTime(), 0, 0);

			if(camera.position.cpy().scl(-1).add(posBuf).x > 6)
				posBuf.x = 6 - camera.position.cpy().scl(-1).x;

			ship.transform.translate(posBuf);
			camera.translate(posBuf.scl(-1));
			space.transform.translate(posBuf.scl(0.5f));
		}

		if(Gdx.input.isKeyPressed(Input.Keys.W))
		{
			posBuf.set(0, bonus * Gdx.graphics.getDeltaTime(), 0);

			if(camera.position.cpy().add(posBuf).y > 6)
				posBuf.y = 6 - camera.position.cpy().y;

			ship.transform.translate(posBuf);
			camera.translate(posBuf);
			space.transform.translate(posBuf.scl(0.5f));
		}

		if(Gdx.input.isKeyPressed(Input.Keys.S))
		{
			posBuf.set(0, -bonus * Gdx.graphics.getDeltaTime(), 0);

			if(camera.position.cpy().add(posBuf).y < -6)
				posBuf.y = -6 - camera.position.cpy().y;

			ship.transform.translate(posBuf);
			camera.translate(posBuf);
			space.transform.translate(posBuf.scl(0.5f));
		}

		if(Gdx.input.isKeyPressed(Input.Keys.SPACE) && lastShoot + 70_000_000 < System.nanoTime())
		{
			ModelInstance rocket = new ModelInstance(assetManager.get("ship/ship.g3db", Model.class), ship.transform.getTranslation(posBuf));
			rocket.transform.rotate(0, 1, 0, 180);
			rocket.transform.scale(0.2f, 0.2f, 0.2f);
			rockets.add(rocket);
			worldObjects.add(rocket);
			lastShoot = System.nanoTime();
		}

		for(ModelInstance rocket : rockets)
		{
			rocket.transform.translate(0, 0, 100 * Gdx.graphics.getDeltaTime());
			rocket.transform.rotate(0, 0, 1, 720 * Gdx.graphics.getDeltaTime());
			rocket.calculateTransforms();
		}

		ship.calculateTransforms();
		space.calculateTransforms();
		camera.update();
	}

	protected boolean isVisible(final Camera cam, final ModelInstance instance)
	{
		instance.transform.getTranslation(posBuf);
		return cam.frustum.sphereInFrustum(posBuf, instance.calculateBoundingBox(boxBuf).getDimensions(sizeBuf).len());
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
		music.dispose();
		batch.dispose();
		worldObjects.clear();
		assetManager.dispose();
	}

	public PerspectiveCamera getCamera()
	{
		return camera;
	}

	public Array<ModelInstance> getWorldObjects()
	{
		return worldObjects;
	}
}
