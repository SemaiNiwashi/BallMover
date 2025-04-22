import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class BallMoverAdvanced extends Application {

    private static int WIDTH = 600;
    private static int HEIGHT = 600;
    private static final int CELL_SIZE = 50;
    private static final int BALL_RADIUS = 20;

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

        // Handle key events to move the ball
        scene.setOnKeyPressed(event -> {
            double dx = 0, dy = 0;
            if (event.getCode()== KeyCode.LEFT) {
                dx = -5;
            }
            else if (event.getCode()== KeyCode.RIGHT) {
                dx = 5;
            }
            else if (event.getCode()== KeyCode.UP) {
                dy = -5;
            }
            else if (event.getCode()== KeyCode.DOWN) {
                dy = 5;
            }
            moveBall(dx, dy);
        });

        // Update the ball position every frame
        AnimationTimer animation = new AnimationTimer() {
            @Override
            public void handle(long now) {
                // Check for collisions with walls
                if (isWall(ball.getCenterX(), ball.getCenterY())) {
                    //moveBall(-ball.getTranslateX(), -ball.getTranslateY());
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
        double x = ball.getCenterX() + dx;
        double y = ball.getCenterY() + dy;

        if (x - BALL_RADIUS >= 0 && x + BALL_RADIUS <= WIDTH
           && y - BALL_RADIUS >= 0 && y + BALL_RADIUS <= HEIGHT) {
            ball.setCenterX(x);
            ball.setCenterY(y);
        }
    }

    private boolean isWall(double x, double y) {
        int row = (int) (y / CELL_SIZE);
        int col = (int) (x / CELL_SIZE);
        return mazeGrid[row][col] == 1;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
