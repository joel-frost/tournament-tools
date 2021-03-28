
public class Team implements Comparable<Team>
{
	
	private String teamName;
	private int elo;
	private int teamID;
	
	public Team(String teamName)
	{
		this.teamName = teamName;
		elo = 0;
	}
	
	public Team(String teamName, int elo)
	{
		this.teamName = teamName;
		this.elo = elo;		
	}
	
	public String getTeamName()
	{
		return teamName;
	}
	
	public void setTeamName(String teamName)
	{
		this.teamName = teamName;
	}
	
	public int getElo()
	{
		return elo;
	}
	
	public void setElo(int elo)
	{
		this.elo = elo;
	}
	
	public int getTeamID()
	{
		return teamID;
	}
	
	public void setTeamID(int teamID)
	{
		this.teamID = teamID;
	}

	@Override
	public int compareTo(Team o) {
	
		if (this.getElo() > o.getElo())
		{
			return 1;
		}
		
		if (this.getElo() < o.getElo())
		{
			return -1;
		}
		
		return 0;
	}

}
