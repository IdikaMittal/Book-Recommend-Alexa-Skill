package BestSellerBookHandlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.*;

import java.util.Map;
import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;
import com.amazon.ask.model.Response;


public class BestsellerIntentHandler implements RequestHandler {
    @Override
    public boolean canHandle(HandlerInput handlerInput) {
        return handlerInput.matches(intentName("BestsellerIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput handlerInput) {
        String speechOutput = "Tell me the category from which you want the bestseller.";
        return handlerInput.getResponseBuilder()
                .withSpeech(speechOutput)
                .withShouldEndSession(false)
                .build();
    }
}
