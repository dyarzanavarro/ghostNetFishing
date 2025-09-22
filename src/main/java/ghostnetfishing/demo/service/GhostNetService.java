package ghostnetfishing.demo.service;

import ghostnetfishing.demo.domain.GhostNet;
import ghostnetfishing.demo.domain.GhostNetStatus;
import ghostnetfishing.demo.domain.Person;
import ghostnetfishing.demo.repo.GhostNetRepository;
import ghostnetfishing.demo.repo.PersonRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GhostNetService {

  private final GhostNetRepository nets;
  private final PersonRepository persons;

  public GhostNetService(GhostNetRepository nets, PersonRepository persons) {
    this.nets = nets;
    this.persons = persons;
  }

  @Transactional
  public GhostNet report(double lat, double lng, String size, Long reporterId, boolean anonymous) {
    GhostNet g = new GhostNet();
    g.setLatitude(lat);
    g.setLongitude(lng);
    g.setSizeEstimate(size);
    if (!anonymous && reporterId != null) {
      persons.findById(reporterId).ifPresent(g::setReportedBy);
    }
    g.setStatus(GhostNetStatus.GEMELDET);
    return nets.save(g);
  }

  @Transactional
  public GhostNet assignRescuer(Long netId, Long rescuerId) {
    GhostNet g = nets.findById(netId).orElseThrow();
    if (g.getAssignedRescuer() != null) throw new IllegalStateException("Bereits zugeordnet");
    Person p = persons.findById(rescuerId).orElseThrow();
    if (!p.isRescuer()) throw new IllegalArgumentException("Person ist keine bergende Person");
    g.setAssignedRescuer(p);
    g.setStatus(GhostNetStatus.BERGUNG_BEVORSTEHEND);
    return g; // Dirty Checking speichert beim Flush
  }

  @Transactional
  public GhostNet markSalvaged(Long netId) {
    GhostNet g = nets.findById(netId).orElseThrow();
    g.setStatus(GhostNetStatus.GEBORGEN);
    return g;
  }

  public List<GhostNet> listOpen() {
    return nets.findByStatusNotOrderByCreatedAtDesc(GhostNetStatus.GEBORGEN);
  }

  public List<GhostNet> listSalvaged() {
    return nets.findByStatusOrderByCreatedAtDesc(GhostNetStatus.GEBORGEN);
  }

  @Transactional
  public void markMissing(long id, long reporterId) {
    GhostNet g = nets.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Geisternetz nicht gefunden: " + id));

    if (g.getStatus() == GhostNetStatus.GEBORGEN) {
      throw new IllegalStateException("Geborgene Netze können nicht als verschollen gemeldet werden.");
    }
    if (g.getStatus() == GhostNetStatus.VERSCHOLLEN) {
      return; // idempotent
    }

    Person reporter = persons.findById(reporterId)
        .orElseThrow(() -> new IllegalArgumentException("Meldende Person nicht gefunden: " + reporterId));
    if (reporter.getPhone() == null || reporter.getPhone().isBlank()) {
      throw new IllegalArgumentException("Verschollen-Meldung erfordert eine Person mit Telefonnummer (nicht anonym).");
    }

    g.setStatus(GhostNetStatus.VERSCHOLLEN);
    
  }
}
