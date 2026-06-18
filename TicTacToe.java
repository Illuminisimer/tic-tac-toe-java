import javax.swing.*;
import java.awt.*;

public class TicTacToe {

    static JButton[] buttons = new JButton[9];
    static boolean playerXTurn = true;
    static boolean gameOver = false;

    public static void main(String[] args) {

        JFrame frame = new JFrame("Tic Tac Toe");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int height = (int) (screenSize.height * 0.7);
        int width = (height * 9) / 16;

        frame.setSize(width, height);
        frame.setLocationRelativeTo(null);

        frame.setLayout(new BorderLayout());

        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(3, 3));

        for (int i = 0; i < 9; i++) {
            buttons[i] = new JButton("");
            buttons[i].setFont(new Font("Arial", Font.BOLD, 40));

            final int index = i;

            buttons[i].addActionListener(e -> handleMove(index));

            boardPanel.add(buttons[i]);
        }

        JButton resetButton = new JButton("Restart");
        resetButton.setFont(new Font("Arial", Font.BOLD, 20));

        resetButton.addActionListener(e -> resetGame());

        frame.add(boardPanel, BorderLayout.CENTER);
        frame.add(resetButton, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    static void handleMove(int i) {

        if (gameOver)
            return;
        if (!buttons[i].getText().equals(""))
            return;

        if (playerXTurn) {
            buttons[i].setText("X");

            if (checkWin("X")) {
                gameOver = true;
                JOptionPane.showMessageDialog(null, "X Wins!");
                return;
            }

        } else {
            buttons[i].setText("O");

            if (checkWin("O")) {
                gameOver = true;
                JOptionPane.showMessageDialog(null, "O Wins!");
                return;
            }
        }

        playerXTurn = !playerXTurn;

        if (checkDraw()) {
            gameOver = true;
            JOptionPane.showMessageDialog(null, "It's a Draw!");
        }
    }

    static boolean checkWin(String player) {

        int[][] winPatterns = {
                { 0, 1, 2 }, { 3, 4, 5 }, { 6, 7, 8 },
                { 0, 3, 6 }, { 1, 4, 7 }, { 2, 5, 8 },
                { 0, 4, 8 }, { 2, 4, 6 }
        };

        for (int[] p : winPatterns) {
            if (buttons[p[0]].getText().equals(player) &&
                    buttons[p[1]].getText().equals(player) &&
                    buttons[p[2]].getText().equals(player)) {
                return true;
            }
        }

        return false;
    }

    static boolean checkDraw() {
        for (JButton b : buttons) {
            if (b.getText().equals("")) {
                return false;
            }
        }
        return true;
    }

    static void resetGame() {

        playerXTurn = true;
        gameOver = false;

        for (JButton b : buttons) {
            b.setText("");
        }
    }
}