package testeTexto;

public class Sort {

	  private static void combinePalavras(Palavras[] v, int begin, int mid, int end, String campo, boolean order){

	    	int i, j, k;
	    	int quantity = end - begin; 

	    	Palavras[] aux = new Palavras[quantity];

	    	i = begin;                                    
	    	j = mid;                                        
	    	k = 0;
	    	
	    	while(i < mid && j < end){ 
	    		if(!order){
	    			if(campo.equals("Palavra"))
			            if(v[i].palavra.compareTo(v[j].palavra) <= 0)
			                aux[k++] = v[i++];                      
			            else
			                aux[k++] = v[j++];
	    			else if(campo.equals("Dificuldade")){
	    				if(v[i].dificuldade <= v[j].dificuldade)
			                aux[k++] = v[i++];                      
			            else
			                aux[k++] = v[j++];
	    			}
	    			else{
	    				if(v[i].procurada <= v[j].procurada)
			                aux[k++] = v[i++];                      
			            else
			                aux[k++] = v[j++];
	    			}
	    		}
	    		else
	    			if(campo.equals("Palavra"))
			            if(v[i].palavra.compareTo(v[j].palavra) >= 0)
			                aux[k++] = v[i++];                      
			            else
			                aux[k++] = v[j++];
	    			else if(campo.equals("Dificuldade")){
	    				if(v[i].dificuldade >= v[j].dificuldade)
			                aux[k++] = v[i++];                      
			            else
			                aux[k++] = v[j++];
	    			}
	    			else{
	    				if(v[i].procurada >= v[j].procurada)
			                aux[k++] = v[i++];                      
			            else
			                aux[k++] = v[j++];
	    			}
	    	}

	    	if(i < mid)                                     
	            for(j=i; j < mid; j++){             
	                aux[k++] = v[j];
	            }
	    	else
	            for(i = j; i < end; i++){
	                aux[k++] = v[i];
	            }

	    	System.arraycopy(aux, 0, v, begin, quantity);
	        //System.arraycopy(aux, begin, v, begin, quantity);
	    }
	    
	    public static void MergeSortPalavras(Palavras[] s, int inicio, int ultimo, String campo, boolean ordem){
	    	
	        if(inicio < ultimo-1){
	          int mid = (inicio + ultimo)/2;        
	          MergeSortPalavras(s, inicio, mid, campo, ordem);   
	          MergeSortPalavras(s, mid, ultimo, campo, ordem);    
	          combinePalavras(s, inicio, mid, ultimo, campo, ordem);       
	       }
	    }
	    
	    private static void combineTextos(TextosLidos[] v, int begin, int mid, int end, String campo, boolean order){

	    	int i, j, k;
	    	int quantity = end - begin; 

	    	TextosLidos[] aux = new TextosLidos[quantity];

	    	i = begin;                                    
	    	j = mid;                                        
	    	k = 0;
	    	
	    	while(i < mid && j < end){ 
	    		if(!order){
	    			if(campo.equals("Título")){
			            if(v[i].nome.compareTo(v[j].nome) <= 0)
			                aux[k++] = v[i++];                      
			            else
			                aux[k++] = v[j++];
	    			}
	    			else if(campo.equals("Data")){
	    				
	    				int dataEmDias1 = Integer.parseInt(v[i].data.substring(0, 2))
	    								+ 30*Integer.parseInt(v[i].data.substring(2, 4))
	    								+ 365*Integer.parseInt(v[i].data.substring(4, 6));
	    				int dataEmDias2 = Integer.parseInt(v[j].data.substring(0, 2))
										+ 30*Integer.parseInt(v[j].data.substring(2, 4))
										+ 365*Integer.parseInt(v[j].data.substring(4, 6));
	    				
	    				if(dataEmDias1 <= dataEmDias2)
			                aux[k++] = v[i++];                      
			            else
			                aux[k++] = v[j++];
	    			}
	    			else{
	    				if(v[i].compreensao <= v[j].compreensao)
			                aux[k++] = v[i++];                      
			            else
			                aux[k++] = v[j++];
	    			}
	    		}
	    		else{
	    			if(campo.equals("Título")){
			            if(v[i].nome.compareTo(v[j].nome) >= 0)
			                aux[k++] = v[i++];                      
			            else
			                aux[k++] = v[j++];
	    			}
	    			else if(campo.equals("Data")){
	    				
	    				int dataEmDias1 = Integer.parseInt(v[i].data.substring(0, 2))
	    								+ 30*Integer.parseInt(v[i].data.substring(2, 4))
	    								+ 365*Integer.parseInt(v[i].data.substring(4, 6));
	    				int dataEmDias2 = Integer.parseInt(v[j].data.substring(0, 2))
										+ 30*Integer.parseInt(v[j].data.substring(2, 4))
										+ 365*Integer.parseInt(v[j].data.substring(4, 6));
	    				
	    				if(dataEmDias1 >= dataEmDias2)
			                aux[k++] = v[i++];                      
			            else
			                aux[k++] = v[j++];
	    			}
	    			else{
	    				if(v[i].compreensao >= v[j].compreensao)
			                aux[k++] = v[i++];                      
			            else
			                aux[k++] = v[j++];
	    			}
	    		}
	    	}

	    	if(i < mid)                                     
	            for(j=i; j < mid; j++){             
	                aux[k++] = v[j];
	            }
	    	else
	            for(i = j; i < end; i++){
	                aux[k++] = v[i];
	            }

	    	System.arraycopy(aux, 0, v, begin, quantity);
	        //System.arraycopy(aux, begin, v, begin, quantity);
	    }
	    
	    public static void MergeSortTextos(TextosLidos[] s, int inicio, int ultimo, String campo, boolean ordem){
	    	
	        if(inicio < ultimo-1){
	          int mid = (inicio + ultimo)/2;        
	          MergeSortTextos(s, inicio, mid, campo, ordem);   
	          MergeSortTextos(s, mid, ultimo, campo, ordem);    
	          combineTextos(s, inicio, mid, ultimo, campo, ordem);       
	       }
	    }
	
	
}
