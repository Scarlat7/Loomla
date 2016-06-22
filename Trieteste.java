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
	 protected final static int MAX = 61;
	 //protected final static int IDMAX = 4; //tamanho máximo de índices
	 private ArrayList<Integer> leafID = new ArrayList<>();
	 private Trieteste[] teste = new Trieteste[MAX];

	 public void addChild(int index){
		 teste[index] = new Trieteste();
	 }

	 public void setLeafID(int ID){
		 leafID.add(ID);
	 }

	 public ArrayList<Integer> getLeafID() {
		 return leafID;
	 }

	 public Trieteste getChild(int index){
		 return teste[index];
	 }

	 public static int charToIndex(int letra){
		    int index;
		    if(letra <= 122 && letra >=97) index  = letra - 97;
		    else if(letra >= 223 && letra <=252) index  = letra - 197;
		    else switch(letra){
		      case 32: index = letra +24; break; //56
		      case 33: index = letra +24; break; //57
		      case 45: index = letra +13; break; //58
		      case 63: index = letra -4; break;  //59
		      case 94: index = letra -34; break; //60
		      default: index = -1; break;
		    }
		    return index;
		}

		public static void writeNewNode(long pointer, RandomAccessFile arquivo) throws IOException{
		    arquivo.seek(pointer);
		    for(int j = 0; j < MAX; j++) {
		      arquivo.writeInt(0);
		    }
		    for(int j = 0; j < 4; j++){
		      arquivo.writeInt(-1);
		    }
		}
	 
	 public void addToTrie(String word, int ID, RandomAccessFile arquivo, int IDMAX) throws IOException{
	     long tam;
	     int aux=0, cont=0, aux2=0, i=0, index = 0; //aux2 vai ser usado como 
	     boolean range = true;
	     aux = MAX*4+IDMAX*4;
	     if(arquivo.length()==0)
		 {
			writeNewNode(0, arquivo);
		 }
	     while(i< word.length() && range){
	       index = charToIndex(word.charAt(i));
	       if(index == -1) range = false;
	       if(range){
	         arquivo.seek(aux-(MAX+IDMAX)*4+index*4);
	         aux2 = aux;
	         tam = arquivo.length();
	         if((aux = arquivo.readInt())== 0){
	            writeNewNode(tam, arquivo);
	            arquivo.seek(aux2 - (MAX+IDMAX)*4+index*4);
	            arquivo.writeInt((int)arquivo.length());
	            aux = (int)arquivo.length();
	         }
	         i++;
	       }
	     }
	     if(i == word.length()){
	        aux = aux-4*IDMAX;
	        arquivo.seek(aux);
	        while(cont <IDMAX &&(index = arquivo.readInt())!= -1 && index != ID)	cont++;
	       
	        if(cont < IDMAX && index != ID){
	          arquivo.seek(arquivo.getFilePointer()-4);
	          arquivo.writeInt(ID);
	       }
	       cont = 0;
	   
	     }
	     else{
	      range = true;
	      //System.out.println("Caracter não reconhecido");
	     } 
	}
	 
	 public ArrayList<Integer> searchTrie(String fileName, String word, String lingua, int IDMAX) throws FileNotFoundException{
		RandomAccessFile arquivo = new RandomAccessFile(fileName, "rw");
		char token; int cont = 0; int IdAux = 0;
		word = word.toLowerCase();
		switch(lingua){
			case "Português": token = '?'; break;
			case "Inglês": token = '^'; break;
			case "Alemão": token = '!'; break;
			default: token = 0;
		}
		//if(token == 0)return null;
		if(token != 0)
			word = token + word;
	 	ArrayList<Integer> idList = new ArrayList<>();
		int index = 4*(IDMAX+MAX);
		int indice = 0;
		int letra;
	 	try{
	 		//arquivo.seek(0);
	 		for(int i = 0; i< word.length(); i++){
	 			letra = word.charAt(i);
	 			if(word.charAt(i) <= 122 && word.charAt(i)>=97) indice = letra - 97;
				 else if(word.charAt(i) >= 223 && word.charAt(i)<=252) indice = letra - 197;
					 else switch(letra){
					 		case 32: indice = letra +24; break; //56
					 		case 33: indice = letra +24; break; //57
					 		case 45: indice = letra +13; break; //58
					 		case 63: indice = letra -4; break;  //59
					 		case 94: indice = letra -34; break; //60
						 	default: indice = -1; break;
					 }
	 			if(letra != -1 && index > 0){
	 				arquivo.seek(index - 4*(IDMAX+MAX) + 4*(indice));
		 			index = arquivo.readInt();
	 			} else index = -1;
		 		
	 			//System.out.println(index);
	 		}
	 		if(index > 4*IDMAX){
	 			arquivo.seek(index-4*IDMAX);
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

	 public static void getText(String textFile, int IDMAX) throws IOException{
		 String word;
		 int ID = 1;
		 BNode german = new BNode(true, "Alemão");
		 BNode port = new BNode(true, "Português");
		 BNode english = new BNode(true, "Inglês");
		 
		 Trieteste ronaldo = new Trieteste();
		 try{
			 RandomAccessFile arquivo = new RandomAccessFile("Words.data", "rw");
			 if(arquivo.length()==0)
			 {
				writeNewNode(0, arquivo);
			 }
			 try{
				 File nome = new File(textFile);
				 Scanner in = new Scanner(nome);
				 
				 while(in.hasNextLine())
				 {
						word = in.nextLine();
						StringTokenizer str = new StringTokenizer(word, ",");
						while(str.hasMoreElements())
						{
							
							String strAux = str.nextToken();
							//System.out.println(strAux);
							//System.out.println(strAux);
							ronaldo.addToTrie(strAux, ID, arquivo, IDMAX);
							switch(strAux.charAt(0)){
								case '^': english = english.insert(ID, strAux, "Inglês"); 
										  //System.out.println(english.getTranslations(1, "Inglês"));
										  break;
								case '?': port = port.insert(ID, strAux, "Português");
										  break;
								case '!': german = german.insert(ID, strAux, "Alemão");
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
	    // Trieteste.getText("arquivoTeste", 4);
		 try {
		 BNode ingles = new BNode("Inglês");
		 BNode port = new BNode("Português");
		 BNode german = new BNode("Alemão");
		 //ingles.insert(1, "!ácido", "Inglês");
		// System.out.println(ingles.getTranslations(1, "Inglês"));
		 Trieteste teste2 = new Trieteste();
		 //teste2.addToTrie("ronaldo", 4, arquivo);
		 ArrayList<Integer> ID = teste2.searchTrie("words.data", "rei", "Português", 4);
		 //ArrayList<Integer> ID2 = teste2.searchTrie("Eusei", "publicidade", "Português", 4);

		 ArrayList<String> translations = port.getTranslations(ID.get(0),"Alemão");
		 System.out.println(ID);
		 System.out.println(translations);
		 } catch (FileNotFoundException e1) {
             e1.printStackTrace();
     }// System.out.println(ID2);
	 }
}