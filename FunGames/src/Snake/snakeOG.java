package Snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class snakeOG extends JPanel implements ActionListener, KeyListener {

    // Game settings
    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / (UNIT_SIZE * UNIT_SIZE);

    // Snake
    final int[] x = new int[GAME_UNITS];
    final int[] y = new int[GAME_UNITS];

    int bodyParts = 5;
    int applesEaten = 0;

    // Food
    int appleX;
    int appleY;

    // Movement
    char direction = 'R';
    boolean running = false;
    boolean paused = false;
    boolean gameOver = false;

    // Game timer
    Timer timer;
    int delay = 110;

    // High score
    int highScore = 0;

    Random random;

    public snakeOG() {

        random = new Random();

        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(new Color(20, 25, 35));
        this.setFocusable(true);
        this.addKeyListener(this);

        startGame();
    }

    public void startGame() {

        bodyParts = 5;
        applesEaten = 0;
        direction = 'R';

        for (int i = 0; i < bodyParts; i++) {
            x[i] = 150 - i * UNIT_SIZE;
            y[i] = 150;
        }

        newApple();

        running = true;
        paused = false;
        gameOver = false;

        delay = 110;

        if (timer != null) {
            timer.stop();
        }

        timer = new Timer(delay, this);
        timer.start();

        repaint();
    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {

        if (running || gameOver || paused) {

            // Grid
            g.setColor(new Color(35, 42, 55));

            for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
                g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
            }

            // Food
            g.setColor(new Color(255, 75, 75));
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            // Food shine
            g.setColor(Color.WHITE);
            g.fillOval(appleX + 6, appleY + 5, 6, 6);

            // Snake
            for (int i = 0; i < bodyParts; i++) {

                if (i == 0) {
                    g.setColor(new Color(80, 255, 120));
                } else {
                    g.setColor(new Color(30, 190, 100));
                }

                g.fillRoundRect(
                    x[i], y[i],
                    UNIT_SIZE, UNIT_SIZE,
                    10, 10
                );

                // Snake eyes
                if (i == 0) {
                    g.setColor(Color.BLACK);
                    g.fillOval(x[i] + 15, y[i] + 5, 4, 4);
                    g.fillOval(x[i] + 15, y[i] + 15, 4, 4);
                }
            }

            // Score
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString("Score: " + applesEaten, 15, 30);

            g.setFont(new Font("Arial", Font.PLAIN, 15));
            g.drawString("High Score: " + highScore, 15, 52);

            if (paused) {
                drawCenteredMessage(g, "PAUSED", "Press SPACE to continue");
            }

            if (gameOver) {
                drawGameOver(g);
            }
        }
    }

    public void newApple() {

        appleX = random.nextInt(SCREEN_WIDTH / UNIT_SIZE) * UNIT_SIZE;
        appleY = random.nextInt(SCREEN_HEIGHT / UNIT_SIZE) * UNIT_SIZE;

        // Avoid spawning food inside the snake
        for (int i = 0; i < bodyParts; i++) {
            if (appleX == x[i] && appleY == y[i]) {
                newApple();
                return;
            }
        }
    }

    public void move() {

        // Move snake body
        for (int i = bodyParts; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

        // Move snake head
        switch (direction) {

            case 'U':
                y[0] -= UNIT_SIZE;
                break;

            case 'D':
                y[0] += UNIT_SIZE;
                break;

            case 'L':
                x[0] -= UNIT_SIZE;
                break;

            case 'R':
                x[0] += UNIT_SIZE;
                break;
        }
    }

    public void checkApple() {

        if (x[0] == appleX && y[0] == appleY) {

            bodyParts++;
            applesEaten++;

            if (applesEaten > highScore) {
                highScore = applesEaten;
            }

            newApple();

            // Increase speed
            delay = Math.max(45, 110 - applesEaten * 3);

            timer.setDelay(delay);
        }
    }

    public void checkCollisions() {

        // Check collision with body
        for (int i = bodyParts; i > 0; i--) {

            if (x[0] == x[i] && y[0] == y[i]) {
                gameOver = true;
            }
        }

        // Check collision with walls
        if (x[0] < 0 || x[0] >= SCREEN_WIDTH ||
            y[0] < 0 || y[0] >= SCREEN_HEIGHT) {

            gameOver = true;
        }

        if (gameOver) {
            running = false;
            timer.stop();
        }
    }

    public void drawCenteredMessage(Graphics g, String title, String subtitle) {

        g.setColor(new Color(0, 0, 0, 180));
        g.fillRoundRect(100, 220, 400, 140, 25, 25);

        g.setColor(new Color(80, 255, 120));
        g.setFont(new Font("Arial", Font.BOLD, 40));

        FontMetrics metrics = getFontMetrics(g.getFont());

        g.drawString(title,
            (SCREEN_WIDTH - metrics.stringWidth(title)) / 2,
            275);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 18));

        metrics = getFontMetrics(g.getFont());

        g.drawString(subtitle,
            (SCREEN_WIDTH - metrics.stringWidth(subtitle)) / 2,
            320);
    }

    public void drawGameOver(Graphics g) {

        drawCenteredMessage(g, "GAME OVER", "Final Score: " + applesEaten);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 17));

        String restart = "Press ENTER to play again";

        FontMetrics metrics = getFontMetrics(g.getFont());

        g.drawString(restart,
            (SCREEN_WIDTH - metrics.stringWidth(restart)) / 2,
            390);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (running && !paused) {

            move();
            checkApple();
            checkCollisions();
        }

        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        // Restart
        if (key == KeyEvent.VK_ENTER && gameOver) {
            startGame();
            return;
        }

        // Pause
        if (key == KeyEvent.VK_SPACE && running) {
            paused = !paused;
            repaint();
            return;
        }

        // Movement controls
        if (running && !paused) {

            switch (key) {

                case KeyEvent.VK_LEFT:
                case KeyEvent.VK_A:
                    if (direction != 'R') {
                        direction = 'L';
                    }
                    break;

                case KeyEvent.VK_RIGHT:
                case KeyEvent.VK_D:
                    if (direction != 'L') {
                        direction = 'R';
                    }
                    break;

                case KeyEvent.VK_UP:
                case KeyEvent.VK_W:
                    if (direction != 'D') {
                        direction = 'U';
                    }
                    break;

                case KeyEvent.VK_DOWN:
                case KeyEvent.VK_S:
                    if (direction != 'U') {
                        direction = 'D';
                    }
                    break;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {

        JFrame frame = new JFrame("🐍 Snake Game");

        snakeOG game = new snakeOG();

        frame.add(game);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        game.requestFocusInWindow();
    }
}