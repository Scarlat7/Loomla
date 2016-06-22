package testeTexto;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.io.RandomAccessFile;
import java.util.StringTokenizer;
import java.util.ArrayList;
public class Trieteste{
	 /**
	 *
	 */
	 protected final static int MAX = 61; //tamanho m�ximo do dicion�rio
	 //protected final static int IDMAX = 4; //tamanho m�ximo de �ndices
	 private ArrayList<Integer> leafID = new ArrayList<>();
	 private Trieteste[] teste = new Trieteste[MAX];

	 public void addChild(int index){	//adiciona filho para um �ndice
		 teste[index] = new Trieteste();
	 }

	 public void setLeafID(int ID){	//insere ID no nodo.
		 leafID.add(ID);
	 }

	 public ArrayList<Integer> getLeafID() {	//recebe IDs do nodo.
		 return leafID;
	 }

	 public Trieteste getChild(int index){	//recebe filho do nodo
		 return teste[index];
	 }

	 	/*	charToIndex(int)
	 	 * 	esse m�todo recebe um char da palavra
		 *	e retorna qual o �ndice deste char no nodo.
		 */
	 
	 public static int charToIndex(int letra){	
		    int index;
		    if(letra <= 122 && letra >=97) index  = letra - 97;	// intervalo a-z
		    else if(letra >= 223 && letra <=252) index  = letra - 197; //intervalo �-�
		    else switch(letra){
		      case 32: index = letra +24; break; // corresponde ao espa�o
		      case 33: index = letra +24; break; // corresponde a exclama��o (usado para identifica��o da l�ngua)
		      case 45: index = letra +13; break; // corresponde ao h�fen
		      case 63: index = letra -4; break;  // corresponde a interroga��o (usado para identifica��o da l�ngua)
		      case 94: index = letra -34; break; // corresponde ao circunflexo  (usado para identifica��o da l�ngua)
		      default: index = -1; break;	//correspondente ao char fora do intervalo
		    }
		    return index;
		}
	 
	 	/* writeNewNode
	 	 * recebe o arquivo e lugar de inser��o de um novo nodo.
	 	 * Escreve um nodo vazio no arquivo.
	 	 */

		public static void writeNewNode(long pointer, RandomAccessFile arquivo) throws IOException{
		    arquivo.seek(pointer);
		    for(int j = 0; j < MAX; j++) {
		      arquivo.writeInt(0);
		    }
		    for(int j = 0; j < 4; j++){
		      arquivo.writeInt(-1);
		    }
		}
	 
		/* addToTrie
		 * adiciona uma string e uma ID para o arquivo Trie correspondente
		 * IDMAX � o tamanho da lista de IDs, necess�ria para calcular deslocamentos
		 * � salvo no �ndice do nodo a posi��o do filho no arquivo
		 * */
		
	 public void addToTrie(String word, int ID, RandomAccessFile arquivo, int IDMAX) throws IOException{
	     long tam;
	     int nodoAtual=0, cont=0, aux2=0, i=0, index = 0; 
	     boolean range = true; //vari�vel para ver se  um caracter � inv�lido
	     
	     nodoAtual = MAX*4+IDMAX*4;	//aux = tamanho de um nodo 
	     
	     if(arquivo.length()==0) writeNewNode(0, arquivo); //se � a primeira inser��o, � preciso criar um primeiro nodo vazio
	     
	     while(i< word.length() && range){		//loop pegando todos caracteres verificando se eles est�o no dicion�rio
	       index = charToIndex(word.charAt(i));	
	       if(index == -1) range = false;	//se est� fora do intervalo, range � falso
	       if(range){
	         arquivo.seek(nodoAtual-(MAX+IDMAX)*4+index*4);	//estando no intervalo, lemos o que est� salvo no �ndice do nodo atual
	        
	         aux2 = nodoAtual;	//salva o nodo atual em uma vari�vel
	         
	         tam = arquivo.length(); 
	         if((nodoAtual = arquivo.readInt())== 0){	//nodoAtual recebe a posi��o do filho da letra correspondente, se for 0 n�o tem filho
	            writeNewNode(tam, arquivo);	
	            arquivo.seek(aux2 - (MAX+IDMAX)*4+index*4); //move o ponteiro do arquivo para o �ndice
	            arquivo.writeInt((int)arquivo.length());	//aqui � salva a posi��o do filho criado no �ndice atual
	            nodoAtual = (int)arquivo.length();		//nodoAtual � filho criado agora e assim sucessivamente
	         }
	         i++;
	       }
	     }
	     if(i == word.length()){	
	        nodoAtual = nodoAtual-4*IDMAX;	//chegou no �ltimo nodo da palavra, agora � salva a ID
	        arquivo.seek(nodoAtual);
	        while(cont <IDMAX &&(index = arquivo.readInt())!= -1 && index != ID)	cont++;	//salva no fim da lista de IDs
	       
	        if(cont < IDMAX && index != ID){		
	          arquivo.seek(arquivo.getFilePointer()-4);	//o ponteiro est� numa casa a mais do que a posi��o que queremos salvar, ent�o volta um inteiro 
	          arquivo.writeInt(ID);
	       }
	       cont = 0;
	   
	     }
	     else{
	      range = true;
	     } 
	}
	 
