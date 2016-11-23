package com.hiram.curiousapps.catalogoproductos;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class RegistrarFragment extends Fragment {

    Button btnRegistrar, btnLimpiar;
    EditText etNombre, etPrecio, etCodigo, etDescripcion;
    ProductosSQLiteHelper bdProductos;

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private OnFragmentInteractionListener mListener;

    public RegistrarFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bdProductos = new ProductosSQLiteHelper(getActivity(), "DBProductos", null, 1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View item = inflater.inflate(R.layout.fragment_registrar, container, false);
        btnRegistrar = (Button) item.findViewById(R.id.btnRegistrar);
        btnLimpiar = (Button) item.findViewById(R.id.btnLimpiar);
        etCodigo = (EditText) item.findViewById(R.id.etCodigo);
        etNombre = (EditText) item.findViewById(R.id.etNombre);
        etPrecio = (EditText) item.findViewById(R.id.etPrecio);
        etDescripcion = (EditText) item.findViewById(R.id.etDescripcion);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarProducto();
            }
        });

        btnLimpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                limpiarRegistro();
            }
        });

        return item;
    }

    public void setCodigoBarras(String codigo) {
        this.etCodigo.setText(codigo);
    }

    public void limpiarRegistro() {
        etCodigo.setText("");
        etNombre.setText("");
        etPrecio.setText("");
        etDescripcion.setText("");
    }

    public void registrarProducto() {
        SQLiteDatabase db = bdProductos.getWritableDatabase();
        String registro;
        ContentValues nuevoProducto = new ContentValues();
        nuevoProducto.put("codigo", etCodigo.getText().toString());
        nuevoProducto.put("nombre", etNombre.getText().toString());
        nuevoProducto.put("precio", etPrecio.getText().toString());
        nuevoProducto.put("descripcion", etDescripcion.getText().toString());
        if (db.insert("Productos", null, nuevoProducto) != -1) {
            registro = "Registro exitoso";
        } else {
            registro = "Registro fallido";
        }
        Snackbar.make(getView(), registro, Snackbar.LENGTH_SHORT).show();
        limpiarRegistro();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
