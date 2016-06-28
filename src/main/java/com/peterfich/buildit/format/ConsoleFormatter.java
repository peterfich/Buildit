package com.peterfich.buildit.format;

import com.peterfich.buildit.domain.Connection;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ConsoleFormatter implements Formatter {

    @Override
    public void format(List<Connection> connections, String startPath) {
        Set<String> uniqueFromPaths = connections.stream()
                .map(c -> c.getFrom())
                .collect(Collectors.toSet());

        for (String fromPath : uniqueFromPaths) {
            System.out.println("From: " + fromPath);
            connections.stream()
                    .filter(c -> c.getFrom().equals(fromPath))
                    .forEach(c -> System.out.println(" -> " + c.getTo()));
        }
    }
}
