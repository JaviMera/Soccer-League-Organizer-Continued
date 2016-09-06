import com.mera.model.Team;
import com.teamtreehouse.model.Menu;
import com.teamtreehouse.model.Player;
import com.teamtreehouse.model.Players;

import java.io.InputStreamReader;
import java.util.*;

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

                        Map<Integer, Team> numberedTeams = createTeamsMap(teams);
                        leagueMenu.displayTeams(numberedTeams);
                        int teamSelected = leagueMenu.getOption("Select a team: ");

                        Set<Player> orderedPlayers = sortPlayersByName(players);
                        Map<Integer, Player> numberedPlayers = createPlayerMap(orderedPlayers);
                        leagueMenu.displayPlayers(numberedPlayers);
                        int playerSelected = leagueMenu.getOption("Select a player: ");

                        Team team = numberedTeams.get(teamSelected);
                        Player player = numberedPlayers.get(playerSelected);

                        team.addPlayer(player);
                        leagueMenu.displayAddedPlayer(player, team);
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

    private static Set<Player> sortPlayersByName(Player[] players)
    {
      Set<Player> orderedPlayers = new TreeSet<>();
      for (Player player : players) {
        orderedPlayers.add(player);
      }

      return orderedPlayers;
    }

    private static Map<Integer, Player> createPlayerMap(Set<Player> players)
    {
        Map<Integer, Player> numberedPlayers = new HashMap<>();
        players.forEach(player -> numberedPlayers.put(numberedPlayers.size() + 1, player));

        return numberedPlayers;
    }

    private static Map<Integer, Team> createTeamsMap(Set<Team> teams)
    {
        Map<Integer, Team> numberedTeams = new HashMap<>();
        teams.forEach(team -> numberedTeams.put(numberedTeams.size() + 1, team));

        return numberedTeams;
    }
}
