package net.mgsx.dl18.springs;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ImmediateModeRenderer20;

public class SpringRenderer {
	private ImmediateModeRenderer20 r = new ImmediateModeRenderer20(false, true, 0);
	public boolean colorizeSpeed = true;
	public SpringNode focusNode;
	
	public void render(SpringModel springs, Camera camera) {
		
		r.begin(camera.combined, GL20.GL_LINES);
		
		Color stableColor = new Color().set(Color.WHITE);
		Color unstableColor = new Color().set(colorizeSpeed ? Color.RED : stableColor);
		
		Color focusColor = new Color().set(Color.GREEN);
		Color anchorColor = new Color().set(Color.BLUE);
		
		float velTrig = .2f;
		
		for(SpringGraph g : springs.graphs){
			
			for(SpringEdge e : g.edges){
				
				if(e.nodeA == focusNode){
					r.color(focusColor);
				}
				else if(e.nodeA.anchor){
					r.color(anchorColor);
				}
				else{
					r.color(e.nodeA.velocity.len() > velTrig ? unstableColor : stableColor);
				}
				r.vertex(e.nodeA.position.x, e.nodeA.position.y, e.nodeA.position.z);
				
				if(e.nodeB == focusNode){
					r.color(focusColor);
				}
				else if(e.nodeB.anchor){
					r.color(anchorColor);
				}
				else{
					r.color(e.nodeB.velocity.len() > velTrig ? unstableColor : stableColor);
				}
				r.vertex(e.nodeB.position.x, e.nodeB.position.y, e.nodeB.position.z);
				
			}
			
		}
		
		r.end();
		
		
	}

}
