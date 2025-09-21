package ghostnetfishing.demo.repo;
import ghostnetfishing.demo.domain.GhostNet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
public interface GhostNetRepository extends JpaRepository<GhostNet, Long> {
  @Query("select g from GhostNet g where g.status <> 'GEBORGEN' order by g.createdAt desc")
  List<GhostNet> findOpen();
}
