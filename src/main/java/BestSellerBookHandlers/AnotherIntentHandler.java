package BestSellerBookHandlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;

import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

public class AnotherIntentHandler implements RequestHandler {
    @Override
    public boolean canHandle(HandlerInput handlerInput) {
        return handlerInput.matches(intentName("AnotherIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput handlerInput) {
        String speechOutput = "Tell me the category in which you want the bestseller";
        return handlerInput.getResponseBuilder()
                .withSpeech(speechOutput)
                .withShouldEndSession(false)
                .build();
    }
}