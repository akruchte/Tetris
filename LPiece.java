public class LPiece extends Piece {
  public LPiece() { 
    super();
    pieceType = L_PIECE;
    squares.add( new Square( L_PIECE, 4, -3 ));
    squares.add( new Square( L_PIECE, 5, -3 ));
    squares.add( new Square( L_PIECE, 5, -2 ));
    squares.add( new Square( L_PIECE, 5, -1 ));
    origin = new Square(5, -2 );
  }
  
  public void kickACW( GameWindow gameWindow ) {
    moveLeft( gameWindow );
    moveRight( gameWindow );
  }
  public void kickCW( GameWindow gameWindow ) {
    moveRight( gameWindow );
    moveLeft( gameWindow );
  }
}
