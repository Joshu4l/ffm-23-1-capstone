package de.groupsethero.backend.controller;
import de.groupsethero.backend.models.UserlocationDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserlocationController {

    @PostMapping("/userlocations")
    public UserlocationDTO createUserlocation (@RequestBody UserlocationDTO userlocationDTO) {
        System.out.println(userlocationDTO);
        return userlocationDTO;

    }

}