	 /* searchTrie
		 * busca uma string e retorna uma ID para o arquivo Trie correspondente
		 * IDMAX � o tamanho da lista de IDs, necess�ria para calcular deslocamentos
		 * � preciso saber a l�ngua da busca para concatenar o token correspondente a l�ngua na string
		 * */
		
	 
	 
	 public ArrayList<Integer> searchTrie(String fileName, String word, String lingua, int IDMAX) throws FileNotFoundException{
		RandomAccessFile arquivo = new RandomAccessFile(fileName, "rw");
		char token; int cont = 0; int IdAux = 0;	
		word = word.toLowerCase();	//o alfabeto t� em letra min�scula
		switch(lingua){				//primeiro letra da string tem que ser um token identificador de que l�ngua a busca � feita
			case "Portugu�s": token = '?'; break;	
			case "Ingl�s": token = '^'; break;
			case "Alem�o": token = '!'; break;
			default: token = 0;
		}
		
		if(token != 0)
			word = token + word;	
		
	 	ArrayList<Integer> idList = new ArrayList<>(); //lista que ser� retornada
		int index = 4*(IDMAX+MAX);	//come�a a busca no primeiro nodo
		int indice = 0;
		int letra;
	 	try{
	 		//arquivo.seek(0);
	 		for(int i = 0; i< word.length(); i++){
	 			letra = word.charAt(i);
	 			indice= charToIndex(letra);	//calcula o �ndice correspondente
	 			
	 			if(letra != -1 && index > 0){
	 				arquivo.seek(index - 4*(IDMAX+MAX) + 4*(indice));	//l� a posi��o do filho
		 			index = arquivo.readInt();							//
	 			} else index = -1;
		 		
	 			//System.out.println(index);
	 		}
	 		if(index > 4*IDMAX){ 			//essa condi��o � necess�ria para que o pr�ximo Seek n�o fique negativo
	 			arquivo.seek(index-4*IDMAX);	//pega todas as IDs e retorna numa lista
				for(int i=0; i<IDMAX; i++){
					if((IdAux = arquivo.readInt()) == -1) cont++;
					idList.add(IdAux);
				}
				if(cont < 4)return idList;
				else return null;
	 		} else return null;
	 		
	  }catch (IOException e) {
	     System.out.println("Error Search");
		}
	 	return null;
	}
	 /*	getText
	  * esse � o m�todo utilizado para minerar o arquivo texto e adicionar
	  * dados na Btree e na Trie
	  * */
	 public static void getText(String textFile, int IDMAX) throws IOException{
		 String word;
		 int ID = 1;
		 BNode german = new BNode(true, "Alem�o");		//cria a pasta para as BTrees com significados
		 BNode port = new BNode(true, "Portugu�s");		
		 BNode english = new BNode(true, "Ingl�s");
		 
		 Trieteste ronaldo = new Trieteste();
		 try{
			 RandomAccessFile arquivo = new RandomAccessFile("dados//Words.data", "rw");	//arquivo destino da Trie
			 
			 try{
				 File nome = new File(textFile);	
				 Scanner in = new Scanner(nome);
				 
				 while(in.hasNextLine()){	//enquanto o arquivo texto tem mais linhas para minera��o
						
					 	word = in.nextLine();	//pega a linha
						StringTokenizer str = new StringTokenizer(word, ",");	//as palavras s�o separadas com v�rgulas
						
						while(str.hasMoreElements()){	
				
							String strAux = str.nextToken();
							ronaldo.addToTrie(strAux, ID, arquivo, IDMAX);	//adiciona a string na trie	
							switch(strAux.charAt(0)){
								case '^': english = english.insert(ID, strAux, "Ingl�s"); //ID correspondente ao significado da palavra adicionado no dicion�rio correspondente a l�ngua
										  break;
								case '?': port = port.insert(ID, strAux, "Portugu�s");
										  break;
								case '!': german = german.insert(ID, strAux, "Alem�o");
										  break;
								default:  break;
							}
						}
						ID++;
				 }
				 in.close();
			 }catch(IOException e){System.out.println("Socorro1");}
		 }catch(IOException e){System.out.println("socorro2");} 
		}

	 public static void main(String[] args) throws IOException{
	     //Trieteste.getText("leozin", 4);
		 try {
			 BNode ingles = new BNode("Ingl�s");
			 BNode port = new BNode("Portugu�s");
			 BNode german = new BNode("Alem�o");
			 Trieteste teste2 = new Trieteste();
			 ArrayList<Integer> ID = teste2.searchTrie("dados//words.data", "floresta��o", "Portugu�s", 4);
			 System.out.println(ID);
			 ArrayList<String> translations = ingles.getTranslations(ID.get(0),"Ingl�s");
		 System.out.println(translations);
		 } catch (FileNotFoundException e1) {
             e1.printStackTrace();
     }// System.out.println(ID2);
	 }
}