package NewPipeGame;

import java.util.ArrayList;

public interface ICacheManager {
	boolean Save(String textToSave); 
	ArrayList<String> load(String problemToCheck);
}
