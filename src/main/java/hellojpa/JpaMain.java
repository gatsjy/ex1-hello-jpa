package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    public static void main(String[] args){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try{
            // 삽입할때 이슈
            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("member1");
            member.setTeam(team);
            em.persist(member);

            em.flush();
            em.clear();

            // 조회할때 이슈
            // Member findMember = em.find(Member.class, member.getId());
            // 이 코드는 멤버에서 팀으로 갈 수 있지만, 팀에서 멤버로는 갈 수 없다.
            // 사실 둘은 서로 왔다 갔다 할 수 있다. -> 이러한 경우를 양방향 연관관계라 표현한다.
            // 테이블의 경우에는 그저 포린키가 들어있으면 서로의 연관관계가 매핑이 된다.
            // 하지만 중요한 사실은 객체의 경우이다. 객체의 경우 서로의 연관관계를 매핑시켜줄 어떠한 코딩을 해야한다.
            // Team findTeam = findMember.getTeam();
            // System.out.println("findTeam = " + findTeam.getName());
            // 반대 방향으로도 객체 그래프 탐색을 할 수 있다.
            Member findMember = em.find(Member.class, member.getId());
            List<Member> members = findMember.getTeam().getMembers();
            for (Member m : members) {
                System.out.println("m.getUsername() = " + m.getUsername());
            }

            tx.commit();
        }catch (Exception e){
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();

    }
}
