package com.finalshare.entities;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.kogaplanet.lunarlatteMarkupLanguage.Parser;
import com.kogaplanet.lunarlatteMarkupLanguage.TagNode;
import com.kogaplanet.lunarlatteMarkupLanguage.api.*;

public class ConfigParser {

	
	private TagHandler handler = new TagHandler();
	private String fileName;
	public List<Card> cardList = new ArrayList<>();
	public int rows, gap;
	
	public ConfigParser(String fileName) {
		handler.parserInit(new Parser(fileName));
		this.fileName = fileName;
	}
	
	
	int cardCycle;
	
	public void start() {
		
		TagNode deckTag = handler.call("deck");
		
		List<String> identifiers = deckTag.childrenIdentifiers;
		
		
		for(cardCycle = 0; cardCycle < identifiers.size(); cardCycle++) {
			TagNode currentTag = deckTag.children.get(identifiers.get(cardCycle));
			
			Card pair, newCard;
			
			
			Object[] cardDefs = parseCardDefinitions(currentTag);
			
			if(cardDefs[3] == null) {
				newCard = new Card((String)cardDefs[0], Integer.parseInt((String)cardDefs[1]));
			}else {
				newCard = new Card((String)cardDefs[0], Integer.parseInt((String)cardDefs[1]), new Texture((String)cardDefs[3]));
			}
			
			cardList.add(newCard);
			
			if(cardDefs[2] != null) {
				pair = (Card)cardDefs[2];
				cardList.add(pair);
				newCard.setPair(pair);
			}				
		}
		
		int[] deckDefs = parseDeckDefinitions(deckTag);
		
		gap = deckDefs[0];
		rows = deckDefs[1];
	}
	
	
	/**
	 * @return Definitions of a card, 0 = label; 1 = fontSize; 2 = pair, 3 = fixture path; 
	 * Cast it to the desired type.
	 * */
	private Object[] parseCardDefinitions(TagNode tag) {
	
		Object[] definitions = new Object[4];
		
		
		for(int dataCycle = 0; dataCycle < tag.data.size(); dataCycle++) {
			switch (tag.data.get(dataCycle)) {
			
			case "label=":
				dataCycle++;

				String labelBuffer = tag.data.get(dataCycle) + " "; 
				
				
				
				if(tag.data.get(dataCycle).contains("\"")) {
					
					int quoteCount = 1;

					dataCycle++;
					
					while (quoteCount != 2) {	
						
						labelBuffer = labelBuffer + tag.data.get(dataCycle) + " ";

						if(tag.data.get(dataCycle).contains("\"")) {
							quoteCount++;
							break;
							
						}else{
							dataCycle++;
						}
						
					}
				}
				
				definitions[0] = labelBuffer;
				break;
				
			case "pair=":
				dataCycle++;
				
				TagNode pairNode = handler.call("deck").children.get(tag.data.get(dataCycle));
				Object[] pairDef = parseCardDefinitions(pairNode);
				
				cardCycle++;
				
				if(pairDef[3] != null) {
					definitions[2] = new Card((String)pairDef[0],  Integer.parseInt((String)pairDef[1]), new Texture((String)pairDef[3]));
				}else {
					definitions[2] = new Card((String)pairDef[0],  Integer.parseInt((String)pairDef[1]));
				}
				
				break;
			
			case "fontSize=":
				dataCycle++;
				definitions[1] = tag.data.get(dataCycle);
				break;
			
			case "img=":
				dataCycle++;
				definitions[3] = tag.data.get(dataCycle);
				break;
			}
		}
		
		return definitions;
	}
	
	/**
	 * @return Definitions of a deck, 0 = gap; 1 = rows;
	 * */
	private int[] parseDeckDefinitions(TagNode tag) {
		
		int[] definitions = new int[2];
	
		for(int dataCycle = 0; dataCycle < tag.data.size(); dataCycle++) {
			switch(tag.data.get(dataCycle)) {
				case "gap=":
					dataCycle++;
					definitions[0] = Integer.parseInt(tag.data.get(dataCycle));
					break;
					
				case "rows=":
					dataCycle++;
					definitions[1] = Integer.parseInt(tag.data.get(dataCycle));
					break;
			}	
		}
		
		return definitions;
	}
	
	
	public void restart() {
		handler.parserInit(new Parser(fileName));
		cardList = new ArrayList<>();
		start();
	}

}
