import java.io.Serializable;

public class Player implements Serializable {
	private String name;
	private int totalScore;
	private int tournamentScore;

	public Player(String name, int totalScore) {
		setName(name);
		setTotalScore(totalScore);
		this.tournamentScore = 0;
	}

	// Getters & Setters:
	public String getName() {
		return this.name;
	}

	public int getTotalScore() {
		return this.totalScore;
	}

	public int getTournamentScore() {
		return this.tournamentScore;
	}

	public void setName(String name) {
		if (name == "")
			throw new IllegalArgumentException("Name cannot be empty.");
		if (name == null)
			throw new NullPointerException("Name cannot be --null--");
		this.name = name;
	}

	public void setTotalScore(int score) {
		if (totalScore < 0)
			throw new IllegalArgumentException("Score cannot be negative.");
		this.totalScore = score;
	}

	public String toString() {
		return new String(String.format("%s %d", this.name, this.totalScore));
	}
	
	
	
	// Other methods:
	public void updateGameWin() {
		final int SCORE_TO_ADD = 10;
		this.tournamentScore += SCORE_TO_ADD;
	}
	
	public void updateTotalScore() {
		this.totalScore += this.tournamentScore;
	}
	

}
