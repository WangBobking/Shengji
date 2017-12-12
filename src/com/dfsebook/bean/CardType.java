package com.dfsebook.bean;

import java.util.ArrayList;
import java.util.List;

public class CardType {

	private int suit;
	
	private int face;
	
	private int rank;
	
	private List<Card> cards;

	public int getSuit() {
		return suit;
	}

	public void setSuit(int suit) {
		this.suit = suit;
	}

	public int getFace() {
		return face;
	}

	public void setFace(int face) {
		this.face = face;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public List<Card> getCards() {
		return cards;
	}

	public void setCards(List<Card> cards) {
		this.cards = cards;
	}
	
	public void addCard(Card card) {
		if (cards == null)
			cards = new ArrayList<Card>();
		cards.add(card);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof CardType) {
			CardType ct = (CardType)obj;
			if (ct.getCards().equals(cards)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName() + " ");
		if (getSuit() == 1)
			sb.append("Diamonds");
		else if (getSuit() == 2)
			sb.append("Clubs");
		else if (getSuit() == 3)
			sb.append("Hearts");
		else
			sb.append("Spades");
		sb.append(" ");
		int size = cards.size();
		if (this instanceof Pair || this instanceof Sister) {
			size = cards.size() / 2;
			for (int index = 0; index < size; index ++) {
				sb.append(face + index);
				sb.append(face + index);
			}
		} else {
			for (int index = 0; index < size; index ++) {
				sb.append(face + index);
			}
		}
		return sb.toString();
	}
	
}
