package edu.nku.CSC440.stagbar.dataaccess;

/** User created type. */
public class CustomAlcoholType {
	public static final int NEW_CUSTOM_TYPE_ID = -1;
	private String Name;
	private AlcoholType kind;
	private int typeId;

	public CustomAlcoholType(int typeId, String name, AlcoholType kind) {
		this.typeId = typeId;
		Name = name;
		this.kind = kind;
	}

	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(o == null || getClass() != o.getClass()) return false;

		CustomAlcoholType that = (CustomAlcoholType)o;

		return getTypeId() == that.getTypeId();

	}

	public AlcoholType getKind() {
		return kind;
	}

	public void setKind(AlcoholType kind) {
		this.kind = kind;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	@Override
	public int hashCode() {
		return getTypeId();
	}
}
