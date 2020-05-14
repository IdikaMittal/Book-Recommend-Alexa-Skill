package BestSellerBookHandlers;

import com.amazon.ask.attributes.AttributesManager;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

public class SameIntentHandler implements RequestHandler {
    @Override
    public boolean canHandle(HandlerInput handlerInput) {
        return handlerInput.matches(intentName("SameIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput handlerInput) {

        AttributesManager attributesManager = handlerInput.getAttributesManager();
        Map<String, Object> attributes = attributesManager.getSessionAttributes();
        String bookCategoryAsked = attributes.get("bookCategoryAsked").toString();

        int SameAskedCount = ((int)attributes.get("SameAskedCount")) + 1;
        attributes.put("SameAskedCount", SameAskedCount);
        String s = String.valueOf(SameAskedCount);
        String sameBookCategoryAsked = bookCategoryAsked + s;

        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
        DynamoDBMapper mapper = new DynamoDBMapper(client);
        BestSellerBookHandlers.BookCatalog partitionKey = new BestSellerBookHandlers.BookCatalog();
        partitionKey.setCategory(sameBookCategoryAsked);
        DynamoDBQueryExpression<BestSellerBookHandlers.BookCatalog> queryExpression = new DynamoDBQueryExpression<BestSellerBookHandlers.BookCatalog>()
                .withHashKeyValues(partitionKey);
        List<BestSellerBookHandlers.BookCatalog> itemList = mapper.query(BestSellerBookHandlers.BookCatalog.class, queryExpression);
        String bookNameAsked = itemList.get(0).getbookName();

        if (bookNameAsked != null) {
            String speechOutput = "Another bestseller in the category " + bookCategoryAsked + " is " + bookNameAsked + ". Do you want more bestsellers?";
            return handlerInput.getResponseBuilder()
                    .withSpeech(speechOutput)
                    .withShouldEndSession(false)
                    .build();
        } else {
            String speechOutput =
                    "I'm sorry, I currently do not know the bestseller for the category " + bookCategoryAsked + ". Do you want more bestsellers? ";
            return handlerInput.getResponseBuilder()
                    .withSpeech(speechOutput)
                    .withShouldEndSession(false)
                    .build();
        }
    }
}
