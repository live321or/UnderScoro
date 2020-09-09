package com.samoylov.gameproject.nextversion;

public class GroupList implements RowType {
    private String groupName;
    public GroupList(String groupName){
        this.groupName=groupName;
    }

    public String getGroupName() {
        return groupName;
    }
}
