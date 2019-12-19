package classes;

import model.Point;
import model.User;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;
import java.util.Locale;

public class DatabaseManagerBean {
    private EntityManager entityManager;
    private final AreaChecker areaChecker;

    public DatabaseManagerBean() {
        Locale.setDefault(Locale.ENGLISH);
        entityManager = Persistence.createEntityManagerFactory( "hibernate" ).createEntityManager();
        areaChecker = new AreaChecker();
    }

    public List getAllPoints(User user){
        Query query = entityManager
                .createQuery("select p from Point p where p.owner=:user", Point.class)
                .setParameter("user", user);
        return query.getResultList();
    }

    public void addNewPointToDB(Point point){
        entityManager.getTransaction().begin();
        entityManager.persist(point);
        entityManager.flush();
        entityManager.getTransaction().commit();
    }

    public User getUserByLogin(String login){
            Query query = entityManager
                    .createQuery("select u from User u where u.login=:login")
                    .setParameter("login", login);
            try {
                return (User)query.getSingleResult();
            } catch (NoResultException e) {
                return null;
            }
    }

    public void addNewUserToDB(User user){
        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.flush();
        entityManager.getTransaction().commit();
    }

    public List<Point> changeRadiusInDB(Double R, User user){
        entityManager.getTransaction().begin();
        List<Point> points = getAllPoints(user);
        for(Point p : points){
            p.setR(R);
            boolean result = areaChecker.check(p);
            p.setResult(result);
        }
        entityManager.flush();
        entityManager.getTransaction().commit();
        return points;
    }
}
