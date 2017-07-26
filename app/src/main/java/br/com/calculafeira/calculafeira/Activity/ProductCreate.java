package br.com.calculafeira.calculafeira.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import br.com.calculafeira.calculafeira.Model.Product;
import br.com.calculafeira.calculafeira.Model.ProductData;
import br.com.calculafeira.calculafeira.Persistence.DataManager;
import br.com.calculafeira.calculafeira.R;

public class ProductCreate extends AppCompatActivity {

    EditText name_product, price_product;
    Spinner category_product;
    ProductData productData;
    Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_create);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        name_product = (EditText)findViewById(R.id.edit_text_name_product);
        price_product = (EditText)findViewById(R.id.edit_text_price_product);
        category_product = (Spinner)findViewById(R.id.spinner_category_product);

        product = new Product();
        productData = new ProductData();

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.categories));
        ArrayAdapter<String> spinnerArrayAdapter = arrayAdapter;
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        category_product.setAdapter(spinnerArrayAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try
                {
                    product.setNameCategory(category_product.getSelectedItem().toString());
                    product.setNameProduct(name_product.getText().toString());
                    Long idProduct = DataManager.getInstance().getProductDAO().save(product);
                    productData.setFkProduct(idProduct);
                    productData.setPrice(Double.parseDouble(price_product.getText().toString()));
                    productData.setQuantity(0);
                    Long idProductData = DataManager.getInstance().getProductDataDAO().save(productData);

                    Intent intent = new Intent(ProductCreate.this, MainList.class);
                    startActivity(intent);

                    Snackbar.make(view, "Produto salvo com sucesso", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                catch (Exception ex)
                {
                    Snackbar.make(view, "ERRO:" + ex.getMessage(), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    Log.e("Erro ProductCreate", ex.getMessage());
                }
            }
        });
    }

}