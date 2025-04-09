package org.example;

import org.intellij.lang.annotations.Flow;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ButtonUI;
import javax.swing.plaf.metal.MetalButtonUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Predicate;

public class MainFrame implements ActionListener {
    JFrame frame;
    private final JButton[] playButtons = new JButton[9];
    private String playerTurn="x";
    private final JButton[] menuButtons=new JButton[4];
    private JButton backToMenu,restartGame,startX,start0;
    private final String[] mButtons={"Play with AI","Play with algorithm","2 players gamemode","Exit"};
    private Minimax joc;
    private String gameMode="";

    public MainFrame() {
        intialize();
    }
    private void intialize() {
        frame = new JFrame("Tic-tac-toe");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setSize(400, 250);
        frame.setResizable(false);
        Dimension bSize=new Dimension(160,30);
        for (int i = 0; i < 4; i++) {
            menuButtons[i] = new JButton(mButtons[i]);
            menuButtons[i].addActionListener(this);
            menuButtons[i].setPreferredSize(bSize);
            menuButtons[i].setMinimumSize(bSize);
            menuButtons[i].setMaximumSize(bSize);
            menuButtons[i].setAlignmentX(Component.CENTER_ALIGNMENT);
        }
        for (int i = 0; i < 9; i++)
        {
            playButtons[i]=new JButton();
            playButtons[i].setUI(new MetalButtonUI(){
                @Override
                protected Color getDisabledTextColor() {
                    return Color.BLACK;
                }
            });
            playButtons[i].addActionListener(this);
            playButtons[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
            playButtons[i].setContentAreaFilled(false);
            playButtons[i].setFocusPainted(false);
            playButtons[i].setForeground(Color.BLACK);
            playButtons[i].setFont(new Font("Serif", Font.BOLD,20));
        }
        backToMenu=new JButton("Back to menu");
        backToMenu.addActionListener(this);
        restartGame=new JButton("Restart");
        restartGame.addActionListener(this);
        startX=new JButton("Incepe cu X");
        startX.addActionListener(this);
        startX.setAlignmentX(Component.CENTER_ALIGNMENT);
        startX.setPreferredSize(bSize);
        startX.setMinimumSize(bSize);
        startX.setMaximumSize(bSize);

        start0=new JButton("Incepe cu 0");
        start0.addActionListener(this);
        start0.setAlignmentX(Component.CENTER_ALIGNMENT);
        start0.setPreferredSize(bSize);
        start0.setMinimumSize(bSize);
        start0.setMaximumSize(bSize);


        frame.setVisible(true);
        startWindow();


    }
    private void startWindow()
    {
        frame.getContentPane().removeAll();
        frame.setSize(400, 250);
        JPanel buttonsPanel = new JPanel();
        JLabel startText;
        startText= new JLabel("MENU");
        startText.setFont(new Font("Serif",Font.BOLD,20));
        JPanel textPanel=new JPanel(new FlowLayout(FlowLayout.CENTER));
        textPanel.add(startText);
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel,BoxLayout.PAGE_AXIS));
        for(int i=0;i<4;i++)
        {
            buttonsPanel.add(menuButtons[i]);
        }
        frame.setLayout(new BorderLayout());
        frame.add(textPanel,BorderLayout.NORTH);
        frame.add(buttonsPanel,BorderLayout.CENTER);


