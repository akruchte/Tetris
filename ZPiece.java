public class ZPiece extends Piece {
  public ZPiece() { 
    super();
    pieceType = Z_PIECE;
    squares.add( new Square( Z_PIECE, 5, -3 ));
    squares.add( new Square( Z_PIECE, 5, -2 ));
    squares.add( new Square( Z_PIECE, 4, -2 ));
    squares.add( new Square( Z_PIECE, 4, -1 ));
    origin = new Square(4, -2 );
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