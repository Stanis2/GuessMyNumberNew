package tsi.stanislaw.gmn;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class Main {
    private static Random rand = new Random();
    private static Scanner scan = new Scanner(System.in);
    private static List<GameResult> results = new ArrayList<>();

    public static void main(String[] args) {

        loadResults();

        System.out.println("Start a new game?");
        String answer = ask();

        if (answer.equals("No")) {
            System.exit(0);
        } else if (answer.equals("Yes")) {

            do {
                System.out.println("Enter your name.");
                String userName = scan.next();

                int myNum = rand.nextInt(100) + 1;
                System.out.println(myNum);
                boolean userLost = true;
                boolean userWin = false;

                long t1 = System.currentTimeMillis();

                for (int i = 1; i < 11; i++) {
                    System.out.println("It's your " + i + " try.");
                    int userNum = askNum();

                    if (userNum > myNum) {
                        System.out.println("Too much!");
                    } else if (userNum < myNum) {
                        System.out.println("Too little!");
                    } else {
                        long t2 = System.currentTimeMillis();
                        System.out.println("You've guessed my number!");
                        userLost = false;
                        userWin = true;
                        GameResult r = new GameResult();
                        r.userName = userName;
                        r.triesCount = i;
                        r.userTime = (t2 - t1);
                        r.userScore = 1000 - (i*10 + ((t2 - t1) / 100));
                        results.add(r);
                        results.sort(Comparator.<GameResult>comparingLong(r0 -> r0.userScore).reversed());
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
            saveResults();
        }
    }

    private static void loadResults() {
        File file = new File("top_score.txt");
        try (Scanner in = new Scanner(file)) {
            while (in.hasNext()) {
                GameResult result = new GameResult();
                result.userName = in.next();
                result.triesCount = in.nextInt();
                result.userTime = in.nextLong();
                result.userScore = in.nextLong();
                results.add(result);
            }
        } catch (IOException e) {
            System.out.println("Error loading file.");
        }
    }

    private static void saveResults() {
        File file = new File("top_score.txt");
        try (PrintWriter out = new PrintWriter(file)) {
            for (GameResult r : results) {
                out.printf("%s %d %d %d\n", r.userName, r.triesCount, r.userTime, r.userScore);
            }
        } catch (IOException e) {
            System.out.println("Error saving to disk.");
        }
    }

//    private static void showResults() {
//        int c = Math.min(5, results.size());
//        for (int i = 0; i < c; i++) {
//            GameResult r = results.get(i);
//            System.out.printf("%s %d tries %d seconds %d score\n", r.userName, r.triesCount, r.userTime/1000, r.userScore);
//        }
//    }

    private static void showResults() {
        results.stream()
                .sorted(Comparator.<GameResult>comparingLong(r -> r.userScore).reversed())
                .limit(5)
                .forEach(r -> {
                    System.out.printf("%s %d tries %d seconds %d score\n", r.userName, r.triesCount, r.userTime/1000, r.userScore);
                });
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
