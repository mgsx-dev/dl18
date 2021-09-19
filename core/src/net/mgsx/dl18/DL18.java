package net.mgsx.dl18;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

import net.mgsx.dl18.springs.SpringLoader;
import net.mgsx.dl18.springs.SpringModel;
import net.mgsx.dl18.springs.SpringRenderer;

public class DL18 extends ApplicationAdapter {
	
	private SpringModel springs;
	private SpringRenderer springRenderer;
	private Camera camera;
	private CameraInputController control;

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
		Gdx.input.setInputProcessor(control);
	}
	
	@Override
	public void resize(int width, int height) {
		camera.viewportWidth = width;
		camera.viewportHeight = height;
		camera.update();
	}

	@Override
	public void render () {
		
		control.update();
		
		ScreenUtils.clear(0, 0, 0, 0, true);
		
		springRenderer.render(springs, camera);
	}
	
}
