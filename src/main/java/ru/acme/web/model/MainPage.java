package ru.acme.web.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import ru.acme.web.model.interfaces.ImageURLs;
import ru.acme.web.util.validation.Coordinate;
import ru.acme.web.util.validation.NewImage;
import ru.acme.web.util.validation.NewMenu;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.stream.Collectors;

@JsonIgnoreProperties
@Entity
@Table(name = "main_page")
public class MainPage implements ImageURLs {
    @Id
    @JsonIgnore
    public final Long id = 1L;
    @NotNull
    private String title;
    @JsonIgnore
    @ElementCollection
    private List<String> imgUrl;
    @Transient
    @NewImage
    private String newImg;
    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "mainPage")
    private List<Menu> menuList;
    @Transient
    @NewMenu
    private List<String> newMenu;
    @NotNull
    @ElementCollection
    private Map<String, String> colorScheme;
    @NotNull
    private String address;
    @JsonIgnore
    private String mappoint;
    @Transient
    @Coordinate
    private Double[] coordinates;
    @NotNull
    @ElementCollection
    private List<String> telList;
    @Email
    private String email;
    @ElementCollection
    private Map<String, String> socials;

    public MainPage() {
    }

    public MainPage(String title, List<String> img, List<Menu> menu, Map<String, String> colorScheme, String address, List<String> telList, String email, Map<String, String> socials) {
        this.title = title;
        this.imgUrl = img;
        this.menuList = menu;
        this.colorScheme = colorScheme;
        this.address = address;
        this.telList = telList;
        this.email = email;
        this.socials = socials;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<String> getTelList() {
        return telList;
    }

    public void setTelList(List<String> telList) {
        this.telList = telList;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Map<String, String> getSocials() {
        return socials;
    }

    public Long getId() {
        return id;
    }

    public void setSocials(Map<String, String> socials) {
        this.socials = socials;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImgUrl(List<String> img) {
        this.imgUrl = img;
    }

    public List<Menu> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<Menu> menuList) {
        this.menuList = menuList;
    }

    public Map<String, String> getColorScheme() {
        return colorScheme;
    }

    public void setColorScheme(Map<String, String> colorScheme) {
        this.colorScheme = colorScheme;
    }

    @JsonIgnore
    public String getNewImg() {
        return newImg;
    }

    @JsonSetter
    public void setNewImg(String newImg) {
        this.newImg = newImg;
    }

    @JsonGetter
    public List<String> getImgUrl() {
        return imgUrl;
    }

    @JsonIgnore
    public List<String> getNewMenu() {
        return newMenu;
    }

    @JsonSetter
    public void setNewMenu(List<String> newMenu) {
        this.newMenu = newMenu;
    }

    @JsonGetter
    public Map<String, String> getMenu() {
        if(menuList != null && !menuList.isEmpty()){
            return menuList
                    .stream()
                    .sorted(Comparator.comparing(Menu::getQueue, Integer::compareTo))
                    .collect(Collectors.toMap(Menu::getMenu, Menu::getName, (x, y) -> y, LinkedHashMap::new));
        } else return new HashMap<>();
    }

    public String getMappoint() {
        return mappoint;
    }

    public void setMappoint(String mappoint) {
        this.mappoint = mappoint;
    }

    public Double[] getCoordinates() {
        if (mappoint != null){
            Double[] result = new Double[2];
            String[] points = mappoint.split(" ");
            result[0] = Double.parseDouble(points[0]);
            result[1] = Double.parseDouble(points[1]);
            return result;
        } else {
            return this.coordinates;
        }
    }

    public void setCoordinates(Double[] coordinates) {
        this.coordinates = coordinates;
        if (coordinates != null && coordinates.length == 2){
            this.mappoint = coordinates[0] + " " + coordinates[1];
        }
    }
}
