package de.xitaso.taskman;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import de.xitaso.taskman.model.Project;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class TaskmanApplicationTests {

    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void getProjects() {
        ResponseEntity<Project[]> projects = this.restTemplate.getForEntity("http://localhost:" + port + "/projects",
                Project[].class);
        assertThat(projects.getBody().length).isEqualTo(2);
    }

    @Test
    void contextLoads() {
    }

}
