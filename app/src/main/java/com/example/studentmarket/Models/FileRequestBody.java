package com.example.studentmarket.Models;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;
import okio.Okio;
import okio.Source;

public class FileRequestBody extends RequestBody {
    private InputStream inputStream;

    private MediaType type;

    public FileRequestBody(InputStream openInputStream, String contentType) {
        this.inputStream = openInputStream;
        this.type = type;
    }

    public void FileRequestBody(InputStream inputStream, MediaType type) {

    }

    @Override
    public MediaType contentType() {
        return type;
    }

    @Override
    public long contentLength() throws IOException {
        return inputStream.available();
    }

    @Override
    public void writeTo(@NonNull BufferedSink sink) throws IOException {
        Source source = null;
        try {
            source = Okio.source(inputStream);
            sink.writeAll(source);
        } catch (Exception e) {
            if (source != null) {
                source.close();
            }
        }
    }
}
