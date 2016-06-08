package testeTexto;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Trieteste {
	 private int leafID;
	 private Trieteste[] teste = new Trieteste[26];
	 
	 public Trieteste() {
		 leafID = -1;
	 }
	 
	 public void addChild(int index){
		 teste[index] = new Trieteste();
	 }
	 
	 public void setLeafID(int ID){
		 leafID = ID;
	 }
	 
	 public int getTesteZero() {
		 return leafID;
	 }
	 
	 public Trieteste getChild(int index)
	 {
		 return teste[index];
	 }
	 
	 public int busca(String word)
	 {
		// int index = word.charAt(0) - 65;
		 Trieteste ronaldo = this.getChild(word.charAt(0) -65);
		 
		   for(int i = 1; i< word.length(); i++){
			   if(ronaldo != null){
				   ronaldo = ronaldo.getChild(word.charAt(i) -65);
			   }
			   else return -1;
		   } 
		 
		 return ronaldo.getTesteZero();
	 }
		 /*Trieteste ronaldo = this.getChild(0);
		 do
		 {
			 ronaldo = ronaldo.getChild(index);
			 index = word.charAt(i) - 65;
			 i++;
			 System.out.println(index);
		 }while(ronaldo.getChild(word.charAt(index)) != null);
		 return ronaldo.leafID;*/
	 

	 public static Trieteste getText(String arquivo) throws FileNotFoundException
	 {
		 String word;
		 int ID = 1;
		 Trieteste ronaldo = new Trieteste();
		 Trieteste raiz = ronaldo;
		 try{
			 File nome = new File(arquivo);
			 Scanner in = new Scanner(nome);
			 while(in.hasNextLine())
			 {
				 ronaldo = raiz;
				 word = in.nextLine(); 
				// System.out.println(word);
				 for(int i = 0; i< word.length();i++)
				 {
					 int index = word.charAt(i) - 65;
					 if(ronaldo.getChild(index) == null)
					 {
						 ronaldo.addChild(index);
					 }
					 ronaldo = ronaldo.getChild(index);
				 }
				 ronaldo.setLeafID(ID);
				 System.out.println(ronaldo.getTesteZero());
				 ID++;
			 }
			 in.close();
		 } catch(FileNotFoundException e){
			 
		 }
		
		
		 return raiz;
	 }
	 
	 public static void main(String[] args) throws FileNotFoundException{
		 Trieteste teste =  Trieteste.getText("arquivoTeste");
		 
		 int id = teste.busca("MORIMORI");
		 System.out.println(id);
	 }
}
