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
	public final static int T = 50; //grau da B-Tree (nï¿½mero mï¿½nimo de filhos)
	public final static int MAX_KEY = 2*T-1; //nï¿½mero mï¿½ximo de chaves

	static int diskCounterPT = 1; //contador para ir botando o nï¿½mero do nodo no arquivo
	static int diskCounterEN = 1;
	static int diskCounterDE = 1;
	
	protected int diskIndex;

	private static boolean existingTree = false;
	private int nkeys; //nï¿½mero de chaves atualmente armazenados
	private boolean leaf; //flag que indica se o nodo ï¿½ folha

	private int IDs[] = new int[MAX_KEY]; //Vetor que contem as chaves
	private ArrayList<String>[] translations = new ArrayList[MAX_KEY];
	private int childrenIndex[] = new int[MAX_KEY + 1]; //nï¿½mero do bloco no disco do filho

	public static void main (String args[]) throws IOException {
		/****descomente para gerar árvore ****/

		/*
		BNode a = new BNode(true);
		for(int i = 2; i<= 150; i++){
			a = a.insert(i, "a");
		}
		a.insert(75, "b");
		*/
		

		/*****descomente para testar ï¿½rvore depois de escrita *****/
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

		if(language.equals("Português"))
			diskCounterPT = new File(language+"//").listFiles().length;
		else if(language.equals("Inglês"))
			diskCounterEN = new File(language+"//").listFiles().length;
			else if(language.equals("Alemão"))
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
			if(language.equals("Português"))
				this.diskIndex = diskCounterPT;
			else if(language.equals("Inglês"))
				this.diskIndex = diskCounterEN;
				else if(language.equals("Alemão"))
					this.diskIndex = diskCounterDE;
			diskWrite(this, true, language);
		}
		if(language.equals("Português"))
			this.diskIndex = diskCounterPT++;
		else if(language.equals("Inglês"))
			this.diskIndex = diskCounterEN++;
			else if(language.equals("Alemão"))
				this.diskIndex = diskCounterDE++;

	}

	private BNode search(BNode n, int ID, String language){

		int i = 0;

		while(i < n.nkeys && ID > n.IDs[i])
				i++;
		if(i < n.nkeys && ID == n.IDs[i])
			return n; 							// retorna nodo que contï¿½m a chave
		if(n.leaf)
			return null; 						// nï¿½o encontrou
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
		
		if( test != null){	//chave já contida na árvore
			test.addNewTranslation(newID, newTranslation, test.diskIndex == root.diskIndex, language);  //então apenas adicionais a nova tradução à lista de traduções dessa palavra
			return diskFetch(0, language);						//retorna raiz para manter o funcionamento da árvore
		}

        //se a raiz estï¿½ cheia, tem que fazer um split e criar uma nova raiz
        if (root.nkeys == MAX_KEY)
        {

            BNode newRoot= new BNode(false, language);

            //a antiga raiz ï¿½ filha da nova
            newRoot.childrenIndex[0] = root.diskIndex;

            //faz split na raiz antiga
            newRoot.split(0, root, language);

            //vï¿½ qual dos filhos da nova raiz vai receber a nova chave
            int i = 0;
            if (newRoot.IDs[0] < newID)
                i++;
            diskFetch(newRoot.childrenIndex[i], language).insertNoSplit(newID, newTranslation, language);

            //atualiza raiz
            root = newRoot;

        }
        else{
        	//se raiz nï¿½o tï¿½ cheia, insere normal sem fazer split
            root.insertNoSplit(newID, newTranslation, language);
    	}

		diskWrite(root, true, language);

	//retorno a raiz mesmo que nï¿½o precise (porque jï¿½ estï¿½ guardada na classe nodo)
	//isso porque as outras operaï¿½ï¿½es sobre a ï¿½rvore ficam mais fï¿½ceis de serem pensadas
	//se as comeï¿½armos da raiz (pelo menos para mim fica)
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
	    //ï¿½ltimo ï¿½ndice usado do array das chaves
	    int i = nkeys-1;

	    if (leaf)
	    {
	        //aplica meio que um insertion sort no array das chaves
	    	//notar que ï¿½ importante que o nodo nï¿½o esteja cheio para esse passo
	    	//porque se estivesse ele tentaria acessar um ï¿½ndice maior que o permitido
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

	    //se nï¿½o for uma folha tem que inserir num nodo do prï¿½ximo nï¿½vel
	    else
	    {
	        //encontra em qual dos filhos a nova chave serï¿½ inserida
	        while (i >= 0 && IDs[i] > newID)
	            i--;
	        i++;

	        BNode children = diskFetch(childrenIndex[i], language);
	        //verifica se o filho no qual a chave deve ser inserida estï¿½ cheio
	        //se estiver tem que fazer um split
	        if (children.nkeys == MAX_KEY)
	        {
	            split(i, children, language);

	            //verifica em qual dos novos filhos a nova chave ficarï¿½ (usando para isso a lï¿½gica
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

	    //copia os ponteiros para o filhos tambï¿½m (se nï¿½o for folha, claro)
	    if (!toBeSplit.leaf)
	        for (int j = 0; j < T; j++){
	        	newRight.childrenIndex[j] = toBeSplit.childrenIndex[j+T];
	        	toBeSplit.childrenIndex[j+T] = 0;
	        }

	    //o nodo divido agora tem o nï¿½mero mï¿½nimo de chaves
	    toBeSplit.nkeys = T-1;

	    //como esse nodo pai vai ter mais um filho,
	    //joga os que existem para o lado (atï¿½ chegar um a direita do nodo que estamos dividindo)
	    //um a direita e nï¿½o um a esquerda porque foi tomada a decisï¿½o de que o novo nodo
	    //contï¿½m as chaves maiores
	    for (int j = nkeys; j >= i+1; j--){
	        childrenIndex[j+1] = childrenIndex[j];
	    }

	    //atualiza o ponteiro para esse novo nodo
	    childrenIndex[i+1] = newRight.diskIndex;

	    //cria espaï¿½o para a nova chave a ser adicionada no nodo pai
	    for (int j = nkeys-1; j >= i; j--){
	        IDs[j+1] = IDs[j];
	        translations[j+1] = translations[j];
	    }

	    //coloca ela nesse espaï¿½o
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

	//printa com caminhamento prï¿½-fixado ï¿½ esquerda
	public void print(int height, String language)
	{

		System.out.println("*** "+height+" ***");

		//printa os elementos desse nï¿½vel
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
