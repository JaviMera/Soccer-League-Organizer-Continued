import com.teamtreehouse.model.Menu;
import com.teamtreehouse.model.Player;
import com.teamtreehouse.model.Players;

public class LeagueManager {

    public static void main(String[] args) {
        Menu leagueMenu = new Menu(System.in);
        Player[] players = Players.load();

        System.out.printf("There are currently %d registered players.%n", players.length);
        // Your code here!
        int optionSelected;

        do {
            leagueMenu.displayOptions();
            optionSelected = leagueMenu.getOption("Enter option: ");

            switch (optionSelected) {
                case 1:
                    System.out.println("You want to create a new team, but you can't now. I haven't coded the logic yet xD");
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
