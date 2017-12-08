package com.dfsebook.run;

import java.util.ArrayList;
import java.util.List;

import com.dfsebook.bean.Card;
import com.dfsebook.bean.Sister;

public class Test {

	public static void main(String[] args) {
		List<Card> cards = new ArrayList<Card>();
		
		cards.add(new Card(1, 3));
		cards.add(new Card(1, 3));
		cards.add(new Card(1, 14));
		cards.add(new Card(1, 14));
		cards.add(new Card(1, 2));
		cards.add(new Card(1, 2));
		for (Card card : cards) {
			card.setRank(0, 0);
		}
		Sister sister = Sister.distinguish(cards, false);
		if (sister != null) {
			System.out.println("YES, FACE'S " + sister.getFace() + " LENGTH'S " + sister.getCards().size());
		} else {
			System.out.println("NO!!!");
		}
	}

}
