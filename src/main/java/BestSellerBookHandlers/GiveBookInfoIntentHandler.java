package BestSellerBookHandlers;

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

public class GiveBookInfoIntentHandler implements RequestHandler {
    private static final String BOOK_SLOT = "BookName";
    @Override
    public boolean canHandle(HandlerInput handlerInput) {
        return handlerInput.matches(intentName(Constants.GIVE_BOOK_INFO_INTENT));
    }

    @Override
    public Optional<Response> handle(HandlerInput handlerInput) {

        Intent intent = ((IntentRequest) handlerInput.getRequestEnvelope().getRequest()).getIntent();
        Map<String, Slot> slots = intent.getSlots();
        Slot bookNameGiven = slots.get(BOOK_SLOT);


        if (bookNameGiven != null && bookNameGiven.getValue() != null) {
            String bookName = bookNameGiven.getValue();
            AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
            DynamoDBMapper mapper = new DynamoDBMapper(client);

                Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
                eav.put(":val1", new AttributeValue().withS(bookName));

                DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                        .withFilterExpression("Book_Name = :val1").withExpressionAttributeValues(eav);

                List<BookCatalog> scanResult = mapper.scan(BookCatalog.class, scanExpression);
                String BookDescription = null;
                for (BookCatalog BookInfo : scanResult) {
                    BookDescription = BookInfo.getBookDescription();
                }

                if (BookDescription != null) {
                    String speechOutput = BookDescription + Constants.MORE_BOOK_INFO_MESSAGE;
                    return handlerInput.getResponseBuilder()
                            .withSpeech(speechOutput)
                            .withShouldEndSession(false)
                            .build();
                } else {
                    String speechOutput = Constants.NO_INFO_FOUND_MESSAGE + bookName + Constants.MORE_BOOK_INFO_MESSAGE;
                    return handlerInput.getResponseBuilder()
                            .withSpeech(speechOutput)
                            .withShouldEndSession(false)
                            .build();
                }
        }
        else{
            {
                return handlerInput.getResponseBuilder()
                        .withSpeech(Constants.PROVIDE_BOOK_MESSAGE)
                        .withShouldEndSession(false)
                        .build();
            }
        }
    }
}
