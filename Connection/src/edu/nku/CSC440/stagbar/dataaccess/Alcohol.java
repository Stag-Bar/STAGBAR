package edu.nku.CSC440.stagbar.dataaccess;

import java.util.Date;

public class Alcohol {

	/**
	 * Unique identifier for each alcohol.
	 * There may be multiple entries with the same name or name/type combination.
	 * Ex. Bud Light on tap & in bottle or a liquor whose bottle size changes.
	 */
	private int alcoholId;
	private String name;
	private AlcoholType type;
	private Date retireDate;

	public Alcohol(int alcoholId, String name, AlcoholType type, Date retireDate) {
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

	@Override
	public int hashCode() {
		return getAlcoholId();
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

	public AlcoholType getType() {
		return type;
	}

	public void setType(AlcoholType type) {
		this.type = type;
	}

	public Date getRetireDate() {
		return retireDate;
	}

	public void setRetireDate(Date retireDate) {
		this.retireDate = retireDate;
	}
}
