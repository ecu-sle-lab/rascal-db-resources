package databaseConnectionsProject.MongoDBquerying.databaseConnectionsProject.MongoDBquerying;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class TestingInMain {
	public static void main(String[] args){
		testingUnified();
	}
	
	public static void testingMySQL(){
		String type = MySQLQuery.Builder.SELECT_SPECIFIC;
		String host = "localhost";
		String dbName = "feedback";
		String username = "sqluser";
		String password = "sqluserpw";
		MySQLQuery.Builder builder = new MySQLQuery.Builder(type, host, dbName, username, password); 
		builder.getColumn("summary");
		builder.setTable("comments");
		builder.getColumn("email");
		builder.where("id=1");
		builder.getColumn("comments");
		//builder.getColumn("one", "two", "three", "four", "five");
		MySQLQuery query = builder.build();
		System.out.println(query.toString());
		try {
			query.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void testingMongo(){
		MongoDBQuery.Builder builder = new MongoDBQuery.Builder();
        builder.setDatabase("test");
        builder.setCollection("insertTest");
        builder.getFields("i");
        builder.addSearchFilter("x", Operator.EQUAL_TO, 0);
        MongoDBQuery query = builder.build();
        List<Document> results = query.execute();
        for(Document current: results){
        	System.out.println(current);
        }
	}
	
	public static void testingUnifiedMySQLPart(){
		UnifiedBuilder builder = new UnifiedBuilder();
        String mysqlHost = "localhost";
		String mysqlDbName = "feedback";
		String mysqlUser = "sqluser";
		String mysqlPassword = "sqluserpw";
        builder.setMysqlConnectionInfo(mysqlHost, mysqlDbName, mysqlUser, mysqlPassword);
        builder.setTable("comments");
        builder.getAttribute("summary", "email", "comments");
        
		builder.addNumericalFilter("id", Operator.EQUAL_TO, 1, true);
		builder.addStringFilter("myuser", Operator.EQUAL_TO, "lars", true);
		builder.build(); 
		try {
			builder.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
		ResultSet rs = builder.getMySQL();
		
	}
	
	public static void testingUnifiedMongoPart(){
		UnifiedBuilder builder = new UnifiedBuilder();
		builder.setMongoConnectionInfo("", "test", "", "");
        
        builder.setCollection("insertTest");
        builder.getAttribute("i");
        builder.addNumericalFilter("x", Operator.EQUAL_TO, 0, true);
        builder.addNumericalFilter("i", Operator.LESS_THAN_OR_EQUAL_TO, 12, true);
        builder.build();
        try {
			builder.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
        List<Document> results = builder.getMongo();
        for(Document current: results){
        	System.out.println(current);
        }
	}
	
	public static void testingUnified(){
		testingUnifiedMySQLPart();
		testingUnifiedMongoPart();
	}
}
