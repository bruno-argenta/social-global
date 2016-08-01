import com.myklovr.helpers.ComunicationHelper;
import com.myklovr.helpers.CryptoHelper;
import com.myklovr.helpers.exception.BussinesException;
import com.myklovr.logger.Log;

public class TestLog {

	public static void main(String[] args) throws BussinesException {
		ComunicationHelper.sendEmail("b.argenta@gmail.com","algo", "algo");
	}

}
