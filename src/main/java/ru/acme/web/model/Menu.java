package ru.acme.web.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ru.acme.web.util.Translate;

import javax.persistence.*;

@Entity
public class Menu {
    @Id
    private String menu;
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "main_page_ID")
    @JsonIgnore
    private MainPage mainPage;
    @JsonIgnore
    private Integer queue = 0;

    public Integer getQueue() {
        return queue;
    }

    public void setQueue(Integer queue) {
        this.queue = queue;
    }

    public MainPage getMainPage() {
        return mainPage;
    }

    public void setMainPage(MainPage mainPage) {
        this.mainPage = mainPage;
    }

    public Menu(String name) {
        this.name = name;
        this.menu = Translate.getCyrillicToLatin(name).toLowerCase().replace(" ", "_");
    }

    public Menu(String name, MainPage mainPage) {
        this.name = name;
        this.mainPage = mainPage;
        this.menu = Translate.getCyrillicToLatin(name).toLowerCase().replace(" ", "_");
    }

    public Menu() {
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.menu;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Menu me)) {
            return false;
        }
        return me.getMenu().equals(this.menu);
    }
}