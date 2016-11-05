import java.util.*;
import java.awt.*;

abstract class Piece{
  static final int NUM_PIECE_TYPES = 7;
  
  static final Color L_PIECE_COLOR = Color.ORANGE;
  static final Color J_PIECE_COLOR = Color.BLUE;
  static final Color S_PIECE_COLOR = Color.GREEN;
  static final Color Z_PIECE_COLOR = Color.RED;
  static final Color LINE_PIECE_COLOR = Color.CYAN;
  static final Color SQUARE_PIECE_COLOR = Color.YELLOW;
  static final Color T_PIECE_COLOR = Color.MAGENTA;
  
  static final int LINE_PIECE = 0;
  static final int L_PIECE = 1;
  static final int J_PIECE = 2;
  static final int S_PIECE = 3;
  static final int SQUARE_PIECE = 4;
  static final int Z_PIECE = 5;
  static final int T_PIECE = 6;
  
  protected ArrayList<Square> squares;  
  protected Square origin;
  public int pieceType;
  
  //constructor
  //initializes arraylist of squares
  public Piece () {
    squares = new ArrayList<Square>();
  }
  
  //piece selection algorithm
  public static double[] freqChosen = new double[]{1, 1, 1, 1, 1, 1, 1 };
  public static double piecesMade = NUM_PIECE_TYPES;
  public static int chooser() {
    int choice = 0;
    int min = 0;
    double distSum = 0;
    double randomVariable = Math.random() ; 
    for ( int i = 0; i < NUM_PIECE_TYPES; i++){
      choice = ( distSum < randomVariable && randomVariable < distSum + freqChosen[i] / piecesMade ? i : choice );
      distSum += freqChosen[i] / piecesMade;
      min = (( freqChosen[i] < freqChosen[ min ] ) ? i : min);
    }
    freqChosen[ choice ] -= 1;
    freqChosen[ min ] +=2;
    piecesMade++;
    return choice;
  }
  
  
  
  //this function should be used in place of a constructor to make new pieces
  //contsructs a piece of random type
  public static Piece newPiece( ) { 
    int choice = chooser();
    if ( choice == LINE_PIECE ) { 
      return new LinePiece();
    } else if ( choice ==  L_PIECE ) { 
      return new LPiece();
    } else if ( choice == J_PIECE ) {
      return new JPiece(); 
    } else if ( choice == S_PIECE ) {
      return new SPiece();
    } else if ( choice == SQUARE_PIECE ) {
      return new SquarePiece();
    } else if ( choice == Z_PIECE ) {
      return new ZPiece();
    } else { //( choice == T_PIECE ) 
      return new TPiece();
    }
  }
  
  //constructs a piece of the selected type
  //useful for testing and saving pieces
  public static Piece choicePiece ( int choice ) {
    if ( choice == LINE_PIECE ) { 
      return new LinePiece();
    } else if ( choice ==  L_PIECE ) { 
      return new LPiece();
    } else if ( choice == J_PIECE ) {
      return new JPiece(); 
    } else if ( choice == S_PIECE ) {
      return new SPiece();
    } else if ( choice == SQUARE_PIECE ) {
      return new SquarePiece();
    } else if ( choice == Z_PIECE ) {
      return new ZPiece();
    } else { //( choice == T_PIECE ) 
      return new TPiece();
    }
  }
  
  
  public void moveDown( GameWindow gameWindow ) {
    if ( moveableDown( gameWindow) ) {
      for( Square block : squares ) {
        block.moveDown( gameWindow );
      }
      origin.moveDown( gameWindow );
    }
  }
  public void quickDrop( GameWindow gameWindow) { 
    while ( moveableDown( gameWindow )) {
      moveDown( gameWindow );
    }
  }
  public void moveLeft( GameWindow gameWindow) { 
    if ( moveableLeft( gameWindow) ){
      for ( Square block : squares ) {
        block.moveLeft();
      }
      origin.moveLeft();
    }
  }
  public void moveRight( GameWindow gameWindow) { 
    if( moveableRight( gameWindow) ) {
      for ( Square block : squares ) {
        block.moveRight();
      }
      origin.moveRight();
    }
  }
  
