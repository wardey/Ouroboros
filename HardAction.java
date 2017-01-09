/*HardAction.java
 * Kalp Shah & Shichen Chang
 * January 15, 2014
 */
package snake;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * Sets gamemode to "Hard" and decreases the delay of the second thread
 */
public class HardAction extends snakeCanvas implements ActionListener 
{

    @Override
    public void actionPerformed(ActionEvent e)
    {
        delay = 30;
    }
    
}
