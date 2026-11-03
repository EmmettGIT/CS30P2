package Snake;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.io.*;
import java.util.*;
import java.util.List;
import javax.sound.sampled.*;

public class snakeV3 extends JPanel {

    private static final int W = 800, H = 700, CELL = 25;
    private static final int COLS = W / CELL, ROWS = 24, TOP = 100;
    private static final String SAVE_FILE = "snake_arcade_save.dat";

    private enum Screen { MENU, PLAYING, PAUSED, SHOP, SETTINGS, ACHIEVEMENTS, GAME_OVER }
    private enum MapType { CLASSIC, MAZE, LAVA, ICE, FOREST }
    private enum Difficulty { EASY, NORMAL, HARD, INSANE }
    private enum Power { SHIELD, SPEED, SLOW, BONUS }

    private Screen screen = Screen.MENU;
    private MapType map = MapType.CLASSIC;
    private Difficulty difficulty = Difficulty.NORMAL;

    private final javax.swing.Timer timer;
    private final javax.swing.Timer musicTimer;
    private int musicNote = 0;

    private final Random random = new Random();
    private final SaveData save = loadSave();

    private ArrayList<Point> p1 = new ArrayList<>();
    private ArrayList<Point> p2 = new ArrayList<>();
    private ArrayList<Point> walls = new ArrayList<>();

    private Point food, goldFood, powerFood, bossTarget;

    private int dir1x = 1, dir1y, next1x = 1, next1y;
    private int dir2x = -1, dir2y, next2x = -1, next2y;

    private boolean twoPlayer, soundOn = true, musicOn;

    private int score, score2, lives = 3, level = 1, speed;
    private int goldTicks, powerTicks;
    private int bossHits, bossRequired = 3, bossNumber;

    private boolean bossActive, shieldActive;

    private int shieldTicks, speedTicks, slowTicks, invincibleTicks;

    private String status = "Choose a game mode";
    private int animationTick;

    private final Color bg = new Color(12, 17, 30);
    private final Color panel = new Color(24, 33, 52);
    private final Color neon = new Color(66, 255, 150);
    private final Color cyan = new Color(65, 210, 255);
    private final Color gold = new Color(255, 205, 65);
    private final Color red = new Color(255, 82, 100);

    private final String[] skins = {
        "Neon Green", "Cyber Blue", "Sunset", "Purple Haze", "Gold Rush"
    };

    private final Color[] skinColors = {
        new Color(65, 255, 145),
        new Color(65, 190, 255),
        new Color(255, 125, 75),
        new Color(190, 105, 255),
        new Color(255, 210, 65)
    };

    private final int[] skinPrices = {0, 50, 100, 150, 250};

    public snakeV3() {

        setPreferredSize(new Dimension(W, H));
        setBackground(bg);
        setFocusable(true);

        setupKeys();

        timer = new javax.swing.Timer(110, e -> tick());
        timer.start();

        musicTimer = new javax.swing.Timer(520, e -> {

            if (musicOn && soundOn &&
                (screen == Screen.MENU || screen == Screen.PLAYING)) {

                int[] notes = {392, 494, 587, 494, 440, 523, 659, 523};

                playTone(notes[musicNote++ % notes.length], 110);
            }
        });

        musicTimer.start();
    }

    // SAVE SYSTEM

    private static class SaveData implements Serializable {

        int coins = 0;
        int highScore = 0;
        int gamesPlayed = 0;
        int selectedSkin = 0;

        boolean[] unlocked = {true, false, false, false, false};

        Set<String> achievements = new HashSet<>();
    }

    private SaveData loadSave() {

        try (ObjectInputStream in =
                 new ObjectInputStream(new FileInputStream(SAVE_FILE))) {

            return (SaveData) in.readObject();

        } catch (Exception ex) {

            return new SaveData();
        }
    }

    private void saveProgress() {

        try (ObjectOutputStream out =
                 new ObjectOutputStream(new FileOutputStream(SAVE_FILE))) {

            out.writeObject(save);

        } catch (IOException ex) {

            status = "Could not save progress.";
        }
    }

    // KEYBOARD CONTROLS

    private void setupKeys() {

        bind("UP", KeyEvent.VK_UP);
        bind("DOWN", KeyEvent.VK_DOWN);
        bind("LEFT", KeyEvent.VK_LEFT);
        bind("RIGHT", KeyEvent.VK_RIGHT);

        bind("W", KeyEvent.VK_W);
        bind("A", KeyEvent.VK_A);
        bind("S", KeyEvent.VK_S);
        bind("D", KeyEvent.VK_D);

        bind("SPACE", KeyEvent.VK_SPACE);
        bind("ENTER", KeyEvent.VK_ENTER);
        bind("ESC", KeyEvent.VK_ESCAPE);
        bind("H", KeyEvent.VK_H);
        bind("M", KeyEvent.VK_M);

        bind("I", KeyEvent.VK_I);
        bind("J", KeyEvent.VK_J);
        bind("K", KeyEvent.VK_K);
        bind("L", KeyEvent.VK_L);
    }

