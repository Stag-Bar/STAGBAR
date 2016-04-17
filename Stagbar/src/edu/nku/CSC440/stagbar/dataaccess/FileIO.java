package edu.nku.CSC440.stagbar.dataaccess;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileIO {

	private static final String CONFIGURATION_FILENAME = "stagbar.stg";
	private static final String DATABASE_NAME_PREFIX = "dbName:";
	private static final Logger log = Logger.getLogger(FileIO.class.getName());

	public static String readDatabaseName() {
		String databaseName = null;
		Path filepath = Paths.get(CONFIGURATION_FILENAME);
		try {
			List<String> lines = Files.readAllLines(filepath, Charset.forName("UTF-8"));
			for(String line : lines) {
				if(line.startsWith(DATABASE_NAME_PREFIX)) {
					databaseName = line.substring(DATABASE_NAME_PREFIX.length());
				}
			}
		} catch(IOException e) {
			log.log(Level.CONFIG, "Stagbar configuration file missing or malformed.", e);
		}

		return databaseName;
	}

	public static void writeDatabaseName(String databaseName) {
		Path filepath = Paths.get(CONFIGURATION_FILENAME);
		try {
			List<String> lines = new ArrayList<>(1);
			lines.add(DATABASE_NAME_PREFIX + databaseName);

			Files.write(filepath, lines, Charset.forName("UTF-8"));
		} catch(IOException e) {
			log.log(Level.CONFIG, "Unable to save Stagbar configuration file to system.", e);
		}
	}
}
