package com.babzip.backend.top10.service;

import com.babzip.backend.global.exception.BusinessException;
import com.babzip.backend.global.exception.ExceptionType;
import com.babzip.backend.top10.domain.Top10;
import com.babzip.backend.top10.dto.request.Top10Request;
import com.babzip.backend.top10.dto.response.Top10Response;
import com.babzip.backend.top10.repository.Top10Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class Top10Service {

    private final Top10Repository top10Repository;

    @Transactional
    public void createTop10(Long userId, List<Top10Request> request){

        if (request.size() > 10) {
            throw new BusinessException(ExceptionType.TOP10_LIMIT_EXCEEDED);
        }

        // 이전의 Top10 삭제
        top10Repository.deleteByUserId(userId);

        for (Top10Request r : request) {

            if (r == null || r.rank() == null || r.restaurantName() == null || r.address() == null) {
                continue;
            }

            top10Repository.save(
                    Top10.builder()
                    .userId(userId)
                    .restaurantName(r.restaurantName())
                    .address(r.address())
                    .rank(r.rank())
                    .build()
            );
        }
    }

    public Page<Top10Response> getTop10(Long userId, Pageable pageable){
        Page<Top10> response = top10Repository.findByUserId(userId, pageable);
        return response.map(Top10Response::toDto);

    }
}
