package hellojpa;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Team {

    @Id @GeneratedValue
    @Column(name = "TEAM_ID")
    private Long id;
    private String name;

    // 멤버에서 팀으로 -> 다대일
    // 팀에서 멤버로 -> 일대다
    // mappedBy 키워는 반대편 방향의 team 이라는 멤버변수를 매칭 시킨다.
    // *mappedBy 의 정체는? -> 객체와 테이블간에 연관관계를 맺는 차이를 이해해야 한다.
    // 객체 연관관계 = 2개
    // 회원 -> 팀 연관관계 1개(단방향)
    // 팀 -> 회원 연관관계 1개(단방향)
    // 테이블 연관관계 = 1개
    // 회원 <-> 팀의 연관관계 1개(양방향)
    @OneToMany
    @JoinColumn(name = "TEAM_ID")
    private List<Member> members = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }
}
