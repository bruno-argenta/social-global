package test;

import com.myklover.api.datainfo.user.in.LoginRegistrationIn;
import com.myklover.api.user.LoginRegistrationAPI;

public class MaintTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LoginRegistrationIn reg = new LoginRegistrationIn();
		reg.setPassword("pass");
		reg.setProvider("myklover");
		reg.setUsername("bruno");

		try {
			LoginRegistrationAPI.registerUser(reg);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}