package com.dealertire.RightTurnFramework.Models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;

import org.apache.logging.log4j.LogManager;

import com.dealertire.RightTurnFramework.Utils.Environment;

//Class is experimental and not finished.
@SuppressWarnings("javadoc")
public class DB {
	
	private static DB instance;
	private static Environment env = Environment.DEMO;
	
	private String connectionString;
	private Connection connection;
	
	private DB() {
		
		String host;
		switch (env) {
			case DEMO:
				host = "qasqlsrv10";
				break;
			case DEV:
				host = "devsqlsrv10";
				break;
			case PROD:
			case STAGE:
				host = "stgsqlsrv10";
				break;
			default:
				throw new IllegalArgumentException("Unexpected environment.");
		}
		
		connectionString = "jdbc:jtds:sqlserver://" + host + "/DealerTireWeb;useNTLMv2=tru‌​e;domain=workgroup";
		
	}
	
	
	public void connect() throws SQLException, ClassNotFoundException {
		Class.forName("net.sourceforge.jtds.jdbc.Driver");
		LogManager.getLogger(this.getClass().getSimpleName()).info("Connecting to database: " + connectionString);
		connection = DriverManager.getConnection(connectionString);
	}
	
	public void disconnect() throws SQLException {
		if (connection != null) connection.close();
	}
	
	public ArrayList<Size> getSizesFromDB(Map<Size.Criteria, Boolean> criteriaList) throws SQLException {
		String queryString = "SELECT TOP 100 ";
					queryString += "T.TireSizeDescription,";
					queryString += "T.ServiceIndex,";
					queryString += " T.SpeedRating,";
					queryString += "T.RunFlatCode,";
					queryString += " P.ProgramID ";
				queryString += "FROM dbo.vw_Item_Properties_TIRE T ";
				queryString += "INNER JOIN dbo.vw_ProductMaster P ON T.ItemId = P.ItemId ";                                                
				queryString += "WHERE ";
				//DEBUG: 
				queryString += "ProgramID = 'BSA' AND ";
				queryString += "RunFlatCode <> '' ";
				
		if (criteriaList.containsKey(Size.Criteria.RUNFLAT)) {
			queryString += " AND P.ItemID IN (Select ItemID FROM dbo.vw_Item_Properties_TIRE WHERE RunFlatCode " 
						+ (criteriaList.get(Size.Criteria.RUNFLAT) ? "=" : "<>") +" 'NA') ";
		}
		
		if (criteriaList.containsKey(Size.Criteria.STAGGERED)) {
			//Where product is [not] the front of a staggered
			queryString += "AND	P.ItemID " + (criteriaList.get(Size.Criteria.STAGGERED) ? "" : "NOT ") + "IN ";
			queryString += "(SELECT FrontItemID FROM [DealerTireWeb].[dbo].[vw_CarlineCatalogStaggeredFitments]) ";
			
			
			//And product is [not] the back of a staggered
			queryString += "AND	P.ItemID " + (criteriaList.get(Size.Criteria.STAGGERED) ? "" : "NOT ") + "IN ";
			queryString += "(SELECT RearItemID FROM [DealerTireWeb].[dbo].[vw_CarlineCatalogStaggeredFitments]) ";
		}
		
		LogManager.getLogger(this.getClass().getSimpleName()).info("Running query: " + queryString);
		Statement stmt = connection.createStatement();
		stmt.execute(queryString);


		ResultSet rs = stmt.getResultSet();
		ArrayList<Size> sizes = new ArrayList<Size>();
		while(rs.next()) {
			String temp = rs.getString("TireSizeDescription").trim();
			temp += " " + rs.getString("ServiceIndex").trim() + rs.getString("SpeedRating").trim();
			sizes.add(new Size(temp));
		}
		
		return sizes;
	}
	
	public static void setEnvironment(Environment newEnv) {
		env = newEnv;
	}
	
	public static DB getInstance() {
		if (instance == null) {
			instance = new DB();
		}
		
		return instance;
	}
}
