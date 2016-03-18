package edu.nku.CSC440.stagbar.dataaccess;

import java.time.LocalDate;

public class Alcohol {

	public static final int NEW_ALCOHOL_ID = -1;

	/**
	 * Unique identifier for each alcohol.
	 * Auto-incremented by database.
	 * There may be multiple entries with the same name or name/type combination.
	 * Ex. Bud Light on tap & in bottle or a liquor whose bottle size changes.
	 */
	private int alcoholId;
	private String name;
	private LocalDate retireDate;
	private CustomAlcoholType type; //TODO: Update to use custom types


	public Alcohol(int alcoholId, String name, CustomAlcoholType type, LocalDate retireDate) {
		this.alcoholId = alcoholId;
		this.name = name;
		this.type = type;
		this.retireDate = retireDate;
	}

	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(o == null || getClass() != o.getClass()) return false;

		Alcohol alcohol = (Alcohol)o;

		return getAlcoholId() == alcohol.getAlcoholId();
	}

	public int getAlcoholId() {
		return alcoholId;
	}

	public void setAlcoholId(int alcoholId) {
		this.alcoholId = alcoholId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getRetireDate() {
		return retireDate;
	}

	public void setRetireDate(LocalDate retireDate) {
		this.retireDate = retireDate;
	}

	public CustomAlcoholType getType() {
		return type;
	}

	public void setType(CustomAlcoholType type) {
		this.type = type;
	}

	@Override
	public int hashCode() {
		return getAlcoholId();
	}

}
