package BestSellerBookHandlers;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName="Books")
public class BookCatalog {

    private Integer Book_ID;
    private String Author_Name;
    private String Book_Name;
    private String Category;
    private String BookDescription;
    private Integer Price;

    @DynamoDBHashKey(attributeName="Book_ID")
    public Integer getBook_ID() {return Book_ID; }
    public void setBook_ID(Integer Book_ID) { this.Book_ID = Book_ID; }

    @DynamoDBAttribute(attributeName="Price")
    public Integer getPrice() {return Price; }
    public void setPrice(Integer Price) { this.Price = Price; }

    @DynamoDBAttribute(attributeName="Author_Name")
    public String getAuthor_Name() { return Author_Name; }
    public void setAuthor_Name(String Author_Name) { this.Author_Name = Author_Name; }

    @DynamoDBAttribute(attributeName="Book_Name")
    public String getBook_Name() { return Book_Name; }
    public void setBook_Name(String Book_Name) { this.Book_Name = Book_Name; }

    @DynamoDBAttribute(attributeName="Category")
    public String getCategory() { return Category; }
    public void setCategory(String Category) { this.Category = Category; }

    @DynamoDBAttribute(attributeName="BookDescription")
    public String getBookDescription() { return BookDescription; }
    public void setBookDescription(String BookDescription) { this.BookDescription = BookDescription; }

}
