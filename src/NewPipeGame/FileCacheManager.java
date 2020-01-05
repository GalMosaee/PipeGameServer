package NewPipeGame;

import java.awt.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.stream.Stream;

public class FileCacheManager implements ICacheManager {

	private String folderPath;
	
	public FileCacheManager(String folder) {
		this.folderPath = String.format("%s%s",javax.swing.filechooser.FileSystemView.getFileSystemView().getHomeDirectory(),folder);
		createCacheFolder(folderPath);
	}
	
	private void createCacheFolder(String folder)
	{
		Path path = Paths.get(folderPath);
		if(!Files.exists(path)) {
		    try {
		      Files.createDirectories(path);
		    } catch (IOException e) {
		      //e.printStackTrace();
		    }
		}
	}
	
	@Override
	public boolean Save(String textToSave) {
		if (folderPath == null || folderPath.isEmpty() || folderPath == "")
			return false;
		long count = 0;
		try (Stream<Path> files = Files.list(Paths.get(folderPath))) {
		    count = files.count();
		}catch (Exception e) {
			//System.out.println(String.format("could not save to file cache manager, path: %s",folderPath));
			// TODO: handle exception
			return false;
		}
		
		String file = String.format("%s\\problem%d.txt",folderPath,count+1);
		try {
			Files.write( Paths.get(file), textToSave.getBytes(), StandardOpenOption.CREATE);

			//System.out.println(String.format("cache path saved, path: %s",file));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		return true;
	}

	// return null if not found for any reason
	@Override
	public ArrayList<String> load(String problemToCheck) {
		ArrayList<String> results = new ArrayList<String>();
		File[] files = new File(folderPath).listFiles();
		if (files == null)
			return null;
		// walk on all files in cache folder check first line of string problem
		for (File file : files)
		{
			// if file open it
			if (file.isFile())
			{
				// read its first line
				try(BufferedReader br = new BufferedReader(new FileReader(file.getPath()))){
					String firstLineProblem = br.readLine();
					// if the file contain the problem in the first line return solution
					if (problemToCheck.equals(firstLineProblem))
					{
						// read all other lines and return as solution
						// TODO: consider return solution object instead of string
						String line;
						ArrayList<String>  solution = new ArrayList<String>();
						while ((line = br.readLine()) != null)
						{
							solution.add(line);
						}
						return solution;
					}
				} catch (IOException e) {
					//e.printStackTrace();
					return null;
				}
			}
		}
		return null;
	}

}
