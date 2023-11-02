package com.finalshare.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PlayerSelector {
	
	
	private Deck deck;
	int selectionPointer = 0;
	private Texture selectionTexture = new Texture("Sprites/card_selected.png");
	Sprite sprite = new Sprite(selectionTexture);
	
	public PlayerSelector(Deck deck) {
		this.deck = deck;
		
		float x = deck.getCardList().get(selectionPointer).getSprite().getX();
		float y = deck.getCardList().get(selectionPointer).getSprite().getY();
		sprite.setPosition(y,x); 
		
	}
	
	public void update(){
		inputHandler();
		
		float x = deck.getCardList().get(selectionPointer).getSprite().getX();
		float y = deck.getCardList().get(selectionPointer).getSprite().getY();
		
		sprite.setPosition(x,y); 
		
	}
	
	private void inputHandler(){
		
		Card currentCard = deck.getCardList().get(selectionPointer);
		
		
		if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
			
			selectionPointer++;
			if(selectionPointer > deck.getCardList().size()-1) {
				selectionPointer--;
			}
			
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
			selectionPointer--;
			if(selectionPointer < 0) {
				selectionPointer++;
			}
		}
		
		if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
			
			selectionPointer = selectionPointer + deck.rowSize;
			
			if(selectionPointer > deck.getCardList().size()-1){
				selectionPointer = selectionPointer - deck.rowSize;	
			}
		}
		
		if(Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
			selectionPointer = selectionPointer - deck.rowSize;
			if(selectionPointer < 0){
				selectionPointer = selectionPointer + deck.rowSize;
			}
		}
		
		if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
			deck.addSelected(currentCard);
			
			currentCard.flipped = !currentCard.flipped;
			
		}
		
			
	}
	
	public void render(SpriteBatch batch) {
		batch.draw(sprite, sprite.getX(), sprite.getY());
	}
	
}
