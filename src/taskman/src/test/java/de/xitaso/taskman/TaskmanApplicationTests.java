package de.xitaso.taskman;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import de.xitaso.taskman.model.Project;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class TaskmanApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void createProject() {
        // var project = new Project("testProject");
        // project.setDeadline(LocalDate.of(2022, 3, 1));
        // project.setDescription("Project description");
        // this.restTemplate.getRootUri()

        ResponseEntity<Project[]> projects = this.restTemplate.getForEntity("/projects",
                Project[].class);
        assertThat(projects.getBody().length).isEqualTo(2);
    }

    @Test
    void contextLoads() {
    }

}
