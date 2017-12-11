package com.dfsebook.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.dfsebook.bean.Card;

public class CardUtil {

	/** 
	* @Title: isSameSuit
	* @Description: 判断所有扑克牌是否为同一花色
	* @param @param cards
	* @param @return   
	* @return boolean    
	* @throws
	 */
	public static boolean isSameSuit(List<Card> cards) {
		if (cards == null || cards.size() == 0)
			return false;
		int suit = cards.get(0).getSuit();
		for (Card card : cards) {
			if (card.getSuit() != suit)
				return false;
		}
		return true;
	}
	
	/** 
	* @Title: isSameRank
	* @Description: 判断所有的牌是否同为主牌与否
	* 思路是这样的：
	* 遍历所有扑克牌找出最大rank值和最小rank值
	* 如果最大rank等于0，不用问所有牌都是付牌
	* 如果最小rank大于0，则所有牌都是主牌
	* 也就解决了主付不能连的问题
	* @param @param cards
	* @param @return    
	* @return boolean    
	* @throws
	 */
	public static boolean isSameRank(List<Card> cards) {
		int min = 50;
		int max = 0;
		for (Card card : cards) {
			if (min >= card.getRank()) {
				min = card.getRank();
			}
			if (max <= card.getRank()) {
				max = card.getRank();
			}
		}
		if (max == 0 || min > 0) {
			return true;
		}
		return false;
	}
	
	/** 
	* @Title: sortCards
	* @Description: 根据牌的rank,suit,face依次排序
	* @param @param cards    游戏者手中的扑克牌
	* @return void    
	* @throws
	 */
	public static void sortCards(List<Card> cards) {
		Collections.sort(cards, new Comparator<Card>() {

			@Override
			public int compare(Card card1, Card card2) {
				int result = card1.getRank() - card2.getRank();//先比较rank值
				if (result != 0)
					return result;
				result = card1.getSuit() - card2.getSuit();//再比较suit值
				if (result != 0)
					return result;
				return card1.getFace() - card2.getFace();//最后比较face值
			}
			
		});
	}
	

	/** 
	* @Title: simpleSortCards
	* @Description: 简单的对扑克牌进行排序，即同花色的牌根据其牌面值face进行排序
	* 而不管是否为自然主等其它属性。
	* @param @param cards    
	* @return void    
	* @throws
	 */
	public static void simpleSortCards(List<Card> cards) {
		Collections.sort(cards, new Comparator<Card>() {

			@Override
			public int compare(Card card1, Card card2) {
				return card1.getFace() - card2.getFace();
			}	
		});
	}
	
	/** 
	* @Title: filterCards
	* @Description: 根据条件过滤筛选扑克
	* 分几种情况，
	* 1.抓牌阶段除王牌以外所有的牌全符合条件
	* 2.拱主阶段如果出牌牌型是自然主，则范围确定为所有自然主
	*   如果出牌牌型是非自然主，则范围确定为同花色牌
	* 3.发随牌阶段如果出牌牌型是主牌，则范围确定为主牌
	*   如果出牌牌型是付牌，则范围确定为同花色牌  
	* @param @param cards
	* @param @param currentSuit 当前牌型的花色
	* @param @param currentRank 当前牌型的rank
	* @param @return    
	* @return List<Card> 过滤后的扑克牌集合
	* @throws
	 */
	public static List<Card> filterCards(List<Card> cards, int trumpSuit,
			int currentSuit, int currentRank) {
		List<Card> adapterCards = new ArrayList<Card>();
		if (trumpSuit == 0) {
			if (currentSuit == 0) {//对应于抓牌阶段
				for (Card card : cards) {
					if (card.getFace() < 20) {
						adapterCards.add(card);
					}
				}				
			} else {//对应于拱主阶段
				if (currentRank == 0) {//出的是付牌
					for (Card card : cards) {
						if (card.getSuit() == currentSuit ||
								card.getRank() == 0) {//同花色的付牌
							adapterCards.add(card);
						}
					}	
				} else {//出的是主牌（也就是自然主）
					for (Card card : cards) {
						if (card.getRank() > 1) {//只能随自然主
							adapterCards.add(card);
						}
					}	
				}			
			}
		} else {//对应于发随牌阶段
			if (currentRank == 0) {//出的是付牌
				for (Card card : cards) {
					if (card.getSuit() == currentSuit ||
							card.getRank() == 0) {//同花色的付牌
						adapterCards.add(card);
					}
				}	
			} else {//出的是主牌
				for (Card card : cards) {
					if (card.getRank() > 0) {//只能随主牌
						adapterCards.add(card);
					}
				}	
			}
		}
		
		return adapterCards;
	}
	
	public static List<Card> createCards() {
		List<Card> cards = new ArrayList<>();
		for (int i = 1; i < 5; i ++) {
			for (int j = 2; j < 15; j ++) {				
				Card card = new Card(i,j);
				cards.add(card);
				card = new Card(i,j);//万万不可省略此句，你知道为什么吗？
				cards.add(card);
			}
		}
		for (int i = 20; i < 22; i ++) {
			Card card = new Card(5,i);
			cards.add(card);
			card = new Card(5,i);//
			cards.add(card);
		}
		Collections.shuffle(cards);//洗牌法，即随机打乱顺序
		List<Card> result = cards.subList(0, 26);
		setRankAndSortCards(result);
		return result;
	}
	
	private static void setRankAndSortCards(List<Card> cards) {
		for (Card card : cards) {
			card.setRank(2, 8);//假定当前主花色是2即梅花，当前级牌是8
		}
        sortCards(cards);
	}
}
