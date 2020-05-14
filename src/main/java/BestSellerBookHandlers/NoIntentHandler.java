package BestSellerBookHandlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;

import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

public class NoIntentHandler implements RequestHandler {
    @Override
    public boolean canHandle(HandlerInput handlerInput) {
        return handlerInput.matches(intentName("NoIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput handlerInput) {
        String speechOutput = "GoodBye!";
        return handlerInput.getResponseBuilder()
                .withSpeech(speechOutput)
                .withShouldEndSession(true)
                .build();
    }
}
