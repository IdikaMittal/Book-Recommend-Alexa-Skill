package BestSellerBookHandlers;
import java.util.Set;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName="Book")
public class BookCatalog {
    private String Category;
    private String bookName;
    @DynamoDBHashKey(attributeName="Category")
    public String getCategory() {return Category; }
    public void setCategory(String Category) { this.Category = Category; }

    @DynamoDBAttribute(attributeName="bookName")
    public String getbookName() { return bookName; }
    public void setbookName(String bookName) { this.bookName = bookName; }
}
