package net.mgsx.dl18.springs;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

public class SpringGraph {
	public final Array<SpringNode> nodes = new Array<SpringNode>();
	public final Array<SpringEdge> edges = new Array<SpringEdge>();
	
	public void update(float delta, SpringConfig config) {
		
		Vector3 damp = new Vector3();
		for(SpringNode n : nodes){
			n.forces.set(config.gravity);
			
			// damping
			damp.set(n.velocity);
			float len2 = damp.len2();
			damp.nor();
			
			n.forces.mulAdd(damp, MathUtils.lerp(0, -len2/2, config.damping));
		}
		
		Vector3 dir = new Vector3();
		for(SpringEdge e : edges){
			
			dir.set(e.nodeB.position).sub(e.nodeA.position);
			
			float distance = dir.len();
			e.distance = distance;
			if(distance > 0){
				dir.scl(1f / distance);
			}
			float displacement = distance - e.originalDistance;
			
			e.nodeA.forces.mulAdd(dir, displacement * config.tension);
			e.nodeB.forces.mulAdd(dir, -displacement * config.tension);
			
		}
		
		for(SpringNode n : nodes){
			if(n.anchor) continue;
			n.velocity.mulAdd(n.forces, delta);
			n.position.mulAdd(n.velocity, delta);
			
			boolean clamped = false;
			if(config.boundsMin != null){
				if(n.position.x <= config.boundsMin.x){
					n.position.x = config.boundsMin.x;
					clamped = true;
				}
				if(n.position.y <= config.boundsMin.y){
					n.position.y = config.boundsMin.y;
					clamped = true;
				}
				if(n.position.z <= config.boundsMin.z){
					n.position.z = config.boundsMin.z;
					clamped = true;
				}
			}
			if(config.boundsMax != null){
				if(n.position.x >= config.boundsMax.x){
					n.position.x = config.boundsMax.x;
					clamped = true;
				}
				if(n.position.y >= config.boundsMax.y){
					n.position.y = config.boundsMax.y;
					clamped = true;
				}
				if(n.position.z >= config.boundsMax.z){
					n.position.z = config.boundsMax.z;
					clamped = true;
				}
			}
			if(clamped){
				n.velocity.setZero();
			}
		}
	}

	public void reset() {
		for(SpringNode n : nodes){
			n.reset();
		}
		for(SpringEdge e : edges){
			e.reset();
		}
		
	}
	
}
