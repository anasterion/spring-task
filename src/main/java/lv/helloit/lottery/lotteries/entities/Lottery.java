package lv.helloit.lottery.lotteries.entities;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

public class Lottery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "limit")
    private Long limit;

    public Lottery() {
    }

    public Lottery(String title, Long limit) {
        this.title = title;
        this.limit = limit;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getLimit() {
        return limit;
    }

    public void setLimit(Long limit) {
        this.limit = limit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lottery lottery = (Lottery) o;
        return Objects.equals(id, lottery.id) &&
                Objects.equals(title, lottery.title) &&
                Objects.equals(limit, lottery.limit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, limit);
    }

    @Override
    public String toString() {
        return "Lottery{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", limit=" + limit +
                '}';
    }
}
