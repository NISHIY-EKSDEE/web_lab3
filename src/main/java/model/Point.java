package model;


import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name="points")
@SequenceGenerator(name="seq", initialValue=1, allocationSize=1)
public class Point {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    @Column(name="ID", updatable = false, nullable = false)
    private Long id;
    @Column(name="X", nullable = false)
    private Double x;
    @Column(name="Y", nullable = false)
    private Double y;
    @Column(name="R", nullable = false)
    private Double r;
    @Column(name="RESULT", nullable = false)
    private Boolean result;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "owner_id")
    private User owner;

    public Point(){}

    public Point(Double x, Double y, Double r) {
        this();
        this.x = x;
        this.y = y;
        this.r = r;
    }

    public Point(Double x, Double y, Double r, Boolean result) {
        this(x, y, r);
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
