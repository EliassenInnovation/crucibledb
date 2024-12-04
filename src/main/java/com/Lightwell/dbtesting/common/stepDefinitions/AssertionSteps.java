package com.Lightwell.dbtesting.common.stepDefinitions;

import com.Lightwell.common.helpers.Logger;
import com.Lightwell.common.helpers.SystemHelper;
import com.Lightwell.dbtesting.common.main.CentralCommand;
import com.Lightwell.dbtesting.common.objects.Results;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Then;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;

public class AssertionSteps
{
    @Then("^I verify the query returned \"(.*)\" rows$")
    public void iVerifyTheQueryReturnedRows(int numberOfRows)
    {
        Results results = CentralCommand.getResults();
        try
        {
            Assertions.assertTrue(results.size() == numberOfRows);
        }
        catch(AssertionError ae)
        {
            Logger.log("Number of rows returned: " + results.size());
            throw ae;
        }
    }

    @Then("^I verify the query returned at least one row$")
    public void iVerifyTheQueryReturnedAtLeastOneRow()
    {
        Assertions.assertTrue(CentralCommand.getResults().size() >= 1);
    }

    public static void checkEnvironmentAccess()
    {
        checkEnvironmentAccess(SystemHelper.getEnvironment());
    }

    public static void checkEnvironmentAccess(String dbName)
    {
        Scenario scenario = CentralCommand.getScenario();
        String environmentTag = "@" + dbName;
        ArrayList<String> tags = new ArrayList<>();
        tags.addAll(scenario.getSourceTagNames());

        boolean okToProceed = tags.contains(environmentTag);

        StringBuilder message = new StringBuilder();
        message.append(System.lineSeparator());
        message.append(System.lineSeparator());
        message.append("ENVIRONMENT TAG MISSING FOR: ");
        message.append(dbName);
        message.append(System.lineSeparator());
        message.append("EXITING TEST");
        message.append(System.lineSeparator());
        message.append("Please add ");
        message.append(environmentTag);
        message.append(" to this feature or scenario if it is OK to execute it against ");
        message.append(dbName);
        message.append(System.lineSeparator());

        Assertions.assertTrue(okToProceed,message.toString());
    }
}
