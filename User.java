package testeTexto;

import java.util.StringTokenizer;

public class User {
  protected int ID;
  protected char nome[] = new char[10];
  protected char senha[] = new char[10];
  protected float fluence;
  protected TextosLidos textosLidos[] = new TextosLidos[5];
  protected Palavras palavras[] = new Palavras[10];
  public static int MAXTEXT = 25;
  public User(){
	  ID = -1;
	  int i;
	  for(i = 0; i<textosLidos.length; i++){
		  textosLidos[i] = new TextosLidos();
	  }
	  for(i = 0; i<palavras.length; i++){
		  palavras[i] = new Palavras();
	  }
  }
  
  public User(int ID, String nome, String senha){
    this.ID = ID;
    this.nome = newArray.sizedArray(nome.toCharArray(),10);
    this.senha = newArray.sizedArray(senha.toCharArray(), 10);
  }
  
  public int getID(){
	  return this.ID;
  }
    
  public char[] getNome(){
	  return this.nome;
  }
  
  public char[] getSenha(){
	  return this.senha;
  }
  
  public float getFluence(){
	  return this.fluence;
  }
  
  public TextosLidos getTextosLidos(int i){
	  return textosLidos[i];
  }
  
  public Palavras getPalavras(int i){
	  return palavras[i];
  }
 
  public void setFluence(float fluence){
    this.fluence = fluence;
  }
  public void setID(int ID){
	  this.ID = ID;
  }
    
  public void setNome(char[] nome){
	  //System.arraycopy(nome, 0, this.nome, 0, nome.length);
	 this.nome = nome;
  }
  
  public void setSenha(char[] senha){
	  this.senha = senha;
  }
  
  
  public void setTextosLidos(TextosLidos texto, int i){
	  this.textosLidos[i] = new TextosLidos(texto.getNome(), texto.getData(),texto.getCompreensao(), texto.getDificuldade());
  }
  
  public void setPalavras(Palavras palavra, int i){
	  this.palavras[i] = new Palavras(palavra.getPalavra(), palavra.getDificuldade(), palavra.getProcurada());
  }
 
  
  public void newRead(TextosLidos text){
      //boolean full = true;
	  int i = 0;
	  int length = this.textosLidos.length;
	    
	  while(i<length && this.textosLidos[i].dificuldade != 0 )
		  i++;
	    
	  if(i == length)
	      for(i = 0; i< length-1; i++)
	    	  this.textosLidos[i] = this.textosLidos[i+1];
    
	  this.textosLidos[i] = text;
  }
  
  public void newWord(String word){
	  int i = 0;
	  int length = this.palavras.length;
	  if(word.length()==0) return;
	  short dificuldade = avaliaDificuldade(word);
	    
	  while(i<length && palavras[i].dificuldade != 0){
		  if(ToString.toString((palavras[i].palavra)).equals(word)){
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
		  if(palavras[i].dificuldade != 0){
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

	   String a = ID + "(" + new String(nome) + ")" + " com senha " + new String(senha)+"\r\n"+
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
	  
	  TextosLidos t = new TextosLidos("babaca", "120416", (float)67.5, (short)15);
	  TextosLidos r = new TextosLidos("troxa", "130416", (float)67.5, (short)15);
	  TextosLidos s = new TextosLidos("babaca", "150416", (float)67.5, (short)15);
	  
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
   protected char[] nome = new char[User.MAXTEXT];
   protected float compreensao;
   protected char[] data = new char[6];
   protected short dificuldade;
   
   public TextosLidos(){
      this.compreensao = 0;
      this.dificuldade = 0;
   }
   public TextosLidos(String nome, String data, float compreensao, short dificuldade){
      this.nome = newArray.sizedArray(nome.toCharArray(), User.MAXTEXT);
      this.compreensao = compreensao;
      this.data = newArray.sizedArray(data.toCharArray(), 6);
      this.dificuldade = dificuldade;
   }
   
   public TextosLidos(char[] nome, char[] data, float compreensao, short dificuldade){
	      this.nome = nome;
	      this.compreensao = compreensao;
	      this.data = data;
	      this.dificuldade = dificuldade;
}
   
   public char[] getNome(){
	   return this.nome;
   }
   
   public char[] getData(){
	   return this.data;
   }
   
   public float getCompreensao(){
	   return compreensao;
   }
   
   public short getDificuldade(){
	   return this.dificuldade;
   }
   
   public void setNome(char[] nome){
	   this.nome = nome;
   }
   
   public void setData(char[] data){
	   this.data = data;
   }
   
   public void setCompreensao(float compreensao){
	   this.compreensao = compreensao;
   }
   
   public void setDificuldade(short dificuldade){
	   this.dificuldade = dificuldade;
   }
   
   public int dificuldadeTexto(String texto){
   	
	   int npalavras = 0;  
	   
	   StringTokenizer str = new StringTokenizer((texto), " ");
	   while(str.hasMoreElements())
		   if(str.nextToken() != null)
			   npalavras++;
	   if(npalavras <= 500)
		   this.dificuldade = 1;
	   else if(npalavras > 500 && npalavras <= 1500)
		   this.dificuldade = 2;
	   else if(npalavras > 1500 && npalavras <= 2500)
		   this.dificuldade = 3;
	   else if(npalavras > 3500 && npalavras <= 4500)
		   this.dificuldade = 4;
	   else if(npalavras >= 4500)
		   this.dificuldade = 5;
		
	   return npalavras;
   }
   
   public void calculaCompreensao(int totalPalavras, int palavrasProcuradas){
	   	
	   float entendimento = 1 - (float) palavrasProcuradas/totalPalavras;
	   float fatorDificuldade = 1 + (int)this.dificuldade*(float)0.01;
	   
	   this.compreensao = 100 * entendimento * fatorDificuldade;
   }
   
   @Override
   public String toString(){

	   String a = String.valueOf(nome) + " em " + String.copyValueOf(data) + " com " + compreensao + "% do texto de dificuldade "+dificuldade+" compreendido.";
	   
	   return a;	
	}
}

class Palavras{
	protected int dificuldade;
	protected int procurada;
	protected char palavra[] = new char[30];

    public Palavras(){
      this.dificuldade = 0;
      this.procurada = 0;
    }
    
    public Palavras(String palavra, int dificuldade, int procurada){
      this.dificuldade = dificuldade;
      this.procurada = procurada;
      this.palavra = newArray.sizedArray(palavra.toCharArray(), 30);
    }
    
    public Palavras(char[] palavra, int dificuldade, int procurada){
        this.dificuldade = dificuldade;
        this.procurada = procurada;
        this.palavra = palavra;
      }
    
    public int getDificuldade(){
    	return this.dificuldade;
    }
    
    public int getProcurada(){
    	return this.procurada;
    }
    
    public char[] getPalavra(){
    	return this.palavra;
    }
    public void setDificuldade(int dificuldade){
    	this.dificuldade = dificuldade;
    }
    
    public void setProcurada(int procurada){
    	this.procurada = procurada;
    }
    
    public void setPalavra(char[] palavra){
    	this.palavra = palavra;
    }
    
    @Override
    public String toString(){

 	   String a = String.valueOf(palavra) + " procurada " + procurada + " vezes de dificuldade " + dificuldade;
 	   
 	   return a;	
 	}
}

class newArray{
	  public static char[] sizedArray(char[] Array, int size){
		  char[] newArray = new char[size];
		  int i;
		  for(i=0; i<Array.length; i++){
			  newArray[i] = Array[i];
		  }
		  for(; i<size; i++){
			  newArray[i] = 0;
		  }
		  return newArray; 
	  }
}
