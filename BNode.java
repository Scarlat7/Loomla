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

	static int diskCounterPT = 1; //contador para ir botando o n�mero do nodo no arquivo
	static int diskCounterEN = 1;
	static int diskCounterDE = 1;
	
	protected int diskIndex;

	private static boolean existingTree = false;
	private int nkeys; //n�mero de chaves atualmente armazenados
	private boolean leaf; //flag que indica se o nodo � folha

	private int IDs[] = new int[MAX_KEY]; //Vetor que contem as chaves
	private ArrayList<String>[] translations = new ArrayList[MAX_KEY];
	private int childrenIndex[] = new int[MAX_KEY + 1]; //n�mero do bloco no disco do filho

	public static void main (String args[]) throws IOException {
		/****descomente para gerar �rvore ****/

		/*
		BNode a = new BNode(true);
		for(int i = 2; i<= 150; i++){
			a = a.insert(i, "a");
		}
		a.insert(75, "b");
		*/
		

		/*****descomente para testar �rvore depois de escrita *****/
		/***um e somente um deve estar descomentado por vez! ****/

		/*
		BNode a = new BNode("portugues");

		ArrayList<String> b = a.getTranslations(75);
		for(String c : b)
			System.out.println(c);
		System.out.println(BNode.removeDuplicateTranslation(75, "b"));
		b = a.getTranslations(75);
		for(String c : b)
			System.out.println(c);
		*/
		
		

	}

	public BNode(String language) throws IOException{

		BNode a = diskFetch(0, language);

		if(language.equals("Portugu�s"))
			diskCounterPT = new File(language+"//").listFiles().length;
		else if(language.equals("Ingl�s"))
			diskCounterEN = new File(language+"//").listFiles().length;
			else if(language.equals("Alem�o"))
				diskCounterDE = new File(language+"//").listFiles().length;
		
		existingTree = true;

		this.diskIndex = a.diskIndex ;
		this.nkeys = a.nkeys;
		this.leaf = a.leaf;
		this.IDs = Arrays.copyOf(a.IDs, MAX_KEY);
		this.translations = Arrays.copyOf(a.translations, MAX_KEY);
		this.childrenIndex = Arrays.copyOf(a.childrenIndex, MAX_KEY+1);

	}

	public BNode(boolean leaf, String language) throws IOException{
		this.nkeys = 0;
		this.leaf = leaf;
		for(int i = 0; i<translations.length; i++)
			translations[i] = new ArrayList<String>();
		if(!existingTree){
			existingTree = true;
			File file = new File(language);
			if(!file.exists()) {
				if (file.mkdirs()){

				} else {
				    throw new IOException("Failed to create directory " + file);
				}
			}
			this.leaf = true;
			if(language.equals("Portugu�s"))
				this.diskIndex = diskCounterPT;
			else if(language.equals("Ingl�s"))
				this.diskIndex = diskCounterEN;
				else if(language.equals("Alem�o"))
					this.diskIndex = diskCounterDE;
			diskWrite(this, true, language);
		}
		if(language.equals("Portugu�s"))
			this.diskIndex = diskCounterPT++;
		else if(language.equals("Ingl�s"))
			this.diskIndex = diskCounterEN++;
			else if(language.equals("Alem�o"))
				this.diskIndex = diskCounterDE++;

	}

	private BNode search(BNode n, int ID, String language){

		int i = 0;

		while(i < n.nkeys && ID > n.IDs[i])
				i++;
		if(i < n.nkeys && ID == n.IDs[i])
			return n; 							// retorna nodo que cont�m a chave
		if(n.leaf)
			return null; 						// n�o encontrou
		else{
			n = diskFetch(n.childrenIndex[i], language);
			return search(n, ID, language);
		}
	}



	public ArrayList<String> getTranslations(int ID, String language){
		int i = 0;
		BNode dummy = search(this, ID, language);
		if(dummy != null){
			while(i < dummy.nkeys && ID > dummy.IDs[i])
				i++;
			if(i < dummy.nkeys && ID == dummy.IDs[i])
				return dummy.translations[i];
		}
		return null;
	}

	public BNode insert(int newID, String newTranslation, String language) throws IOException{

		BNode root;

		root = diskFetch(0, language);
		
		BNode test = search(this, newID, language);
		
		if( test != null){	//chave j� contida na �rvore
			test.addNewTranslation(newID, newTranslation, test.diskIndex == root.diskIndex, language);  //ent�o apenas adicionais a nova tradu��o � lista de tradu��es dessa palavra
			return diskFetch(0, language);						//retorna raiz para manter o funcionamento da �rvore
		}

        //se a raiz est� cheia, tem que fazer um split e criar uma nova raiz
        if (root.nkeys == MAX_KEY)
        {

            BNode newRoot= new BNode(false, language);

            //a antiga raiz � filha da nova
            newRoot.childrenIndex[0] = root.diskIndex;

            //faz split na raiz antiga
            newRoot.split(0, root, language);

            //v� qual dos filhos da nova raiz vai receber a nova chave
            int i = 0;
            if (newRoot.IDs[0] < newID)
                i++;
            diskFetch(newRoot.childrenIndex[i], language).insertNoSplit(newID, newTranslation, language);

            //atualiza raiz
            root = newRoot;

        }
        else{
        	//se raiz n�o t� cheia, insere normal sem fazer split
            root.insertNoSplit(newID, newTranslation, language);
    	}

		diskWrite(root, true, language);

	//retorno a raiz mesmo que n�o precise (porque j� est� guardada na classe nodo)
	//isso porque as outras opera��es sobre a �rvore ficam mais f�ceis de serem pensadas
	//se as come�armos da raiz (pelo menos para mim fica)
	return root;
	}

	private void addNewTranslation(int ID, String newTranslation, boolean isRoot, String language) throws IOException
	{
		int i = 0;
		while(i<nkeys && IDs[i] != ID)
			i++;
		translations[i].add(newTranslation);
		diskWrite(this, isRoot, language);
	}
	
	private static boolean removeDuplicateTranslation(int ID, String dupTrans, String language) throws IOException
	{
		int i = 0;
		BNode root = diskFetch(0, language);
		BNode t = root.search(root, ID, language);
		
		while(i<t.nkeys && t.IDs[i] != ID)
			i++;
		for(String a : t.translations[i]){
			if(a.equals(dupTrans)){
				t.translations[i].remove(dupTrans);
				return true;
			}
		}
		return false;
	}
	
	private void insertNoSplit(int newID, String newTranslation, String language) throws IOException
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
	        diskWrite(this, false, language);
	    }

	    //se n�o for uma folha tem que inserir num nodo do pr�ximo n�vel
	    else
	    {
	        //encontra em qual dos filhos a nova chave ser� inserida
	        while (i >= 0 && IDs[i] > newID)
	            i--;
	        i++;

	        BNode children = diskFetch(childrenIndex[i], language);
	        //verifica se o filho no qual a chave deve ser inserida est� cheio
	        //se estiver tem que fazer um split
	        if (children.nkeys == MAX_KEY)
	        {
	            split(i, children, language);

	            //verifica em qual dos novos filhos a nova chave ficar� (usando para isso a l�gica
	            //de maior para direta, menor para a esquerda)
	            if (IDs[i] < newID)
	                i++;
	        }
	        children.insertNoSplit(newID, newTranslation, language);
	    }
	}

	private void split(int i, BNode toBeSplit, String language) throws IOException
	{
	    //novo nodo que vai guardar as chaves da parte maior do nodo a ser dividido
	    BNode newRight = new BNode(toBeSplit.leaf, language);
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

	    diskWrite(this, false, language);
	    diskWrite(newRight, false, language);
	    diskWrite(toBeSplit, false, language);

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
	public void print(int height, String language)
	{

		System.out.println("*** "+height+" ***");

		//printa os elementos desse n�vel
		for(int j = 0; j<nkeys; j++)
			System.out.print(IDs[j]+"("+translations[j]+")" + " ");
		System.out.println();

		//printa os filhos desse elemento
		for(int i=0; i<nkeys+1; i++)
			if(childrenIndex[i] != 0)
				diskFetch(childrenIndex[i], language).print(height+1, language);
	}

	private static void diskWrite(BNode a, boolean isRoot, String language) throws IOException{

		try{
			File file;
			if(isRoot)
				file = new File(language+"//"+"b"+0+".bin");
			else
				file = new File(language+"//"+"b"+a.diskIndex+".bin");

			FileOutputStream output = new FileOutputStream(file);
			ObjectOutputStream objOutput = new ObjectOutputStream(output);
			objOutput.writeObject(a);
			objOutput.close();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	  }

	private static BNode diskFetch(int index, String language){

		   try{
			   FileInputStream input = new FileInputStream(language+"//"+"b"+index+".bin");
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
