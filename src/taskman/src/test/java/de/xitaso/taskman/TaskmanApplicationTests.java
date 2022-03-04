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
import de.xitaso.taskman.api.models.ProjectOverview;
import de.xitaso.taskman.api.models.TaskCreation;
import de.xitaso.taskman.api.models.TaskDetails;
import de.xitaso.taskman.entities.TaskState;

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
    public void listProjects() {
        var projectToCreate = new ProjectCreation("testProject", "Project description", LocalDate.of(2022, 3, 1));
        this.restTemplate.postForLocation("/projects", projectToCreate);
        this.restTemplate.postForLocation("/projects", projectToCreate);
        this.restTemplate.postForLocation("/projects", projectToCreate);

        var entity = this.restTemplate.getForEntity("/projects", ProjectOverview[].class);
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        var projects = entity.getBody();

        assertThat(projects.length).isEqualTo(3);
    }

    @Test
    public void addTaskToProject() {
        var projectToCreate = new ProjectCreation("testProject", "Project description", LocalDate.of(2022, 3, 1));
        var projectLocation = this.restTemplate.postForLocation("/projects", projectToCreate);

        var taskToCreate = new TaskCreation("My test task");
//        var taskLocation = this.restTemplate.postForLocation(projectLocation.toString() + "/tasks", taskToCreate);
        var response = this.restTemplate.postForEntity(projectLocation.toString() + "/tasks", taskToCreate,
                Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        var taskLocation = response.getHeaders().getLocation();

        var taskEntity = this.restTemplate.getForEntity(taskLocation, TaskDetails.class);
        assertThat(taskEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        var task = taskEntity.getBody();

        assertThat(task.getDescription()).isEqualTo(taskToCreate.getDescription());
        assertThat(task.getState()).isEqualTo(TaskState.ToDo);
    }

}
