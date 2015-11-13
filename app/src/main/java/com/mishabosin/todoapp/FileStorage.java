package com.mishabosin.todoapp;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class FileStorage {
    private static String TODO_FILE_NAME = "todo.txt";

    private File filesDir;

    public FileStorage(File filesDir) {
        this.filesDir = filesDir;
    }

    public ArrayList<String> readItems() {
        File todoFile = new File(this.filesDir, TODO_FILE_NAME);
        try {
            return new ArrayList<String>(FileUtils.readLines(todoFile));
        } catch (IOException e) {
            return new ArrayList<String>();
        }
    }

    public void writeItems(ArrayList<String> items) {
        File todoFile = new File(this.filesDir, TODO_FILE_NAME);
        try {
            FileUtils.writeLines(todoFile, items);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
