package ru.otus.hw.repositories;

import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import ru.otus.hw.models.Action;
import ru.otus.hw.models.Device;
import ru.otus.hw.models.Incident;
import ru.otus.hw.services.ActionTypeServiceImpl;
import ru.otus.hw.services.IncidentServiceImpl;

import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Репозиторий на основе JPA")
@DataJpaTest
@ComponentScan({"ru.otus.hw.converters", "ru.otus.hw.repositories"})
@Import({IncidentServiceImpl.class, ActionTypeServiceImpl.class})
class JpaIncidentRepositoryTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private IncidentRepository incidentRepository;

    @Autowired ActionRepository actionRepository;

    private List<Device> deviceExampleList;

    private List<Incident> incidentExampleList;

    @BeforeEach
    void setUp() {
        createTestIncidents();
    }

    @DisplayName("должен загружать инцидент по id")
    @Test
    void shouldReturnCorrectIncById() {
        Incident expected = incidentExampleList.get(0);
        var actual = incidentRepository.findById(expected.getId());

        assertThat(actual).isPresent()
                .get()
                .isEqualTo(expected);
    }

    @DisplayName("должен загружать список всех инцидентов")
    @Test
    void shouldReturnCorrectIncsList() {
        var actualIncidents = incidentRepository.findAll();
        var expectedIncidents = incidentExampleList;

        assertThat(actualIncidents).containsExactlyElementsOf(expectedIncidents);
        actualIncidents.forEach(System.out::println);
    }

    @DisplayName("должен сохранять новый инцидент")
    @Test
    void shouldSaveNewIncident() {
        var expectedIncident = new Incident(0, "Incident_10500", deviceExampleList.get(0));
        var returnedIncident = incidentRepository.save(expectedIncident);
        assertThat(returnedIncident).isNotNull()
                .matches(book -> book.getId() > 0)
                .usingRecursiveComparison().ignoringExpectedNullFields().isEqualTo(expectedIncident);

        assertThat(incidentRepository.findById(returnedIncident.getId()))
                .isPresent()
                .get()
                .isEqualTo(returnedIncident);
    }

    @DisplayName("должен сохранять измененный объект")
    @Test
    void shouldSaveUpdatedIncident() {
        var expectedIncident = new Incident(1L, "IncidentTitle_10500", deviceExampleList.get(2));

        assertThat(incidentRepository.findById(expectedIncident.getId()))
                .isPresent()
                .get()
                .isNotEqualTo(expectedIncident);

        var returnedIncident = incidentRepository.save(expectedIncident);
        assertThat(returnedIncident).isNotNull()
                .matches(incident -> incident.getId() > 0)
                .usingRecursiveComparison().ignoringExpectedNullFields().isEqualTo(expectedIncident);

        assertThat(incidentRepository.findById(returnedIncident.getId()))
                .isPresent()
                .get()
                .isEqualTo(returnedIncident);
    }

    @DisplayName("должен удалять инцидент по id ")
    @Test
    void shouldDeleteIncident() {
        assertNotNull(em.find(Incident.class, 1L));
        incidentRepository.deleteById(1L);
        assertNull(em.find(Incident.class, 1L));
        em.flush();
        assertNull(em.find(Incident.class, 1L));

    }

    @DisplayName("должен справляться с попыткой удалить несуществующий объект по id ")
    @Test
    void shouldThrowExceptionByDeleting() {
        assertNull(em.find(Incident.class, 0L));
        assertDoesNotThrow(()-> {
            incidentRepository.deleteById(0L);
        });
    }

    @DisplayName("должен вызывать исключение  ViolationException при попытке сохранить некорректные связи")
    @Test
    void shouldThrowViolationException() {
        Incident incident = em.find(Incident.class, 1L);
        assertNotNull(incident);

        var device = new Device("ID_NOT_EXIST", "xxx");
        incident.setDevice(device);

        assertThrows(IllegalStateException.class, () -> {
            incidentRepository.save(incident);
            em.flush();
        });
    }


    @DisplayName("должен загружать список действий по инциденту")
    @Test
    void shouldReturnCorrectActionsList() {
        Incident expectedIncident = em.find(Incident.class, 1L);

        var actualActions = actionRepository.findAllByIncidentId (expectedIncident.getId());
        var expectedIncidentActions = expectedIncident.getActions();

        assertThat(actualActions).containsExactlyElementsOf(expectedIncidentActions);
        actualActions.forEach(a -> System.out.println(a));
    }

    @DisplayName("должен загружать действие")
    @Test
    void shouldReturnCorrectAction() {
        var action = actionRepository.findById(1L);
        assertNotNull("what_do_1".equals(action.get().getWhatDo()));
    }


    @DisplayName("должен сохранять действие")
    @Test
    void shouldSaveNewAction() {
        var newAction = new Action(0, "do_nothing", 1L, 1L);
        var returnedAction = actionRepository.save(newAction);

        em.flush();

        assertThat(incidentRepository.findById(1L)).isPresent();
        assertThat(incidentRepository.findById(1L).get().getActions().contains(returnedAction));

    }

     @DisplayName("должен удалять действие")
    @Test
    void shouldDeleteAction() {
        assertNotNull(em.find(Incident.class, 1L));
        var incident = em.find(Incident.class, 1L);
        assertThat(incident.getActions().size() > 0);

        var action1  = incident.getActions().get(0);
        long actionId = action1.getId();
        em.detach(incident);

        actionRepository.deleteById(actionId);
        em.flush();

        assertNull(em.find(Action.class, actionId));
    }


    private static List<Device> createDevices() {
        return IntStream.range(1, 4).boxed()
                .map(id -> new Device("ID_Kamera_" + id, "Kamera_" + id))
                .toList();
    }

    public static final long ACTION_TYPE_1 = 1L;

    private List<Action> createActions() {
        return IntStream.range(1, 4).boxed()
                .map(id -> new Action( id, "what_do_" + id, 1L, ACTION_TYPE_1))
                .toList();
    }

    private List<Incident> createIncidents(List<Device> dbDevices) {
        return IntStream.range(1, 4).boxed()
                .map(id -> new Incident(id, "incident_" + id, dbDevices.get(id - 1)))
                .toList();
    }

    private void createTestIncidents() {
        deviceExampleList = createDevices();
        var actions = createActions();
        incidentExampleList = createIncidents(deviceExampleList);
        incidentExampleList.get(0).setActions(actions);
    }
}