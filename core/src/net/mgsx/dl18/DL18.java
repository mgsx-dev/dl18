package net.mgsx.dl18;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import net.mgsx.dl18.springs.SpringConfig;
import net.mgsx.dl18.springs.SpringLoader;
import net.mgsx.dl18.springs.SpringModel;
import net.mgsx.dl18.springs.SpringRenderer;
import net.mgsx.dl18.ui.SpringUI;

public class DL18 extends ApplicationAdapter {
	
	private SpringModel springs;
	private SpringRenderer springRenderer;
	private Camera camera;
	private CameraInputController control;
	private SpringConfig config;
	private Stage stage;

	@Override
	public void create () {
		springs = new SpringLoader().load("models/models.gltf");
		springRenderer = new SpringRenderer();
		camera = new PerspectiveCamera();
		camera.position.set(1, 1, 1).scl(10);
		camera.up.set(Vector3.Y);
		camera.lookAt(Vector3.Zero);
		camera.update();
		
		control = new CameraInputController(camera);
		
		config = new SpringConfig();
		config.boundsMin = new Vector3(-10, 0, -10);
		config.boundsMax = new Vector3(10, 10, 10);
		config.gravity.set(0,-9.8f, 0);
		config.tension = 500f;
		config.damping = 1f;
		
		stage = new Stage(new ScreenViewport());
		Skin skin = new Skin(Gdx.files.internal("skins/uiskin.json"));
		SpringUI ui = new SpringUI(skin, config, springs);
		stage.addActor(ui);
		
		Gdx.input.setInputProcessor(new InputMultiplexer(stage, control));
	}
	
	@Override
	public void resize(int width, int height) {
		camera.viewportWidth = width;
		camera.viewportHeight = height;
		camera.update();
		stage.getViewport().update(width, height);
	}

	@Override
	public void render () {
		
		float delta = Gdx.graphics.getDeltaTime();
		
		springs.update(delta, config);
		
		control.update();
		
		stage.act();
		
		ScreenUtils.clear(0, 0, 0, 0, true);
		
		springRenderer.render(springs, camera);
		
		stage.draw();
	}
	
}
