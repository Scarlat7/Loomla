package testeTexto;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class UserFiles {

  public void addUserData(User usuario) throws IOException{
	  RandomAccessFile userData = new RandomAccessFile("UserData", "r");
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
			  for(int j=0; j<15; j++){
				  userData.writeChar(0);
			  }
			  userData.writeFloat(0);
			  for(int j=0; j<6;j++){
				 userData.writeChar(0); 
			  }
			  userData.writeShort(0);
		  }
		  else{
			  for(int j=0; j<15;j++){
				  userData.writeChar(texto.getNome()[j]);
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
		  if(palavra == null){
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
				  userData.writeChar(palavra.getPalavra()[j]);
			  }
		  }
	  }
	  userData.close();
  }
	
  public static int getNewUserID(String fileName)	throws IOException{
	  RandomAccessFile arquivo = new RandomAccessFile(fileName, "r");
	  int ID = (int)arquivo.length();
	  arquivo.close();
	  return ID;
  }
  public void addUser(String name, String password) throws IOException{
	  RandomAccessFile userTrie = new RandomAccessFile("UserTrie", "rw");
	  Trieteste usuario = new Trieteste();
	  User newUser = new User(getNewUserID("UserData"), name, password);
	  usuario.addToTrie(name, newUser.getID(), userTrie);
  }
}
