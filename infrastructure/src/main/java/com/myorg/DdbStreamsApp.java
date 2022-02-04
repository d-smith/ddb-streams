package com.myorg;

import software.amazon.awscdk.App;
import software.amazon.awscdk.Environment;
import software.amazon.awscdk.StackProps;

import java.util.Arrays;

public class DdbStreamsApp {
    public static void main(final String[] args) {
        App app = new App();

        Environment env = Environment.builder()
                .account(System.getenv("PA_ACCOUNT_NO"))
                .region(System.getenv("AWS_REGION"))
                .build();

        new DdbStreamsStack(app, "DdbStreamsStack", StackProps.builder()
                .env(env)
                .build(), false, true);

        app.synth();
    }
}

