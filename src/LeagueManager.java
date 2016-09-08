import com.mera.model.Team;
import com.mera.model.Teams;
import com.mera.model.Menu;
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
        Map<Integer, Team> displayableTeams = new HashMap<>();

        do {
            leagueMenu.displayTitle("***** WELCOME TO THE AMAZING SOCCER LEAGUE *****");
            leagueMenu.displayOptions();
            optionSelected = leagueMenu.getOption("Enter option: ");

            switch (optionSelected) {
                case 1:

                    if(originalTeamSet.size() == players.length / Teams.MAX_PLAYERS){
                        leagueMenu.displayError("You have reached number of teams allowed in the league. Sorry :(");
                        break;
                    }
                    String teamName = leagueMenu.getName("Enter team name: ");
                    String coachName = leagueMenu.getName("Enter coach name: ");
                    originalTeamSet.add(new Team(teamName, coachName));
                    displayableTeams = Teams.mapByName(originalTeamSet);
                    break;
                case 2:
                    if (originalTeamSet.isEmpty()) {
                        leagueMenu.displayError("There are currently no teams in the league.");
                        break;
                    }

                    leagueMenu.displayTitle("***** Available Teams *****");
                    team = selectTeam(leagueMenu, displayableTeams);

                    if (isNull(team)) {
                        leagueMenu.displayError("Invalid selection. :(");
                        break;
                    }

                    if(team.isFull()){
                        leagueMenu.displayError("Team is full. Can't add more players");
                        break;
                    }

                    leagueMenu.displayTitle("***** Available Players *****");
                    player = selectPlayer(sortedPlayers, leagueMenu);

                    if (isNull(player)){
                        leagueMenu.displayError("Invalid selection. :(");
                        break;
                    }

                    leagueMenu.displayAddedPlayer(player, team);
                    team.addPlayer(player);

                    // Remove player from available list
                    sortedPlayers.remove(player);

                    break;
                case 3:
                    if (originalTeamSet.isEmpty()) {
                        leagueMenu.displayError("There are currently no teams in the league.");
                        break;
                    }

                    leagueMenu.displayTitle("***** Available Teams *****");
                    team = selectTeam(leagueMenu, displayableTeams);

                    if (isNull(team)) {
                        leagueMenu.displayError("Invalid selection. :(");
                        break;
                    }

                    leagueMenu.displayTitle("***** Available Players *****", String.format("%4s%-25s%-20s", "   ", "Player Name", "Player Height") );
                    player = selectPlayer(team.getPlayers(), leagueMenu);

                    if (isNull(player)) {
                        leagueMenu.displayError("Invalid selection. :(");
                        break;
                    }

                    leagueMenu.displayRemovedPlayer(player, team);
                    team.removePlayer(player);

                    // Add player back to available list
                    sortedPlayers.add(player);

                    break;
                case 4:

                    leagueMenu.displayTitle("***** Available Teams *****");
                    team = selectTeam(leagueMenu, displayableTeams);

                    if (isNull(team)) {
                        leagueMenu.displayError("Invalid selection. :(");
                        break;
                    }

                    Map<String, List<Player>> playersByHeight = team.groupByHeight();
                    Set<String> heightRanges = new TreeSet<>(playersByHeight.keySet());
                    Map<String, Map<Integer, String>> playersByHeightRange = mapPlayersByHeightRange(heightRanges, playersByHeight);

                    leagueMenu.displayTitle("***** Team Report By Height *****");
                    leagueMenu.displayTeamReport(heightRanges, playersByHeightRange);

                    break;
                case 5:
                    leagueMenu.displayTitle("***** League Balance Report *****");
                    leagueMenu.displayLeagueBalanceReport(originalTeamSet);
                    break;
                case 6:
                    if (originalTeamSet.isEmpty()) {
                        leagueMenu.displayError("There are currently no teams in the league.");
                        break;
                    }

                    leagueMenu.displayTitle("***** Available Teams *****");
                    team = selectTeam(leagueMenu, displayableTeams);

                    if (isNull(team)){
                        leagueMenu.displayError("Invalid selection. :(");
                        break;
                    }

                    leagueMenu.displayTitle("***** Team Roster *****");
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
        Map<Integer, Player> numberedPlayers = mapByName(players);
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
        String delimeter = ", ";
        for (String key : heightRanges) {
            for (Player p : playersByHeight.get(key)) {
                playerNames += p.getLastName() + " " + p.getFirstName() + delimeter;
            }

            if(!playerNames.isEmpty())
                // Subtract 3 due to the last two characters being ',' and ' ', and cut it on the character before the comma.
                playerNames = playerNames.substring(0, playerNames.length() - delimeter.length() - 1);

            Map<Integer, String> heightCount = new HashMap<>();

            heightCount.put(playersByHeight.get(key).size(), playerNames);

            playersByHeightRange.put(key, heightCount);
            playerNames = "";
        }

        return playersByHeightRange;
    }

    private static boolean isNull(Object team)
    {
        return team == null;
    }

    public static Map<Integer, Player> mapByName(Set<Player> players)
    {
        Map<Integer, Player> numberedPlayers = new HashMap<>();
        players.forEach(player -> numberedPlayers.put(numberedPlayers.size() + 1, player));

        return numberedPlayers;
    }
}