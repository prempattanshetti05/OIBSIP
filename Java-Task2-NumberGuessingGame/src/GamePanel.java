import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Random;

public class GamePanel extends JPanel {

        // =========================
        // GAME LOGIC
        // =========================

        private Random random = new Random();

        private int secretNumber;
        private int attempts = 0;
        private int maxAttempts = 7;
        private int maxNumber = 100;

        private int round = 1;
        private int score = 0;

        private boolean gameOver = false;

        // =========================
        // COLORS
        // =========================

        private final Color BACKGROUND = new Color(15, 23, 42);
        private final Color CARD_COLOR = new Color(30, 41, 59);
        private final Color CARD_LIGHT = new Color(51, 65, 85);

        private final Color PRIMARY = new Color(99, 102, 241);
        private final Color PRIMARY_HOVER = new Color(129, 140, 248);

        private final Color TEXT = new Color(248, 250, 252);
        private final Color SECONDARY_TEXT = new Color(148, 163, 184);

        private final Color SUCCESS = new Color(34, 197, 94);
        private final Color DANGER = new Color(239, 68, 68);

        // =========================
        // UI COMPONENTS
        // =========================

        private JLabel roundLabel;
        private JLabel scoreLabel;
        private JLabel attemptsLabel;
        private JLabel attemptDotsLabel;
        private JLabel messageLabel;
        private JLabel instructionLabel;

        private JTextField guessField;

        private JButton easyButton;
        private JButton mediumButton;
        private JButton hardButton;
        private JButton guessButton;

        private JButton selectedDifficultyButton;

        // Result screen
        private JPanel resultPanel;
        private JLabel resultIconLabel;
        private JLabel resultTitleLabel;
        private JLabel resultDescriptionLabel;
        private JButton resultButton;

        // =========================
        // CONSTRUCTOR
        // =========================

        public GamePanel() {

                setBackground(BACKGROUND);

                setLayout(
                                new BorderLayout(20, 20));

                setBorder(
                                new EmptyBorder(25, 35, 25, 35));

                createHeader();
                createMainContent();

                startNewRound();
        }

        // =========================
        // HEADER
        // =========================

        private void createHeader() {

                JPanel header = new JPanel(
                                new BorderLayout());

                header.setOpaque(false);

                // Title
                JPanel titlePanel = new JPanel();

                titlePanel.setLayout(
                                new BoxLayout(
                                                titlePanel,
                                                BoxLayout.Y_AXIS));

                titlePanel.setOpaque(false);

                JLabel title = new JLabel(
                                "NUMBER GUESSING GAME");

                title.setFont(
                                new Font(
                                                "SansSerif",
                                                Font.BOLD,
                                                28));

                title.setForeground(TEXT);

                JLabel subtitle = new JLabel(
                                "Guess the secret number and beat your best score!");

                subtitle.setFont(
                                new Font(
                                                "SansSerif",
                                                Font.PLAIN,
                                                14));

                subtitle.setForeground(
                                SECONDARY_TEXT);

                titlePanel.add(title);

                titlePanel.add(
                                Box.createVerticalStrut(5));

                titlePanel.add(subtitle);

                // Stats
                JPanel statsPanel = new JPanel(
                                new GridLayout(1, 2, 15, 0));

                statsPanel.setOpaque(false);

                roundLabel = createStatLabel(
                                "ROUND " + round);

                scoreLabel = createStatLabel(
                                "SCORE " + score);

                statsPanel.add(roundLabel);
                statsPanel.add(scoreLabel);

                header.add(
                                titlePanel,
                                BorderLayout.WEST);

                header.add(
                                statsPanel,
                                BorderLayout.EAST);

                add(
                                header,
                                BorderLayout.NORTH);
        }

        // =========================
        // MAIN CONTENT
        // =========================

