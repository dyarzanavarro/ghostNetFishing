package ghostnetfishing.demo.web;

import ghostnetfishing.demo.service.GhostNetService;
import ghostnetfishing.demo.repo.PersonRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/nets")
public class GhostNetController {

  private final GhostNetService service;
  private final ObjectMapper mapper;
  private final PersonRepository persons;

  public GhostNetController(GhostNetService service, ObjectMapper mapper, PersonRepository persons) {
  this.service = service;
  this.mapper = mapper;
  this.persons = persons;
}

  @GetMapping
  public String list(Model model) {
    model.addAttribute("nets", service.listOpen());
    model.addAttribute("salvaged", service.listSalvaged());
    model.addAttribute("reporters", persons.findByPhoneIsNotNullOrderByNameAsc()); // für Verschollen-Form
    return "nets-list";
  }

  @GetMapping("/new")
  public String form() { return "nets-form"; }

  @PostMapping
  public String create(@RequestParam double latitude,
                       @RequestParam double longitude,
                       @RequestParam String sizeEstimate,
                       @RequestParam(required=false) Long reporterId,
                       @RequestParam(defaultValue="false") boolean anonymous) {
    service.report(latitude, longitude, sizeEstimate, reporterId, anonymous);
    return "redirect:/nets";
  }

  @GetMapping("/{id}/assign")
  public String assignForm(@PathVariable Long id, Model model) {
    model.addAttribute("netId", id);
    return "nets-assign";
  }

   @GetMapping("/map")
  public String map(Model model) throws Exception {
    model.addAttribute("netsJson", mapper.writeValueAsString(service.listOpen()));
    return "nets-map";
  }

  @PostMapping("/{id}/assign")
  public String assign(@PathVariable Long id, @RequestParam Long rescuerId) {
    service.assignRescuer(id, rescuerId);
    return "redirect:/nets";
  }

  @PostMapping("/{id}/salvage")
  public String salvage(@PathVariable Long id) {
    service.markSalvaged(id);
    return "redirect:/nets";
  }

  @PostMapping("/{id}/missing")
  public String markMissing(@PathVariable Long id,
                            @RequestParam Long reporterId,
                            RedirectAttributes ra) {
    try {
      service.markMissing(id, reporterId);
      ra.addFlashAttribute("message", "Netz #" + id + " wurde als verschollen gemeldet.");
    } catch (Exception e) {
      ra.addFlashAttribute("error", e.getMessage());
    }
    return "redirect:/nets";
  }

  
}
