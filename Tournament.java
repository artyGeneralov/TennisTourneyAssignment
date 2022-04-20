import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class Tournament {

	private final int NUMBER_OF_PLAYERS = 8;
	private final static String FILE_NAME = "TennisTournament.dat";
	private String name;
	private NodeList<Player> playerList;
	private ObjectOutputStream playerOut;
	private ObjectInputStream playerIn;
	private Player[] ar;

	public Tournament(String name) {
		playerList = new NodeList<Player>(null);
		ar = new Player[NUMBER_OF_PLAYERS];
		this.name = name;
		try {
			readPlayersFromFile();
			addToSortArr();
		} catch (IOException e) {
			// Initialize default players
			System.err.printf("New file has been created at %s\n", Paths.get(FILE_NAME).toAbsolutePath().toString());
			System.out.println("\n\n");
			ar[0] = new Player("Artium The Great", 10000);
			ar[1] = new Player("Itamar The Great", 11000);
			ar[2] = new Player("Roey The Great", 9000);
			ar[3] = new Player("Dan The Great", 8000);
			ar[4] = new Player("Ran The Great", 3);
			ar[5] = new Player("Pete The Great", 6000);
			ar[6] = new Player("Ben The Great", 5000);
			ar[7] = new Player("Gad The Bad", 4000);
			addToSortArr();
		}
		openOutStream();
	}

	public void addToSortArr() {
		sortArr();
		for (Player player : ar)
			playerList.insertAtFront(player);
	}

	// sortArr method: we're sorting array from small to large
	// The values would be inserted into the List from large to small!!
	private void sortArr() {
		boolean swapped;
		int current;

		// Bubble sort:
		while (true) {
			swapped = false;
			current = 0;
			while (current < ar.length - 1) {
				if (ar[current].getTotalScore() > ar[current + 1].getTotalScore()) {
					Player temp = ar[current];
					ar[current] = ar[current + 1];
					ar[current + 1] = temp;
					swapped = true;
				}
				current++;
			}
			if (!swapped)
				break;
		}
	}

	/*
	 * simulateTournament method:
	 */
	public void simulateTournament() {
		System.out.println("**************************");
		System.out.printf("Tennis tournament %s is starting now\n", this.name);
		System.out.println("**************************");
		while (!playerList.isEmpty()) {
			Player player1 = null;
			Player player2 = null;
			try {
				player1 = (Player) playerList.removeFromBack();
				player2 = (Player) playerList.removeFromBack();
				Player winner = simulateGame(player1, player2);
				// Loser to file:
				if (winner.getName().equals(player1.getName())) {
					// player2 lost, update his score, add him to file
					player1.updateTotalScore();
					addPlayerToFile(player2);
				} else {
					// player1 lost, update his score, add him to file.
					player2.updateTotalScore();
					addPlayerToFile(player1);
				}

				// Winner back to list:
				playerList.insertAtFront(winner);
			} catch (EmptyListException e) {
				// exception thrown only when player2 is null!
				// player1 winner
				// update his score, add him to the file.
				player1.updateTotalScore();
				addPlayerToFile(player1);
			}
		}
		closeOutputStream();
		try {
			readPlayersFromFile();
			addToSortArr();
			playerList.print();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * simulateGame method: receives two Player objects as parameters, performs a
	 * calculation on their scores to determine the winner returns the winning
	 * player.
	 */
	private Player simulateGame(Player p1, Player p2) {
		double newRandNum = Math.random();
		double sum = (p1.getTotalScore() + p2.getTotalScore()) * newRandNum;
		String gameMessage = String.format("Game  %s - %s: ", p1.getName(), p2.getName());
		if (Math.abs(p1.getTotalScore() - sum) > Math.abs(p2.getTotalScore() - sum)) {
			// player1 win
			p1.updateGameWin();
			gameMessage += String.format("winner - %s", p1.getName());
			System.out.println(gameMessage);
			return p1;
		} else {
			// player2 win
			p2.updateGameWin();
			gameMessage += String.format("winner - %s", p2.getName());
			System.out.println(gameMessage);
			return p2;
		}
	}

	/* File Handling */

	private void openOutStream() {
		try {
			playerOut = new ObjectOutputStream(Files.newOutputStream(Paths.get(FILE_NAME)));
		} catch (IOException e) {
			System.err.println("Could not open file.");
		}
	}

	private void readPlayersFromFile() throws IOException {

		// Open file
		try {
			playerIn = new ObjectInputStream(Files.newInputStream(Paths.get(FILE_NAME)));
			boolean reading = true;
			int i = 0;
			// Fetch data
			while (reading && i < NUMBER_OF_PLAYERS) {
				Player player = null;
				try {
					player = (Player) playerIn.readObject();
				}catch(EOFException eof) {
					throw new IOException();
				}
				catch (ClassNotFoundException cnf) {
					System.err.println("No player class found");
				} catch (IOException e) {
					e.printStackTrace();
					throw new IOException();
				}
				if (player != null)
					ar[i++] = player;
				else
					reading = false;
			}

		} catch (IOException e) {
			throw new IOException();
		} finally {
			// Close file
			if (playerIn != null) {
				try {
					playerIn.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	

	private void addPlayerToFile(Player player) {
		if (playerOut != null) {
			try {
				playerOut.writeObject(player);
			} catch (IOException e) {
				System.err.println("Can't write to file.");
				System.exit(1);
			}
		}
	}

	private void closeOutputStream() {
		if (playerOut != null) {
			try {
				playerOut.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/*
	 * clearTournamentData method: clears the tournament data off a file and leaves
	 * an empty one in its place.
	 */
	public static void clearTournamentData() {
		try {
			new ObjectOutputStream(Files.newOutputStream(Paths.get(FILE_NAME)))
					.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
