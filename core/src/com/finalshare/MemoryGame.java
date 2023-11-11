package com.finalshare;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.finalshare.entities.ConfigParser;
import com.finalshare.entities.Deck;
import com.finalshare.entities.PlayerSelector;

public class MemoryGame extends ApplicationAdapter {
	
	BitmapFont font;
	SpriteBatch batch;
	Sprite back, front;
	Deck deck;
	OrthographicCamera cam;
	PlayerSelector player;
	ConfigParser configs;
	public static List<Deck> DECK_INSTANCE_CONTROLLER = new ArrayList<>();
	
	@Override
	public void create () {
		
		font = new BitmapFont(false);
		batch = new SpriteBatch();
		back = new Sprite(new Texture("Sprites/card_back.png"));
		front = back;
		
		configs = new ConfigParser("deck.3ml");
		configs.start();
		
		deck = new Deck(configs.cardList, 4, 40);
		
		DECK_INSTANCE_CONTROLLER.add(deck);
		
		player = new PlayerSelector(DECK_INSTANCE_CONTROLLER.get(0), 4, 40, configs);
		
		cam = new OrthographicCamera(Gdx.graphics.getWidth() * 2, Gdx.graphics.getHeight()* 2);
		
		
	}
	
	@Override
	public void render () {
		
		ScreenUtils.clear(Color.valueOf("#20394F"));
		debugCamera();
		cam.update();
		
		//System.out.println(deck.getSelection());
		
		batch.setProjectionMatrix(cam.combined);
		
		player.update();
		
		batch.begin();
		DECK_INSTANCE_CONTROLLER.get(0).render(batch);
		player.render(batch);
		batch.end();
	}
	
	private void debugCamera() {
		
		if(Gdx.input.isKeyPressed(Input.Keys.W)) {
			cam.position.y += 3;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.S)) {
			cam.position.y-= 3;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.A)) {
			cam.position.x-= 3;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.D)) {
			cam.position.x+= 3;
		}
			
		
	}
	
	@Override
	public void dispose () {
		font.dispose();
		batch.dispose();
	}
}
