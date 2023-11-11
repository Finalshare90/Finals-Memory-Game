package com.finalshare.entities;

import java.util.ArrayList;
import java.util.List;

import com.kogaplanet.lunarlatteMarkupLanguage.Parser;
import com.kogaplanet.lunarlatteMarkupLanguage.TagNode;
import com.kogaplanet.lunarlatteMarkupLanguage.api.*;

public class ConfigParser {

	
	TagHandler handler = new TagHandler();
	String fileName;
	public List<Card> cardList = new ArrayList<>();
	
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
			
			Object[] cardDefinitions = parseDefinitions(currentTag);
			
			newCard = new Card((String)cardDefinitions[0], Integer.parseInt((String)cardDefinitions[1]));

			cardList.add(newCard);
			
			if(cardDefinitions[2] != null) {
				pair = (Card)cardDefinitions[2];
				cardList.add(pair);
				newCard.setPair(pair);
			}				
		}
		
	}
	
	
	/**
	 * @return Definitions of a card, 0 = label; 1 = fontSize; 2 = pair; 
	 * Cast it to the desired type.
	 * */
	private Object[] parseDefinitions(TagNode tag) {
	
		Object[] definitions = new Object[3];
		
		
		for(int dataCycle = 0; dataCycle < tag.data.size(); dataCycle++) {
			switch (tag.data.get(dataCycle)) {
			case "label=":
				dataCycle++;
				definitions[0] = tag.data.get(dataCycle);
				break;
				
			case "pair=":
				dataCycle++;
				
				TagNode pairNode = handler.call("deck").children.get(tag.data.get(dataCycle));
				Object[] pairDef = parseDefinitions(pairNode);
				
				cardCycle++;
				
				definitions[2] = new Card((String)pairDef[0],  Integer.parseInt((String)pairDef[1]));
				
				break;
			
			case "fontSize=":
				dataCycle++;
				definitions[1] = tag.data.get(dataCycle);
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
