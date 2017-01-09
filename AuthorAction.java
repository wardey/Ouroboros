package snake;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Generates a new window 
 */
public class AuthorAction implements ActionListener
{
    public void actionPerformed(ActionEvent e) 
    {
        snakeCanvas.isInMenu = true;
        JFrame authors = new JFrame("Authors");  //Creates a new frame called "Authors"
        authors.setSize(200,100);   //Frame size is set to 200 pixels wide and 100 pixels high
        authors.setVisible(true);
        
        JPanel makers = (JPanel) authors.getContentPane();
        makers.setBackground(Color.WHITE);  //Frame background set to white
        makers.setLayout(new GridLayout(2,1));  
        makers.add(new JLabel("*Kalp Shah"));
        makers.add(new JLabel("*Shichen Chang"));

    }
    
}
