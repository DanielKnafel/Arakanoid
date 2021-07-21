// ID: 316012624

package highscoretracker;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Scanner;

/**
 * A class for writing and reading high scores from a file.
 * @author Daniel Knafel
 * ID: 316012624
 */
public class HighScoreWriter {
    private String fileName;
    private String fileText;

    /**
     * Constructor.
     */
    public HighScoreWriter() {
        this.fileName = "highscores.txt";
        this.fileText = "The highest score so far is: ";
    }

    /**
     * Write a given high score to the file.
     * @param highScore score to be written.
     */
    public void storeHighScore(int highScore) {
        File highScoreFile = new File(this.fileName);
        try {
            // Try creating a new file.
            if (highScoreFile.createNewFile()) {
                write(highScore, highScoreFile);
            // If the file already exists, check its high score.
            } else {
                int currentHighScore = getCurrentHighScore();
                if (currentHighScore < highScore) {
                    write(highScore, highScoreFile);
                }
            }
          } catch (IOException e) {
            System.out.println("Cannot open highScore file\n");
            e.printStackTrace();
          }
    }

    /**
     * Write a given high score to a given file.
     * @param number the number to be written.
     * @param file the file to write to.
     */
    private void write(int number, File file) {
        try {
            // Write the current high score to the new file.
            Writer writer = new FileWriter(this.fileName);
            writer.write(this.fileText + number);
            writer.close();
        } catch (IOException e) {
            System.out.println("An error writing to the highscore file\n");
            e.printStackTrace();
        }
    }

    /**
     * Return the current high score stored in the file.
     * @return current high score stored in the file, if exists. -1 otherwise.
     */
    public int getCurrentHighScore() {
        File highScoreFile = new File(this.fileName);
        int currentHighScore = -1;
        try {
            Scanner scanner = new Scanner(highScoreFile);
            String text = scanner.nextLine();
            scanner.close();
            text = text.substring(this.fileText.length(), text.length());
            currentHighScore = Integer.parseInt(text);
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found!");
            e.printStackTrace();
        }
        return currentHighScore;
    }
}