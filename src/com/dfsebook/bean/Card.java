package com.dfsebook.bean;


public class Card {

	private int suit;
	
	private int face;
	
	private int rank;
	
	public Card(int suit, int face) {
		this.suit = suit;
		this.face = face;
	}

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

	public void setRank(int trump_suit,int grade_face) {
		if (face == 2) 
			rank = 2;
		if (face == 3)
			rank = 4;
		if (face == 5) 
			rank = 6;
		if (face == grade_face)
			rank = 8;
		if (face == 20)
			rank = 10;
		if (face == 21)
			rank = 11;
		if (suit == trump_suit)
			rank += 1;			
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Card) {
			Card card = (Card)obj;
			if (card.suit == suit && card.face == face)
				return true;
		}
		return false;
	}
	
}