        private void createMainContent() {

                JPanel content = new JPanel();

                content.setOpaque(false);

                content.setLayout(
                                new BoxLayout(
                                                content,
                                                BoxLayout.Y_AXIS));

                // =========================
                // DIFFICULTY CARD
                // =========================

                JPanel difficultyCard = createCard();

                JLabel difficultyTitle = new JLabel(
                                "CHOOSE DIFFICULTY");

                difficultyTitle.setFont(
                                new Font(
                                                "SansSerif",
                                                Font.BOLD,
                                                15));

                difficultyTitle.setForeground(TEXT);

                difficultyTitle.setAlignmentX(
                                Component.CENTER_ALIGNMENT);

                JPanel difficultyButtons = new JPanel(
                                new GridLayout(1, 3, 12, 0));

                difficultyButtons.setOpaque(false);

                difficultyButtons.setMaximumSize(
                                new Dimension(600, 55));

                easyButton = createButton("EASY");
                mediumButton = createButton("MEDIUM");
                hardButton = createButton("HARD");

                // Medium is default
                selectDifficultyButton(
                                mediumButton);

                // Easy
                easyButton.addActionListener(e -> {

                        selectDifficultyButton(
                                        easyButton);

                        setDifficulty(
                                        50,
                                        10);
                });

                // Medium
                mediumButton.addActionListener(e -> {

                        selectDifficultyButton(
                                        mediumButton);

                        setDifficulty(
                                        100,
                                        7);
                });

                // Hard
                hardButton.addActionListener(e -> {

                        selectDifficultyButton(
                                        hardButton);

                        setDifficulty(
                                        200,
                                        5);
                });

                difficultyButtons.add(easyButton);
                difficultyButtons.add(mediumButton);
                difficultyButtons.add(hardButton);

                difficultyCard.add(
                                difficultyTitle);

                difficultyCard.add(
                                Box.createVerticalStrut(15));

                difficultyCard.add(
                                difficultyButtons);

                content.add(difficultyCard);

                content.add(
                                Box.createVerticalStrut(15));

                // =========================
                // GUESS CARD
                // =========================

                JPanel guessCard = createCard();

                instructionLabel = new JLabel(
                                "Guess a number between 1 and "
                                                + maxNumber);

                instructionLabel.setFont(
                                new Font(
                                                "SansSerif",
                                                Font.PLAIN,
                                                16));

                instructionLabel.setForeground(
                                SECONDARY_TEXT);

                instructionLabel.setAlignmentX(
                                Component.CENTER_ALIGNMENT);

                guessField = new JTextField();

                guessField.setFont(
                                new Font(
                                                "SansSerif",
                                                Font.BOLD,
                                                28));

                guessField.setHorizontalAlignment(
                                JTextField.CENTER);

                guessField.setForeground(TEXT);

                guessField.setBackground(
                                CARD_LIGHT);

                guessField.setCaretColor(TEXT);

                guessField.setBorder(
                                new EmptyBorder(
                                                10,
                                                20,
                                                10,
                                                20));

                guessField.setMaximumSize(
                                new Dimension(250, 60));

                // Press Enter to guess
                guessField.addActionListener(
                                e -> checkGuess());

                guessButton = createButton(
                                "GUESS  →");

                guessButton.addActionListener(
                                e -> checkGuess());

                guessButton.setMaximumSize(
                                new Dimension(250, 50));

                guessCard.add(
                                instructionLabel);

                guessCard.add(
                                Box.createVerticalStrut(15));

                guessCard.add(
                                guessField);

                guessCard.add(
                                Box.createVerticalStrut(15));

                guessCard.add(
                                guessButton);

                content.add(guessCard);

                content.add(
                                Box.createVerticalStrut(15));

                // =========================
                // BOTTOM INFORMATION
                // =========================

                JPanel bottomPanel = new JPanel(
                                new GridLayout(1, 2, 15, 0));

                bottomPanel.setOpaque(false);

                // =========================
                // ATTEMPTS CARD
                // =========================

                JPanel attemptsCard = createCard();

                JLabel attemptsTitle = new JLabel(
                                "ATTEMPTS");

                attemptsTitle.setFont(
                                new Font(
                                                "SansSerif",
                                                Font.BOLD,
                                                13));

                attemptsTitle.setForeground(
                                SECONDARY_TEXT);

                attemptsTitle.setAlignmentX(
                                Component.CENTER_ALIGNMENT);

                attemptsLabel = new JLabel(
                                "0 / " + maxAttempts);

                attemptsLabel.setFont(
                                new Font(
                                                "SansSerif",
                                                Font.BOLD,
                                                25));

                attemptsLabel.setForeground(TEXT);

                attemptsLabel.setAlignmentX(
                                Component.CENTER_ALIGNMENT);

                attemptDotsLabel = new JLabel(
                                createDots());

                attemptDotsLabel.setFont(
                                new Font(
                                                "SansSerif",
                                                Font.PLAIN,
                                                12));

                attemptDotsLabel.setForeground(
                                SUCCESS);

                attemptDotsLabel.setAlignmentX(
                                Component.CENTER_ALIGNMENT);

                attemptsCard.add(
                                attemptsTitle);

                attemptsCard.add(
                                Box.createVerticalStrut(8));

                attemptsCard.add(
                                attemptsLabel);

                attemptsCard.add(
                                Box.createVerticalStrut(8));

                attemptsCard.add(
                                attemptDotsLabel);

                // =========================
                // MESSAGE CARD
                // =========================

                JPanel messageCard = createCard();

                messageLabel = new JLabel(
                                "Make your first guess!");

                messageLabel.setFont(
                                new Font(
                                                "SansSerif",
                                                Font.BOLD,
                                                15));

                messageLabel.setForeground(
                                PRIMARY_HOVER);

                messageLabel.setHorizontalAlignment(
                                SwingConstants.CENTER);

                messageLabel.setAlignmentX(
                                Component.CENTER_ALIGNMENT);

                messageCard.add(
                                messageLabel);

                bottomPanel.add(
                                attemptsCard);

                bottomPanel.add(
                                messageCard);

                content.add(
                                bottomPanel);

                add(
                                content,
                                BorderLayout.CENTER);
        }

