import javax.swing.*;
import java.awt.*;
import java.util.*;

//component to display the upcoming piece
//stores a piece to be pushed into the gamewindow
public class PieceWindow extends JComponent{
  public static final int NUM_STORED = 5;
  private ArrayList<Piece> storedPieces;
  private ArrayList<DrawComponent> gridElements;
  
  public PieceWindow() {
    storedPieces = new ArrayList<Piece>();
    gridElements = new ArrayList<DrawComponent>();
    for ( int i = 0; i < NUM_STORED; i++ ) {
      storedPieces.add(Piece.newPiece());
      gridElements.add( new DrawComponent( this.storedPieces.get(i)));
      this.add( gridElements.get(i));
    }
   this.setLayout( new GridLayout( NUM_STORED, 1 ));
   this.setBorder( BorderFactory.createLineBorder( Color.BLACK ));
  }
  
  //make next piece in the window
  //returns piece
  public Piece pop( Tetris tetris) {
    Piece returnPiece = storedPieces.remove(0);
    gridElements.remove( 0);
    Piece pieceToAdd = Piece.newPiece();
    storedPieces.add( pieceToAdd );
    gridElements.add( new DrawComponent( pieceToAdd));
    for ( int i = 0; i < NUM_STORED ; i++ ) {
      this.gridElements.set(i, gridElements.get(i));
      this.gridElements.get(i).repaint();
    } 
    this.removeAll();
    for ( int i = 0; i < NUM_STORED; i++ ) {
      this.add( gridElements.get(i));
    }
    this.repaint();
    return returnPiece;
   
  }
}
  