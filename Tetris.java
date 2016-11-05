import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

//the game frame
public class Tetris extends JFrame implements KeyListener{
  public static final int INITIAL_DELAY = 1024;
  
  public static final int HEIGHT = Square.SQUARE_SIZE * (GameWindow.GRID_ROWS + 1 ) + 8; //8 pixels added to render graphics correctly
  public static final int WIDTH = Square.SQUARE_SIZE * GameWindow.GRID_COLS + 3; //3 pixels added to render graphics correctly
  
  public int delay;
  public GameWindow gameWindow;
  protected PieceWindow pieceWindow;
  protected ScoreWindowPanel scoreWindowPanel;
  protected Timer timer;
  protected Timer clearTimer;
  protected MenuWindowPanel menuWindowPanel;
  protected DrawComponent switchPiecePanel;
  protected JPanel leftPanel;
  
  //initialize game
  public static void main( String[] args ) {
    Tetris tetris = new Tetris();
    tetris.addKeyListener( tetris);
    tetris.getContentPane().setBackground( Color.WHITE);
    
    tetris.setVisible( true );
    tetris.setDefaultCloseOperation( EXIT_ON_CLOSE );
    tetris.setSize( WIDTH * 3, HEIGHT  );
    tetris.setResizable( false );
    //Tests.test( tetris );
  }
  
  public Tetris( ) { 
    //building window layout
    this.delay = INITIAL_DELAY;
    this.setTitle( "Tetris" );
    GridLayout layoutMain = new GridLayout( 1, 3 );
    this.getContentPane().setLayout( layoutMain );
    
    GridLayout layoutLeft = new GridLayout(3, 1);
    leftPanel = new JPanel();
    leftPanel.setLayout( layoutLeft );
    
    menuWindowPanel = new MenuWindowPanel();
    gameWindow = new GameWindow();
    pieceWindow = new PieceWindow();
    scoreWindowPanel = new ScoreWindowPanel();
    switchPiecePanel = new DrawComponent();
    
    leftPanel.setVisible( true );
    pieceWindow.setVisible( true );
    
    leftPanel.add( switchPiecePanel );
    leftPanel.add( scoreWindowPanel );
    leftPanel.add( menuWindowPanel );
    leftPanel.setBorder( BorderFactory.createLineBorder( Color.BLACK ));
    
    this.getContentPane().add( leftPanel );
    this.getContentPane().add( gameWindow );
    this.getContentPane().add( pieceWindow );
    
    
    //timers
    timer = new Timer(this.delay, new TimerListener());
    timer.start();
    
    clearTimer = new Timer( 11, new ClearTimer( this ) );
    clearTimer.start();
    
    //initialize first piece
    gameWindow.currentPiece = pieceWindow.pop( this); 
    gameWindow.shadowPiece = new ShadowPiece ( gameWindow.currentPiece );
    //adding listeners to menu
    
    menuWindowPanel.pauseButton.addActionListener( new ActionListener() {
      public void actionPerformed( ActionEvent e ) {
        pause();
      }
    });
    menuWindowPanel.resumeButton.addActionListener( new ActionListener() {
      public void actionPerformed( ActionEvent e ) {
        resume();
      }
    });
    menuWindowPanel.resetButton.addActionListener( new ResetActionListener( ) );
    
  }
  
  //reinitialize the game
  private void reset( ) { 
    scoreWindowPanel.resetTotal();
    menuWindowPanel.pauseButton.setEnabled( false ); 
    removeKeyListener( this );
    this.timer.stop();
    this.timer.removeActionListener( timer.getActionListeners()[0]);
 
    this.gameWindow.shadowPiece.setVisible( false );
    
    this.getContentPane().removeAll();
    this.getContentPane().setBackground( Color.WHITE);
    this.gameWindow.setBackground( Color.WHITE );
    
    this.setVisible( true );
    this.setDefaultCloseOperation( EXIT_ON_CLOSE );
    this.setSize( WIDTH * 3, HEIGHT  );
    this.setResizable( false );
    this.delay = INITIAL_DELAY;
    GridLayout layoutMain = new GridLayout( 1, 3 );
    this.getContentPane().setLayout( layoutMain );
    
    
    
    GridLayout layoutLeft = new GridLayout(3, 1);
    leftPanel = new JPanel();
    leftPanel.setLayout( layoutLeft );
    leftPanel.setBorder( BorderFactory.createLineBorder( Color.BLACK ));
  
    gameWindow = new GameWindow();
    pieceWindow = new PieceWindow();
    scoreWindowPanel = new ScoreWindowPanel();
    switchPiecePanel = new DrawComponent();
    
    leftPanel.add( switchPiecePanel);
    leftPanel.add( scoreWindowPanel );
    leftPanel.add( menuWindowPanel );
    
    this.getContentPane().add( leftPanel );
    this.getContentPane().add( gameWindow );
    this.getContentPane().add( pieceWindow );
    
    
    
    clearTimer.stop();
    this.clearTimer = null;
    this.resume();
    clearTimer = new Timer( 11, new ClearTimer( this ) );
    clearTimer.start();
    timer = new Timer( INITIAL_DELAY, new TimerListener() );
    timer.start();
    this.removeKeyListener( this );
    this.addKeyListener( this );

    gameWindow.currentPiece = null;
    gameWindow.currentPiece = pieceWindow.pop( this ); 
    gameWindow.shadowPiece = new ShadowPiece ( gameWindow.currentPiece );
    
  }
  
