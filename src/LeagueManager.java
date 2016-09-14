import com.mera.model.Team;
import com.mera.model.Teams;
import com.mera.model.Menu;
import com.teamtreehouse.model.Player;
import com.teamtreehouse.model.Players;

import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.*;

public class LeagueManager {

    public static void main(String[] args) {

        Menu leagueMenu = new Menu(new InputStreamReader(System.in));
        Set<Team> originalTeamSet = new TreeSet<>();
        Player[] players = Players.load();
        Set<Player> sortedPlayers = new TreeSet<>(Arrays.asList(players));
        Queue<Player> playerWaitlist = new LinkedList<>();

        System.out.printf("There are currently %d registered players.%n", players.length);

        boolean playerRemoved;
        int optionSelected;
        Team team;
        Player player;
        Map<Integer, Team> displayableTeams = new HashMap<>();

        do {
            leagueMenu.displayTitle("***** WELCOME TO THE AMAZING SOCCER LEAGUE *****");
            leagueMenu.displayOptions();
            optionSelected = leagueMenu.getNumber("Enter option: ");

            switch (optionSelected) {

                case 1:
                    if (originalTeamSet.isEmpty()) {
                        leagueMenu.displayError("There are currently no teams in the league.");
                        break;
                    }

                    List<Player> listPlayers = new ArrayList<>(sortedPlayers);

                    originalTeamSet.forEach(t -> {
                        int p = 0;
                        int max = Teams.MAX_PLAYERS - t.size();
                        for(; p < max;) {

                            boolean canAdd = false;
                            int playerIndex = new Random().nextInt(sortedPlayers.size());
                            Player randomPlayer = listPlayers.get(playerIndex);

                            // Check in case the list is out of either experienced or inexperienced players.
                            // In this case you want to add no matter what the stats of a team are
                            if (experiencedPlayers(listPlayers) == 0 || inexperiencedPlayers(listPlayers) == 0)
                            {
                                canAdd = true;
                            }
                            // If the list contains both experienced and inexperienced players, then check the stats of the team.
                            // If the current player will not make the team weaker or stronger by a large margin, then add it.
                            else if((t.getExperienceAverage() <= 50.0f && randomPlayer.isPreviousExperience()) || (t.getExperienceAverage() > 50.0 && !randomPlayer.isPreviousExperience()))
                            {
                                canAdd = true;
                            }
                            // There's a 65% chance that the system will still build unbalanced teams :D
                            else if((new Random().nextInt(101)) >= 35)
                            {
                                canAdd = true;
                            }

                            if(canAdd)
                            {
                                t.addPlayer(randomPlayer);
                                listPlayers.remove(randomPlayer);
                                sortedPlayers.remove(randomPlayer);
                                p++;
                            }
                        }
                    });
                    break;
                case 2:

                    if(originalTeamSet.size() == players.length / Teams.MAX_PLAYERS){
                        leagueMenu.displayError("You have reached number of teams allowed in the league. Sorry :(");
                        break;
                    }
                    String teamName = leagueMenu.getString("Enter team name: ");
                    String coachName = leagueMenu.getString("Enter coach name: ");
                    originalTeamSet.add(new Team(teamName, coachName));
                    displayableTeams = Teams.mapByName(originalTeamSet);
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

                case 4:
                    leagueMenu.displayTitle("***** Adding a player to wait list *****");
                    String firstName = leagueMenu.getString("Enter first name: ");
                    String lastName = leagueMenu.getString("Enter last name: ");
                    int height = leagueMenu.getNumber("Enter height: ");
                    String isExperienced = leagueMenu
                            .getString("Is player experienced? (Y/N): ")
                            .toLowerCase();

                    Player newPlayer = new Player(firstName, lastName, height, isExperienced.equals("y"));
                    playerWaitlist.add(newPlayer);

                    leagueMenu.displayAddedPlayerInWaitlist(newPlayer);
                    break;
                case 5:
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

                case 6:
                    leagueMenu.displayTitle("***** Removing a player from the league *****");
                    player = selectPlayer(sortedPlayers, leagueMenu);

                    if (isNull(player)) {
                        leagueMenu.displayError("Invalid selection. :(");
                        break;
                    }

                    sortedPlayers.remove(player);

                    if(!playerWaitlist.isEmpty()){

                        Player nextPlayer = playerWaitlist.poll();
                        sortedPlayers.add(nextPlayer);
                    }

                    break;
                case 7:

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
                case 8:
                    leagueMenu.displayTitle("***** League Balance Report *****");
                    leagueMenu.displayLeagueBalanceReport(originalTeamSet);
                    break;
                case 9:
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
                case 10:
                    System.out.println("Exiting...");
                    break;
                default:
                    break;
            }
        } while (optionSelected != 10);
    }

    private static int experiencedPlayers(List<Player> players)
    {
        int count = 0;
        for (Player player : players) {
            if(player.isPreviousExperience())
            {
                count++;
            }
        }

        return count;
    }

    private static int inexperiencedPlayers(List<Player> players)
    {
        int count = 0;
        for (Player player : players) {
            if(!player.isPreviousExperience())
            {
                count++;
            }
        }

        return count;
    }

    private static Team selectTeam(Menu leagueMenu, Map<Integer, Team> numberedTeams) {
        leagueMenu.displayTeams(numberedTeams);
        int teamSelected = leagueMenu.getNumber("Select a team: ");

        if (teamSelected == -1 || (teamSelected < 0 && teamSelected >= numberedTeams.size()))
            return null;

        return numberedTeams.get(teamSelected);
    }

    private static Player selectPlayer(Set<Player> players, Menu leagueMenu) {
        Map<Integer, Player> numberedPlayers = mapByName(players);
        leagueMenu.displayPlayers(numberedPlayers);
        int playerSelected = leagueMenu.getNumber("Select a player: ");

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