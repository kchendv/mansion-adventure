import java.io.IOException;
import java.util.Scanner;
import student.adventure.GameEngine;

public class Main {
    public static void main(String[] args) throws IOException {
        GameEngine game = new GameEngine("src/main/resources/dungeon.json");

        Scanner userInput = new Scanner(System.in);
        game.playGame(userInput, true);
    }
}