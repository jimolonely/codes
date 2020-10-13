package com.jimo.tool.arg;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(commandDescription = "delete hbase table")
public class CmdDelete {
    @Parameter(names = "--zkQuorum", description = "eg: host1:2181,host2:2181", order = 0)
    public String zkQuorum;
    @Parameter(description = "eg: full table name or regex table name", order = 1)
    public String tablePattern;
    @Parameter(names = "isRegex", description = "default is false", order = 2)
    public boolean isRegex;
}
