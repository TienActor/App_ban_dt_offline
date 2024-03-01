package com.example.doan_sale;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
public class UserViewModel extends ViewModel {
    private final MutableLiveData<Integer> userId = new MutableLiveData<>();
    public void setUserId(int id) {
        userId.setValue(id);
    }
    public LiveData<Integer> getUserId() {
        return userId;
    }
}

