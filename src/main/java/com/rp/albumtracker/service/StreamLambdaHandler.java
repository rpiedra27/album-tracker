package com.rp.albumtracker.service;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;

import com.amazonaws.serverless.exceptions.ContainerInitializationException;
import com.amazonaws.serverless.proxy.spring.SpringBootLambdaContainerHandler;
import com.amazonaws.serverless.proxy.model.AwsProxyRequest;
import com.amazonaws.serverless.proxy.model.AwsProxyResponse;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.rp.albumtracker.AlbumTrackerApplication;

public class StreamLambdaHandler implements RequestStreamHandler {
  private static final SpringBootLambdaContainerHandler<AwsProxyRequest, AwsProxyResponse> handler;

  static {
      try {
          handler = SpringBootLambdaContainerHandler.getAwsProxyHandler(AlbumTrackerApplication.class);
      } catch (ContainerInitializationException e) {
          // if we fail here. We re-throw the exception to force another cold start
          e.printStackTrace();
          throw new RuntimeException("Could not initialize Spring framework", e);
      }
  }

  @Override
  public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context)
          throws IOException {
      handler.proxyStream(inputStream, outputStream, context);
  }
}
