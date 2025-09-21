package ghostnetfishing.demo.config;

import ghostnetfishing.demo.domain.*;
import ghostnetfishing.demo.repo.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class DevData {
  @Bean CommandLineRunner seed(PersonRepository people, GhostNetRepository nets) {
    return args -> {
      if (people.count() == 0) {
        var a = new Person(); a.setName("Team Alpha"); a.setPhone("+49 123 456"); a.setRescuer(true);
        var b = new Person(); b.setName("Team Beta");  b.setPhone("+41 44 123 45 67"); b.setRescuer(true);
        var r = new Person(); r.setName("Hinweisgeber:in A"); r.setRescuer(false);
        people.save(a); people.save(b); people.save(r);
      }
    };
  }
}
