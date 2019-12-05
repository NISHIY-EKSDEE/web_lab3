package model;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

@ManagedBean
@SessionScoped
public class CheckAndHistoryBean {

    private Double x;
    private Double y;
    private Double r;
    private Double hiddenX;
    private Double hiddenY;
    private Boolean result;
    private List<model.Point> history;
    private DrawingMap drawingMap;
    private boolean isSecondImageRequest = false;
    private EntityManager em;
    private boolean RSelected;

    public int getHistorySize() {
        return history.size();
    }

    public CheckAndHistoryBean() {
        RSelected = false;
        Locale.setDefault(Locale.ENGLISH);
        em  = Persistence.createEntityManagerFactory( "hibernate" ).createEntityManager();
        Query query = em.createQuery("select p from Point p");

        history = new ArrayList<Point>();
        history = query.getResultList();
        history.sort(Comparator.comparing(Point::getId));
        history.forEach(p -> System.out.println(p.toString()));

        drawingMap = new DrawingMap();
        drawingMap.drawAllPoints(history);
    }

    public void submitPoint(){
        checkPoint(x, y, r);
    }

    public void submitHiddenPoint(){
        checkPoint(hiddenX, hiddenY, r);
    }

    private void checkPoint(Double x, Double y, Double r){
        if(r != null) {
            result = (x >= 0 && x <= r/2 && y <= 0 && y >= -r)
                    ||(x <= 0 && y <= 0 && y >= -x - r/2)
                    ||(x >= 0 && y >= 0 && Math.pow(x, 2) + Math.pow(y, 2) <= Math.pow(r/2, 2) );
            Point point = new Point(x, y, r, result);
            addNewPoint(point);
            addNewPointToDB(point);
            drawingMap.drawNewPoint(point);
        }else{
            System.err.println("R is NULL");
        }
    }

    private void addNewPoint(Point point){
        history.add(0, point);
    }

    private void addNewPointToDB(Point point){
        em.getTransaction().begin();
        em.persist(point);
        em.flush();
        em.getTransaction().commit();
        System.out.println("point added to db");
    }


    //USELESS
    public void radiusChanged(ActionEvent e){
        try {
            r = Double.parseDouble(
                    (String)e.getComponent().getAttributes().get("value"));
        } catch (NullPointerException | NumberFormatException ex) {
            r = 0d;
        }
    }


        //-------------------------------------------------------
       //-------------------------------------------------------
      //-----------------GETTERS AND SETTERS-------------------
     //-------------------------------------------------------
    //-------------------------------------------------------


    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public Double getR() {
        return r;
    }

    public void setR(Double r) {
        this.r = r;
        drawingMap.drawMap(history, r);
        RSelected = true;
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public List<Point> getHistory() {
        return history;
    }

    public void setHistory(List<Point> history) {
        this.history = history;
    }

    public StreamedContent getImage() throws IOException {
            if(isSecondImageRequest) {
                isSecondImageRequest = false;
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                BufferedImage img = drawingMap.getImage();
                ImageIO.write(img, "png", os);
                InputStream is = new ByteArrayInputStream(os.toByteArray());
                return new DefaultStreamedContent(is, "image/png");
            }
            else{
                isSecondImageRequest = true;
                return new DefaultStreamedContent();
            }

    }

    public Double getHiddenX() {
        return hiddenX;
    }

    public void setHiddenX(Double hiddenX) {
        this.hiddenX = hiddenX;
    }

    public Double getHiddenY() {
        return hiddenY;
    }

    public void setHiddenY(Double hiddenY) {
        this.hiddenY = hiddenY;
    }

    public boolean isRSelected() {
        return RSelected;
    }

    public void setRSelected(boolean RSelected) {
        this.RSelected = RSelected;
    }
}
