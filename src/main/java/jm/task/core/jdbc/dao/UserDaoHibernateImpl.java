package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.query.Query;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try (Session session = Util.getSessionFactory().getCurrentSession()) {
            session.beginTransaction();
            String createTableSQL1 = "CREATE TABLE IF NOT EXISTS  user("
                    + "id   serial   not null, "
                    + "name VARCHAR(255) NOT NULL, "
                    + "last_name VARCHAR(255) NOT NULL, "
                    + "age INTEGER NOT NULL " + ")";
            session.createSQLQuery(createTableSQL1).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = Util.getSessionFactory().getCurrentSession()) {
            session.beginTransaction();
            String deleteTableSQL1 = "DROP TABLE IF EXISTS user";
            session.createSQLQuery(deleteTableSQL1).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = Util.getSessionFactory().getCurrentSession()) {
            session.beginTransaction();
            Query query = session.createSQLQuery("INSERT INTO user  VALUES ( id, :name1, :lastname1 ,:age1)");
            query.setParameter("name1", name);
            query.setParameter("lastname1", lastName);
            query.setParameter("age1", age);
            query.executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getSessionFactory().getCurrentSession()) {
            session.beginTransaction();
            Query query = session.createSQLQuery("DELETE FROM user WHERE :id ");
            query.setParameter("id", id);
            query.executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = Util.getSessionFactory().getCurrentSession()) {
            session.beginTransaction();
            Query query = session.createSQLQuery("SELECT * FROM user");

            List<Object[]> res = query.getResultList();
            List<User> list= new ArrayList<>();

            Iterator it = res.iterator();
            while(it.hasNext()){
                Object[] line = (Object[]) it.next();
                User eq = new User();
                eq.setId( ((BigInteger) line[0]).longValue() );
                eq.setName((String) line[1]);
                eq.setLastName((String) line[2]);
                eq.setAge(((Integer) line[3]).byteValue());
                list.add(eq);
            }
            return list;
        }
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = Util.getSessionFactory().getCurrentSession()) {
            session.beginTransaction();
            session.createSQLQuery("TRUNCATE TABLE user").executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
