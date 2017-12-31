
package com.dfsebook.bean;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.dfsebook.util.CardUtil;

public class Thunder extends CardType {

	/** 
	 * @Title: distinguish
	* @Description: 判断发牌者发的牌是否为雷
	*  思路是这样的：
	*  1.张数小于4直接返回空值
	*  2.判断所有的牌是否为自然主
	*  3.判断所有的牌face是否全相等
	*  4.是否包含所有四种花色
	* @param @param cards
	* @param @return    
	* @return Rain    返回雷或空值
	* @throws
	 */
	public static Thunder distinguish(List<Card> cds) {
		List<Card> cards = new ArrayList<Card>(cds);
		if (cards == null || cards.size() < 4)
			return null;
		Set<Integer> suits = new HashSet<Integer>();
		int face = cards.get(0).getFace();
		for (Card card : cards) {
			if(card.getRank() < 2)
				return null;
			if(card.getFace() != face)
				return null;
			suits.add(card.getSuit());
		}
		if (suits.size() < 4)
			return null;
		Thunder thu = new Thunder();
		thu.setSuit(cards.get(0).getSuit());
		thu.setFace(face);
		thu.setCards(cards);
		return thu;
	}

	private static List<Card> tCards;
	
	public static List<Thunder> find(List<Card> cds, int currentSize) {
		List<Card> cards = new ArrayList<Card>(cds);
		List<Thunder> thus = new ArrayList<Thunder>();
		cards = CardUtil.filterNuturals(cards);
		if (cards == null || cards.size() < 4)
			return thus;
		CardUtil.simpleSortCards(cards);
		for (int i = 0; i < cards.size(); i ++) {
			Card card = cards.get(i);
			tCards = new ArrayList<Card>();
			tCards.add(card);
			deepFind(cards, i);
			Thunder thu = formThunderByCards(cards, currentSize);
			if (thu != null) {
				thus.add(thu);
				i += tCards.size();
			}
		}
		return thus;
	}
		
	private static void deepFind(List<Card> cards, int index) {
		if (index < cards.size()) {
			Card card = cards.get(index);
			Card crd = cards.get(index + 1);
			if (crd.getFace() == card.getFace()) {
				tCards.add(crd);
				deepFind(cards, index + 1);
			}
		}
	}
	
	private static Thunder formThunderByCards(List<Card> tCards, int currentSize) {
		if (tCards.size() < currentSize)
			return null;
		Set<Integer> suits = new HashSet<Integer>();
		for (Card card : tCards) {
			suits.add(card.getSuit());
		}
		if (suits.size() < 4)
			return null;
		Thunder thu = new Thunder();
		thu.setSuit(tCards.get(0).getSuit());
		thu.setFace(tCards.get(0).getFace());
		thu.setCards(tCards);
		return thu;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName() );
		String face = String.valueOf(getFace());
		if (getFace() == 11)
			face = "J";
		if (getFace() == 12)
			face = "Q";
		if (getFace() == 13)
			face = "K";
		if (getFace() == 14)
			face = "A";
		sb.append(face + " Pieces ");
		sb.append(getCards().size());		
		return sb.toString();
	}
	
}
