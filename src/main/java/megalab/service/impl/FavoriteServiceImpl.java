package megalab.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import megalab.dto.SimpleResponse;
import megalab.dto.favorite.FavoritePagination;
import megalab.dto.favorite.FavoriteRequest;
import megalab.dto.favorite.FavoriteResponse;

import megalab.entity.Favorite;
import megalab.entity.News;
import megalab.entity.User;

import megalab.exception.NotFoundException;
import megalab.repository.FavoriteRepository;
import megalab.repository.NewsRepository;
import megalab.repository.UserRepository;
import megalab.service.FavoriteService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import java.time.ZonedDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    private final NewsRepository newsRepository;

    @Override
    public SimpleResponse favoriteUserToNews(FavoriteRequest favoriteRequest) {
        String nickName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.getUserByUserInfoNickName(nickName).orElseThrow(() -> new NotFoundException("User with nickname: %s not found".formatted(nickName)));
        News news = newsRepository.findById(favoriteRequest.newsId()).orElseThrow(() -> new NotFoundException("News with id: %s not found".formatted(favoriteRequest.newsId())));
        Favorite favorite = Favorite
                .builder()
                .createDate(ZonedDateTime.now())
                .build();
        favorite.setUser(user);
        favorite.setNews(news);
        favoriteRepository.save(favorite);
        return SimpleResponse
                .builder()
                .status(HttpStatus.OK)
                .message("Favorite successfully saved")
                .build();

    }

    @Override
    public FavoritePagination getAllFavorite(int currentPage, int pageSize) {
        String nickName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.getUserByUserInfoNickName(nickName).orElseThrow(() -> new NotFoundException("User with nickname: %s not found".formatted(nickName)));
        Pageable pageable = PageRequest.of(currentPage -1,pageSize);
        Page<FavoriteResponse> byIdFavorite = favoriteRepository.findByIdFavorite(user.getId(), pageable);
        return FavoritePagination
                .builder()
                .favoriteResponses(byIdFavorite.getContent())
                .currentPage(byIdFavorite.getNumber() + 1)
                .pageSize(byIdFavorite.getSize())
                .build();
    }

    @Override
    public FavoriteResponse getByIdFavorite(Long id) {
        return favoriteRepository.getFavoriteId(id).orElseThrow(() -> new NotFoundException("Favorite with id: %s not found".formatted(id)));
    }

    @Override
    public SimpleResponse updateFavorite(Long id, FavoriteRequest favoriteRequest) {
        String nickName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.getUserByUserInfoNickName(nickName).orElseThrow(() -> new NotFoundException("User with nickname: %s not found".formatted(nickName)));
        Favorite favorite = favoriteRepository.getFavoriteByNewsAndUserId(id, user.getId(), favoriteRequest.newsId()).orElseThrow(() -> new NotFoundException("Comment with id: %s not found".formatted(id)));
        News news = newsRepository.findById(favoriteRequest.newsId()).orElseThrow(() -> new NotFoundException("News with id: %s not found".formatted(favoriteRequest.newsId())));
        favorite.setNews(news);
        favoriteRepository.save(favorite);
        return SimpleResponse
                .builder()
                .status(HttpStatus.OK)
                .message("Favorite with id: %s successfully updated".formatted(id))
                .build();
    }

    @Override
    public SimpleResponse deleteFavorite(Long id) {
        String nickName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.getUserByUserInfoNickName(nickName).orElseThrow(() -> new NotFoundException("User with nickname: %s not found".formatted(nickName)));
        Favorite favorite = favoriteRepository.getFavoriteByIdAndUserId(id,user.getId()).orElseThrow(() -> new NotFoundException("Favorite with id: %s not found".formatted(id)));
        favoriteRepository.delete(favorite);
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("deleted")
                .build();
    }
}
