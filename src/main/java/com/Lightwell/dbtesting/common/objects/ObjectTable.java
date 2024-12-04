package com.Lightwell.dbtesting.common.objects;

import java.util.Hashtable;

public class ObjectTable extends Hashtable<String, String>
{
    @Override
    public String put(String _key, String _value)
    {
    return super.put(_key.toLowerCase(), _value);
    }
}
