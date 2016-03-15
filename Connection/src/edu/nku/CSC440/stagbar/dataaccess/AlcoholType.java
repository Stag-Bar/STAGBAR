package edu.nku.CSC440.stagbar.dataaccess;

public enum AlcoholType {
	SINGLE_SERVE("Single Serve / Bottled Beer"), DRAFT("Draft"), SHELF("Shelf / Liquor");

	private String string;

	AlcoholType(String s){
		this.string = s;
	}

	public String toString(){
		return string;
	}
}
