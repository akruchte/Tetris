public class TPiece extends Piece {
  public TPiece() {
    super();
    pieceType = T_PIECE;
    squares.add( new Square( T_PIECE, 4, -2 ));
    squares.add( new Square( T_PIECE, 5, -2 ));
    squares.add( new Square( T_PIECE, 6, -2 ));
    squares.add( new Square( T_PIECE, 5, -1 ));
    origin = new Square(5, -2 );
  }
  
  public void kickACW( GameWindow gameWindow ) {
    if (!( this.squares.get(2).moveableRight( gameWindow ) )) {
      moveRight( gameWindow);
      moveRight( gameWindow );
      moveLeft( gameWindow);
      
    }
      
  }
  public void kickCW( GameWindow gameWindow ) {
    if (!( this.squares.get(2).moveableLeft( gameWindow ) ) ){
      moveLeft( gameWindow );
      moveLeft( gameWindow );
      moveRight( gameWindow );
      
    }
  }
} 
