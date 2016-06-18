package testeTexto;

import java.awt.FlowLayout;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.text.JTextComponent;

public class testeContainer{
	public static void main(String[] args) {
		 new Xyz();
		
	}
	
}
class Xyz extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Xyz() {
		JPanel painel = new JPanel();
		this.getContentPane().add(painel, "North");
		JTextArea amor = new JTextArea("Our plans include unlimited texting, calling, and data, starting as low as $18.99 per month with no contracts. We even have a free, Wi-Fi only version of TextNow, available for download on your existing device.");
		amor.setLineWrap(true);
		painel.add(amor);
		painel.getComponent(0).addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e){
				//System.out.println(painel.getComponent(0));
			}
			public void mouseReleased(MouseEvent e) {
				String busca = ((JTextComponent) painel.getComponent(0)).getSelectedText();
				Trieteste teste2 = new Trieteste();
				RandomAccessFile arquivo;
				try {
					arquivo = new RandomAccessFile("Peq.bin", "rw");
					ArrayList<Integer> ID = teste2.searchTrie(arquivo, busca);
					// ArrayList<Integer> ID2 = teste2.searchTrie(arquivo, "rena");
					 System.out.println(ID);
					 //System.out.println(ID2);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				 //teste.writeNode(arquivo, teste);
				 
				 
				
			}
		});
		
		setLayout(new FlowLayout());
		setVisible(true);
		setSize(400,400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}