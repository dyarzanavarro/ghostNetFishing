package ghostnetfishing.demo.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class GhostNet {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
  @Column(nullable=false) private double latitude;
  @Column(nullable=false) private double longitude;
  @Column(nullable=false) private String sizeEstimate;
  @Enumerated(EnumType.STRING) @Column(nullable=false)
  private GhostNetStatus status = GhostNetStatus.GEMELDET;
  @ManyToOne(fetch = FetchType.LAZY) private Person reportedBy;      
  @ManyToOne(fetch = FetchType.LAZY) private Person assignedRescuer; 
  @Column(nullable=false) private LocalDateTime createdAt;
  @Column(nullable=false) private LocalDateTime updatedAt;
  @PrePersist void onCreate(){ createdAt = updatedAt = LocalDateTime.now(); }
  @PreUpdate  void onUpdate(){ updatedAt = LocalDateTime.now(); }
  // getters/setters
}