        // =========================
        // CREATE CARD
        // =========================

        private JPanel createCard() {

                JPanel panel = new JPanel();

                panel.setBackground(
                                CARD_COLOR);

                panel.setBorder(
                                new EmptyBorder(
                                                18,
                                                25,
                                                18,
                                                25));

                panel.setLayout(
                                new BoxLayout(
                                                panel,
                                                BoxLayout.Y_AXIS));

                return panel;
        }

        // =========================
        // CREATE BUTTON
        // =========================

        private JButton createButton(
                        String text) {

                JButton button = new JButton(text);

                button.setFont(
                                new Font(
                                                "SansSerif",
                                                Font.BOLD,
                                                13));

                button.setForeground(TEXT);

                button.setBackground(
                                PRIMARY);

                button.setFocusPainted(false);

                button.setBorderPainted(true);

                button.setBorder(
                                BorderFactory.createEmptyBorder(
                                                2,
                                                2,
                                                2,
                                                2));

                button.setOpaque(true);

                button.setCursor(
                                new Cursor(
                                                Cursor.HAND_CURSOR));

                button.addMouseListener(
                                new java.awt.event.MouseAdapter() {

                                        @Override
                                        public void mouseEntered(
                                                        java.awt.event.MouseEvent e) {

                                                button.setBackground(
                                                                PRIMARY_HOVER);
                                        }

                                        @Override
                                        public void mouseExited(
                                                        java.awt.event.MouseEvent e) {

                                                button.setBackground(
                                                                PRIMARY);
                                        }
                                });

                return button;
        }

        // =========================
        // CREATE STAT LABEL
        // =========================

        private JLabel createStatLabel(
                        String text) {

                JLabel label = new JLabel(
                                "<html><center>"
                                                + text
                                                + "</center></html>");

                label.setFont(
                                new Font(
                                                "SansSerif",
                                                Font.BOLD,
                                                12));

                label.setForeground(
                                SECONDARY_TEXT);

                label.setHorizontalAlignment(
                                SwingConstants.CENTER);

                return label;
        }

        // =========================
        // START NEW ROUND
        // =========================

        private void startNewRound() {

                secretNumber = random.nextInt(maxNumber) + 1;

                attempts = 0;

                gameOver = false;

                updateAttemptIndicator();

                guessField.setText("");

                attemptsLabel.setText(
                                "0 / " + maxAttempts);

                messageLabel.setText(
                                "Make your first guess!");

                messageLabel.setForeground(
                                PRIMARY_HOVER);

                roundLabel.setText(
                                "<html><center>ROUND "
                                                + round
                                                + "</center></html>");

                scoreLabel.setText(
                                "<html><center>SCORE "
                                                + score
                                                + "</center></html>");

                instructionLabel.setText(
                                "Guess a number between 1 and "
                                                + maxNumber);

                guessButton.setEnabled(true);

                guessField.setEnabled(true);

                guessField.requestFocusInWindow();
        }

