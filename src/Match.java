import java.util.Random;

public class Match {
	
	private Team firstTeam, secondTeam, result;
	private int firstTeamScore, secondTeamScore;
	private String matchID;
	Random random = new Random();
	
	public Match()
	{
		firstTeam = new Team("TBD");
		secondTeam = new Team("TBD");
		matchID = String.valueOf(random.nextInt(1000));
	}
	
	public Match(Team firstTeam, Team secondTeam)
	{
		this.firstTeam = firstTeam;
		this.secondTeam = secondTeam;
		matchID = String.valueOf(random.nextInt(1000));
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
	
	public String getMatchID() 
	{
		return matchID;
	}
	
	public void setMatchID(String matchID) 
	{
		this.matchID = matchID;
	}
	
}
