package com.libraryDao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.library.entity.Registration;

public class RegistrationDao {
	
	private SessionFactory sessionFactory;

    public RegistrationDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void save(Registration registration) {
        Session session = sessionFactory.getCurrentSession(); // Use getCurrentSession() instead of openSession()
        session.beginTransaction();
        session.persist(registration);
        session.getTransaction().commit();
    }
    public boolean isUsernameExists(String username) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            long count = session.createQuery("SELECT COUNT(r) FROM Registration r WHERE r.username = :username", Long.class)
                                .setParameter("username", username)
                                .getSingleResult();
            session.getTransaction().commit();
            return count > 0;
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