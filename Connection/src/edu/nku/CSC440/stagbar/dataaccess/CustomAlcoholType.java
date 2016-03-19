package edu.nku.CSC440.stagbar.dataaccess;

/** User created type. */
public class CustomAlcoholType {
	public static final int NEW_CUSTOM_TYPE_ID = -1;
	private AlcoholType kind;
	private String name;
	private int typeId;

	public CustomAlcoholType(int typeId, String name, AlcoholType kind) {
		this.typeId = typeId;
		this.name = name;
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

	public String getName() {
		return name;
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

	@Override
	public String toString() {
		return getName();
	}
}
