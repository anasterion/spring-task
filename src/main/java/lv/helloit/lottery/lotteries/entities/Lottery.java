package lv.helloit.lottery.lotteries.entities;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "lottery")
public class Lottery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "user_limit")
    private String limit;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "lottery_status")
    private String lotteryStatus;

    public Lottery() {
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

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getLotteryStatus() {
        return lotteryStatus;
    }

    public void setLotteryStatus(String lotteryStatus) {
        this.lotteryStatus = lotteryStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lottery lottery = (Lottery) o;
        return Objects.equals(id, lottery.id) &&
                Objects.equals(title, lottery.title) &&
                Objects.equals(limit, lottery.limit) &&
                Objects.equals(startDate, lottery.startDate) &&
                Objects.equals(endDate, lottery.endDate) &&
                Objects.equals(lotteryStatus, lottery.lotteryStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, limit, startDate, endDate, lotteryStatus);
    }

    @Override
    public String toString() {
        return "Lottery{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", limit='" + limit + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", lotteryStatus='" + lotteryStatus + '\'' +
                '}';
    }
}
