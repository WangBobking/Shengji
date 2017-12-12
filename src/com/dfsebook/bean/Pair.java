package com.dfsebook.bean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.dfsebook.util.CardUtil;

public class Pair extends CardType {

	/**
	 * 
	* @Title: distinguish
	* @Description: 判断发牌者所出的牌是否是对牌
	* @param @param card1 第一张牌
	* @param @param card2 第二张牌
	* @param @return   
	* @return Pair    返回对或空
	* @throws
	 */
	
	public static Pair distinguish(Card card1, Card card2) {		
		if (card1.equals(card2)) {
			Pair pair = new Pair();
			pair.setFace(card1.getFace());
			pair.setSuit(card1.getSuit());
			pair.addCard(card1);
			pair.addCard(card2);
			return pair;
		}
		return null; 
	}
	/** 
	* @Title: find
	* @Description: 查找所有符合条件的对牌。 
	* @param @param cards 游戏者手中的所有的牌
	* @param @param currentSuit 发牌者出的牌型的花色
	* @param @return    
	* @return List<Pair>  对牌集合
	* @throws
	 */
	public static List<Pair> find(List<Card> cards, boolean controlled, 
			int trumpSuit, int currentSuit) {
		List<Pair> pairs = new ArrayList<Pair>();
		List<Card> adapterCards = filterCards(cards, controlled, trumpSuit, currentSuit);//过滤
		int size = adapterCards.size();
		if (size < 2) 
			return pairs;
		CardUtil.sortCards(adapterCards);
		for (int i = 0 ; i < size - 1; i ++) {
			Card card1 = adapterCards.get(i);
			Card card2 = adapterCards.get(i + 1);
			Pair pair = distinguish(card1, card2);
			if (pair != null) {
				pairs.add(pair);
				i ++;//循环变量前移
			}
		}
		return pairs;
	}
	
	public static void sortPairs(List<Pair> pairs) {
		Collections.sort(pairs, new Comparator<Pair>() {

			@Override
			public int compare(Pair pair1, Pair pair2) {
				int result = pair1.getRank() - pair2.getRank();
				if (result != 0) return result;
				result = pair1.getSuit() - pair2.getSuit();
				if (result != 0) return result;
				return pair1.getFace() - pair2.getFace();
			}
			
		});
	}
	
	private static List<Card> filterCards(List<Card> cards, boolean controlled, 
			int trumpSuit, int currentSuit) {
		List<Card> result = new ArrayList<Card>();
		if (!controlled) {
			result.addAll(cards);
		} else {
			for (Card card : cards) {
				if (trumpSuit == currentSuit) {//对应于拱主结束，并且此时发牌牌型肯定是主牌
					if (card.getSuit() == currentSuit) {//只需要挑出主花色的牌即可
						result.add(card);
					}
				} else {//对应于拱主阶段或拱主结束后发牌牌型是付牌
					if (card.getSuit() == currentSuit && card.getRank() == 0) {
						result.add(card);
					}	
				}			
			}
		}
		return result;
	}
}