    private void bind(String name, int key) {

        getInputMap(WHEN_IN_FOCUSED_WINDOW)
            .put(KeyStroke.getKeyStroke(key, 0), name);

        getActionMap().put(name, new AbstractAction() {

            public void actionPerformed(ActionEvent e) {

                keyAction(name);
            }
        });
    }

    private void keyAction(String k) {

        if (screen == Screen.MENU) {

            if (k.equals("ENTER")) startGame(false);
            if (k.equals("H")) screen = Screen.ACHIEVEMENTS;

            updateButtonVisibility1();
            repaint();
            return;
        }

        if (screen == Screen.PLAYING) {

            if (k.equals("SPACE") || k.equals("ESC")) {
                screen = Screen.PAUSED;
            } else if (k.equals("M")) {
                musicOn = !musicOn;
            } else {
                setDirection(k);
            }

            repaint();
            return;
        }

        if (screen == Screen.PAUSED) {

            if (k.equals("SPACE") || k.equals("ENTER")) {
                screen = Screen.PLAYING;
            }

            if (k.equals("ESC")) {
                screen = Screen.MENU;
            }

            updateButtonVisibility1();
            repaint();
            return;
        }

        if (screen == Screen.GAME_OVER) {

            if (k.equals("ENTER")) startGame(twoPlayer);
            if (k.equals("ESC")) screen = Screen.MENU;

            updateButtonVisibility1();
            repaint();
            return;
        }

        if (k.equals("ESC") || k.equals("ENTER")) {
            screen = Screen.MENU;
        }

        updateButtonVisibility1();
        repaint();
    }

    private void setDirection(String k) {

        if (k.equals("I") && twoPlayer && dir2y != 1) {
            next2x = 0;
            next2y = -1;
        }

        else if (k.equals("K") && twoPlayer && dir2y != -1) {
            next2x = 0;
            next2y = 1;
        }

        else if (k.equals("J") && twoPlayer && dir2x != 1) {
            next2x = -1;
            next2y = 0;
        }

        else if (k.equals("L") && twoPlayer && dir2x != -1) {
            next2x = 1;
            next2y = 0;
        }

        else if (k.equals("UP") || k.equals("W")) {

            if (dir1y != 1) {
                next1x = 0;
                next1y = -1;
            }
        }

        else if (k.equals("DOWN") || k.equals("S")) {

            if (dir1y != -1) {
                next1x = 0;
                next1y = 1;
            }
        }

        else if (k.equals("LEFT") || k.equals("A")) {

            if (dir1x != 1) {
                next1x = -1;
                next1y = 0;
            }
        }

        else if (k.equals("RIGHT") || k.equals("D")) {

            if (dir1x != -1) {
                next1x = 1;
                next1y = 0;
            }
        }
    }

    // GAME SETUP

    private void startGame(boolean multiplayer) {

        twoPlayer = multiplayer;

        score = 0;
        score2 = 0;
        lives = 3;
        level = 1;

        bossActive = false;
        bossHits = 0;
        bossNumber = 0;

        shieldTicks = 0;
        speedTicks = 0;
        slowTicks = 0;
        invincibleTicks = 0;

        goldFood = null;
        powerFood = null;
        bossTarget = null;

        dir1x = next1x = 1;
        dir1y = next1y = 0;

        dir2x = next2x = -1;
        dir2y = next2y = 0;

        p1.clear();
        p2.clear();
        walls.clear();

        p1.add(new Point(5, 11));
        p1.add(new Point(4, 11));
        p1.add(new Point(3, 11));

        if (twoPlayer) {

            p2.add(new Point(COLS - 6, 12));
            p2.add(new Point(COLS - 5, 12));
            p2.add(new Point(COLS - 4, 12));
        }

        buildMap();
        spawnFood();

        screen = Screen.PLAYING;

        save.gamesPlayed++;

        updateSpeed();
        saveProgress();

        playTone(520, 75);
    }

