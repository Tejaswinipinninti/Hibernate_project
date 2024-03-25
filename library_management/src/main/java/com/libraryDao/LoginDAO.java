package com.libraryDao;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.library.entity.Login;

public class LoginDAO {
	
	private SessionFactory sessionFactory;

    public LoginDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void save(Login login) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.persist(login);
        session.getTransaction().commit();
    }
    
    public Login findByUsername(String username) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        try {
            return session.createQuery("SELECT l FROM Login l WHERE l.username = :username", Login.class)
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            session.getTransaction().commit();
        }
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }


    public boolean validateLogin(String username, String password) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Login login = session.createQuery("SELECT l FROM Login l WHERE l.username = :username AND l.password = :password", Login.class)
                                .setParameter("username", username)
                                .setParameter("password", password)
                                .uniqueResult();
            session.getTransaction().commit();
            return login != null;
        } catch (NoResultException e) {
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean isMemberRegistered(String username) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Long count = session.createQuery("SELECT COUNT(r) FROM Registration r WHERE r.username = :username", Long.class)
                                .setParameter("username", username)
                                .uniqueResult();
            session.getTransaction().commit();
            return count > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}