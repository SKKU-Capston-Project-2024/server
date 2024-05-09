package site.mutopia.server.domain.topster.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import site.mutopia.server.domain.topster.entity.TopsterAlbumEntity;
import site.mutopia.server.domain.topster.entity.TopsterAlbumId;

import java.util.List;

@Repository
public interface TopsterAlbumRepository extends JpaRepository<TopsterAlbumEntity, TopsterAlbumId> {
    List<TopsterAlbumEntity> findByTopsterId(Long topsterId);

    @Modifying
    @Transactional
    @Query("DELETE FROM TopsterAlbumEntity ta WHERE ta.topster.id = :topsterId AND ta.album.id IN :albumIds")
    void deleteByTopsterIdAndAlbumIds(@Param("topsterId") Long topsterId, @Param("albumIds") List<String> albumIds);
}