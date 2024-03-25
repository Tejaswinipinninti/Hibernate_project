 package com.librarymain;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;


import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.library.entity.Library;
import com.library.entity.Login;
import com.library.entity.Registration;
import com.libraryDao.LibraryDao;
import com.libraryDao.LoginDAO;
import com.libraryDao.RegistrationDao;

public class Main {
	
	
	public static void main(String[] args) {
		Configuration configuration = new Configuration().configure();
        SessionFactory sessionFactory = configuration.buildSessionFactory();

        // Initialize DAO classes
        LoginDAO loginDAO = new LoginDAO(sessionFactory);
        RegistrationDao registrationDao = new RegistrationDao(sessionFactory);
        LibraryDao libraryDao = new LibraryDao(sessionFactory);
        //MemberDao memberDao = new MemberDao(sessionFactory);


        Scanner scanner = new Scanner(System.in);

        // Prompt user to choose an option
        while (true) {
            System.out.println("All options are given below:");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Add a book");
	        System.out.println("4. Find a book by ID");
	        System.out.println("5. Find all book detais");
	        System.out.println("6. Update book details");
	        System.out.println("7. Remove a book");
	        System.out.println("0. Exit");
	        
	        System.out.println("Enter your choice:");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    registerUser(scanner, registrationDao, loginDAO);
                    break;
                case 2:
                    loginUser(scanner, loginDAO);
                    break;
                
                case 3:
                    // Add Book
                	System.out.print("Enter book ID: ");
                    int bookid = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter book name: ");
                    String bookName = scanner.nextLine();
                    System.out.println("Enter assured date (yyyy-MM-dd): ");
                    LocalDate assuredDate = LocalDate.parse(scanner.nextLine());
                    System.out.println("Enter return date (yyyy-MM-dd): ");
                    LocalDate returnDate = LocalDate.parse(scanner.nextLine());
                    System.out.println("Enter renewal date(YYYY-MM-DD):");
                    LocalDate renewalDate=LocalDate.parse(scanner.nextLine());
                    libraryDao.insertBook(bookid, bookName, assuredDate, returnDate, renewalDate);
                    System.out.println("Book added successfully.");
                    break;
                    
                case 4:
                	// Find a Book by ID
                    System.out.print("Enter book ID to find: ");
                    int findId = scanner.nextInt();
                    Optional<Library> bookById = libraryDao.findById(findId);
                    if (bookById.isPresent()) {
                        Library book = bookById.get();
                        System.out.println("Book found: " + book);
                    } else {
                        System.out.println("Book not found.");
                    }
                    break;
                    
                case 5:
                	System.out.println("Find all book details");
                	List<Library> allBooks = libraryDao.findAll();
                	if (!allBooks.isEmpty()) {
                	    System.out.println("List of all books:");
                	    for (Library book : allBooks) {
                	        System.out.println(book);
                	    }
                	} else {
                	    System.out.println("No books found.");
                	}
                	break;
                case 6:
                    // Update Book
                    System.out.print("Enter book id to update: ");
                    int updateBookId = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    Optional<Library> optionalLibrary = libraryDao.findById(updateBookId);
                    if (optionalLibrary.isPresent()) {
                        Library library = optionalLibrary.get();
                        System.out.print("Enter updated book name: ");
                        library.setBook_name(scanner.nextLine());
                        System.out.print("Enter updated assured date (yyyy-MM-dd): ");
                        library.setAssured_date(LocalDate.parse(scanner.nextLine()));
                        System.out.print("Enter updated return date (yyyy-MM-dd): ");
                        library.setReturn_date(LocalDate.parse(scanner.nextLine()));
                        libraryDao.save(library);
                        System.out.println("Book updated successfully!");
                    } else {
                        System.out.println("Book not found.");
                    }
                    break;
                case 7:
                    // Delete Book
                    System.out.print("Enter book id to delete: ");
                    int deleteBookId = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    libraryDao.remove(deleteBookId);
                    System.out.println("Book deleted successfully!");
                    break;
                   	
                case 0:
                    // Exit
                    System.out.println("Exiting...");
                    sessionFactory.close(); // Close Hibernate session factory
                    scanner.close(); // Close scanner
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
	}
    private static void registerUser(Scanner scanner, RegistrationDao registrationDao, LoginDAO loginDAO) {
        System.out.println("=== Registration ===");
        System.out.println("Enter name:");
        String name = scanner.nextLine();
        System.out.println("Enter email:");
        String email = scanner.nextLine();
        System.out.println("Set password:");
        String password = scanner.nextLine();
        Registration registration = new Registration();
        registration.setName(name);
        registration.setEmail(email);
        registrationDao.save(registration);

        // Save login credentials
        Login login = new Login();
        login.setUsername(email);
        login.setPassword(password);
        loginDAO.save(login);

        System.out.println("Registration successful!");
    }

    private static void loginUser(Scanner scanner, LoginDAO loginDAO) {
        System.out.println("=== Login ===");
        System.out.println("Enter username (email):");
        String username = scanner.nextLine();
        System.out.println("Enter password:");
        String password = scanner.nextLine();

        // Validate login
        Login login = loginDAO.findByUsername(username);
        if (login != null && login.getPassword().equals(password)) {
            System.out.println("Login successful!");
            // Proceed with the rest of the application
        } else {
            System.out.println("Invalid username or password. Please try again.");
        }
    }
    
      
    
}

