import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToeFrame extends JFrame {
    private JButton[][] boardButtons = new JButton[3][3];
    private JTextArea messageArea;
    private char currentPlayer = 'X';
    private char[][] board = new char[3][3];
    private String playerXName;
    private String playerOName;

    public TicTacToeFrame() {
        playerXName = JOptionPane.showInputDialog(this, "Enter name for Player X:");
        playerOName = JOptionPane.showInputDialog(this, "Enter name for Player O:");

        if (playerXName == null || playerXName.isEmpty()) {
            playerXName = "Player X";
        }
        if (playerOName == null || playerOName.isEmpty()) {
            playerOName = "Player O";
        }

        setTitle("Tic Tac Toe");
        setSize(400, 500);  // Increased size to accommodate new buttons
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(3, 3));
        initializeBoard(boardPanel);

        messageArea = new JTextArea();
        messageArea.setEditable(false);

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());

        JButton quitButton = new JButton("Quit");
        quitButton.setBackground(Color.RED);
        quitButton.setForeground(Color.BLACK);
        quitButton.setFont(new Font("Arial", Font.BOLD, 16));
        quitButton.addActionListener(e -> System.exit(0));

        JButton replayButton = new JButton("Replay");
        replayButton.setBackground(Color.GREEN);
        replayButton.setForeground(Color.BLACK);
        replayButton.setFont(new Font("Arial", Font.BOLD, 16));
        replayButton.addActionListener(e -> resetBoard());

        JButton changeNameButton = new JButton("Change Player Name");
        changeNameButton.setBackground(Color.YELLOW);
        changeNameButton.setForeground(Color.BLACK);
        changeNameButton.setFont(new Font("Arial", Font.BOLD, 16));
        changeNameButton.addActionListener(e -> changePlayerNames());

        controlPanel.add(replayButton);
        controlPanel.add(changeNameButton);
        controlPanel.add(quitButton);

        add(boardPanel, BorderLayout.CENTER);
        add(messageArea, BorderLayout.SOUTH);
        add(controlPanel, BorderLayout.NORTH);

        resetBoard();
    }

    private void initializeBoard(JPanel boardPanel) {
        ButtonListener listener = new ButtonListener();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                boardButtons[i][j] = new JButton("");
                boardButtons[i][j].setFont(new Font("Arial", Font.PLAIN, 60));
                boardButtons[i][j].addActionListener(listener);
                boardPanel.add(boardButtons[i][j]);
            }
        }
    }

    private void resetBoard() {
        currentPlayer = 'X';
        updateMessage();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = ' ';
                boardButtons[i][j].setText("");
                boardButtons[i][j].setEnabled(true);
            }
        }
    }

    private void changePlayerNames() {
        playerXName = JOptionPane.showInputDialog(this, "Enter new name for Player X:");
        playerOName = JOptionPane.showInputDialog(this, "Enter new name for Player O:");

        if (playerXName == null || playerXName.isEmpty()) {
            playerXName = "Player X";
        }
        if (playerOName == null || playerOName.isEmpty()) {
            playerOName = "Player O";
        }

        updateMessage();
    }

    private void updateMessage() {
        if (currentPlayer == 'X') {
            messageArea.setText(playerXName + "'s turn (X)");
        } else {
            messageArea.setText(playerOName + "'s turn (O)");
        }
    }

    private class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton clickedButton = (JButton) e.getSource();
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (boardButtons[i][j] == clickedButton && board[i][j] == ' ') {
                        board[i][j] = currentPlayer;
                        boardButtons[i][j].setText(String.valueOf(currentPlayer));
                        boardButtons[i][j].setEnabled(false);
                        if (checkForWin()) {
                            messageArea.setText((currentPlayer == 'X' ? playerXName : playerOName) + " wins! Click Replay to play again.");
                            disableAllButtons();
                            return;
                        }
                        if (checkForTie()) {
                            messageArea.setText("It's a tie! Click Replay to play again.");
                            return;
                        }
                        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
                        updateMessage();
                    }
                }
            }
        }

        private boolean checkForWin() {
            // Check rows, columns, and diagonals for a win
            for (int i = 0; i < 3; i++) {
                if ((board[i][0] == currentPlayer && board[i][1] == currentPlayer && board[i][2] == currentPlayer) ||
                        (board[0][i] == currentPlayer && board[1][i] == currentPlayer && board[2][i] == currentPlayer)) {
                    return true;
                }
            }
            if ((board[0][0] == currentPlayer && board[1][1] == currentPlayer && board[2][2] == currentPlayer) ||
                    (board[0][2] == currentPlayer && board[1][1] == currentPlayer && board[2][0] == currentPlayer)) {
                return true;
            }
            return false;
        }

        private boolean checkForTie() {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == ' ') {
                        return false;
                    }
                }
            }
            return true;
        }

        private void disableAllButtons() {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    boardButtons[i][j].setEnabled(false);
                }
            }
        }
    }
}
