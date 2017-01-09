/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package snake;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * Restarts the game
 */
public class NewGameAction extends snakeCanvas implements ActionListener
{
    @Override
    public void actionPerformed(ActionEvent e) 
    {
        restart = true; 
    }
    
}
