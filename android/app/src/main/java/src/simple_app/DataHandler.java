package src.simple_app;

import android.graphics.Bitmap;


public interface DataHandler {

    void setUserImage(Bitmap bitmap);

    void setUserName(String userName);

    void setOrganizationName(String organizationName);
}
