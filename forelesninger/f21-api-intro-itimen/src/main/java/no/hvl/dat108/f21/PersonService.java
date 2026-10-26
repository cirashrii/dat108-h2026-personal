package no.hvl.dat108.f21;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class PersonService {

    private final Map<Long, Person> database = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public PersonService() {

        //Litt startdata - Første får id=1
        Long id = idGenerator.getAndIncrement();
        database.put(id, new Person(id, "Atle", "Patle"));

        id = idGenerator.getAndIncrement();
        database.put(id, new Person(id, "Per", "Viskeler"));
    }

    public List<Person> hentAlle() {
        return new ArrayList<>(database.values());
    }

    public Optional<Person> hentEtterId(Long id) {
        return Optional.ofNullable(database.get(id));
    }

    public Person opprett(Person nyPerson) {
        Long id = idGenerator.getAndIncrement();
        Person lagretPerson = new Person(id, nyPerson.fornavn(), nyPerson.etternavn());
        database.put(id, lagretPerson);
        return lagretPerson;
    }

    public Optional<Person> oppdater(Long id, Person oppdatertPerson) {
        if (!database.containsKey(id)) {
            return Optional.empty();
        }
        Person nyData = new Person(id, oppdatertPerson.fornavn(), oppdatertPerson.etternavn());
        database.put(id, nyData);
        return Optional.of(nyData);
    }

    public boolean slett(Long id) {
        return database.remove(id) != null;
    }
}
