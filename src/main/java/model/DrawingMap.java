package model;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
public class DrawingMap {

    private static final Color BACKGROUND_COLOR = new Color(0xffebcd);
    private static final Color FALSE_COLOR = Color.red;
    private static final Color TRUE_COLOR = Color.green;
    private static final Color AREA_COLOR = new Color(0x0066ff);
    private BufferedImage image;
    private Graphics2D g2d;

    public DrawingMap(){
        image = new BufferedImage(400, 400, BufferedImage.TYPE_INT_RGB);
        g2d = image.createGraphics();
        drawBackground();
        drawAxis();
    }

    public void drawMap(List<model.Point> points, Double curRad){
        drawBackground();

        if(curRad != null)
            drawArea(curRad);

        if(points != null && points.size() > 0)
            drawAllPoints(points, curRad);

        drawAxis();
    }

    private void drawBackground(){
        g2d.setBackground(BACKGROUND_COLOR);
        g2d.clearRect(0,0,400,400);
    }

    private void drawAxis(){
        g2d.setColor(Color.black);

        //AXIS
        g2d.drawLine(200, 0, 200, 400);
        g2d.drawLine(0, 200, 400, 200);

        //ARROWS
        g2d.drawLine(200, 0, 195, 5);
        g2d.drawLine(200, 0, 205, 5);
        g2d.drawLine(400, 200, 395, 195);
        g2d.drawLine(400, 200, 395, 205);

        //MARKS
        for(int x = -5; x <= 5; x++){
            g2d.drawLine(200 + x*35, 197, 200 + x*35, 203);
            g2d.drawString(Integer.valueOf(x).toString(), 197 + x*35, 190);
        }
        for(int y = -5; y <= 5; y++){
            g2d.drawLine(197, 200 - y*35, 203, 200 - y*35);
            if(y != 0)
                g2d.drawString(Integer.valueOf(y).toString(), 205, 203 - y*35);
        }
    }

    private void drawArea(Double curRad){
        //CONVERTING RADIUS TO PIXELS EQUIVALENT
        int pxRad = (int)Math.round(curRad*35);

        //AREA WITH CURRENT RADIUS
        g2d.setColor(AREA_COLOR);
        g2d.fillRect(200, 200, pxRad/2, pxRad);
        g2d.fillArc(200 - pxRad/2, 200 - pxRad/2, pxRad, pxRad, 0, 90);
        //generating triangle
        Polygon triangle = new Polygon();
        triangle.addPoint(200, 200);
        triangle.addPoint(200, 200 + pxRad/2);
        triangle.addPoint(200 - pxRad/2, 200);
        g2d.fillPolygon(triangle);
    }

    public void drawNewPoint(Point p){
        int x = 200 + (int)Math.round(p.getX()*35);
        int y = 200 - (int)Math.round(p.getY()*35);
        if(p.getResult()){
            g2d.setColor(TRUE_COLOR);
        }
        else{
            g2d.setColor(FALSE_COLOR);
        }
        g2d.fillOval(x - 3, y - 3, 6, 6);
    }

    public void drawAllPoints(List<model.Point> points){
        //POINTS
        int pixX;
        int pixY;
        for(Point p : points){
            pixX = 200 + (int)Math.round(p.getX()*35);
            pixY = 200 - (int)Math.round(p.getY()*35);
            g2d.setColor(FALSE_COLOR);
            g2d.fillOval(pixX - 3, pixY - 3, 6, 6);
        }
    }

    public void drawAllPoints(List<model.Point> points, Double r){
        //POINTS
        double x;
        double y;
        int pixX;
        int pixY;
        for(Point p : points){
            x = p.getX();
            y = p.getY();
            pixX = 200 + (int)Math.round(p.getX()*35);
            pixY = 200 - (int)Math.round(p.getY()*35);
            boolean result = (x >= 0 && x <= r/2 && y <= 0 && y >= -r)
                    ||(x <= 0 && y <= 0 && y >= -x - r/2)
                    ||(x >= 0 && y >= 0 && Math.pow(x, 2) + Math.pow(y, 2) <= Math.pow(r/2, 2) );
            if(result){
                g2d.setColor(TRUE_COLOR);
            }
            else{
                g2d.setColor(FALSE_COLOR);
            }
            g2d.fillOval(pixX - 3, pixY - 3, 6, 6);
        }
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage img) {
        this.image = img;
    }

}