  //pause game
  public void pause() { 
    timer.stop();
    menuWindowPanel.pause();
    removeKeyListener( this );
  }
  
  //resume game
  public void resume() {
    timer.start();
    menuWindowPanel.resume();
    addKeyListener( this );
  } 
  
  //move the current piece into the correct position in the stack 
  public void explode() {
    gameWindow.currentPiece.quickDrop( gameWindow );
    gameWindow.explodePiece();
    gameWindow.currentPiece = pieceWindow.pop( this );
    gameWindow.shadowPiece = new ShadowPiece( gameWindow.currentPiece );
    gameWindow.pieceSwitched = false;
    //pause functionality is efficient and fixes rendering issues
    //functionality used here
    pause();
    resume();
  }
  public void keyTyped( KeyEvent e ) { }
  public void keyPressed( KeyEvent e ) { 
    requestFocus();
    if ( e.getKeyCode() == KeyEvent.VK_LEFT ) {
      gameWindow.currentPiece.moveLeft( gameWindow );
    } else if ( e.getKeyCode() == KeyEvent.VK_RIGHT ) {
      gameWindow.currentPiece.moveRight( gameWindow);
    } else if ( e.getKeyCode() == KeyEvent.VK_UP ) {
      gameWindow.currentPiece.rotateCW( gameWindow);
    } else if ( e.getKeyCode() == KeyEvent.VK_DOWN ) {
      gameWindow.currentPiece.moveDown( gameWindow );
    } else if ( e.getKeyCode() == KeyEvent.VK_CONTROL ) {
      gameWindow.currentPiece.rotateACW( gameWindow);
    } else if ( e.getKeyCode() == KeyEvent.VK_SPACE ) {
      explode();
    } else if ( e.getKeyCode() == KeyEvent.VK_SHIFT || e.getKeyCode() == KeyEvent.VK_C ){
      gameWindow.switchPiece( this );
      leftPanel.removeAll();
      switchPiecePanel = new DrawComponent( Piece.choicePiece(gameWindow.savedPiece) );
      leftPanel.add( switchPiecePanel );
      leftPanel.add( scoreWindowPanel );
      leftPanel.add( menuWindowPanel );
      //pause and resume are efficient and fix rendering issues
      //there functionality used here
      pause();
      resume();
      
    }
    //make shadowpieces in correct positions
    gameWindow.shadowPiece = new ShadowPiece( gameWindow.currentPiece );
    gameWindow.shadowPiece.quickDrop( gameWindow );
    repaint();
  }
  public void keyReleased( KeyEvent e ) { }
  
  //implement timer for piece movement
  public class TimerListener implements ActionListener {
    public void actionPerformed( ActionEvent e) {
      gameWindow.shadowPiece.quickDrop( gameWindow );
      if( gameWindow.currentPiece.moveableDown( gameWindow ) ){
        gameWindow.currentPiece.moveDown( gameWindow );
      } else {
        explode();
      }
      
      repaint();
    }
  }
  
  //implement listener for reset button
  private class ResetActionListener implements ActionListener {
    public void actionPerformed ( ActionEvent e ) {
      reset();
    }
  }
  
  private class ClearTimer implements ActionListener { //set short cleartimer //no common multiples with other timer
    public Tetris tetris;
    public void actionPerformed( ActionEvent e ) {
      switchPiecePanel.repaint();
      leftPanel.repaint();
      if ( gameWindow.gameOver()){
        menuWindowPanel.pauseButton.setEnabled( false ); 
        removeKeyListener( tetris );
        tetris.timer.stop();
        tetris.gameWindow.shadowPiece.setVisible( false );
        e = null;
      }  
      if( scoreWindowPanel.setScore( gameWindow.clearFilledLines()) ) {
        delay /= 2;
        if ( delay <= 32 ) {
          delay = 32;
          timer.setDelay( 32 );
        } else {
          timer.setDelay( delay );
        }
      }
    }
    public ClearTimer( Tetris tetris ) {
      this.tetris = tetris;
    }
    
      
  }
}