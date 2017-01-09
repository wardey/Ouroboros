/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package snake;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *   Terminates the window
 */
class ExitAction implements ActionListener 
{
      public void actionPerformed(ActionEvent e) 
          {
              System.exit(0); //Closes the window      
          }
    
    
}
