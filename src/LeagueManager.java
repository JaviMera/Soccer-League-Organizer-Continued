import com.mera.model.Team;
import com.teamtreehouse.model.Menu;
import com.teamtreehouse.model.Player;
import com.teamtreehouse.model.Players;

import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class LeagueManager {

    public static void main(String[] args) {

        Menu leagueMenu = new Menu(new InputStreamReader(System.in));
        Set<Team> teams = new TreeSet<>();
        Player[] players = Players.load();

        System.out.printf("There are currently %d registered players.%n", players.length);
        // Your code here!
        int optionSelected;

        do {
            leagueMenu.displayTitle();
            leagueMenu.displayOptions();
            optionSelected = leagueMenu.getOption("Enter option: ");

            switch (optionSelected) {
                case 1:
                    String teamName = leagueMenu.getName("Enter team name: ");
                    String coachName = leagueMenu.getName("Enter coach name: ");
                    teams.add(new Team(teamName, coachName));

                    break;

                case 2:
                    if(teams.isEmpty()) {

                        leagueMenu.displayEmptyTeamTitle();
                    }
                    else {

                        Map<Integer, Team> numberedTeams = sortTeamsByName(teams);
                        leagueMenu.displayTeams(numberedTeams);
                    }
                    break;
                case 3:

                    break;
                case 5:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid option master of the mountains. Try again");
                    break;
            }
        }while(optionSelected != 5);
    }

    private static Map<Integer, Team> sortTeamsByName(Set<Team> teams)
    {
        Map<Integer, Team> numberedTeams = new HashMap<>();
        teams.forEach(team -> numberedTeams.put(numberedTeams.size() + 1, team));

        return numberedTeams;
    }
}
