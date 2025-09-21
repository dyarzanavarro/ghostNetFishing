package ghostnetfishing.demo.repo;

import ghostnetfishing.demo.domain.GhostNet;
import ghostnetfishing.demo.domain.GhostNetStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface GhostNetRepository extends JpaRepository<GhostNet, Long> {

  List<GhostNet> findByStatusNotOrderByCreatedAtDesc(GhostNetStatus status);

  List<GhostNet> findByStatusOrderByCreatedAtDesc(GhostNetStatus status);
}
