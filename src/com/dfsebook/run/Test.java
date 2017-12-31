package com.dfsebook.run;

import java.util.ArrayList;
import java.util.List;

import com.dfsebook.bean.Card;
import com.dfsebook.bean.Rain;
import com.dfsebook.bean.Snow;
import com.dfsebook.bean.Thunder;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<Card> cards = new ArrayList<Card>();
		Card card = new Card(1, 14);
		card.setRank(2, 6);
		cards.add(card);
		card = new Card(4, 4);
		card.setRank(2, 6);
		cards.add(card);
		card = new Card(1, 12);
		card.setRank(2, 6);
		cards.add(card);
		card = new Card(1, 4);
		card.setRank(2, 6);
		cards.add(card);
		card = new Card(1, 7);
		card.setRank(2, 6);
		cards.add(card);
		card = new Card(1, 10);
		card.setRank(2, 6);
		cards.add(card);
		card = new Card(1, 13);
		card.setRank(2, 6);
		cards.add(card);
		card = new Card(2, 13);
		card.setRank(2, 6);
		cards.add(card);
		Snow snow = Snow.find(cards, 2, 1);
		if (snow != null)
			System.out.println(snow.toString());
		else
			System.out.println("no!");
	}

}
