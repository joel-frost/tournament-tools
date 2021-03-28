import java.util.ArrayList;

public class Tournament {
	
	private int tournamentID;
	private int numRounds;
	private ArrayList<Schedule> tournamentSchedules = new ArrayList<>();
	
	// TODO: This probably isn't the best place to check validity
	public Tournament(int ID, ArrayList<Team> toSchedule)
	{
		tournamentID = ID;
		Schedule round1 = new Schedule(toSchedule);
		try
		{
			round1.checkValid();
		}
		
		catch (InvalidNumTeamsException e)
		{
			System.out.println("test");
		}
		
		tournamentSchedules.add(round1);
		numRounds = calculateRounds(round1);
			
	}
			
	
	public int getTournamentID() 
	{
		return tournamentID;
	}

	public void setTournamentID(int tournamentID) 
	{
		this.tournamentID = tournamentID;
	}
	
	private int calculateRounds(Schedule sched)
	{
		int rounds = 0;
		int numTeams = sched.getTeamsToSchedule().size();
		if (numTeams < 2)
		{
			return 1;
		}
		
		while(numTeams % 2 == 0)
		{
			numTeams = numTeams / 2;
			rounds++;
		}
			
		if (numTeams == 1)
		{
			return rounds;
		}
		
		// Return if error
		return -1;		
	}
	
	
	// TODO: Remove hardcoded scheduling method
	public String generateBrackets()
	{
		String bracketString = "";
		int roundsToSchedule = numRounds;
		
		ArrayList<Match> round1 = tournamentSchedules.get(0).closestElo();
		int bracketSize = round1.size() * 2;
		
		roundsToSchedule = numRounds - 1;
		
		while (roundsToSchedule > 0)
		{
			ArrayList<Team> placeholderTeams = new ArrayList<>();
			
			bracketSize = bracketSize / 2;
			
			for (int i = 0; i < bracketSize; i++)
			{
				Team team = new Team("Unscheduled");
				placeholderTeams.add(team);
			}
			
			Schedule sched = new Schedule(placeholderTeams);
			sched.inOrder();
			tournamentSchedules.add(sched);
			
			roundsToSchedule--;			
		}
		
		for (int i = 0; i < tournamentSchedules.size(); i++)
		{
			bracketString += "Round " + (i+1) + "\n\n";
			for (int j = 0; j < tournamentSchedules.get(i).getMatches().size(); j++)
			{
				Match m = tournamentSchedules.get(i).getMatches().get(j);
				bracketString += "Match " + m.getMatchID() + " " + m.getFirstTeam().getTeamName() + " vs " +  m.getSecondTeam().getTeamName() + "\n\n";
				
			}
		}
		
		
		
		
		return bracketString;
	}
	
	public void showLeaderboard()
	{
		//TODO
	}

	public int getNumRounds() {
		return numRounds;
	}

}
