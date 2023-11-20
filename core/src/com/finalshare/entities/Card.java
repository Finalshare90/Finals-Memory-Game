package com.finalshare.entities;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;


public class Card {
	
	private Texture fixtureTexture;
	public Sprite fixtureSprite;
	
	private Texture front = new Texture("Sprites/b_card_front.png"), back = new Texture("Sprites/b_card_back.png");
	public boolean flipped = false;
	private String labelBuffer;
	public String label;
	Card pair;
	private Sprite sprite = new Sprite(back);
	private FreeTypeFontGenerator fg = new FreeTypeFontGenerator(Gdx.files.internal("fonts/UbuntuLight.ttf"));
	private FreeTypeFontParameter fp = new FreeTypeFontParameter();
	public BitmapFont font;
	private int id;
	public float labelX, labelY;
	boolean isLabelPosDefined = false;
	
	
	public Card(String label, int labelFontSize) {
		this.labelBuffer = label;
		fp.color = Color.WHITE;
		fp.size = labelFontSize;
		font = fg.generateFont(fp);
		
	}
	
	public Card(String label, Card card, int labelFontSize){
		this(label, labelFontSize);
		setPair(card);
		
	}
	
	public Card(String label, int labelFontSize , Texture fixtureTexture){
		this(label, labelFontSize);
		
		
		this.fixtureTexture = fixtureTexture;
		fixtureSprite = new Sprite(fixtureTexture);
		fixtureSprite.setSize(getTexture().getWidth() / 2, getTexture().getWidth() / 2);
	}
	
	
	public Sprite getSprite() {
		
		getTexture();
		
		return sprite;
	}
	
	public void setLabelPosition(int leftGap, int downGap){
		if(!isLabelPosDefined) {
			labelX = leftGap + sprite.getX();
			labelY = sprite.getTexture().getHeight() / 2 + sprite.getY() + downGap;
		}
		
		if(fp.size * labelBuffer.length() > sprite.getTexture().getWidth() && label == null) {
			fitLabel(fp.size, downGap);
		}else {
			label = labelBuffer;
		}
		
	}	
	
	
	private void fitLabel(int fontSize, int downGap) {
		
		String labelBufferCopy = new String(labelBuffer);
		
		labelY = sprite.getY() + getSprite().getHeight() - downGap;
		
		char[] labelChars = labelBuffer.toCharArray();
		List<Character> labelCharsBuffer = new ArrayList<>();
		List<String> labelLineBuffer = new ArrayList<>();

		for(int cycle = 0; cycle < labelChars.length; cycle++) {
				
			if(labelCharsBuffer.size() * fontSize < getTexture().getWidth()*1.7) {
				labelCharsBuffer.add(labelChars[cycle]);
			}else {
				labelLineBuffer.add(combineCharList(labelCharsBuffer) + "\n");
				labelCharsBuffer = new ArrayList<>();
				cycle--;
			}
		}
		
		
		labelLineBuffer.add(combineCharList(labelCharsBuffer));
		//labelLineBuffer.add(Character.toString(labelChars[labelChars.length-1]));
		
		
		labelBuffer = combineStringList(labelLineBuffer);
	
		label = labelBuffer;
		labelBuffer = labelBufferCopy;
		isLabelPosDefined = true;
	}
	
	private String combineCharList(List<Character> charList) {
		
		String combinedString = charList.get(0).toString();
		
		for(int cycle = 1; cycle < charList.size(); cycle++) {
			combinedString = combinedString + charList.get(cycle).toString();
		}
		
		return combinedString;
	}
	
	private String combineStringList(List<String> stringList){
		
		String combinedString = stringList.get(0);
		
		for(int cycle = 1; cycle < stringList.size(); cycle++) {
			combinedString = combinedString + stringList.get(cycle);
		}
		
		return combinedString;
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
		font.dispose();
		back.dispose();
		front.dispose();
		fg.dispose();
	}
	
}
