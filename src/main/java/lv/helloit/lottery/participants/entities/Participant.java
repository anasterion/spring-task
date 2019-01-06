package lv.helloit.lottery.participants.entities;

import lv.helloit.lottery.lotteries.entities.Lottery;
import lv.helloit.lottery.participants.constraints.AgeReq;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Objects;

@Entity
@Table(name = "participant")
public class Participant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Email(message = "should be valid email")
    @Column(name = "email")
    private String email;

    @AgeReq
    @Column(name = "age")
    private String age;

    @Column(name = "code")
    private Integer code;

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

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Lottery getLottery() {
        return lottery;
    }

    public void setLottery(Lottery lottery) {
        this.lottery = lottery;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Participant that = (Participant) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(email, that.email) &&
                Objects.equals(age, that.age) &&
                Objects.equals(code, that.code) &&
                Objects.equals(lottery, that.lottery);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, age, code, lottery);
    }

    @Override
    public String toString() {
        return "Participant{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", age='" + age + '\'' +
                ", code=" + code +
                ", lottery=" + lottery +
                '}';
    }
}
