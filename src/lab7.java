
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.JLabel;

public class lab7 {

	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					lab7 window = new lab7();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public lab7() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 648, 452);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		
		
		JTextArea textArea = new JTextArea();
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		textArea.setBounds(10, 51, 612, 129);
		frame.getContentPane().add(textArea);
		
		
		
		JButton btnRetriveData = new JButton("Retrive data JSON");
		btnRetriveData.setBounds(199, 11, 190, 23);
		frame.getContentPane().add(btnRetriveData);
		btnRetriveData.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				Thread thread = new Thread (new Runnable()
				{
					public void run()
					{
						
						String params = null;
						String strUrl = "https://jsonplaceholder.typicode.com/comments";
						JSONArray jsonObj;
						try {
							jsonObj = new JSONArray(makeHttpRequest(strUrl,"GET", params)) ;
							String strFromPHP = null;
							strFromPHP = jsonObj.toString();
							textArea.setText(strFromPHP);
							
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						
						
						
					}
				});
				thread.start();
	           
			}
		});
		
		
		textField = new JTextField();
		textField.setBounds(248, 205, 77, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		
		
		textField_1 = new JTextField();
		textField_1.setBounds(133, 273, 422, 20);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setBounds(133, 304, 422, 20);
		frame.getContentPane().add(textField_2);
		textField_2.setColumns(10);
		
		JLabel lblName = new JLabel("Name:");
		lblName.setBounds(46, 276, 77, 14);
		frame.getContentPane().add(lblName);
		
		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setBounds(46, 307, 77, 14);
		frame.getContentPane().add(lblEmail);

		JLabel lblBody = new JLabel("Body:");
		lblBody.setBounds(46, 342, 77, 14);
		frame.getContentPane().add(lblBody);
		
		JLabel postid = new JLabel("PostID:");
		postid.setBounds(170, 208, 46, 14);
		frame.getContentPane().add(postid);
		
		JLabel Id = new JLabel("ID:");
		Id.setBounds(170, 245, 64, 14);
		frame.getContentPane().add(Id);
		
		textField_3 = new JTextField();
		textField_3.setBounds(248, 242, 77, 20);
		frame.getContentPane().add(textField_3);
		textField_3.setColumns(10);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.setBounds(399, 221, 77, 23);
		frame.getContentPane().add(btnSearch);
		
		
		JTextArea textArea_1 = new JTextArea();
		textArea_1.setBounds(133, 337, 422, 48);
		frame.getContentPane().add(textArea_1);
		
		
		btnSearch.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				Thread thread = new Thread (new Runnable()
				{
					public void run()
					{
						String value1 = textField.getText();
						String value2 = textField_3.getText();
						String params = "postId="+value1+"&id="+value2;
						String strUrl = "https://jsonplaceholder.typicode.com/comments";
						JSONArray jsonObj;
						
						try {
							jsonObj = new JSONArray(makeHttpRequest(strUrl,"GET", params)) ;
							 
							JSONObject obj1 = jsonObj.getJSONObject(0);
							String name = obj1.getString("name");
							String email = obj1.getString("email");
							String body = obj1.get("body").toString();
							textField_1.setText(name);
							textField_2.setText(email);
							textArea_1.setText(body);
							
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						
					}
				});
				thread.start();
	           
			}
		});
	}
	
	public String makeHttpRequest(String strUrl, String method, String params) throws JSONException {
		InputStream is = null;
		String json = "";
		
		try {
			strUrl = strUrl+"?"+params;
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(strUrl);
			HttpResponse httpResponse = httpClient.execute(httpGet);
			HttpEntity httpEntity = httpResponse.getEntity();
			is = httpEntity.getContent();
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while((line = reader.readLine())!=null) 
				sb.append(line+"\n");
			is.close();
			json = sb.toString();
			
		}	catch (Exception ee) {
			ee.printStackTrace();
		}
		return json;
	}
}