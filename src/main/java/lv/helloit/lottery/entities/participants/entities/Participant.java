package lv.helloit.lottery.entities.participants.entities;

import lv.helloit.lottery.entities.lotteries.constraints.IsGreaterThanZero;
import lv.helloit.lottery.entities.lotteries.entities.Lottery;
import lv.helloit.lottery.entities.participants.constraints.AgeReq;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Objects;

@Entity
@Table(name = "participant")
public class Participant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @IsGreaterThanZero
    @Column(name = "lottery_id")
    private String lotteryId;

    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$", message = "should be a valid e-mail")
    @NotBlank(message = "can't be empty")
    @Column(name = "email")
    private String email;

    @AgeReq
    @Column(name = "age")
    private String age;

    @Column(name = "code")
    private String code;

    @Column(name = "status")
    private String status;

    @ManyToOne
    @JoinColumn(name = "participant_lottery_id")
    private Lottery lottery;

    public Participant() {
    }

    public Participant(String lotteryId, String email, String age, String code, String status, Lottery lottery) {
        this.lotteryId = lotteryId;
        this.email = email;
        this.age = age;
        this.code = code;
        this.status = status;
        this.lottery = lottery;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email.trim();
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Lottery getLottery() {
        return lottery;
    }

    public void setLottery(Lottery lottery) {
        this.lottery = lottery;
    }

    public String getLotteryId() {
        return lotteryId;
    }

    public void setLotteryId(String lotteryId) {
        this.lotteryId = lotteryId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Participant that = (Participant) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(lotteryId, that.lotteryId) &&
                Objects.equals(email, that.email) &&
                Objects.equals(age, that.age) &&
                Objects.equals(code, that.code) &&
                Objects.equals(status, that.status) &&
                Objects.equals(lottery, that.lottery);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, lotteryId, email, age, code, status, lottery);
    }

    @Override
    public String toString() {
        return "Participant{" +
                "id=" + id +
                ", lotteryId='" + lotteryId + '\'' +
                ", email='" + email + '\'' +
                ", age='" + age + '\'' +
                ", code='" + code + '\'' +
                ", status='" + status + '\'' +
                ", lottery=" + (lottery != null ? lottery.getId() : "") +
                '}';
    }
}
