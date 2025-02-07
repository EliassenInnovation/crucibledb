package com.Lightwell.dbtesting.common.main;

import com.eliassen.crucible.common.helpers.FileHelper;
import com.Lightwell.dbtesting.common.helpers.DriverHelper;
import com.Lightwell.dbtesting.common.helpers.Logger;
import com.Lightwell.dbtesting.common.helpers.QueryHelper;
import com.Lightwell.dbtesting.common.helpers.QueryTable;
import com.Lightwell.dbtesting.common.objects.DBInfo;
import com.Lightwell.dbtesting.common.objects.DBType;
import com.Lightwell.dbtesting.common.objects.ObjectTable;
import com.Lightwell.dbtesting.common.objects.Results;
import com.Lightwell.dbtesting.common.workers.DataWorker;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DBObject
{
    private String logFileDir = "logs";
    private DateFormat df = new SimpleDateFormat("YYYYMMdd");
    private DataWorker dataWorker;
    public static String dbName = "pdb1";
    public static String usersJSONPath = "users.json";
    public static String configFilePath = "config.json";

    private ObjectTable objectTable;

    private ObjectTable getObjects()
    {
        if(objectTable == null)
        {
            objectTable = new ObjectTable();
        }

        return objectTable;
    }

    public DBObject()
    {
        dataWorker = null;
        objectTable = null;
    }

    public DBObject(DBInfo info)
    {
        dataWorker = new DataWorker(info);
        objectTable = null;
    }

    public DBObject(DBType dbType, String _hostname, String _dbName, String _userName, String _password)
    {
        DBInfo info = DriverHelper.getInfo(dbType);
        info.setHostName(_hostname);
        info.setDbName(_dbName);
        info.setPassword(_password);
        info.setUserName(_userName);

        dataWorker = new DataWorker(info);
        objectTable = null;
    }

    public DBObject(DBType dbType, String _hostname, String _dbName, String _userName, String _password, boolean _activeDirectory)
    {
        DBInfo info = DriverHelper.getInfo(dbType);
        info.setHostName(_hostname);
        info.setDbName(_dbName);
        info.setPassword(_password);
        info.setUserName(_userName);
        info.setActiveDirectory(_activeDirectory);

        dataWorker = new DataWorker(info);
        objectTable = null;
    }

    public DBObject(DBType dbType, String _hostname, String _dbName, boolean _activeDirectory)
    {
        DBInfo info = DriverHelper.getInfo(dbType);
        info.setHostName(_hostname);
        info.setDbName(_dbName);
        info.setActiveDirectory(_activeDirectory);

        dataWorker = new DataWorker(info);
        objectTable = null;
    }

    public DBObject(DBType dbType, String _tnsEntryName, String _userName, String _password)
    {
        DBInfo info = DriverHelper.getInfo(dbType);
        info.setPassword(_password);
        info.setUserName(_userName);
        info.setTnsEntryName(_tnsEntryName);

        dataWorker = new DataWorker(info);
        objectTable = null;
    }

    private String GetLogFileName()
    {
        return df.format(new Date()) + "_dbtesting_Log.txt";
    }

    public Connection getConnection()
    {
        return dataWorker.getConnection();
    }

    public void shutDownDb(){dataWorker.shutDownDb();}

    public void closeConnection()
    {
        dataWorker.closeConnection();
    }

    public ArrayList<Results> executeQueryByName(String queryName) throws SQLException {
        return dataWorker.executeQueryByName(queryName);
    }

    public void executeNonqueryByName(String queryName)
    {
        dataWorker.executeNonqueryByName(queryName);
    }

    public void executeNonqueryByNameWithParameter(String queryName, String parameter)
    {
        Object[] paramters = {parameter};
        executeNonqueryByNameWithParameters(queryName, paramters);
    }

    public void executeNonqueryByNameWithParameters(String queryName, Object[] parameters)
    {
        String formattedQuery = formatQueryWithParameters(queryName, parameters);
        dataWorker.executeNonquery(formattedQuery);
    }

    public void executeNonQueryFromFileWithParameters(String fileName, Object[] parameters)
    {
        String query = new FileHelper().getTextFileContent(fileName);
        String formattedQuery = String.format(query, parameters);
        dataWorker.executeNonquery(formattedQuery);
    }

    public ArrayList<Results> executeQueryByNameAndWhere(String queryName, String whereClause) throws SQLException {
        return dataWorker.executeQueryByNameAndWhere(queryName, whereClause);
    }

    public ArrayList<Results> executeQueryWithParameter(String queryName, String id) throws SQLException {
        Object[] parameters = new Object[] { id };

        return executeQueryWithParameters(queryName, parameters);
    }

    public ArrayList<Results> executeQueryWithParameters(String queryName, Object[] parameters) throws SQLException {
        String formattedQuery = formatQueryWithParameters(queryName, parameters);

        return dataWorker.executeQueryWithParameter(formattedQuery);
    }

    public String formatQueryWithParameters(String queryName, Object[] parameters)
    {
        String query = QueryTable.get(queryName);
        String formattedQuery = String.format(query, parameters);

        if(CentralCommand.getScenario().getSourceTagNames().contains(QueryHelper.LOG_QUERY_TAG))
        {
            logQuery(queryName, formattedQuery);
        }

        return formattedQuery;
    }

    public static void logQuery(String queryName, String query)
    {
        StringBuilder message = new StringBuilder();
        message.append("Query name: ");
        message.append(queryName);
        message.append("\n");
        message.append("Query: ");
        message.append(query);

        Logger.log(message.toString());
    }

    public void store(String key, String value)
    {
        if(getObjects().containsKey(key))
        {
            getObjects().replace(key, value);
        }
        else
        {
            getObjects().put(key, value);
        }
    }

    public String retrieve(String key)
    {
        return getObjects().get(key.toLowerCase());
    }
}
