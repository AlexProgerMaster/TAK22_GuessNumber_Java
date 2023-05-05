import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Model {
    private final int MINIMUM = 1;
    private final int MAXIMUM = 100;
    private String filename = "scoreboard.txt";
    private Scanner scanner = new Scanner(System.in);
    private int pc_number;
    private int steps;
    private boolean gameOver;
    private List<Content> scoreboard = new ArrayList<>();

    public void showMenu() {
        System.out.println("1. Play");
        System.out.println("2. Scoreboard");
        System.out.println("3. Exit");
        System.out.print("Enter 1, 2, or 3: ");
        int choice = scanner.nextInt();
        // System.out.print(choice); // Test
        switch (choice) {
            case 1:
                // System.out.println("Play"); // Test
                setupGame();
                letsPlay();
                break;
            case 2:
                // System.out.println("Scoreboard"); // Test
                showScoreboard();
                showMenu();
                break;
            case 3:
                System.out.println("Bye, bye...");
                System.exit(0);
            default: // any wrong int choice
                showMenu(); // show menu again
        }
    }

    private void setupGame() {
        pc_number = ThreadLocalRandom.current().nextInt(MINIMUM, MAXIMUM+1);
        steps = 0;
        gameOver = false;
    }
    private void ask(){
        System.out.printf("Input number between %d - %d: ", MINIMUM, MAXIMUM);
        int userNumber = scanner.nextInt();
        steps += 1;
        if(userNumber > pc_number && userNumber != 10000) {
            System.out.println("Smaller");
        } else if (userNumber < pc_number && userNumber != 10000) {
            System.out.println("Bigger");
        } else if (userNumber == pc_number && userNumber != 10000) {
            System.out.printf("You guessed the number in %d steps.%n", steps);
            gameOver = true;
        } else {
            System.out.printf("You found the back door. The correct number is %d%n", pc_number);
        }
    }
    private void letsPlay() {
        while (!gameOver) {
            ask();
        }
        askName();
        showMenu();
    }
    private void askName() {
        System.out.print("Enter your name: ");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String name;
        try {
            name = br.readLine();
            if(name.strip().length() > 1) {
                writeToFile(name);
            } else {
                System.out.println("The name is short!");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void writeToFile(String name) {
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(filename, true))) {
            String line = name + ";" + steps;
            bw.write(line); // Write line to file
            bw.newLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void showScoreboard() {
        readFromFile();
        System.out.println();
        for(Content c : scoreboard) {
            c.showData();
        }
        System.out.println();
    }

    private void readFromFile() {
        try(BufferedReader br = new BufferedReader(new FileReader(filename))) {
            scoreboard = new ArrayList<>();
            for (String line; (line = br.readLine()) != null;) {
                String name = line.split(";")[0]; // Name from file line
                int steps = Integer.parseInt(line.split(";")[1]); // steps from file line
                scoreboard.add(new Content(name, steps));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
