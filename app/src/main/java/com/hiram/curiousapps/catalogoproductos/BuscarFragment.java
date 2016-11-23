package com.hiram.curiousapps.catalogoproductos;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


public class BuscarFragment extends Fragment {

    EditText etCodigo, etNombre, etPrecio, etDescripcion;
    Button btnBuscar;
    ProductosSQLiteHelper bdProductos;
    Puente puente;

    private OnFragmentInteractionListener mListener;

    public BuscarFragment() {
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
        //return inflater.inflate(R.layout.fragment_buscar, container, false);
        View item = inflater.inflate(R.layout.fragment_buscar, container, false);
        etCodigo = (EditText) item.findViewById(R.id.etCodigo);
        etNombre = (EditText) item.findViewById(R.id.etNombre);
        etPrecio = (EditText) item.findViewById(R.id.etPrecio);
        etDescripcion = (EditText) item.findViewById(R.id.etDescripcion);
        btnBuscar = (Button) item.findViewById(R.id.btnBuscar);

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String codigo = etCodigo.getText().toString();
                buscarCodigoBarras(codigo);
            }
        });
        return item;
    }

    public void setCodigoBarras(String codigo) {
        this.etCodigo.setText(codigo);
        buscarCodigoBarras(codigo);
    }

    public void buscarCodigoBarras(final String codigo) {
        SQLiteDatabase db = bdProductos.getReadableDatabase();
        String[] args = new String[]{codigo};
        Cursor c = db.rawQuery("SELECT nombre, precio, descripcion FROM Productos WHERE codigo = ?", args);
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                String nombre = c.getString(0);
                String precio = c.getString(1);
                String descripcion = c.getString(2);
                Log.d("DATOS-> ", "nombre: " + nombre + " precio: " + precio + " descripcion: " + descripcion);
                etNombre.setText(nombre);
                etPrecio.setText(precio);
                etDescripcion.setText(descripcion);
            } while (c.moveToNext());
        } else {
            Log.d("BUCAR", "No encontró registro");
            etNombre.setText("");
            etPrecio.setText("");
            etDescripcion.setText("");
            Snackbar.make(getView(), "No se encontró producto", Snackbar.LENGTH_LONG)
                    .setAction("Registrar", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            puente.accionador("registrar", codigo);
                        }
                    })
                    .show();
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        puente = (Puente) getActivity();
    }
}
