package com.dfsebook.bean;

import java.util.List;

import com.dfsebook.util.CardUtil;

public class Sister extends CardType {

	/** 
	* @Title: distinguish
	* @Description:判别发牌者出的扑克牌是否是姊妹对，如果是返回该姊妹对，否则返回空
	* @param @param cards 发牌者出的牌
	* @param @param controlled 是否处于主付不能连状态
	* @param @return    
	* @return Sister    返回姊妹对或空
	* @throws
	 */
	public static Sister distinguish(List<Card> cards, boolean controlled) {
		int size = cards.size();
		if (cards == null || size < 4 ||
				size % 2 != 0 || !CardUtil.isSameSuit(cards)) {
			//出牌为空或张数小于4或不为偶数或不为同一花色，直接返回空
			return null;
		}
		if (controlled && !CardUtil.isSameRank(cards)) {
			//主付不能连状态成为controlled(受控制，受约束)
			//处于受控状态，但出牌中既有主牌又有付牌，则返回空
			return null;
		}
		Sister sister = formSister(cards, size);
		if (sister != null)
			return sister;
		//把扑克牌中的A的牌面值从14变为1
		for (Card card : cards) {
			if (card.getFace() == 14) {
				card.setFace(1);
			}
		}
		sister = formSister(cards, size);
		return sister;
	}
	
	/** 
	* @Title: formSister
	* @Description: 
	*思路是这样的：单纯按照face排序,如果所有牌组成姊妹对，那么
	* 第一张和第二张牌是相等的，最后一张和倒数第二张是相等的
	* 并且倒数第二张牌的牌面值一定等于第一张牌的牌面值加上牌的总张数的一半减1
	* 满足这个条件就是姊妹对。这里面是否有漏洞？同志们有没有更好的方法。。。。
	* @param @param cards
	* @param @return   
	* @return Sister  
	* @throws
	 */
	private static Sister formSister(List<Card> cards, int size) {
		CardUtil.simpleSortCards(cards);		
		Card card1 = cards.get(0);
		Card card2 = cards.get(1);
		Card card3 = cards.get(size - 2);
		Card card4 = cards.get(size - 1);
		if (card1.equals(card2) && card3.equals(card4) &&
				card3.getFace() == card1.getFace() + size / 2 - 1) {
			Sister sister = new Sister();
			sister.setFace(card1.getFace());
			sister.setSuit(card1.getSuit());
			sister.setCards(cards);
			return sister;
		}
		return null;
	}
}
