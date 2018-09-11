package tsi.stanislaw.gmn;

import java.util.*;

public class Main {
    private static Random rand = new Random();
    private static Scanner scan = new Scanner(System.in);
    private static List<GameResult> results = new ArrayList<>();

    public static void main(String[] args) {

        System.out.println("Start a new game?");
        String answer = ask();

        if (answer.equals("No")) {
            System.exit(0);
        } else if (answer.equals("Yes")) {

            do {
                System.out.println("Enter your name.");
                String userName = scan.next();

                long t1 = System.currentTimeMillis();

                int myNum = rand.nextInt(100) + 1;
                System.out.println(myNum);
                boolean userLost = true;
                boolean userWin = false;

                for (int i = 1; i < 11; i++) {
                    System.out.println("It's your " + i + " try.");
                    int userNum = askNum();

                    if (userNum > myNum) {
                        System.out.println("Too much!");
                    } else if (userNum < myNum) {
                        System.out.println("Too little!");
                    } else {
                        System.out.println("You've guessed my number!");
                        userLost = false;
                        userWin = true;
                        long t2 = System.currentTimeMillis();
                        GameResult r = new GameResult();
                        r.userNames = userName;
                        r.triesCount = i;
                        r.userTime = (t2 - t1) / 1000;
                        results.add(r);
                        break;
                    }
                }

                if (userLost) {
                    System.out.println("Try again?");
                    answer = ask();
                }

                if (userWin) {
                    System.out.println("Play again?");
                    answer = ask();
                }
            } while (answer.equals("Yes"));

            showResults();
        }
    }

    private static void showResults() {
        for (GameResult r : results) {
            System.out.println(r.userNames + " - " + r.triesCount + " tries - " + r.userTime + " seconds.");
        }
    }

    private static String ask() {
        String answer;
        do {
            System.out.println("Yes or No");
            answer = scan.next();
        } while (!answer.equals("Yes") && !answer.equals("No"));
        return answer;
    }

    private static int askNum() {
        int answer;
        do {
            try {
                answer = scan.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Only numbers!");
                scan.next();
                continue;
            }
            if (answer < 1 || answer > 100) {
                System.out.println("Only numbers between 1 - 100");
            } else {
                return answer;
            }
        } while (true);
    }
}
