public class TicTacToeRunner {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            TicTacToeFrame frame = new TicTacToeFrame();
            frame.setVisible(true);
        });
    }
}
