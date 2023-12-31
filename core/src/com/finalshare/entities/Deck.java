package com.finalshare.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Deck {
	
	private List<Card> cardList = new ArrayList<>();
	public List<Card> cardListCopy;
	Card[] cardSelection = new Card[2];
	int rowSize;
	
	public List<Card> copyList(List<Card> list) {
		
		List<Card> listBuffer = new ArrayList<Card>();
		
		for(int count = 0; list.size() > count; count++) {
			listBuffer.add(list.get(count));
		}
		
		return listBuffer;
	}
	
	// Starts a empty deck.
	public Deck() {}
	
	/**
	 * Starts a Deck with a cardList and sort its cards.
	 * Use that constructor by default.
	 * Row size just changes the way that your cards are going to be rendered.
	 * */
	public Deck(List<Card> cardList, int rowSize, int gap) {
		this.cardList = cardList;
		
		sortDeck(this.cardList);
		
		for(Card card : cardList) {
			card.label = null;
		}
		
		setCardPosition(rowSize, gap, cardList);
		
		this.rowSize = rowSize;
		
		cardListCopy = copyList(cardList);
	}
	
	private void setCardPosition(int rowSize, int gap, List<Card> cardList) {
		
		int x = 0, y = 0;	
		
		for(int cycle = 0, rowCycle = 0; cycle < cardList.size(); cycle++, rowCycle++) {
			
			if(rowCycle == rowSize) {
				x = 0;
				y = y - cardList.get(cycle).getTexture().getHeight() - gap;		
				rowCycle = 0;
			}
			
			if(rowCycle != rowSize) {
				x = x + cardList.get(cycle).getTexture().getWidth() + gap;
			
			}
			
			cardList.get(cycle).getSprite().setPosition(x, y);
			
		}
		
		setCardsComponentsPos();
		
	}
	
	private void setCardsComponentsPos() {
		for(Card card : cardList) {
			card.isLabelPosDefined = false;
			card.setLabelPosition(10, 10);
		}
	}
	
	/**
	 * Randomly sorts a deck.
	 * */
	public void sortDeck(List<Card> cardList) {
		
		
		Card[] cardBuffer = new Card[cardList.size()];
		int[] idBuffer = new int[cardList.size()];
		
		fillBuffer(cardBuffer);
		genUniqueIdVec(idBuffer, cardList.size());
		
		
		for(int cycle = 0; cycle < cardList.size(); cycle++) {
			cardBuffer[idBuffer[cycle]] = cardList.get(cycle);
			cardBuffer[idBuffer[cycle]].setId(idBuffer[cycle], cardList.size());
		}
		
		cardList.clear();
		
		for(int cycle = 0; cycle < cardBuffer.length; cycle++) {
			cardList.add(cardBuffer[cycle]);
		}
		
		
	}
	
	/**
	 * Choose a set of index positions, and sort it randomly.
	 * Its what powers the sortDeck() "id" sorting.
	 * */ 
	private void genUniqueIdVec(int[] idVector, int size){
		Random rand = new Random();
		int idCount[]= new int[size];
		
		for(int cycle = 0; cycle < size; cycle++){
			idVector[cycle] = rand.nextInt(size);
			idCount[cycle] = 0;
		}
		
		for(int cycle = 0; idVector.length > cycle; cycle++) {
			idCount[idVector[cycle]]++;
		}
		
		for(int idCycle = 0; idVector.length > idCycle; idCycle++){
		
			if(idCount[idVector[idCycle]] > 1) {
				
				Boolean isCountZero = false;
				int countCycle = 0;
				while(isCountZero == false){
					if(idCount[countCycle] == 0) {
						
						idCount[idVector[idCycle]]--;
						
						idVector[idCycle] = countCycle;
						
						idCount[countCycle]++;
						
						isCountZero = true;
					}
					countCycle++;
				}
				
			}
		
		}
	}
	
	
	public int selected = 0;
	public void addSelected(Card card){
		if(card != cardSelection[0] && card != cardSelection[1]) {
			cardSelection[selected] = card;
			selected++;
		}
		
	}
	/**
	 * Logical deck update, almost a render, but for 
	 * deck logic.
	 * */
	public void updateSelection() {
		
		if(checkPair()) {
			cardList.remove(cardSelection[0]);
			cardList.remove(cardSelection[1]);
			
			for(Card card : cardList) {
				card.label = null;
			}
			
			setCardPosition(rowSize, 40, cardList);
		}
		
		if(cardSelection[0] != null && cardSelection[1] != null) {	
			if(cardSelection[0] != cardSelection[1]) {
				flipCards();
			}
			removeFromSelection();
			selected = 0;	
		}
		
	}
	
	private void flipCards() {
		cardSelection[0].flipped = !cardSelection[0].flipped;
		cardSelection[1].flipped = !cardSelection[1].flipped;
	}
	
	private void removeFromSelection() {
		cardSelection[0] = null;
		cardSelection[1] = null;
	}
	
	/**
	 * Choose a set of index positions, and sort it randomly.
	 * Its what powers the sortDeck() "id" sorting.
	 * */ 
	
	private void fillBuffer(Card[] buffer) {
		for(int cycle = 0; cycle < buffer.length; cycle++){
			buffer[cycle] = null;
		}
	}
	
	/**
	 * Safe way to check for pairs
	 * */
	public boolean checkPair() {
		
		try {
			checkForNull(this.cardSelection);
		}catch (Exception e) {
			return false;
		}
		
		if(this.cardSelection[0].getPair() == cardSelection[1]){
			return true;
		}else {
			return false;
		}
		
	}
	
	
	private void checkForNull(Card[] cardVector) throws NullPointerException{
		
		for (int cycle = 0; cycle < cardVector.length; cycle++) {
			if(cardVector[cycle] == null){
				throw new NullPointerException();
			}
		}	
		
	}
	
	
	/**
	 * Add a pair to an deck, its a default constructor abstraction.
	 * Instead of setting multiple instances and configuring its pair manually,
	 * you can use that method.
	 * */
	public void add(Card card1, Card card2) {
		if(card1.getPair() == null || card2.getPair() == null) {
			card1.setPair(card2);
			card2.setPair(card1);
		}
		
		cardList.add(card1);
		cardList.add(card2);
		
	}
	
	public Card[] getSelection() {
		
		return cardSelection;
	}
	
	
	/**
	 * Graphics updating
	 * */ 
	public void render(SpriteBatch spriteBatch) {
		
		for(int cycle = 0; cycle < cardList.size(); cycle++) {
			
			float x = cardList.get(cycle).getSprite().getX();
			float y = cardList.get(cycle).getSprite().getY();
			
			Texture cardTexture = cardList.get(cycle).getTexture();
			
			spriteBatch.draw(cardTexture, x, y);
			
			
			if(cardList.get(cycle).flipped) {	
				
				Card currentCard = cardList.get(cycle);
				String labelToDraw = cardList.get(cycle).label;
								
				float labelX = currentCard.labelX;
				float labelY = currentCard.labelY;
				
				if(currentCard.fixtureSprite != null) {
					float fixtureX, fixtureY;
					
					fixtureX = currentCard.getSprite().getX() + currentCard.getSprite().getHeight() / 2;
					fixtureY = currentCard.getSprite().getY();
					
					currentCard.fixtureSprite.setPosition(fixtureX, fixtureY);
					currentCard.fixtureSprite.draw(spriteBatch);
				}
				
				currentCard.font.draw(spriteBatch, labelToDraw, labelX, labelY);
			}
		}
	}
	
	public List<Card> getCardList() {
		return cardList;
	}
}
