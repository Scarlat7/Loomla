package testeTexto;

import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

public class User {
  protected int ID;
  protected String nome;
  protected String senha;
  protected float fluence;
  protected TextosLidos textosLidos[] = new TextosLidos[5];
  protected Palavras palavras[] = new Palavras[10];
  
  public User(int ID, String nome, String senha){
    this.ID = ID;
    this.nome = nome;
    this.senha = senha;
  }
  
  public void setFluence(float fluence){
    this.fluence = fluence;
  }
  
  public void newRead(TextosLidos text){
      //boolean full = true;
	  int i = 0;
	  int length = this.textosLidos.length;
	    
	  while(this.textosLidos[i] != null && i<length)
		  i++;
	    
	  if(i == length)
	      for(i = 0; i< length-1; i++)
	    	  this.textosLidos[i] = this.textosLidos[i+1];
    
	  this.textosLidos[i] = text;
  }
  
  public void newWord(String word){
	  int i = 0;
	  int length = this.palavras.length;
	  
	  short dificuldade = avaliaDificuldade(word);
	    
	  while(i<length && palavras[i] != null){
		  if(palavras[i].palavra == word){
			  palavras[i].procurada++;
			  while(i>0 && palavras[i].procurada > palavras[i-1].procurada){
					Palavras aux = palavras[i-1];
					palavras[i-1] = palavras[i];
					palavras[i] = aux;
			  }	
			  return;
		  }
		  i++;
	  }
	    
	  if(i == length){
		  i--;
		  while(i>=0 &&palavras[i].procurada == 1 && palavras[i].dificuldade >= dificuldade)
			  i--;
		  if(palavras[i].procurada == 1)
			  palavras[i] = new Palavras(word, dificuldade, 1);
	  }
	  else{
		  if(palavras[i] != null){
			  i--;
			  while(i>0 && palavras[i].procurada <= 1){
				  palavras[i+1] = palavras[i];
				  i--;
			  }
		  }
		  palavras[i] = new Palavras(word, dificuldade, 1);
		  
	  }
	  
  }
  
  @Override
  public String toString(){

	   String a = ID + "(" + nome + ")" + " com senha " + senha+"\r\n"+
			   "Fluencia " +fluence+ "% \r\n"+
			   "Textos: \r\n";
	   int i = 0;
	   while(i<textosLidos.length && textosLidos[i] != null){
		   a += textosLidos[i] + "\r\n";
		   i++;
	   }
	   
	   a+= "Palavras: \r\n";
	   
	   i = 0;
	   while(i<palavras.length && palavras[i] != null){
		   a += palavras[i] + "\r\n";
		   i++;
	   }
	   
	   return a;	
  }
	  
  
  private short avaliaDificuldade(String word){
	  
	  short cont = 0;
	  for(int i = 0; i<word.length(); i++)
		  if(word.charAt(i) == 'a' || word.charAt(i) == 'e' || word.charAt(i) == 'i' || 
		     word.charAt(i) == 'o' || word.charAt(i) == 'u')
			  	cont--;
	  return (short)(cont + word.length()); 
  }
  
  public static void main(String[] args){
	  
	  User eu = new User(0, "Scarlat7", "hahaha");
	  eu.setFluence(50);
	  
	  TextosLidos t = new TextosLidos("babaca", "12/04/2016", (float)67.5, (short)15);
	  TextosLidos r = new TextosLidos("troxa", "13/04/2016", (float)67.5, (short)15);
	  TextosLidos s = new TextosLidos("babaca", "15/04/2016", (float)67.5, (short)15);
	  
	  eu.newRead(t);
	  eu.newRead(r);
	  eu.newRead(s);  
	  
	  eu.newWord("tata");
	  eu.newWord("tata");
	  eu.newWord("asshole");
	  eu.newWord("still an asshole");
	  eu.newWord("still an esshole");
	  eu.newWord("still en asshole");
	  eu.newWord("still on asshole");
	  eu.newWord("asshole");
	  eu.newWord("asshole");
	  eu.newWord("kkkk");
	  eu.newWord("still in asshole");
	  eu.newWord("still en esshole");
	  eu.newWord("stell en asshole");
	  eu.newWord("stall en asshole");
	  eu.newWord("stoll en asshole");
	  
	  
	  System.out.println(eu);
	  
	     /*Trieteste.getText("arquivoTeste");
		 Trieteste teste2 = new Trieteste();
		 RandomAccessFile arquivo = new RandomAccessFile("Trie.bin", "rw");
		 //teste.writeNode(arquivo, teste);
		 ArrayList<Integer> ID = teste2.searchTrie(arquivo, "have");
		 ArrayList<Integer> ID2 = teste2.searchTrie(arquivo, "rena");
		 
		 System.out.println(ID);

		 System.out.println(ID2);*/
	 }
  
  
}

class TextosLidos{
   protected String nome;
   protected float compreensao;
   protected String data;
   protected short dificuldade;
   public TextosLidos(){
      this.nome = "";
      this.data = "";
      this.compreensao = 0;
      this.dificuldade = 0;
   }
   public TextosLidos(String nome, String data, float compreensao, short dificuldade){
      this.nome = nome;
      this.compreensao = compreensao;
      this.data = data;
      this.dificuldade = dificuldade;
   }
   
   @Override
   public String toString(){

	   String a = nome + " em " + data + " com " + compreensao + "% do texto de dificuldade "+dificuldade+" compreendido.";
	   
	   return a;	
	}
}

class Palavras{
	protected int dificuldade;
	protected int procurada;
	protected String palavra;

    public Palavras(){
      this.dificuldade = 0;
      this.procurada = 0;
      this.palavra = "";
    }
    
    public Palavras(String palavra, int dificuldade, int procurada){
      this.dificuldade = dificuldade;
      this.procurada = procurada;
      this.palavra = palavra;
    }
    
    @Override
    public String toString(){

 	   String a = palavra + " procurada " + procurada + " vezes de dificuldade " + dificuldade;
 	   
 	   return a;	
 	}
}