        frame.repaint();
        frame.revalidate();

    }
    private void chooseStartWindow(String text)
    {
        playerTurn="x";
        frame.getContentPane().removeAll();
        frame.setSize(400, 250);
        JPanel buttonsPanel = new JPanel();
        JLabel startText;
        startText= new JLabel("Cu ce vrei sa incepi?");
        startText.setFont(new Font("Serif",Font.BOLD,20));
        JPanel textPanel=new JPanel(new FlowLayout(FlowLayout.CENTER));
        textPanel.add(startText);
        textPanel.setBorder(new EmptyBorder(20,0,0,0));
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel,BoxLayout.PAGE_AXIS));
        buttonsPanel.add(startX);
        buttonsPanel.add(start0);
        buttonsPanel.setBorder(new EmptyBorder(20,0,0,0));
        frame.setLayout(new BorderLayout());
        frame.add(textPanel,BorderLayout.NORTH);
        frame.add(buttonsPanel,BorderLayout.CENTER);
        frame.repaint();
        frame.revalidate();


    }
    private void gameWindow(String text){
        joc=new Minimax();
        frame.getContentPane().removeAll();
        JLabel startText;
        startText= new JLabel(text);
        startText.setFont(new Font("Serif",Font.BOLD,20));
        JPanel textPanel=new JPanel(new FlowLayout(FlowLayout.CENTER));
        textPanel.add(startText);
        frame.add(textPanel,BorderLayout.NORTH);
        JPanel bPanel=new JPanel();
        bPanel.setLayout(new GridLayout(3,3));
        for (int i = 0; i < 9; i++)
        {
            bPanel.add(playButtons[i]);
        }
        Dimension panelSize=new Dimension(200,200);
        bPanel.setPreferredSize(panelSize);
        bPanel.setMinimumSize(panelSize);
        bPanel.setMaximumSize(panelSize);
        JPanel wrapper=new JPanel();
        wrapper.setLayout(new FlowLayout());
        wrapper.setAlignmentX(Component.CENTER_ALIGNMENT);
        wrapper.add(bPanel);
        frame.add(wrapper,BorderLayout.CENTER);
        frame.setSize(400,350);
        frame.repaint();
        frame.revalidate();

        if(text.equals(mButtons[0]))
        {

            playWithAi();
        }
        else if(text.equals(mButtons[1]))
        {
            playWithAlgorithm();
        }
        else
        {
            versusMode();
        }




    }
    private void playWithAi()
    {
        if(playerTurn.equals("0")) {
            String move;
            String aiTurn = "x";
            try {
                move = AiRequest.getInstance().getResponse("We are playing tic-tac-toe , the free postions are :" + joc.getFreePositions() + " and the table is : " +joc.getTabla() +"Choose the next position for " + aiTurn + " ,reply with only the pair i,j");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            int pozitie = (move.charAt(0) - '0' - 1) * 3 + move.charAt(2) - '0' - 1;
            joc.setBoardPosition(move.charAt(0)-'0',move.charAt(2)-'0',aiTurn.charAt(0));
            playButtons[pozitie].setText(aiTurn);
            playButtons[pozitie].setEnabled(false);


        }

    }
    private void playWithAlgorithm()
    {
        if(playerTurn.equals("x"))
        {
            joc.setTurn('0');

        }
        else {
            joc.setTurn('x');
            Pair<Integer,Integer> move=joc.bestMove();
            int pozitie=(move.t-1)*3+move.u-1;
            playButtons[pozitie].setText(joc.getTurn()+"");
            playButtons[pozitie].setEnabled(false);

        }


    }
    private void versusMode()
    {

    }



    private void miscare(int index){
        playButtons[index].setText(playerTurn);
        joc.setBoardPosition(index/3+1,index%3+1,playerTurn.charAt(0));
        for(int i=0;i<9;i++)
        {
            playButtons[i].setEnabled(false);
        }
        if(joc.getWinner()!='n') {
            displayWinner(joc.getWinner());
            return;
        }
        Timer algorithmMove=new Timer(1000,e -> {
            Pair<Integer, Integer> move = joc.bestMove();
            int pozitie = (move.t - 1) * 3 + move.u-1;
            playButtons[pozitie].setText(joc.getTurn() + "");
            if(joc.getWinner()!='n') {
                for(int i=0;i<9;i++)
                {
                    playButtons[i].setEnabled(false);
                }
                displayWinner(joc.getWinner());
            }
            else {
                for(int i=0;i<9;i++)
                {
                    if(playButtons[i].getText().isEmpty())
                        playButtons[i].setEnabled(true);
                }
            }
        });
        Timer aiMove=new Timer(1000,e -> {
            String move;
            String aiTurn;
            if(playerTurn.equals("0"))aiTurn="x";
            else aiTurn="0";
            try {
                move = AiRequest.getInstance().getResponse("We are playing tic-tac-toe , the free postions are :" + joc.getFreePositions() + " and the table is : " +joc.getTabla() +"Choose the next position for " + aiTurn + " ,reply with only the pair i,j");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            int pozitie =(move.charAt(0)-'0' - 1) * 3 + move.charAt(2)-'0'-1 ;

            joc.setBoardPosition(move.charAt(0)-'0',move.charAt(2)-'0',aiTurn.charAt(0));
            playButtons[pozitie].setText(aiTurn);
            if(joc.getWinner()!='n') {
                for(int i=0;i<9;i++)
                {
                    playButtons[i].setEnabled(false);
                }
                displayWinner(joc.getWinner());
            }
            else {
                for(int i=0;i<9;i++)
                {
                    if(playButtons[i].getText().isEmpty())
                        playButtons[i].setEnabled(true);
                }
            }

        });
        aiMove.setRepeats(false);
        algorithmMove.setRepeats(false);
        if(gameMode.equals(mButtons[1]))
            algorithmMove.start();
        else if(gameMode.equals(mButtons[0]))
            aiMove.start();
        else{

            if(playerTurn.equals("0"))playerTurn="x";
            else playerTurn="0";
            for(int i=0;i<9;i++)
            {
                if(playButtons[i].getText().isEmpty())
                    playButtons[i].setEnabled(true);
            }
        }



    }
    private void displayWinner(char c)
    {
        String message = "";
        switch (c)
        {
            case 't':
            {
                message="TIE";
                break;
            }
            case 'x':
            {
                message="X WINS";
                break;
            }
            case '0':
            {
                message="0 WINS";
                break;
            }
            default:
                break;
        }
        JLabel winner=new JLabel(message);
        JPanel endingPanel=new JPanel();
        endingPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        endingPanel.add(winner);
        endingPanel.add(restartGame);
        endingPanel.add(backToMenu);
        JPanel centered=new JPanel(new FlowLayout());
        centered.add(endingPanel);
        centered.setAlignmentX(Component.CENTER_ALIGNMENT);
        centered.setBorder(new EmptyBorder(0,0,10,0));
        frame.add(centered,BorderLayout.SOUTH);
        frame.repaint();
        frame.revalidate();

    }
    private void clearButtons()
    {

        for (int i = 0; i < 9; i++)
        {
            playButtons[i].setEnabled(true);
            playButtons[i].setText("");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==start0)
        {
            playerTurn="0";
            gameWindow(gameMode);
        }
        if(e.getSource()==startX)
        {
            playerTurn="x";
            gameWindow(gameMode);
        }
        for(int i=0;i<4;i++)
        {
            if(e.getSource()==menuButtons[i])
            {
                gameMode=mButtons[i];
                if(i==3)frame.dispose();
                else if(i==2)
                {
                    gameWindow(gameMode);
                }
                else{
                    chooseStartWindow(gameMode);
                }
            }
        }
        for(int i=0;i<9;i++)
        {
            if(e.getSource()==playButtons[i])
            {
                miscare(i);
            }
        }

        if(e.getSource()==restartGame)
        {
            clearButtons();
            if(gameMode.equals(mButtons[2])) {
                gameWindow(gameMode);
            }
            else chooseStartWindow(gameMode);
        }
        if(e.getSource()==backToMenu)
        {
            clearButtons();
            startWindow();
        }


    }
}
