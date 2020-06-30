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

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

public class DatabseHandler {
	public static final EntityManagerFactory EMF = 
            Persistence.createEntityManagerFactory("KurzovniListky");
	
	public List<KurzListky> getDataFromDB(){
		List<KurzListky> kl = new ArrayList<>();
		EntityManager em = EMF.createEntityManager();
        try{
            EntityTransaction et = em.getTransaction();
            TypedQuery<KurzListky> tqkl = em.createQuery("SELECT kl FROM Kurzovnilistky kl", KurzListky.class);
            for(KurzListky klpom : tqkl.getResultList()) {
            	kl.add(klpom);
            }
        }finally {
        	em.close();
        }
		
		return kl;
	}
	public static List<String> mySplit(String result){
        char[] chArr = result.toCharArray(); 
        int len = chArr.length;
        
        List<String> fArr = new ArrayList();
        String pom = "";
        boolean skip_next = false;
        for (char ch : chArr){
            if (!skip_next) {
                pom = pom + ch;
                if (ch == '}') {
                    skip_next = true;
                    fArr.add(pom);
                    //System.out.println(pom);
                    pom = "";
                }
            }
            else{
                skip_next = false;
            }        
        }
        //System.out.println(pom);
        return fArr;
    }
	public List<KurzListky> getDataFromAPI() {
		System.out.println("_____getDataFromAPI____");
		List<KurzListky> klArr = new ArrayList<>();
		try{
            //EntityTransaction et = em.getTransaction();
            
            
            String url = "https://www.csast.csas.cz/webapi/api/v2/rates/exchangerates?web-api-key=86d63706-3a9c-4762-bd7a-415651cc26f8";
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            //int resCode = con.getResponseCode();
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine())!= null){
                response.append(inputLine);
                //System.out.println(inputLine);
            }
            in.close();
            
            String responseString = response.toString();
            responseString = responseString.substring(1, responseString.length()-1);
            //System.out.println(responseString);
            List<String> myList = mySplit(responseString);
            System.out.println("**from jsonstring to object");
            for(String s : myList){
                //System.out.println(s);
                //JSONObject jsonObject = new JSONObject(s);
                System.out.println(s);
                
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
		EntityManager em = EMF.createEntityManager();
        try{
            EntityTransaction et = em.getTransaction();
            Query q = em.createQuery("DELETE FROM Kurzovnilistky");  
            et.begin();
            q.executeUpdate();
            et.commit();
            for(KurzListky klpom : klArr) {
            	et.begin();
            	em.persist(klpom);
            	et.commit();
            }
        }finally {
        	em.close();
        }
        
	}
}
