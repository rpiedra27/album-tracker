package com.rp.albumtracker.exception;

public class FileDownloadException extends SpringBootFileUploadException{
  public FileDownloadException(String message) {
    super(message);
}
}
