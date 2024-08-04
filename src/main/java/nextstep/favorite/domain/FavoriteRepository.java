package nextstep.favorite.domain;

import java.util.List;
import java.util.Optional;

public interface FavoriteRepository {
    Optional<Favorite> findById(Long id);

    List<Favorite> findByMemberId(Long memberId);

    void deleteById(Long id);

    Favorite save(Favorite favorite);
}
