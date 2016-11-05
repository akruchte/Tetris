import java.awt.*;
import javax.swing.*;

public class DrawComponent extends JPanel {
  public Piece drawPiece;
  public DrawComponent ( Piece drawPiece ){
    this.drawPiece = drawPiece;
    this.setSize( Tetris.WIDTH / 3, Tetris.HEIGHT / 3 );
  }
  
  public void paintComponent( Graphics g ) {
    g.translate(  0, (int) (4.7 * Square.SQUARE_SIZE )); //making pieces visible and centered
    if( drawPiece != null ){
      drawPiece.draw( g);
    }
  }
  
  public DrawComponent( int choice ) {
    this.drawPiece = Piece.choicePiece( choice );
  }
  
  public DrawComponent ( ) {
    this.drawPiece = null;
  }
}