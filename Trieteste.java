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
	 protected final static int IDMAX = 4; //tamanho máximo de índices
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

	 public void writeNode(RandomAccessFile arquivo, Trieteste tree){
		try{
			arquivo.seek(arquivo.length());
		 	for(int i = 0; i < MAX; i++) {
		 		arquivo.writeInt(0);
		 	}
			for(int i = 0; i < 4; i++){
				arquivo.writeInt(-1);
			}
		 	long tam = arquivo.length();
		 	if(tree.getLeafID().isEmpty())
			 	for(int i = 0; i < MAX; i++){
			 		if(tree.getChild(i) != null){
			 			arquivo.seek(tam - (MAX+IDMAX)*4+i*4); //esse fseek vai a partir do incio
			 			arquivo.writeInt((int)arquivo.length());
			 			tree.writeNode(arquivo, tree.getChild(i));
			 		}
				}
		 	else{
	 			arquivo.seek(tam-4*IDMAX); //esse fseek vai a partir do incio
				for(int i=0; i< tree.getLeafID().size(); i++){
					arquivo.writeInt(tree.getLeafID().get(i));
				}
	 			
	 			for(int i = 0; i < MAX; i++){
			 		if(tree.getChild(i) != null){
			 			arquivo.seek(tam - (MAX+IDMAX)*4+i*4); //esse fseek vai a partir do incio
			 			arquivo.writeInt((int)arquivo.length());
			 			tree.writeNode(arquivo, tree.getChild(i));
		 			}
			 	}
	 		}
		}catch (IOException e) {
	         System.out.println("Error");
	   }
	 }

	 public ArrayList<Integer> searchTrie(RandomAccessFile arquivo, String word){
	 	ArrayList<Integer> idList = new ArrayList<>();
		int index = 0;
		int subtrai = 0;
	 	try{
	 		arquivo.seek(0);
	 		for(int i = 0; i< word.length(); i++){
	 			if(word.charAt(i) <= 122 && word.charAt(i)>=97) subtrai = 97;
				 else if(word.charAt(i) >= 223 && word.charAt(i)<=252) subtrai = 197;
					 else switch(word.charAt(i)){
						 	case ' ': subtrai = -24; break;
						 	case '-': subtrai = -12; break;
					 }
		 		arquivo.seek(index + 4*(word.charAt(i)-subtrai));
	 			index = arquivo.readInt();
	 		}
	 		arquivo.seek(index+4*MAX);
			for(int i=0; i<IDMAX; i++){
				idList.add(arquivo.readInt());
			}
			return idList;
	  }catch (IOException e) {
	     System.out.println("Error");
		}

	 	return null;

	}

	 public ArrayList<Integer> busca(String word){
		int subtrai=0;
		if(word.charAt(0) <= 122 && word.charAt(0)>=97) subtrai = 97;
		 else if(word.charAt(0) >= 223 && word.charAt(0)<=252) subtrai = 197;
		 	else switch(word.charAt(0)){
			 	case 32: subtrai = -24; break;
			 	case 45: subtrai = -12; break;
			}
		Trieteste ronaldo = this.getChild(word.charAt(0) -subtrai);

		for(int i = 1; i< word.length(); i++){
			if(word.charAt(i) <= 122 && word.charAt(i)>=97) subtrai = 97;
			 else if(word.charAt(i) >= 223 && word.charAt(i)<=252) subtrai = 197;
				 else switch(word.charAt(i)){
					 	case ' ': subtrai = -24; break;
					 	case '-': subtrai = -12; break;
				 }
			if(ronaldo != null){
				ronaldo = ronaldo.getChild(word.charAt(i) -subtrai);
			}
			else return null;
		}

		return ronaldo.getLeafID();
	 }

	 public static Trieteste getText(String arquivo) throws FileNotFoundException{
		 String word;
		 int ID = 1;
		 Trieteste ronaldo = new Trieteste();
		 Trieteste raiz = ronaldo;
		 try{
			 File nome = new File(arquivo);
			 Scanner in = new Scanner(nome);
			 int index = 0;
			 while(in.hasNextLine())
			 {
					word = in.nextLine();
					StringTokenizer str = new StringTokenizer(word, "!^,?");
					while(str.hasMoreElements())
					{
						ronaldo = raiz;
						String strAux = str.nextToken();
						for(int i = 0; i< strAux.length(); i++)
	 				  {
							if(strAux.charAt(i) <= 122 && strAux.charAt(i)>=97) index  = strAux.charAt(i) - 97;
							else if(strAux.charAt(i) >= 223 && strAux.charAt(i)<=252) index  = strAux.charAt(i) - 197;
							else switch(strAux.charAt(i)){
							 	case 32: index = strAux.charAt(i)+24; break;
							 	case 45: index = strAux.charAt(i)+12; break;
						 	}

							if(ronaldo.getChild(index) == null){
		 						 ronaldo.addChild(index);
		 					}

							ronaldo = ronaldo.getChild(index);
	 				  }
						if(ronaldo.getLeafID().size()< 4)	ronaldo.setLeafID(ID);
					}
					ID++;
			 }
			 in.close();
		 }catch(FileNotFoundException e){}
		 return raiz;
		}

	 public static void main(String[] args) throws FileNotFoundException{
		 Trieteste teste =  Trieteste.getText("arquivoTeste");
		 Trieteste teste2 = new Trieteste();
		 RandomAccessFile arquivo = new RandomAccessFile("theUltimateFile2.bin", "rw");
		 teste.writeNode(arquivo, teste);
		 ArrayList<Integer> ID = teste2.searchTrie(arquivo, "dog");
		 System.out.println(ID);
	 }
}