        // =========================
        // CHECK GUESS
        // =========================

        private void checkGuess() {

                if (gameOver) {
                        return;
                }

                String input = guessField.getText().trim();

                // Empty input
                if (input.isEmpty()) {

                        messageLabel.setText(
                                        "Please enter a number!");

                        messageLabel.setForeground(
                                        DANGER);

                        return;
                }

                int guess;

                try {

                        guess = Integer.parseInt(input);

                } catch (NumberFormatException e) {

                        messageLabel.setText(
                                        "Please enter a valid number!");

                        messageLabel.setForeground(
                                        DANGER);

                        return;
                }

                // Range validation
                if (guess < 1 ||
                                guess > maxNumber) {

                        messageLabel.setText(
                                        "Enter a number between 1 and "
                                                        + maxNumber);

                        messageLabel.setForeground(
                                        DANGER);

                        return;
                }

                // Count attempt
                attempts++;

                attemptsLabel.setText(
                                attempts
                                                + " / "
                                                + maxAttempts);

                updateAttemptIndicator();

                // Too high
                if (guess > secretNumber) {

                        messageLabel.setText(
                                        "↑ TOO HIGH! Try a smaller number.");

                        messageLabel.setForeground(
                                        DANGER);
                }

                // Too low
                else if (guess < secretNumber) {

                        messageLabel.setText(
                                        "↓ TOO LOW! Try a larger number.");

                        messageLabel.setForeground(
                                        PRIMARY_HOVER);
                }

                // Correct
                else {

                        messageLabel.setText(
                                        "🎉 CORRECT! You got it!");

                        messageLabel.setForeground(
                                        SUCCESS);

                        score++;

                        gameOver = true;

                        scoreLabel.setText(
                                        "<html><center>SCORE "
                                                        + score
                                                        + "</center></html>");

                        showResultPanel(
                                        true,
                                        attempts,
                                        secretNumber);

                        return;
                }

                // Attempts finished
                if (attempts >= maxAttempts) {

                        gameOver = true;

                        messageLabel.setText(
                                        "💀 YOU LOST! Number was "
                                                        + secretNumber);

                        messageLabel.setForeground(
                                        DANGER);

                        showResultPanel(
                                        false,
                                        attempts,
                                        secretNumber);

                        return;
                }

                guessField.setText("");

                guessField.requestFocusInWindow();
        }

        // =========================
        // SET DIFFICULTY
        // =========================

        private void setDifficulty(
                        int numberRange,
                        int attemptsLimit) {

                maxNumber = numberRange;

                maxAttempts = attemptsLimit;

                // New difficulty starts from Round 1
                round = 1;

                score = 0;

                instructionLabel.setText(
                                "Guess a number between 1 and "
                                                + maxNumber);

                startNewRound();

                messageLabel.setText(
                                "Difficulty selected! Make your guess.");

                messageLabel.setForeground(
                                PRIMARY_HOVER);
        }

        // =========================
        // SELECTED DIFFICULTY UI
        // =========================

        private void selectDifficultyButton(
                        JButton selectedButton) {

                selectedDifficultyButton = selectedButton;

                easyButton.setBorder(
                                BorderFactory.createEmptyBorder(
                                                2,
                                                2,
                                                2,
                                                2));

                mediumButton.setBorder(
                                BorderFactory.createEmptyBorder(
                                                2,
                                                2,
                                                2,
                                                2));

                hardButton.setBorder(
                                BorderFactory.createEmptyBorder(
                                                2,
                                                2,
                                                2,
                                                2));

                selectedButton.setBorder(
                                BorderFactory.createLineBorder(
                                                Color.WHITE,
                                                2));
        }

        // =========================
        // UPDATE ATTEMPT INDICATOR
        // =========================

        private void updateAttemptIndicator() {

                StringBuilder dots = new StringBuilder();

                for (int i = 0; i < maxAttempts; i++) {

                        if (i < attempts) {

                                dots.append("○ ");

                        } else {

                                dots.append("● ");
                        }
                }

                attemptDotsLabel.setText(
                                dots.toString());

                if (attempts >= maxAttempts) {

                        attemptDotsLabel.setForeground(
                                        DANGER);

                } else if (attempts >= maxAttempts / 2) {

                        attemptDotsLabel.setForeground(
                                        new Color(
                                                        250,
                                                        204,
                                                        21));

                } else {

                        attemptDotsLabel.setForeground(
                                        SUCCESS);
                }
        }

