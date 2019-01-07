package lv.helloit.lottery.participants.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lv.helloit.lottery.lotteries.constraints.IsGreaterThanZero;
import lv.helloit.lottery.lotteries.entities.Lottery;
import lv.helloit.lottery.participants.constraints.AgeReq;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
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

    @Email(message = "should be valid email")
    @NotBlank(message = "can't be empty")
    @Column(name = "email")
    private String email;

    @AgeReq
    @Column(name = "age")
    private String age;

    @Column(name = "code")
    private String code;

    @ManyToOne
    @JoinColumn(name = "participant_lottery_id")
    private Lottery lottery;

    public Participant() {
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
                Objects.equals(lottery, that.lottery);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, lotteryId, email, age, code, lottery);
    }

    @Override
    public String toString() {
        return "Participant{" +
                "id=" + id +
                ", lotteryId='" + lotteryId + '\'' +
                ", email='" + email + '\'' +
                ", age='" + age + '\'' +
                ", code=" + code +
                ", lottery=" + (lottery != null ? lottery.getId() : "") +
                '}';
    }
}
