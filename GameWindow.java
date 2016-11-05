import javax.swing.*;
import java.awt.*;
import java.util.*;

//component to display current game
public class GameWindow extends JComponent{
  public static final int GRID_ROWS = 28;
  public static final int GRID_COLS = 10;
  
  public static final int WIDTH = Square.SQUARE_SIZE * GRID_ROWS;
  public static final int HEIGHT = Square.SQUARE_SIZE * GRID_COLS ;
  
  public Piece currentPiece;
  public ShadowPiece shadowPiece;
  public int savedPiece = 10; //bigger than any possible choice
  
  //saving a piece is initially allowed
  protected boolean pieceSwitched = false;
  protected ArrayList<Square> stack;
  
  public GameWindow() { 
    this.setSize( WIDTH, HEIGHT );
    stack = new ArrayList<Square>();
  }
  
  public void addToStack( Square square) { 
    stack.add( square );
  }
  
  //add the current piece to the stack
  public void explodePiece() {
    this.currentPiece.explode( this );
  }
  
  public void drawGrid( Graphics g){
    this.setBorder( BorderFactory.createLineBorder( Color.BLACK ));
    g.setColor( Color.GRAY );
    for( int i = 1; i < GRID_COLS; i++ ) {
      g.drawLine( i * Square.SQUARE_SIZE - 1, 0, i * Square.SQUARE_SIZE - 1, WIDTH);
    }
    for( int i = 1; i < GRID_ROWS; i++ ) {
      g.drawLine( 0, i * Square.SQUARE_SIZE - 1,  HEIGHT, i * Square.SQUARE_SIZE - 1);
    }
  }
  public void paintComponent( Graphics g) { 
    this.drawGrid( g );
    for ( Square square : stack ) {
      square.draw( g );
    }
    shadowPiece.draw( g );
    currentPiece.draw( g );
  }
  
  //remove lines that are complete from board and move other blocks down
  public int clearFilledLines() {
    ArrayList<Square> toRemove = new ArrayList<Square>();
    int numLines = 0;
    int lastLine = 0;
    
    for ( int i = 0; i <= GRID_ROWS; i++ ){
      if ( lineFilled( i )) {
        numLines++;
        lastLine = i;
        for( Square square : stack ) {
          if( square.y == i ) {
            toRemove.add( square );
          }
        }
      }
    }
    stack.removeAll ( toRemove );
    for ( Square square : stack ) {
      if ( square.y <= lastLine ){ 
        for( int i = 0; i < numLines; i++ ) {
          square.y += 1 ;
        }
      }
    }
    repaint();
    return numLines;
  }
  
  //check if a line is filled
  public boolean lineFilled( int i ) {
    boolean filled = true;
    boolean blockFilled = false;
    for (int ii = 0; ii < GRID_COLS; ii++ ) {
      for( Square square : stack ) {
        blockFilled = (square.x == ii && square.y == i );
        //there is a block in this place on the grid
        if ( blockFilled ){
          break;
        }
      }
      //every block is filled
      filled &= blockFilled;
    }
    return filled;
  }
  
  //save a piece
  public void switchPiece( Tetris tetris) {
    if ( savedPiece == 10 ) {
      savedPiece = currentPiece.pieceType;
      currentPiece = tetris.pieceWindow.pop( tetris );
      pieceSwitched = true;
    } else {
      if ( !pieceSwitched ) {
        int savedPieceTemp = currentPiece.pieceType;
        currentPiece = Piece.choicePiece( savedPiece );
        savedPiece = savedPieceTemp;
        pieceSwitched = true;
      }
    }
  }
  
  public boolean gameOver() {
    boolean over = false;
    for ( Square square : stack ) {
      over |= square.y < 0;
      if (over) {
        break;
      }
    }
    return over;
  }
}  
