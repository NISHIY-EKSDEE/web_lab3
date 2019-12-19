package beans;

import classes.AreaChecker;
import classes.DatabaseManagerBean;
import classes.DrawingMap;
import model.Point;
import model.User;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CheckAndHistoryBean {

    private Double x;
    private Double y;
    private Double r;
    private Double hiddenX;
    private Double hiddenY;
    private Boolean result;
    private User user;
    private DatabaseManagerBean dbManager;
    private List<model.Point> history;
    private DrawingMap drawingMap;
    private LoginBean loginBean;
    private boolean isSecondImageRequest = false;
    private boolean RSelected;
    private AreaChecker areaChecker;

    public int getHistorySize() {
        return history.size();
    }

    public CheckAndHistoryBean() {

    }

    @PostConstruct
    public void checkLogin(){

            if(loginBean.getLogin() == null || loginBean.equals("")){
                try {
                    FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else{
                System.out.println("|||||||||||||||");
                System.out.println("|||||||||||||||");
                System.out.println(loginBean.getLogin());
                System.out.println("|||||||||||||||");
                System.out.println("|||||||||||||||");
                RSelected = false;
                areaChecker = new AreaChecker();
                if(dbManager.getUserByLogin(loginBean.getLogin()) == null) {
                    user = new User(loginBean.getLogin());
                    dbManager.addNewUserToDB(user);
                    history = new ArrayList<Point>();
                }else{
                    user = dbManager.getUserByLogin(loginBean.getLogin());
                    history = new ArrayList<>(dbManager.getAllPoints(user));
                    history.sort(Comparator.comparing(Point::getId).reversed());
                }
                drawingMap = new DrawingMap();
                drawingMap.drawAllPoints(history);
            }

    }

    public void submitPoint(){
        checkPoint(x, y, r);
    }

    public void submitHiddenPoint(){
        checkPoint(hiddenX, hiddenY, r);
    }

    private void checkPoint(Double x, Double y, Double r){
        x = (double) Math.round(x*10000)/10000;
        y = (double) Math.round(y*10000)/10000;
        System.out.printf("X: %f, Y: %f", x, y);
        if(r != null) {
            Point point = new Point(x, y, r);
            boolean result = areaChecker.check(point);
            point.setResult(result);
            point.setOwner(user);

            addNewPoint(point);
            dbManager.addNewPointToDB(point);
            drawingMap.drawNewPoint(point);
        }else{
            System.err.println("R is NULL");
        }
    }

    private void addNewPoint(Point point){
        history.add(0, point);
    }

    public void radiusChanged(Double r){
        RSelected = true;
        drawingMap.drawMap(history, r);
        history = dbManager.changeRadiusInDB(r, user);
    }


        //-------------------------------------------------------//
       //-------------------------------------------------------//
      //-----------------GETTERS AND SETTERS-------------------//
     //-------------------------------------------------------//
    //-------------------------------------------------------//


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
        radiusChanged(r);
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public List<Point> getHistory() {
        if(history != null)
            history.sort(Comparator.comparingLong(Point::getId).reversed());
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

    public LoginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    public DatabaseManagerBean getDbManager() {
        return dbManager;
    }

    public void setDbManager(DatabaseManagerBean dbManager) {
        this.dbManager = dbManager;
    }
}
