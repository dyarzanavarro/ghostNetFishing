package ghostnetfishing.demo.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Person {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
  @NotBlank @Column(nullable = false) private String name;
  private String phone;
  @Column(nullable = false) private boolean rescuer; 
}