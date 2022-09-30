package ru.acme.web.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;
import ru.acme.web.model.interfaces.ImageURLs;
import ru.acme.web.util.validation.NewImage;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "content")
public class Content implements ImageURLs {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "content_generator")
    @SequenceGenerator(name = "content_generator", sequenceName = "glb_id_gen", allocationSize = 1)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "menu_id")
    @JsonIgnore
    private Menu menu;
    @Transient
    private String menuId;
    @JsonIgnore
    private ZonedDateTime creation;
    @NotNull
    private String title;
    @NotNull
    @Column(name = "content_data")
    private String content;
    @JsonIgnore
    @ElementCollection
    private List<String> imgUrl;
    @NotNull
    @Min(0)
    @Max(10)
    private Integer priority;
    @NotNull
    @Column(name = "active")
    private boolean show;
    @ManyToOne
    @JoinColumn(name = "author_id")
    @JsonIgnore
    private User author;

    @Transient
    @NewImage
    private String newImg;
    @Transient
    private Long authorId;

    public Content(Long id, Menu menu, String title, String content, List<String> imgUrl, ZonedDateTime creation, User author, Integer priority, boolean show) {
        this.id = id;
        this.menu = menu;
        this.title = title;
        this.content = content;
        this.imgUrl = imgUrl;
        this.creation = creation;
        this.priority = priority;
        this.show = show;
        this.author = author;
        this.authorId = author.getId();
    }

    public Content() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public String getMenuId() {
        if (menu != null && menu.getMenu() != null) {
            return menu.getMenu();
        }
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @JsonIgnore
    public String getNewImg() {
        return newImg;
    }

    @JsonSetter
    public void setNewImg(String newImg) {
        this.newImg = newImg;
    }

    public void setImgUrl(List<String> imgUrl) {
        this.imgUrl = imgUrl;
    }

    @JsonGetter
    public List<String> getImgUrl() {
        return imgUrl;
    }

    public ZonedDateTime getCreation() {
        return creation;
    }

    public void setCreation(ZonedDateTime creation) {
        this.creation = creation;
    }

    @JsonIgnore
    public String getDateTime() {
        return DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(this.creation);
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Long getAuthorId() {
        if (author != null) {
            setAuthorId(author.getId());
        }
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Content content1 = (Content) o;
        return show == content1.show && Objects.equals(id, content1.id)
                && title.equals(content1.title) && content.equals(content1.content)
                && priority.equals(content1.priority);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