        // =========================
        // CREATE DOTS
        // =========================

        private String createDots() {

                StringBuilder dots = new StringBuilder();

                for (int i = 0; i < maxAttempts; i++) {

                        dots.append("● ");

                }

                return dots.toString();
        }

        // =========================
        // RESULT SCREEN
        // =========================

        private void showResultPanel(
                        boolean won,
                        int attemptsUsed,
                        int correctNumber) {

                resultPanel = new JPanel();

                resultPanel.setBackground(
                                CARD_COLOR);

                resultPanel.setLayout(
                                new BoxLayout(
                                                resultPanel,
                                                BoxLayout.Y_AXIS));

                resultPanel.setBorder(
                                new EmptyBorder(
                                                60,
                                                50,
                                                60,
                                                50));

                // Icon
                resultIconLabel = new JLabel(
                                won ? "🎉" : "💀");

                resultIconLabel.setFont(
                                new Font(
                                                "SansSerif",
                                                Font.PLAIN,
                                                55));

                resultIconLabel.setAlignmentX(
                                Component.CENTER_ALIGNMENT);

                // Title
                resultTitleLabel = new JLabel(
                                won
                                                ? "YOU WON!"
                                                : "GAME OVER");

                resultTitleLabel.setFont(
                                new Font(
                                                "SansSerif",
                                                Font.BOLD,
                                                32));

                resultTitleLabel.setForeground(
                                won
                                                ? SUCCESS
                                                : DANGER);

                resultTitleLabel.setAlignmentX(
                                Component.CENTER_ALIGNMENT);

                // Description
                String description;

                if (won) {

                        description = "You guessed the number in "
                                        + attemptsUsed
                                        + " attempts!";

                } else {

                        description = "The correct number was "
                                        + correctNumber
                                        + ".";
                }

                resultDescriptionLabel = new JLabel(
                                description);

                resultDescriptionLabel.setFont(
                                new Font(
                                                "SansSerif",
                                                Font.PLAIN,
                                                16));

                resultDescriptionLabel.setForeground(
                                SECONDARY_TEXT);

                resultDescriptionLabel.setAlignmentX(
                                Component.CENTER_ALIGNMENT);

                // Result button
                resultButton = createButton(
                                won
                                                ? "NEXT ROUND  →"
                                                : "TRY AGAIN  ↻");

                resultButton.setMaximumSize(
                                new Dimension(
                                                220,
                                                50));

                resultButton.setAlignmentX(
                                Component.CENTER_ALIGNMENT);

                resultButton.addActionListener(
                                e -> closeResultPanel());

                // Add components
                resultPanel.add(
                                resultIconLabel);

                resultPanel.add(
                                Box.createVerticalStrut(20));

                resultPanel.add(
                                resultTitleLabel);

                resultPanel.add(
                                Box.createVerticalStrut(15));

                resultPanel.add(
                                resultDescriptionLabel);

                resultPanel.add(
                                Box.createVerticalStrut(30));

                resultPanel.add(
                                resultButton);

                // Disable old game components
                guessButton.setEnabled(false);
                guessField.setEnabled(false);

                easyButton.setEnabled(false);
                mediumButton.setEnabled(false);
                hardButton.setEnabled(false);

                // Replace screen
                removeAll();

                add(
                                resultPanel,
                                BorderLayout.CENTER);

                revalidate();
                repaint();
        }

        // =========================
        // CLOSE RESULT SCREEN
        // =========================

        private void closeResultPanel() {

                // Increase round ONLY here
                round++;

                // Rebuild the game screen
                removeAll();

                createHeader();
                createMainContent();

                // Restore the correct difficulty button
                if (maxNumber == 50) {

                        selectDifficultyButton(
                                        easyButton);

                } else if (maxNumber == 200) {

                        selectDifficultyButton(
                                        hardButton);

                } else {

                        selectDifficultyButton(
                                        mediumButton);
                }

                // Start the next round
                startNewRound();

                messageLabel.setText(
                                "Round "
                                                + round
                                                + " started! Make your guess.");

                messageLabel.setForeground(
                                PRIMARY_HOVER);

                revalidate();
                repaint();
        }
}