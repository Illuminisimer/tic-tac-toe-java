// Import necessary Java Swing and AWT packages for GUI components
import javax.swing.*;  // For JFrame, JButton, JPanel, JOptionPane
import java.awt.*;     // For Dimension, Toolkit, BorderLayout, GridLayout, Font

/**
 * A classic Tic Tac Toe game implementation using Java Swing
 */
public class TicTacToe {

    // Array to hold all 9 buttons representing the game board
    static JButton[] buttons = new JButton[9];
    // Tracks which player's turn it is (true for X, false for O)
    static boolean playerXTurn = true;
    // Flag to indicate if the game has concluded (win or draw)
    static boolean gameOver = false;

    /**
     * Main method - entry point of the application
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        // Create the main application window
        JFrame frame = new JFrame("Tic Tac Toe");
        // Ensure application exits when window is closed
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Get the screen dimensions for responsive window sizing
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        // Set window height to 70% of screen height
        int height = (int) (screenSize.height * 0.7);
        // Calculate width to maintain 16:9 aspect ratio
        int width = (height * 9) / 16;

        // Set the calculated window dimensions
        frame.setSize(width, height);
        // Center the window on the screen
        frame.setLocationRelativeTo(null);

        // Use BorderLayout for main window organization
        frame.setLayout(new BorderLayout());

        // Create panel for the 3x3 game board
        JPanel boardPanel = new JPanel();
        // Use GridLayout for evenly spaced buttons
        boardPanel.setLayout(new GridLayout(3, 3));

        // Initialize all 9 buttons for the game board
        for (int i = 0; i < 9; i++) {
            buttons[i] = new JButton("");  // Empty button with no text
            // Set large, bold font for X and O symbols
            buttons[i].setFont(new Font("Arial", Font.BOLD, 40));

            // Need final variable for use in lambda expression
            final int index = i;

            // Add action listener to handle button clicks
            buttons[i].addActionListener(e -> handleMove(index));

            // Add button to the game board panel
            boardPanel.add(buttons[i]);
        }

        // Create restart button to reset the game
        JButton resetButton = new JButton("Restart");
        // Set appropriate font size for restart button
        resetButton.setFont(new Font("Arial", Font.BOLD, 20));

        // Add action listener to reset game when clicked
        resetButton.addActionListener(e -> resetGame());

        // Add game board to center of window
        frame.add(boardPanel, BorderLayout.CENTER);
        // Add restart button to bottom of window
        frame.add(resetButton, BorderLayout.SOUTH);

        // Make the window visible
        frame.setVisible(true);
    }

    /**
     * Handles player moves when a button is clicked
     * @param i Index of the clicked button (0-8)
     */
    static void handleMove(int i) {
        // Ignore clicks if game is already over
        if (gameOver)
            return;
        // Ignore clicks on already occupied buttons
        if (!buttons[i].getText().equals(""))
            return;

        // Handle X player's turn
        if (playerXTurn) {
            buttons[i].setText("X");

            // Check if X has won
            if (checkWin("X")) {
                gameOver = true;
                // Show win message
                JOptionPane.showMessageDialog(null, "X Wins!");
                return;
            }
        } 
        // Handle O player's turn
        else {
            buttons[i].setText("O");

            // Check if O has won
            if (checkWin("O")) {
                gameOver = true;
                // Show win message
                JOptionPane.showMessageDialog(null, "O Wins!");
                return;
            }
        }

        // Switch turns between players
        playerXTurn = !playerXTurn;

        // Check for a draw (no winner and board full)
        if (checkDraw()) {
            gameOver = true;
            // Show draw message
            JOptionPane.showMessageDialog(null, "It's a Draw!");
        }
    }

    /**
     * Checks if the specified player has won
     * @param player The player to check ("X" or "O")
     * @return true if player has won, false otherwise
     */
    static boolean checkWin(String player) {
        // All possible winning combinations (rows, columns, diagonals)
        int[][] winPatterns = {
                {0, 1, 2}, {3, 4, 5}, {6, 7, 8},  // Rows
                {0, 3, 6}, {1, 4, 7}, {2, 5, 8},  // Columns
                {0, 4, 8}, {2, 4, 6}              // Diagonals
        };

        // Check each possible winning pattern
        for (int[] p : winPatterns) {
            if (buttons[p[0]].getText().equals(player) &&
                buttons[p[1]].getText().equals(player) &&
                buttons[p[2]].getText().equals(player)) {
                return true;  // Winning pattern found
            }
        }

        return false;  // No winning pattern found
    }

    /**
     * Checks if the game is a draw (no winner and board full)
     * @return true if game is a draw, false otherwise
     */
    static boolean checkDraw() {
        // Check if any button is still empty
        for (JButton b : buttons) {
            if (b.getText().equals("")) {
                return false;  // Game not over yet
            }
        }
        return true;  // All buttons filled with no winner
    }

    /**
     * Resets the game to initial state
     */
    static void resetGame() {
        // Reset turn to X player
        playerXTurn = true;
        // Reset game over flag
        gameOver = false;

        // Clear all buttons
        for (JButton b : buttons) {
            b.setText("");
        }
    }
}
