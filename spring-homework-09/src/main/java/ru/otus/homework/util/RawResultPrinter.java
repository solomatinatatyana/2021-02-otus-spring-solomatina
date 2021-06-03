package ru.otus.homework.util;

import org.bson.Document;

public interface RawResultPrinter {

    void prettyPrintRawResult(Document document);
}
