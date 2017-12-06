package com.dfsebook.run;

import java.awt.Graphics;
import java.awt.Image;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import com.dfsebook.bean.Card;

public class ShowCards extends JPanel {

	private static final long serialVersionUID = -1632266864726184289L;

	private List<Card> cards;

	public ShowCards(List<Card> cards) {
		this.cards = cards;
	}

	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(getBackground());
		g.fillRect(0, 0, getWidth(), getHeight());
		int width = getWidth();
		int w = width - 105 - 20*(cards.size() - 1);
		w = w/2;//保证所有扑克显示到窗体水平居中位置
		int span = 0;//相邻两张扑克间隔距离
		try {			
			for (int j = 0;  j < cards.size(); j ++) {
				Card card = cards.get(j);
				String name = card.getFace() < 10 ? "0" + card.getFace() : "" + card.getFace();
				name = card.getSuit() + name;
				String path = "images/" + name + ".jpg";
				Image im = ImageIO.read(new FileInputStream(path));
				g.drawImage(im, span + w, 20, 105, 150, this);
				span += 20;
			}
			g = null;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
