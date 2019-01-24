package com.example.t0ken.cifradoj;

import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends TabActivity implements View.OnClickListener
{
    private TabHost tabHost;
    private Button btnCifrar, btnCopiarCifrar, btnDescifrar, btnCopiarDescifrar;
    private EditText etTextoCifrar, etCodigoCifrar, etTextoDescifrar, etCodigoDescifrar;
    private TextView tvTextoCifrado, tvTextoDescifrado;
    private ThreadCifrar threadCifrar;
    private ThreadDescifrar threadDescifrar;
    private InputMethodManager inputMethodManager;
    private ClipboardManager clipboard;
    private ClipData clip;
    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        iniciarComponentes();
    }

    private void iniciarComponentes()
    {
        setContentView(R.layout.activity_main);
        tabHost = getTabHost();

        btnCifrar = (Button) findViewById(R.id.btnCifrar);
        btnCopiarCifrar = (Button) findViewById(R.id.btnCopiarCifrar);
        btnDescifrar = (Button) findViewById(R.id.btnDescifrar);
        btnCopiarDescifrar = (Button) findViewById(R.id.btnCopiarDescifrar);

        etTextoCifrar = (EditText) findViewById(R.id.etTextoCifrar);
        etCodigoCifrar = (EditText) findViewById(R.id.etCodigoCifrar);
        etTextoDescifrar = (EditText) findViewById(R.id.etTextoDescifrar);
        etCodigoDescifrar = (EditText) findViewById(R.id.etCodigoDescifrar);

        tvTextoCifrado = (TextView) findViewById(R.id.tvTextoCifrado);
        tvTextoCifrado.setText(getText(R.string.consejo));
        tvTextoCifrado.setMovementMethod(new ScrollingMovementMethod());
        tvTextoDescifrado = (TextView) findViewById(R.id.tvTextoDescifrado);
        tvTextoDescifrado.setText(getText(R.string.consejo));
        tvTextoDescifrado.setMovementMethod(new ScrollingMovementMethod());

        tabHost.addTab(tabHost.newTabSpec("tabCifrar").setIndicator(getString(R.string.cifrar), null).setContent(R.id.layoutCifrar));
        tabHost.addTab(tabHost.newTabSpec("tabDescifrar").setIndicator(getString(R.string.descifrar), null).setContent(R.id.layoutDescifrar));

        btnCifrar.setOnClickListener(this);
        btnCopiarCifrar.setOnClickListener(this);
        btnCopiarCifrar.setEnabled(false);
        btnDescifrar.setOnClickListener(this);
        btnCopiarDescifrar.setOnClickListener(this);
        btnCopiarDescifrar.setEnabled(false);

        inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

        clipboard = (ClipboardManager)this.getSystemService(CLIPBOARD_SERVICE);

        builder = new AlertDialog.Builder(MainActivity.this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState)
    {
        outState.putString("etTextoCifrar",etTextoCifrar.getText().toString());
        outState.putInt("etCodigoCifrar",Integer.parseInt(etCodigoCifrar.getText().toString()));
        outState.putString("etTextoDescifrar",etTextoDescifrar.getText().toString());
        outState.putInt("etCodigoDescifrar",Integer.parseInt(etCodigoDescifrar.getText().toString()));
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState, PersistableBundle persistentState)
    {
        etTextoCifrar.setText(savedInstanceState.getString("etTextoCifrar"));
        etCodigoCifrar.setText(savedInstanceState.getInt("etCodigoCifrar"));
        etTextoDescifrar.setText(savedInstanceState.getString("etTextoDescifrar"));
        etCodigoDescifrar.setText(savedInstanceState.getInt("etCodigoDescifrar"));
        super.onRestoreInstanceState(savedInstanceState, persistentState);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCifrar:
                cifrar(etTextoCifrar, etCodigoCifrar);
                break;

            case R.id.btnCopiarCifrar:
                copiar(etTextoCifrar,etCodigoCifrar,tvTextoCifrado);
                btnCopiarCifrar.setEnabled(false);
                break;

            case R.id.btnDescifrar:
                descifrar(etTextoDescifrar, etCodigoDescifrar);
                break;

            case R.id.btnCopiarDescifrar:
                copiar(etTextoDescifrar,etCodigoDescifrar,tvTextoDescifrado);
                btnCopiarDescifrar.setEnabled(false);
                break;

        }
    }


    private void cifrar(EditText t, EditText c)
    {
        if (t.length() > 0)
        {
            if (c.length() > 2)
            {
                if(Integer.parseInt(c.getText().toString())>1)
                {
                    try
                    {
                        threadCifrar = new ThreadCifrar();
                        threadCifrar.execute(t.getText().toString(), Integer.parseInt(c.getText().toString()));
                    } catch (Exception e)
                    {
                        Toast.makeText(this, getText(R.string.error), Toast.LENGTH_SHORT).show();
                    }
                }
                else
                    Toast.makeText(this, getText(R.string.validarLlaveCero), Toast.LENGTH_SHORT).show();
            }
            else
                Toast.makeText(this, getText(R.string.validarLlaveCantidad), Toast.LENGTH_SHORT).show();

        } else
            Toast.makeText(this, getText(R.string.validar), Toast.LENGTH_SHORT).show();
    }


    private void descifrar(EditText t,EditText c)
    {
        if (t.length() > 0)
        {
            if (c.length() > 2)
            {
                if (Integer.parseInt(c.getText().toString())>1)
                {
                    try
                    {
                        threadDescifrar = new ThreadDescifrar();
                        threadDescifrar.execute(t.getText().toString(), Integer.parseInt(c.getText().toString()));
                    } catch (Exception e) {
                        Toast.makeText(this, getText(R.string.error), Toast.LENGTH_SHORT).show();
                    }
                }
                else
                    Toast.makeText(this, getText(R.string.validarLlaveCero), Toast.LENGTH_SHORT).show();
            }
            else
                Toast.makeText(this, getText(R.string.validarLlaveCantidad), Toast.LENGTH_SHORT).show();

        } else
            Toast.makeText(this, getText(R.string.validar), Toast.LENGTH_SHORT).show();
    }


    private void copiar(EditText t,EditText c,TextView s)
    {
        builder.setMessage(getText(R.string.salida)+c.getText().toString());
        AlertDialog dialog = builder.create();
        dialog.show();

        clip = ClipData.newPlainText("",s.getText().toString());
        clipboard.setPrimaryClip(clip);

        Toast.makeText(this, getText(R.string.copiado), Toast.LENGTH_SHORT).show();

        t.setText("");
        c.setText("");
        s.setText(R.string.consejo);
    }

    private class ThreadCifrar extends AsyncTask<Object,Void,String>
    {
        final Cifrado cifrado;

        public ThreadCifrar()
        {
            cifrado=new Cifrado();
        }


        @Override
        protected void onPreExecute()
        {
            tvTextoCifrado.setText("");
            btnCifrar.setEnabled(false);
            inputMethodManager.hideSoftInputFromWindow(etTextoCifrar.getWindowToken(), 0);
            Toast.makeText(MainActivity.this,getText(R.string.proceso),Toast.LENGTH_SHORT).show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Object... params)
        {
            String textoCifrado ="";
            try
            {
                textoCifrado = cifrado.cifrar((String)params[0],(Integer)params[1]);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            return textoCifrado;
        }

        @Override
        protected void onPostExecute(String s)
        {
            tvTextoCifrado.setText(s);
            btnCifrar.setEnabled(true);
            btnCopiarCifrar.setEnabled(true);
            Toast.makeText(MainActivity.this,getText(R.string.hecho),Toast.LENGTH_SHORT).show();
            super.onPostExecute(s);
        }
    }

    private class ThreadDescifrar extends AsyncTask<Object,Void,String>
    {
        final Cifrado cifrado;
        public ThreadDescifrar()
        {
            cifrado=new Cifrado();
        }

        @Override
        protected void onPreExecute()
        {
            tvTextoDescifrado.setText("");
            btnDescifrar.setEnabled(false);
            inputMethodManager.hideSoftInputFromWindow(etTextoDescifrar.getWindowToken(), 0);
            Toast.makeText(MainActivity.this,getText(R.string.proceso),Toast.LENGTH_SHORT).show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Object... params)
        {
            String textoDescifrado ="";
            try
            {
                textoDescifrado = cifrado.descifrar((String)params[0],(Integer)params[1] );
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            return textoDescifrado;
        }

        @Override
        protected void onPostExecute(String s)
        {
            tvTextoDescifrado.setText(s);
            btnDescifrar.setEnabled(true);
            btnCopiarDescifrar.setEnabled(true);
            Toast.makeText(MainActivity.this,getText(R.string.hecho),Toast.LENGTH_SHORT).show();
            super.onPostExecute(s);
        }
    }
}