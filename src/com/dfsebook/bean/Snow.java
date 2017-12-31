
package com.dfsebook.bean;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.dfsebook.util.CardUtil;

public class Snow extends CardType {

	public static Snow distinguish(List<Card> cds) {
		List<Card> cards = new ArrayList<>(cds);
		if (cards == null || cards.size() != 5)
			return null;
		if (!CardUtil.isSameSuit(cards))
			return null;
		if (!CardUtil.isSameRank(cards))
			return null;
		CardUtil.simpleSortCards(cards);
		if (cards.get(4).getFace() != 14)
			return null;
		for (int i = 0; i < 4; i ++) {
			if (cards.get(i).getFace() != 3 * i + 4)
				return null;
		}
		Snow snow = new Snow();
		snow.setCards(cards);
		snow.setSuit(cards.get(0).getSuit());
		return snow;
	}
	
	public static Snow find(List<Card> cds,
			int trumpSuit, int currentSuit) {
		List<Card> cards = new ArrayList<Card>(cds);
		cards = CardUtil.filterCards(cards, trumpSuit, currentSuit);
		Set<Card> cs = new HashSet<Card>();
		for (Card card : cards) {
			if (card.getFace() == 4) cs.add(card);
			if (card.getFace() == 7) cs.add(card);
			if (card.getFace() == 10) cs.add(card);
			if (card.getFace() == 13) cs.add(card);
			if (card.getFace() == 14) cs.add(card);
		}
		if (cs.size() == 5) {
			Snow snow = new Snow();
			List<Card> myCards = new ArrayList<>(cs);
			snow.setCards(myCards);
			snow.setSuit(myCards.get(0).getSuit());
			return snow;
		}
		return null;		
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (getSuit() == 1)
			sb.append("Diamonds");
		else if (getSuit() == 2)
			sb.append("Clubs");
		else if (getSuit() == 3)
			sb.append("Hearts");
		else
			sb.append("Spades");		
		sb.append(" Snow A 4 7 10 K");
		return sb.toString();
	}
	
}
