import com.myklovr.helpers.ComunicationHelper;
import com.myklovr.helpers.CryptoHelper;
import com.myklovr.helpers.exception.BussinesException;
import com.myklovr.logger.Log;

public class TestLog {

	public static void main(String[] args) throws BussinesException {
		String encripted = CryptoHelper.encryptString("myklovr");
		System.out.println(encripted);
		System.out.println(CryptoHelper.decryptString(encripted));
		
	}

}
