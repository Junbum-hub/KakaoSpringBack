package go.glogprototype.domain.post.application;

import go.glogprototype.domain.post.dao.PostRepository;
import go.glogprototype.domain.post.domain.Post;
import go.glogprototype.domain.post.dto.PostDto.*;
import go.glogprototype.domain.post.dto.PostWriteDto;
import go.glogprototype.domain.user.dao.MemberRepository;
import go.glogprototype.domain.user.domain.Member;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Page<FindPostResponseDto> findAllPost(String keyword, Pageable pageable) {
        return postRepository.postListResponseDto(keyword, pageable);
    }


    @Transactional
    public ResponseEntity<PostWriteDto> saveAllPost(PostWriteDto postWriteDto) {
        // 해당 유저가 멤버 테이블에 존재하는지 확인한다.
        Member member = memberRepository.findById(postWriteDto.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        Post post = Post.builder()
                .member(member)
                .title(postWriteDto.getTitle())
                .postContent(postWriteDto.getPostContent())
                .thumbnailImage(postWriteDto.getThumbnailImage())
                .createdDate(postWriteDto.getCreatedDate())
                .categories(postWriteDto.getCategories())
                .isPublic(postWriteDto.isPublic())
                .build();

        // 엔티티를 데이터베이스에 저장
        Post savedPost = postRepository.save(post);


        PostWriteDto responseDto = PostWriteDto.builder()
                .memberId(savedPost.getMember().getId())
                .title(savedPost.getTitle())
                .postContent(savedPost.getPostContent())
                .thumbnailImage(savedPost.getThumbnailImage())
                .createdDate(savedPost.getCreatedDate())
                .categories(savedPost.getCategories())
                .isPublic(savedPost.isPublic())
                .build();



        return ResponseEntity.ok(responseDto);

    }


}
