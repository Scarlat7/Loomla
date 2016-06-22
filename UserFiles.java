package testeTexto;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

public class UserFiles {
  public static int MAXTEXT = 25;
  
  public static void addUserData(User usuario) throws IOException{
	  RandomAccessFile userData = new RandomAccessFile("UserData", "rw");
	  userData.seek(usuario.getID());
	  userData.writeInt(usuario.getID());
	  for(int i=0; i<10; i++){
		 userData.writeChar(usuario.getNome()[i]);
	  }
	  for(int i=0; i<10;i++){

		 userData.writeChar(usuario.getSenha()[i]);
	  }
	  userData.writeFloat(usuario.getFluence());
	  for(int i =0; i<5; i++){
		  TextosLidos texto = usuario.getTextosLidos(i);
		 
		 if(texto == null){
			  for(int j=0; j<MAXTEXT; j++){
				  userData.writeChar(0);
			  }
			  userData.writeFloat(0);
			  for(int j=0; j<6;j++){
				 userData.writeChar(0); 
			  }
			  userData.writeShort(0);
		  }
		  else{
			  for(int j=0; j<MAXTEXT;j++){
				  //System.out.println(i + " " + j + texto.getNome()[j]);
				  if(texto.getNome().length > j){
					  userData.writeChar(texto.getNome()[j]);
				  }
					  
				  else userData.writeChar(0);
			  }
			  userData.writeFloat(texto.getCompreensao());
			  for(int j=0; j<6;j++){
					 userData.writeChar(texto.getData()[j]); 
			  }
			  userData.writeShort(texto.getDificuldade());
		  }	  
	  }
	  for(int i=0; i<10; i++){
		  Palavras palavra = usuario.getPalavras(i);
		  System.out.println(usuario.getPalavras(i));
		  if(palavra == null){
			  System.out.println(i);
			  userData.writeInt(0);
			  userData.writeInt(0);
			  for(int j=0; j<30; j++){
				  userData.writeChar(0);
			  }
			  
		  }
		  else{
			  userData.writeInt(palavra.getDificuldade());
			  userData.writeInt(palavra.getProcurada());
			  for(int j=0; j<30; j++){
				  if(palavra.getPalavra().length > j)
					  userData.writeChar(palavra.getPalavra()[j]);
				  else userData.writeChar(0);
			  }
		 }
	  }
	  userData.close();
  }
  public static User getUserData(String nome) throws IOException{
	  RandomAccessFile userData = new RandomAccessFile("UserData", "r");
	  char arrayAux1[] = new char[10];
	  Trieteste user = new Trieteste();
	  ArrayList<Integer> b = user.searchTrie("UserTrie", nome, "", 1);
	  if(b == null)
		  return null;
	  int ID = user.searchTrie("UserTrie", nome, "", 1).get(0);
	  User usuario = new User();
	  userData.seek(ID);
	  usuario.setID(userData.readInt()); //Le a ID;
	  for(int i=0; i<10; i++){ 
		 arrayAux1[i] = userData.readChar(); //Le o nome
	  }
	  usuario.setNome(arrayAux1); //Usuario recebe nome
	  arrayAux1 = new char[10];
	  for(int i=0; i<10;i++){
		 arrayAux1[i] = userData.readChar(); //Le a senha
	  }
	  usuario.setSenha(arrayAux1);		//usuario recebe
	  usuario.setFluence(userData.readFloat());
	  TextosLidos texto[] = new TextosLidos[5];
	  for(int i =0; i<5; i++){
		  texto[i] = new TextosLidos();
		  arrayAux1 = new char[MAXTEXT];
		  for(int j=0; j<MAXTEXT; j++){
			  arrayAux1[j] = userData.readChar();
		  }
		  texto[i].setNome(arrayAux1);
		  texto[i].setCompreensao(userData.readFloat());
		  arrayAux1 = new char[6];
		  for(int j=0; j<6;j++){
			 arrayAux1[j] = userData.readChar(); 
		  }
		  texto[i].setData(arrayAux1);
		  texto[i].setDificuldade(userData.readShort());

		  usuario.setTextosLidos(texto[i], i);
	  }
	  Palavras palavra[] = new Palavras[10];
	  
	  for(int i=0; i<10; i++){
		  arrayAux1 = new char[30];
		  	  palavra[i] = new Palavras();
			  palavra[i].setDificuldade(userData.readInt());
			  palavra[i].setProcurada(userData.readInt());
			  for(int j=0; j<30; j++){
				  arrayAux1[j] = userData.readChar();
			  }
			  palavra[i].setPalavra(arrayAux1);
			  usuario.setPalavras(palavra[i], i);
	  }
	  userData.close();
	  return usuario;
  }
  public static int getNewUserID(String fileName)	throws IOException{
	  RandomAccessFile arquivo = new RandomAccessFile(fileName, "rw");
	  int ID = (int)arquivo.length();
	  arquivo.close();
	  return ID;
  }
  public static void addUser(String name, String password) throws IOException{
	  RandomAccessFile userTrie = new RandomAccessFile("UserTrie", "rw");
	  Trieteste usuario = new Trieteste();
	  User newUser = new User(getNewUserID("UserData"), name, password);
	  usuario.addToTrie(name, newUser.getID(), userTrie, 1);
	  addUserData(newUser);
  }
  public static void main(String[] args) throws IOException {
	 //Trieteste user = new Trieteste();
	 User leo = getUserData("leo");
	 //Palavras word = leo.getPalavras(5);
	 //char array[] = {'a', 'b', 'c'};;
	 System.out.println(leo.getPalavras(5));
	 //word.setPalavra(array);
	 addUserData(leo);
	 for(int i = 0; i < 10; i++)
	 {
		 System.out.println(leo.getPalavras(i));
	 }
	// leo.setPalavras(, 5);
	 
	 //addUser("rei", "oleole");
	  addUser("thomas", "nat222");
	 //addUser("leo", "oleole");
	 
	 //User leonardo = getUserData("nat");
	 //System.out.println(leonardo.getSenha());
  }
}

class ToString{
	public static String toString(char[] word){
		String palavra;
		int i=0;
		while(i < word.length && word[i]!=0){
			i++;
		}
		char Aux[] = new char[i];
		for(int j =0; j<i; j++){
			Aux[j] = word[j];
		}
		palavra = String.valueOf(Aux);
		return palavra;
	}
}
