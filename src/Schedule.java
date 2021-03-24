import java.util.ArrayList;
import java.util.Collections;

public class Schedule {
	
	private ArrayList<Team> teamsToSchedule;
	private ArrayList<Match> matches;
	
	public Schedule(ArrayList<Team> teamsToSchedule)
	{
		this.teamsToSchedule = teamsToSchedule;
	}
	
	// Check if a knockout tournament is valid with given number of teams
	private boolean checkValid()
	{
		int numTeams = teamsToSchedule.size();
		if (numTeams < 2)
		{
			return false;
		}
		
		while(numTeams % 2 == 0)
		{
			numTeams = numTeams / 2;
		}
			
		if (numTeams == 1)
		{
			return true;
		}
		
		return false;
	}
	
	public ArrayList<Match> closestElo()
	{
		matches = new ArrayList<>();
		
		if (!checkValid())
		{
			return null;
		}
		
		Collections.sort(teamsToSchedule, Collections.reverseOrder());
		
		for (int i = 0; i < teamsToSchedule.size(); i+=2)
		{
			Match m = new Match(teamsToSchedule.get(i), teamsToSchedule.get(i+1));
			matches.add(m);			
		}
		
		return matches;		
	}
	
	public ArrayList<Match> bestWithWorst()
	{
		matches = new ArrayList<>();
		
		if (!checkValid())
		{
			return null;
		}
		
		Collections.sort(teamsToSchedule, Collections.reverseOrder());
		
		int start = 0;
		int end = teamsToSchedule.size() - 1;
		
		while (start < end)
		{
			Match m = new Match(teamsToSchedule.get(start), teamsToSchedule.get(end));
			matches.add(m);
			start++;
			end--;
		}
		
		return matches;		
	}
	
	public ArrayList<Match> randomMatches()
	{
		matches = new ArrayList<>();
		
		if (!checkValid())
		{
			return null;
		}
		
		Collections.shuffle(teamsToSchedule);
		
		for (int i = 0; i < teamsToSchedule.size(); i+=2)
		{
			Match m = new Match(teamsToSchedule.get(i), teamsToSchedule.get(i+1));
			matches.add(m);			
		}
		
		return matches;		
	}
	
	public ArrayList<Match> inOrder()
	{
		matches = new ArrayList<>();
		
		if (!checkValid())
		{
			return null;
		}
		
		for (int i = 0; i < teamsToSchedule.size(); i+=2)
		{
			Match m = new Match(teamsToSchedule.get(i), teamsToSchedule.get(i+1));
			matches.add(m);			
		}
		
		return matches;		
	}
	
	public ArrayList<Team> getWinners()
	{
		ArrayList<Team> winners = new ArrayList<>();
		
		for (Match m : matches)
		{
			winners.add(m.getResult());
		}
		
		return winners;
	}
	

}