    private void buildMap() {

        walls.clear();

        if (map == MapType.MAZE) {

            for (int x = 5; x < COLS - 5; x++) {

                if (x != 9 && x != 10 && x != 20 && x != 21) {

                    walls.add(new Point(x, 6));
                    walls.add(new Point(x, 17));
                }
            }

            for (int y = 8; y < 16; y++) {

                if (y != 11 && y != 12) {
                    walls.add(new Point(12, y));
                }
            }
        }

        else if (map == MapType.LAVA) {

            for (int x = 3; x < COLS - 3; x += 3) {

                for (int y = 2; y < ROWS - 2; y += 5) {

                    walls.add(new Point(x, y));
                }
            }
        }

        else if (map == MapType.FOREST) {

            for (int i = 0; i < 17; i++) {

                Point p = randomCell();

                if (p.x > 3 && p.x < COLS - 4 &&
                    p.y > 2 && p.y < ROWS - 3) {

                    walls.add(p);
                }
            }
        }

        walls.removeIf(p -> p1.contains(p) || p2.contains(p));
    }

    private Point randomCell() {

        return new Point(
            random.nextInt(COLS),
            random.nextInt(ROWS)
        );
    }

    private void spawnFood() {

        food = freeCell();

        if (food == null) {
            food = new Point(15, 10);
        }
    }

    private Point freeCell() {

        for (int tries = 0; tries < 1000; tries++) {

            Point p = randomCell();

            if (!occupied(p)) {
                return p;
            }
        }

        return null;
    }

    private boolean occupied(Point p) {

        return p1.contains(p) ||
            p2.contains(p) ||
            walls.contains(p) ||
            (goldFood != null && goldFood.equals(p)) ||
            (powerFood != null && powerFood.equals(p)) ||
            (bossTarget != null && bossTarget.equals(p));
    }

    // GAME LOOP

    private void tick() {

        animationTick++;

        if (screen != Screen.PLAYING) {
            repaint();
            return;
        }

        moveOne(p1, true);

        if (twoPlayer && screen == Screen.PLAYING) {
            moveOne(p2, false);
        }

        if (goldFood != null && --goldTicks <= 0) {
            goldFood = null;
        }

        if (powerFood != null && --powerTicks <= 0) {
            powerFood = null;
        }

        if (shieldTicks > 0) shieldTicks--;
        if (speedTicks > 0) speedTicks--;
        if (slowTicks > 0) slowTicks--;
        if (invincibleTicks > 0) invincibleTicks--;

        if (bossActive && bossTarget != null &&
            animationTick % 5 == 0) {

            moveBossTarget();
        }

        updateSpeed();
        repaint();
    }

    private void moveOne(ArrayList<Point> snake, boolean playerOne) {

        if (snake.isEmpty() || screen != Screen.PLAYING) {
            return;
        }

        int dx = playerOne ? next1x : next2x;
        int dy = playerOne ? next1y : next2y;

        if (playerOne) {
            dir1x = dx;
            dir1y = dy;
        } else {
            dir2x = dx;
            dir2y = dy;
        }

        Point head = snake.get(0);

        Point nh = new Point(head.x + dx, head.y + dy);

        boolean ate = nh.equals(food) ||
            nh.equals(goldFood) ||
            nh.equals(powerFood) ||
            (bossActive && nh.equals(bossTarget));

        if (!ate) {
            snake.remove(snake.size() - 1);
        }

        snake.add(0, nh);

        boolean hit =
            nh.x < 0 ||
            nh.x >= COLS ||
            nh.y < 0 ||
            nh.y >= ROWS ||
            walls.contains(nh) ||
            snake.subList(1, snake.size()).contains(nh);

        if (twoPlayer && playerOne && p2.contains(nh)) hit = true;
        if (twoPlayer && !playerOne && p1.contains(nh)) hit = true;

        if (hit) {

            if (playerOne && shieldTicks > 0) {
                shieldTicks = 0;
                snake.remove(0);
                snake.add(0, head);
                return;
            }

            if (invincibleTicks > 0) {
                snake.remove(0);
                snake.add(0, head);
                return;
            }

            if (playerOne) {

                lives--;

                if (lives <= 0) {
                    endGame();
                } else {
                    resetAfterHit();
                }

            } else {

                score += 25;
                p2.clear();
            }

            playTone(150, 180);
            return;
        }

        if (nh.equals(food)) {

            if (playerOne) score += 10;
            else score2 += 10;

            spawnFood();
            awardCoins(2);
            playTone(760, 65);

            maybeSpawnSpecial();
            checkLevel();
        }

        else if (goldFood != null && nh.equals(goldFood)) {

            if (playerOne) score += 50;
            else score2 += 50;

            goldFood = null;
            awardCoins(10);
            playTone(1000, 100);

            checkLevel();
        }

        else if (powerFood != null && nh.equals(powerFood)) {

            activatePower();
            powerFood = null;
            awardCoins(5);
            playTone(900, 100);
        }

        else if (bossActive && bossTarget != null &&
                 nh.equals(bossTarget)) {

            bossHits++;
            bossTarget = freeCell();

            awardCoins(15);
            playTone(1100, 130);

            if (bossHits >= bossRequired) {

                bossActive = false;
                bossTarget = null;

                if (playerOne) score += 150;

                awardCoins(50);
                status = "BOSS DEFEATED! +150 BONUS";

                unlock("Boss Hunter");
            }
        }

        if (playerOne) {

            if (score > save.highScore) {
                save.highScore = score;
            }

            if (score >= 100) unlock("Century");
            if (snake.size() >= 20) unlock("Long Snake");
            if (level >= 5) unlock("Level Five");

            saveProgress();
        }
    }

