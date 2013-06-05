package lesson5;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.Random;

public class CanvasMain extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(layout());

        stage.setScene(scene);
        stage.setTitle("Canvas");
        stage.show();

        timer.start();
    }

    private TabPane layout() {
        final Canvas canvas1 = new Canvas(500, 500);
        final Canvas canvas2 = new Canvas(500, 500);

        drawSimple(canvas1.getGraphicsContext2D());
        drawAnimated(canvas2.getGraphicsContext2D());

        final TabPane tabPane = new TabPane();
        tabPane.getTabs().add(canvasTab("Simple", canvas1));
        tabPane.getTabs().add(canvasTab("Animated", canvas2));
        return tabPane;
    } 

    private Tab canvasTab(String title, Canvas canvas) {
        StackPane pane = new StackPane();
        pane.getChildren().add(canvas);

        Tab tab = new Tab(title);
        tab.setContent(pane);
        tab.setClosable(false);
        return tab;
    }

    private void drawSimple(GraphicsContext ctx) {
        ctx.clearRect(0, 0, 500, 500);
        ctx.setFill(Color.web("#00ff00"));
        ctx.fillRect(0, 0, 250, 250);
        ctx.fillRect(250, 250, 250, 250);
        ctx.setFill(Color.web("#ff0000"));
        ctx.fillRect(250, 0, 250, 250);
        ctx.fillRect(0, 250, 250, 250);
    }

    private void drawAnimated(final GraphicsContext ctx) {
        particles = new Particle[NO_OF_PARTICLES];
        timer     = new AnimationTimer() {
            @Override public void handle(final long NOW) {
                if (NOW > lastTimerCall + 33000) {
                    drawParticles(ctx);
                    lastTimerCall = NOW;
                }
            }
        };
        for (int i = 0 ; i < NO_OF_PARTICLES ; i++) {
            particles[i] = new Particle();
        }
    }

    // ==--------------------------------------------------------------------==

    private static final double WIDTH             = 500;
    private static final double HEIGHT            = 500;
    private static final int    NO_OF_PARTICLES   = 100;
    private static final double PARTICLE_SIZE     = 10;
    private static final double MIN_DISTANCE      = 60;
    private Particle[]          particles;
    private long                lastTimerCall;
    private AnimationTimer      timer;

    private void drawParticles(GraphicsContext ctx) {
        ctx.setFill(Color.rgb(0, 0, 0, 1));
        ctx.fillRect(0, 0, WIDTH, HEIGHT);

        for (int i = 0 ; i < NO_OF_PARTICLES ; i++) {
            Particle p = particles[i];

            ctx.setFill(p.color);
            ctx.fillOval(p.x - p.size * 0.5, p.y - p.size * 0.5, p.size, p.size);

            p.x += p.vX;
            p.y += p.vY;

            if (p.x + p.size > WIDTH) {
                p.x = p.size;
            } else if (p.x - p.size < 0) {
                p.x = WIDTH - p.size;
            }
            if (p.y + p.size > HEIGHT) {
                p.y = p.size;
            } else if (p.y - p.size < 0) {
                p.y = HEIGHT - p.size;
            }

            for (int j = i + 1; j < NO_OF_PARTICLES ; j++) {
                Particle p2 = particles[j];
                connect(p, p2);
            }
        }
    }

    private void connect(final Particle P1, final Particle P2) {
        double dx = P1.x - P2.x;
        double dy = P1.y - P2.y;
        double distance = Math.sqrt(dx * dx + dy * dy);
        if (distance <= MIN_DISTANCE) {
            P1.size = PARTICLE_SIZE * (1.0 - distance / MIN_DISTANCE) + 1;
            P1.color  = Color.hsb(90, (1.0 - distance / MIN_DISTANCE), 1.0);
            P2.size = PARTICLE_SIZE * (1.0 - distance / MIN_DISTANCE) + 1;
            P2.color  = Color.hsb(0, (1.0 - distance / MIN_DISTANCE), 1.0);

            // Accelerate and Decelerate
            double ax = dx / 4000;
            double ay = dy / 4000;
            P1.vX -= ax;
            P1.vY -= ay;
            P2.vX += ax;
            P2.vY += ay;
        }
    }

    private class Particle {
        private final Random RND = new Random();
        private double       x;
        private double       y;
        private double       vX;
        private double       vY;
        private int          red;
        private int          green;
        private int          blue;
        private double       opacity;
        private double       hue;
        private double       saturation;
        private Color        color;
        private double       size;
        private double       life;
        private double       remainingLife;

        public Particle() {
            // Position
            x = RND.nextDouble() * WIDTH;
            y = RND.nextDouble() * HEIGHT;

            // Velocity
            vX = -1 + RND.nextDouble() * 2;
            vY = -1 + RND.nextDouble() * 2;

            // Color
            hue        = 342;
            saturation = 0.0;
            color = Color.WHITE;

            // Radius
            size = PARTICLE_SIZE;

            // Life
            life          = 1;
            remainingLife = life;
        }
    }
}
