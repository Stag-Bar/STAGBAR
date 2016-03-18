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
	private LocalDate creationDate;
	private String name;
	private LocalDate retireDate;
	private CustomAlcoholType type;


	public Alcohol(int alcoholId, String name, CustomAlcoholType type, LocalDate creationDate, LocalDate retireDate) {
		this.alcoholId = alcoholId;
		this.name = name;
		this.type = type;
		this.creationDate = creationDate;
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

	public LocalDate getCreationDate() {
		return creationDate;
	}

	public String getName() {
		return name;
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

	@Override
	public int hashCode() {
		return getAlcoholId();
	}

}
