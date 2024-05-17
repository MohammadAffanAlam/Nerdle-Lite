import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class Main extends JFrame implements KeyListener {
    int row = 0;
    int column = 0;
    NerdleEquation equation = new NerdleEquation();
    JButton[] buttons = new JButton[14];
    int gamesPlayed;
    int gamesWon;
    int currentStreak;
    int maxStreak;

    public static void main(String[] args) {
        Main Window = new Main();
        Window.requestFocus();
    }

    public Main() {
        super("Nerdle");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(2560, 1600);
        setLocationRelativeTo(null);
        setExtendedState(MAXIMIZED_BOTH);
        final Font font = new Font("Courier", 0, 52);
        final Font font2 = new Font("Courier", 0, 26);
        final Color correctColor = new Color(56, 225, 49);
        final Color differentSpotColor = new Color(236, 236, 6);
        final Color operationCell = new Color(229, 229, 229);
        addKeyListener(this);
        getContentPane().setBackground(Color.BLACK);
        setLayout(new BorderLayout(0, 2));

        // Initializing the grid where the equation is displayed
        JPanel nerdleGrid = new JPanel();
        add(nerdleGrid, "Center");
        nerdleGrid.setBackground(Color.BLACK);
        nerdleGrid.setLayout(new GridLayout(6, 8, 2, 2));
        final JPanel[][] cells = new JPanel[6][8];
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 8; j++) {
                cells[i][j] = new JPanel();
                cells[i][j].setBackground(Color.WHITE);
                if (j == 2)
                    cells[i][j].setBackground(operationCell);
                if (j == 5)
                    cells[i][j].setBackground(Color.LIGHT_GRAY);
                cells[i][j].setLayout(new GridLayout(1, 1, 5, 5));
                nerdleGrid.add(cells[i][j]);
            }
        }
        final JLabel[][] cellText = new JLabel[6][8];
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 8; j++) {
                cellText[i][j] = new JLabel();
                cellText[i][j].setHorizontalAlignment(0);
                cellText[i][j].setFont(font);
                if (j == 5)
                    cellText[i][j].setText("=");
                cells[i][j].add(cellText[i][j]);
            }
        }

        // Initializing a grid of the input buttons (numbers, operations, back, and enter)
        JPanel buttonGrid = new JPanel();
        buttonGrid.setBackground(Color.BLACK);
        buttonGrid.setLayout(new GridLayout(1, 14, 3, 3));
        add(buttonGrid, "South");
        for (int k = 0; k < 14; k++) {
            buttons[k] = new JButton();
            buttons[k].setHorizontalAlignment(0);
            buttons[k].setFont(font);
            buttons[k].setOpaque(true);
            buttons[k].setBorderPainted(false);
            buttons[k].setFocusable(false);
            buttonGrid.add(buttons[k]);
        }
        buttons[0].setText("1");
        buttons[1].setText("2");
        buttons[2].setText("3");
        buttons[3].setText("4");
        buttons[4].setText("5");
        buttons[5].setText("6");
        buttons[6].setText("7");
        buttons[7].setText("8");
        buttons[8].setText("9");
        buttons[9].setText("0");
        buttons[10].setText("+");
        buttons[11].setText("-");
        buttons[12].setText("⬅");
        buttons[13].setText("↵");
        try {
            // Loading data from the file "Save.txt" when the application is opened
            Scanner sc = new Scanner(new File("Save.txt"));
            if (sc.hasNext()) {
                gamesPlayed = sc.nextInt();
                gamesWon = sc.nextInt();
                currentStreak = sc.nextInt();
                maxStreak = sc.nextInt();
                row = sc.nextInt();
                column = sc.nextInt();
                equation.setNum(sc.next());
                equation.setNum2(sc.next());
                equation.setNum3(sc.next());
                equation.setOperation(Boolean.valueOf(sc.nextBoolean()));

                for (int i = 0; i < 6; i++) {
                    for (int j = 0; j < 8; j++) {
                        String input = sc.next();
                        if (input.matches("\\d+")) {
                            cellText[i][j].setText(input);
                        } else if (input.equals("+") || input.equals("=") || input.equals("-")) {
                            cellText[i][j].setText(input);
                        } else {
                            cellText[i][j].setText("");
                        }
                    }
                }
                for (int i = 0; i < 6; i++) {
                    for (int j = 0; j < 8; j++)
                        cells[i][j].setBackground(new Color(sc.nextInt()));
                }
                for (int i = 0; i < 6; i++) {
                    for (int j = 0; j < 8; j++)
                        cellText[i][j].setForeground(new Color(sc.nextInt()));
                }
                for (int i = 0; i < 12; i++)
                    buttons[i].setBackground(new Color(sc.nextInt()));
                for (int i = 0; i < 12; i++)
                    buttons[i].setForeground(new Color(sc.nextInt()));
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        if (column != 8) {
            cells[row][column].setBorder(BorderFactory.createLineBorder(Color.black, 2));
        }

        // Initializing the panel at the top displaying user stats
        JPanel statPanel = new JPanel();
        statPanel.setLayout(new GridLayout(1, 4, 0, 0));

        JPanel gamesPlayedPanel = new JPanel(); // JPanel that displays the amount of games played by the user
        gamesPlayedPanel.setLayout(new BorderLayout());
        gamesPlayedPanel.setBackground(Color.WHITE);

        JLabel gamesPlayedLabel = new JLabel("Games played"); // JLabel displaying the text "Games played"
        gamesPlayedLabel.setHorizontalAlignment(0);
        gamesPlayedLabel.setFont(font2);

        // JLabel displaying the amount of games played by the user
        final JLabel gamesPlayedStat = new JLabel(Integer.toString(gamesPlayed));
        gamesPlayedStat.setHorizontalAlignment(0);
        gamesPlayedStat.setFont(font);
        gamesPlayedPanel.add(gamesPlayedLabel, "North");
        gamesPlayedPanel.add(gamesPlayedStat, "Center");

        JPanel gamesWonPanel = new JPanel(); // JPanel that displays the user's win rate
        gamesWonPanel.setLayout(new BorderLayout());
        gamesWonPanel.setBackground(Color.WHITE);

        JLabel gamesWonLabel = new JLabel("Win rate"); // JLabel displaying the text "win rate"
        gamesWonLabel.setHorizontalAlignment(0);
        gamesWonLabel.setFont(font2);

        // JLabel displaying the user's win rate
        final JLabel gamesWonStat;
        if (gamesPlayed == 0) {
            gamesWonStat = new JLabel("0%");
        } else {
            gamesWonStat = new JLabel("" + Math.round(gamesWon / gamesPlayed * 100.0F) + "%");
        }
        gamesWonStat.setHorizontalAlignment(0);
        gamesWonStat.setFont(font);
        gamesWonPanel.add(gamesWonLabel, "North");
        gamesWonPanel.add(gamesWonStat, "Center");

        JPanel currentStreakPanel = new JPanel(); // JPanel that displays the user's current win streak
        currentStreakPanel.setLayout(new BorderLayout());
        currentStreakPanel.setBackground(Color.WHITE);

        JLabel currentStreakLabel = new JLabel("Current streak");
        currentStreakLabel.setHorizontalAlignment(0);
        currentStreakLabel.setFont(font2);

        // JLabel that displays the user's current win streak
        final JLabel currenStreakStat = new JLabel(Integer.toString(currentStreak));
        currenStreakStat.setHorizontalAlignment(0);
        currenStreakStat.setFont(font);
        currentStreakPanel.add(currentStreakLabel, "North");
        currentStreakPanel.add(currenStreakStat, "Center");

        JPanel maxStreakPanel = new JPanel();
        maxStreakPanel.setLayout(new BorderLayout());
        maxStreakPanel.setBackground(Color.WHITE);

        JLabel maxStreakLabel = new JLabel("Max streak");
        maxStreakLabel.setHorizontalAlignment(0);
        maxStreakLabel.setFont(font2);

        final JLabel maxStreakStat = new JLabel(Integer.toString(maxStreak));
        maxStreakStat.setHorizontalAlignment(0);
        maxStreakStat.setFont(font);
        maxStreakPanel.add(maxStreakLabel, "North");
        maxStreakPanel.add(maxStreakStat, "Center");

        statPanel.add(gamesPlayedPanel);
        statPanel.add(gamesWonPanel);
        statPanel.add(currentStreakPanel);
        statPanel.add(maxStreakPanel);
        add(statPanel, "North");

        // Button functionality
        buttons[0].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                cells[row][column].setBorder((Border)null);
                if (column != 8 && column != 2) {
                    cellText[row][column].setText("1");
                    column++;
                }
                if (column == 5)
                    column++;
                if (column != 8)
                    cells[row][column].setBorder(BorderFactory.createLineBorder(Color.black, 2));
            }
        });
        buttons[1].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                cells[row][column].setBorder((Border)null);
                if (column != 8 && column != 2) {
                    cellText[row][column].setText("2");
                    column++;
                }
                if (column == 5)
                    column++;
                if (column != 8)
                    cells[row][column].setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            }
        });
        buttons[2].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                cells[row][column].setBorder((Border)null);
                if (column != 8 && column != 2) {
                    cellText[row][column].setText("3");
                    column++;
                }
                if (column == 5)
                    column++;
                if (column != 8)
                    cells[row][column].setBorder(BorderFactory.createLineBorder(Color.black, 2));
            }
        });
        buttons[3].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                cells[row][column].setBorder((Border)null);
                if (column != 8 && column != 2) {
                    cellText[row][column].setText("4");
                    column++;
                }
                if (column == 5)
                    column++;
                if (column != 8)
                    cells[row][column].setBorder(BorderFactory.createLineBorder(Color.black, 2));
            }
        });
        buttons[4].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                cells[row][column].setBorder((Border)null);
                if (column != 8 && column != 2) {
                    cellText[row][column].setText("5");
                    column++;
                }
                if (column == 5)
                    column++;
                if (column != 8)
                    cells[row][column].setBorder(BorderFactory.createLineBorder(Color.black, 2));
            }
        });
        buttons[5].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                cells[row][column].setBorder((Border)null);
                if (column != 8 && column != 2) {
                    cellText[row][column].setText("6");
                    column++;
                }
                if (column == 5)
                    column++;
                if (column != 8)
                    cells[row][column].setBorder(BorderFactory.createLineBorder(Color.black, 2));
            }
        });
        buttons[6].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                cells[row][column].setBorder((Border)null);
                if (column != 8 && column != 2) {
                    cellText[row][column].setText("7");
                    column++;
                }
                if (column == 5)
                    column++;
                if (column != 8)
                    cells[row][column].setBorder(BorderFactory.createLineBorder(Color.black, 2));
            }
        });
        buttons[7].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                cells[row][column].setBorder((Border)null);
                if (column != 8 && column != 2) {
                    cellText[row][column].setText("8");
                    column++;
                }
                if (column == 5)
                    column++;
                if (column != 8)
                    cells[row][column].setBorder(BorderFactory.createLineBorder(Color.black, 2));
            }
        });
        buttons[8].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                cells[row][column].setBorder((Border)null);
                if (column != 8 && column != 2) {
                    cellText[row][column].setText("9");
                    column++;
                }
                if (column == 5)
                    column++;
                if (column != 8)
                    cells[row][column].setBorder(BorderFactory.createLineBorder(Color.black, 2));
            }
        });
        buttons[9].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                cells[row][column].setBorder((Border)null);
                if (column != 8 && column != 2) {
                    cellText[row][column].setText("0");
                    column++;
                }
                if (column == 5)
                    column++;
                if (column != 8)
                    cells[row][column].setBorder(BorderFactory.createLineBorder(Color.black, 2));
            }
        });
        buttons[10].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                cells[row][column].setBorder((Border)null);
                if (column == 2) {
                    cellText[row][column].setText("+");
                    column++;
                }
                if (column != 8)
                    cells[row][column].setBorder(BorderFactory.createLineBorder(Color.black, 2));
            }
        });
        buttons[11].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                cells[row][column].setBorder((Border)null);
                if (column == 2) {
                    cellText[row][column].setText("-");
                    column++;
                }
                if (column != 8)
                    cells[row][column].setBorder(BorderFactory.createLineBorder(Color.black, 2));
            }
        });
        buttons[12].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                if (column != 8)
                    cells[row][column].setBorder((Border)null);
                if (column == 6)
                    column--;
                if (column != 0) {
                    cellText[row][column - 1].setText("");
                    column--;
                }
                cells[row][column].setBorder(BorderFactory.createLineBorder(Color.black, 2));
            }
        });
        buttons[13].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                // Check for whether the equation inputted is valid
                Boolean validEquation = Boolean.valueOf(false);
                if (column == 8) {
                    int num1 = 10 * Integer.parseInt(cellText[row][0].getText()) + Integer.parseInt(cellText[row][1].getText());
                    int num2 = 10 * Integer.parseInt(cellText[row][3].getText()) + Integer.parseInt(cellText[row][4].getText());
                    int num3 = 10 * Integer.parseInt(cellText[row][6].getText()) + Integer.parseInt(cellText[row][7].getText());
                    if (cellText[row][2].getText().matches("-")) {
                        if (num1 - num2 == num3)
                            validEquation = Boolean.valueOf(true);
                    } else if (num1 + num2 == num3) {
                        validEquation = Boolean.valueOf(true);
                    }
                    if (validEquation.booleanValue()) {
                        intList emptySlots = new intList();
                        int i;
                        for (i = 0; i < 8; i++) {
                            if (i != 5)
                                if (equation.rightPosCheck(cellText[row][i].getText(), i).booleanValue()) {
                                    cells[row][i].setBackground(correctColor);
                                } else if (i != 2) {
                                    emptySlots.addNode(i);
                                }
                        }
                        if (emptySlots.isEmpty().booleanValue()) {
                            JOptionPane.showMessageDialog(null, "You win");
                            gameReset(cells, cellText, operationCell);
                            gamesWon++;
                            currentStreak++;
                            if (currentStreak > maxStreak)
                                maxStreak = currentStreak;
                            gamesPlayedStat.setText(Integer.toString(gamesPlayed));
                            gamesWonStat.setText("" + Math.round(gamesWon / gamesPlayed * 100.0F) + "%");
                            currenStreakStat.setText(Integer.toString(currentStreak));
                            maxStreakStat.setText(Integer.toString(maxStreak));
                        } else {
                            for (i = 0; i < 8; i++) {
                                if (i != 5 && i != 2 &&
                                        cells[row][i].getBackground() != correctColor) {
                                    int j = equation.diffPosCheck(cellText[row][i].getText(), emptySlots);
                                    if (j >= 0) {
                                        cells[row][i].setBackground(differentSpotColor);
                                        emptySlots.removeNode(j);
                                    } else {
                                        cells[row][i].setBackground(Color.DARK_GRAY);
                                        cellText[row][i].setForeground(Color.WHITE);
                                    }
                                }
                            }
                            if (cells[row][2].getBackground() != correctColor) {
                                cells[row][2].setBackground(Color.DARK_GRAY);
                                cellText[row][2].setForeground(Color.WHITE);
                            }
                            ButtonUpdate(buttons, cells, cellText, correctColor, differentSpotColor);
                            row++;
                            column = 0;
                            if (row == 6) {
                                JOptionPane.showMessageDialog(null, "You lose");
                                gameReset(cells, cellText, operationCell);
                                currentStreak = 0;
                                gamesPlayedStat.setText(Integer.toString(gamesPlayed));
                                gamesWonStat.setText("" + Math.round(gamesWon / gamesPlayed * 100.0F) + "%");
                                currenStreakStat.setText(Integer.toString(currentStreak));
                            }
                            cells[row][column].setBorder(BorderFactory.createLineBorder(Color.black, 2));
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "The equation is invalid");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "The equation is incomplete");
                }
            }
        });

        // Automatically saving the game state when the window is closed
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                try {
                    FileWriter SaveFile = new FileWriter("Save.txt");
                    SaveFile.write("" + gamesPlayed + "\n");
                    SaveFile.write("" + gamesWon + "\n");
                    SaveFile.write("" + currentStreak + "\n");
                    SaveFile.write("" + maxStreak + "\n");
                    SaveFile.write("" + row + "\n");
                    SaveFile.write("" + column + "\n");
                    SaveFile.write(equation.getNum() + "\n");
                    SaveFile.write(equation.getNum2() + "\n");
                    SaveFile.write(equation.getNum3() + "\n");
                    SaveFile.write("" + equation.getOperation() + "\n");
                    int i;
                    for (i = 0; i < 6; i++) {
                        for (int j = 0; j < 8; j++) {
                            if (!cellText[i][j].getText().equals("")) {
                                SaveFile.write(cellText[i][j].getText() + " ");
                            } else {
                                SaveFile.write("a ");
                            }
                        }
                        SaveFile.write("\n");
                    }
                    for (i = 0; i < 6; i++) {
                        for (int j = 0; j < 8; j++)
                            SaveFile.write("" + cells[i][j].getBackground().getRGB() + " ");
                        SaveFile.write("\n");
                    }
                    for (i = 0; i < 6; i++) {
                        for (int j = 0; j < 8; j++)
                            SaveFile.write("" + cellText[i][j].getForeground().getRGB() + " ");
                        SaveFile.write("\n");
                    }
                    for (i = 0; i < 12; i++)
                        SaveFile.write("" + buttons[i].getBackground().getRGB() + " ");
                    SaveFile.write("\n");
                    for (i = 0; i < 12; i++)
                        SaveFile.write("" + buttons[i].getForeground().getRGB() + " ");
                    SaveFile.write("\n");
                    SaveFile.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
        setVisible(true);
    }

    // Updates the color of the input buttons based on how accurate the user's guess was
    public void ButtonUpdate(JButton[] buttons, JPanel[][] cells, JLabel[][] cellText, Color correctColor, Color differentSpotColor) {
        for (int i = 0; i < 8; i++) {
            if (i != 2 && i != 5) {
                int j = Integer.parseInt(cellText[row][i].getText()) - 1;
                if (j < 0) { j = 9; }
                Boolean a = Boolean.valueOf((cells[row][i].getBackground() == correctColor));
                Boolean b = Boolean.valueOf((cells[row][i].getBackground() == differentSpotColor && buttons[j].getBackground() != correctColor));
                Boolean c = Boolean.valueOf((buttons[j].getBackground() != correctColor && buttons[j].getBackground() != differentSpotColor));
                Boolean d = Boolean.valueOf((cells[row][i].getBackground() == Color.DARK_GRAY && c.booleanValue()));
                if (a.booleanValue() || b.booleanValue() || d.booleanValue()) {
                    buttons[j].setBackground(cells[row][i].getBackground());
                    buttons[j].setForeground(cellText[row][i].getForeground());
                }
            }
        }
        if (cellText[row][2].getText().equals("-")) {
            buttons[11].setBackground(cells[row][2].getBackground());
            buttons[11].setForeground(cellText[row][2].getForeground());
        } else {
            buttons[10].setBackground(cells[row][2].getBackground());
            buttons[10].setForeground(cellText[row][2].getForeground());
        }
    }

    // Clears the Nerdle grid and creates a new equation
    public void gameReset(JPanel[][] cells, JLabel[][] cellText, Color operationCell) {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 8; j++) {
                cellText[i][j].setForeground(Color.BLACK);
                cellText[i][j].setText("");
                cells[i][j].setBackground(Color.white);
                if (j == 2) {
                    cells[i][j].setBackground(operationCell);
                }
                if (j == 5) {
                    cells[i][j].setBackground(Color.LIGHT_GRAY);
                cellText[i][j].setText("=");
                }
            }
        }
        for (int i = 0; i < 12; i++) {
            buttons[i].setForeground(Color.BLACK);
            buttons[i].setBackground(Color.WHITE);
        }
        row = 0;
        column = 0;
        gamesPlayed++;
        equation = new NerdleEquation();
    }

    // Keyboard listener
    public void keyTyped(KeyEvent e) {
        switch (e.getKeyChar()) {
            case '1':
                buttons[0].doClick();
                break;
            case '2':
                buttons[1].doClick();
                break;
            case '3':
                buttons[2].doClick();
                break;
            case '4':
                buttons[3].doClick();
                break;
            case '5':
                buttons[4].doClick();
                break;
            case '6':
                buttons[5].doClick();
                break;
            case '7':
                buttons[6].doClick();
                break;
            case '8':
                buttons[7].doClick();
                break;
            case '9':
                buttons[8].doClick();
                break;
            case '0':
                buttons[9].doClick();
                break;
            case '+':
                buttons[10].doClick();
                break;
            case '-':
                buttons[11].doClick();
                break;
            case '\b':
                buttons[12].doClick();
                break;
            case '\n':
                buttons[13].doClick();
                break;
        }
    }

    public void keyPressed(KeyEvent e) {}

    public void keyReleased(KeyEvent e) {}
}
