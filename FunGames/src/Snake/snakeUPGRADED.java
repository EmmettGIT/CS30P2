package Snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class snakeUPGRADED extends JPanel implements ActionListener {

    // Screen settings
    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 650;
    static final int UNIT_SIZE = 25;
    static final int GAME_WIDTH = 600;
    static final int GAME_HEIGHT = 600;

    // Game settings
    final int TOTAL_UNITS = 24 * 24;

    // Snake
    ArrayList<Point> snake = new ArrayList<>();

    // Obstacles
    ArrayList<Point> obstacles = new ArrayList<>();

    // Food
    Point apple;
    Point goldenApple;

    // Random generator
    Random random = new Random();

    // Game states
    enum GameState {
        MENU, PLAYING, PAUSED, GAME_OVER, HIGH_SCORES
    }

    GameState state = GameState.MENU;

    // Movement
    int directionX = 1;
    int directionY = 0;

    int nextDirectionX = 1;
    int nextDirectionY = 0;

    // Score and difficulty
    int score = 0;
    int highScore = 0;
    int lives = 3;
    int level = 1;
    int speed = 120;

    // Golden apple timer
    int goldenTimer = 0;

    // Game timer
    Timer timer;

    // Colors
    Color background = new Color(13, 17, 30);
    Color green = new Color(50, 255, 130);
    Color darkGreen = new Color(20, 180, 90);
    Color red = new Color(255, 65, 85);
    Color gold = new Color(255, 205, 50);
    Color blue = new Color(60, 160, 255);

    public snakeUPGRADED() {

        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        setBackground(background);

        loadHighScore();

        setupControls();

        timer = new Timer(speed, this);
        timer.start();
    }

    // =====================================
    // HIGH SCORE SYSTEM
    // =====================================

    public void loadHighScore() {

        try {
            File file = new File("snake_highscore.txt");

            if (file.exists()) {
                BufferedReader reader = new BufferedReader(
                    new FileReader(file)
                );

                highScore = Integer.parseInt(reader.readLine());
                reader.close();
            }

        } catch (Exception e) {
            highScore = 0;
        }
    }

    public void saveHighScore() {

        try {
            FileWriter writer = new FileWriter("snake_highscore.txt");

            writer.write(String.valueOf(highScore));

            writer.close();

        } catch (IOException e) {
            System.out.println("Could not save high score.");
        }
    }

    // =====================================
    // GAME SETUP
    // =====================================

    public void startGame() {

        score = 0;
        lives = 3;
        level = 1;
        speed = 120;

        directionX = 1;
        directionY = 0;

        nextDirectionX = 1;
        nextDirectionY = 0;

        snake.clear();
        obstacles.clear();

        // Starting snake
        snake.add(new Point(8, 12));
        snake.add(new Point(7, 12));
        snake.add(new Point(6, 12));
        snake.add(new Point(5, 12));

        // Generate obstacles
        for (int i = 0; i < 10; i++) {
            Point obstacle = randomPosition();

            if (!snake.contains(obstacle) &&
                !obstacles.contains(obstacle)) {

                obstacles.add(obstacle);
            }
        }

        goldenApple = null;
        goldenTimer = 0;

        spawnApple();

        state = GameState.PLAYING;

        timer.setDelay(speed);

        repaint();
    }

    // =====================================
    // RANDOM POSITIONS
    // =====================================

    public Point randomPosition() {

        return new Point(
            random.nextInt(GAME_WIDTH / UNIT_SIZE),
            random.nextInt(GAME_HEIGHT / UNIT_SIZE)
        );
    }

    public void spawnApple() {

        do {
            apple = randomPosition();

        } while (
            snake.contains(apple) ||
            obstacles.contains(apple)
        );

        // 20% chance of golden apple
        if (random.nextInt(5) == 0) {

            do {
                goldenApple = randomPosition();

            } while (
                snake.contains(goldenApple) ||
                obstacles.contains(goldenApple) ||
                apple.equals(goldenApple)
            );

            goldenTimer = 60;

        } else {
            goldenApple = null;
        }
    }

    // =====================================
    // MOVEMENT
    // =====================================

    public void moveSnake() {

        directionX = nextDirectionX;
        directionY = nextDirectionY;

        Point head = snake.get(0);

        Point newHead = new Point(
            head.x + directionX,
            head.y + directionY
        );

        snake.add(0, newHead);

        // Eat regular apple
        if (newHead.equals(apple)) {

            score += 10;

            spawnApple();

            playSound();
        }

        // Eat golden apple
        else if (goldenApple != null &&
                 newHead.equals(goldenApple)) {

            score += 50;

            goldenApple = null;

            playSound();

        } else {

            snake.remove(snake.size() - 1);
        }

        // Update high score
        if (score > highScore) {

            highScore = score;
            saveHighScore();
        }

        // Increase difficulty
        level = (score / 50) + 1;

        speed = Math.max(55, 120 - (level - 1) * 8);

        timer.setDelay(speed);

        checkCollisions();

        if (goldenApple != null) {

            goldenTimer--;

            if (goldenTimer <= 0) {
                goldenApple = null;
            }
        }
    }

    // =====================================
    // COLLISIONS
    // =====================================

    public void checkCollisions() {

        Point head = snake.get(0);

        boolean collision = false;

        // Wall collision
        if (head.x < 0 ||
            head.x >= GAME_WIDTH / UNIT_SIZE ||
            head.y < 0 ||
            head.y >= GAME_HEIGHT / UNIT_SIZE) {

            collision = true;
        }

        // Self collision
        for (int i = 1; i < snake.size(); i++) {

            if (head.equals(snake.get(i))) {
                collision = true;
            }
        }

        // Obstacle collision
        if (obstacles.contains(head)) {
            collision = true;
        }

        if (collision) {

            lives--;

            playSound();

            if (lives <= 0) {

                state = GameState.GAME_OVER;

                if (score > highScore) {
                    highScore = score;
                    saveHighScore();
                }

            } else {

                resetSnake();
            }
        }
    }

    // =====================================
    // RESET AFTER LOSING A LIFE
    // =====================================

    public void resetSnake() {

        snake.clear();

        snake.add(new Point(8, 12));
        snake.add(new Point(7, 12));
        snake.add(new Point(6, 12));
        snake.add(new Point(5, 12));

        directionX = 1;
        directionY = 0;

        nextDirectionX = 1;
        nextDirectionY = 0;

        // Move food if it overlaps the snake
        if (snake.contains(apple)) {
            spawnApple();
        }
    }

    // =====================================
    // SOUND EFFECTS
    // =====================================

    public void playSound() {

        try {
            Toolkit.getDefaultToolkit().beep();
        } catch (Exception e) {
            // Sound unavailable
        }
    }

    // =====================================
    // KEYBOARD CONTROLS
    // =====================================

    public void setupControls() {

        bindKey("UP", KeyEvent.VK_UP);
        bindKey("DOWN", KeyEvent.VK_DOWN);
        bindKey("LEFT", KeyEvent.VK_LEFT);
        bindKey("RIGHT", KeyEvent.VK_RIGHT);

        bindKey("W", KeyEvent.VK_W);
        bindKey("A", KeyEvent.VK_A);
        bindKey("S", KeyEvent.VK_S);
        bindKey("D", KeyEvent.VK_D);

        bindKey("SPACE", KeyEvent.VK_SPACE);
        bindKey("ENTER", KeyEvent.VK_ENTER);
        bindKey("ESCAPE", KeyEvent.VK_ESCAPE);
    }

    public void bindKey(String name, int keyCode) {

        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
            .put(KeyStroke.getKeyStroke(keyCode, 0), name);

        getActionMap().put(name, new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {

                handleKey(name);
            }
        });
    }

    public void handleKey(String key) {

        // Main menu
        if (state == GameState.MENU) {

            if (key.equals("ENTER")) {
                startGame();
            }

            repaint();
            return;
        }

        // High scores
        if (state == GameState.HIGH_SCORES) {

            if (key.equals("ESCAPE") ||
                key.equals("ENTER")) {

                state = GameState.MENU;
            }

            repaint();
            return;
        }

        // Game over
        if (state == GameState.GAME_OVER) {

            if (key.equals("ENTER")) {
                startGame();
            }

            if (key.equals("ESCAPE")) {
                state = GameState.MENU;
            }

            repaint();
            return;
        }

        // Pause
        if (key.equals("ESCAPE") ||
            key.equals("SPACE")) {

            if (state == GameState.PLAYING) {
                state = GameState.PAUSED;
            } else if (state == GameState.PAUSED) {
                state = GameState.PLAYING;
            }

            repaint();
            return;
        }

        if (state != GameState.PLAYING) {
            return;
        }

        // Movement
        switch (key) {

            case "UP":
            case "W":

                if (directionY != 1) {
                    nextDirectionX = 0;
                    nextDirectionY = -1;
                }
                break;

            case "DOWN":
            case "S":

                if (directionY != -1) {
                    nextDirectionX = 0;
                    nextDirectionY = 1;
                }
                break;

            case "LEFT":
            case "A":

                if (directionX != 1) {
                    nextDirectionX = -1;
                    nextDirectionY = 0;
                }
                break;

            case "RIGHT":
            case "D":

                if (directionX != -1) {
                    nextDirectionX = 1;
                    nextDirectionY = 0;
                }
                break;
        }
    }

    // =====================================
    // GAME TIMER
    // =====================================

    @Override
    public void actionPerformed(ActionEvent e) {

        if (state == GameState.PLAYING) {

            moveSnake();
        }

        repaint();
    }

    // =====================================
    // GRAPHICS
    // =====================================

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON
        );

        switch (state) {

            case MENU:
                drawMenu(g2);
                break;

            case PLAYING:
                drawGame(g2);
                break;

            case PAUSED:
                drawGame(g2);
                drawOverlay(g2, "PAUSED", "Press SPACE to continue");
                break;

            case GAME_OVER:
                drawGame(g2);
                drawGameOver(g2);
                break;

            case HIGH_SCORES:
                drawHighScores(g2);
                break;
        }

        g2.dispose();
    }

    // =====================================
    // MAIN MENU
    // =====================================

    public void drawMenu(Graphics2D g) {

        drawBackground(g);

        g.setFont(new Font("Arial", Font.BOLD, 65));
        g.setColor(green);

        drawCentered(g, "SNAKE", 210);

        g.setFont(new Font("Arial", Font.BOLD, 25));
        g.setColor(Color.WHITE);

        drawCentered(g, "ARCADE EDITION", 250);

        g.setFont(new Font("Arial", Font.PLAIN, 19));
        g.setColor(new Color(170, 180, 200));

        drawCentered(g, "Eat. Grow. Survive.", 310);

        g.setColor(gold);
        g.setFont(new Font("Arial", Font.BOLD, 24));

        drawCentered(g, "PRESS ENTER TO PLAY", 410);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 17));

        drawCentered(g, "H - HIGH SCORES", 465);
        drawCentered(g, "WASD / ARROW KEYS - MOVE", 505);
        drawCentered(g, "SPACE - PAUSE", 535);

        g.setColor(green);
        g.setFont(new Font("Arial", Font.BOLD, 17));

        drawCentered(g, "BEST SCORE: " + highScore, 590);
    }

    // =====================================
    // GAME SCREEN
    // =====================================

    public void drawGame(Graphics2D g) {

        drawBackground(g);

        // HUD
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 18));

        g.drawString("SCORE: " + score, 20, 30);
        g.drawString("LEVEL: " + level, 240, 30);

        g.setColor(gold);
        g.drawString("BEST: " + highScore, 440, 30);

        // Lives
        g.setColor(red);
        g.setFont(new Font("Arial", Font.BOLD, 17));

        g.drawString("LIVES: " + lives, 20, 50);

        // Grid
        g.setColor(new Color(30, 38, 55));

        for (int i = 0; i <= 24; i++) {

            g.drawLine(
                i * UNIT_SIZE,
                50,
                i * UNIT_SIZE,
                650
            );

            g.drawLine(
                0,
                50 + i * UNIT_SIZE,
                600,
                50 + i * UNIT_SIZE
            );
        }

        // Obstacles
        for (Point obstacle : obstacles) {

            int px = obstacle.x * UNIT_SIZE;
            int py = 50 + obstacle.y * UNIT_SIZE;

            g.setColor(new Color(100, 110, 135));

            g.fillRoundRect(
                px + 2, py + 2,
                UNIT_SIZE - 4,
                UNIT_SIZE - 4,
                8, 8
            );
        }

        // Regular apple
        drawFood(g, apple, red);

        // Golden apple
        if (goldenApple != null) {
            drawFood(g, goldenApple, gold);
        }

        // Snake
        for (int i = 0; i < snake.size(); i++) {

            Point part = snake.get(i);

            int px = part.x * UNIT_SIZE;
            int py = 50 + part.y * UNIT_SIZE;

            if (i == 0) {

                g.setColor(green);

                g.fillRoundRect(
                    px, py,
                    UNIT_SIZE,
                    UNIT_SIZE,
                    10, 10
                );

                // Eyes
                g.setColor(Color.BLACK);

                g.fillOval(px + 6, py + 6, 5, 5);
                g.fillOval(px + 16, py + 6, 5, 5);

            } else {

                g.setColor(
                    new Color(
                        Math.max(0, 30 - i),
                        Math.max(100, 220 - i * 2),
                        Math.max(60, 130 - i)
                    )
                );

                g.fillRoundRect(
                    px + 1, py + 1,
                    UNIT_SIZE - 2,
                    UNIT_SIZE - 2,
                    8, 8
                );
            }
        }

        // Golden apple countdown
        if (goldenApple != null) {

            g.setColor(gold);
            g.setFont(new Font("Arial", Font.BOLD, 14));

            g.drawString(
                "GOLDEN APPLE: " + (goldenTimer / 10) + "s",
                400, 50
            );
        }
    }

    // =====================================
    // FOOD GRAPHICS
    // =====================================

    public void drawFood(Graphics2D g, Point food, Color color) {

        if (food == null) {
            return;
        }

        int px = food.x * UNIT_SIZE;
        int py = 50 + food.y * UNIT_SIZE;

        g.setColor(color);

        g.fillOval(
            px + 2, py + 2,
            UNIT_SIZE - 4,
            UNIT_SIZE - 4
        );

        g.setColor(Color.WHITE);

        g.fillOval(
            px + 7, py + 6,
            5, 5
        );
    }

    // =====================================
    // BACKGROUND
    // =====================================

    public void drawBackground(Graphics2D g) {

        GradientPaint gradient = new GradientPaint(
            0, 0, new Color(12, 16, 30),
            600, 650, new Color(25, 35, 55)
        );

        g.setPaint(gradient);

        g.fillRect(0, 0, 600, 650);
    }

    // =====================================
    // PAUSE OVERLAY
    // =====================================

    public void drawOverlay(
        Graphics2D g,
        String title,
        String subtitle
    ) {

        g.setColor(new Color(0, 0, 0, 190));

        g.fillRect(0, 0, 600, 650);

        g.setColor(green);
        g.setFont(new Font("Arial", Font.BOLD, 50));

        drawCentered(g, title, 290);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 19));

        drawCentered(g, subtitle, 340);
    }

    // =====================================
    // GAME OVER
    // =====================================

    public void drawGameOver(Graphics2D g) {

        drawOverlay(g, "GAME OVER", "You scored " + score + " points!");

        g.setColor(gold);
        g.setFont(new Font("Arial", Font.BOLD, 23));

        drawCentered(g, "BEST SCORE: " + highScore, 400);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 17));

        drawCentered(g, "ENTER - PLAY AGAIN", 455);
        drawCentered(g, "ESC - MAIN MENU", 490);
    }

    // =====================================
    // HIGH SCORES
    // =====================================

    public void drawHighScores(Graphics2D g) {

        drawBackground(g);

        g.setColor(gold);
        g.setFont(new Font("Arial", Font.BOLD, 42));

        drawCentered(g, "HIGH SCORES", 220);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 30));

        drawCentered(g, "1. " + highScore, 320);

        g.setFont(new Font("Arial", Font.PLAIN, 18));

        drawCentered(g, "YOUR PERSONAL BEST", 360);
        drawCentered(g, "Press ENTER to return", 470);
    }

    // =====================================
    // CENTER TEXT
    // =====================================

    public void drawCentered(
        Graphics2D g,
        String text,
        int y
    ) {

        FontMetrics metrics = g.getFontMetrics();

        int x = (SCREEN_WIDTH - metrics.stringWidth(text)) / 2;

        g.drawString(text, x, y);
    }

    // =====================================
    // MAIN METHOD
    // =====================================

    public static void main(String[] args) {

        JFrame frame = new JFrame("Snake Arcade Edition");

        snakeUPGRADED game = new snakeUPGRADED();

        frame.add(game);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setResizable(false);

        frame.pack();

        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }
}