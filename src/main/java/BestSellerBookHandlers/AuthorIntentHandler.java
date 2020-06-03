package BestSellerBookHandlers;

import com.amazon.ask.attributes.AttributesManager;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
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
import static com.amazon.ask.request.Predicates.intentName;

public class AuthorIntentHandler implements RequestHandler {
    private static final String AUTHOR_SLOT = "Author";

    @Override
    public boolean canHandle(HandlerInput handlerInput) {
        return handlerInput.matches(intentName(Constants.AUTHOR_INTENT));
    }

    @Override
    public Optional<Response> handle(HandlerInput handlerInput) {

        Intent intent = ((IntentRequest) handlerInput.getRequestEnvelope().getRequest()).getIntent();
        Map<String, Slot> slots = intent.getSlots();
        Slot AuthorAsked = slots.get(AUTHOR_SLOT);

        AttributesManager attributesManager = handlerInput.getAttributesManager();
        Map<String, Object> attributes = attributesManager.getSessionAttributes();
        String bookCategoryAsked = attributes.get("bookCategoryAsked").toString();
        String AuthorNameAsked = AuthorAsked.getValue();
        String Budget = attributes.get("Budget").toString();

        if (AuthorAsked != null && AuthorAsked.getValue() != null) {

            AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
            DynamoDBMapper mapper = new DynamoDBMapper(client);
            Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
            eav.put(":val1", new AttributeValue().withN(Budget));
            eav.put(":val2", new AttributeValue().withS(bookCategoryAsked));
            eav.put(":val3", new AttributeValue().withS(AuthorNameAsked));

            DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                    .withFilterExpression("Price <= :val1 and Category = :val2 and Author_Name = :val3").withExpressionAttributeValues(eav);
            List<BookCatalog> scanResult = mapper.scan(BookCatalog.class, scanExpression);
            int count = 0;
            for (BookCatalog book : scanResult) {
                count = count + 1;
            }
            if (count > 1) {

                String speechOutput1 = Constants.BOOK_NAME_MESSAGE;
                for (BookCatalog book : scanResult) {
                    speechOutput1 = speechOutput1 + book.getBook_Name() + " by author " + book.getAuthor_Name() + " with a cost of Rs." + book.getPrice() + ", ";
                }
                speechOutput1 = speechOutput1 + Constants.WANT_BOOK_INFO_MESSAGE;
                return handlerInput.getResponseBuilder()
                        .withSpeech(speechOutput1)
                        .withShouldEndSession(false)
                        .build();

            } else if (count == 1) {
                String speechOutput2 = Constants.BOOK_NAME_MESSAGE;
                String BookDescription = "";
                for (BookCatalog book : scanResult) {
                    speechOutput2 = speechOutput2 + book.getBook_Name() + " by author " + book.getAuthor_Name() + " with a cost of Rs." + book.getPrice() + ".";
                    BookDescription = book.getBookDescription();
                }
                attributes.put("BookDesciption", BookDescription);
                attributesManager.setSessionAttributes(attributes);
                speechOutput2 = speechOutput2 + Constants.WANT_MORE_BOOK_INFO_MESSAGE;
                return handlerInput.getResponseBuilder()
                        .withSpeech(speechOutput2)
                        .withShouldEndSession(false)
                        .build();
            } else {
                String speechOutput3 = Constants.NO_BOOK_FOUND_MESSAGE;
                return handlerInput.getResponseBuilder()
                        .withSpeech(speechOutput3)
                        .withShouldEndSession(false)
                        .build();
            }

        }
        else{
            return handlerInput.getResponseBuilder()
                    .withSpeech(Constants.PROVIDE_AUTHOR_MESSAGE)
                    .withShouldEndSession(false)
                    .build();
        }
    }
}
