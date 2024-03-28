package com.rp.albumtracker.exception;

public class FileEmptyException extends SpringBootFileUploadException{
  public FileEmptyException(String message) {
    super(message);
}
}
