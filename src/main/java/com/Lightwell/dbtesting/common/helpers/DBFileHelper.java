package com.Lightwell.dbtesting.common.helpers;

import com.eliassen.crucible.common.helpers.FileHelper;

public class DBFileHelper extends FileHelper
{
    public void PutTnsFileAtRoot()
    {
        String tnsFileName = "tnsnames.ora", permissions = "600";

        ExtractFile(tnsFileName, permissions);
    }
}
