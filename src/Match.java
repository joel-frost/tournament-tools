
public class Match {
	
	private Team firstTeam, secondTeam, result;
	private int firstTeamScore, secondTeamScore, matchID;
	
	public Match(Team firstTeam, Team secondTeam)
	{
		this.firstTeam = firstTeam;
		this.secondTeam = secondTeam;
	}
	
	public Team getFirstTeam() 
	{
		return firstTeam;
	}
	
	public void setFirstTeam(Team firstTeam) 
	{
		this.firstTeam = firstTeam;
	}
	
	public Team getSecondTeam() 
	{
		return secondTeam;
	}
	
	public void setSecondTeam(Team secondTeam) 
	{
		this.secondTeam = secondTeam;
	}
	
	public Team getResult() 
	{
		return result;
	}
	
	public void setResult(Team result) 
	{
		this.result = result;
	}
	
	public int getFirstTeamScore() 
	{
		return firstTeamScore;
	}
	
	public void setFirstTeamScore(int firstTeamScore) 
	{
		this.firstTeamScore = firstTeamScore;
	}
	
	public int getSecondTeamScore() 
	{
		return secondTeamScore;
	}
	
	public void setSecondTeamScore(int secondTeamScore) 
	{
		this.secondTeamScore = secondTeamScore;
	}
	
	public int getMatchID() 
	{
		return matchID;
	}
	
	public void setMatchID(int matchID) 
	{
		this.matchID = matchID;
	}
	
	public void resolveMatch()
	{
		if (firstTeamScore > secondTeamScore)
		{
			result = firstTeam;
		}
		
		else if (secondTeamScore > firstTeamScore)
		{
			result = secondTeam;
		}
	}
	

}
