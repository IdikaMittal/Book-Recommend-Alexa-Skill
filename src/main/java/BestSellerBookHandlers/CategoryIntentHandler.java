package BestSellerBookHandlers;

import com.amazon.ask.attributes.AttributesManager;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import static com.amazon.ask.request.Predicates.intentName;
import com.amazon.ask.model.*;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CategoryIntentHandler implements RequestHandler {
    private static final String CATEGORY_SLOT = "Category";

    @Override
    public boolean canHandle(HandlerInput handlerInput) {
        return handlerInput.matches(intentName(Constants.CATEGORY_INTENT));
    }

    @Override
    public Optional<Response> handle(HandlerInput handlerInput) {

        Intent intent = ((IntentRequest) handlerInput.getRequestEnvelope().getRequest()).getIntent();
        Map<String, Slot> slots = intent.getSlots();
        Slot bookCategoryAsked = slots.get(CATEGORY_SLOT);

        AttributesManager attributesManager = handlerInput.getAttributesManager();
        Map<String, Object> attributes = attributesManager.getSessionAttributes();
        attributes.put("bookCategoryAsked", bookCategoryAsked.getValue());
        attributesManager.setSessionAttributes(attributes);

        if (bookCategoryAsked != null && bookCategoryAsked.getValue() != null) {

            String categoryName = bookCategoryAsked.getValue();
            String categoryAuthorsAsked = categoryName + "Author";

            AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
            DynamoDBMapper mapper = new DynamoDBMapper(client);
            Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
            eav.put(":val1", new AttributeValue().withS(categoryAuthorsAsked));
            DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("Category = :val1").withExpressionAttributeValues(eav);

                List<BookCatalog> scanResult = mapper.scan(BookCatalog.class, scanExpression);
                String AuthorNames = null;
                for (BookCatalog Authors : scanResult) {
                    AuthorNames = Authors.getAuthor_Name();
                }

                if (AuthorNames != null) {
                    String speechOutput = Constants.SELECT_AUTHOR_MESSAGE + AuthorNames;
                    return handlerInput.getResponseBuilder()
                            .withSpeech(speechOutput)
                            .withShouldEndSession(false)
                            .build();
                } else {
                    String speechOutput = Constants.NOT_FOUND_CATEGORY_MESSAGE + categoryName + Constants.MORE_BESTSELLERS_MESSAGE;
                    return handlerInput.getResponseBuilder()
                            .withSpeech(speechOutput)
                            .withShouldEndSession(false)
                            .build();
                }
        }
        else
        {
            return handlerInput.getResponseBuilder()
                    .withSpeech(Constants.PROVIDE_CATEGORY_MESSAGE)
                    .withShouldEndSession(false)
                    .build();
        }
    }
}
