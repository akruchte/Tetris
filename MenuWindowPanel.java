import javax.swing.*;

public class MenuWindowPanel extends JPanel {
  public JButton resetButton, pauseButton, resumeButton;
  
  public MenuWindowPanel(){
    resetButton = new JButton( "Reset" );
    pauseButton = new JButton( "Pause" );
    resumeButton = new JButton( "Resume" );
    
    resetButton.setFocusable( false );
    pauseButton.setFocusable( false );
    resumeButton.setFocusable( false );
    
    this.add( resetButton);
    this.add( pauseButton );
    this.add( resumeButton );
    
    resumeButton.setVisible( false );
    resumeButton.setEnabled( false );
  }
  
  public void pause() { 
    pauseButton.setVisible( false );
    resumeButton.setVisible( true );
    
    pauseButton.setEnabled( false);
    resumeButton.setEnabled( true);
  }
  public void resume() { 
    resumeButton.setVisible( false );
    pauseButton.setVisible( true );
    
    pauseButton.setEnabled( true);
    resumeButton.setEnabled( false);
  }
}