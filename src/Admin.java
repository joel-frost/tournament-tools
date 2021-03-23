import java.nio.file.Path;

public class Admin {
	
	public void changeScore(Match m, int firstTeamScore, int secondTeamScore)
	{
		m.setFirstTeamScore(firstTeamScore);
		m.setSecondTeamScore(firstTeamScore);
	}
	
	public void importFromFile(Path p)
	{
		//TODO
	}
	
	public void saveToFile(Path p)
	{
		//TODO
	}
	
	public void adjustBracket(Match m)
	{
		//TODO: Not sure what this is supposed to do? 
	}

}
