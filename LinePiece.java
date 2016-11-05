public class LinePiece extends Piece {
  public LinePiece() { 
    super();
    pieceType = LINE_PIECE;
    squares.add( new Square( LINE_PIECE, 4, -4 ));
    squares.add( new Square( LINE_PIECE, 4, -3 ));
    squares.add( new Square( LINE_PIECE, 4, -2 ));
    squares.add( new Square( LINE_PIECE, 4, -1 ));
    origin = new Square(4, -2 );
  }
  
  public void kickACW( GameWindow gameWindow ) {
  }
  public void kickCW( GameWindow gameWindow ) {
  }
}
