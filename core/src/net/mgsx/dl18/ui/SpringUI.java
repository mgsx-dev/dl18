package net.mgsx.dl18.ui;

import java.util.function.Consumer;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;

import net.mgsx.dl18.DL18;
import net.mgsx.dl18.utils.UI;

public class SpringUI extends Window
{
	public SpringUI(Skin skin, DL18 game) {
		super("Springs", skin);
		
		addSlider("Gravity", -10, 10, 1, game.config.gravity.y, v->game.config.gravity.y=v);
		addSlider("Tension", 0, 1000, 10, game.config.tension, v->game.config.tension=v);
		addSlider("Damping", 0, 1, .1f, game.config.damping, v->game.config.damping=v);
		addTrigger("Reset springs", ()->game.springs.reset());
		addToggle("Colorize speed", game.renderer.colorizeSpeed, b->game.renderer.colorizeSpeed=b);
		
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
	private void addToggle(String name, boolean value, Consumer<Boolean> handler){
		add();
		TextButton bt = new TextButton(name, getSkin(), "toggle");
		bt.setChecked(value);
		add(UI.change(bt, e->handler.accept(bt.isChecked())));
		row();
	}
}
