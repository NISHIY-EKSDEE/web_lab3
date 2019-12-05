package model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name="LAB3_POINTS")
public class Point {
    @Id
    @Column(name="ID", nullable = false)
    private String id;
    @Column(name="X", nullable = false)
    private Double x;
    @Column(name="Y", nullable = false)
    private Double y;
    @Column(name="R", nullable = false)
    private Double r;
    @Column(name="RESULT", nullable = false)
    private Boolean result;

    public Point(){
        id = UUID.randomUUID().toString();
    }

    public Point(Double x, Double y, Double r, Boolean result) {
        this();
        this.x = x;
        this.y = y;
        this.r = r;
        this.result = result;
    }

    @Override
    public String toString() {
        return "Point{" +
                "id='" + id + '\'' +
                ", x=" + x +
                ", y=" + y +
                ", r=" + r +
                ", result=" + result +
                '}';
    }

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
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
