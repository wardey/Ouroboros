package snake;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Creates a new window and displays instructions
 */
public class HowToPlayAction implements ActionListener
{
    public void actionPerformed(ActionEvent e) 
    {
        snakeCanvas.isInMenu = true;
        JFrame howtoplay = new JFrame("How to Play");  //Creates a new window called "How to Play"
        howtoplay.setSize(300,200); //Sets window size to 300 pixels wide and 200 pixels high
        howtoplay.setVisible(true);
        
        JPanel instructions = (JPanel) howtoplay.getContentPane();
        instructions.setBackground(Color.WHITE);    //Sets window background to white
        instructions.setLayout(new GridLayout(4,1));
        instructions.add(new JLabel("-Move with Arrow Keys,"));
        instructions.add(new JLabel("-Don't Hit The Walls"));
        instructions.add(new JLabel("-Don't Eat Yourself"));
        instructions.add(new JLabel("-\"P\" to pause"));
    }   
    
}
