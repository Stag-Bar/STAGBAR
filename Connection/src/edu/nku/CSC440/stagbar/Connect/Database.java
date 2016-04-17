package edu.nku.CSC440.stagbar.Connect;

import edu.nku.CSC440.stagbar.dataaccess.data.*;

import java.time.LocalDate;
import java.util.Set;

public interface Database {
	boolean authenticateUser(String username, String password);

	/** Deletes given ingredient for given drink. */
	boolean deleteMixedDrinkIngredient(String mixedDrinkName, Alcohol alcohol);

	/** Delete user from database */
	boolean deleteUser(String username);

	/**
	 * Checks database for an alcohol with given name & type that is active (not retired as of given date, inclusive).
	 *
	 * @return <code>true</code> if specified alcohol is defined in database and is active, <code>false</code> otherwise.
	 */
	boolean doesActiveAlcoholExist(String name, CustomAlcoholType type, LocalDate date);

	/**
	 * Checks database for given drink.
	 *
	 * @param drinkName Drink to check database for.
	 * @return <code>true</code> if given drink is in the database,
	 * <code>false</code> otherwise.
	 * @throws RuntimeException If user's database connection is closed.
	 */
	boolean doesDrinkExist(String drinkName);

	/**
	 * Checks database for given username.
	 *
	 * @param username Username to check database for.
	 * @return <code>true</code> if given username is in the database,
	 * <code>false</code> otherwise.
	 * @throws RuntimeException If user's database connection is closed.
	 */
	boolean doesUserExist(String username);

	/** Searches database for alcohol whose retire date is null or after the start date & whose creation date is before (inclusive) the end date. */
	Set<Alcohol> findActiveAlcohol(LocalDate startDate, LocalDate endDate);

	/** Searches database for alcohol whose retire date is null or after the given date & whose creation date is before (inclusive) given date. */
	Set<Alcohol> findActiveAlcohol(LocalDate date);

	/** Searches database for alcohol, matching given type, whose retire date is null or after the start date & whose creation date is before (inclusive) the end date. */
	Set<Alcohol> findActiveAlcoholByType(CustomAlcoholType type, LocalDate startDate, LocalDate endDate);

	/** Searches database for alcohol, matching given type, whose retire date is null or after the given date & whose creation date is before (inclusive) given date. */
	Set<Alcohol> findActiveAlcoholByType(CustomAlcoholType type, LocalDate date);

	Set<Alcohol> findAllAlcohol();

	/** Pulls all custom types from database. */
	Set<CustomAlcoholType> findAllCustomAlcoholTypes();

	Set<String> findAllMixedDrinkNames();

	/** Searches database for all mixed drinks and their corresponding ingredients. */
	Set<MixedDrink> findAllMixedDrinks();

	/** Retrieves all users from database. */
	Set<User> findAllUsers();

	MixedDrink findMixedDrinkByName(String drinkName);

	PermissionLevel findPermissionsForUser(String username);

	/** Creates the user and necessary tables. */
	void firstTimeSetup(String database, String userName, String password);

	String getDatabaseName();

	/** Sets retire date for given alcohol. */
	boolean retireAlcohol(int alcoholId, LocalDate date);

	/** Sets retire date for given mixed drink. */
	boolean retireMixedDrink(String mixedDrink, boolean isRetired);

	/**
	 * Saves a new Alcohol record.
	 * Should be written in a way that can be called effectively once or in a loop.
	 *
	 * @return <code>true</code> if successful, <code>false</code> otherwise.
	 */
	boolean saveAlcohol(Alcohol alcohol);

	boolean saveCustomAlcoholType(CustomAlcoholType type);

	/** Creates a new row on the Delivery table with the given entry. */
	boolean saveDeliveryEntry(Entry entry);

	/** Creates a new row on the Inventory table with the given entry. */
	boolean saveInventoryEntry(Entry entry);

	/** Creates a new mixed drink with given name and no retire date. */
	boolean saveMixedDrink(String name);

	/** Creates a new mixed drink ingredient for drink with given name. */
	boolean saveMixedDrinkIngredient(String mixedDrinkName, Alcohol alcohol, Double amount);

	/** Creates a new row on the Sales table with the given entry. */
	boolean saveSalesEntry(Entry entry);

	boolean saveUser(String username, String password, PermissionLevel permissionLevel);

	/** Updates given ingredient for given drink with given value. */
	boolean updateMixedDrinkIngredient(String mixedDrinkName, Alcohol alcohol, Double amount);

	/** Updates password for given user to given value. */
	boolean updateUserPassword(String username, String password);

	/** Updates permissions for given user to given value. */
	boolean updateUserPermissions(String username, PermissionLevel permissionLevel);

	enum EntryCategory {
		INVENTORY, DELIVERY, SALES
	}
}
