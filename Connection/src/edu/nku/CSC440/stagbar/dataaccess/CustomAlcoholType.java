package edu.nku.CSC440.stagbar.dataaccess;

/** User created type. */
public class CustomAlcoholType {
	public static final int NEW_CUSTOM_TYPE_ID = -1;
	private final AlcoholType kind;
	private final String name;
	private final int typeId;

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

	@Override
	public int hashCode() {
		return getTypeId();
	}

	public String print() {
		return "CustomAlcoholType{" +
				"kind=" + kind +
				", name='" + name + '\'' +
				", typeId=" + typeId +
				'}';
	}

	@Override
	public String toString() {
		return getName();
	}
}
