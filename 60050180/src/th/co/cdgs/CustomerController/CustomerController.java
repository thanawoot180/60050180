package th.co.cdgs.CustomerController;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("customer")
public class CustomerController {
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCustomer() {
		List<CustomerDto> list = new ArrayList<>();
		ResultSet rs = null;
		CustomerDto customerDto = null; 
		PreparedStatement pst = null;
		Connection conn = null;
		try {
			conn = DriverManager.getConnection
				("jdbc:mysql://localhost:3306/workshop","root","p@ssw0rd");
			pst = conn.prepareStatement("SELECT customer_id,"
				+"CONCAT(first_name,' ',last_name)as full_name,"
				+"address,tel,email FROM customer");
			rs = pst.executeQuery();
			customerDto = null;
		while(rs.next()) {
			customerDto = new CustomerDto();
			customerDto.setCustomerId(rs.getLong("customer_id"));
			customerDto.setFullName(rs.getString("full_name"));
			customerDto.setAddress(rs.getString("address"));
			customerDto.setTel(rs.getString("tel"));
			customerDto.setEmail(rs.getString("email"));
			list.add(customerDto);	
			}
		}catch (SQLException e){
			e.printStackTrace();
		}finally{
			if(rs !=null) {
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(pst != null) {
				try {
					pst.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		return Response.ok().entity(list).build();
	}
	/*@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createCustomer(CustomerDto customer) {
		Connection conn = null;
		PreparedStatement pst = null;
			
	}*/

}
