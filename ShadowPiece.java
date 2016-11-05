//extension of pieces to show where the piece will land once dropped
import java.awt.*;
public class ShadowPiece extends Piece {
  public Color outlineColor;
  public ShadowPiece( Piece p ) {
    super();
    for ( Square square : p.squares ) {
      this.squares.add( new ShadowSquare( square, this ) );
    }
    this.origin = new Square( p.origin.x, p.origin.y );
    this.outlineColor = Color.BLACK;
  }
  
  public void kickCW( GameWindow gameWindow ) {
  }
  public void kickACW( GameWindow gameWindow ) {
  }
  
  public void setVisible( boolean b ) {
    if ( !b ) {
      this.outlineColor = Color.GRAY;
      for ( Square square : squares ) {
        square.color = Color.WHITE;
      }
    }
  }
}