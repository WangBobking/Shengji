package com.dfsebook.bean;

import java.util.ArrayList;
import java.util.List;

import com.dfsebook.util.CardUtil;

public class Sister extends CardType {
	
	/** 
	* @Title: distinguish
	* @Description:判别发牌者出的扑克牌是否是姊妹对
	* @param @param cards 发牌者出的牌
	* @param @param controlled 是否处于主付不能连状态
	* @param @return    
	* @return Sister    返回姊妹对或空
	* @throws
	 */
	public static Sister distinguish(List<Card> cds, boolean controlled) {
		List<Card> cards = new ArrayList<Card>(cds);
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
	* 思路是这样的：单纯按照face排序,如果所有牌组成姊妹对，那么
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
	
	/** 
	* @Title: find
	* @Description: 在一定范围内查找所有姊妹对
	* 思路是这样的：
	* 1.首先找出所有对牌，并排序
	* 2.只要相邻的对牌花色一致，牌面值连续，就构成姊妹对
	* 3.由于随牌时只能随一致的牌型，所以当发牌为6张姊妹对如667788，我们只能随6张姊妹对，4张的舍弃
	*   但是对于8张的姊妹对我们就可以把它拆分为2个6张姊妹对，如991010JJQQ，拆分为991010JJ和1010JJQQ，
	*   所以我们只需找出等于和大于发牌张数的姊妹对，以备以后拆分即可
	* 4.查找过程使用了遍历和递归
	* @param @param cards
	* @param @param currentSuit
	* @param @param currentRank
	* @param @return    设定文件
	* @return List<Sister>    返回类型
	* @throws
	 */
	public static List<Sister> find(List<Card> cards, boolean controlled, 
			int trumpSuit, int currentSuit, int currentSize) {
		List<Sister> sisters = new ArrayList<Sister>();
		List<Pair> pairs = Pair.find(cards, controlled, trumpSuit, currentSuit);
		Pair.sortPairs(pairs);
		nomalFind(sisters, pairs,currentSize);
		convertATo1(pairs);
		nomalFind(sisters, pairs,  currentSize);
		//这样处理以后可能会出现重复现象。比如出牌包含有2233AA时就会出现姊妹对AA2233和2233两个姊妹对，
		//又如包含有22QQKKAA时会出现QQKKAA，QQKK，AA22三个姊妹对，对此在以后提示功能中做一下去重操作就好。
		return sisters;
	}
	
	private static void convertATo1(List<Pair> pairs) {
		//将对A的face由14改为1重新查找
		for (Pair pair : pairs) {
			if (pair.getFace() == 14) {
				pair.setFace(1);
			}
		}
		//重新对pairs排序，在Pair类中写个sortPairs方法
		Pair.sortPairs(pairs);
	}
	
	private static List<Pair> tPairs;//	

	private static void nomalFind(List<Sister> sisters, List<Pair> pairs, int currentSize) {
		for (int i = 0; i < pairs.size(); i ++) {
			Pair pair = pairs.get(i);
			tPairs = new ArrayList<Pair>();//一个临时的集合存放符合条件的Pair
			tPairs.add(pair);//预先加入该Pair
			deepFind(pairs, pair, i);
			if (tPairs.size() >= currentSize / 2) {//符合条件的对的个数大于等于发牌姊妹对牌的张数除2时
				i += tPairs.size() - 1;//循环变量前移到该处
				Sister sister = formSisterFromPairs();
				if (!sisters.contains(sister)) {//判断集合中是否包含该姊妹对，为此要重写CardType类的equals
					sisters.add(sister);//作用就是对A变为对1时，不要重复加入其它姊妹对
				}
			}
		}
	}
	
	private static void deepFind(List<Pair> pairs, Pair pair, int index) {
		if (index < pairs.size() - 1) {//搜索深度到此即结束搜索
			Pair temPair = pairs.get(index + 1);//得到下一个对
			//如果该对和上一个对花色一致，牌面值等于上一对牌面值加1
			//此两对就符合构成姊妹对的条件，一同加入临时集合中
			if (temPair.getSuit() == pair.getSuit() && 
					temPair.getFace() == pair.getFace() + 1) {
				tPairs.add(temPair);
				deepFind(pairs, temPair, index + 1);//符合条件就进行新一轮深度搜索
			}
			//如果相邻的两对不符合条件则退出递归，进入下一轮循环
		}
	}
	
	private static Sister formSisterFromPairs() {
		List<Card> cards = new ArrayList<Card>();
		for (Pair pair : tPairs) {
			cards.addAll(pair.getCards());
		}
		Sister sister = new Sister();
		sister.setFace(tPairs.get(0).getFace());
		sister.setSuit(tPairs.get(0).getSuit());
		sister.setRank(tPairs.get(0).getRank());
		sister.setCards(cards);
		return sister;
	}
	
}
