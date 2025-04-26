package model;

import java.util.Objects;

public final class Author {
	public final String NAME;
	public final int BIRTH_YEAR;
	public final int DEATH_YEAR;
	
	public Author(String name, int birthYear, int deathYear) {
		NAME = name;
		BIRTH_YEAR = birthYear;
		DEATH_YEAR = deathYear;
	}
	
	@Override
	public String toString() {
		return NAME + ", Alive from " + BIRTH_YEAR + " to " + DEATH_YEAR;
	}

	public int compareTo(Author other) {
		// TODO Auto-generated method stub
		return NAME.compareTo(other.NAME);
	}

	@Override
	public int hashCode() {
		return Objects.hash(BIRTH_YEAR, DEATH_YEAR, NAME);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Author other = (Author) obj;
		return BIRTH_YEAR == other.BIRTH_YEAR && DEATH_YEAR == other.DEATH_YEAR && Objects.equals(NAME, other.NAME);
	}
	
	
}
