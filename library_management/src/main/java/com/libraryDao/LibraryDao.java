package com.libraryDao;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.library.entity.Library;
import com.library.entity.Registration;


public class LibraryDao {
	
	private SessionFactory sessionFactory;

    public LibraryDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void save(Library library) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.saveOrUpdate(library);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void insertBook(int id, String name, LocalDate assuredDate, LocalDate returnDate,LocalDate renewalDate) {
        Library library = new Library(id, name, assuredDate, returnDate,renewalDate);
        save(library);
    }

    public Optional<Library> findById(int bookId) {
        try (Session session = sessionFactory.openSession()) {
            Library book = session.get(Library.class, bookId);
            if (book != null) {
                // Initialize lazy associations if needed
                // Hibernate.initialize(book.getSomeLazyAssociation());
                return Optional.of(book);
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
    public List<Library> findAll() {
        try (Session session = sessionFactory.openSession()) {
            Query<Library> query = session.createQuery("from Library", Library.class);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public void updateLibrary(int bookId, String bookName, LocalDate assuredDate, LocalDate returnDate) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            Library library = session.get(Library.class, bookId);
            if (library != null) {
                library.setBook_name(bookName);
                library.setAssured_date(assuredDate);
                library.setReturn_date(returnDate);
                session.update(library);
            }
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void remove(int bookId) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            Library library = session.get(Library.class, bookId);
            if (library != null) {
                session.delete(library);
            }
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isMemberRegistered(String username) {
        try (Session session = sessionFactory.openSession()) {
            Query<Registration> query = session.createQuery("SELECT r FROM Registration r WHERE r.username = :username", Registration.class);
            query.setParameter("username", username);
            List<Registration> registrations = query.list();
            return !registrations.isEmpty();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void performActionIfRegistered(String username, Runnable action) {
        if (isMemberRegistered(username)) {
            action.run();
        } else {
            System.out.println("Access denied. Only registered members can access this functionality.");
        }
    }
}
