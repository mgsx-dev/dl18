package net.mgsx.dl18.springs;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ImmediateModeRenderer20;

public class SpringRenderer {
	private ImmediateModeRenderer20 r = new ImmediateModeRenderer20(false, true, 0);
	
	public void render(SpringModel springs, Camera camera) {
		
		r.begin(camera.combined, GL20.GL_LINES);
		
		Color color = new Color().set(Color.WHITE);
		
		for(SpringGraph g : springs.graphs){
			
			for(SpringEdge e : g.edges){
				
				r.color(color);
				r.vertex(e.nodeA.position.x, e.nodeA.position.y, e.nodeA.position.z);
				r.color(color);
				r.vertex(e.nodeB.position.x, e.nodeB.position.y, e.nodeB.position.z);
				
			}
			
		}
		
		r.end();
	}

}
