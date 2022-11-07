package com.example.comedores.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.comedores.entidades.Usuario;

public class UsuarioViewModel extends ViewModel {
    private MutableLiveData<Usuario> usuarioMLD;

    public UsuarioViewModel(){
        usuarioMLD= new MutableLiveData<>();
    }

    public LiveData<Usuario> getData(){ return usuarioMLD; }
    public void setData(Usuario usuario){ this.usuarioMLD.setValue(usuario); }
    public void postData(Usuario usuario){ this.usuarioMLD.postValue(usuario);}
}
