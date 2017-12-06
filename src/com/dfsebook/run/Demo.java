package com.dfsebook.run;

import java.awt.Container;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JFrame;

import com.dfsebook.bean.Card;


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

        List<Card> cards = createCards();
        setRankAndSortCards(cards);
        ShowCards sc = new ShowCards(cards);
        sc.setBounds(45, 120, 900, 170);
        container.add(sc);
	}
	
	private List<Card> createCards() {
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
		return cards.subList(0, 26);
	}
	
	private void setRankAndSortCards(List<Card> cards) {
		for (Card card : cards) {
			card.setRank(2, 8);//假定当前主花色是2即梅花，当前级牌是8
		}
        Card.sort(cards);
	}
}
