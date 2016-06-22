package testeTexto;

import java.util.StringTokenizer;

public class User {
  public static int MAXTEXT = 25;	//constante para tamanho máximo do nome do texto
  public static int MAXWORDS = 20;	//constante para tamanho máximo da lista de palavras salva que um usuário buscou
  
  protected int ID;		//ID do usuário
  protected char nome[] = new char[10];	//nome do Usuário
  protected char senha[] = new char[10];//senha do usuário
  protected float fluence;	//fluencia do usuário
  protected TextosLidos textosLidos[] = new TextosLidos[5];	//array de textos do usuário
  protected Palavras palavras[] = new Palavras[MAXWORDS];	//array de palavras do usuário
  
  public User(){
	  ID = -1;	//não tem ID se ele foi inicializado sem parâmetros
	  int i;
	  for(i = 0; i<textosLidos.length; i++){	//inicializa textos lidos pelo usuário
		  textosLidos[i] = new TextosLidos();
	  }
	  for(i = 0; i<palavras.length; i++){
		  palavras[i] = new Palavras();		//inicializa palavras buscadas pelo usuário
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
					i--;
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
  
  
  private short avaliaDificuldade(String word){ 	//avaliamos a dificuldade pelo número de letras - número de vogais
	  
	  short cont = 0;
	  for(int i = 0; i<word.length(); i++)
		  if(word.charAt(i) == 'a' || word.charAt(i) == 'e' || word.charAt(i) == 'i' || 
		     word.charAt(i) == 'o' || word.charAt(i) == 'u')
			  	cont--;
	  return (short)(cont + word.length()); 
  }  
  
}

class TextosLidos{
   protected char[] nome = new char[User.MAXTEXT];	//nome do texto
   protected float compreensao;		//compreensao do texto em função das palavras que o usuário buscou e a dificuldade do texto
   protected char[] data = new char[6];	//data de quando o texto foi lido
   protected short dificuldade;	//dificuldade do texto baseada no número de palavras do texto
   
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
	   
	   if(100 * entendimento * fatorDificuldade >= 100)
		   this.compreensao = 100 * entendimento;
	   else
		   this.compreensao = 100 * entendimento * fatorDificuldade;
   }
   
   @Override
   public String toString(){

	   String a = String.valueOf(nome) + " em " + String.copyValueOf(data) + " com " + compreensao + "% do texto de dificuldade "+dificuldade+" compreendido.";
	   
	   return a;	
	}
}

class Palavras{
	protected int dificuldade;	//dificuldade da palavra em função do número de letras e vogais
	protected int procurada;	//quantas vezes ela foi procurada
	protected char palavra[] = new char[30];	//a palavra em si

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

class newArray{		//classe para reescrever um array com um tamanho determinado. É utilizado para converter uma string no código
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
