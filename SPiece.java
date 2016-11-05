public class SPiece extends Piece {
  public SPiece() { 
    super();
    pieceType = S_PIECE;
    squares.add( new Square( S_PIECE, 4, -3 ));
    squares.add( new Square( S_PIECE, 4, -2 ));
    squares.add( new Square( S_PIECE, 5, -2 ));
    squares.add( new Square( S_PIECE, 5, -1 ));
    origin = new Square(4, -2 );
  }
  
  public void kickCW( GameWindow gameWindow ) {
    moveLeft( gameWindow );
    moveRight( gameWindow );
  }
  public void kickACW( GameWindow gameWindow ) {
    moveRight( gameWindow );
    moveLeft( gameWindow );
  }
}
