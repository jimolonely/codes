package com.jimo.tool;

import com.beust.jcommander.JCommander;
import com.jimo.tool.arg.CmdMerge;

/**
 * @author jimo
 * @version 1.0.0
 * @date 2020/10/12 21:33
 */
public class ToolMain {

    public static void main(String[] args) {

        final CmdMerge cmdMerge = new CmdMerge();
        final JCommander cmd = JCommander.newBuilder()
                .addObject(new ToolMain())
                .addCommand("merge", cmdMerge)
                .addCommand("delete", cmdMerge)
                .build();

        cmd.parse(args);
    }
}
