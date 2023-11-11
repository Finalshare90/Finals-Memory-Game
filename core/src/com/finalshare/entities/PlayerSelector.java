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
	private Texture selectionTexture = new Texture("Sprites/b_card_selected.png");
	Sprite sprite = new Sprite(selectionTexture);
	ConfigParser configs;
	
	public PlayerSelector(Deck deck, int rowSize, int gap, ConfigParser configs) {
		this.configs = configs;
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
				
				int indexToRollBack = 1;
				
				while (selectionPointer - indexToRollBack >= deck.getCardList().size()) {
					indexToRollBack = indexToRollBack + 1;
				}
				
				x = deck.getCardList().get(selectionPointer - indexToRollBack).getSprite().getX();
				y = deck.getCardList().get(selectionPointer - indexToRollBack).getSprite().getY();
				selectionPointer = selectionPointer - indexToRollBack;
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
				currentCard = new Card("place holder", 10);
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
			
			boolean isSameCard = false;
			
			for(int cycle = 0; cycle < deck.cardSelection.length; cycle++) {
				if(currentCard == deck.cardSelection[cycle]) {
					deck.cardSelection[cycle] = null;
					deck.selected--;
					isSameCard = true;
				}
			}
			
			if(!isSameCard) {
				deck.addSelected(currentCard);				
			}
			currentCard.flipped = !currentCard.flipped;
		}
		
		if(Gdx.input.isKeyJustPressed(Input.Keys.R)) {
			
			configs.restart();
			
			List<Card> deckCopy = configs.cardList;
			
			MemoryGame.DECK_INSTANCE_CONTROLLER.remove(0);
			MemoryGame.DECK_INSTANCE_CONTROLLER.add(deck = new Deck(deckCopy, rowSize, gap));			
		}
		
			
	}
	
	public void render(SpriteBatch batch) {
		batch.draw(sprite, sprite.getX(), sprite.getY());
	}
	
}
