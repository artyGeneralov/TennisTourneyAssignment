import java.util.Scanner;

public class TennisDriver {

	static final Scanner input = new Scanner(System.in);
	static final String[] tournamentNames = { "First Tournament", "Second Tournament", "Third Tournament",
			"Fourth Tournament", "Grand Finale" };

	public static void main(String[] args) {
		System.out.println("Would you like to delete the old rankings? (y/n)");
		String answer = input.nextLine();

		if (answer.equals("y")) {
			// delete old file if exists
			Tournament.clearTournamentData();
		}

		for (String tournamentName : tournamentNames) {
			Tournament t = new Tournament(tournamentName);
			t.simulateTournament();
		}
	}

}
