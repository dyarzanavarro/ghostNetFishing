package ghostnetfishing.demo.web;

import ghostnetfishing.demo.service.GhostNetService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/nets")
public class GhostNetController {

  private final GhostNetService service;
  private final ObjectMapper mapper;
  public GhostNetController(GhostNetService service, ObjectMapper mapper) {
  this.service = service;
  this.mapper = mapper;
}

  @GetMapping
  public String list(Model model) {
    model.addAttribute("nets", service.listOpen());
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

   @GetMapping("/map")
  public String map(Model model) throws Exception {
    model.addAttribute("netsJson", mapper.writeValueAsString(service.listOpen()));
    return "nets-map";
  }
}
