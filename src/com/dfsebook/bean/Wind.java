package com.dfsebook.bean;

import java.util.ArrayList;
import java.util.List;

import com.dfsebook.util.CardUtil;

public class Wind extends CardType {

	/** 
	* @Title: distinguish
	* @Description: 判断发牌是否是风
	* @param @param cards
	* @param @return    设定文件
	* @return Wind    返回类型
	* @throws
	 */
	public static Wind distinguish(List<Card> cds) {
		List<Card> cards = new ArrayList<Card>(cds);
		if (cards == null || cards.size() < 5 ||
				!CardUtil.isSameSuit(cards))
			return null;
		CardUtil.simpleSortCards(cards);
		int face1 = cards.get(0).getFace();
		int face2 = cards.get(cards.size() - 1).getFace();
		if (face2 == face1 + 2 * (cards.size() - 1)) {
			Wind wind  = new Wind();
			wind.setFace(face1);
			wind.setSuit(cards.get(0).getSuit());
			wind.setCards(cards);
			return wind;
		}
		return null;
	}
	/** 
	* @Title: find
	* @Description: 找到所有符合条件的Wind
	* @param @param cards
	* @param @param trumpSuit 主花色
	* @param @param currentSuit 发牌牌型花色
	* @param @param parity Wind 第一张牌的奇偶性
	* @param @param currentSize 发牌Wind所含扑克牌的张数
	* @param @return    
	* @return List<Wind>    返回Wind集合
	* @throws
	 */
	public static List<Wind> find(List<Card> cds, int trumpSuit, 
			int currentSuit, int parity, int currentSize) {
		List<Card> cards = new ArrayList<Card>(cds);
		List<Wind> winds = new ArrayList<Wind>();
		cards = CardUtil.filterCards(cards, trumpSuit, currentSuit);		
		if (cards == null || cards.size() < 5)
			return winds;
		nomalFind(cards, winds, parity, currentSize);
		CardUtil.convertAndSortCards(cards);
		nomalFind(cards, winds, parity, currentSize);
		return winds;
	}
	
	private static List<Card> tCards;//
	
	private static void nomalFind(List<Card> cards, List<Wind> winds, 
			int parity, int currentSize) {
		for (int i = 0; i < cards.size(); i ++) {
			Card card = cards.get(i);
			tCards = new ArrayList<Card>();
			tCards.add(card);
			deepFind(cards,card, parity);
			if (tCards.size() >= currentSize) {
				Wind wind = formWindByCards();
				if (!winds.contains(wind)) {
					winds.add(wind);
					i += tCards.size() - 1;
				}
			}
		}
	}

	/** 
	* @Title: deepFind
	* @Description: 递归查找
	*  根据参数中的card，在集合中查找符合条件的牌，若有继续查找
	*  该递归对系统消耗很大，但是因为游戏中扑克牌张数有限，计算量和内存占有量
	*  不是很大，经过测试还是可以的，欢迎指导写出好的方法。
	* @param @param cards
	* @param @param card
	* @param @param parity  
	* @return void    
	* @throws
	 */
	private static void deepFind(List<Card> cards, Card card, int parity) {
		for (Card crd : cards) {
			if (crd.getFace() == card.getFace() + 2 &&
					crd.getFace() % 2 == parity) {
				tCards.add(crd);
				deepFind(cards, crd, parity);
			}
		}
	}
	
	private static Wind formWindByCards() {
		Wind wind = new Wind();
		wind.setFace(tCards.get(0).getFace());
		wind.setSuit(tCards.get(0).getSuit());
		wind.setCards(tCards);		
		return wind;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Wind ");
		if (getSuit() == 1)
			sb.append("Diamonds");
		else if (getSuit() == 2)
			sb.append("Clubs");
		else if (getSuit() == 3)
			sb.append("Hearts");
		else
			sb.append("Spades");
		sb.append(" ");
		for (int i = 0; i < getCards().size(); i ++) {
			sb.append(getFace() + 2 * i);
		}
		return sb.toString();
	}
	
	
}
