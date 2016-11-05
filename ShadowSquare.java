import java.awt.*;

//squares that belong in shadow pieces
//was easier to make seperate class because of way square was designed
public class ShadowSquare extends Square {
  protected Color outlineColor;
  private ShadowPiece embeddedPiece;
  public ShadowSquare( Square square, ShadowPiece embeddedPiece ) {
    super( square.x, square.y );
    this.color = Color.GRAY;
    this.embeddedPiece = embeddedPiece;
  }
  
  public void draw(Graphics g) {
    g.setColor( this.color);
    g.fillRect( this.x * SQUARE_SIZE - 1, this.y * SQUARE_SIZE - 1, SQUARE_SIZE, SQUARE_SIZE );
    
    g.setColor( this.embeddedPiece.outlineColor );
    g.drawRect( this.x * SQUARE_SIZE - 1, this.y * SQUARE_SIZE - 1, SQUARE_SIZE, SQUARE_SIZE );
  }
}