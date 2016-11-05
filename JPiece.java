public class JPiece extends Piece {
  public JPiece() { 
    super();
    pieceType = J_PIECE;
    squares.add( new Square( J_PIECE, 5, -3 ));
    squares.add( new Square( J_PIECE, 4, -3 ));
    squares.add( new Square( J_PIECE, 4, -2 ));
    squares.add( new Square( J_PIECE, 4, -1 ));
    origin = new Square(5, -2 );
  }
  
  public void kickACW( GameWindow gameWindow ) {
    moveLeft( gameWindow);
    moveRight( gameWindow );
  }
  public void kickCW( GameWindow gameWindow ) {
    moveRight( gameWindow );
    moveLeft( gameWindow );
  }
}
