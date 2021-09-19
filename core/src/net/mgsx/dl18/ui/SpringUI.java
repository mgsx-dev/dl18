package net.mgsx.dl18.ui;

import java.util.function.Consumer;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;

import net.mgsx.dl18.springs.SpringConfig;
import net.mgsx.dl18.springs.SpringModel;
import net.mgsx.dl18.utils.UI;

public class SpringUI extends Window
{
	public SpringUI(Skin skin, SpringConfig config, SpringModel springs) {
		super("Springs", skin);
		
		addSlider("Gravity", -10, 10, 1, config.gravity.y, v->config.gravity.y=v);
		addSlider("Tension", 0, 1000, 10, config.tension, v->config.tension=v);
		addSlider("Damping", 0, 1, .1f, config.damping, v->config.damping=v);
		addTrigger("Reset springs", ()->springs.reset());
		
		pack();
	}
	
	private void addSlider(String name, float min, float max, float steps, float value, Consumer<Float> v){
		Slider s = new Slider(min, max, steps, false, getSkin());
		s.setValue(value);
		UI.slide(s, v);
		add(name);
		add(s);
		row();
	}
	
	private void addTrigger(String name, Runnable handler){
		add();
		add(UI.change(new TextButton(name, getSkin()), e->handler.run()));
		row();
	}
	
}
