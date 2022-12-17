package com.chandler.aoc.year22;

import com.chandler.aoc.common.Day;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Long.parseLong;

public class Day07 extends Day {

    public static void main(String[] args) {
        new Day07().printParts();
    }

    private final Map<String, Directory> directories = new HashMap<>();
    private static final long TOTAL_DISK_SPACE = 70_000_000L;
    private static final long UNUSED_SPACE = 30_000_000L;

    private record File(String name, long size) {}

    private record Directory(String directoryName, List<File> files) {}

    @Override
    protected Object part1() {
        String[] lines = dayString().split("\r\n");

        processInput(lines);

        return directories.values()
                          .stream()
                          .map(directory -> this.getTotalSize(directory, 0L))
                          .filter(totalSize -> totalSize <= 100_000L)
                          .reduce(Long::sum)
                          .orElseThrow();
    }

    private void processInput(String[] lines) {
        Directory rootDirectory = new Directory("", new ArrayList<>());
        directories.put("/", rootDirectory);
        Directory currentDirectory = rootDirectory;
        for (String line : lines) {
            if (line.startsWith("$ cd")) {
                currentDirectory = changeDirectory(currentDirectory, line);
            } else if (!line.startsWith("$ ls")) {
                addFile(currentDirectory, line);
            }
        }
    }

    private long getTotalSize(Directory directory, long totalSize) {
        List<File> files = directory.files();
        for (File file : files) {
            totalSize += file.size() != -1L
                ? file.size()
                : getTotalSize(directories.get(file.name()), 0L);
        }
        return totalSize;
    }

    private void addFile(Directory currentDirectory, String line) {
        if (line.startsWith("dir")) {
            String directoryName = line.substring(4);
            Directory directory = new Directory("%s/%s".formatted(currentDirectory.directoryName(), directoryName), new ArrayList<>());
            String path = "%s/%s".formatted(currentDirectory.directoryName(), directoryName);
            directories.put(path, directory);
            currentDirectory.files().add(new File(path, -1));
        } else {
            String[] fileInfo = line.split(" ");
            currentDirectory.files().add(new File(fileInfo[1], parseLong(fileInfo[0])));
        }
    }

    private Directory changeDirectory(Directory currentDirectory, String line) {
        String inputDirectory = line.substring(5);
        if (inputDirectory.equals("/")) {
            return directories.get("/");
        }
        if (inputDirectory.equals("..")) {
            Directory directory = directories.get(currentDirectory.directoryName().substring(0, currentDirectory.directoryName().lastIndexOf('/')));
            return directory == null ? directories.get("/") : directory;
        }
        return directories.get("%s/%s".formatted(currentDirectory.directoryName(), inputDirectory));
    }

    @Override
    protected Object part2() {
        String[] lines = dayString().split("\r\n");
        directories.clear();

        processInput(lines);

        long rootSize = getTotalSize(directories.get("/"), 0L);
        long freeSpace = TOTAL_DISK_SPACE - rootSize;
        long necessarySpace = UNUSED_SPACE - freeSpace;

        return directories.values()
                          .stream()
                          .filter(directory -> !directory.directoryName().equals(""))
                          .map(directory -> getTotalSize(directory, 0L))
                          .filter(size -> size >= necessarySpace)
                          .min(Long::compareTo)
                          .orElseThrow();
    }

}
