package api.ergast.model.reponse;

public class NationalityWiseStanding implements Comparable<NationalityWiseStanding> {

	private String nationality;
	private int wins;
	private int rank;

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public int getWins() {
		return wins;
	}

	public void setWins(int wins) {
		this.wins = wins;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public void addToVictoryTally(int wins) {
		this.wins = this.wins + wins;
	}

	@Override
	public String toString() {
		return rank + (",") + nationality + (",") + wins;
	}

	public String toCsvString() {
		return rank + (",") + nationality + (",") + wins;
	}

	public NationalityWiseStanding(String nationality, int wins) {
		super();
		this.nationality = nationality;
		this.wins = wins;
	}

	@Override
	public int compareTo(NationalityWiseStanding o) {
		int i = o.getWins() - this.getWins();
		if (i != 0)
			return i;
		i = this.getNationality().compareTo(o.getNationality());
		return i;
	}

}
