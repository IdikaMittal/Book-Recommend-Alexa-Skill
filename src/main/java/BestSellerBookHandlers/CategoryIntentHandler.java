package BestSellerBookHandlers;

import com.amazon.ask.attributes.AttributesManager;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import static com.amazon.ask.request.Predicates.intentName;
import com.amazon.ask.model.*;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CategoryIntentHandler implements RequestHandler {
    private static final String ITEM_SLOT = "Item";

    @Override
    public boolean canHandle(HandlerInput handlerInput) {
        return handlerInput.matches(intentName("CategoryIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput handlerInput) {

        Request request = handlerInput.getRequestEnvelope().getRequest();
        IntentRequest intentRequest = (IntentRequest) request;
        Intent intent = intentRequest.getIntent();
        Map<String, Slot> slots = intent.getSlots();
        Slot bookCategoryAsked = slots.get(ITEM_SLOT);

        AttributesManager attributesManager = handlerInput.getAttributesManager();
        Map<String, Object> attributes = attributesManager.getSessionAttributes();
        attributes.put("bookCategoryAsked", bookCategoryAsked.getValue());
        attributes.put("SameAskedCount", 0);
        attributesManager.setSessionAttributes(attributes);

        if (bookCategoryAsked != null && bookCategoryAsked.getValue() != null) {
            String categoryName = bookCategoryAsked.getValue();

            AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
            DynamoDBMapper mapper = new DynamoDBMapper(client);
            BestSellerBookHandlers.BookCatalog partitionKey = new BestSellerBookHandlers.BookCatalog();
            partitionKey.setCategory(bookCategoryAsked.getValue());
            DynamoDBQueryExpression<BestSellerBookHandlers.BookCatalog> queryExpression = new DynamoDBQueryExpression<BestSellerBookHandlers.BookCatalog>()
                    .withHashKeyValues(partitionKey);
            List<BestSellerBookHandlers.BookCatalog> itemList = mapper.query(BestSellerBookHandlers.BookCatalog.class, queryExpression);
            String bookNameAsked = itemList.get(0).getbookName();

            if (bookNameAsked != null) {
                String speechOutput = "The bestseller book in category " + categoryName +" is " + bookNameAsked.toString() + ". Do you want more bestsellers? ";
                return handlerInput.getResponseBuilder()
                        .withSpeech(speechOutput)
                        .withShouldEndSession(false)
                        .build();
            } else {
                String speechOutput =
                        "I'm sorry, I currently do not know the bestseller for the category " + categoryName + ". Do you want more bestsellers? ";
                return handlerInput.getResponseBuilder()
                        .withSpeech(speechOutput)
                        .withShouldEndSession(false)
                        .build();
            }
        }
        return Optional.empty();
    }

}
