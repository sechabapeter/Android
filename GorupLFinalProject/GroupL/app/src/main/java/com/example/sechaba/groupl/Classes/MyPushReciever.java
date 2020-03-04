package com.example.sechaba.groupl.Classes;

import com.backendless.push.BackendlessBroadcastReceiver;
import com.backendless.push.BackendlessPushService;

/**
 * Created by Cedrick on 2017/08/29.
 */

public class MyPushReciever extends BackendlessBroadcastReceiver {

    @Override
    public Class<? extends BackendlessPushService> getServiceClass() {
        return Backservice.class;
    }
}
