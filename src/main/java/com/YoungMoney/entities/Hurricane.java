package com.YoungMoney.entities;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Created by stevenburris on 10/21/16.
 */
@Entity
@Table(name = "hurricanes")
public class Hurricane {
    public enum Category {
        ONE, TWO, THREE, FOUR, FIVE
    }

    @Id
    @GeneratedValue
    public int id;

    @Column(nullable = false)
    public String name;

    @Column(nullable = false)
    public String location;

    @Column(nullable = false)
    public Category category;

    @Column(nullable = false)
    public String image;

    @Column(nullable = false)
    public LocalDate date;

    @ManyToOne
    public User user;

    @Transient
    public boolean isMe;

    public Hurricane() {
    }

    public Hurricane(String name, String location, Category category, String image, LocalDate date, User user) {
        this.name = name;
        this.location = location;
        this.category = category;
        this.image = image;
        this.user = user;
        this.date = date;

    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
