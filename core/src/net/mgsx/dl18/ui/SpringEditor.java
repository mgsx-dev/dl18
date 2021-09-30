package net.mgsx.dl18.ui;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;

import net.mgsx.dl18.DL18;
import net.mgsx.dl18.springs.SpringGraph;
import net.mgsx.dl18.springs.SpringNode;

public class SpringEditor extends InputAdapter
{
	public boolean enabled = true;
	private DL18 game;
	private Vector3 intersection = new Vector3();
	private float screenZ;
	private boolean wasAnchor;
	
	public SpringEditor(DL18 game) {
		this.game = game;
	}
	
	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		if(!enabled) return false;
		Ray ray = game.camera.getPickRay(screenX, screenY);
		
		SpringNode bestNode = null;
		for(SpringGraph g : game.springs.graphs){
			for(SpringNode n : g.nodes){
				float radius = .5f;
				if(Intersector.intersectRaySphere(ray, n.position, radius, intersection)){
					bestNode = n;
				}
			}
		}
		
		if(bestNode != null){
			game.renderer.focusNode = bestNode;
		}else{
			game.renderer.focusNode = null;
		}
		
		return true;
	}
	
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if(!enabled || game.renderer.focusNode==null) return false;
		wasAnchor = game.renderer.focusNode.anchor;
		game.renderer.focusNode.anchor = true;
		screenZ = game.camera.project(new Vector3(game.renderer.focusNode.position)).z;
		return true;
	}
	
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		if(!enabled || game.renderer.focusNode==null) return false;
		if(game.renderer.focusNode.anchor){
			game.camera.unproject(game.renderer.focusNode.position.set(screenX, screenY, screenZ));
		}
		return true;
	}
	
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if(!enabled || game.renderer.focusNode==null) return false;
		game.renderer.focusNode.anchor = !wasAnchor;
		return true;
	}

}
