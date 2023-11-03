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
import com.finalshare.entities.Card;
import com.finalshare.entities.Deck;
import com.finalshare.entities.PlayerSelector;

public class MemoryGame extends ApplicationAdapter {
	
	BitmapFont font;
	SpriteBatch batch;
	Sprite back, front;
	Deck deck;
	OrthographicCamera cam;
	PlayerSelector player;
	public static List<Deck> DECK_INSTANCE_CONTROLLER = new ArrayList<>();
	
	@Override
	public void create () {
		
		font = new BitmapFont(false);
		batch = new SpriteBatch();
		back = new Sprite(new Texture("Sprites/card_back.png"));
		front = back;
		
		List<Card> cardList = new ArrayList<>();
		
		cardList.add(new Card("1"));
		cardList.add(new Card("2"));
		
		cardList.get(0).setPair(cardList.get(1));
		
		cardList.add(new Card("3"));
		cardList.add(new Card("4"));
		
		cardList.get(2).setPair(cardList.get(3));
		
		cardList.add(new Card("5"));
		cardList.add(new Card("6"));
		
		cardList.get(4).setPair(cardList.get(5));
		
		cardList.add(new Card("7"));
		cardList.add(new Card("8"));
		
		cardList.get(6).setPair(cardList.get(7));
		
		cardList.add(new Card("9"));
		cardList.add(new Card("10"));
		
		cardList.get(8).setPair(cardList.get(9));
		
		cardList.add(new Card("11"));
		cardList.add(new Card("12"));
		
		cardList.get(10).setPair(cardList.get(11));
		
		cardList.add(new Card("13"));
		cardList.add(new Card("14"));
		
		cardList.get(12).setPair(cardList.get(13));
		
		cardList.add(new Card("15"));
		cardList.add(new Card("16"));
		
		cardList.get(14).setPair(cardList.get(15));
		
		deck = new Deck(cardList, 3, 40);
		
		DECK_INSTANCE_CONTROLLER.add(deck);
		
		player = new PlayerSelector(DECK_INSTANCE_CONTROLLER.get(0), 3, 40);
		
		cam = new OrthographicCamera(1200,1200);
		
		
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
			cam.position.y++;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.S)) {
			cam.position.y--;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.A)) {
			cam.position.x--;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.D)) {
			cam.position.x++;
		}
			
		
	}
	
	@Override
	public void dispose () {
		font.dispose();
		batch.dispose();
	}
}
