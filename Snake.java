/*Snake.java
 * Kalp Shah & Shichen Chang
 * January 15, 2014
 */
package snake;
import java.awt.Color;
import javax.swing.*;

/**
 * Creates the main window and a dropdown menu at the top of the window
 */

public class Snake extends JFrame 
{
    public static void main(String[] args) 
    {
       JFrame frame = new JFrame("Snake");
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    //Terminates the program when the window is closed
       frame.setSize(500,480);  //Frame size is set to 500 pixels wide and 480 pixels high
       frame.setVisible(true);
       JPanel pane=(JPanel) frame.getContentPane();
       pane.setBackground(Color.WHITE); //Background set to white
       pane.add(new snakeCanvas()); //Adds snakeCanvas class onto window
       
       JMenuBar menubar = new JMenuBar();   //Creates empty menu bar
       frame.setJMenuBar(menubar);
       
       JMenu file = new JMenu("File");  //Creates the menu button called "File"
       menubar.add(file);
       JMenuItem newGame = new JMenuItem("New Game");  //Creates the drop down button called "New Game"
       file.add(newGame);
       JMenuItem exit = new JMenuItem("Exit");  //Creates the drop down button called "Exit"
       file.add(exit);   
          
       JMenu help = new JMenu("Help");  //Creates the menu button called "Help"
       menubar.add(help);
       JMenuItem howToPlay = new JMenuItem("How to Play");  //Creates the drop down button called "How to Play"
       help.add(howToPlay);
       JMenuItem authors = new JMenuItem("Authors");    //Creates the drop down button called "Authors"
       help.add(authors);
       
       JMenu settings = new JMenu("Settings");  //Creates the menu button called "Settings"
       menubar.add(settings);
       JMenuItem easy = new JMenuItem("Easy");  //Creates the drop down button called "Easy"
       settings.add(easy);
       JMenuItem normal = new JMenuItem("Normal");  //Creates the drop down button called "Normal"
       settings.add(normal);
       JMenuItem hard = new JMenuItem("Hard");  //Creates the drop down button called "Hard"
       settings.add(hard);
       
       easy.addActionListener(new EasyAction());    //Adds action listener to easy button 
       normal.addActionListener(new NormalAction());    //Adds action listener to normal button 
       hard.addActionListener(new HardAction());    //Adds action listener to hard button 
       newGame.addActionListener(new NewGameAction());  //Adds action listener to newGame button 
       exit.addActionListener(new ExitAction());    //Adds action listener to exit button 
       howToPlay.addActionListener(new HowToPlayAction());  //Adds action listener to howToPlay button 
       authors.addActionListener(new AuthorAction());  //Adds action listener to authors button 
       
       frame.setSize(500,481);
       frame.setResizable(false);
   }
    
}

