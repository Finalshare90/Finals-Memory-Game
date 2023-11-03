package com.finalshare.entities;

import java.util.List;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.finalshare.MemoryGame;

public class PlayerSelector {
	
	private int rowSize, gap;
	private Deck deck;
	int selectionPointer = 0;
	private Texture selectionTexture = new Texture("Sprites/card_selected.png");
	Sprite sprite = new Sprite(selectionTexture);
	
	public PlayerSelector(Deck deck, int rowSize, int gap) {
		this.deck = deck;
		this.rowSize = rowSize;
		this.gap = gap;
		float x = deck.getCardList().get(selectionPointer).getSprite().getX();
		float y = deck.getCardList().get(selectionPointer).getSprite().getY();
		sprite.setPosition(y,x); 
		
	}
	
	public void update(){
		inputHandler();
		
		float x, y;
		
		if(deck.getCardList().size() != 0) {
			try {
				x = deck.getCardList().get(selectionPointer).getSprite().getX();
				y = deck.getCardList().get(selectionPointer).getSprite().getY();
			}catch (Exception e) {
				x = deck.getCardList().get(selectionPointer - 1).getSprite().getX();
				y = deck.getCardList().get(selectionPointer - 1).getSprite().getY();
				selectionPointer = selectionPointer - 1;
			}
			sprite.setPosition(x,y); 
		
		}else {
			x = 0;
			y = 0;
		}
	}
	
	private void inputHandler(){
		
		Card currentCard;
		
		try {
			currentCard = deck.getCardList().get(selectionPointer);			
			} catch (Exception e) {
				//e.printStackTrace();
				currentCard = new Card("place holder");
			}
		
		
		deck.updateSelection();
		
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
		
		if(Gdx.input.isKeyJustPressed(Input.Keys.R)) {
			List<Card> deckCopy = deck.copyList(deck.cardListCopy);
			
			System.out.println("R");
			MemoryGame.DECK_INSTANCE_CONTROLLER.remove(0);
			MemoryGame.DECK_INSTANCE_CONTROLLER.add(deck = new Deck(deckCopy, rowSize, gap));			
		}
		
			
	}
	
	public void render(SpriteBatch batch) {
		batch.draw(sprite, sprite.getX(), sprite.getY());
	}
	
}