  //recenter origin and apply rotation matrix
  public void rotateCW(GameWindow gameWindow ) {
    
    //handles wall-kicks
    kickCW( gameWindow );
    kickACW( gameWindow );
    
    
    int[] xp = new int[squares.size()];
    int[] yp = new int[squares.size()];
    for( int i = 0; i < squares.size(); i++ ) {
      squares.get(i).x -= origin.x;
      squares.get(i).y -= origin.y;
      
      xp[i] = -squares.get(i).y;
      yp[i] = squares.get(i).x;
      
      xp[i] += origin.x;
      yp[i] += origin.y;
    }
    
    if ( rotatable( gameWindow, xp, yp )){ 
      for ( int i = 0; i < squares.size(); i++) {
        squares.get(i).x = xp[i];
        squares.get(i).y = yp[i];
      }
    } else {
      for ( int i = 0; i < squares.size(); i++ ) {
        squares.get(i).x +=origin.x;
        squares.get(i).y += origin.y;
      }
    }
  }
  
  //recenter origin and apply rotation matrix
  public void rotateACW( GameWindow gameWindow) { 
    kickACW( gameWindow );
    kickCW( gameWindow );
    
    int[] xp = new int[squares.size()];
    int[] yp = new int[squares.size()];
    for( int i = 0; i < squares.size(); i++ ) {
      squares.get(i).x -= origin.x;
      squares.get(i).y -= origin.y;
      
      xp[i] = squares.get(i).y;
      yp[i] = -squares.get(i).x;
      
      xp[i] += origin.x;
      yp[i] += origin.y;
    }
    
    if ( rotatable( gameWindow, xp, yp )){ 
      for ( int i = 0; i < squares.size(); i++) {
        squares.get(i).x = xp[i];
        squares.get(i).y = yp[i];
      }
    } else {
      for ( int i = 0; i < squares.size(); i++ ) {
        squares.get(i).x +=origin.x;
        squares.get(i).y += origin.y;
      }
    }
  }
  
  //add all pieces in the shape to a stack
  public void explode( GameWindow gameWindow) { 
    for ( Square block : squares ) {
      gameWindow.addToStack( block );
    } 
  }
  
  public void draw(Graphics g) {
    for( Square block : squares ) {
      block.draw( g);
    }
  }
  
  public boolean moveableLeft( GameWindow gameWindow) {
    boolean moveable = true;
    boolean notBlocked = true;
    
    //is any block in the piece next to a block in the stack
    for ( Square pieceSquare : squares ) {
      moveable &= pieceSquare.x > 0;
      for ( Square stackSquare : gameWindow.stack ) {
        notBlocked &= !( pieceSquare.x == (stackSquare.x + 1) && pieceSquare.y == stackSquare.y );
      }
    }
    return moveable && notBlocked;
    
  }
  public boolean moveableRight(GameWindow gameWindow) { 
    boolean moveable = true;
    boolean notBlocked = true;
    
    //is any block in the piece next to a block in the stack
    for ( Square pieceSquare : squares ) {
      moveable &= pieceSquare.x < GameWindow.GRID_COLS - 1;
      for ( Square stackSquare : gameWindow.stack ) {
        notBlocked &= !( pieceSquare.x == (stackSquare.x - 1) && pieceSquare.y == stackSquare.y );
      }
    }
    return moveable && notBlocked;
  }
  public boolean moveableDown( GameWindow gameWindow) { 
    boolean notAtBottom = true;
    boolean notBlocked = true;
    
    //is any block in the piece next to a block in the stack
    for ( Square pieceSquare : squares ) {
      notAtBottom &= pieceSquare.y < (GameWindow.GRID_ROWS - 1 ); 
      for ( Square stackSquare : gameWindow.stack ) {
        notBlocked &= !( pieceSquare.y == (stackSquare.y - 1) && pieceSquare.x == stackSquare.x );
      }
    }
    return notAtBottom && notBlocked;
  }
  
  public boolean rotatable( GameWindow gameWindow, int[] xp, int[] yp ) {
    boolean rotatablex = false;
    boolean rotatabley = false;
    for( int x : xp ) {
      if ( x < 0 || x > GameWindow.GRID_COLS - 1) {
        return false;
      }
    } 
    for ( int y : yp ) {
      if ( y > GameWindow.GRID_ROWS - 1){
        return false;
      }
    } 
    for ( Square square : gameWindow.stack ) {
      for( int x : xp ) {
        rotatablex |= ( x == square.x );
      }
      for (int y : yp ) {
        rotatabley |= ( y == square.y );
      }
    }
    return !( rotatablex && rotatabley );
  } 
  
  public abstract void kickCW( GameWindow gameWindow );
  public abstract void kickACW( GameWindow gameWindow ); 
}
