package BestSellerBookHandlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;

import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

public class YesIntentHandler implements RequestHandler {
    @Override
    public boolean canHandle(HandlerInput handlerInput) {
        return handlerInput.matches(intentName("YesIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput handlerInput) {
        String speechOutput = "Do you want more bestsellers from the same category or another";
        return handlerInput.getResponseBuilder()
                .withSpeech(speechOutput)
                .withShouldEndSession(false)
                .build();
    }
}
