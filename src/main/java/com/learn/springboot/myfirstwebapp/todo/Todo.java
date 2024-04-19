package com.learn.springboot.myfirstwebapp.todo;

import java.time.LocalDate;

public class Todo {
	
	private int id;
	private String username;
	private String description;
	private LocalDate targetDare;
	private boolean done;
	
	public Todo(int id, String username, String description, LocalDate targetDare, boolean done) {
		super();
		this.id = id;
		this.username = username;
		this.description = description;
		this.targetDare = targetDare;
		this.done = done;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getTargetDare() {
		return targetDare;
	}

	public void setTargetDare(LocalDate targetDare) {
		this.targetDare = targetDare;
	}

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	@Override
	public String toString() {
		return "Todo [id=" + id + ", username=" + username + ", description=" + description + ", targetDare="
				+ targetDare + ", done=" + done + "]";
	}

}
