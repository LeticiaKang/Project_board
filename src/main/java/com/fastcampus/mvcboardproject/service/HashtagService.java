package com.fastcampus.mvcboardproject.service;

import com.fastcampus.mvcboardproject.domain.Hashtag;
import org.springframework.stereotype.Service;
import java.util.Set;

@Service
public class HashtagService {
    public Set<String> parseHashtagNames(String content) {
        return null;
    }

    public Set<Hashtag> findHashtagsByNames(Set<String> expectedHashtagNames) {
        return null;
    }

    public void deleteHashtagWithoutArticles(Object any) {
    }
}
