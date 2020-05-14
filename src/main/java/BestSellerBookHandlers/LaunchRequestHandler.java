package BestSellerBookHandlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.LaunchRequest;
import com.amazon.ask.model.Response;

import java.util.Map;
import java.util.Optional;

import static com.amazon.ask.request.Predicates.requestType;

public class LaunchRequestHandler implements RequestHandler {
    public boolean canHandle(HandlerInput input) {
        return input.matches(requestType(LaunchRequest.class));
    }

    public Optional<Response> handle(HandlerInput handlerInput) {
        String speechText = "Welcome to Book Recommend Skill, what can I help you with?";
        return handlerInput.getResponseBuilder()
                .withSpeech(speechText)
                .withShouldEndSession(false)
                .build();
    }
}
