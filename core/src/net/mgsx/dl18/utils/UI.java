package net.mgsx.dl18.utils;

import java.util.function.Consumer;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;

public class UI {
	public static <T extends Actor> T change(T actor, Consumer<ChangeEvent> handler){
		actor.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				handler.accept(event);
			}
		});
		return actor;
	}
	
	public static <T extends Slider> T changeCompleted(T slider, Consumer<ChangeEvent> handler){
		slider.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if(!slider.isDragging()) handler.accept(event);
			}
		});
		return slider;
	}
	public static <T extends Slider> T slide(T slider, Consumer<Float> handler){
		slider.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				handler.accept(slider.getValue());
			}
		});
		return slider;
	}
}
