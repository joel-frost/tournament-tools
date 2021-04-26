import java.nio.file.Paths;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		
		//showGUI();
		
		// Testing Code:
		ArrayList<Team> teamsToSchedule = new ArrayList<Team>();
		
		Team teamA = new Team("Team A", 50);
		Team teamB = new Team("Team B", 56);
		Team teamC = new Team("Team C", 23);
		Team teamD = new Team("Team D", 98);
		Team teamE = new Team("Team E", 76);
		Team teamF = new Team("Team F", 34);
		Team teamG = new Team("Team G", 12);
		Team teamH = new Team("Team H", 76);
		Team teamI = new Team("Team I", 34);
		Team teamJ = new Team("Team J", 87);
		Team teamK = new Team("Team K", 46);
		Team teamL = new Team("Team L", 23);
		Team teamM = new Team("Team M", 54);
		Team teamN = new Team("Team N", 76);
		Team teamO = new Team("Team O", 65);
		Team teamP = new Team("Team P", 15);
		
		//Team errorTeam = new Team("Testing", 50);
		
		teamsToSchedule.add(teamA);
		teamsToSchedule.add(teamB);
		teamsToSchedule.add(teamC);
		teamsToSchedule.add(teamD);
		teamsToSchedule.add(teamE);
		teamsToSchedule.add(teamF);
		teamsToSchedule.add(teamG);
		teamsToSchedule.add(teamH);
		teamsToSchedule.add(teamI);
		teamsToSchedule.add(teamJ);
		teamsToSchedule.add(teamK);
		teamsToSchedule.add(teamL);
		teamsToSchedule.add(teamM);
		teamsToSchedule.add(teamN);
		teamsToSchedule.add(teamO);
		teamsToSchedule.add(teamP);
		
		//teamsToSchedule.add(errorTeam);
		
		
		// Schedule Testing:
		/*
		 * Schedule firstRound = new Schedule(teamsToSchedule);
		 * 
		 * //ArrayList<Match> matches = sched.closestElo(); ArrayList<Match> matches =
		 * firstRound.bestWithWorst();
		 * 
		 * System.out.println("Round 1"); int matchNo = 1; for (Match m : matches) {
		 * System.out.println("Match " + matchNo + " " + m.getFirstTeam().getTeamName()
		 * + " vs " + m.getSecondTeam().getTeamName() + "\n\n"); matchNo++;
		 * m.setResult(m.getFirstTeam()); }
		 * 
		 * ArrayList<Team> winners = firstRound.getWinners();
		 * 
		 * Schedule secondRound = new Schedule(winners); matches =
		 * secondRound.inOrder();
		 * 
		 * System.out.println("Round 2 \n"); matchNo = 1; for (Match m : matches) {
		 * System.out.println("Match " + matchNo + " " + m.getFirstTeam().getTeamName()
		 * + " vs " + m.getSecondTeam().getTeamName() + "\n\n"); matchNo++;
		 * m.setResult(m.getFirstTeam()); }
		 */
		
		// Tournament Testing:		
		//Tournament t = new Tournament(1, teamsToSchedule);
		
		Tournament t = new Tournament(2);
		try 
		{
			t.importFromFile(Paths.get("./data/jsonexample.json"));
		} 
		catch (DataLoadingException e) {
			e.printStackTrace();
		}
		
		t.generateBrackets();
		t.generatePDF("data/export.pdf");
		t.exportJSON("data/export.json");
	}
	
	public static void showGUI()
	{
		// GUI code will go here
	}

}
