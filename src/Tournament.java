import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import org.json.*;


public class Tournament {
	
	private int tournamentID;
	private int numRounds;
	private ArrayList<Schedule> tournamentSchedules = new ArrayList<>();
	private ArrayList<Team> toSchedule = new ArrayList<>();
	private SchedulingMethods schedulingMethod = SchedulingMethods.IN_ORDER;
	
	
	public Tournament(int ID, ArrayList<Team> toSchedule, SchedulingMethods schedulingMethod)
	{		
		tournamentID = ID;
		this.toSchedule = toSchedule;
		this.schedulingMethod = schedulingMethod;

	}
	
	public Tournament(int ID, ArrayList<Team> toSchedule)
	{		
		tournamentID = ID;
		this.toSchedule = toSchedule;

	}
	
	public Tournament(int ID, SchedulingMethods schedulingMethod)
	{		
		tournamentID = ID;
		this.schedulingMethod = schedulingMethod;

	}
	
	public Tournament(int ID)
	{
		tournamentID = ID;
	}
			
	
	public int getTournamentID() 
	{
		return tournamentID;
		
	}

	public void setTournamentID(int tournamentID) 
	{
		this.tournamentID = tournamentID;
	}
	
	public SchedulingMethods getSchedulingMethod() 
	{
		return schedulingMethod;
	}

	public void setSchedulingMethod(SchedulingMethods schedulingMethod) 
	{
		this.schedulingMethod = schedulingMethod;
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
	
	// TODO: Give potential teams for next brackets
	// TODO: Auto finalise matches
	// TODO: Refactor in to smaller methods	
	public String generateBrackets()	
	{
		Schedule round1 = new Schedule(toSchedule);
		try
		{
			round1.checkValid();
		}
		
		catch (InvalidNumTeamsException e)
		{
			System.err.print("Invalid number of teams");
			// TODO: Cut off team to make this valid
		}
		
		tournamentSchedules.add(round1);
		numRounds = calculateRounds(round1);
		
		String bracketString = "";
		int roundsToSchedule = numRounds;
		
		ArrayList<Match> round1Matches;
		
		switch (schedulingMethod)
		{
		case CLOSEST_ELO:
			round1Matches = tournamentSchedules.get(0).closestElo();
			break;
		case BEST_WITH_WORST:
			round1Matches = tournamentSchedules.get(0).bestWithWorst();
			break;
		case RANDOM_MATCHES:
			round1Matches = tournamentSchedules.get(0).randomMatches();
			break;
		case IN_ORDER:
		default:
			round1Matches = tournamentSchedules.get(0).inOrder();
			break;
		}
		
		int bracketSize = round1Matches.size() * 2;
		
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
	
	public void importFromFile(Path p) throws DataLoadingException
	{
		try
		{
			if (p == null)
			{
				throw new DataLoadingException();
			}
			BufferedReader br = Files.newBufferedReader(p);
			String json = "";
			String line = "";
			
			while((line = br.readLine()) != null)
			{
				json += line;
			} 
			
			br.close();
			
			JSONArray root = new JSONArray(json);
			
			for (int i = 0; i < root.length(); i++)
			{
				JSONObject currentTeam = root.getJSONObject(i);
				Team t = new Team(currentTeam.getString("teamName"), currentTeam.getInt("elo"), currentTeam.getInt("teamID"));
				toSchedule.add(t);	
			}
			
		}
		
		catch (IOException | JSONException e)
		{
			throw new DataLoadingException();
		}
	}

}
