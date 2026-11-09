package FPS;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class FortniteFPS extends JPanel implements ActionListener,
        KeyListener, MouseListener, MouseMotionListener {

    // WINDOW SETTINGS
    static final int WIDTH = 1000;
    static final int HEIGHT = 650;

    Timer timer;
    Random random = new Random();

    // PLAYER
    double px = 3.5;
    double py = 3.5;
    double angle = 0;
    double playerSpeed = 0.085;

    int health = 100;
    int ammo = 30;
    int reserveAmmo = 120;
    int kills = 0;

    boolean gameOver = false;
    boolean victory = false;
    boolean reloading = false;

    long reloadStart = 0;
    long lastShot = 0;
    long startTime = System.currentTimeMillis();

    // INPUT
    boolean forward, backward, left, right;
    boolean turnLeft, turnRight;

    int lastMouseX = -1;

    // WORLD MAP: 1 = wall, 0 = empty
    int[][] map = {
        {1,1,1,1,1,1,1,1,1,1,1,1},
        {1,0,0,0,0,0,0,0,0,0,0,1},
        {1,0,0,0,0,0,0,0,0,0,0,1},
        {1,0,0,0,1,1,0,0,0,0,0,1},
        {1,0,0,0,1,0,0,0,0,0,0,1},
        {1,0,0,0,0,0,0,1,1,0,0,1},
        {1,0,0,0,0,0,0,1,0,0,0,1},
        {1,0,1,1,0,0,0,0,0,0,0,1},
        {1,0,0,0,0,0,0,0,0,1,0,1},
        {1,0,0,0,0,0,0,0,0,0,0,1},
        {1,0,0,0,0,0,0,0,0,0,0,1},
        {1,1,1,1,1,1,1,1,1,1,1,1}
    };

    // ENEMIES
    class Enemy {
        double x, y;
        int health = 100;
        double speed = 0.018;
        boolean alive = true;

        Enemy(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    ArrayList<Enemy> enemies = new ArrayList<>();

    // STORM
    double stormRadius = 8.0;
    double stormX = 6;
    double stormY = 6;

    public FortniteFPS() {

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);

        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);

        // CREATE ENEMIES
        enemies.add(new Enemy(8.5, 2.5));
        enemies.add(new Enemy(9.5, 5.5));
        enemies.add(new Enemy(3.5, 9.5));
        enemies.add(new Enemy(7.5, 9.5));
        enemies.add(new Enemy(5.5, 7.5));
        enemies.add(new Enemy(2.5, 6.5));
        enemies.add(new Enemy(9.5, 9.5));

        timer = new Timer(16, this);
        timer.start();
    }

    // MAIN GAME LOOP
    public void actionPerformed(ActionEvent e) {

        if (!gameOver && !victory) {
            updatePlayer();
            updateEnemies();
            updateStorm();

            if (enemies.stream().noneMatch(enemy -> enemy.alive)) {
                victory = true;
            }

            if (health <= 0) {
                gameOver = true;
            }
        }

        repaint();
    }

    // PLAYER MOVEMENT
    void updatePlayer() {

        double moveX = 0;
        double moveY = 0;

        if (forward) {
            moveX += Math.cos(angle) * playerSpeed;
            moveY += Math.sin(angle) * playerSpeed;
        }

        if (backward) {
            moveX -= Math.cos(angle) * playerSpeed;
            moveY -= Math.sin(angle) * playerSpeed;
        }

        if (left) {
            moveX += Math.cos(angle - Math.PI / 2) * playerSpeed;
            moveY += Math.sin(angle - Math.PI / 2) * playerSpeed;
        }

        if (right) {
            moveX += Math.cos(angle + Math.PI / 2) * playerSpeed;
            moveY += Math.sin(angle + Math.PI / 2) * playerSpeed;
        }

        if (turnLeft) angle -= 0.045;
        if (turnRight) angle += 0.045;

        if (!isWall(px + moveX, py)) px += moveX;
        if (!isWall(px, py + moveY)) py += moveY;

        // Keep angle between 0 and 2 PI
        angle = (angle + Math.PI * 2) % (Math.PI * 2);
    }

    boolean isWall(double x, double y) {

        int mx = (int) x;
        int my = (int) y;

        if (mx < 0 || my < 0 ||
            mx >= map[0].length || my >= map.length) {
            return true;
        }

        return map[my][mx] == 1;
    }

    // ENEMY AI
    void updateEnemies() {

        for (Enemy enemy : enemies) {

            if (!enemy.alive) continue;

            double dx = px - enemy.x;
            double dy = py - enemy.y;

            double distance = Math.sqrt(dx * dx + dy * dy);

            if (distance > 0.65 && distance < 10) {

                double moveX = (dx / distance) * enemy.speed;
                double moveY = (dy / distance) * enemy.speed;

                if (!isWall(enemy.x + moveX, enemy.y)) {
                    enemy.x += moveX;
                }

                if (!isWall(enemy.x, enemy.y + moveY)) {
                    enemy.y += moveY;
                }
            }

            // Enemy damages player at close range
            if (distance < 0.8) {
                health -= 1;
            }
        }
    }

    // STORM SYSTEM
    void updateStorm() {

        long elapsed = (System.currentTimeMillis() - startTime) / 1000;

        stormRadius = Math.max(2.0, 8.0 - elapsed * 0.012);

        double dx = px - stormX;
        double dy = py - stormY;

        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance > stormRadius) {
            health -= 1;
        }
    }

    // SHOOTING
    void shoot() {

        if (gameOver || victory || reloading) return;

        if (System.currentTimeMillis() - lastShot < 250) return;

        if (ammo <= 0) {
            reload();
            return;
        }

        ammo--;
        lastShot = System.currentTimeMillis();

        Enemy target = null;
        double closest = 999;

        for (Enemy enemy : enemies) {

            if (!enemy.alive) continue;

            double dx = enemy.x - px;
            double dy = enemy.y - py;

            double distance = Math.sqrt(dx * dx + dy * dy);

            double enemyAngle = Math.atan2(dy, dx);
            double difference = Math.atan2(
                Math.sin(enemyAngle - angle),
                Math.cos(enemyAngle - angle)
            );

            // Target must be close to the crosshair
            if (Math.abs(difference) < 0.10 &&
                distance < closest &&
                distance < 9 &&
                hasLineOfSight(enemy.x, enemy.y)) {

                target = enemy;
                closest = distance;
            }
        }

        if (target != null) {

            target.health -= 50;

            if (target.health <= 0) {
                target.alive = false;
                kills++;
            }
        }
    }

    boolean hasLineOfSight(double x, double y) {

        double dx = x - px;
        double dy = y - py;

        double distance = Math.sqrt(dx * dx + dy * dy);

        int steps = (int)(distance / 0.05);

        for (int i = 1; i < steps; i++) {

            double checkX = px + dx * i / steps;
            double checkY = py + dy * i / steps;

            if (isWall(checkX, checkY)) return false;
        }

        return true;
    }

    // RELOAD
    void reload() {

        if (reloading || ammo == 30 || reserveAmmo <= 0) return;

        reloading = true;
        reloadStart = System.currentTimeMillis();
    }

    void updateReload() {

        if (reloading &&
            System.currentTimeMillis() - reloadStart >= 1500) {

            int needed = 30 - ammo;
            int amount = Math.min(needed, reserveAmmo);

            ammo += amount;
            reserveAmmo -= amount;

            reloading = false;
        }
    }

    // DRAW EVERYTHING
    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        updateReload();

        if (gameOver) {
            drawEndScreen(g2, "GAME OVER");
            return;
        }

        if (victory) {
            drawEndScreen(g2, "VICTORY ROYALE!");
            return;
        }

        drawWorld(g2);
        drawEnemies(g2);
        drawWeapon(g2);
        drawCrosshair(g2);
        drawHUD(g2);
        drawStormWarning(g2);
    }

    // 3D RAYCASTING WORLD
    void drawWorld(Graphics2D g) {

        g.setColor(new Color(90, 160, 210));
        g.fillRect(0, 0, WIDTH, HEIGHT / 2);

        g.setColor(new Color(65, 65, 65));
        g.fillRect(0, HEIGHT / 2, WIDTH, HEIGHT / 2);

        for (int x = 0; x < WIDTH; x++) {

            double rayAngle = angle +
                Math.atan((x - WIDTH / 2.0) / 550.0);

            double rayX = Math.cos(rayAngle);
            double rayY = Math.sin(rayAngle);

            double distance = 0;
            boolean hit = false;

            while (!hit && distance < 15) {

                distance += 0.025;

                double checkX = px + rayX * distance;
                double checkY = py + rayY * distance;

                if (isWall(checkX, checkY)) {
                    hit = true;
                }
            }

            // Correct fisheye distortion
            distance *= Math.cos(rayAngle - angle);
            distance = Math.max(0.1, distance);

            int wallHeight = (int)(HEIGHT / distance);

            int wallTop = HEIGHT / 2 - wallHeight / 2;
            int wallBottom = HEIGHT / 2 + wallHeight / 2;

            int shade = Math.max(25,
                Math.min(210, (int)(190 / distance)));

            // Alternating wall shades
            if (((int)(px + rayX * distance) +
                 (int)(py + rayY * distance)) % 2 == 0) {
                shade = (int)(shade * 0.8);
            }

            g.setColor(new Color(
                shade / 2,
                shade,
                Math.min(255, shade + 25)
            ));

            g.drawLine(x, wallTop, x, wallBottom);
        }
    }

    // DRAW ENEMIES IN FIRST PERSON
    void drawEnemies(Graphics2D g) {

        for (Enemy enemy : enemies) {

            if (!enemy.alive) continue;

            double dx = enemy.x - px;
            double dy = enemy.y - py;

            double distance = Math.sqrt(dx * dx + dy * dy);

            double enemyAngle = Math.atan2(dy, dx);

            double difference = Math.atan2(
                Math.sin(enemyAngle - angle),
                Math.cos(enemyAngle - angle)
            );

            if (Math.abs(difference) > 0.9 || distance > 10) {
                continue;
            }

            int screenX = (int)(
                WIDTH / 2.0 + Math.tan(difference) * 550
            );

            int size = (int)(350 / distance);

            int screenY = HEIGHT / 2 - size / 3;

            // Enemy body
            g.setColor(new Color(30, 80, 220));
            g.fillRoundRect(
                screenX - size / 3,
                screenY,
                size * 2 / 3,
                size,
                15, 15
            );

            // Head
            g.setColor(new Color(230, 180, 130));
            g.fillOval(
                screenX - size / 4,
                screenY - size / 3,
                size / 2,
                size / 2
            );

            // Eyes
            g.setColor(Color.BLACK);
            g.fillOval(screenX - size / 8, screenY - size / 6,
                Math.max(2, size / 15), Math.max(2, size / 15));

            // Enemy health bar
            g.setColor(Color.RED);
            g.fillRect(screenX - size / 2,
                screenY - size / 2, size, 7);

            g.setColor(Color.GREEN);
            g.fillRect(screenX - size / 2,
                screenY - size / 2,
                size * enemy.health / 100, 7);
        }
    }

    // WEAPON DISPLAY
    void drawWeapon(Graphics2D g) {

        int x = WIDTH / 2;
        int y = HEIGHT - 40;

        // Gun body
        g.setColor(new Color(35, 35, 35));
        g.fillRoundRect(x - 35, y - 120, 70, 130, 12, 12);

        // Barrel
        g.setColor(Color.DARK_GRAY);
        g.fillRect(x - 12, y - 175, 24, 75);

        // Gun details
        g.setColor(Color.GRAY);
        g.fillRect(x - 28, y - 105, 56, 15);

        g.setColor(Color.BLACK);
        g.fillRect(x - 20, y - 65, 40, 35);
    }

    // CROSSHAIR
    void drawCrosshair(Graphics2D g) {

        g.setColor(Color.WHITE);

        int x = WIDTH / 2;
        int y = HEIGHT / 2;

        g.setStroke(new BasicStroke(3));

        g.drawLine(x - 12, y, x - 4, y);
        g.drawLine(x + 4, y, x + 12, y);
        g.drawLine(x, y - 12, x, y - 4);
        g.drawLine(x, y + 4, x, y + 12);

        g.setStroke(new BasicStroke(1));
    }

    // GAME HUD
    void drawHUD(Graphics2D g) {

        g.setFont(new Font("Arial", Font.BOLD, 24));

        // Health
        g.setColor(Color.BLACK);
        g.fillRoundRect(20, HEIGHT - 75, 220, 35, 10, 10);

        g.setColor(Color.RED);
        g.fillRoundRect(25, HEIGHT - 70,
            Math.max(0, health * 2), 25, 8, 8);

        g.setColor(Color.WHITE);
        g.drawString("HP: " + health, 25, HEIGHT - 85);

        // Ammo
        g.setFont(new Font("Arial", Font.BOLD, 28));
        g.setColor(Color.WHITE);

        String ammoText = reloading ?
            "RELOADING..." : ammo + " / " + reserveAmmo;

        g.drawString(ammoText, WIDTH - 230, HEIGHT - 35);

        // Kills
        g.setFont(new Font("Arial", Font.BOLD, 22));
        g.drawString("ELIMINATIONS: " + kills, 20, 40);

        // Remaining enemies
        int remaining = 0;

        for (Enemy enemy : enemies) {
            if (enemy.alive) remaining++;
        }

        g.drawString("PLAYERS LEFT: " + (remaining + 1),
            20, 70);
    }

    // STORM WARNING
    void drawStormWarning(Graphics2D g) {

        double dx = px - stormX;
        double dy = py - stormY;

        if (Math.sqrt(dx * dx + dy * dy) > stormRadius) {

            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 30));

            g.drawString("YOU ARE IN THE STORM!",
                WIDTH / 2 - 180, 110);
        }
    }

    // END SCREEN
    void drawEndScreen(Graphics2D g, String message) {

        g.setColor(new Color(15, 20, 35));
        g.fillRect(0, 0, WIDTH, HEIGHT);

        g.setFont(new Font("Arial", Font.BOLD, 65));
        g.setColor(message.contains("VICTORY") ?
            Color.YELLOW : Color.RED);

        FontMetrics fm = g.getFontMetrics();

        g.drawString(message,
            (WIDTH - fm.stringWidth(message)) / 2,
            HEIGHT / 2 - 40);

        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.setColor(Color.WHITE);

        g.drawString("Eliminations: " + kills,
            WIDTH / 2 - 100, HEIGHT / 2 + 30);

        g.drawString("Press ENTER to play again",
            WIDTH / 2 - 190, HEIGHT / 2 + 100);
    }

    // RESTART
    void restart() {

        px = 3.5;
        py = 3.5;
        angle = 0;

        health = 100;
        ammo = 30;
        reserveAmmo = 120;
        kills = 0;

        gameOver = false;
        victory = false;
        reloading = false;

        startTime = System.currentTimeMillis();

        stormRadius = 8;

        enemies.clear();

        enemies.add(new Enemy(8.5, 2.5));
        enemies.add(new Enemy(9.5, 5.5));
        enemies.add(new Enemy(3.5, 9.5));
        enemies.add(new Enemy(7.5, 9.5));
        enemies.add(new Enemy(5.5, 7.5));
        enemies.add(new Enemy(2.5, 6.5));
        enemies.add(new Enemy(9.5, 9.5));
    }

    // KEYBOARD CONTROLS
    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_W) forward = true;
        if (key == KeyEvent.VK_S) backward = true;
        if (key == KeyEvent.VK_A) left = true;
        if (key == KeyEvent.VK_D) right = true;

        if (key == KeyEvent.VK_LEFT) turnLeft = true;
        if (key == KeyEvent.VK_RIGHT) turnRight = true;

        if (key == KeyEvent.VK_R) reload();

        if (key == KeyEvent.VK_ENTER &&
            (gameOver || victory)) {
            restart();
        }
    }

    public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_W) forward = false;
        if (key == KeyEvent.VK_S) backward = false;
        if (key == KeyEvent.VK_A) left = false;
        if (key == KeyEvent.VK_D) right = false;

        if (key == KeyEvent.VK_LEFT) turnLeft = false;
        if (key == KeyEvent.VK_RIGHT) turnRight = false;
    }

    public void keyTyped(KeyEvent e) {}

    // MOUSE CONTROLS
    public void mousePressed(MouseEvent e) {

        if (SwingUtilities.isLeftMouseButton(e)) {
            shoot();
        }
    }

    public void mouseMoved(MouseEvent e) {

        if (lastMouseX != -1) {

            int difference = e.getX() - lastMouseX;

            angle += difference * 0.008;

            angle = (angle + Math.PI * 2) %
                (Math.PI * 2);
        }

        lastMouseX = e.getX();
    }

    public void mouseDragged(MouseEvent e) {
        mouseMoved(e);
    }

    public void mouseReleased(MouseEvent e) {
        lastMouseX = -1;
    }

    public void mouseClicked(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}

    // START GAME
    public static void main(String[] args) {

        JFrame frame = new JFrame("JAVA BATTLE ROYALE");

        FortniteFPS game = new FortniteFPS();

        frame.add(game);
        frame.pack();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);

        game.requestFocusInWindow();
    }
}
