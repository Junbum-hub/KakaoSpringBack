package go.glogprototype.domain.user.domain;

import go.glogprototype.domain.post.domain.Post;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@Getter
@RequiredArgsConstructor
public class View {

    @Id @GeneratedValue
    @Column(name = "view_id")
    private Long id;

    private int view_count;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

}
