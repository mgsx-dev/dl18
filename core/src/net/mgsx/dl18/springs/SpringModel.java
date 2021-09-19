package net.mgsx.dl18.springs;

import com.badlogic.gdx.utils.Array;

public class SpringModel {
	public final Array<SpringGraph> graphs = new Array<SpringGraph>();

	public void update(float delta, SpringConfig config) {
		for(SpringGraph g : graphs){
			g.update(delta, config);
		}
	}

	public void reset() {
		for(SpringGraph g : graphs){
			g.reset();
		}
	}
}
