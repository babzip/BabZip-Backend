package com.babzip.backend.guestbook.repository;

import com.babzip.backend.guestbook.dto.response.GuestbookSearchResponse;
import com.babzip.backend.guestbook.entity.Guestbook;
import com.babzip.backend.guestbook.entity.QGuestbook;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RequiredArgsConstructor
public class GuestbookQueryDslRepositoryImpl implements GuestbookQueryDslRepository{

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<GuestbookSearchResponse> searchByRestaurantName(String restaurantName, Pageable pageable, Long userId) {
        QGuestbook guestbook = QGuestbook.guestbook;
        BooleanBuilder builder = new BooleanBuilder();


        builder.and(guestbook.user.id.eq(userId));
        if(restaurantName != null && !restaurantName.isEmpty()){
            builder.and(guestbook.restaurantName.containsIgnoreCase(restaurantName));
        }

        List<Guestbook> guestbookList = queryFactory.selectFrom(guestbook)
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory
                .select(guestbook.count())
                .from(guestbook)
                .where(builder)
                .fetchOne();

        List<GuestbookSearchResponse> response = guestbookList.stream().map(
                GuestbookSearchResponse::toDto
        ).toList();

        return new PageImpl<>(response, pageable, total != null ? total : 0);
    }
}
