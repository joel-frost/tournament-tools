import java.util.ArrayList;
import java.util.Collections;

public class Schedule {
	
	private ArrayList<Team> teamsToSchedule;
	
	public Schedule(ArrayList<Team> teamsToSchedule)
	{
		this.teamsToSchedule = teamsToSchedule;
	}
	
	
	public ArrayList<Match> closestElo()
	{
		ArrayList<Match> matches = new ArrayList<>();
		
		// Check if even number. TODO: check for reverse factorial instead
		if (teamsToSchedule.size() % 2 != 0)
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

}
