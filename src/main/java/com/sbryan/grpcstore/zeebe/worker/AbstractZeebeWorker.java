package com.sbryan.grpcstore.zeebe.worker;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.response.CompleteJobResponse;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.exception.ZeebeBpmnError;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * Abstract base class for implementing Zeebe workers.
 * Provides common functionality for processing jobs and handling errors.
 *
 * @param <R> the type of result returned by the worker
 */
@Slf4j
abstract class AbstractZeebeWorker<R> {


    /**
     * Executes the business logic for the job and returns the result.
     *
     * @param client the client used to interact with Zeebe
     * @param job    the activated job to process
     * @return the result of the business logic
     */
    abstract R process(final JobClient client, final ActivatedJob job);

    /**
     * Creates a map of result parameters to be passed to the next task in the workflow.
     *
     * @param result the result of the business logic
     * @param job    the activated job
     * @return a map of result parameters
     */
    abstract Map<String, Object> createResultParameters(R result, ActivatedJob job);

    /**
     * Handles the execution of the job, including processing and error handling.
     *
     * @param client the client used to interact with Zeebe
     * @param job    the activated job to process
     */
    void handle(final JobClient client, final ActivatedJob job) {
        try {
            log.info("Received task {} from process {} with id {}", job.getElementId(), job.getBpmnProcessId(),
                    job.getProcessInstanceKey());

            // Execute the main business logic
            var result = process(client, job);

            // Create parameters for completing the job
            var params = createResultParameters(result, job);
            if (params == null) {
                params = new HashMap<>(); // Инициализируем пустой Map, если params равен null
            }

            // Complete the job
            client.newCompleteCommand(job.getKey())
                    .variables(params)
                    .send()
                    .exceptionally(t -> {
                        // Handle errors during job completion
                        log.error("Could not complete job with id {}. Reason: {}", job.getProcessInstanceKey(), t.getMessage());

                        // Fail the job with an error message
                        client.newFailCommand(job.getKey())
                                .retries(2) // Устанавливаем количество ретраев
                                .errorMessage("Failed to complete job: " + t.getMessage())
                                .send()
                                .exceptionally(failException -> {
                                    log.error("Could not fail job with id {}. Reason: {}", job.getProcessInstanceKey(), failException.getMessage());
                                    throw new RuntimeException("Could not fail job: " + failException.getMessage(), failException);
                                });

                        throw new RuntimeException("Could not complete job: " + t.getMessage(), t);
                    })
                    .thenAccept(resultConsumer());  // Handle successful job completion

        } catch (RuntimeException ex) {
            // Handle exceptions during job execution
            log.error("Oops, job with key {} error: {} {}", job.getKey(), ex.getMessage(), ex);

            // Fail the job with an error message
            client.newFailCommand(job.getKey())
                    .retries(1) // Set the number of retries
                    .errorMessage("Job failed due to: " + ex.getMessage())
                    .send()
                    .exceptionally(t -> {
                        log.error("Could not fail job with id {}. Reason: {}", job.getProcessInstanceKey(), t.getMessage());
                        throw new RuntimeException("Could not fail job: " + t.getMessage(), t);
                    });

            throw new ZeebeBpmnError("unknownError", ex.getMessage(), Map.of());
        }
    }

    /**
     * Retrieves a parameter value from the job variables.
     *
     * @param paramName the name of the parameter to retrieve
     * @param job       the activated job
     * @param <T>       the type of the parameter value
     * @return the parameter value, or null if not found
     */
    <T> T getParameterValue(String paramName, ActivatedJob job) {
        var params = job.getVariablesAsMap();
        return (T) Optional.ofNullable(params).map(prs -> prs.get(paramName)).orElse(null);
    }

    /**
     * Provides a consumer for handling successful job completion responses.
     *
     * @return a consumer for successful job completion responses
     */
    Consumer<? super CompleteJobResponse> resultConsumer() {
        return t -> {
        };
    }


}
