package model;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "users")
@SequenceGenerator(name="seqq", initialValue=1, allocationSize=1)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqq")
    @Column(name="USER_ID", updatable = false, nullable = false)
    private Long id;
    @Column(name = "LOGIN")
    private String login;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "owner", cascade = CascadeType.ALL)
    private List<Point> points;

    public User(){    }

    public User(String login){
        this.login = login;
    }

    public void addPoint(Point p){
        points.add(0, p);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public List<Point> getPoints() {
        return points;
    }

    public void setPoints(List<Point> points) {
        this.points = points;
    }
}
