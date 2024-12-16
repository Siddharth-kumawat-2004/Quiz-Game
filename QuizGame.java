import java.io.*;
import java.util.*;

class QuizGame {

    static Scanner scanner = new Scanner(System.in);
    static Map<String, Integer> scores = new HashMap<>();

    public static void main(String[] args) {
        System.out.println("Welcome to the Quiz Game!");
        System.out.println("1. Register and Play\n2. Exit");
        System.out.print("Choose an option: ");

        int choice = scanner.nextInt();
        scanner.nextLine(); 

        if (choice == 1) {
            System.out.print("Enter your name: ");
            String playerName = scanner.nextLine();

            System.out.println("\nHello, " + playerName + "! Let's start the quiz.");

            int score = startQuiz();
            scores.put(playerName, score);

            System.out.println("\nYour final score: " + score);
            displayScoreboard();
        } else {
            System.out.println("Thank you for visiting! Goodbye.");
        }
    }

    private static int startQuiz() {
        int score = 0;
        List<String[]> questions = loadQuestionsFromFile("questions.txt");

        if (questions.isEmpty()) {
            System.out.println("No questions available. Please check the file.");
            return score;
        }

        Collections.shuffle(questions); 

        for (String[] question : questions) {
            System.out.println("\n" + question[0]);
            for (int i = 1; i <= 4; i++) {
                System.out.println(i + ". " + question[i]);
            }

            System.out.print("Your answer (1-4): ");
            int answer = scanner.nextInt();

            if (answer == Integer.parseInt(question[5])) {
                System.out.println("Correct!");
                score++;
            } else {
                System.out.println("Wrong! The correct answer was: " + question[Integer.parseInt(question[5])]);
            }
        }
        return score;
    }

    private static List<String[]> loadQuestionsFromFile(String fileName) {
        List<String[]> questions = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";", 6);
                if (parts.length == 6) {
                    questions.add(parts);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        return questions;
    }

    private static void displayScoreboard() {
        System.out.println("\nScoreboard:");
        for (Map.Entry<String, Integer> entry : scores.entrySet()) {
            System.out.println("Player: " + entry.getKey() + " | Score: " + entry.getValue());
        }
    }
}
