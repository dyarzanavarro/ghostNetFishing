package ghostnetfishing.demo.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ghost_net")
public class GhostNet {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable=false)
  private double latitude;

  @Column(nullable=false)
  private double longitude;

  @Column(nullable=false)
  private String sizeEstimate;

  @Enumerated(EnumType.STRING)
  @Column(nullable=false)
  private GhostNetStatus status = GhostNetStatus.GEMELDET;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name="reported_by_id")
  private Person reportedBy;      // optional

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name="assigned_rescuer_id")
  private Person assignedRescuer; 

  @Column(nullable=false)
  private LocalDateTime createdAt;

  @Column(nullable=false)
  private LocalDateTime updatedAt;

  @PrePersist void onCreate(){ createdAt = updatedAt = LocalDateTime.now(); }
  @PreUpdate  void onUpdate(){ updatedAt = LocalDateTime.now(); }

  // --- getters & setters ---
  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }

  public double getLatitude() { return latitude; }
  public void setLatitude(double latitude) { this.latitude = latitude; }

  public double getLongitude() { return longitude; }
  public void setLongitude(double longitude) { this.longitude = longitude; }

  public String getSizeEstimate() { return sizeEstimate; }
  public void setSizeEstimate(String sizeEstimate) { this.sizeEstimate = sizeEstimate; }

  public GhostNetStatus getStatus() { return status; }
  public void setStatus(GhostNetStatus status) { this.status = status; }

  public Person getReportedBy() { return reportedBy; }
  public void setReportedBy(Person reportedBy) { this.reportedBy = reportedBy; }

  public Person getAssignedRescuer() { return assignedRescuer; }
  public void setAssignedRescuer(Person assignedRescuer) { this.assignedRescuer = assignedRescuer; }

  public LocalDateTime getCreatedAt() { return createdAt; }
  public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

  public LocalDateTime getUpdatedAt() { return updatedAt; }
  public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
