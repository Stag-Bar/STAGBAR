package edu.nku.CSC440.stagbar.dataaccess.data;

public enum AlcoholType {
	BOTTLED("Single Serve / Bottled Beer"), DRAFT("Draft"), SHELF("Shelf / Liquor");

	private final String string;

	AlcoholType(String s){
		this.string = s;
	}

	public String toString(){
		return string;
	}
}
