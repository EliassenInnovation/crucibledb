package com.Lightwell.dbtesting.common.helpers;

import com.eliassen.crucible.common.helpers.Functions;
import com.Lightwell.dbtesting.common.objects.ResultRow;
import com.Lightwell.dbtesting.common.objects.Results;

import java.util.ArrayList;

public class ListHelper
{
    public static ArrayList<Object> getColumnOfValuesFromResults(Results results, String columnName)
    {
        ArrayList<Object> returnList = new ArrayList<>();

        for(ResultRow row : results)
        {
            returnList.add(row.get(columnName));
        }

        return returnList;
    }

    public static String getListOfValuesAsSingleCSVValue(ArrayList<Object> valueList)
    {
        String csvOfValues = "";
        for(Object value : valueList)
        {
            if(csvOfValues.length() > 0)
            {
                csvOfValues += ",";
            }

            if(!Functions.isNumeric(value))
            {
                csvOfValues += "'" + value + "'";
            }
            else
            {
                csvOfValues += value;
            }
        }

        return csvOfValues;
    }
}
