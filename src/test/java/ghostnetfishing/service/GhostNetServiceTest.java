package ghostnetfishing.demo.service;

import ghostnetfishing.demo.domain.*;
import ghostnetfishing.demo.repo.GhostNetRepository;
import ghostnetfishing.demo.repo.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;


@ExtendWith(MockitoExtension.class)
public class GhostNetServiceTest {

  @Mock GhostNetRepository nets;
  @Mock PersonRepository persons;

  GhostNetService service;

  @BeforeEach
  void setUp() {
    service = new GhostNetService(nets, persons);
  }

  @Test
  @DisplayName("report(): anonym -> Status GEMELDET, kein Reporter")
  void report_anonymous() {
    GhostNet saved = new GhostNet();
    // on Save das selbe Netz zurückgeben
    saved.setId(1L);
    willReturn(saved).given(nets).save(ArgumentMatchers.any(GhostNet.class));

    GhostNet res = service.report(10.0, 20.0, "Mittel", null, true);

    assertThat(res.getStatus()).isEqualTo(GhostNetStatus.GEMELDET);
    
    // Reporter ist null
    assertThat(res.getReportedBy()).isNull();
    then(nets).should().save(ArgumentMatchers.any(GhostNet.class));
  }

  @Test
  @DisplayName("report(): mit Reporter -> Referenz gesetzt, Status GEMELDET")
  void report_withReporter() {
    Person reporter = new Person();
    reporter.setId(7L);
    reporter.setName("Alice");
    willReturn(Optional.of(reporter)).given(persons).findById(7L);

    GhostNet saved = new GhostNet(); saved.setId(2L);
    willReturn(saved).given(nets).save(ArgumentMatchers.any(GhostNet.class));

    GhostNet res = service.report(1,2,"Klein", 7L, false);

    assertThat(res.getStatus()).isEqualTo(GhostNetStatus.GEMELDET);
    assertThat(res.getReportedBy()).isEqualTo(reporter);
  }

   @Test
  @DisplayName("assignRescuer(): Retter gesetzt, Status BERGUNG_BEVORSTEHEND")
  void assignRescuer_ok() {
    GhostNet g = new GhostNet();
    g.setId(3L);
    g.setStatus(GhostNetStatus.GEMELDET);
    willReturn(Optional.of(g)).given(nets).findById(3L);

    Person rescuer = new Person();
    rescuer.setId(9L);
    rescuer.setName("Team Alpha");
    rescuer.setRescuer(true);
    willReturn(Optional.of(rescuer)).given(persons).findById(9L);

    GhostNet res = service.assignRescuer(3L, 9L);

    assertThat(res.getAssignedRescuer()).isEqualTo(rescuer);
    assertThat(res.getStatus()).isEqualTo(GhostNetStatus.BERGUNG_BEVORSTEHEND);
  }

  @Test
  @DisplayName("assignRescuer(): bereits zugeordnet -> IllegalStateException")
  void assignRescuer_alreadyAssigned() {
    GhostNet g = new GhostNet();
    g.setId(4L);
    Person already = new Person(); already.setId(1L); already.setRescuer(true);
    g.setAssignedRescuer(already);
    willReturn(Optional.of(g)).given(nets).findById(4L);

    assertThatThrownBy(() -> service.assignRescuer(4L, 9L))
        .isInstanceOf(IllegalStateException.class)
        .hasMessageContaining("Bereits zugeordnet");
  }

  @Test
  @DisplayName("assignRescuer(): Person ist kein Retter -> IllegalArgumentException")
  void assignRescuer_notRescuer() {
    GhostNet g = new GhostNet(); g.setId(5L);
    willReturn(Optional.of(g)).given(nets).findById(5L);

    Person p = new Person(); p.setId(2L); p.setRescuer(false);
    willReturn(Optional.of(p)).given(persons).findById(2L);

    assertThatThrownBy(() -> service.assignRescuer(5L, 2L))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("keine bergende Person");
  }

  @Test
  @DisplayName("markSalvaged(): setzt Status GEBORGEN")
  void markSalvaged_ok() {
    GhostNet g = new GhostNet(); g.setId(6L);
    willReturn(Optional.of(g)).given(nets).findById(6L);

    GhostNet res = service.markSalvaged(6L);

    assertThat(res.getStatus()).isEqualTo(GhostNetStatus.GEBORGEN);
  }
}