package homework;
import javax.swing.*;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.io.*;

import javax.imageio.ImageIO;


public class VisualisationPanel extends JPanel {

    private BufferedImage image;
    private int h_offset = 0;
    private int v_offset = 0;

    final static int MAX_ROWS = 5;
    final static int MAX_COLS = 5;


    /* Constructor loading the image */
    public VisualisationPanel() {
        try {
            image = ImageIO.read( new File( "src/img/boat.png" ) );
        } catch (IOException ex) {
            System.out.println( ex.toString() );
            System.exit(1);
        }
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        /* Convert to Graphics2D object for advanced drawing features */
        Graphics2D g2d = (Graphics2D) g;

        /* --Grid-- */
        g2d.setColor( Color.BLUE );
        /* Calculate horizontal offset */
        if ( h_offset >= ( this.getHeight() / MAX_ROWS ) || Math.abs( SwingNavigator.angle ) == 90 )
            h_offset = 0;
        else
            h_offset = h_offset + ( this.getHeight() / MAX_ROWS * SwingNavigator.speed / SwingNavigator.SPEED_MAX );
        /* Calculate vertical offset */
        if ( v_offset <= this.getWidth() / MAX_COLS && v_offset >= -this.getWidth() / MAX_COLS )
            v_offset = v_offset + ( this.getWidth() / MAX_COLS * SwingNavigator.speed / SwingNavigator.SPEED_MAX * -SwingNavigator.angle / SwingNavigator.ANGLE_MAX );
        else
            v_offset = 0;
        /* Draw horizontals */
        for( int i = 0; i <= MAX_ROWS; i++ ) {
            g2d.drawLine( 0, i * this.getHeight()/MAX_ROWS + h_offset, this.getWidth(), i * this.getHeight()/MAX_ROWS + h_offset );
        }
        /* Draw verticals */
        for( int i = 0; i <= MAX_COLS; i++ ) {
            g2d.drawLine( i * this.getWidth()/MAX_COLS + v_offset, 0, i * this.getWidth()/MAX_COLS + v_offset, this.getHeight() );
        }

        /* --Sprite-- */
        /* Create triangle (polygon) */
        int xPoints[] = { this.getWidth() / 2 - 20, this.getWidth() / 2, this.getWidth() / 2 + 20 };
        int yPoints[] = { this.getHeight() / 2 + 20, this.getHeight() / 2 - 20, this.getHeight() / 2 + 20 };
        int nPoints = 3;
        Polygon sprite = new Polygon( xPoints, yPoints, nPoints );
        /* Rotate polygon */
        Rectangle bounds = sprite.getBounds();
        AffineTransform at = new AffineTransform();
        at.rotate( Math.toRadians( SwingNavigator.angle ), bounds.getX() + bounds.width / 2, bounds.getY() + bounds.height / 2 );
        g2d.setColor( Color.BLACK );
        g2d.fill( at.createTransformedShape( sprite ) );

        /* Rotate image */
        AffineTransform at2 = new AffineTransform();
        at2.rotate( Math.toRadians( SwingNavigator.angle ), image.getWidth() / 2, image.getHeight() / 2);
        AffineTransformOp op = new AffineTransformOp(at2, AffineTransformOp.TYPE_BILINEAR);
        int x_center = ( this.getWidth() - image.getWidth() ) / 2;
        int y_center = ( this.getHeight() - image.getHeight() ) / 2;
        g2d.drawImage( op.filter( image, null ), x_center, y_center, null ) ;
    }

}