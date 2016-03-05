package com.cable.app.exception;

import java.io.IOException;

import lombok.extern.log4j.Log4j;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;


@Log4j
public class MyResponseErrorHandler implements ResponseErrorHandler {
	
	@Override
    public void handleError(ClientHttpResponse response) throws IOException {
        log.error("Response error: { code : "+response.getStatusCode()+" , Message : "+ response.getStatusText() +" } ");
    }

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return RestUtil.isError(response.getStatusCode());
    }

}
