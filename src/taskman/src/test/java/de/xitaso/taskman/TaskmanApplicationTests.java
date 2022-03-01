package de.xitaso.taskman;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import de.xitaso.taskman.entities.Project;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class TaskmanApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void createProject() {
        var projectToCreate = new Project("testProject");
        projectToCreate.setDeadline(LocalDate.of(2022, 3, 1));
        projectToCreate.setDescription("Project description");

        var location = this.restTemplate.postForLocation("/projects", projectToCreate);

        var projectFromApi = this.restTemplate.getForObject(location, Project.class);

        assertThat(projectFromApi.getName()).isEqualTo(projectToCreate.getName());
        assertThat(projectFromApi.getDescription()).isEqualTo(projectToCreate.getDescription());
        assertThat(projectFromApi.getDeadline()).isEqualTo(projectToCreate.getDeadline());
        assertThat(projectFromApi.getTasks()).isEmpty();
    }

    @Test
    void contextLoads() {
    }

}
