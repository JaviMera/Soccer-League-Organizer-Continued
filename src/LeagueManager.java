import com.mera.model.Team;
import com.sun.javafx.collections.MappingChange;
import com.teamtreehouse.model.Menu;
import com.teamtreehouse.model.Player;
import com.teamtreehouse.model.Players;

import java.util.HashMap;
import java.util.Map;

public class LeagueManager {

    public static void main(String[] args) {
        Menu leagueMenu = new Menu(System.in);
        Map<Integer, Team> teams = new HashMap<>();
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
                    teams.put(teams.size() + 1, new Team(teamName, coachName));
                    break;

                case 2:
                    //TODO: add player from a team
                    break;
                case 3:
                    leagueMenu.displayTeams(teams);
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
}
