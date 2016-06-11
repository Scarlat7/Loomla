package testeTexto;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class BNode implements Serializable{
	
	
	private static final long serialVersionUID = -1676695448599438086L;

	public static void main (String args[]) {
	    
		
		BNode a = new BNode();
		a.insert(0, 1);
		System.out.println(a.getOffset(0));
		
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

	public final static int T = 50; //grau da B-Tree (número mínimo de chaves)
	public final static int MAX_KEY = 2*T; //número máximo de chaves
	
	static int diskCounter = 0; //contador para ir botando o número do nodo no arquivo
	
	private int diskIndex;
	private int nchaves; //número de chaves atualmente armazenados
	private boolean leaf; //flag que indica se o nodo é folha
	private int IDs[][] = new int[MAX_KEY][2]; //matriz de duas dimensões contendo 1- o ID da palavra, 2-o deslocamento para achá-la na tabela de traduções
	private BNode children[] = new BNode[MAX_KEY + 1]; //ponteiros para os filhos
	
	public BNode(){
		this.nchaves = 0;
		this.leaf = true;
		for(int i = 0; i<MAX_KEY; i++){
			IDs[i][0] = -1;
		}
		this.diskIndex = diskCounter;
		diskCounter++;
	}
	
	private BNode search(BNode n, int ID){
		int i = 0;
		while(i < n.nchaves && ID < n.IDs[i][0])
				i++;
		if(i < n.nchaves && ID == n.IDs[i][0])
			return n; 							// retorna nodo que contém a chave
		if(n.leaf)
			return null; 						// não encontrou
		/*else if(i < n.nchaves)
			n = diskFetch(n.children[i]); 		// procura no filho esquerdo
		else
			n = diskFetch(n.children[i+1]);		 // procura no filho direito
		*/
		return search(n, ID);
	}
	
	public void split(BNode parent, int index)
	{
		////Referência para split ((não usa mesma B-Tree que o sôr))
		
	    BNode z = new BNode();

	    // Copy the t-1 keys in positions t to 2t-2 into z.
	    for (int j = 0; j < T-1; j++) {
	    	z.IDs[j][0] = IDs[j+T][0];
	    	IDs[j+T][0] = 0; 		// remove the reference
	    }
	    
	    // If this node is not a leaf, copy the t children in
	    // positions t to 2t-1, too.
	    if (!leaf)
	    	for (int j = 0; j < T; j++) {
	    		z.children[j] = children[j+T];
		    	children[j+T] = null; // remove the reference
	    	}

	    nchaves = T-1;

	    // Move the children in x that are to the right of y by
	    // one position to the right.
	    for (int j = parent.nchaves; j >= index+1; j--)
	    	parent.children[j+1] = parent.children[j];

	    // Drop z into x's child i+1.
	    parent.children[index+1] = z;

	    // Move the keys in x that are to the right of y by one
	    // position to the right.
	    for (int j = parent.nchaves-1; j >= index; j--)
	    	parent.IDs[j+1][0] = parent.IDs[j][0];

	    // Move this node's median key into x, and remove the
	    // reference to the key in this node.
	    parent.IDs[index][0] = IDs[T-1][0];
	    IDs[T-1][0] = 0;

	    parent.nchaves++;		// one more key/child in x

	    // All done.  Write out the nodes.
	    //diskWrite();
	    //z.diskWrite();
	    //parent.diskWrite();
	}

	public BNode insert(int newID, int newOffset)
	{
	
		int i = nchaves-1;					//último índice utilizado do array de chaves
		
		if(search(this, newID) != null)
			return null; 					//chave já contida na árvore
		
		if(leaf && nchaves < MAX_KEY){		//tem espaço no nodo folha, então adiciona
			while(i>=0 && IDs[i][0] > newID){	//tipo um InsertionSort
				IDs[i+1][0] = IDs[i--][0];
				IDs[i+1][1] = IDs[i--][1];
			}
			IDs[i+1][0] = newID;
			IDs[i+1][1] = newOffset;
			nchaves++;
			//diskWrite();
			
			return this;				//retorna a folha onde foi colocada a chave
		}
		else{	
			i = 0;
			while (i <= MAX_KEY && IDs[i][0] < newID)
				i++; 					//encontra o nodo que devemos percorrer
			
			if (children[i].nchaves == MAX_KEY) {
				//como o nodo está cheio tem que fazer split
				//e atualizar as referências para os filhos
				children[i].split(this, i);
			    if (IDs[i][0] < newID)
				i++;
			}
				
			/*int mediano = IDs[T][0];
			if(newID < mediano){
				mediano = IDs[T-1][0];
				if(mediano < newID)
					mediano = newID;
			}*/
					
			if (IDs[i][0] < newID)
				   i++;
			//children[i].diskRead();
			children[i].insert(newID, newOffset);	//continua procura no filho esquerdo
		}
		
		return new BNode();
	}
	

	public int getOffset(int ID){
		int i = 0;
		BNode dummy = search(this, ID);
		if(dummy != null){
			while(i < dummy.nchaves && ID < dummy.IDs[i][0])
				i++;
			if(i < dummy.nchaves && ID == dummy.IDs[i][0])
				return dummy.IDs[i][1];
		}
		return -1;		
	}	
	
}
