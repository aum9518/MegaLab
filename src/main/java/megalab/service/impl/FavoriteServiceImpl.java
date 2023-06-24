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
import megalab.repository.FavoriteRepository;
import megalab.repository.NewsRepository;
import megalab.repository.UserRepository;
import megalab.service.FavoriteService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {
    private  final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    private final NewsRepository newsRepository;
    @Override
    public SimpleResponse favoriteUserToNews(Long userId,Long newsId,FavoriteRequest favoriteRequest) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NullPointerException("not user id:" + userId));
        News news = newsRepository.findById(newsId).orElseThrow(() -> new NullPointerException("not news id:" + newsId));

        if (user.getFavorites() != null) {
            for (Favorite f : user.getFavorites()) {
                if (f.getNews().equals(news)) {
                    user.getFavorites().remove(f);
                    news.getFavorites().remove(f);
                    favoriteRepository.deleteById(f.getId());
                    return new SimpleResponse();
                }
            }
        }
        Favorite favorite = new Favorite();
        favorite.setNews(news);
        favorite.setUser(user);
        favoriteRepository.save(favorite);
        user.getFavorites().add(favorite);
        return new SimpleResponse();

    }

    @Override
    public FavoritePagination getAllFavorite(int currentPage, int pageSize) {
        List<FavoriteResponse> favoriteResponses = new ArrayList<>();
        int startIndex = (currentPage - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, favoriteResponses.size());
        List<FavoriteResponse> paginatedResponses = favoriteResponses.subList(startIndex, endIndex);
        return new FavoritePagination(paginatedResponses, currentPage, pageSize);
    }

    @Override
    public FavoriteResponse getByIdFavorite(Long id) {
        return favoriteRepository.findByIdFavorite(id).orElseThrow(()-> new NullPointerException(""));
    }

    @Override
    public SimpleResponse updateFavorite(Long id, FavoriteRequest favoriteRequest) {
        return null;
    }

    @Override
    public SimpleResponse deleteFavorite(Long id) {
        Favorite favorite = favoriteRepository.findById(id).orElseThrow(()-> new NullPointerException(""));
        favoriteRepository.delete(favorite);
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("deleted")
                .build();
    }
}
