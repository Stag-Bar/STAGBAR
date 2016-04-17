package edu.nku.CSC440.stagbar.Connect.mock;

import edu.nku.CSC440.stagbar.Connect.Database;
import edu.nku.CSC440.stagbar.dataaccess.data.*;

import java.time.LocalDate;
import java.util.Set;

public class DatabaseMock implements Database {
	@Override
	public boolean authenticateUser(String username, String password) {
		return false;
	}

	@Override
	public boolean deleteMixedDrinkIngredient(String mixedDrinkName, Alcohol alcohol) {
		return false;
	}

	@Override
	public boolean deleteUser(String username) {
		return false;
	}

	@Override
	public boolean doesActiveAlcoholExist(String name, CustomAlcoholType type, LocalDate date) {
		return false;
	}

	@Override
	public boolean doesDrinkExist(String drinkName) {
		return false;
	}

	@Override
	public boolean doesUserExist(String username) {
		return false;
	}

	@Override
	public Set<Alcohol> findActiveAlcohol(LocalDate startDate, LocalDate endDate) {
		return ConnectMock.findActiveAlcohol(startDate, endDate);
	}

	@Override
	public Set<Alcohol> findActiveAlcohol(LocalDate date) {
		return findActiveAlcohol(date, date);
	}

	@Override
	public Set<Alcohol> findActiveAlcoholByType(CustomAlcoholType type, LocalDate startDate, LocalDate endDate) {
		return ConnectMock.findActiveAlcoholByType(type, startDate, endDate);
	}

	@Override
	public Set<Alcohol> findActiveAlcoholByType(CustomAlcoholType type, LocalDate date) {
		return findActiveAlcoholByType(type, date, date);
	}

	@Override
	public Set<Alcohol> findAllAlcohol() {
		return ConnectMock.findAllAlcohol();
	}

	@Override
	public Set<CustomAlcoholType> findAllCustomAlcoholTypes() {
		return ConnectMock.findAllCustomAlcoholTypes();
	}

	@Override
	public Set<String> findAllMixedDrinkNames() {
		return null;
	}

	@Override
	public Set<MixedDrink> findAllMixedDrinks() {
		return ConnectMock.findAllMixedDrinks();
	}

	@Override
	public Set<User> findAllUsers() {
		return null;
	}

	@Override
	public MixedDrink findMixedDrinkByName(String drinkName) {
		return null;
	}

	@Override
	public PermissionLevel findPermissionsForUser(String username) {
		return null;
	}

	@Override
	public void firstTimeSetup(String database, String userName, String password) {

	}

	@Override
	public String getDatabaseName() {
		return null;
	}

	@Override
	public boolean retireAlcohol(int alcoholId, LocalDate date) {
		return false;
	}

	@Override
	public boolean retireMixedDrink(String mixedDrink, boolean isRetired) {
		return false;
	}

	@Override
	public boolean saveAlcohol(Alcohol alcohol) {
		return false;
	}

	@Override
	public boolean saveCustomAlcoholType(CustomAlcoholType type) {
		return false;
	}

	@Override
	public boolean saveDeliveryEntry(Entry entry) {
		return false;
	}

	@Override
	public boolean saveInventoryEntry(Entry entry) {
		return false;
	}

	@Override
	public boolean saveMixedDrink(String name) {
		return false;
	}

	@Override
	public boolean saveMixedDrinkIngredient(String mixedDrinkName, Alcohol alcohol, Double amount) {
		return false;
	}

	@Override
	public boolean saveSalesEntry(Entry entry) {
		return false;
	}

	@Override
	public boolean saveUser(String username, String password, PermissionLevel permissionLevel) {
		return false;
	}

	@Override
	public boolean updateMixedDrinkIngredient(String mixedDrinkName, Alcohol alcohol, Double amount) {
		return false;
	}

	@Override
	public boolean updateUserPassword(String username, String password) {
		return false;
	}

	@Override
	public boolean updateUserPermissions(String username, PermissionLevel permissionLevel) {
		return false;
	}
}