    private void resetAfterHit() {

        p1.clear();

        p1.add(new Point(5, 11));
        p1.add(new Point(4, 11));
        p1.add(new Point(3, 11));

        dir1x = next1x = 1;
        dir1y = next1y = 0;

        if (twoPlayer) {

            p2.clear();

            p2.add(new Point(COLS - 6, 12));
            p2.add(new Point(COLS - 5, 12));
        }
    }

    private void endGame() {

        screen = Screen.GAME_OVER;

        unlock("First Game");

        if (save.gamesPlayed >= 10) {
            unlock("Arcade Regular");
        }

        saveProgress();
        playTone(110, 400);
    }

    // LEVELS AND BOSSES

    private void checkLevel() {

        int newLevel = score / 100 + 1;

        if (newLevel > level) {

            level = newLevel;

            updateSpeed();

            if (level % 5 == 0 && !bossActive) {
                startBoss();
            }

            if (level >= 10) {
                unlock("Level Ten");
            }
        }
    }

    private void startBoss() {

        bossActive = true;
        bossHits = 0;
        bossNumber++;

        bossTarget = freeCell();

        status = "BOSS BATTLE! Catch the moving target 3 times!";

        playTone(330, 250);
    }

    private void moveBossTarget() {

        if (bossTarget == null) return;

        ArrayList<Point> choices = new ArrayList<>();

        int[][] steps = {
            {1, 0}, {-1, 0}, {0, 1}, {0, -1}
        };

        for (int[] s : steps) {

            Point q = new Point(
                bossTarget.x + s[0],
                bossTarget.y + s[1]
            );

            if (q.x >= 0 && q.x < COLS &&
                q.y >= 0 && q.y < ROWS &&
                !occupied(q)) {

                choices.add(q);
            }
        }

        if (!choices.isEmpty()) {

            bossTarget = choices.get(
                random.nextInt(choices.size())
            );
        }
    }

    // SPECIAL FOOD AND POWER-UPS

    private void maybeSpawnSpecial() {

        if (goldFood == null && random.nextInt(4) == 0) {

            goldFood = freeCell();
            goldTicks = 55;
        }

        if (powerFood == null && random.nextInt(5) == 0) {

            powerFood = freeCell();
            powerTicks = 75;
        }
    }

    private void activatePower() {

        Power p = Power.values()[random.nextInt(Power.values().length)];

        switch (p) {

            case SHIELD:
                shieldTicks = 70;
                status = "SHIELD ACTIVATED";
                break;

            case SPEED:
                speedTicks = 65;
                status = "SPEED BOOST";
                break;

            case SLOW:
                slowTicks = 65;
                status = "SLOW MOTION";
                break;

            case BONUS:
                score += 30;
                status = "BONUS +30";
                break;
        }

        updateSpeed();
    }

    private void updateSpeed() {

        int base;

        switch (difficulty) {

            case EASY:
                base = 150;
                break;

            case HARD:
                base = 85;
                break;

            case INSANE:
                base = 60;
                break;

            default:
                base = 115;
        }

        int adjusted = Math.max(45, base - (level - 1) * 5);

        if (speedTicks > 0) {
            adjusted = Math.max(35, adjusted - 25);
        }

        if (slowTicks > 0) {
            adjusted += 45;
        }

        speed = adjusted;
        timer.setDelay(speed);
    }

    // COINS AND ACHIEVEMENTS

    private void awardCoins(int amount) {

        save.coins += amount;
        saveProgress();
    }

    private void unlock(String name) {

        if (save.achievements.add(name)) {

            status = "ACHIEVEMENT UNLOCKED: " + name;

            awardCoins(20);
        }
    }

    // SOUND

