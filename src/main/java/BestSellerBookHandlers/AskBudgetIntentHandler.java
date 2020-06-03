package BestSellerBookHandlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import java.util.Optional;
import static com.amazon.ask.request.Predicates.intentName;
import com.amazon.ask.model.Response;


public class AskBudgetIntentHandler implements RequestHandler {
    @Override
    public boolean canHandle(HandlerInput handlerInput) {
        return handlerInput.matches(intentName(Constants.BESTSELLER_INTENT));
    }

    @Override
    public Optional<Response> handle(HandlerInput handlerInput) {
        return handlerInput.getResponseBuilder()
                .withSpeech(Constants.BUDGET_MESSAGE)
                .withShouldEndSession(false)
                .build();
    }
}
