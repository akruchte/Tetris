import java.awt.Graphics;
import java.awt.Color;

public class Square{
  public static final int SQUARE_SIZE = 20;
   
  protected Color color;
  public int x,  y;   
  
  public Square( int x, int y ) {
    this.color = null;
    this.x = x;
    this.y = y;
  }
    
  public Square( int piece, int x, int y ) { 
    this.x = x;
    this.y = y;
    if ( piece == Piece.LINE_PIECE ){
      this.color = Piece.LINE_PIECE_COLOR;
    }else if ( piece == Piece.L_PIECE ){
      this.color = Piece.L_PIECE_COLOR;
    }else if ( piece == Piece.J_PIECE ){
      this.color = Piece.J_PIECE_COLOR;
    }else if ( piece == Piece.S_PIECE ){
      this.color = Piece.S_PIECE_COLOR;
    }else if ( piece == Piece.SQUARE_PIECE ){
      this.color = Piece.SQUARE_PIECE_COLOR;
    } else if ( piece == Piece.Z_PIECE ){
      this.color = Piece.Z_PIECE_COLOR;
    } else { //( piece == Piece.T_PIECE )
      this.color = Piece.T_PIECE_COLOR;
    }
  }
  
  public void moveDown( GameWindow gameWindow ) { 
      this.y += 1;
  }
  public void moveLeft() { 
    this.x -= 1;
  }
  public void moveRight() {
    this.x += 1;
  }
  
  //continue to move down piece until cannot move any longer
  public void quickDrop( GameWindow gameWindow) {
    while( moveableDown( gameWindow ) ) {
      this.moveDown( gameWindow );
    }
  }
      
  //checks if squares can move down further
  public boolean moveableDown( GameWindow gameWindow) { 
    boolean notAtBottom = true;
    boolean notBlocked = true;
      notAtBottom &= this.y < (GameWindow.GRID_ROWS - 1 ); 
      for ( Square stackSquare : gameWindow.stack ) {
        notBlocked &= !( this.y == (stackSquare.y - 1) && this.x == stackSquare.x );
        if ( ! notBlocked ) {
          break; 
        }
      }
    return notAtBottom && notBlocked;
  }
  public boolean moveableLeft( GameWindow gameWindow) { 
    boolean notAtBottom = true;
    boolean notBlocked = true;
      notAtBottom &= this.x > (0); 
      for ( Square stackSquare : gameWindow.stack ) {
        notBlocked &= !( this.x == (stackSquare.x - 1) && this.y == stackSquare.y );
        if ( ! notBlocked ){
          break;
        }
      }
    return notAtBottom && notBlocked;
  }
  public boolean moveableRight( GameWindow gameWindow) { 
    boolean notAtBottom = true;
    boolean notBlocked = true;
      notAtBottom &= this.x < (GameWindow.GRID_COLS - 1); 
      for ( Square stackSquare : gameWindow.stack ) {
        notBlocked &= !( this.x == (stackSquare.x + 1) && this.y == stackSquare.y );
      }
    return notAtBottom && notBlocked;
  }
  
  public void draw(Graphics g) {
    g.setColor( this.color);
    g.fillRect( this.x * SQUARE_SIZE - 1, this.y * SQUARE_SIZE - 1, SQUARE_SIZE, SQUARE_SIZE );
    
    g.setColor( Color.black);
    g.drawRect( this.x * SQUARE_SIZE - 1, this.y * SQUARE_SIZE - 1, SQUARE_SIZE, SQUARE_SIZE );
  }
}
