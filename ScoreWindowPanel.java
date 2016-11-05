import javax.swing.*;

//panel to display current score
public class ScoreWindowPanel extends JPanel {
  private int linesToRemove = 10;
  private int remaining = 10;
  private int total = 0;
  private JLabel linesRemaining, totalLines;
  
  public ScoreWindowPanel(){ 
    linesRemaining = new JLabel( "Lines Remaining: " + remaining );
    totalLines = new JLabel ( "Total Lines Removed: " + total );
    this.add( linesRemaining );
    this.add( totalLines );
  }
  
  public boolean setScore( int removed ){
    boolean speedUp;
    total += removed;
    if( this.remaining - removed > 0 ) {
      this.remaining -= removed;
      speedUp = false;
    } else {
      this.remaining = linesToRemove + (this.remaining - removed );
      speedUp = true;
    }
    linesRemaining.setText( "Lines Remaining: " + remaining );
    totalLines.setText( "Total Lines Removed: " + total );
    return speedUp;
  }
  
  public void resetTotal() {
    this.total = 0; 
  }
}