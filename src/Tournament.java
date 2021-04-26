import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import org.json.*;
import com.itextpdf.kernel.pdf.PdfDocument; 
import com.itextpdf.kernel.pdf.PdfWriter; 
import com.itextpdf.layout.Document; 
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;  

public class Tournament {
	
	private String tournamentName;
	private int tournamentID;
	private int numRounds;
	private ArrayList<Schedule> tournamentSchedules = new ArrayList<>();
	private ArrayList<Team> toSchedule = new ArrayList<>();
	private SchedulingMethods schedulingMethod = SchedulingMethods.IN_ORDER;
	private String generatedTournament;
	private JSONObject cachedJSON;
	
	
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
	
	public String getTournamentName()
	{
		return tournamentName;
	}
	
	public void setTournamentName(String tournamentName)
	{
		this.tournamentName = tournamentName;
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
				Team team = new Team("TBD");
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
		
		generatedTournament = bracketString;
		return bracketString;
	}
	
	public ArrayList<Match> getScheduledMatches()
	{
		ArrayList<Match> scheduledMatches = new ArrayList<Match>();
		
		for (int i = 0; i < tournamentSchedules.size(); i++)
		{
			for (int j = 0; j < tournamentSchedules.get(i).getMatches().size(); j++)
			{
				scheduledMatches.add(tournamentSchedules.get(i).getMatches().get(j));
			}
		}
		
		return scheduledMatches;		
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
			
			JSONObject root = new JSONObject(json);
			cachedJSON = root;
			
			String name = root.getString("tournamentName");
			String scedulingMethod = root.getString("scheduleType");
			
			setTournamentName(name);
			//schedulingMethod = SchedulingMethods.valueOf(scedulingMethod);
			
			JSONArray teams = root.getJSONArray("teams");
			
			for (int i = 0; i < teams.length(); i++)
			{
				JSONObject currentTeam = teams.getJSONObject(i);
				Team t = new Team(currentTeam.getString("teamName"), currentTeam.getInt("elo"), currentTeam.getInt("ID"));
				toSchedule.add(t);	
			}
			
		}
		
		catch (IOException | JSONException e)
		{
			throw new DataLoadingException();
		}
	}
	
	public void generatePDF(String dest)
	{
		
		try
		{
			PdfWriter writer = new PdfWriter(dest);
			PdfDocument pdf = new PdfDocument(writer);
			Document document = new Document(pdf);
			
			Paragraph para1 = new Paragraph(tournamentName);
			Paragraph para2 = new Paragraph(generatedTournament);
			para1.setBold();
			para1.setFontSize(16);
			para2.setFontSize(14);
			para1.setTextAlignment(TextAlignment.CENTER);
			para1.setPaddingBottom(50);
			para2.setTextAlignment(TextAlignment.CENTER);
			document.add(para1);
			document.add(para2);
			
			document.close();
		}
		
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		
	}
	
	public void exportJSON(String dest)
	{
		JSONArray matches = cachedJSON.getJSONArray("matches");
		
		for (Match m : getScheduledMatches())
		{
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("matchID", m.getMatchID());
			jsonObject.put("firstTeam", m.getFirstTeam().getTeamName());
			jsonObject.put("secondTeam", m.getSecondTeam().getTeamName());
			jsonObject.put("result", "");
			
			matches.put(jsonObject);
		}
		
		 try 
		 {
	         FileWriter file = new FileWriter(dest);
	         file.write(cachedJSON.toString(2));
	         file.close();
	     } 
		 catch (IOException e) 
		 {
	         e.printStackTrace();
		 }
		
	}
	
	

}
