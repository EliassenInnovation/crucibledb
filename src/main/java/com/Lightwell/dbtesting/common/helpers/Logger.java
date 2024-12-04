package com.Lightwell.dbtesting.common.helpers;

import com.Lightwell.dbtesting.common.main.CentralCommand;

public class Logger extends com.Lightwell.common.helpers.Logger
{
    public Logger()
    {

    }

    public static void log(String message)
    {
        if(CentralCommand.getScenario() != null)
        {
            CentralCommand.getScenario().log(message);
        }
        else
        {
            System.out.println(message);
        }
    }

    public static void log(String message, boolean logToScenario)
    {
        if(CentralCommand.getScenario() != null && logToScenario)
        {
            CentralCommand.getScenario().log(message);
        }
        else
        {
            System.out.println(message);
        }
    }
}
