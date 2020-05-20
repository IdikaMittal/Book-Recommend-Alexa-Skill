package BestSellerBookHandlers;

import com.amazon.ask.attributes.AttributesManager;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import java.util.Map;
import java.util.Optional;
import static com.amazon.ask.request.Predicates.intentName;

public class MoreIntentHandler implements RequestHandler {
    @Override
    public boolean canHandle(HandlerInput handlerInput) {
        return handlerInput.matches(intentName(Constants.MORE_INTENT));
    }

    @Override
    public Optional<Response> handle(HandlerInput handlerInput) {

        AttributesManager attributesManager = handlerInput.getAttributesManager();
        Map<String, Object> attributes = attributesManager.getSessionAttributes();
        String BookDescription = attributes.get("BookDesciption").toString();
        BookDescription = BookDescription + Constants.MORE_BESTSELLERS_MESSAGE;
        return handlerInput.getResponseBuilder()
                .withSpeech(BookDescription)
                .withShouldEndSession(false)
                .build();
    }
}
