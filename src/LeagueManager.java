import com.mera.model.Team;
import com.mera.model.Teams;
import com.teamtreehouse.model.Menu;
import com.teamtreehouse.model.Player;
import com.teamtreehouse.model.Players;

import java.io.InputStreamReader;
import java.util.*;

public class LeagueManager {

    public static void main(String[] args) {

        Menu leagueMenu = new Menu(new InputStreamReader(System.in));
        Set<Team> originalTeamSet = new TreeSet<>();
        Player[] players = Players.load();
        Set<Player> sortedPlayers = new TreeSet<>(Arrays.asList(players));

        System.out.printf("There are currently %d registered players.%n", players.length);

        boolean playerRemoved;
        int optionSelected;
        Team team;
        Player player;
        Map<Integer, Team> mapTeamsWithOptions = new HashMap<>();

        do {
            leagueMenu.displayTitle();
            leagueMenu.displayOptions();
            optionSelected = leagueMenu.getOption("Enter option: ");

            switch (optionSelected) {
                case 1:

                    if(originalTeamSet.size() == players.length / Teams.MAX_PLAYERS){
                        leagueMenu.displayAddTeamFailure();
                        break;
                    }
                    String teamName = leagueMenu.getName("Enter team name: ");
                    String coachName = leagueMenu.getName("Enter coach name: ");
                    originalTeamSet.add(new Team(teamName, coachName));
                    mapTeamsWithOptions = Teams.mapByName(originalTeamSet);
                    break;
                case 2:
                    if (originalTeamSet.isEmpty()) {
                        leagueMenu.displayEmptyTeamTitle();
                        break;
                    }

                    team = selectTeam(leagueMenu, mapTeamsWithOptions);

                    if (team == null)
                        break;

                    player = selectPlayer(sortedPlayers, leagueMenu);

                    if (player == null)
                        break;

                    leagueMenu.displayAddedPlayer(player, team);
                    boolean added = team.addPlayer(player);

                    if (!added) {
                        leagueMenu.displayAddPlayerFailure(player);
                    } else {
                        sortedPlayers.remove(player);
                    }

                    break;
                case 3:
                    if (originalTeamSet.isEmpty()) {
                        leagueMenu.displayEmptyTeamTitle();
                        break;
                    }

                    team = selectTeam(leagueMenu, mapTeamsWithOptions);

                    if (team == null)
                        break;

                    player = selectPlayer(team.getPlayers(), leagueMenu);

                    if (player == null)
                        break;

                    leagueMenu.displayRemovedPlayer(player, team);
                    playerRemoved = team.removePlayer(player);

                    if (!playerRemoved) {
                        leagueMenu.displayRemovePlayerFailure();
                    } else {
                        sortedPlayers.add(player);
                    }

                    break;
                case 4:

                    team = selectTeam(leagueMenu, mapTeamsWithOptions);

                    if (team == null)
                        break;

                    Map<String, List<Player>> playersByHeight = team.groupByHeight();
                    Set<String> heightRanges = new TreeSet<>(playersByHeight.keySet());
                    Map<String, Map<Integer, String>> playersByHeightRange = mapPlayersByHeightRange(heightRanges, playersByHeight);

                    leagueMenu.displayTeamReport(heightRanges, playersByHeightRange);

                    break;
                case 5:
                    leagueMenu.displayLeagueBalanceReport(originalTeamSet);
                    break;
                case 6:
                    if (originalTeamSet.isEmpty()) {
                        leagueMenu.displayEmptyTeamTitle();
                        break;
                    }

                    team = selectTeam(leagueMenu, mapTeamsWithOptions);

                    if (team == null)
                        break;

                    team.getPlayers();
                    leagueMenu.displayTeamRoster(team.getPlayers());
                    break;
                case 7:
                    System.out.println("Exiting...");
                    break;
                default:
                    break;
            }
        } while (optionSelected != 7);
    }

    private static Team selectTeam(Menu leagueMenu, Map<Integer, Team> numberedTeams) {
        leagueMenu.displayTeams(numberedTeams);
        int teamSelected = leagueMenu.getOption("Select a team: ");

        if (teamSelected == -1 || (teamSelected < 0 && teamSelected >= numberedTeams.size()))
            return null;

        return numberedTeams.get(teamSelected);
    }

    private static Player selectPlayer(Set<Player> players, Menu leagueMenu) {
        Map<Integer, Player> numberedPlayers = Players.mapByName(players);
        leagueMenu.displayPlayers(numberedPlayers);
        int playerSelected = leagueMenu.getOption("Select a player: ");

        if (playerSelected == -1 || (playerSelected < 0 && playerSelected >= players.size()))
            return null;

        return numberedPlayers.get(playerSelected);
    }

    private static Map<String, Map<Integer,String>> mapPlayersByHeightRange(Set<String> heightRanges, Map<String, List<Player>> playersByHeight)
    {
        Map<String, Map<Integer,String>> playersByHeightRange = new HashMap<>();

        String playerNames = "";
        for (String key : heightRanges) {
            for (Player p : playersByHeight.get(key)) {
                playerNames += p.getLastName() + " " + p.getFirstName() + ", ";
            }

            Map<Integer, String> heightCount = new HashMap<>();
            heightCount.put(playersByHeight.get(key).size(), playerNames);

            playersByHeightRange.put(key, heightCount);
            playerNames = "";
        }

        return playersByHeightRange;
    }
}