package testeTexto;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;

public class BNode implements Serializable{
	
	
	private static final long serialVersionUID = -1676695448599438086L;
	public final static int T = 2;//50; //grau da B-Tree (número mínimo de filhos)
	public final static int MAX_KEY = 2*T-1; //número máximo de chaves
	
	static int diskCounter = 0; //contador para ir botando o número do nodo no arquivo
	
	private int diskIndex;
	
	private static BNode root;
	private int nkeys; //número de chaves atualmente armazenados
	private boolean leaf; //flag que indica se o nodo é folha
	
	private int IDs[] = new int[MAX_KEY]; //Vetor que contem as chaves
	private int Offsets[] = new int[MAX_KEY]; //Vetor que contém os deslocamento no arquivo de tradução referentes às chaves (dados)
	private BNode children[] = new BNode[MAX_KEY + 1]; //ponteiros para os filhos
	
	public static void main (String args[]) {
	    
		
		BNode a = new BNode(true);
		for(int i = 1; i<= 15; i++){
			a = a.insert(i, i+1);
			System.out.println("**Inseriu "+i+" ***");
		}
		
		a.print(0);
		
		/*try {
			BNode a = new BNode();
			a.insert(1, 0);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos;
			oos = new ObjectOutputStream(baos);
			oos.writeObject(a);
			oos.close();
			System.out.println(baos.size());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
	
	public BNode(boolean leaf){
		if(root == null)
			root = this;
		this.nkeys = 0;
		this.leaf = leaf;
		this.diskIndex = diskCounter++;
	}
	
	private BNode search(BNode n, int ID){
		int i = 0;
		
		while(i < n.nkeys && ID > n.IDs[i])
				i++;
		if(i < n.nkeys && ID == n.IDs[i])
			return n; 							// retorna nodo que contém a chave
		if(n.leaf)
			return null; 						// não encontrou
		else //if(i < n.nkeys)
			n = n.children[i];
			//n = diskFetch(n.children[i]); 		// procura no filho esquerdo
		//else
			//n = n.children[i];
			//n = diskFetch(n.children[i+1]);		 // procura no filho direito
		
		return search(n, ID);
	}
	
	

	public int getOffset(int ID){
		int i = 0;
		BNode dummy = search(this, ID);
		if(dummy != null){
			while(i < dummy.nkeys && ID > dummy.IDs[i])
				i++;
			if(i < dummy.nkeys && ID == dummy.IDs[i])
				return dummy.Offsets[i];
		}
		return -1;		
	}	

	
	public BNode insert(int newID, int newOffset){
		
		if(search(this, newID) != null)
			return null; 					//chave já contida na árvore
		
		//nova árvore
		if (root == null){
	        root = new BNode(true);
	        root.IDs[0] = newID;
	        root.nkeys = 1;
	    }
		else //árvore já existe
	    {
	        //se a raiz está cheia, tem que fazer um split e criar uma nova raiz
	        if (root.nkeys == MAX_KEY)
	        {
	        	
	            BNode newRoot= new BNode(false);
	 
	            //a antiga raiz é filha da nova
	            newRoot.children[0] = root;
	            
	            //faz split na raiz antiga
	            newRoot.split(0, root);
	 
	            //vê qual dos filhos da nova raiz vai receber a nova chave
	            int i = 0;
	            if (newRoot.IDs[0] < newID)
	                i++;
	            newRoot.children[i].insertNonFull(newID, newOffset);
	 
	            //atualiza raiz
	            root = newRoot;
	        }
	        else  
	        	//se raiz não tá cheia, insere normal sem fazer split
	            root.insertNonFull(newID, newOffset);
	    }
		//retorno a raiz mesmo que não precise (porque já está guardada na classe nodo)
		//isso porque as outras operações sobre a árvore ficam mais fáceis de serem pensadas
		//se as começarmos da raiz (pelo menos para mim fica)
		return root;
	}
	
	public void insertNonFull(int newID, int newOffset)
	{
	    //último índice usado do array das chaves
	    int i = nkeys-1;
	    
	    if (leaf)
	    {
	        //aplica meio que um insertion sort no array das chaves
	    	//notar que é importante que o nodo não esteja cheio para esse passo
	    	//porque se estivesse ele tentaria acessar um índice maior que o permitido
	        while (i >= 0 && IDs[i] > newID)
	        {
	            IDs[i+1] = IDs[i];
	            Offsets[i+1] = Offsets[i];
	            i--;
	        }

	        IDs[i+1] = newID;
	        Offsets[i+1] = newOffset;
	        nkeys++;
	    }
	    
	    //se não for uma folha tem que inserir num nodo do próximo nível
	    else
	    {
	        //encontra em qual dos filhos a nova chave será inserida
	        while (i >= 0 && IDs[i] > newID)
	            i--;
	        i++;
	        
	        //verifica se o filho no qual a chave deve ser inserida está cheio
	        //se estiver tem que fazer um split
	        if (children[i].nkeys == MAX_KEY)
	        {
	            split(i, children[i]);
	 
	            //verifica em qual dos novos filhos a nova chave ficará (usando para isso a lógica
	            //de maior para direta, menor para a esquerda)
	            if (IDs[i] < newID)
	                i++;
	        }
	        children[i].insertNonFull(newID, newOffset);
	    }
	}
	
	public void split(int i, BNode toBeSplit)
	{
	    //novo nodo que vai guardar as chaves da parte maior do nodo a ser dividido
	    BNode newRight = new BNode(toBeSplit.leaf);
	    newRight.nkeys = T-1;
	 
	    //copia essas chaves da metade maior para 
	    for (int j = 0; j < T-1; j++){
	    	newRight.IDs[j] = toBeSplit.IDs[j+T];
	    	newRight.Offsets[j] = toBeSplit.Offsets[j+T];
	    	toBeSplit.IDs[j+T] = 0;
	    }
	 
	    //copia os ponteiros para o filhos também (se não for folha, claro)
	    if (!toBeSplit.leaf)
	        for (int j = 0; j < T; j++){
	        	newRight.children[j] = toBeSplit.children[j+T];
	        	toBeSplit.children[j+T] = null;
	        }      
	 
	    //o nodo divido agora tem o número mínimo de chaves
	    toBeSplit.nkeys = T-1;
	 
	    //como esse nodo pai vai ter mais um filho,
	    //joga os que existem para o lado (até chegar um a direita do nodo que estamos dividindo)
	    //um a direita e não um a esquerda porque foi tomada a decisão de que o novo nodo
	    //contém as chaves maiores
	    for (int j = nkeys; j >= i+1; j--)
	        children[j+1] = children[j];
	 
	    //atualiza o ponteiro para esse novo nodo
	    children[i+1] = newRight;
	 
	    //cria espaço para a nova chave a ser adicionada no nodo pai
	    for (int j = nkeys-1; j >= i; j--){
	        IDs[j+1] = IDs[j];
	        Offsets[j+1] = Offsets[j];
	    }
	     
	    //coloca ela nesse espaço
	    IDs[i] = toBeSplit.IDs[T-1];
	    Offsets[i] = toBeSplit.Offsets[T-1];
	    toBeSplit.IDs[T-1] = 0;
	 
	    //agora o nodo pai tem mais uma chave
	    nkeys++;
	}
	
	//printa com caminhamento pré-fixado à esquerda
	public void print(int height)
	{
			
		System.out.println("*** "+height+" ***");
		
		//printa os elementos desse nível
		for(int j = 0; j<nkeys; j++)
			System.out.print(IDs[j]+"("+Offsets[j]+")" + " ");
		System.out.println();
			
		//printa os filhos desse elemento
		for(int i=0; i<nkeys+1; i++)
			if(children[i] != null)
				children[i].print(height+1);
	}
}
