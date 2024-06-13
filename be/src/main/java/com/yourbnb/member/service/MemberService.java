package com.yourbnb.member.service;

import com.yourbnb.member.exception.MemberNotFoundException;
import com.yourbnb.member.model.Member;
import com.yourbnb.member.model.dto.MemberResponse;
import com.yourbnb.member.repository.MemberRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public List<MemberResponse> getAllMembers() {
        return memberRepository.findAll().stream()
                .map(MemberResponse::from)
                .collect(Collectors.toList());
    }

    public Optional<MemberResponse> getMemberById(String memberId) {
        return memberRepository.findById(memberId)
                .map(MemberResponse::from);
    }

    /**
     * 데이터베이스에서 멤버를 조회하고 존재하면 반환한다.
     *
     * @param memberId 멤버 아이디
     * @return 멤버 엔티티 객체
     * @throws MemberNotFoundException 멤버를 찾을 수 없는 경우
     */
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public Member getMemberByIdOrThrow(String memberId) {
        return memberRepository.findById(memberId).orElseThrow(() -> new MemberNotFoundException(memberId));
    }
}
