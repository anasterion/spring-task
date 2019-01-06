package lv.helloit.lottery.lotteries.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lv.helloit.lottery.lotteries.constraints.IsGreaterThanZero;
import lv.helloit.lottery.participants.entities.Participant;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "lottery")
public class Lottery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "value can't be empty")
    @Column(name = "title")
    private String title;

    @IsGreaterThanZero
    @NotBlank(message = "value can't be empty")
    @Column(name = "user_limit")
    private String limit;

    @Column(name = "start_date")
    private String startDate;

    @Column(name = "end_date")
    private String endDate;

    @Column(name = "lottery_status")
    private String lotteryStatus;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "lottery")
    @JsonIgnore // had issue with infinite jackson loop in logs, fixed by adding json ignore annotation here
    private List<Participant> participants;

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
        this.title = title.trim();
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit.trim();
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getLotteryStatus() {
        return lotteryStatus;
    }

    public void setLotteryStatus(String lotteryStatus) {
        this.lotteryStatus = lotteryStatus;
    }

    public List<Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Participant> participants) {
        this.participants = participants;
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
