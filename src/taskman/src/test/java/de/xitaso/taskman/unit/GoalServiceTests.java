package de.xitaso.taskman.unit;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;

import de.xitaso.taskman.data.TaskRepository;
import de.xitaso.taskman.databuilders.AddressBuilder;
import de.xitaso.taskman.entities.Goal;
import de.xitaso.taskman.entities.Project;
import de.xitaso.taskman.entities.Task;
import de.xitaso.taskman.entities.TaskState;
import de.xitaso.taskman.entities.User;
import de.xitaso.taskman.services.GoalPriorizationService;

public class GoalServiceTests {

    @Test
    public void returnsCriticalGoal_whenRepsoniblePersonIsInMunich() {
        var userOfNonCriticalGoal = new User();
        userOfNonCriticalGoal.setName("Hans");
        userOfNonCriticalGoal.setAddress(new AddressBuilder().build());
        var nonCriticalGoal = new Goal();
        nonCriticalGoal.setResponsiblePerson(userOfNonCriticalGoal);

        var userOfCriticalGoal = new User();
        userOfCriticalGoal.setName("Hans");
        userOfCriticalGoal.setAddress(new AddressBuilder().inMunich().build());
        var criticalGoal = new Goal();
        criticalGoal.setResponsiblePerson(userOfCriticalGoal);

        var systemUnderTest = new GoalPriorizationService(new TaskRepository());
        var result = systemUnderTest.findCriticalGoals(List.of(nonCriticalGoal, criticalGoal));

        assertThat(result).containsExactly(criticalGoal);
    }

    @Test
    public void returnsCriticalGoal_whenNumberOfOpenTasksIsMoreThan50() {
        var user = new User();
        user.setAddress(new AddressBuilder().build());
        var nonCriticalGoal = new Goal();
        nonCriticalGoal.setResponsiblePerson(user);

        var taskrepository = new TaskRepository();
        var criticalGoal = new Goal();
        criticalGoal.setResponsiblePerson(user);
        var project1 = new Project("Project 1");
        for (int i = 0; i < 25; i++) {
            var task = new Task("task " + i);
            taskrepository.save(task);
            project1.addTask(task);
        }
        var project2 = new Project("Project 2");
        for (int i = 0; i < 25; i++) {
            var task = new Task("task " + i);
            task.setState(TaskState.Waiting);
            taskrepository.save(task);
            project2.addTask(task);
        }
        criticalGoal.addProject(project1);
        criticalGoal.addProject(project2);

        var systemUnderTest = new GoalPriorizationService(taskrepository);
        var result = systemUnderTest.findCriticalGoals(List.of(nonCriticalGoal, criticalGoal));

        assertThat(result).containsExactly(criticalGoal);
    }

    @Test
    public void returnsEmptyCollection_whenThereIsNoCriticalGoal() {
        var user = new User();
        user.setAddress(new AddressBuilder().build());
        var nonCriticalGoal = new Goal();
        nonCriticalGoal.setResponsiblePerson(user);

        var taskrepository = new TaskRepository();
        var criticalGoal = new Goal();
        criticalGoal.setResponsiblePerson(user);
        var project1 = new Project("Project 1");
        for (int i = 0; i < 25; i++) {
            var task = new Task("task " + i);
            task.setState(TaskState.Done);
            taskrepository.save(task);
            project1.addTask(task);
        }
        var project2 = new Project("Project 2");
        for (int i = 0; i < 25; i++) {
            var task = new Task("task " + i);
            taskrepository.save(task);
            project2.addTask(task);
        }
        criticalGoal.addProject(project1);
        criticalGoal.addProject(project2);

        var systemUnderTest = new GoalPriorizationService(taskrepository);
        var result = systemUnderTest.findCriticalGoals(List.of(nonCriticalGoal, criticalGoal));

        assertThat(result).isEmpty();
    }

}
