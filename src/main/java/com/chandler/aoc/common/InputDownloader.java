package com.chandler.aoc.common;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static java.lang.Integer.parseInt;
import static java.net.http.HttpResponse.BodyHandlers.ofFile;
import static java.time.Duration.ofSeconds;

public class InputDownloader {

    public static void main(String[] args) throws ParseException, IOException, InterruptedException {
        var options = new Options();
        options.addOption("y", "year", true, "The Advent of Code year");
        options.addOption("d", "day", true, "The Advent of Code day");
        options.addRequiredOption("c", "cookie", true, "Logged-in cookie session value");

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);

        var zonedDateTime = ZonedDateTime.now(ZoneId.of("America/New_York"));
        int currentYear = zonedDateTime.getYear();
        int currentDay = zonedDateTime.getDayOfMonth();

        int year = parseInt(cmd.getOptionValue("y", String.valueOf(currentYear)));
        int day = parseInt(cmd.getOptionValue("d", String.valueOf(currentDay)));
        String cookie = cmd.getOptionValue("c");

        String uri = "https://adventofcode.com/%d/day/%d/input".formatted(year, day);

        try (var client = HttpClient.newHttpClient()) {
            var request = HttpRequest.newBuilder()
                                     .uri(URI.create(uri))
                                     .setHeader("Content-Type", "text/plain")
                                     .setHeader("Cookie", "session=%s".formatted(cookie))
                                     .timeout(ofSeconds(5))
                                     .build();
            Path outputPath = Paths.get("src", "main", "resources", "%d-%02d.txt".formatted(year, day));
            client.send(request, ofFile(outputPath));
        }
    }

}
