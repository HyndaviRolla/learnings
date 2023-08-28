 package com.learning.hello.controller;

 

import java.util.ArrayList;
import java.util.List;

 

public class Notice{
    private List<Notice> notices;

 

    public Notice(){
    this.notices = new ArrayList<>();
    }

 

    public void addNotice(Notice notice) {
        if (notices.size() < 6) 
                notices.add(notice);
        else {
            notices.remove(notices.get(0));
            notices.add(notice);
        }
    }

 

    public List<Notice> getAllNotices() {
         return notices;
    }
}
