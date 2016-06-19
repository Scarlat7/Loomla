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
	 protected final static int MAX = 60;
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
		      case 32: index = letra +24; break;
		      case 45: index = letra +12; break;
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
	      System.out.println("Caracter não reconhecido");
	     } 
	}

	 public ArrayList<Integer> searchTrie(String fileName, String word, int IDMAX) throws FileNotFoundException{
		RandomAccessFile arquivo = new RandomAccessFile(fileName, "rw");
	 	ArrayList<Integer> idList = new ArrayList<>();
		int index = 4*(IDMAX+MAX);
		int subtrai = 0;
	 	try{
	 		//arquivo.seek(0);
	 		for(int i = 0; i< word.length(); i++){
	 			if(word.charAt(i) <= 122 && word.charAt(i)>=97) subtrai = 97;
				 else if(word.charAt(i) >= 223 && word.charAt(i)<=252) subtrai = 197;
					 else switch(word.charAt(i)){
						 	case ' ': subtrai = -24; break;
						 	case '-': subtrai = -12; break;
						 	default: subtrai = -1; break;
					 }
	 			if(subtrai != -1 && index > 0){
	 				arquivo.seek(index - 4*(IDMAX+MAX) + 4*(word.charAt(i)-subtrai));
		 			index = arquivo.readInt();
	 			} else index = -1;
		 		
	 			//System.out.println(index);
	 		}
	 		if(index > 4*IDMAX){
	 			arquivo.seek(index-4*IDMAX);
				for(int i=0; i<IDMAX; i++){
					idList.add(arquivo.readInt());
				}
				return idList;
	 		} else return null;
	 		
	  }catch (IOException e) {
	     System.out.println("Error Search");
		}
	 	return null;
	}

	 public static void getText(String textFile, int IDMAX) throws FileNotFoundException{
		 String word;
		 int ID = 1;
		 Trieteste ronaldo = new Trieteste();
		 try{
			 RandomAccessFile arquivo = new RandomAccessFile("soco", "rw");
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
						StringTokenizer str = new StringTokenizer(word, "!^,?");
						while(str.hasMoreElements())
						{
							String strAux = str.nextToken();
							//System.out.println(strAux);
							ronaldo.addToTrie(strAux, ID, arquivo, IDMAX);
						}
						ID++;
				 }
				 in.close();
			 }catch(FileNotFoundException e){System.out.println("Socorro1");}
		 }catch(IOException e){System.out.println("socorro2");} 
		}

	 public static void main(String[] args) throws IOException{
	     //Trieteste.getText("arquivoTeste");
		 Trieteste teste2 = new Trieteste();
		 //teste2.addToTrie("ronaldo", 4, arquivo);
		 ArrayList<Integer> ID = teste2.searchTrie("soco", "have", 4);
		 ArrayList<Integer> ID2 = teste2.searchTrie("soco", "dog", 4);
		 
		 System.out.println(ID);

		 System.out.println(ID2);
	 }
}