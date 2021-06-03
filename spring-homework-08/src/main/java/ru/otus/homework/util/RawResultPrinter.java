package ru.otus.homework.util;

import org.bson.Document;

public interface RawResultPrinter {
    @SuppressWarnings("unchecked")
    void prettyPrintRawResult(Document document);
}
