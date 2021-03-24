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
		
		teamsToSchedule.add(teamA);
		teamsToSchedule.add(teamB);
		teamsToSchedule.add(teamC);
		teamsToSchedule.add(teamD);
		teamsToSchedule.add(teamE);
		teamsToSchedule.add(teamF);
		teamsToSchedule.add(teamG);
		teamsToSchedule.add(teamH);
		
		Schedule firstRound = new Schedule(teamsToSchedule);
		
		//ArrayList<Match> matches = sched.closestElo();
		ArrayList<Match> matches = firstRound.bestWithWorst();
		
		System.out.println("Round 1");
		int matchNo = 1;
		for (Match m : matches)
		{
			System.out.println("Match " + matchNo + " " + m.getFirstTeam().getTeamName() + " vs " +  m.getSecondTeam().getTeamName() + "\n\n");
			matchNo++;
			m.setResult(m.getFirstTeam());
		}
		
		ArrayList<Team> winners = firstRound.getWinners();
		
		Schedule secondRound = new Schedule(winners);
		matches = secondRound.inOrder();
		
		System.out.println("Round 2 \n");
		matchNo = 1;
		for (Match m : matches)
		{
			System.out.println("Match " + matchNo + " " + m.getFirstTeam().getTeamName() + " vs " +  m.getSecondTeam().getTeamName() + "\n\n");
			matchNo++;
			m.setResult(m.getFirstTeam());
		}				
	}
	
	public static void showGUI()
	{
		// GUI code will go here
	}

}
