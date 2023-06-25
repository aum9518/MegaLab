package megalab.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import megalab.dto.SimpleResponse;
import megalab.dto.favorite.FavoritePagination;
import megalab.dto.favorite.FavoriteRequest;
import megalab.dto.favorite.FavoriteResponse;
import megalab.entity.Favorite;
import megalab.entity.News;
import megalab.entity.User;
import megalab.exception.AlreadyExistException;
import megalab.exception.BadCredentialException;
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
@Slf4j
public class FavoriteServiceImpl implements FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    private final NewsRepository newsRepository;

    @Override
    public SimpleResponse saveFavorite(FavoriteRequest favoriteRequest) {
        String nickName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.getUserByUserInfoNickName(nickName)
                .orElseThrow(() -> {
                    log.error("User with nickname: %s not found".formatted(nickName));
                    return new NotFoundException("User with nickname: %s not found".formatted(nickName));
                });
        News news = newsRepository.findById(favoriteRequest.newsId())
                .orElseThrow(() -> {
                    log.error("News with id: {} not found", favoriteRequest.newsId());
                    return new NotFoundException("News with id: %s not found".formatted(favoriteRequest.newsId()));
                });
        if (favoriteRepository.existsByUserIdAndNewsId(user.getId(), news.getId())) {
            log.error("News with id: {} already exists", news.getId());
            throw new AlreadyExistException("News with id: %s already exists".formatted(news.getId()));
        }
        Favorite favorite = Favorite
                .builder()
                .createDate(ZonedDateTime.now())
                .build();
        news.getFavorites().add(favorite);
        user.getFavorites().add(favorite);
        favorite.setUser(user);
        favorite.setNews(news);
        favoriteRepository.save(favorite);
        log.info("Favorite successfully saved");
        return SimpleResponse
                .builder()
                .status(HttpStatus.OK)
                .message("Favorite successfully saved")
                .build();

    }

    @Override
    public FavoritePagination getAllFavorite(int currentPage, int pageSize) {
        String nickName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.getUserByUserInfoNickName(nickName)
                .orElseThrow(() -> {
                    log.error("User with nickname: %s not found".formatted(nickName));
                    return new NotFoundException("User with nickname: %s not found".formatted(nickName));
                });
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize);
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
        return favoriteRepository.getFavoriteId(id)
                .orElseThrow(() -> {
                    log.error("Favorite with id: %s not found".formatted(id));
                    return new NotFoundException("Favorite with id: %s not found".formatted(id));
                });
    }

    private Favorite getFavoriteByIdAndCurrentUser(Long id) {
        String nickName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.getUserByUserInfoNickName(nickName)
                .orElseThrow(() -> {
                    log.error("User with nickname: %s not found".formatted(nickName));
                    return new NotFoundException("User with nickname: %s not found".formatted(nickName));
                });
        if (!favoriteRepository.existsById(id)) {
            log.error("Favorite with id: %s not found".formatted(id));
            throw new NotFoundException("Favorite with id: %s not found".formatted(id));
        }
        if (!favoriteRepository.existsByIdAndUserId(id, user.getId())) {
            log.error("You don't have access");
            throw new BadCredentialException("You don't have access");
        }
        return favoriteRepository.getFavoriteByIdAndUserId(id, user.getId())
                .orElseThrow(() ->
                {
                    log.error("Favorite with id: %s not found".formatted(id));
                    return new NotFoundException("Favorite with id: %s not found".formatted(id));
                });
    }

    @Override
    public SimpleResponse updateFavorite(Long id, FavoriteRequest favoriteRequest) {
        if (!favoriteRepository.existsById(id)) {
            log.error("Favorite with id: %s not found".formatted(id));
            throw new NotFoundException("Favorite with id: %s not found".formatted(id));
        }
        Favorite favorite = getFavoriteByIdAndCurrentUser(id);
        News news = newsRepository.findById(favoriteRequest.newsId())
                .orElseThrow(() ->
                {
                    log.error("News with id: %s not found".formatted(favoriteRequest.newsId()));
                    return new NotFoundException("News with id: %s not found".formatted(favoriteRequest.newsId()));
                });
        favorite.setNews(news);
        favoriteRepository.save(favorite);
        log.info("Favorite with id: %s successfully updated".formatted(id));
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("Favorite with id: %s successfully updated".formatted(id))
                .build();
    }

    @Override
    public SimpleResponse deleteFavorite(Long id) {
        Favorite favorite = getFavoriteByIdAndCurrentUser(id);
        favoriteRepository.delete(favorite);
        log.info("Favorite with id: %s successfully deleted".formatted(id));
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("Favorite successfully deleted")
                .build();
    }


}
