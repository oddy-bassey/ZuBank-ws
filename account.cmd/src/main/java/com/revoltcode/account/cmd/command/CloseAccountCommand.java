package com.revoltcode.account.cmd.command;

import com.revoltcode.cqrs.core.command.BaseCommand;

public class CloseAccountCommand extends BaseCommand {

    public CloseAccountCommand(String id){
        super(id);
    }
}
