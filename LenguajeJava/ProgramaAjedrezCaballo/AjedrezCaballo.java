/**
 * @author luisamabel
 *
 */
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.applet.*;  
import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JOptionPane; 

public class AjedrezCaballo extends Applet{
// Thread delay measured in milliseconds.
public final static int DELAY = 500;
// Thread that runs the knight's tour.
private Thread thd;
// Initialize that applet.

@SuppressWarnings("unchecked")
public void init (){
	
	

   // Create a Label object that identifies the applet's title.
   Label lblTitle = new Label ("Posibles Movimientos Caballo", Label.CENTER);
   lblTitle.setFont (new Font ("Arial", Font.BOLD, 18));
   // Add the Label object to the applet's Panel container.
	add (lblTitle);
   // Create a ChessBoard object that represents a component capable of
   // displaying a chessboard and moving a knight to any square, leaving a
   // trail from the previous square.
   final ChessBoard cb = new ChessBoard (this);
   // Add the ChessBoard object to the applet's Panel container.
   add (cb);
   // Create a Panel object to hold a Label object, a Choice object, and a
   // Button object.
   Panel pnl = new Panel ();
   // Create a Label object that describes the Choice object. Add that
   // object to the Panel.
   //pnl.add (new Label ("Posicion incial:"));
   // Create a Choice object that represents a component capable of
   // presenting starting positions for the tour. Add that object to the
   // Panel.

  
   // Add the Panel to the applet's Panel container.
  add (pnl);
   // Create a Button object that represents a component for taking the
   // knight's tour.
   final Button btn = new Button ("Iniciar");
   // Create the Button's action listener. The listener identifies the
   // tour in terms of board positions. Moving the knight from position to
   // position constitutes the knight's tour.
   ActionListener al;
   al = new ActionListener ()
        {
            public void actionPerformed (ActionEvent e)
            {	int sx= Integer.parseInt(JOptionPane.showInputDialog("Digite el primer numero: "));
		    int sy= Integer.parseInt(JOptionPane.showInputDialog("Digite el segundo numero: ")); 
               Runnable r;
               r = new Runnable ()
               
                   {  
            	   class ktour
            		{
            		    public static final int N = 8;
            		 
            		    // Move pattern on basis of the change of
            		    // x coordinates and y coordinates respectively
            		    public final int cx[] = {1, 1, 2, 2, -1, -1, -2, -2};
            		    public final int cy[] = {2, -2, 1, -1, 2, -2, 1, -1};
            		 
            		    // function restricts the knight to remain within
            		    // the 8x8 chessboard
            		    boolean limits(int x, int y)
            		    {
            		        return ((x >= 0 && y >= 0) &&
            		                 (x < N && y < N));
            		    }
            		 
            		    /* Checks whether a square is valid and
            		    empty or not */
            		    boolean isempty(int a[], int x, int y)
            		    {
            		        return (limits(x, y)) && (a[y * N + x] < 0);
            		    }
            		 
            		    /* Returns the number of empty squares
            		    adjacent to (x, y) */
            		    int getDegree(int a[], int x, int y)
            		    {
            		        int count = 0;
            		        for (int i = 0; i < N; ++i)
            		            if (isempty(a, (x + cx[i]),
            		                           (y + cy[i])))
            		                count++;
            		 
            		        return count;
            		    }
            		 
            		    // Picks next point using Warnsdorff's heuristic.
            		    // Returns false if it is not possible to pick
            		    // next point.
            		    Cell nextMove(int a[], Cell cell)
            		    {
            		        int min_deg_idx = -1, c,
            		            min_deg = (N + 1), nx, ny;
            		 
            		        // Try all N adjacent of (*x, *y) starting
            		        // from a random adjacent. Find the adjacent
            		        // with minimum degree.
            		        int start = ThreadLocalRandom.current().nextInt(1000) % N;
            		        for (int count = 0; count < N; ++count)
            		        {
            		            int i = (start + count) % N;
            		            nx = cell.x + cx[i];
            		            ny = cell.y + cy[i];
            		            if ((isempty(a, nx, ny)) &&
            		                (c = getDegree(a, nx, ny)) < min_deg)
            		            {
            		                min_deg_idx = i;
            		                min_deg = c;
            		            }
            		        }
            		 
            		        // IF we could not find a next cell
            		        if (min_deg_idx == -1)
            		            return null;
            		 
            		        // Store coordinates of next point
            		        nx = cell.x + cx[min_deg_idx];
            		        ny = cell.y + cy[min_deg_idx];
            		 
            		        // Mark next move
            		        a[ny * N + nx] = a[(cell.y) * N +
            		                           (cell.x)] + 1;
            		 
            		        // Update next point
            		        cell.x = nx;
            		        cell.y = ny;
            		 
            		        return cell;
            		    }
            		 
            		    /* displays the chessboard with all the
            		    legal knight's moves */
            		    void print(int a[])
            		    {
            		        for (int i = 0; i < N; ++i)
            		        {
            		            for (int j = 0; j < N; ++j)
            		                System.out.printf("%d\t", a[j * N + i]);
            		            System.out.printf("\n");
            		        }
            		    }
            		 
            		    /* checks its neighbouring squares */
            		    /* If the knight ends on a square that is one
            		    knight's move from the beginning square,
            		    then tour is closed */
            		    boolean neighbour(int x, int y, int xx, int yy)
            		    {
            		        for (int i = 0; i < N; ++i)
            		            if (((x + cx[i]) == xx) &&
            		                ((y + cy[i]) == yy))
            		                return true;
            		 
            		        return false;
            		    }
            		 
            		    /* Generates the legal moves using warnsdorff's
            		    heuristics. Returns false if not possible */            		               		 
            		       
            		    int[] arreglo(int sx, int sy)
            		    {
            		    	Boolean bandera= false;
            		        // Filling up the chessboard matrix with -1's
            		        int a[] = new int[N * N];
            		        for (int i = 0; i < N * N; ++i)
            		            a[i] = -1;
            		 
            		        // initial position         
            		        //int sx = 4;
            		        //int sy = 4;
            		 
            		        // Current points are same as initial points
            		        Cell cell = new Cell(sx, sy);
            		 
            		        a[cell.y * N + cell.x] = 1; // Mark first move.
            		 
            		        // Keep picking next points using
            		        // Warnsdorff's heuristic
            		        Cell ret = null;
            		        for (int i = 0; i < N * N - 1; ++i)
            		        {
            		            ret = nextMove(a, cell);
            		            if (ret == null)
            		                bandera= false;
            		        }
            		 
            		        // Check if tour is closed (Can end
            		        // at starting point)
            		        if (!neighbour(ret.x, ret.y, sx, sy))
            		            bandera= false;
            		        bandera= true;
            		        int[] posiciones = new int[64];
            				for (int i = 0; i < a.length; i++) {
            					posiciones[i]=encontrarPos(a,i);					
            				}
            				//for (int i = 0; i < posiciones.length; i++) {
            					//System.out.println(posiciones[i]);					
            		//		}
            		        
            		        return posiciones;
            		        
            		    }
            		    
            		    
            		    public int encontrarPos(int[] a, int n) {
                            			
            				int pos=0;
            				for (int i = 0; i < a.length; i++) {			
            					if (a[i]==n+1) {
            						pos=i+1;				
            					}		
            				}				
            				return pos;	
            			}
                        
            		 
            		    public int[] arregloImpresion(int sx,int sy) {
                    
            		    	ktour clasek = new ktour();
            		                   		    	
            			    if(sx==0 && sy ==0) {
            			    	sx=0;
            			    	sy=0;            			    	
            			    	int[] pos =clasek.arreglo(sx, sy);
            			    	return pos;
            				}else {
            					sx=sx-1;
            					sy=sy-1;            	           		        
            		        int[] pos = clasek.arreglo(sx, sy);
            		        return pos;	        
            		        }
            			    
            		    }              			

            		class Cell {
            		    int x;
            		    int y;
            		 
            		    public Cell(int x, int y){
            		        this.x = x;
            		        this.y = y;
            		    }	    
            		}
            		
            		
            	}
            	

            	   
            	   
            	   
                       //int [] boardPositions1 = {};                       
                       
                       
                       public void run ()
                       {
                    	   
                          cb.reset ();
                          // thd != null is our check for stopping the
                          // applet should the user move away from applet's
                          // Webpage.
                        ktour k = new ktour();
                   		k.arregloImpresion(sx,sy);
                   		int[] vectorPos = k.arregloImpresion(sx,sy);
                   		int [] boardPositions1 = vectorPos; 
                       
                          for (int i = 0; i < boardPositions1.length &&
                               thd != null; i++) {
                        	  
                                   cb.moveKnight (boardPositions1 [i]);
                                   try {
									Thread.sleep (DELAY);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}                               
                          }
                          
                          btn.setEnabled (true);
                       }
                   };               
               btn.setEnabled (false);
               thd = new Thread (r);
               thd.start ();
            }
        };
   btn.addActionListener (al);
   // Add the Button object to the applet's Panel container.
   add (btn);
	}
// Stop the applet.
public void stop (){
   // Must stop the "knight's tour" thread if user moves away from Webpage.
   thd = null;
	}
}
final class ChessBoard extends Canvas
{
// Color of non-white squares.
private final static Color SQUARECOLOR = new Color (195, 214, 242);
// Dimension of chessboard square.
private final static int SQUAREDIM = 40;
// Dimension of chessboard -- includes black outline.
private final static int BOARDDIM = 8 * SQUAREDIM + 2;
// Left coordinate of chessboard's upper-left corner.
private int boardx;
// Top coordinate of chessboard's upper-left corner.
private int boardy;
// Board width.
private int width;
// Board height.
private int height;
// Image buffer.
private Image imBuffer;
// Graphics context associated with image buffer.
private Graphics imG;
// Knight's image.
private Image imKnight;
private Image tablaImg;
// Knight image width.
private int knightWidth;
// Knight image height.
private int knightHeight;
// Coordinates of knight's trail.
private ArrayList trail;
// Left coordinate of knight rectangle origin (upper-left corner).
private int ox;
// Top coordinate of knight rectangle origin (upper-left corner).
private int oy;
// Applet that created ChessBoard object -- we call its getImage() and
// getDocumentBase() methods; and we use it as an image observer for
// drawImage().
Applet a;
// Construct the ChessBoard.

ChessBoard (Applet a)
{
   // Determine the component's size.
   width = BOARDDIM+1;
   height = BOARDDIM+1;
   // Initialize chessboard's origin, so that board is centered.
   boardx = (width - BOARDDIM) / 2 + 1;
   boardy = (height - BOARDDIM) / 2 + 1;
   // Use a media tracker to ensure that knight's image completely loads
   // before we get its height and width.
   MediaTracker mt = new MediaTracker (this);
   // Load knight's image.
   imKnight = a.getImage (a.getDocumentBase (), "caballo.png");
  
   mt.addImage (imKnight, 0);
   try
   {
      mt.waitForID (0);
   }
   catch (InterruptedException e) {}
   // Obtain knight's width and height, which helps to center the knight
   // image within a square.
   knightWidth = imKnight.getWidth (a);
   knightHeight = imKnight.getHeight (a);
   // Initialize knight image's starting origin so that knight is centered
   // in the square located in the top row and left column.
   ox = boardx + (SQUAREDIM - knightWidth) / 2 + 1;
   oy = boardy + (SQUAREDIM - knightHeight) / 2 + 1;
   // Create a datastructure to hold knight's trail as knight moves
   // around the board.
   trail = new ArrayList ();
   // Save applet reference for later use in call to drawImage().
   this.a = a;
}
// This method is called when the ChessBoard component's peer is created.
// The code in this method cannot be called in the ChessBoard constructor
// because the createImage() method returns null at that point. It doesn't
// return a meaningful value until the ChessBoard component has been added
// to its container. The addNotify() method is not called until the first
// time ChessBoard is added to a container.
public void addNotify ()
{
   // Given this object's Canvas "layer" a chance to create a Canvas peer.
   super.addNotify ();
   // Create image buffer.
   imBuffer = createImage (width, height);
   // Retrieve graphics context associated with image buffer.
   imG = imBuffer.getGraphics ();
}
// This method is called by the applet's layout manager when positioning
// its components. If at all possible, the component is displayed at its
// preferred size.
public Dimension getPreferredSize ()
{
   return new Dimension (width, height);
}
// Move the knight to the specified board position. Throw an exception if
// the position is less than 1 or greater than 64.
public void moveKnight (int boardPosition)
{
   if (boardPosition < 1 || boardPosition > 64)
       throw new IllegalArgumentException ("invalid board position: " +
                                          boardPosition);
   Graphics g;
   int rebasedBoardPosition = boardPosition-1;
   int col = rebasedBoardPosition % 8;
   int row = rebasedBoardPosition / 8;
   ox = boardx + col * SQUAREDIM + (SQUAREDIM - knightWidth) / 2 + 1;
   oy = boardy + row * SQUAREDIM + (SQUAREDIM - knightHeight) / 2 + 1;
   trail.add (new Point (ox + knightWidth / 2, oy + knightHeight / 2));
   paintKnight (imG,ox, oy);
   repaint ();
}
// Paint the component -- first the chessboard and then the knight.
public void paint (Graphics g)
{
   // Paint the chessboard.
   paintChessBoard (imG, boardx, boardy);
   // Paint the knight.
   paintKnight (imG,ox, oy);
   // Paint the knight's trail.
   paintTrail (imG);
   // Draw contents of image buffer.
   g.drawImage (imBuffer, 0, 0, this); 

   
  
}




// Paint the chessboard -- (x, y) is the upper-left corner.
void paintChessBoard (Graphics g, int x, int y)
{
   // Paint chessboard outline.
   g.setColor (Color.black);
   g.drawRect (x, y, 8 * SQUAREDIM + 1, 8 * SQUAREDIM + 1);
   // Paint checkerboard.
   for (int row = 0; row < 8; row++)
   {
        g.setColor (((row & 1) != 0) ? SQUARECOLOR : Color.white);
        for (int col = 0; col < 8; col++)
        {
             g.fillRect (x + 1 + col * SQUAREDIM, y + 1 + row * SQUAREDIM,
                         SQUAREDIM, SQUAREDIM);
             g.setColor ((g.getColor () == SQUARECOLOR) ? Color.white :
                         SQUARECOLOR);
        }
   }
}
// Paint the knight -- (x, y) is the upper-left corner.
void paintKnight (Graphics g, int x, int y){	
	g.drawImage (imKnight, x, y, a);	
}

// Paint the knight's trail.
void paintTrail (Graphics g)
{
	
   g.setColor (Color.black);
   int len = trail.size ();
   if (len == 0)
       return;
   Point pt = (Point) trail.get (0);
   int ox = pt.x;
   int oy = pt.y;
   for (int i = 1; i < len; i++)
   {
        pt = (Point) trail.get (i);
        g.drawLine (ox, oy, pt.x, pt.y); 
        
        g.fillOval(pt.x,pt.y,5,5);
        g.drawOval(pt.x,pt.y,5,5);
        ox = pt.x;
        oy = pt.y;
   }
   


   
}
// Reset the chessboard by clearing the ArrayList.
public void reset ()
{
   trail.clear ();
}
// The AWT invokes the update() method in response to the repaint() method
// call that is made as a knight is moved. The default implementation of 
// this method, which is inherited from the Container class, clears the
// applet's drawing area to the background color prior to calling paint().
// This clearing followed by drawing causes flicker. KT overrides
// update() to prevent the background from being cleared, which eliminates
// the flicker.
public void update (Graphics g)
{
   paint (g);
}
}










