package ghostnetfishing.demo.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "person")
public class Person {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  @Column(nullable = false)
  private String name;

  private String phone;        // optional für Meldende

  @Column(nullable = false)
  private boolean rescuer;    

  // --- getters & setters ---
  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }

  public String getName() { return name; }
  public void setName(String name) { this.name = name; }

  public String getPhone() { return phone; }
  public void setPhone(String phone) { this.phone = phone; }

  public boolean isRescuer() { return rescuer; }
  public void setRescuer(boolean rescuer) { this.rescuer = rescuer; }
}
