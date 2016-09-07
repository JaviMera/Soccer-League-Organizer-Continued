import com.mera.model.Team;
import com.teamtreehouse.model.Menu;
import com.teamtreehouse.model.Player;
import com.teamtreehouse.model.Players;

import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;
import java.util.Map;

public class LeagueManager {

    public static void main(String[] args) {

        Menu leagueMenu = new Menu(new InputStreamReader(System.in));
        Set<Team> teams = new TreeSet<>();
        Player[] players = Players.load();

        System.out.printf("There are currently %d registered players.%n", players.length);

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

                        Team team = selectTeam(teams, leagueMenu);

                        if(team != null) {
                            Set<Player> orderedPlayers = sortPlayersByName(players);
                            Player player = selectPlayer(orderedPlayers, leagueMenu);

                            if (player != null) {
                                leagueMenu.displayAddedPlayer(player, team);
                                boolean added = team.addPlayer(player);

                                if (!added) {
                                    leagueMenu.displayAddPlayerFailure(player);
                                }
                            }
                        }
                    }
                    break;
                case 3:
                    if(teams.isEmpty()) {

                        leagueMenu.displayEmptyTeamTitle();
                    }
                    else {

                        Team team = selectTeam(teams, leagueMenu);

                        if(team != null)
                        {
                            Player player = selectPlayer(team.getPlayers(), leagueMenu);

                            if(player != null)
                            {
                                leagueMenu.displayRemovedPlayer(player, team);
                                team.removePlayer(player);
                            }
                        }
                    }
                    break;
                case 4:
                    Team team = selectTeam(teams, leagueMenu);

                    if(team != null)
                    {
                        leagueMenu.displayTeamReport(team);
                    }

                    break;
                case 5:
                    leagueMenu.displayLeagueBalanceReport(teams);
                    break;
                case 6:
                    System.out.println("Exiting...");
                    break;
                default:
                    break;
            }
        }while(optionSelected != 6);
    }

    private static Team selectTeam(Set<Team> teams, Menu leagueMenu)
    {
        Map<Integer, Team> numberedTeams = createTeamsMap(teams);
        leagueMenu.displayTeams(numberedTeams);
        int teamSelected = leagueMenu.getOption("Select a team: ");

        if(teamSelected == -1 || (teamSelected < 0 && teamSelected >= teams.size()))
            return null;

        return numberedTeams.get(teamSelected);
    }

    private static Player selectPlayer(Set<Player> players, Menu leagueMenu)
    {
        Map<Integer, Player> numberedPlayers = createPlayerMap(players);
        leagueMenu.displayPlayers(numberedPlayers);
        int playerSelected = leagueMenu.getOption("Select a player: ");

        if(playerSelected == -1 || (playerSelected < 0 && playerSelected >= players.size()))
            return null;

        return numberedPlayers.get(playerSelected);
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
