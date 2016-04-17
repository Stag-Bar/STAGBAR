package edu.nku.CSC440.stagbar.dataaccess;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class FileIOTest {

	/** This test should only be run manually. Overwrites configuration file. */
	@Ignore
	@Test
	public void testWriteRead() {
		final String databaseName = "testDatabase";
		FileIO.writeDatabaseName(databaseName);
		assertEquals("Read/Write error.", databaseName, FileIO.readDatabaseName());
	}
}