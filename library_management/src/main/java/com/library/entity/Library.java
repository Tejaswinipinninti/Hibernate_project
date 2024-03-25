package com.library.entity;

import java.time.LocalDate;

import javax.persistence.*;
@Entity
@Table(name="LibraryData")
public class Library {
	
	@Id
	@Column(name="book_id")
	private int book_id;
	private String book_name;
	private LocalDate assured_date;
	private LocalDate renewal_date;
	private LocalDate return_date;
	
	/*@ManyToOne
    @JoinColumn(name = "registration_id")
    private Registration addedBy;

    @ManyToOne
    @JoinColumn(name = "login_id")
    private Login lastUpdatedBy;
    */
    

	/*public boolean isBorrowed() {
		return borrowed;
	}
	public void setBorrowed(boolean borrowed) {
		this.borrowed = borrowed;
	}
	
	
	public Library(int book_id, String book_name, LocalDate assured_date, LocalDate renewal_date, LocalDate return_date,
			boolean borrowed) {
		super();
		this.book_id = book_id;
		this.book_name = book_name;
		this.assured_date = assured_date;
		this.renewal_date = renewal_date;
		this.return_date = return_date;
		this.borrowed = borrowed;
	}*/
	
	
	public int getBook_id() {
		return book_id;
	}
	public void setBook_id(int book_id) {
		this.book_id = book_id;
	}
	public String getBook_name() {
		return book_name;
	}
	public void setBook_name(String book_name) {
		this.book_name = book_name;
	}
	public LocalDate getAssured_date() {
		return assured_date;
	}
	public void setAssured_date(LocalDate assured_date) {
		this.assured_date = assured_date;
	}
	public LocalDate getRenewal_date() {
		return renewal_date;
	}
	public void setRenewal_date(LocalDate renewal_date) {
		this.renewal_date = renewal_date;
	}
	public LocalDate getReturn_date() {
		return return_date;
	}
	public void setReturn_date(LocalDate return_date) {
		this.return_date = return_date;
	}
	
	
	
	public Library() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Library(int book_id, String book_name, LocalDate assured_date, LocalDate renewal_date,
			LocalDate return_date) {
		super();
		this.book_id = book_id;
		this.book_name = book_name;
		this.assured_date = assured_date;
		this.renewal_date = renewal_date;
		this.return_date = return_date;
	}
	
	
	public Library(String book_name, LocalDate assured_date, LocalDate renewal_date) {
		super();
		this.book_name = book_name;
		this.assured_date = assured_date;
		this.renewal_date = renewal_date;
	}
	@Override
	public String toString() {
		return "Library [book_id=" + book_id + ", book_name=" + book_name + ", assured_date=" + assured_date
				+ ", renewal_date=" + renewal_date + ", return_date=" + return_date + "]";
	}
	
}


	
	