package de.xitaso.taskman;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;

import de.xitaso.taskman.api.models.ProjectCreation;
import de.xitaso.taskman.api.models.ProjectDetails;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class TaskmanApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void createProject() {
        var projectToCreate = new ProjectCreation("testProject", "Project description", LocalDate.of(2022, 3, 1));

        var location = this.restTemplate.postForLocation("/projects", projectToCreate);

        var entity = this.restTemplate.getForEntity(location, ProjectDetails.class);
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        var projectFromApi = entity.getBody();

        assertThat(projectFromApi.getName()).isEqualTo(projectToCreate.getName());
        assertThat(projectFromApi.getDescription()).isEqualTo(projectToCreate.getDescription());
        assertThat(projectFromApi.getDeadline()).isEqualTo(projectToCreate.getDeadline());
    }

    @Test
    void contextLoads() {
    }

}
