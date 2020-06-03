package BestSellerBookHandlers;

import com.amazon.ask.attributes.AttributesManager;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.*;
import java.util.Map;
import java.util.Optional;
import static com.amazon.ask.request.Predicates.intentName;

public class BudgetIntentHandler implements RequestHandler {
    private static final String PRICE_SLOT = "Price";

    @Override
    public boolean canHandle(HandlerInput handlerInput) {
        return handlerInput.matches(intentName(Constants.BUDGET_INTENT));
    }

    @Override
    public Optional<Response> handle(HandlerInput handlerInput) {

        Intent intent = ((IntentRequest) handlerInput.getRequestEnvelope().getRequest()).getIntent();
        Map<String, Slot> slots = intent.getSlots();
        Slot MaxPrice = slots.get(PRICE_SLOT);

        AttributesManager attributesManager = handlerInput.getAttributesManager();
        Map<String, Object> attributes = attributesManager.getSessionAttributes();
        attributes.put("Budget", MaxPrice.getValue());
        attributesManager.setSessionAttributes(attributes);

        return handlerInput.getResponseBuilder()
                .withSpeech(Constants.ASK_CATEGORY_MESSAGE)
                .withShouldEndSession(false)
                .build();
    }
}
