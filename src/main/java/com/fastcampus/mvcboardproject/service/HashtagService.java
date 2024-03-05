package com.fastcampus.mvcboardproject.service;

import com.fastcampus.mvcboardproject.domain.Hashtag;
import com.fastcampus.mvcboardproject.repository.HashtagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Transactional
@RequiredArgsConstructor
@Service
public class HashtagService {

    private final HashtagRepository hashtagRepository;

    @Transactional(readOnly = true)
    public Set<Hashtag> findHashtagsByNames(Set<String> hashtagNames) {
        // 해시태그 이름을 가진 해시태그들을 가져오고, 중복 없는 Set으로 변환
        return new HashSet<>(hashtagRepository.findByHashtagNameIn(hashtagNames));
    }

    public Set<String> parseHashtagNames(String content) {
        /*
        content 내에서 해시태그를 파싱하여 해시태그 이름들을 가져온다.
        content가 없으면 파싱할 필요가 없어, set은 비어있고,
        파싱을 위한 패턴은 #으로 시작하고, 알파벳, 숫자, 한글, _이 오는 문자열을 가져온다.
        매칭된 문자열은 #을 빼고 Set에 추가하고, 최종 결과를 불변 객체로 만든다.
        */
        if (content == null) {
            return Set.of();
        }

        Pattern pattern = Pattern.compile("#[\\w가-힣]+");
        Matcher matcher = pattern.matcher(content.strip());
        Set<String> result = new HashSet<>();

        while (matcher.find()) {
            result.add(matcher.group().replace("#", ""));
        }

        return Set.copyOf(result);
    }

    public void deleteHashtagWithoutArticles(Long hashtagId) {
        /*
        해시태그 아이디를 가지고 조회하였을 때, 게시글 목록이 있으면 삭제하지 않고,
        게시글 목록이 비어있으면 삭제한다.
         */
        Hashtag hashtag = hashtagRepository.getReferenceById(hashtagId);
        if (hashtag.getArticles().isEmpty()) {
            hashtagRepository.delete(hashtag);
        }
    }
}
