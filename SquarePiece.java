public class SquarePiece extends Piece {
  public SquarePiece() { 
    super();
    pieceType = SQUARE_PIECE;
    squares.add( new Square( SQUARE_PIECE, 4, -2 ));
    squares.add( new Square( SQUARE_PIECE, 5, -2 ));
    squares.add( new Square( SQUARE_PIECE, 4, -1 ));
    squares.add( new Square( SQUARE_PIECE, 5, -1 ));
    origin = new Square(5, -1 );
  }
  
  public boolean rotatable ( GameWindow gameWindow, int[] xp, int[] yp) {
    return false; 
  }
  
  public void kickCW( GameWindow gameWindow ) {
  }
  public void kickACW( GameWindow gameWindow ) {
  }
}
