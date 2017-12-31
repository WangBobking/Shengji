package com.dfsebook.bean;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.dfsebook.util.CardUtil;

public class Rain extends CardType {

	/** 
	* @Title: distinguish
	* @Description: 判断发牌者发的牌是否为雨
	*  思路是这样的：
	*  1.张数小于5直接返回空值
	*  2.判断是否为同一花色
	*  3.按照face排序
	*  4.最后一张牌face2与第一张牌的face1满足一定条件就是雨
	*  即face2 = face1 + 发牌张数 - 1
	* @param @param cards
	* @param @return    
	* @return Rain    返回雨或空值
	* @throws
	 */
	public static Rain distinguish(List<Card> cds) {
		List<Card> cards = new ArrayList<Card>(cds);
		if (cards == null || cards.size() < 5)
			return null;
		if (!CardUtil.isSameSuit(cards))
			return null;
		CardUtil.simpleSortCards(cards);
		int face1 = cards.get(0).getFace();
		int face2 = cards.get(cards.size() - 1).getFace();
		if (face2 == face1 + cards.size() - 1) {
			Rain rain = new Rain();
			rain.setSuit(cards.get(0).getSuit());
			rain.setFace(face1);
			rain.setCards(cards);
			return rain;
		}
		return null;
	}

	/** 
	* @Title: find
	* @Description: 查找所有符合条件的雨
	*  思路是这样的：
	*  1.过滤牌，去重，简单排序
	*  2.利用遍历和递归查找
	*  3.注意扑克中含有A时的情况
	* @param @param cards
	* @param @return    
	* @return List<Rain>    返回雨的集合
	* @throws
	 */
	public static List<Rain> find(List<Card> cds, int trumpSuit, 
			int currentSuit, int currentSize) {
		List<Card> cards = new ArrayList<Card>(cds);
		List<Rain> rains = new ArrayList<Rain>();
		cards = CardUtil.filterCards(cards, trumpSuit, currentSuit);
		distinctCards(cards);
		if (cards == null || cards.size() < 5)
			return rains;
		CardUtil.simpleSortCards(cards);
		nomalFind(cards, rains, currentSize);
		CardUtil.convertAndSortCards(cards);
		nomalFind(cards, rains, currentSize);
		return rains;
	}
	
	private static void distinctCards(List<Card> cards) {
		//利用HashSet的不重复性去重，这就需要重写Card类的hashCode()方法
		HashSet<Card> hs = new HashSet<Card>(cards);
		cards.clear();
		cards.addAll(hs);		
	}
	
	private static List<Card> tCards;//
	
	private static void deepFind(List<Card> cards, int index) {
		if (index < cards.size() - 1) {
			Card card = cards.get(index);
			Card crd = cards.get(index + 1);
			if (crd.getFace() == card.getFace() + 1) {
				tCards.add(crd);
				deepFind(cards, index + 1);
			}
		}
	}
	
	private static Rain formRainByCards(List<Card> tCards) {
		Rain rain = new Rain();
		rain.setFace(tCards.get(0).getFace());
		rain.setSuit(tCards.get(0).getSuit());
		rain.setCards(tCards);
		return rain;
	}
	
	private static void nomalFind(List<Card>cards, List<Rain> rains, int currentSize) {
		for (int i = 0; i < cards.size(); i ++) {
			Card card = cards.get(i);
			tCards = new ArrayList<Card>();
			tCards.add(card);
			deepFind(cards,i);
			if (tCards.size() >= currentSize) {
				Rain rain = formRainByCards(tCards);
				if (!rains.contains(rain)) {
					rains.add(rain);
					i += tCards.size() - 1;
				}
			}
		}
	}

}
