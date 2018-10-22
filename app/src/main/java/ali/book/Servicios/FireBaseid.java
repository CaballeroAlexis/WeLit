package ali.book.Servicios;

import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Alexis on 31/08/2017.
 */

public class FireBaseid extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
    }
}
