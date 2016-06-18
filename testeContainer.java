package testeTexto;

import java.awt.FlowLayout;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.JTextComponent;

public class testeContainer{
	public static void main(String[] args) {
		Xyz obj = new Xyz();
		
	}
	
}
class Xyz extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Xyz(){
		JPanel painel = new JPanel();
		this.getContentPane().add(painel, "North");
		painel.add(new JTextArea("oi"));
		painel.getComponent(0).addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e){
				//System.out.println(painel.getComponent(0));
			}
			public void mouseReleased(MouseEvent e){
				System.out.println(((JTextComponent) painel.getComponent(0)).getSelectedText());
			}
		});
		
		setLayout(new FlowLayout());
		setVisible(true);
		setSize(400,400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}