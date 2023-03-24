package com.caudron.amusementpark.viewmodels.database_view_model.callback;

public interface IDatabaseCallBack {
    void onSuccess();
    void onFailure(String errorMessage);
}
