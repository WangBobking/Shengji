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
}
