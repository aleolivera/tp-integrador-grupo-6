package com.example.comedores.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.comedores.entidades.Comedor;
import com.example.comedores.entidades.Usuario;

public class ComedoresViewModel extends ViewModel {

    private MutableLiveData<Comedor> comedorMLD;

    public ComedoresViewModel(){
        comedorMLD= new MutableLiveData<>();
    }

    public LiveData<Comedor> getData(){ return comedorMLD; }
    public void setData(Comedor comedor){ this.comedorMLD.setValue(comedor); }
    public void postData(Comedor comedor){ this.comedorMLD.postValue(comedor);}
}
