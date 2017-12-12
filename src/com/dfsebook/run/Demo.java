package com.dfsebook.run;

import java.awt.Container;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import com.dfsebook.bean.Card;
import com.dfsebook.util.CardUtil;


public class Demo extends JFrame{

	private static final long serialVersionUID = -7895532137821175642L;

	private Container container;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable(){ 
			@Override
			public void run() {
				try{
					new Demo();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		});
	}

	public Demo() {
		setDefaultLookAndFeelDecorated(true);
        setTitle("ShengjiApp");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 350);
        setLocationRelativeTo(null);
        setLayout(null);
        container = getContentPane();
        setVisible(true);

        List<Card> cards = demoCards();//CardUtil.createCards();
        ShowCards sc = new ShowCards(cards);
        sc.setBounds(45, 120, 900, 170);
        container.add(sc);
	}
	
	private static List<Card> demoCards() {
		List<Card> cards = new ArrayList<Card>();
		cards.add(new Card(1, 6));
		cards.add(new Card(1, 6));
		cards.add(new Card(1, 10));
		cards.add(new Card(1, 10));
		cards.add(new Card(1, 11));
		cards.add(new Card(1, 11));
		cards.add(new Card(2, 9));
		cards.add(new Card(3, 10));
		cards.add(new Card(4, 6));
		cards.add(new Card(3, 3));
		cards.add(new Card(1, 5));
		cards.add(new Card(1, 5));
		cards.add(new Card(2, 5));
		cards.add(new Card(4, 5));
		for (Card card : cards) {
			card.setRank(0, 14);
		}
		return cards;
	}
}
