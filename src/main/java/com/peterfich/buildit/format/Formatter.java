package com.peterfich.buildit.format;

import com.peterfich.buildit.domain.Connection;

import java.util.List;

public interface Formatter {
    void format(List<Connection> connections, String startPath);
}
