package testeTexto;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class BNode implements Serializable{


	private static final long serialVersionUID = -1676695448599438086L;
	public final static int T = 50; //grau da B-Tree (n�mero m�nimo de filhos)
	public final static int MAX_KEY = 2*T-1; //n�mero m�ximo de chaves

	static int diskCounter = 1; //contador para ir botando o n�mero do nodo no arquivo

	protected int diskIndex;

	private static boolean existingTree = false;
	private int nkeys; //n�mero de chaves atualmente armazenados
	private boolean leaf; //flag que indica se o nodo � folha

	private int IDs[] = new int[MAX_KEY]; //Vetor que contem as chaves
	private ArrayList<String>[] translations = new ArrayList[MAX_KEY];
	//private ArrayList<String> translations[] = new ArrayList<String>[MAX_KEY];
	private int childrenIndex[] = new int[MAX_KEY + 1]; //n�mero do bloco no disco do filho

	public static void main (String args[]) throws IOException {

		/*BNode.translations[0] = new ArrayList<>();
		BNode.translations[1] = new ArrayList<>();
		translations[0].add("oiii");
		translations[0].add("eii");
		translations[1].add("aii");

		for(String a : translations[0]){
			System.out.println(a);
		}
		for(String a : translations[1]){
			System.out.println(a);
		}*/
		
		/****descomente para gerar �rvore ****/

		/*
		BNode a = new BNode(true);
		for(int i = 2; i<= 1000; i++){
			a = a.insert(i, i+1);
		}
		*/

		/*****descomente para testar �rvore depois de escrita *****/
		/***um e somente um deve estar descomentado por vez! ****/

		/*
		BNode a = new BNode();
		System.out.println(BNode.diskCounter);
		System.out.println(a.IDs[0]);
		System.out.println(BNode.existingTree);

		System.out.println(a.getOffset(4));
		a.insert(148794, 69);
		System.out.println(a.getOffset(148794));
		*/
		
		

	}

	public BNode() throws IOException{

		BNode a = diskFetch(0);

		diskCounter = new File("data_files//").listFiles().length;
		existingTree = true;

		this.diskIndex = a.diskIndex ;
		this.nkeys = a.nkeys;
		this.leaf = a.leaf;
		this.IDs = Arrays.copyOf(a.IDs, MAX_KEY);
		this.translations = Arrays.copyOf(a.translations, MAX_KEY);
		this.childrenIndex = Arrays.copyOf(a.childrenIndex, MAX_KEY+1);

	}

	public BNode(boolean leaf) throws IOException{
		this.nkeys = 0;
		this.leaf = leaf;
		if(!existingTree){
			existingTree = true;
			File file = new File("data_files");
			if(!file.exists()) {
				if (file.mkdirs()){

				} else {
				    throw new IOException("Failed to create directory " + file);
				}
			}
			this.leaf = true;
			this.diskIndex = diskCounter;
			diskWrite(this, true);
		}
		this.diskIndex = diskCounter++;
	}

	private BNode search(BNode n, int ID){

		int i = 0;

		while(i < n.nkeys && ID > n.IDs[i])
				i++;
		if(i < n.nkeys && ID == n.IDs[i])
			return n; 							// retorna nodo que cont�m a chave
		if(n.leaf)
			return null; 						// n�o encontrou
		else{
			n = diskFetch(n.childrenIndex[i]);
			return search(n, ID);
		}
	}



	public ArrayList<String> getTranslations(int ID){
		int i = 0;
		BNode dummy = search(this, ID);
		if(dummy != null){
			while(i < dummy.nkeys && ID > dummy.IDs[i])
				i++;
			if(i < dummy.nkeys && ID == dummy.IDs[i])
				return dummy.translations[i];
		}
		return null;
	}

	public BNode insert(int newID, String newTranslation) throws IOException{

		if(search(this, newID) != null)
			return null; 					//chave j� contida na �rvore

		BNode root;

		root = diskFetch(0);

        //se a raiz est� cheia, tem que fazer um split e criar uma nova raiz
        if (root.nkeys == MAX_KEY)
        {

            BNode newRoot= new BNode(false);

            //a antiga raiz � filha da nova
            newRoot.childrenIndex[0] = root.diskIndex;

            //faz split na raiz antiga
            newRoot.split(0, root);

            //v� qual dos filhos da nova raiz vai receber a nova chave
            int i = 0;
            if (newRoot.IDs[0] < newID)
                i++;
            diskFetch(newRoot.childrenIndex[i]).insertNoSplit(newID, newTranslation);

            //atualiza raiz
            root = newRoot;

        }
        else{
        	//se raiz n�o t� cheia, insere normal sem fazer split
            root.insertNoSplit(newID, newTranslation);
    	}

		diskWrite(root, true);

	//retorno a raiz mesmo que n�o precise (porque j� est� guardada na classe nodo)
	//isso porque as outras opera��es sobre a �rvore ficam mais f�ceis de serem pensadas
	//se as come�armos da raiz (pelo menos para mim fica)
	return root;
	}

	private void insertNoSplit(int newID, String newTranslation) throws IOException
	{
	    //�ltimo �ndice usado do array das chaves
	    int i = nkeys-1;

	    if (leaf)
	    {
	        //aplica meio que um insertion sort no array das chaves
	    	//notar que � importante que o nodo n�o esteja cheio para esse passo
	    	//porque se estivesse ele tentaria acessar um �ndice maior que o permitido
	        while (i >= 0 && IDs[i] > newID)
	        {
	            IDs[i+1] = IDs[i];
	            translations[i+1] = translations[i];
	            i--;
	        }

	        IDs[i+1] = newID;
	        translations[i+1].add(newTranslation);
	        nkeys++;
	        diskWrite(this, false);
	    }

	    //se n�o for uma folha tem que inserir num nodo do pr�ximo n�vel
	    else
	    {
	        //encontra em qual dos filhos a nova chave ser� inserida
	        while (i >= 0 && IDs[i] > newID)
	            i--;
	        i++;

	        BNode children = diskFetch(childrenIndex[i]);
	        //verifica se o filho no qual a chave deve ser inserida est� cheio
	        //se estiver tem que fazer um split
	        if (children.nkeys == MAX_KEY)
	        {
	            split(i, children);

	            //verifica em qual dos novos filhos a nova chave ficar� (usando para isso a l�gica
	            //de maior para direta, menor para a esquerda)
	            if (IDs[i] < newID)
	                i++;
	        }
	        children.insertNoSplit(newID, newTranslation);
	    }
	}

	private void split(int i, BNode toBeSplit) throws IOException
	{
	    //novo nodo que vai guardar as chaves da parte maior do nodo a ser dividido
	    BNode newRight = new BNode(toBeSplit.leaf);
	    newRight.nkeys = T-1;

	    //copia essas chaves da metade maior para
	    for (int j = 0; j < T-1; j++){
	    	newRight.IDs[j] = toBeSplit.IDs[j+T];
	    	newRight.translations[j] = toBeSplit.translations[j+T];
	    	toBeSplit.IDs[j+T] = 0;
	    }

	    //copia os ponteiros para o filhos tamb�m (se n�o for folha, claro)
	    if (!toBeSplit.leaf)
	        for (int j = 0; j < T; j++){
	        	newRight.childrenIndex[j] = toBeSplit.childrenIndex[j+T];
	        	toBeSplit.childrenIndex[j+T] = 0;
	        }

	    //o nodo divido agora tem o n�mero m�nimo de chaves
	    toBeSplit.nkeys = T-1;

	    //como esse nodo pai vai ter mais um filho,
	    //joga os que existem para o lado (at� chegar um a direita do nodo que estamos dividindo)
	    //um a direita e n�o um a esquerda porque foi tomada a decis�o de que o novo nodo
	    //cont�m as chaves maiores
	    for (int j = nkeys; j >= i+1; j--){
	        childrenIndex[j+1] = childrenIndex[j];
	    }

	    //atualiza o ponteiro para esse novo nodo
	    childrenIndex[i+1] = newRight.diskIndex;

	    //cria espa�o para a nova chave a ser adicionada no nodo pai
	    for (int j = nkeys-1; j >= i; j--){
	        IDs[j+1] = IDs[j];
	        translations[j+1] = translations[j];
	    }

	    //coloca ela nesse espa�o
	    IDs[i] = toBeSplit.IDs[T-1];
	    translations[i] = toBeSplit.translations[T-1];
	    toBeSplit.IDs[T-1] = 0;

	    //agora o nodo pai tem mais uma chave
	    nkeys++;

	    diskWrite(this, false);
	    diskWrite(newRight, false);
	    diskWrite(toBeSplit, false);

	}

	@Override
	public String toString(){

		String a = "";

		for(int i = 0; i<nkeys; i++){
			a += (this.IDs[i] + "("+this.translations[i]+") ");
		}

		return a;
	}

	//printa com caminhamento pr�-fixado � esquerda
	public void print(int height)
	{

		System.out.println("*** "+height+" ***");

		//printa os elementos desse n�vel
		for(int j = 0; j<nkeys; j++)
			System.out.print(IDs[j]+"("+translations[j]+")" + " ");
		System.out.println();

		//printa os filhos desse elemento
		for(int i=0; i<nkeys+1; i++)
			if(childrenIndex[i] != 0)
				diskFetch(childrenIndex[i]).print(height+1);
	}

	public void diskWrite(BNode a, boolean isRoot) throws IOException{

		try{
			File file;
			if(isRoot)
				file = new File("data_files//"+"b"+0+".bin");
			else
				file = new File("data_files//"+"b"+a.diskIndex+".bin");

			FileOutputStream output = new FileOutputStream(file);
			ObjectOutputStream objOutput = new ObjectOutputStream(output);
			objOutput.writeObject(a);
			objOutput.close();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	  }

	public BNode diskFetch(int index){

		   try{
			   FileInputStream input = new FileInputStream("data_files//"+"b"+index+".bin");
			   ObjectInputStream objIn = new ObjectInputStream(input);
			   BNode a = (BNode) objIn.readObject();
			   objIn.close();
			   return a;

		   }catch(Exception ex){
			   ex.printStackTrace();
			   return null;
		   }
	   }
}
