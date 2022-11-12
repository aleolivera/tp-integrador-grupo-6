package com.example.comedores.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.comedores.entidades.Reporte;

public class ReportesViewModel extends ViewModel {

    private MutableLiveData<Reporte> ReporteMDL;

    public ReportesViewModel() {
        ReporteMDL = new MutableLiveData<>();
    }

    public LiveData<Reporte> getData() {
        return ReporteMDL;
    }

    public void setData(Reporte reporte) {
        this.ReporteMDL.setValue(reporte);
    }

    public void postData(Reporte reporte) {
        this.ReporteMDL.postValue(reporte);
    }
}
