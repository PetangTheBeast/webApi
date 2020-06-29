package kurzlistky;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

/**
 * Servlet implementation class KurzListkyServlet
 */
@WebServlet("/KurzListkyServlet")
public class KurzListkyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public KurzListkyServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String par = request.getParameter("attribute");
		boolean b = Boolean.parseBoolean(par);
		System.out.println(par +" vs "+ b);
		if(par.equals("true")) {
			List<KurzListky> klArr = new DatabseHandler().getDataFromDB();
			ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
			String fJson = "[\n";
			for(KurzListky kl : klArr) {
				
				String json = ow.writeValueAsString(kl);
				fJson +=json +",";
			}
			fJson = fJson.substring(0,fJson.length()-1);
			fJson += "\n]";
			response.setCharacterEncoding("UTF-8");
			response.getWriter().append(fJson);
			
		}
		else if(par.equals("false")) {
			DatabseHandler dh = new DatabseHandler();
			List<KurzListky> klArr = dh.getDataFromAPI();
			dh.updateDatabase(klArr);
			klArr = new DatabseHandler().getDataFromDB();
			ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
			String fJson = "[\n";
			for(KurzListky kl : klArr) {
				
				String json = ow.writeValueAsString(kl);
				fJson +=json +",";
			}
			System.out.println(fJson.length());
			fJson = fJson.substring(0, fJson.length()-1);
			
			System.out.println(fJson.length());
			fJson += "\n]";
			response.setCharacterEncoding("UTF-8");
			response.getWriter().append(fJson);
		}
		else {
			response.getWriter().append("Wrong attribute value");
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("lolece");
		doGet(request, response);
	}

}