    private void playTone(int frequency, int durationMs) {

        if (!soundOn) return;

        Thread t = new Thread(() -> {

            try {

                float sampleRate = 16000;

                AudioFormat format =
                    new AudioFormat(sampleRate, 8, 1, true, false);

                SourceDataLine line =
                    AudioSystem.getSourceDataLine(format);

                line.open(format, durationMs * 20);
                line.start();

                byte[] data = new byte[
                    (int)(sampleRate * durationMs / 1000)
                ];

                for (int i = 0; i < data.length; i++) {

                    data[i] = (byte)(
                        Math.sin(2 * Math.PI * frequency * i / sampleRate) * 55
                    );
                }

                line.write(data, 0, data.length);

                line.drain();
                line.close();

            } catch (Exception ignored) {

                Toolkit.getDefaultToolkit().beep();
            }
        });

        t.setDaemon(true);
        t.start();
    }

    // BUTTONS

    private JButton button(String text, Runnable action) {

        JButton b = new JButton(text);

        b.setFont(new Font("Arial", Font.BOLD, 16));
        b.setForeground(Color.WHITE);
        b.setBackground(new Color(38, 55, 78));
        b.setFocusPainted(false);

        b.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(75, 105, 140), 1),
            new EmptyBorder(10, 18, 10, 18)
        ));

        b.addActionListener(e -> {

            action.run();
            repaint();
            requestFocusInWindow();
        });

        return b;
    }

    private void addMenuButton(
        String text,
        int x,
        int y,
        int w,
        int h,
        Runnable action
    ) {

        JButton b = button(text, () -> {

            action.run();
            updateButtonVisibility1();
        });

        b.setBounds(x, y, w, h);

        add(b);
    }

    public void addNotify1() {

        super.addNotify();

        if (getComponentCount() == 0) {
            installButtons1();
        }
    }

    private void installButtons1() {

        setLayout(null);

        addMenuButton("1 PLAYER", 230, 465, 150, 42,
            () -> startGame(false));

        addMenuButton("2 PLAYERS", 420, 465, 150, 42,
            () -> startGame(true));

        addMenuButton("SHOP", 95, 520, 120, 38,
            () -> screen = Screen.SHOP);

        addMenuButton("MAP", 235, 520, 120, 38,
            () -> map = MapType.values()[
                (map.ordinal() + 1) % MapType.values().length
            ]);

        addMenuButton("DIFFICULTY", 375, 520, 145, 38,
            () -> difficulty = Difficulty.values()[
                (difficulty.ordinal() + 1) % Difficulty.values().length
            ]);

        addMenuButton("ACHIEVEMENTS", 540, 520, 165, 38,
            () -> screen = Screen.ACHIEVEMENTS);

        addMenuButton("SETTINGS", 310, 570, 150, 38,
            () -> screen = Screen.SETTINGS);

        addMenuButton("SOUND ON/OFF", 310, 615, 150, 32,
            () -> soundOn = !soundOn);

        addMenuButton("BACK / MENU", 315, 645, 160, 30,
            () -> screen = Screen.MENU);

        for (int i = 0; i < skins.length; i++) {

            final int index = i;

            addMenuButton("SELECT", 630, 178 + i * 75, 95, 35, () -> {

                if (save.unlocked[index]) {

                    save.selectedSkin = index;

                } else if (save.coins >= skinPrices[index]) {

                    save.coins -= skinPrices[index];
                    save.unlocked[index] = true;
                    save.selectedSkin = index;
                }

                saveProgress();
            });
        }

        addMenuButton("TOGGLE MUSIC", 315, 390, 170, 38,
            () -> musicOn = !musicOn);

        addMenuButton("TOGGLE SOUND", 315, 440, 170, 38,
            () -> soundOn = !soundOn);

        updateButtonVisibility1();
    }

    private void updateButtonVisibility1() {

        for (Component c : getComponents()) {
            c.setVisible(false);
        }

        if (screen == Screen.MENU) {

            for (int i = 0; i < 9; i++) {
                getComponent(i).setVisible(true);
            }

        } else if (screen == Screen.SHOP) {

            for (int i = 9; i < 14; i++) {
                getComponent(i).setVisible(true);
            }

            getComponent(8).setVisible(true);

        } else if (screen == Screen.SETTINGS) {

            getComponent(14).setVisible(true);
            getComponent(15).setVisible(true);
            getComponent(8).setVisible(true);

        } else if (screen == Screen.ACHIEVEMENTS ||
                   screen == Screen.PAUSED ||
                   screen == Screen.GAME_OVER) {

            getComponent(8).setVisible(true);
        }

        repaint();
    }

    // GRAPHICS

    private void drawCentered(
        Graphics2D g,
        String text,
        int y,
        int size,
        Color color
    ) {

        g.setFont(new Font("Arial", Font.BOLD, size));
        g.setColor(color);

        FontMetrics fm = g.getFontMetrics();

        g.drawString(
            text,
            (W - fm.stringWidth(text)) / 2,
            y
        );
    }

    private void drawBackground(Graphics2D g) {

        g.setPaint(new GradientPaint(
            0, 0, new Color(9, 14, 28),
            W, H, new Color(27, 39, 62)
        ));

        g.fillRect(0, 0, W, H);

        g.setColor(new Color(42, 58, 82, 65));

        for (int x = 0; x < W; x += CELL) {
            g.drawLine(x, 0, x, H);
        }

        for (int y = 0; y < H; y += CELL) {
            g.drawLine(0, y, W, y);
        }
    }

    private void drawMenu(Graphics2D g) {

        drawBackground(g);

        drawCentered(g, "SNAKE", 155, 76, neon);
        drawCentered(g, "ULTIMATE ARCADE EDITION", 195, 23, Color.WHITE);

        drawCentered(
            g,
            "Eat • Grow • Survive • Upgrade",
            235,
            18,
            new Color(180, 195, 215)
        );

        drawCentered(
            g,
            "BEST SCORE: " + save.highScore +
            "     COINS: " + save.coins,
            285,
            20,
            gold
        );

        drawCentered(g, "SELECT A MODE", 345, 20, cyan);

        drawCentered(
            g,
            "Choose a game mode using the buttons below",
            380,
            15,
            Color.LIGHT_GRAY
        );

        drawCentered(
            g,
            "MAP: " + map + "     DIFFICULTY: " + difficulty,
            430,
            18,
            Color.WHITE
        );

        drawCentered(
            g,
            "P1: WASD / ARROWS     P2: IJKL     SPACE: PAUSE",
            570,
            15,
            Color.LIGHT_GRAY
        );

        drawCentered(
            g,
            "H: ACHIEVEMENTS     ESC: BACK",
            600,
            14,
            Color.LIGHT_GRAY
        );

        drawCentered(g, status, 645, 14, gold);
    }

    private void drawGame(Graphics2D g) {

        drawBackground(g);

        g.setColor(new Color(8, 12, 23, 230));
        g.fillRect(0, 0, W, TOP);

        g.setFont(new Font("Arial", Font.BOLD, 17));
        g.setColor(Color.WHITE);

        g.drawString("P1: " + score, 18, 30);
        g.drawString("LEVEL " + level, 175, 30);

        g.setColor(gold);
        g.drawString("COINS: " + save.coins, 320, 30);

        g.setColor(cyan);
        g.drawString("BEST: " + save.highScore, 490, 30);

        g.setColor(red);
        g.drawString("LIVES: " + lives, 665, 30);

        g.setFont(new Font("Arial", Font.PLAIN, 13));
        g.setColor(Color.LIGHT_GRAY);

        g.drawString(
            "Map: " + map + "  |  " + difficulty +
            (twoPlayer ? "  |  P2: " + score2 : ""),
            18, 65
        );

        g.drawString("SPACE = PAUSE", 650, 65);

        for (Point p : walls) {
            drawCell(g, p, new Color(100, 115, 140), 3);
        }

        for (Point p : p1) {
            drawCell(g, p, skinColors[save.selectedSkin], 7);
        }

        if (twoPlayer) {

            for (Point p : p2) {
                drawCell(g, p, cyan, 7);
            }
        }

        drawFood(g, food, red, "●");
        drawFood(g, goldFood, gold, "★");
        drawFood(g, powerFood, new Color(190, 100, 255), "?");
        drawFood(g, bossTarget, new Color(255, 80, 220), "B");

        if (bossActive) {

            g.setColor(new Color(255, 80, 220));
            g.setFont(new Font("Arial", Font.BOLD, 17));

            g.drawString(
                "BOSS: " + bossHits + "/" + bossRequired,
                18, 90
            );
        }

        if (shieldTicks > 0) drawStatus(g, "SHIELD");
        else if (speedTicks > 0) drawStatus(g, "SPEED BOOST");
        else if (slowTicks > 0) drawStatus(g, "SLOW MOTION");

        if (map == MapType.LAVA) {

            g.setColor(new Color(255, 80, 20, 45));
            g.fillRect(0, TOP, W, H - TOP);
        }

        g.setColor(gold);
        g.setFont(new Font("Arial", Font.PLAIN, 13));
        g.drawString(status, 18, H - 12);
    }

    private void drawStatus(Graphics2D g, String s) {

        g.setColor(gold);
        g.setFont(new Font("Arial", Font.BOLD, 14));

        g.drawString(s, 650, 90);
    }

    private void drawCell(Graphics2D g, Point p, Color c, int arc) {

        int x = p.x * CELL;
        int y = TOP + p.y * CELL;

        g.setColor(new Color(
            c.getRed(), c.getGreen(), c.getBlue(), 65
        ));

        g.fillRoundRect(
            x - 3, y - 3,
            CELL + 6, CELL + 6,
            arc + 4, arc + 4
        );

        g.setColor(c);

        g.fillRoundRect(
            x + 2, y + 2,
            CELL - 4, CELL - 4,
            arc, arc
        );
    }

    private void drawFood(Graphics2D g, Point p, Color c, String symbol) {

        if (p == null) return;

        int x = p.x * CELL;
        int y = TOP + p.y * CELL;

        int pulse = (int)(Math.sin(animationTick * 0.18) * 2);

        g.setColor(new Color(
            c.getRed(), c.getGreen(), c.getBlue(), 65
        ));

        g.fill(new Ellipse2D.Double(
            x - 4 - pulse,
            y - 4 - pulse,
            CELL + 8 + pulse * 2,
            CELL + 8 + pulse * 2
        ));

        g.setColor(c);
        g.fillOval(x + 3, y + 3, CELL - 6, CELL - 6);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 13));

        FontMetrics fm = g.getFontMetrics();

        g.drawString(
            symbol,
            x + (CELL - fm.stringWidth(symbol)) / 2,
            y + 18
        );
    }

    private void drawOverlay(
        Graphics2D g,
        String title,
        String subtitle
    ) {

        g.setColor(new Color(0, 0, 0, 190));
        g.fillRect(0, 0, W, H);

        drawCentered(g, title, 280, 52, neon);
        drawCentered(g, subtitle, 330, 20, Color.WHITE);
    }

    private void drawShop(Graphics2D g) {

        drawBackground(g);

        drawCentered(g, "SNAKE SKIN SHOP", 115, 42, gold);

        drawCentered(
            g,
            "Coins: " + save.coins,
            155,
            20,
            Color.WHITE
        );

        for (int i = 0; i < skins.length; i++) {

            int y = 205 + i * 75;

            g.setColor(panel);
            g.fillRoundRect(175, y - 30, 450, 55, 15, 15);

            g.setColor(skinColors[i]);
            g.fillRoundRect(195, y - 15, 55, 25, 9, 9);

            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 18));

            g.drawString(skins[i], 275, y + 2);

            String label = save.unlocked[i]
                ? (save.selectedSkin == i ? "EQUIPPED" : "OWNED")
                : "BUY: " + skinPrices[i] + " COINS";

            g.setColor(save.unlocked[i] ? neon : gold);

            g.drawString(label, 475, y + 2);
        }

        drawCentered(g, "Press ESC to return", 620, 17, Color.LIGHT_GRAY);
    }

    private void drawAchievements(Graphics2D g) {

        drawBackground(g);

        drawCentered(g, "ACHIEVEMENTS", 115, 42, gold);

        String[] all = {
            "First Game",
            "Century",
            "Long Snake",
            "Level Five",
            "Level Ten",
            "Boss Hunter",
            "Arcade Regular"
        };

        for (int i = 0; i < all.length; i++) {

            boolean done = save.achievements.contains(all[i]);

            g.setColor(done ? neon : new Color(130, 140, 160));

            g.setFont(new Font("Arial", Font.BOLD, 20));

            g.drawString(
                (done ? "✓  " : "○  ") + all[i],
                230,
                195 + i * 48
            );
        }

        drawCentered(
            g,
            "Each new achievement awards 20 coins",
            570,
            16,
            gold
        );

        drawCentered(
            g,
            "Press ESC to return",
            620,
            17,
            Color.LIGHT_GRAY
        );
    }

    private void drawSettings(Graphics2D g) {

        drawBackground(g);

        drawCentered(g, "SETTINGS", 140, 44, gold);

        drawCentered(
            g,
            "Sound effects: " + (soundOn ? "ON" : "OFF"),
            250,
            23,
            Color.WHITE
        );

        drawCentered(
            g,
            "Background music: " + (musicOn ? "ON" : "OFF"),
            315,
            23,
            Color.WHITE
        );

        drawCentered(
            g,
            "Use the buttons below to toggle audio",
            390,
            16,
            Color.LIGHT_GRAY
        );

        drawCentered(
            g,
            "Press ESC to return",
            570,
            17,
            Color.LIGHT_GRAY
        );
    }

    private void drawGameOver(Graphics2D g) {

        drawOverlay(
            g,
            "GAME OVER",
            "Score: " + score +
            "  |  ENTER: play again  |  ESC: menu"
        );
    }

    @Override
    protected void paintComponent(Graphics graphics) {

        super.paintComponent(graphics);

        Graphics2D g = (Graphics2D) graphics.create();

        g.setRenderingHint(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON
        );

        switch (screen) {

            case MENU:
                drawMenu(g);
                break;

            case PLAYING:
                drawGame(g);
                break;

            case PAUSED:
                drawGame(g);
                drawOverlay(
                    g,
                    "PAUSED",
                    "Press SPACE or ENTER to resume"
                );
                break;

            case GAME_OVER:
                drawGame(g);
                drawGameOver(g);
                break;

            case SHOP:
                drawShop(g);
                break;

            case ACHIEVEMENTS:
                drawAchievements(g);
                break;

            case SETTINGS:
                drawSettings(g);
                break;
        }

        g.dispose();
    }

    // BUTTON INSTALLATION

    @Override
    public void addNotify() {

        super.addNotify();

        if (getComponentCount() == 0) {
            installButtons1();
        }
    }

    private void installButtons() {

        setLayout(null);

        addMenuButton(
            "1 PLAYER", 230, 465, 150, 42,
            () -> startGame(false)
        );

        addMenuButton(
            "2 PLAYERS", 420, 465, 150, 42,
            () -> startGame(true)
        );

        addMenuButton(
            "SHOP", 95, 520, 120, 38,
            () -> screen = Screen.SHOP
        );

        addMenuButton(
            "MAP", 235, 520, 120, 38,
            () -> map = MapType.values()[
                (map.ordinal() + 1) % MapType.values().length
            ]
        );

        addMenuButton(
            "DIFFICULTY", 375, 520, 145, 38,
            () -> difficulty = Difficulty.values()[
                (difficulty.ordinal() + 1) % Difficulty.values().length
            ]
        );

        addMenuButton(
            "ACHIEVEMENTS", 540, 520, 165, 38,
            () -> screen = Screen.ACHIEVEMENTS
        );

        addMenuButton(
            "SETTINGS", 310, 570, 150, 38,
            () -> screen = Screen.SETTINGS
        );

        addMenuButton(
            "SOUND ON/OFF", 310, 615, 150, 32,
            () -> soundOn = !soundOn
        );

        addMenuButton(
            "BACK / MENU", 315, 645, 160, 30,
            () -> screen = Screen.MENU
        );

        for (int i = 0; i < skins.length; i++) {

            final int index = i;

            addMenuButton(
                "SELECT",
                630,
                178 + i * 75,
                95,
                35,
                () -> {

                    if (save.unlocked[index]) {

                        save.selectedSkin = index;

                    } else if (save.coins >= skinPrices[index]) {

                        save.coins -= skinPrices[index];
                        save.unlocked[index] = true;
                        save.selectedSkin = index;
                    }

                    saveProgress();
                }
            );
        }

        addMenuButton(
            "TOGGLE MUSIC", 315, 390, 170, 38,
            () -> musicOn = !musicOn
        );

        addMenuButton(
            "TOGGLE SOUND", 315, 440, 170, 38,
            () -> soundOn = !soundOn
        );

        updateButtonVisibility1();
    }

    private void updateButtonVisibility() {

        for (Component c : getComponents()) {
            c.setVisible(false);
        }

        if (screen == Screen.MENU) {

            for (int i = 0; i < 9; i++) {
                getComponent(i).setVisible(true);
            }

        } else if (screen == Screen.SHOP) {

            for (int i = 9; i < 14; i++) {
                getComponent(i).setVisible(true);
            }

            getComponent(8).setVisible(true);

        } else if (screen == Screen.SETTINGS) {

            getComponent(14).setVisible(true);
            getComponent(15).setVisible(true);
            getComponent(8).setVisible(true);

        } else if (screen == Screen.ACHIEVEMENTS ||
                   screen == Screen.PAUSED ||
                   screen == Screen.GAME_OVER) {

            getComponent(8).setVisible(true);
        }

        repaint();
    }

    // MAIN METHOD

    public static void main(String[] args) 
    {

        SwingUtilities.invokeLater(() -> 
        {

            JFrame frame = new JFrame(
                "Snake 3.0 - Ultimate Arcade Edition"
            );

            snakeV3 game = new snakeV3();

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);

            frame.add(game);

            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            game.requestFocusInWindow();
        });
    }
}