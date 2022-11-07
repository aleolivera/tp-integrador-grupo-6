package com.example.comedores.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.comedores.entidades.Solicitud;

import java.util.ArrayList;

public class SolicitudesViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Solicitud>>solicitudes;

    public SolicitudesViewModel(){
        this.solicitudes = new MutableLiveData<>();
    }

    public LiveData<ArrayList<Solicitud>> GetData(){ return solicitudes; }
    public void setData(ArrayList<Solicitud> solicitudes){
        this.solicitudes.setValue(solicitudes);
    }
    public void postData(ArrayList<Solicitud> solicitudes){
        this.solicitudes.postValue(solicitudes);
    }
}
