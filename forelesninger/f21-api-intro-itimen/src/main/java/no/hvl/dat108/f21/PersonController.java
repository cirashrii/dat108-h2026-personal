package no.hvl.dat108.f21;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/personer")
class PersonController {

    private final PersonService personService;

    public PersonController(PersonService ps) {
        this.personService = ps;
    }

    /*
     * Dette var det vi gjorde i timen. Løsningene er ikke komplette, men
     * er en skisse på hvordan ting implementeres. Spesielt feilhåndtering
     * er ikke gjort etter boken.
     *
     * Det er meningen at eksemplet fortsetter og fullføres i f22.
     */

    // Hent alle personer
    @GetMapping
    public List<Person> hentAllePersoner() {
        return personService.hentAlle();
    }

    // Hent person med id=?
    @GetMapping("/{id}")
    public Person hentEnPerson(@PathVariable long id) {
        Person p = personService.hentEtterId(id)
                .orElse(new Person(-1L, "N", "N"));
        return p;
    }

    // Legge inn ny person
    @PostMapping
    public ResponseEntity<Person> leggTilPerson(@RequestBody Person nyPerson) {
        Person lagretPerson = personService.opprett(nyPerson);

        // Siden vi ønsker å returnere '201 Created', pakker vi lagretPerson
        // inn i et respons-objekt på denne måten.
        return ResponseEntity.status(HttpStatus.CREATED).body(lagretPerson);
    }

    // Oppdatere person med id=?
    @PutMapping("/{id}")
    public Person oppdaterPerson(@PathVariable long id, @RequestBody Person p) {
        return personService.oppdater(id, p)
                .orElse(new Person(-1L, "N", "N"));
    }
}
