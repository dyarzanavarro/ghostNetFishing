package ghostnetfishing.demo.repo;

import ghostnetfishing.demo.domain.GhostNet;
import ghostnetfishing.demo.domain.GhostNetStatus;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface GhostNetRepository extends JpaRepository<GhostNet, Long> {

  // offene = alles außer GEBORGEN
  @EntityGraph(attributePaths = {"assignedRescuer", "reportedBy"})
  List<GhostNet> findByStatusNotOrderByCreatedAtDesc(GhostNetStatus status);

  // geborgene
  @EntityGraph(attributePaths = {"assignedRescuer", "reportedBy"})
  List<GhostNet> findByStatusOrderByCreatedAtDesc(GhostNetStatus status);
}