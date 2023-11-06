package de.groupsethero.backend.controller;
import de.groupsethero.backend.exceptions.RadiusInKmTooSmallException;
import de.groupsethero.backend.models.Userlocation;
import de.groupsethero.backend.models.UserlocationDTO;
import de.groupsethero.backend.service.UserlocationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.NoSuchElementException;


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

    @GetMapping("/userlocations")
    @ResponseStatus(HttpStatus.OK)
    public List<Userlocation> getAllUserlocations () {
        return userlocationService.getAllUserlocations();
    }

    @GetMapping("/userlocations/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Userlocation getUserlocationById (@PathVariable String id) throws NoSuchElementException {
        return userlocationService.getUserlocationById(id);
    }

    @DeleteMapping("/userlocations/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String deleteUserlocationById (@PathVariable String id) {
        return userlocationService.deleteUserlocationById(id);
    }

    @PutMapping("/userlocations/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Userlocation updateUserlocationById (@PathVariable String id, @RequestBody Userlocation userlocation) {
        return userlocationService.updateUserlocationById(id, userlocation);
    }

}
