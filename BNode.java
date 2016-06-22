package testeTexto;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class BNode implements Serializable{


	private static final long serialVersionUID = -1676695448599438086L;
	public final static int T = 50; 			//grau da B-Tree (número mínimo de filhos)
	public final static int MAX_KEY = 2*T-1; 	//número máximo de chaves

	/* Contadores para atribuir o diskIndex de cada nodo em cada uma das três árvores B */
	static int diskCounterPT = 1;	 //contador para a árvore B do português
	static int diskCounterEN = 1; 	 //contador para a árvore B do alemão
	static int diskCounterDE = 1; 	 //contador para a árvore B do inglês
	
	/* Índice do nodo (usado para fazer o apontamento para nodos filhos e leitura do disco) 
	 * Ele é único para cada nodo (isto é, nenhum nodo tem um diskIndex similar a outro nodo */
	protected int diskIndex;

	private int nkeys; 		//número de chaves atualmente armazenados
	private boolean leaf; 	//flag que indica se o nodo é folha

	private int IDs[] = new int[MAX_KEY]; //Vetor que contem as chaves
	/* Vetor que contém as listas de traduções associadas a cada chave (ID) */ 
	private ArrayList<String>[] translations = new ArrayList[MAX_KEY];
	private int childrenIndex[] = new int[MAX_KEY + 1]; //Índice dos nodos filhos no disco

	/* Lê o nodo raiz para a instância
	 * Esse construtor é usado quando quisermos acessar a árvore B armazenada em disco
	 * */
	public BNode(String language) throws IOException{

		BNode a = diskFetch(0, language);

		/* O diskCounter depende de qual língua ele está acessando
		 * Ele é pego através da quantidade de arquivos existentes 
		 * no diretório onde a árvore B está armazenada */
		if(language.equals("Português"))
			diskCounterPT = new File("dados//"+language+"//").listFiles().length;
		else if(language.equals("Inglês"))
			diskCounterEN = new File("dados//"+language+"//").listFiles().length;
			else if(language.equals("Alemão"))
				diskCounterDE = new File("dados//"+language+"//").listFiles().length;

		this.diskIndex = a.diskIndex ;
		this.nkeys = a.nkeys;
		this.leaf = a.leaf;
		this.IDs = Arrays.copyOf(a.IDs, MAX_KEY);
		this.translations = Arrays.copyOf(a.translations, MAX_KEY);
		this.childrenIndex = Arrays.copyOf(a.childrenIndex, MAX_KEY+1);

	}

	/* Esse construtor é usado quando vamos criar um BTree nova
	 * Ele é diferente do outro pois não pega um nodo raiz existente,
	 * mas sim cria um diretório para armazenar a árvore e escreve
	 * o primeiro nodo (raiz) da BTree nele
	 */
	public BNode(boolean leaf, String language) throws IOException{
		
		/* São feitas as inicializações do nodo */
		this.nkeys = 0;
		this.leaf = leaf;
		for(int i = 0; i<translations.length; i++)
			translations[i] = new ArrayList<String>();
	
			File file = new File("dados//"+language);
			if(!file.exists()) {
				if (file.mkdirs()){

				} else {
				    throw new IOException("Failed to create directory " + file);
				}
			
			this.leaf = true;
			
			/* Atribui o atual índice do contador ao nodo */
			if(language.equals("Português"))
				this.diskIndex = diskCounterPT;
			else if(language.equals("Inglês"))
				this.diskIndex = diskCounterEN;
				else if(language.equals("Alemão"))
					this.diskIndex = diskCounterDE;
			
			diskWrite(this, true, language); //escreve raiz no disco
		}
		if(language.equals("Português"))
			this.diskIndex = diskCounterPT++;
		else if(language.equals("Inglês"))
			this.diskIndex = diskCounterEN++;
			else if(language.equals("Alemão"))
				this.diskIndex = diskCounterDE++;

	}

	/* Procura a chave 'ID' na árvore */
	private BNode search(BNode n, int ID, String language){

		int i = 0;

		while(i < n.nkeys && ID > n.IDs[i])		//acha em qual nodo filho a chave deve estar
				i++;
		if(i < n.nkeys && ID == n.IDs[i])
			return n; 							//retorna nodo que contém a chave
		if(n.leaf)
			return null; 						// não encontrou a chave
		else{
			n = diskFetch(n.childrenIndex[i], language);	//pega o próximo nodo do disco
			return search(n, ID, language);
		}
	}
	
	/* Retorna a lista de traduções associada à chave 'ID' */
	public ArrayList<String> getTranslations(int ID, String language){
		int i = 0;
		BNode dummy = search(this, ID, language);
		if(dummy != null){
			while(i < dummy.nkeys && ID > dummy.IDs[i])
				i++;
			if(i < dummy.nkeys && ID == dummy.IDs[i])
				return dummy.translations[i];
		}
		return null;	//a chave não foi encontrada na árvore
	}

	/* Insere a chave 'newID' com a tradução 'newTranslation' na árvore */
	public BNode insert(int newID, String newTranslation, String language) throws IOException{

		BNode root;

		root = diskFetch(0, language);
		
		BNode test = search(this, newID, language);
		
		if( test != null){					//chave já contida na árvore
			test.addNewTranslation(newID, newTranslation, test.diskIndex == root.diskIndex, language);  //então apenas adiciona a nova tradução à lista de traduções dessa palavra
			return diskFetch(0, language);						//retorna raiz para manter o funcionamento da árvore
		}

        //se a raiz estiver cheia, tem que fazer um split e criar uma nova raiz
        if (root.nkeys == MAX_KEY)
        {

            BNode newRoot= new BNode(false, language);

            //a antiga raiz é filha da nova
            newRoot.childrenIndex[0] = root.diskIndex;

            //faz split na raiz antiga
            newRoot.split(0, root, language);

            //vê qual dos filhos da nova raiz vai receber a nova chave
            int i = 0;
            if (newRoot.IDs[0] < newID)
                i++;
            diskFetch(newRoot.childrenIndex[i], language).insertNoSplit(newID, newTranslation, language);

            //atualiza raiz
            root = newRoot;

        }
        else{
        	//se raiz não está cheia, insere normal sem fazer split
            root.insertNoSplit(newID, newTranslation, language);
    	}

		diskWrite(root, true, language);

	//retorno a raiz mesmo que não precise (porque já está guardada no arquivo)
	//isso porque as outras operações sobre a árvore ficam mais fáceis de serem pensadas
	//se as começarmos da raiz
	return root;
	}

	/* Adiciona uma nova tradução à lista de traduções da chave 'ID' */
	private void addNewTranslation(int ID, String newTranslation, boolean isRoot, String language) throws IOException
	{
		int i = 0;
		while(i<nkeys && IDs[i] != ID)
			i++;
		translations[i].add(newTranslation);
		diskWrite(this, isRoot, language);
	}
	
	/* Função para remover uma tradução duplicada 'dupTrans' da lista de traduções associadas a 'ID' 
	 * retorna true se string foi removida
	 * retorna false se a string não se encontrava no nodo com a chave 'ID'*/
	public static boolean removeDuplicateTranslation(int ID, String dupTrans, String language) throws IOException
	{
		int i = 0;
		BNode root = diskFetch(0, language);
		BNode t = root.search(root, ID, language); 	//acha o nodo que contém a chave
		
		while(i<t.nkeys && t.IDs[i] != ID)			//faz com que i seja o índice da chave 'ID' e de sua lista de traduções
			i++;
		for(String a : t.translations[i]){			//itera sobre a lista de traduções até achar uma tradução igual à procurada
			if(a.equals(dupTrans)){
				t.translations[i].remove(dupTrans);
				return true;
			}
		}
		return false;
	}
	
	/* Insere uma chave 'newID' em um nodo não cheio */
	private void insertNoSplit(int newID, String newTranslation, String language) throws IOException
	{
	    //último índice usado do array das chaves
	    int i = nkeys-1;

	    if (leaf)
	    {
	        //aplica um insertion sort no array das chaves
	    	//notar que é importante que o nodo não esteja cheio para esse passo
	    	//porque se estivesse ele tentaria acessar um índice maior que o permitido
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

	    //se não for uma folha tem que inserir num nodo do próximo nível
	    else
	    {
	        //encontra em qual dos filhos a nova chave será inserida
	        while (i >= 0 && IDs[i] > newID)
	            i--;
	        i++;

	        BNode children = diskFetch(childrenIndex[i], language);
	        //verifica se o filho no qual a chave deve ser inserida está cheio
	        //se estiver tem que fazer um split
	        if (children.nkeys == MAX_KEY)
	        {
	            split(i, children, language);

	            //verifica em qual dos novos filhos a nova chave ficará (usando para isso a lógica
	            //de maior para o filho direito, menor para o filho esquerdo)
	            if (IDs[i] < newID)
	                i++;
	        }
	        children.insertNoSplit(newID, newTranslation, language);
	    }
	}

	/* Faz a operação de split em um nodo cheio */
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

	    //copia os ponteiros para o filhos também (se não for folha, claro)
	    if (!toBeSplit.leaf)
	        for (int j = 0; j < T; j++){
	        	newRight.childrenIndex[j] = toBeSplit.childrenIndex[j+T];
	        	toBeSplit.childrenIndex[j+T] = 0;
	        }

	    //o nodo divido agora tem o número mínimo de chaves
	    toBeSplit.nkeys = T-1;

	    //como esse nodo pai vai ter mais um filho,
	    //joga os que existem para o lado (até chegar um a direita do nodo que estamos dividindo)
	    //um a direita e não um a esquerda porque foi tomada a decisão de que o novo nodo
	    //contém as chaves maiores
	    for (int j = nkeys; j >= i+1; j--){
	        childrenIndex[j+1] = childrenIndex[j];
	    }

	    //atualiza o ponteiro para esse novo nodo
	    childrenIndex[i+1] = newRight.diskIndex;

	    //cria espaço para a nova chave a ser adicionada no nodo pai
	    for (int j = nkeys-1; j >= i; j--){
	        IDs[j+1] = IDs[j];
	        translations[j+1] = translations[j];
	    }

	    //coloca ela nesse espaço
	    IDs[i] = toBeSplit.IDs[T-1];
	    translations[i] = toBeSplit.translations[T-1];
	    toBeSplit.IDs[T-1] = 0;

	    //agora o nodo pai tem mais uma chave
	    nkeys++;

	    //escreve as alterações pro disco
	    diskWrite(this, false, language);
	    diskWrite(newRight, false, language);
	    diskWrite(toBeSplit, false, language);

	}

	/* Função para escrever um nodo e seus dados como uma string */
	@Override
	public String toString(){

		String a = "";

		for(int i = 0; i<nkeys; i++){
			a += (this.IDs[i] + "("+this.translations[i]+") ");
		}

		return a;
	}

	//printa com caminhamento pré-fixado à esquerda
	public void print(int height, String language)
	{

		System.out.println("*** "+height+" ***");

		//printa os elementos desse nível
		for(int j = 0; j<nkeys; j++)
			System.out.print(IDs[j]+"("+translations[j]+")" + " ");
		System.out.println();

		//printa os filhos desse elemento
		for(int i=0; i<nkeys+1; i++)
			if(childrenIndex[i] != 0)
				diskFetch(childrenIndex[i], language).print(height+1, language);
	}

	/* Escreve um nodo para o disco */
	private static void diskWrite(BNode a, boolean isRoot, String language) throws IOException{

		try{
			File file;
			/* Se o nodo é raiz, o arquivo b0.bin é reservado para ele */
			if(isRoot)
				file = new File("dados//"+language+"//"+"b"+0+".bin");
			else
				file = new File("dados//"+language+"//"+"b"+a.diskIndex+".bin");

			FileOutputStream output = new FileOutputStream(file);
			ObjectOutputStream objOutput = new ObjectOutputStream(output);
			objOutput.writeObject(a);
			objOutput.close();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	  }

	/* Lê um nodo do disco */
	private static BNode diskFetch(int index, String language){

		   try{
			   FileInputStream input = new FileInputStream("dados//"+language+"//"+"b"+index+".bin");
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
