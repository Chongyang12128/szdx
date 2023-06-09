package view;

import  view.ChessGameFrame;
import controller.GameController;
import model.*;
import javax.swing.*;
import java.awt.*;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static model.Constant.CHESSBOARD_COL_SIZE;
import static model.Constant.CHESSBOARD_ROW_SIZE;

/**
 * This class represents the checkerboard component object on the panel
 */
public  class ChessboardComponent extends JComponent {
    private final CellComponent[][] gridComponents = new CellComponent[CHESSBOARD_ROW_SIZE.getNum()][CHESSBOARD_COL_SIZE.getNum()];
    private final int CHESS_SIZE;
    private final Set<ChessboardPoint> riverCell = new HashSet<>();
    private final Set<ChessboardPoint> landCell = new HashSet<>();
    private final Set<ChessboardPoint> trapCell = new HashSet<>();
    private final Set<ChessboardPoint> densCell = new HashSet<>();
    private String string=null;
    private GameController gameController;
    private int n =0;
    private ArrayList<CellComponent> area = new ArrayList<CellComponent>(10);
    private ArrayList<CellComponent> killArea =new ArrayList<>(10);
    private ChessGameFrame chessGameFrame;

    public ChessboardComponent(int chessSize) {
        CHESS_SIZE = chessSize;
        int width = CHESS_SIZE * 7;
        int height = CHESS_SIZE * 9;
        enableEvents(AWTEvent.MOUSE_EVENT_MASK);// Allow mouse events to occur
        setLayout(null); // Use absolute layout.
        setSize(width, height);
        System.out.printf("chessboard width, height = [%d : %d], chess size = %d\n", width, height, CHESS_SIZE);
        clearAllComponent();
        initiateGridComponents();
    }


