package model;

public class Author {
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
}
