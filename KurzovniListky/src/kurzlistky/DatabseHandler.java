package kurzlistky;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

public class DatabseHandler {
	public List<KurzListky> getDataFromDB(){
		List<KurzListky> kl = new ArrayList<>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			
			Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/KurzovniListkyDB?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "1234");
			Statement myStmt = myConn.createStatement();
			ResultSet myRs = myStmt.executeQuery("select * from kurzovnilistky");
			while(myRs.next()) {
				KurzListky klpom = new KurzListky();
				klpom.setShortName(myRs.getString("shortName"));
				klpom.setValidFrom(myRs.getString("validFrom"));
				klpom.setName(myRs.getString("name"));
				klpom.setCountry(myRs.getString("country"));
				klpom.setAmount(myRs.getInt("amount"));
				klpom.setValBuy(myRs.getDouble("valBuy"));
				klpom.setValSell(myRs.getDouble("valSell"));
				klpom.setValMid(myRs.getDouble("valMid"));
				klpom.setCurrBuy(myRs.getDouble("currBuy"));
				klpom.setCurrSell(myRs.getDouble("currSell"));
				klpom.setCurrMid(myRs.getDouble("currMid"));
				klpom.setMove(myRs.getDouble("move"));
				klpom.setCnbMid(myRs.getDouble("cnbMid"));
				klpom.setVersion(myRs.getDouble("version"));
				klpom.setEcbMid(myRs.getDouble("ecbMid"));
				kl.add(klpom);
				System.out.println(myRs.getString("name"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return kl;
	}
	public static List<String> mySplit(String result){
        char[] chArr = result.toCharArray(); 
        List<String> fArr = new ArrayList();
        String pom = "";
        boolean skip_next = false;
        for (char ch : chArr){
            if (!skip_next) {
                pom = pom + ch;
                if (ch == '}') {
                    skip_next = true;
                    fArr.add(pom);
                    pom = "";
                }
            }
            else{
                skip_next = false;
            }        
        }
        return fArr;
    }
	public List<KurzListky> getDataFromAPI() {
		List<KurzListky> klArr = new ArrayList<>();
		try{
            //EntityTransaction et = em.getTransaction();
            System.out.println("______Start_____");
            
            String url = "https://www.csast.csas.cz/webapi/api/v2/rates/exchangerates?web-api-key=86d63706-3a9c-4762-bd7a-415651cc26f8";
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            //int resCode = con.getResponseCode();
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine())!= null){
                response.append(inputLine);
                System.out.println(inputLine);
            }
            in.close();
            
            String responseString = response.toString();
            responseString = responseString.substring(1, responseString.length()-2);
            List<String> myList = mySplit(responseString);
            for(String s : myList){
                //System.out.println(s);
                //JSONObject jsonObject = new JSONObject(s);
                //System.out.println(jsonObject);
                
                Gson gson = new Gson();  
                KurzListky kl = gson.fromJson(s, KurzListky.class);  
                klArr.add(kl);
                
                
                //System.out.println(kl);
            }
            return klArr;
            
            
        }catch(Exception e){
            System.out.println(e);
        }
        
		return klArr;
	}
	public void updateDatabase(List<KurzListky> klArr) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/KurzovniListkyDB?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "1234");
			Statement myStmt = myConn.createStatement();
			myStmt.executeUpdate("DELETE from kurzovnilistky");
			
			//ResultSet myRs = myStmt.executeQuery("select * from kurzovnilistky");
			for(KurzListky kl : klArr)  {
				String insert = "INSERT INTO Kurzovnilistky " +
		                   "VALUES ("
		                   + "'"+kl.getShortName()+"', "
		                   + "'"+kl.getValidFrom()+"', "
                		   + "'"+kl.getName()+"', "
        				   + "'"+kl.getCountry()+"', "
						   + kl.getAmount()+", "
						   + kl.getValBuy()+", "
						   + kl.getValSell()+", "
						   + kl.getValMid()+", "
						   + kl.getCurrBuy()+", "
						   + kl.getCurrSell()+", "
						   + kl.getCurrMid()+", "
						   + kl.getMove()+", "
						   + kl.getCnbMid()+", "
						   + kl.getVersion()+", "
						   + kl.getEcbMid()
		                   + ")";
				System.out.println(insert);
				myStmt.executeUpdate(insert);
				
			}
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
}
