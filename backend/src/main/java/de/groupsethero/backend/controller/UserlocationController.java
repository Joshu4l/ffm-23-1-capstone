package de.groupsethero.backend.controller;
import de.groupsethero.backend.exceptions.RadiusInKmTooSmallException;
import de.groupsethero.backend.models.Userlocation;
import de.groupsethero.backend.models.UserlocationDTO;
import de.groupsethero.backend.service.UserlocationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserlocationController {

    private final UserlocationService userlocationService;

    @PostMapping("/userlocations")
    @ResponseStatus(HttpStatus.CREATED)
    public Userlocation createUserlocation (@RequestBody UserlocationDTO userlocationDTO) throws RadiusInKmTooSmallException {
        return userlocationService.createUserlocation(userlocationDTO);
    }

}
