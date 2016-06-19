package testeTexto;

import java.io.IOException;
import java.io.RandomAccessFile;

public class UserFiles {
	
  public void setNewUserID() throws IOException{
	 RandomAccessFile arquivo = new RandomAccessFile("newUserID", "rw");
	 int ID = arquivo.readInt();
	 arquivo.seek(0);
	 arquivo.writeInt(ID+1);
	 arquivo.close();
  }
  public int getNewUserID()	throws IOException{
	  RandomAccessFile arquivo = new RandomAccessFile("newUserID", "rw");
	  return arquivo.readInt();
  }

}
