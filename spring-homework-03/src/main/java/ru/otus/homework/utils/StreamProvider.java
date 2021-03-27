package ru.otus.homework.utils;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

@Component
public class StreamProvider {
    private PrintStream out;
    private BufferedReader bf;
    private InputStream in;

    public StreamProvider() {
        this.out = new PrintStream(System.out);
        this.in = System.in;
        this.bf = new BufferedReader(new InputStreamReader(in));
    }

    public InputStream getIn() {
        return in;
    }

    public PrintStream getOut() {
        return out;
    }

    public BufferedReader getBf() {
        return bf;
    }

    public StreamProvider(PrintStream out, InputStream in) {
        this.out = out;
        this.in = in;
        this.bf = new BufferedReader(new InputStreamReader(in));
    }

    public void setOut(PrintStream out) {
        this.out = out;
    }

    public void setBf(BufferedReader bf) {
        this.bf = bf;
    }

    public void setIn(InputStream in) {
        this.in = in;
    }
}
