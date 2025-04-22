import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import java.util.HashSet;
import java.util.Set;

public class BallMoverAdvanced extends Application {

    private static int WIDTH = 600;
    private static int HEIGHT = 600;
    private static final int CELL_SIZE = 50;
    private static final int BALL_RADIUS = 20;
    private final Set<KeyCode> pressedKeys = new HashSet<>();
    
    private Group maze;
    private Circle ball;
    private int[][] mazeGrid;

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Create the maze grid
        mazeGrid = new int[][]{
                {1, 1, 1, 1, 1, 1},
                {1, 0, 1, 0, 0, 1},
                {1, 0, 1, 0, 1, 1},
                {1, 0, 0, 0, 0, 1},
                {1, 1, 1, 1, 0, 1},
                {1, 1, 1, 1, 1, 1}
        };
        
        WIDTH = mazeGrid[0].length * CELL_SIZE;
        HEIGHT = mazeGrid.length * CELL_SIZE;
        // Create the maze
        maze = new Group();
        for (int y = 0; y < mazeGrid.length; y++) {
            for (int x = 0; x < mazeGrid[y].length; x++) {
                Rectangle cell = new Rectangle(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                if (mazeGrid[y][x] == 1) {
                    cell.setFill(Color.BLACK);
                } else {
                    cell.setFill(Color.WHITE);
                }
                maze.getChildren().add(cell);
            }
        }

        // Create the ball
        ball = new Circle(BALL_RADIUS, Color.RED);
        ball.setCenterX(CELL_SIZE + BALL_RADIUS);
        ball.setCenterY(CELL_SIZE + BALL_RADIUS);

        // Create the scene
        Scene scene = new Scene(new Group(maze, ball), WIDTH, HEIGHT, Color.WHITE);

        // Log key events to track pressed keys
        scene.setOnKeyPressed(event -> pressedKeys.add(event.getCode()));
        scene.setOnKeyReleased(event -> pressedKeys.remove(event.getCode()));
        /*scene.setOnKeyPressed(event -> {
            if (event.getCode()== KeyCode.LEFT) {
                System.out.println("left key pressed!");
            }
        });*/

        // Main loop
        AnimationTimer animation = new AnimationTimer() {
            @Override
            public void handle(long now) {
              // Handle key events to move the ball
                if (pressedKeys.contains(KeyCode.LEFT)) {
                    moveBall(-5,0);
                }
                else if (pressedKeys.contains(KeyCode.RIGHT)) {
                    moveBall(5, 0);
                }
                else if (pressedKeys.contains(KeyCode.UP)) {
                    moveBall(0, -5);
                }
                else if (pressedKeys.contains(KeyCode.DOWN)) {
                    moveBall(0, 5);
                }
            }
        };
        animation.start();

        // Show the scene
        primaryStage.setTitle("Ball Maze");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void moveBall(double dx, double dy) {
        ball.setCenterX(ball.getCenterX() + dx);
        ball.setCenterY(ball.getCenterY() + dy);
        double x = ball.getCenterX();
        double y = ball.getCenterY();

        if (x - BALL_RADIUS >= 0 && x + BALL_RADIUS <= WIDTH
            && y - BALL_RADIUS >= 0 && y + BALL_RADIUS <= HEIGHT
            && !isCollision())
        {
            ball.setCenterX(x);
            ball.setCenterY(y);
        }
        else
        {
            ball.setCenterX(ball.getCenterX() - dx);
            ball.setCenterY(ball.getCenterY() - dy);
        }
    }

    private boolean isWall(double x, double y) {
        int row = (int) (y / CELL_SIZE);
        int col = (int) (x / CELL_SIZE);
        return mazeGrid[row][col] == 1;
    }

    private boolean isCollision(){
        return isWall(ball.getCenterX() + BALL_RADIUS, ball.getCenterY())
            || isWall(ball.getCenterX() - BALL_RADIUS, ball.getCenterY())
            || isWall(ball.getCenterX(), ball.getCenterY() + BALL_RADIUS)
            || isWall(ball.getCenterX(), ball.getCenterY() - BALL_RADIUS);
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
