package de.xitaso.taskman.unit;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;

import de.xitaso.taskman.databuilders.AddressBuilder;
import de.xitaso.taskman.entities.Goal;
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

        var systemUnderTest = new GoalPriorizationService();
        var result = systemUnderTest.findCriticalGoals(List.of(nonCriticalGoal, criticalGoal));

        assertThat(result).containsExactly(criticalGoal);
    }

}
