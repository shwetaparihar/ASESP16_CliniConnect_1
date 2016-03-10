

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;

/**
 * Servlet implementation class GetPatients
 */
@WebServlet("/GetPatientsTable")
public class GetPatientsTable extends HttpServlet {
	private static final long serialVersionUID = 1L;
    JSONObject info;
    JSONArray jArr;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetPatientsTable() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		info = new JSONObject();
		jArr = new JSONArray();
		MongoClientURI uri = new MongoClientURI("mongodb://admin:admin@ds023388.mlab.com:23388/clinic_db");
		MongoClient mongoClient = new MongoClient(uri);
		MongoDatabase db = mongoClient.getDatabase("clinic_db");
		
		FindIterable<Document> iterable = db.getCollection("test_patients").find();
				
		iterable.forEach(new Block<Document>() {
			
		    @Override
		    public void apply(final Document document) {
		        buildJSON(document);

		    } 
		});
		mongoClient.close();
		
		try {
			info.put("patReturn", "success");
			info.put("patients", jArr);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		response.setContentType("application/json");     
		PrintWriter out = response.getWriter();  
		out.print(info);
		out.flush();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	public void buildJSON(Document document){
		try {
			JSONObject info2 = new JSONObject();
			info2.put("name",document.get("name").toString());
			info2.put("date",document.get("date").toString());
			info2.put("dr",document.get("dr").toString());
			jArr.put(info2);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