    /**
     * This method represents how to initiate ChessComponent
     * according to Chessboard information
     */
    public void initiateChessComponent(Chessboard chessboard) {
        Cell[][] grid = chessboard.getGrid();
        for (int i = 0; i < CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < CHESSBOARD_COL_SIZE.getNum(); j++) {
                gridComponents[i][j].removeAll();
            }
        }
        for (int i = 0; i < CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < CHESSBOARD_COL_SIZE.getNum(); j++) {


                if (grid[i][j].getPiece() != null) {
                    ChessPiece chessPiece = grid[i][j].getPiece();
                    System.out.println(chessPiece.getOwner());
                    if (chessPiece.getName().equals("Elephant")) {
                        gridComponents[i][j].add(
                                new ElephantChessComponent(
                                        chessPiece.getOwner(),
                                        CHESS_SIZE));
                    } else if (chessPiece.getName().equals("Lion")) {
                        gridComponents[i][j].add(
                                new LionChessComponent(
                                        chessPiece.getOwner(),
                                        CHESS_SIZE));
                    } else if (chessPiece.getName().equals("Tiger")) {
                        gridComponents[i][j].add(
                                new TigerChessComponent(
                                        chessPiece.getOwner(),
                                        CHESS_SIZE));
                    } else if (chessPiece.getName().equals("Leopard")) {
                        gridComponents[i][j].add(
                                new LeopardChessComponent(
                                        chessPiece.getOwner(),
                                        CHESS_SIZE));
                    } else if (chessPiece.getName().equals("Wolf")) {
                        gridComponents[i][j].add(
                                new WolfChessComponent(
                                        chessPiece.getOwner(),
                                        CHESS_SIZE));
                    } else if (chessPiece.getName().equals("Dog")) {
                        gridComponents[i][j].add(
                                new DogChessComponent(
                                        chessPiece.getOwner(),
                                        CHESS_SIZE));
                    } else if (chessPiece.getName().equals("Cat")) {
                        gridComponents[i][j].add(
                                new CatChessComponent(
                                        chessPiece.getOwner(),
                                        CHESS_SIZE));
                    } else if (chessPiece.getName().equals("Rat")) {
                        gridComponents[i][j].add(
                                new RatChessComponent(
                                        chessPiece.getOwner(),
                                        CHESS_SIZE));
                    }
                }
            }

        }

    }


    public void clearAllComponent() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 7; j++) {
                if (gridComponents[i][j] != null) {
                    this.remove(gridComponents[i][j]);
                    gridComponents[i][j] = null;
                }
            }
        }
    }

    public void initiateGridComponents() {
        riverCell.clear();
        trapCell.clear();
        densCell.clear();
        riverCell.add(new ChessboardPoint(3, 1));
        riverCell.add(new ChessboardPoint(3, 2));
        riverCell.add(new ChessboardPoint(4, 1));
        riverCell.add(new ChessboardPoint(4, 2));
        riverCell.add(new ChessboardPoint(5, 1));
        riverCell.add(new ChessboardPoint(5, 2));

        riverCell.add(new ChessboardPoint(3, 4));
        riverCell.add(new ChessboardPoint(3, 5));
        riverCell.add(new ChessboardPoint(4, 4));
        riverCell.add(new ChessboardPoint(4, 5));
        riverCell.add(new ChessboardPoint(5, 4));
        riverCell.add(new ChessboardPoint(5, 5));

        trapCell.add(new ChessboardPoint(0, 2));
        trapCell.add(new ChessboardPoint(0, 4));
        trapCell.add(new ChessboardPoint(1, 3));
        trapCell.add(new ChessboardPoint(7, 3));
        trapCell.add(new ChessboardPoint(8, 2));
        trapCell.add(new ChessboardPoint(8, 4));

        densCell.add(new ChessboardPoint(0, 3));
        densCell.add(new ChessboardPoint(8, 3));

        for (int i = 0; i < CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < CHESSBOARD_COL_SIZE.getNum(); j++) {
                if (gridComponents[i][j] != null) {
                    this.remove(gridComponents[i][j]);
                    gridComponents[i][j] = null;
                }
            }
        }
            for (int i = 0; i < CHESSBOARD_ROW_SIZE.getNum(); i++) {
                for (int j = 0; j < CHESSBOARD_COL_SIZE.getNum(); j++) {
                    ChessboardPoint temp = new ChessboardPoint(i, j);
                    CellComponent cell;
                    if (riverCell.contains(temp)) {
                        cell = new CellComponent(CellType.water, calculatePoint(i, j),CHESS_SIZE);
                          this.add(cell);
                    } else if (densCell.contains(temp)) {
                        cell = new CellComponent(CellType.dens, calculatePoint(i, j), CHESS_SIZE);
                        this.add(cell);
                    } else if (trapCell.contains(temp)) {
                        cell = new CellComponent(CellType.traps, calculatePoint(i, j), CHESS_SIZE);
                        this.add(cell);
                    }else {
                        cell = new CellComponent(CellType.land, calculatePoint(i,j),CHESS_SIZE);
                        this.add(cell);
                    }

                    gridComponents[i][j]=cell;

                }
            }

    }
          public void HideArea(ChessboardPoint chessboardPoint){
              this.remove( gridComponents[chessboardPoint.getRow()][chessboardPoint.getCol()]);
              this.add(area.get(n));
              gridComponents[chessboardPoint.getRow()][chessboardPoint.getCol()]=area.get(n);
              n=n+1;
              if (n==area.size()){
                  area=new ArrayList<>(10);
                  n=0;
              }
         }
          public void showArea(ChessboardPoint chessboardPoint){
                area.add(gridComponents[chessboardPoint.getRow()][chessboardPoint.getCol()]);
                this.remove( gridComponents[chessboardPoint.getRow()][chessboardPoint.getCol()]);
                ShowArea showArea=new ShowArea(gameController.getModel().getGridAt(chessboardPoint).getType(),calculatePoint(chessboardPoint.getRow(),chessboardPoint.getCol()),CHESS_SIZE,gameController);
                this.add(showArea);
                gridComponents[chessboardPoint.getRow()][chessboardPoint.getCol()]=showArea;
    }

    public void registerController(GameController gameController) {
        this.gameController = gameController;
    }
    public GameController gameController(){
        return gameController;
    }

    public void setChessComponentAtGrid(ChessboardPoint point, ChessComponent chess) {
        getGridComponentAt(point).add(chess);
    }

    public ChessComponent getChessComponentAtGrid(ChessboardPoint point){
        ChessComponent chess = (ChessComponent) getGridComponentAt(point).getComponents()[0];
        return chess;
    }
    public ChessComponent removeChessComponentAtGrid(ChessboardPoint point) {
        // Note re-validation is required after remove / removeAll.
        ChessComponent chess = (ChessComponent) getGridComponentAt(point).getComponents()[0];
        getGridComponentAt(point).removeAll();
        getGridComponentAt(point).revalidate();
        chess.setSelected(false);
        return chess;
    }

    public CellComponent getGridComponentAt(ChessboardPoint point) {
        return gridComponents[point.getRow()][point.getCol()];
    }

    private ChessboardPoint getChessboardPoint(Point point) {
        System.out.println("[" + point.y / CHESS_SIZE + ", " + point.x / CHESS_SIZE + "] Clicked");
        return new ChessboardPoint(point.y / CHESS_SIZE, point.x / CHESS_SIZE);
    }

    private Point calculatePoint(int row, int col) {
        return new Point(col * CHESS_SIZE, row * CHESS_SIZE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

   // @Override
    /*protected void processMouseEvent(MouseEvent e) {

            super.processMouseEvent(e);
            if (e.getID()==MouseEvent.MOUSE_ENTERED){

            }
            if (e.getID() == MouseEvent.MOUSE_PRESSED) {

            JComponent clickedComponent = (JComponent) getComponentAt(e.getX(), e.getY());
            if (clickedComponent.getComponentCount() == 0) {

                gameController.onPlayerClickCell(getChessboardPoint(e.getPoint()), (CellComponent) clickedComponent);

            } else {

                gameController.onPlayerClickChessPiece(getChessboardPoint(e.getPoint()), (ChessComponent) clickedComponent.getComponents()[0]);

             }
        }
    }*/



}