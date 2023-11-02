package com.finalshare.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Card {

	private Texture front = new Texture("Sprites/card_front.png"), back = new Texture("Sprites/card_back.png");
	public boolean flipped = false;
	public String label;
	Card pair;
	private Sprite sprite = new Sprite(back);
	private int id;
	
	public Card(String label) {
		this.label = label;
	}
	
	public Card(String label, Card card){
		setPair(card);
	}
	
	public Sprite getSprite() {
		
		getTexture();
		
		return sprite;
	}
	
	public Texture getTexture() {
		if(flipped) {
			sprite.setTexture(this.front);
			return this.front;
		}else{
			sprite.setTexture(this.back);
			return this.back;
			}
	}
	
	public int getId() {
		return this.id;
	}
	
	public void setId(int id, int deckSize) throws IndexOutOfBoundsException{
		if(id > deckSize - 1) {
			System.err.println("Deck size is smaller than specified ID");
			throw new IndexOutOfBoundsException();
		}
		
		this.id = id;
	}
	
	public void setPair(Card pair) {
		pair.pair = this;
		this.pair = pair;
	}
	
	public Card getPair() {
		return pair;
	}
	
	public void dispose() {
		back.dispose();
		front.dispose();
	}
	
}
