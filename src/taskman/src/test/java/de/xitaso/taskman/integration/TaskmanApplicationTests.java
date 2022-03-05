package de.xitaso.taskman.integration;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.UUID;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;

import de.xitaso.taskman.api.models.ProjectCreation;
import de.xitaso.taskman.api.models.ProjectDetails;
import de.xitaso.taskman.api.models.ProjectOverview;
import de.xitaso.taskman.api.models.ProjectUpdate;
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
        var projectToCreate = new ProjectCreation("testProject" + UUID.randomUUID().toString(), "Project description",
                LocalDate.of(2022, 3, 1));
        this.restTemplate.postForLocation("/projects", projectToCreate);
        this.restTemplate.postForLocation("/projects", projectToCreate);
        this.restTemplate.postForLocation("/projects", projectToCreate);

        var entity = this.restTemplate.getForEntity("/projects", ProjectOverview[].class);
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        var projects = Stream.of(entity.getBody()).filter(p -> p.getName().equals(projectToCreate.getName()));

        assertThat(projects.count()).isEqualTo(3);
    }

    @Test
    public void updateProject() {
        var projectToCreate = new ProjectCreation("testProject", "Project description", LocalDate.of(2022, 3, 1));
        var projectLocation = this.restTemplate.postForLocation("/projects", projectToCreate);

        var update = new ProjectUpdate("new description", LocalDate.of(2022, 4, 1), new Long[0]);
        this.restTemplate.put(projectLocation, update);

        var projectDetails = this.restTemplate.getForObject(projectLocation, ProjectDetails.class);

        assertThat(projectDetails.getDescription()).isEqualTo(update.getDescription());
        assertThat(projectDetails.getDeadline()).isEqualTo(update.getDeadline());
        assertThat(projectDetails.getTaskIds()).isEqualTo(update.getTaskIds());
    }

    @Test
    public void addTaskToProject() {
        var taskToCreate = new TaskCreation("My test task");
        var taskLocation = this.restTemplate.postForLocation("/tasks", taskToCreate);
        assertThat(taskLocation).isNotNull();

        var taskEntity = this.restTemplate.getForEntity(taskLocation, TaskDetails.class);
        assertThat(taskEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        var task = taskEntity.getBody();

        assertThat(task.getDescription()).isEqualTo(taskToCreate.getDescription());
        assertThat(task.getState()).isEqualTo(TaskState.ToDo);

        var projectToCreate = new ProjectCreation("testProject", "Project description", LocalDate.of(2022, 3, 1));
        var projectLocation = this.restTemplate.postForLocation("/projects", projectToCreate);
        var projectDetails = this.restTemplate.getForObject(projectLocation, ProjectDetails.class);

        var projectUpdate = new ProjectUpdate(projectDetails.getDescription(), projectDetails.getDeadline(),
                new Long[] { task.getID() });
        this.restTemplate.put(projectLocation, projectUpdate);

        var updatedProjectDetails = this.restTemplate.getForObject(projectLocation, ProjectDetails.class);

        assertThat(updatedProjectDetails.getTaskIds().length).isEqualTo(1);
        assertThat(updatedProjectDetails.getTaskIds()[0]).isEqualTo(task.getID());
    }

}
